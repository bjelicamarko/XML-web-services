package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Component;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class InteresovanjeRdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    public InteresovanjeRdfRepository(){
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public int getInteresovanjaBetweenDates(String dateFrom, String dateTo) {
        String sparqlCondition = String.format("SELECT (COUNT(?subject) as ?subjectCount) \n" +
                "WHERE {" +
                "?subject" + " <" +PREDICATE_INTERESOVANJE_CREATED  + "> " + "?datum" + "\n" +
                "FILTER(?datum >= \"%s\" && ?datum <= \"%s\")" + "\n" +
                "}", dateFrom, dateTo);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint +
                INTERESOVANJE_NAMED_GRAPH_URI, sparqlCondition);

        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        ResultSet results = query.execSelect();
        QuerySolution res = results.nextSolution();
        query.close();

        return Integer.parseInt(res.get("subjectCount").toString().split("\\^")[0]);
    }
}
