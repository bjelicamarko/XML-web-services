package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

public class InteresovanjeExtractMetadata extends ExtractMetadata{

    public InteresovanjeExtractMetadata(AuthenticationUtilities.ConnectionPropertiesFusekiJena conn) {
        super(conn);
    }

    public void extractMetadata(Interesovanje interesovanje) {
        Model model = createModel();

        Resource resource = model.createResource(INTERESOVANJE_NAMESPACE_PATH + interesovanje.getXmlId());

        Property createdWhen = model.createProperty(PREDICATE_NAMESPACE, "createdWhen");
        Literal date = model.createLiteral(interesovanje.getDatum().toString());
        model.add(model.createStatement(resource, createdWhen, date));

        //Korisnik podneo interesovanje -> dovrsi
        String personIdentifier = returnPersonIdentifier(interesovanje);
        Resource personResource = model.createResource(OSOBA_NAMESPACE_PATH + personIdentifier);
    }

    private String returnPersonIdentifier(Interesovanje interesovanje) {
        String jmbg = interesovanje.getDrzavljanstvo().getJMBG();
        if(jmbg != null) return jmbg;

        String brojPasosa = interesovanje.getDrzavljanstvo().getBrPasosa();
        if(brojPasosa != null) return brojPasosa;

        return interesovanje.getDrzavljanstvo().getEvidencioniBrojStranca();
    }

}
