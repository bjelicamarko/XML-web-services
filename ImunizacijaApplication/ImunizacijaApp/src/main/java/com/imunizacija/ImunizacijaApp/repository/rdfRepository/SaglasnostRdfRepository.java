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
public class SaglasnostRdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    @Autowired
    private RdfRepository rdfRepository;

    public SaglasnostRdfRepository(){
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public void getSaglasnostWithReferences(SearchResults searchResults){
        for (SearchResult searchResult: searchResults.getSearchResults()) {
            // prvo gledas interesovanja
            String sparqlCondition =
                    "<" + SAGLASNOST_NAMESPACE_PATH + searchResult.getDocumentId() + "> "+ REF_BY_PREDICATE_DB +" ?object .";
            this.rdfRepository.getReferences("object", searchResult, sparqlCondition, SAGLASNOST_NAMED_GRAPH_URI, INTERESOVANJE_NAMESPACE_PATH);
            // onda gledas potvrde
            sparqlCondition = "?subject "+ REF_BY_PREDICATE_DB + " <" + SAGLASNOST_NAMESPACE_PATH + searchResult.getDocumentId()+">";
            this.rdfRepository.getReferences("subject", searchResult, sparqlCondition, POTVRDA_NAMED_GRAPH_URI, POTVRDA_NAMESPACE_PATH);

        }
    }
}
