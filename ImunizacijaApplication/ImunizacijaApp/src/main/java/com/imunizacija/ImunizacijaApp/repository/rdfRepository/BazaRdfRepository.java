package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class BazaRdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    public BazaRdfRepository() {
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public int getNewVaccineReports(String dateFrom, String dateTo) {
        String sparqlCondition = String.format(
                "SELECT (COUNT(?subject) as ?subjectCount)\n" +
                        "WHERE {" +
                        "?subject" + " <" +PREDICATE_DOZA_GIVEN  + "> " + "?datum" + "\n" +
                        "FILTER(?datum >= \"%s\" && ?datum <= \"%s\" && regex(str(?subject), \"-1\"))" + "\n" +
                        "}", dateFrom, dateTo);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint +
                DOZA_NAMED_GRAPH_URI, sparqlCondition);

        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        ResultSet results = query.execSelect();
        QuerySolution res = results.nextSolution();
        query.close();

        return Integer.parseInt(res.get("subjectCount").toString().split("\\^")[0]);
    }

    public Map<String, Integer> getVaccineReports(String dateFrom, String dateTo) {
        String sparqlCondition = String.format(
                "SELECT ?tip (COUNT(?tip) as ?tipCount)\n" +
                "WHERE {" +
                "?subject" + " <" +PREDICATE_DOZA_GIVEN  + "> " + "?datum . " + "\n" +
                "?subject" + " <" +PREDICATE_DOZA_TIP  + "> " + "?tip" + "\n" +
                "FILTER(?datum >= \"%s\" && ?datum <= \"%s\")" + "\n" +
                "}\n" +
                "GROUP BY ?tip", dateFrom, dateTo);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint +
                DOZA_NAMED_GRAPH_URI, sparqlCondition);

        Map<String, Integer> mapa = new HashMap<>();
        mapa.put("Ukupno", 0);

        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String tip = res.get("tip").toString();
            String tipCount = res.get("tipCount").toString().split("\\^")[0];

            mapa.put(tip, Integer.valueOf(tipCount));
            mapa.put("Ukupno", mapa.get("Ukupno") + Integer.parseInt(tipCount));
        }
        query.close();

        return mapa;
    }
}
