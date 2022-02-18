package com.sluzbenik.SluzbenikApp.repository.rdfRepository;

import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import com.sluzbenik.SluzbenikApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Component;

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

}
