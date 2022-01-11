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

    public void extract(Interesovanje interesovanje) {
        Model model = createModel();

        Resource resource = model.createResource(INTERESOVANJE_NAMESPACE_PATH + interesovanje.getXmlId());

        Property createdWhen = model.createProperty(PREDICATE_NAMESPACE, "createdWhen");
        Literal date = model.createLiteral(interesovanje.getDatum().toString());
        model.add(model.createStatement(resource, createdWhen, date));

        super.modelWrite(model, INTERESOVANJE_NAMED_GRAPH_URI);

        extractForPerson(interesovanje, resource);
    }

    private void extractForPerson(Interesovanje interesovanje, Resource interesovanjeResource) {
        Model model = createModel();

        String personIdentifier = returnPersonIdentifier(interesovanje);
        Resource personResource = model.createResource(OSOBA_NAMESPACE_PATH + personIdentifier);
        Property issued = model.createProperty(PREDICATE_NAMESPACE, "issued");
        model.add(model.createStatement(personResource, issued, interesovanjeResource));

        Property nameIs = model.createProperty(PREDICATE_NAMESPACE, "nameIs");
        Literal name = model.createLiteral(interesovanje.getIme());
        model.add(model.createStatement(personResource, nameIs, name));

        Property lastnameIs = model.createProperty(PREDICATE_NAMESPACE, "lastnameIs");
        Literal lastname = model.createLiteral(interesovanje.getPrezime());
        model.add(model.createStatement(personResource, lastnameIs, lastname));

        Property hasEmail = model.createProperty(PREDICATE_NAMESPACE, "hasEmail");
        Literal email = model.createLiteral(interesovanje.getKontakt().getEmailAdresa());
        model.add(model.createStatement(personResource, hasEmail, email));

        super.modelWrite(model, OSOBA_NAMED_GRAPH_URI);
    }

    private String returnPersonIdentifier(Interesovanje interesovanje) {
        /** Handle exception if all null **/

        String jmbg = interesovanje.getDrzavljanstvo().getJMBG();
        if(jmbg != null) return jmbg;

        String brojPasosa = interesovanje.getDrzavljanstvo().getBrPasosa();
        if(brojPasosa != null) return brojPasosa;

        return interesovanje.getDrzavljanstvo().getEvidencioniBrojStranca();
    }

}
