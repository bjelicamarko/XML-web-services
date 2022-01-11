package com.imunizacija.ImunizacijaApp.repository.rdfRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

public class SaglasnostExtractMetadata extends ExtractMetadata{

    public SaglasnostExtractMetadata(AuthenticationUtilities.ConnectionPropertiesFusekiJena conn) {
        super(conn);
    }

    public void extract(Saglasnost saglasnost) {
        Model model = createModel();

        Resource resource = model.createResource(SAGLANOST_NAMESPACE_PATH + saglasnost.getXmlId());

        Property createdAt = model.createProperty(PREDICATE_NAMESPACE, "createdAt");
        Literal date = model.createLiteral(saglasnost.getDatum().toString());
        model.add(model.createStatement(resource, createdAt, date));

        Property issuedTo = model.createProperty(PREDICATE_NAMESPACE, "issuedTo");
        String idOsobe = "";
        switch (saglasnost.getDrzavljanstvo().getTip()){
            case "JMBG":
                idOsobe = saglasnost.getDrzavljanstvo().getJMBG();
                break;
            case "Br_pasosa":
                idOsobe = saglasnost.getDrzavljanstvo().getBrPasosa();
                break;
            case "Evidencioni_broj_stranca":
                idOsobe = saglasnost.getDrzavljanstvo().getEvidencioniBrojStranca();
                break;
            default:
                System.out.println("ERROR! Ivalid type of 'Drzavljanstvo' for 'Saglasnost'!");
                return;
        }
        Resource person = model.createResource(OSOBA_NAMESPACE_PATH + idOsobe);
        model.add(model.createStatement(resource, issuedTo, person));

        super.modelWrite(model, SAGLANOST_NAMED_GRAPH_URI);
    }
}
