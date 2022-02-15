package com.sluzbenik.SluzbenikApp.repository.rdfRepository;

import com.sluzbenik.SluzbenikApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import com.sluzbenik.SluzbenikApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Component;

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
            String request = res.get("sertifikat").toString();
            requestList.add(request.substring(DZS_NAMESPACE_PATH.length())); //uzimamo samo id
        }
        query.close();
        return requestList;
    }
}
