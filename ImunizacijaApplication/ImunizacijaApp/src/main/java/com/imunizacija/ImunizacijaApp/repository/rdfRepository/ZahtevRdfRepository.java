package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class ZahtevRdfRepository extends ExtractMetadata{

    public ZahtevRdfRepository() {
        super(AuthenticationUtilities.setUpPropertiesFusekiJena());
    }


    public int getZahteviBetweenDates(String dateFrom, String dateTo) {
        String sparqlCondition = String.format("SELECT (COUNT(?subject) as ?subjectCount) \n" +
                "WHERE {" +
                "?subject" + " <" +PREDICATE_ZAHTEV_CREATED_AT  + "> " + "?datum" + "\n" +
                "FILTER(<http://www.w3.org/2001/XMLSchema#date>(?datum) >= " +
                "<http://www.w3.org/2001/XMLSchema#date>(\"%s\") && " +
                "<http://www.w3.org/2001/XMLSchema#date>(?datum) " +
                "<= <http://www.w3.org/2001/XMLSchema#date>(\"%s\"))" + "\n" +
                "}", dateFrom, dateTo);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint +
                ZAHTEV_NAMED_GRAPH_URI, sparqlCondition);

        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        ResultSet results = query.execSelect();
        QuerySolution res = results.nextSolution();
        query.close();

        return Integer.parseInt(res.get("subjectCount").toString().split("\\^")[0]);
    }

    public String getStatusZahtev(String id) {
        String sparqlCondition = "<" + ZAHTEV_NAMESPACE_PATH + id + ">" +  HAS_STATUS_PREDICATE_DB + "?status";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + ZAHTEV_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        String status = null;
        if(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            status = res.get("status").toString();
        }
        query.close();
        return status;
    }

    public void setStatusZahtev(String id, String status, String oldStatus) {
        Model oldModel = super.createModel();
        Model newModel = super.createModel();

        Resource resourceO = oldModel.createResource(ZAHTEV_NAMESPACE_PATH + id);

        Property hasStatusO = oldModel.createProperty(PREDICATE_NAMESPACE, "hasStatus");
        Literal statusO = oldModel.createLiteral(oldStatus);

        Resource resourceN = newModel.createResource(ZAHTEV_NAMESPACE_PATH + id);

        Property hasStatusN = newModel.createProperty(PREDICATE_NAMESPACE, "hasStatus");
        Literal statusN = newModel.createLiteral(status);


        oldModel.add(oldModel.createStatement(resourceO, hasStatusO, statusO));
        newModel.add(newModel.createStatement(resourceN, hasStatusN, statusN));


        super.modelOverWrite(oldModel, newModel, ZAHTEV_NAMED_GRAPH_URI);
    }

    public void deleteZahtev(String id) {
        System.out.println("[INFO] Inserting the triples to a named graph \"" + ZAHTEV_NAMED_GRAPH_URI + "\".");
        String sparqlUpdate = "WITH <http://localhost:8083/ImunizacijaDataset/data/zahtev/metadata> \n" +
                "DELETE { <http://www.vakc-sistem.rs/zahtev/" + id + "> ?p ?o } \n" +
                "WHERE \n" +
                "  { ?s ?p ?o}";
        System.out.println(sparqlUpdate);

        HttpClient hc = super.authHttpClient("admin", "pw123");

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        // UpdateProcessor sends update request to a remote SPARQL update service.
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint, hc);
        processor.execute();
    }
}
