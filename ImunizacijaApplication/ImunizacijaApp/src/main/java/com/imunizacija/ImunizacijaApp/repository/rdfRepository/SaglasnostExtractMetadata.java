package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

public class SaglasnostExtractMetadata extends ExtractMetadata {

    public SaglasnostExtractMetadata(AuthenticationUtilities.ConnectionPropertiesFusekiJena conn) {
        super(conn);
    }

    public void extract(Saglasnost saglasnost) {
        Model model = createModel();

        Resource resource = model.createResource(SAGLASNOST_NAMESPACE_PATH + saglasnost.getXmlId());

        Property createdAt = model.createProperty(PREDICATE_NAMESPACE, "createdAt");
        Literal date = model.createLiteral(saglasnost.getDatum().toString());
        model.add(model.createStatement(resource, createdAt, date));

        Property issuedTo = model.createProperty(PREDICATE_NAMESPACE, "issuedTo");
        String idOsobe = "";
        if(saglasnost.getDrzavljanstvo().getTip().equals("DOMACE")) idOsobe = saglasnost.getDrzavljanstvo().getJMBG();
        if(saglasnost.getDrzavljanstvo().getTip().equals("STRANO_SA_BORAVKOM")) idOsobe = saglasnost.getDrzavljanstvo().getBrPasosa();
        if(saglasnost.getDrzavljanstvo().getTip().equals("STRANO_BEZ_BORAVKA")) idOsobe = saglasnost.getDrzavljanstvo().getEvidencioniBrojStranca();
        Resource person = model.createResource(OSOBA_NAMESPACE_PATH + idOsobe);
        model.add(model.createStatement(resource, issuedTo, person));

        // referenca ka interesovanju
        String interesovanjeUri = getInteresovanjeId(idOsobe);
        Property refBy = model.createProperty(PREDICATE_NAMESPACE, "refBy");
        Resource interesovanje = model.createResource(interesovanjeUri);
        model.add(model.createStatement(resource, refBy, interesovanje));

        if(saglasnost.getOVakcinaciji() != null) {
            Property issuedBy = model.createProperty(PREDICATE_NAMESPACE, "issuedBy");
            Resource doc = model.createResource(OSOBA_NAMESPACE_PATH + saglasnost.getOVakcinaciji().getPodaciOLekaru().getJMBG());
            model.add(model.createStatement(resource, issuedBy, doc));
        }


        super.modelWrite(model, SAGLASNOST_NAMED_GRAPH_URI);
    }

    public String getInteresovanjeId(String idKorisnika){
        // Issuing a simple SPARQL query to make sure the changes were made...
		System.out.println("[INFO] Making sure the changes were made in the named graph \"" + OSOBA_NAMED_GRAPH_URI + "\".");
        String sparqlCondition = "<" + OSOBA_NAMESPACE_PATH + idKorisnika +">" + " " + "<" + PREDICATE_NAMESPACE + "issued" + ">" +" ?o";
		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + OSOBA_NAMED_GRAPH_URI, sparqlCondition);

		// Create a QueryExecution that will access a SPARQL service over HTTP
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the collection, dump output response with the use of ResultSetFormatter
		ResultSet results = query.execSelect();

        String interesovanjeId = results.nextSolution().get("o").toString();
		query.close();
        return interesovanjeId;
    }
}
