package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities.ConnectionPropertiesFusekiJena;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import java.io.ByteArrayOutputStream;

import static com.imunizacija.ImunizacijaApp.repository.Constants.DOZA_NAMED_GRAPH_URI;
import static com.imunizacija.ImunizacijaApp.repository.Constants.PREDICATE_NAMESPACE;

public abstract class ExtractMetadata {

    protected ConnectionPropertiesFusekiJena conn;

    public ExtractMetadata(ConnectionPropertiesFusekiJena conn){
        this.conn = conn;
    }

    protected Model createModel(){
        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix("pred", PREDICATE_NAMESPACE);
        return model;
    }

    protected void modelWrite(Model model, String namedGraphURI){
        model.write(System.out, SparqlUtil.NTRIPLES);
        // Issuing the SPARQL update...
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);

        // Updating the named graph with the triples from RDF model
        System.out.println("[INFO] Inserting the triples to a named graph \"" + namedGraphURI + "\".");
        String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + namedGraphURI, out.toString());
        System.out.println(sparqlUpdate);

        HttpClient hc = authHttpClient("admin", "pw123");

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        // UpdateProcessor sends update request to a remote SPARQL update service.
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint, hc);
        processor.execute();
    }

    private HttpClient authHttpClient(String user, String password) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        Credentials credentials = new UsernamePasswordCredentials(user, password);
        credsProvider.setCredentials(AuthScope.ANY, credentials);
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
    }
}
