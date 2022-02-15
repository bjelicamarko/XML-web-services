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
public class ZahtevRdfRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    public ZahtevRdfRepository() {
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public int getZahteviBetweenDates(String dateFrom, String dateTo) {
        String sparqlCondition = String.format("SELECT (COUNT(?subject) as ?subjectCount) \n" +
                "WHERE {" +
                "?subject" + " <" +PREDICATE_ZAHTEV_CREATED_AT  + "> " + "?datum" + "\n" +
                "FILTER(?datum >= \"%s\" && ?datum <= \"%s\")" + "\n" +
                "}", dateFrom, dateTo);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint +
                ZAHTEV_NAMED_GRAPH_URI, sparqlCondition);

        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        ResultSet results = query.execSelect();
        QuerySolution res = results.nextSolution();
        query.close();

        return Integer.parseInt(res.get("subjectCount").toString().split("\\^")[0]);
    }
}
