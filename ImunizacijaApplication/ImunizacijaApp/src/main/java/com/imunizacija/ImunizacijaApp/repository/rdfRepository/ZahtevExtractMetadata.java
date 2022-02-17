package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.util.Drzavljanstvo;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Component;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class ZahtevExtractMetadata  extends ExtractMetadata{

    public ZahtevExtractMetadata() {
        super(AuthenticationUtilities.setUpPropertiesFusekiJena());
    }

    public void extractData(Zahtev zahtev) {
        Model model = createModel();

        Resource resource = model.createResource(ZAHTEV_NAMESPACE_PATH + zahtev.getXmlId());

        Property createdWhen = model.createProperty(PREDICATE_NAMESPACE, "createdWhen");
        Literal date = model.createLiteral(zahtev.getDatum().toString());
        model.add(model.createStatement(resource, createdWhen, date));

        Property createdIn = model.createProperty(PREDICATE_NAMESPACE, "createdIn");
        Literal place = model.createLiteral(zahtev.getMesto());
        model.add(model.createStatement(resource, createdIn, place));

        Property hasStatus = model.createProperty(PREDICATE_NAMESPACE, "hasStatus");
        Literal status = model.createLiteral("pending");
        model.add(model.createStatement(resource, hasStatus, status));  //dodat status zahteva pending

        // OVO PAZITI AKO JE JMBG NULL, PROVJERITI TIP ID-a OSOBE
        Property createdBy = model.createProperty(PREDICATE_NAMESPACE, "createdBy");
        Resource podnosilac = model.createResource(OSOBA_NAMESPACE_PATH + getPersonIdentifier(zahtev.getPodnosilac().getDrzavljanstvo()));
        model.add(model.createStatement(resource, createdBy, podnosilac));

        super.modelWrite(model, ZAHTEV_NAMED_GRAPH_URI);
    }

    private String getPersonIdentifier(Drzavljanstvo drzavljanstvo) {

        if(drzavljanstvo.getTip().equals("DOMACE")){
            return drzavljanstvo.getJMBG();
        }
        else if (drzavljanstvo.getTip().equals("STRANO_SA_BORAVKOM")){
            return drzavljanstvo.getEvidencioniBrojStranca();
        }
        else // STRANO_BEZ_BORAVKA
            return drzavljanstvo.getBrPasosa();
    }
}
