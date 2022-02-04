package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.util.DozaSaUstanovom;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities.ConnectionPropertiesFusekiJena;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

public class PotvrdaExtractMetadata extends ExtractMetadata{

    public PotvrdaExtractMetadata(ConnectionPropertiesFusekiJena conn) {
        super(conn);
    }

    private void extractForDose(DozaSaUstanovom dozaSaUstanovom, String idPotvrde){
        Model model = createModel();

        Resource resource = model.createResource(DOZA_NAMESPACE_PATH + idPotvrde + "-" + dozaSaUstanovom.getRedniBroj());

        Property belongsTo = model.createProperty(PREDICATE_NAMESPACE, "belongsTo");
        Resource potvrda = model.createResource(POTVRDA_NAMESPACE_PATH + idPotvrde);
        model.add(model.createStatement(resource, belongsTo, potvrda));

        Property givenAt = model.createProperty(PREDICATE_NAMESPACE, "givenAt");
        Literal date = model.createLiteral(dozaSaUstanovom.getDatum().toString());
        model.add(model.createStatement(resource, givenAt, date));

        Property madeBy = model.createProperty(PREDICATE_NAMESPACE, "madeBy");
        Literal manufacturer = model.createLiteral(dozaSaUstanovom.getProizvodjac());
        model.add(model.createStatement(resource, madeBy, manufacturer));

        Property ordinal = model.createProperty(PREDICATE_NAMESPACE, "hasOrdinal");
        Literal number = model.createLiteral(dozaSaUstanovom.getRedniBroj().toString());
        model.add(model.createStatement(resource, ordinal, number));

        Property vaccineType = model.createProperty(PREDICATE_NAMESPACE, "hasVaccineType");
        Literal type = model.createLiteral(dozaSaUstanovom.getTip());
        model.add(model.createStatement(resource, vaccineType, type));

        super.modelWrite(model, DOZA_NAMED_GRAPH_URI);
    }

    public void extract(PotvrdaOVakcinaciji potvrda) {
        Model model = createModel();

        Resource resource = model.createResource(POTVRDA_NAMESPACE_PATH + potvrda.getXmlId());

        Property createdAt = model.createProperty(PREDICATE_NAMESPACE, "createdAt");
        Literal date = model.createLiteral(potvrda.getPodaciOPotvrdi().getDatumIzdavanja().toString());
        model.add(model.createStatement(resource, createdAt, date));

        Property issuedTo = model.createProperty(PREDICATE_NAMESPACE, "issuedTo");
        Resource person = model.createResource(OSOBA_NAMESPACE_PATH + potvrda.getPodaciOPrimaocu().getJMBG());
        model.add(model.createStatement(resource, issuedTo, person));

        Property refBy = model.createProperty(PREDICATE_NAMESPACE, "refBy");
        for (String saglasnostUri: getSaglasnostIds(potvrda.getPodaciOPrimaocu().getJMBG()))
        {
            System.out.println(saglasnostUri);
            Resource saglasnost = model.createResource(saglasnostUri);
            model.add(model.createStatement(resource, refBy, saglasnost));
        }

        super.modelWrite(model, POTVRDA_NAMED_GRAPH_URI);

        for (DozaSaUstanovom doza : potvrda.getPodaciOVakcini().getDoze()){
            extractForDose(doza, potvrda.getXmlId());
        }
    }

    public List<String> getSaglasnostIds(String idKorisnika){
        // Issuing a simple SPARQL query to make sure the changes were made...
        System.out.println("[INFO] Making sure the changes were made in the named graph \"" + SAGLASNOST_NAMED_GRAPH_URI + "\".");
        String sparqlCondition = "?s "+ "<" + PREDICATE_NAMESPACE + "issuedTo" + ">" + " <" + OSOBA_NAMESPACE_PATH + idKorisnika +">";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + SAGLASNOST_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String> saglasnostIds = new ArrayList<>();
        while(results.hasNext())
            saglasnostIds.add(results.nextSolution().get("s").toString());
        query.close();
        return saglasnostIds;
    }
}
