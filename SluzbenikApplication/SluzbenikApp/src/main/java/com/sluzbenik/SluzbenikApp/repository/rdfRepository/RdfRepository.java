package com.sluzbenik.SluzbenikApp.repository.rdfRepository;

import com.sluzbenik.SluzbenikApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import com.sluzbenik.SluzbenikApp.utils.SparqlUtil;
import org.apache.jena.query.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;


@Component
public class RdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    public RdfRepository(){
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public List<String> getDZSFromUser(String userID){ //njih ne sortiramo
        String sparqlCondition = "?sertifikat " +  ISSUED_TO_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + userID + ">";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + DZS_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String> requestList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String certificate = res.get("sertifikat").toString();
            requestList.add(certificate.substring(DZS_NAMESPACE_PATH.length())); //uzimamo samo id
        }
        query.close();
        return requestList;
    }

    public String generateJSON(String documentRdfUrl, String id, String namedGraphUri) throws IOException {
        String sparqlCondition = "VALUES ?subject { <" + documentRdfUrl + id + "> }" +
                " ?subject ?predicate ?object .";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + namedGraphUri, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(byteArrayOutputStream, results);
        String json = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        int indexOfSubStr = json.indexOf("bindings");
        String newJson  = String.format("{\n  %s", json.substring(indexOfSubStr - 1)); // creating substring from results to end
        byteArrayOutputStream.close();
        query.close();
        return newJson;
    }
}
