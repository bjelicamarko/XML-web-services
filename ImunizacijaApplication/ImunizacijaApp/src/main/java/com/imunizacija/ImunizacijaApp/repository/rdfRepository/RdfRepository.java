package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities.*;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class RdfRepository {

    protected ConnectionPropertiesFusekiJena conn;

    public RdfRepository(){
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    public String getInterestFromUser(String userID){
        String sparqlCondition = "<" + OSOBA_NAMESPACE_PATH + userID + ">" + ISSUED_PREDICATE_DB + "?interesovanje";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + OSOBA_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        String interesovanje = null;
        if(results.hasNext()) {
            interesovanje = results.nextSolution().get("interesovanje").toString();
            interesovanje = interesovanje.substring(INTERESOVANJE_NAMESPACE_PATH.length()); //uzimamo samo id
        }
        query.close();

        return interesovanje;
    }

    public List<String> getConsentsFromUser(String userID){ //sortirani po datumu, rastuce
        String sparqlCondition = "?saglasnost " +  ISSUED_TO_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + userID + "> ."
                + "?saglasnost " + CREATED_AT_PREDICATE_DB + " ?datum";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + SAGLASNOST_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String[]> consentList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String consent = res.get("saglasnost").toString();
            String date = res.get("datum").toString();
            consentList.add(new String[]{consent.substring(SAGLASNOST_NAMESPACE_PATH.length()), date}); //uzimamo samo id
        }
        query.close();
        consentList.sort(Comparator.comparing(o -> LocalDate.parse(o[1])));

        List<String> sortedConsent = new ArrayList<>();
        consentList.forEach(consent -> sortedConsent.add(consent[0]));
        return sortedConsent;
    }

    public List<String> getVaccinationAffirmationFromUser(String userID){ //sortirani po datumu, rastuce
        String sparqlCondition = "?potvrda " +  ISSUED_TO_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + userID + "> ."
                + "?potvrda " + CREATED_AT_PREDICATE_DB + " ?datum";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + POTVRDA_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String[]> affirmationList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String affirmation = res.get("potvrda").toString();
            String date = res.get("datum").toString();
            affirmationList.add(new String[]{affirmation.substring(POTVRDA_NAMESPACE_PATH.length()), date}); //uzimamo samo id
        }
        query.close();
        affirmationList.sort(Comparator.comparing(o -> LocalDate.parse(o[1])));

        List<String> sortedAffirmation = new ArrayList<>();
        affirmationList.forEach(affirmation -> sortedAffirmation.add(affirmation[0]));
        return sortedAffirmation;
    }

    public List<String[]> getRequestForCertificateFromUser(String userID){ //njih ne sortiramo
        String sparqlCondition = "?zahtev " +  CREATED_BY_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + userID + "> . " +
                "?zahtev " + HAS_STATUS_PREDICATE_DB + " ?status";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + ZAHTEV_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String[]> requestList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String request = res.get("zahtev").toString();
            String requestStatus = res.get("status").toString();
            requestList.add(new String[]{request.substring(ZAHTEV_NAMESPACE_PATH.length()), requestStatus}); //uzimamo samo id
        }
        query.close();
        return requestList;
    }

    public boolean userHasPendingRequest(String userID){
        String sparqlCondition = "?zahtev " +  CREATED_BY_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + userID + "> . " +
                "?zahtev " + HAS_STATUS_PREDICATE_DB + " 'pending'";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + ZAHTEV_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();

        boolean hasNext = results.hasNext();
        query.close();

        return hasNext;
    }

    public DocumentsOfUserDTO getDocumentsOfUser(String userID){
        DocumentsOfUserDTO documentsOfUserDTO = new DocumentsOfUserDTO();
        documentsOfUserDTO.setInteresovanjeID(this.getInterestFromUser(userID));
        documentsOfUserDTO.setPotvrdaOVakcList(this.getVaccinationAffirmationFromUser(userID));
        documentsOfUserDTO.setSalgasnostList(this.getConsentsFromUser(userID));

        List<String[]> requests = this.getRequestForCertificateFromUser(userID);
        List<String> pending = new ArrayList<>();
        List<String> accepted = new ArrayList<>();

        for (String[] strArray : requests){
            if (strArray[1].equals("pending")){
                pending.add(strArray[0]);
            }
            if (strArray[1].equals("accepted")){
                accepted.add(strArray[0]);
            }
        }
        documentsOfUserDTO.setZahtevDZSList(pending);
        documentsOfUserDTO.setPrihvaceniZahtevDZSList(accepted);
        return documentsOfUserDTO;
    }

    public String getPosljednjaPotvrdaIzBazeId(String korisnikId) {
        String sparqlCondition = "?potvrda " +  ISSUED_TO_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + korisnikId + "> ."
                + "?potvrda " + CREATED_AT_PREDICATE_DB + " ?datum";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + POTVRDA_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String[]> affirmationList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String affirmation = res.get("potvrda").toString();
            String date = res.get("datum").toString();
            affirmationList.add(new String[]{affirmation.substring(POTVRDA_NAMESPACE_PATH.length()), date}); //uzimamo samo id
        }
        query.close();
        if(affirmationList.isEmpty())
            return "-1"; // ako nema onda -1
        affirmationList.sort(Comparator.comparing(o -> LocalDate.parse(o[1])));

        List<String> sortedAffirmation = new ArrayList<>();
        affirmationList.forEach(affirmation -> sortedAffirmation.add(affirmation[0]));
        return sortedAffirmation.get(sortedAffirmation.size() - 1);
    }

    public boolean userHasCertificate(String userId) {
        String sparqlCondition = "?potvrda " +  ISSUED_TO_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + userId + "> .";

        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + POTVRDA_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();

        boolean hasNext = results.hasNext();
        query.close();

        return hasNext;
    }

    public String generateRDFJSON(String doumentRdfUrl, String id, String namedGraphUri) throws IOException {
        String sparqlCondition = "<" + doumentRdfUrl + id + "> ?predicate ?object .";
        System.out.println(sparqlCondition);
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + namedGraphUri, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();

        // checking if ResultSet is empty
//        if (results.hasNext()) {
//            query.close();
//            return "{}";
//        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(byteArrayOutputStream, results);
        String json = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        int indexOfSubStr = json.indexOf("results");
        String newJson  = String.format("{\n  %s", json.substring(indexOfSubStr - 1)); // creating substring from results to end
        byteArrayOutputStream.close();
        query.close();
        return newJson;
    }
}
