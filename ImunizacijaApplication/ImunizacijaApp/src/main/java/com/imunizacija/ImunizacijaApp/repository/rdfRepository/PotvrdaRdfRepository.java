package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResult;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class PotvrdaRdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    @Autowired
    private RdfRepository rdfRepository;

    public PotvrdaRdfRepository(){
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public void getPotvrdaWithReferences(SearchResults searchResults){
        for (SearchResult searchResult: searchResults.getSearchResults()) {
            // prvo gledas saglasnosti
            String sparqlCondition =
                    "<" + POTVRDA_NAMESPACE_PATH + searchResult.getDocumentId() + "> "+ REF_BY_PREDICATE_DB +" ?object .";
            getReferences("object", searchResult, sparqlCondition, POTVRDA_NAMED_GRAPH_URI, SAGLASNOST_NAMESPACE_PATH);
            // onda gledas dzs
            sparqlCondition =
                    "<" + POTVRDA_NAMESPACE_PATH + searchResult.getDocumentId() + "> "+ REF_PREDICATE_DB +" ?object .";
            getReferences("subject", searchResult, sparqlCondition, POTVRDA_NAMED_GRAPH_URI, DZS_NAMESPACE_PATH);
            // potom bi trebalo da se gleda i za digitalni zeleni na drugom back-u
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

