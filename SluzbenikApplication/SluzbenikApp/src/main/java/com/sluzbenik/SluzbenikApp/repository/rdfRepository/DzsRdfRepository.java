package com.sluzbenik.SluzbenikApp.repository.rdfRepository;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResult;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import com.sluzbenik.SluzbenikApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

@Component
public class DzsRdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    public DzsRdfRepository() { conn = AuthenticationUtilities.setUpPropertiesFusekiJena(); }

    public int getDzsBetweenDates(String dateFrom, String dateTo) {
        String sparqlCondition = String.format("SELECT (COUNT(?subject) as ?subjectCount) \n" +
                "WHERE {" +
                "?subject" + " <" + PREDICATE_DZS_CREATED_AT + "> " + "?datum" + "\n" +
                "FILTER(<http://www.w3.org/2001/XMLSchema#date>(?datum) " +
                ">= " +
                "<http://www.w3.org/2001/XMLSchema#date>(\"%s\") " +
                "&& " +
                "<http://www.w3.org/2001/XMLSchema#date>(?datum) " +
                "<= " +
                "<http://www.w3.org/2001/XMLSchema#date>(\"%s\"))" + "\n" +
                "}", dateFrom, dateTo);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint +
                DZS_NAMED_GRAPH_URI, sparqlCondition);

        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        ResultSet results = query.execSelect();
        QuerySolution res = results.nextSolution();
        query.close();

        return Integer.parseInt(res.get("subjectCount").toString().split("\\^")[0]);
    }

    public void getDzsWithReferences(SearchResults searchResults){
        for (SearchResult searchResult: searchResults.getSearchResults()) {
            // prvo gledas potvrde
            String sparqlCondition =
                    "<" + DZS_NAMESPACE_PATH + searchResult.getDocumentId() + "> "+ REF_PREDICATE_DB +" ?object .";
            this.getReferences("object", searchResult, sparqlCondition, DZS_NAMED_GRAPH_URI, POTVRDA_NAMESPACE_PATH);

            // onda gledas zahteve
            sparqlCondition =
                    "<" + DZS_NAMESPACE_PATH + searchResult.getDocumentId() + "> "+ REF_BY_PREDICATE_DB +" ?object .";
            this.getReferences("subject", searchResult, sparqlCondition, DZS_NAMED_GRAPH_URI, ZAHTEV_NAMESPACE_PATH);

        }
    }

    public void getReferences(String type, SearchResult searchResult, String sparqlCondition, String namedGraphUri, String substringPath){
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + namedGraphUri, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            if(Objects.equals(type, "object")) {
                String documentIdPath = res.get("object").toString();
                searchResult.addReference(documentIdPath.substring(substringPath.length()));
            }
            if(Objects.equals(type, "subject")) {
                String documentIdPath = res.get("object").toString();
                searchResult.addReferenceBy(documentIdPath.substring(substringPath.length()));
            }
        }
        query.close();
    }

}
