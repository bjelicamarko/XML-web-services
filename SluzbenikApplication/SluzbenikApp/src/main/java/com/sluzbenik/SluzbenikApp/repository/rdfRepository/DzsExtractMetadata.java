package com.sluzbenik.SluzbenikApp.repository.rdfRepository;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Component;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

@Component
public class DzsExtractMetadata extends ExtractMetadata {
    public DzsExtractMetadata() {
        super(AuthenticationUtilities.setUpPropertiesFusekiJena());
    }

    /** String idSluzbenika - unique id from logged user **/
    public void extract(DigitalniZeleniSertifikat digitalniZeleniSertifikat, String idZahteva) {
        Model model = createModel();

        Resource resource = model.createResource(DZS_NAMESPACE_PATH + digitalniZeleniSertifikat.getXmlId());

        Property createdAt = model.createProperty(PREDICATE_NAMESPACE, "createdAt");
        Literal date = model.createLiteral(digitalniZeleniSertifikat.getPodaciOSertifikatu().getDatumIzdavanjaSertifikata().toString());
        model.add(model.createStatement(resource, createdAt, date));

        Property issuedTo = model.createProperty(PREDICATE_NAMESPACE, "issuedTo");
        Resource person = model.createResource(OSOBA_NAMESPACE_PATH + digitalniZeleniSertifikat.getPodaciOPrimaocu().getJMBG());
        model.add(model.createStatement(resource, issuedTo, person));

        Property createdBy = model.createProperty(PREDICATE_NAMESPACE, "createdBy");
        Resource sluzbenik = model.createResource(OSOBA_NAMESPACE_PATH + digitalniZeleniSertifikat.getIdSluzbenika());
        model.add(model.createStatement(resource, createdBy, sluzbenik));

        Property refBy = model.createProperty(PREDICATE_NAMESPACE, "refBy");
        Resource zahtevRef = model.createResource(ZAHTEV_NAMESPACE_PATH + idZahteva);
        model.add(model.createStatement(resource, refBy, zahtevRef));

        super.modelWrite(model, DZS_NAMED_GRAPH_URI);
    }
}
