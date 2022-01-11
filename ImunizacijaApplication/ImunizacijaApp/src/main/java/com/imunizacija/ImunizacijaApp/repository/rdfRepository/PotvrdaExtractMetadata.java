package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.util.DozaSaUstanovom;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities.ConnectionPropertiesFusekiJena;
import org.apache.jena.rdf.model.*;

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

        super.modelWrite(model, POTVRDA_NAMED_GRAPH_URI);

        for (DozaSaUstanovom doza : potvrda.getPodaciOVakcini().getDoze()){
            extractForDose(doza, potvrda.getXmlId());
        }
    }
}
