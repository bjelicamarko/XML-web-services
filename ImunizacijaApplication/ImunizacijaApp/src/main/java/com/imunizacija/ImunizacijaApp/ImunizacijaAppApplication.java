package com.imunizacija.ImunizacijaApp;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.InteresovanjeExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.PotvrdaExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.SaglasnostExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.StoreRetrieveXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@SpringBootApplication
public class ImunizacijaAppApplication {

	public static void main(String[] args) {
		GenericXMLReaderWriter<Zahtev> zahtevXMLReaderWriter = new GenericXMLReaderWriter<>(PACKAGE_PATH_ZAHTEV_DZS, XML_SCHEMA_PATH_ZAHTEV);
		Zahtev zahtevFromXml = zahtevXMLReaderWriter.readFromXml("data/xml_example/zahtev_za_izdavanje_zelenog.xml");

		GenericXMLReaderWriter<Interesovanje> interesovanjeXMLReaderWriter = new GenericXMLReaderWriter<>(PACKAGE_PATH_INTERESOVANJE, XML_SCHEMA_PATH_INTERESOVANJE);
		Interesovanje interesovanjeFromXml = interesovanjeXMLReaderWriter.readFromXml("data/xml_example/interesovanje.xml");

		GenericXMLReaderWriter<Saglasnost> saglasnostXMLReaderWriter = new GenericXMLReaderWriter<>(PACKAGE_PATH_SAGLASNOST, XML_SCHEMA_PATH_SAGLASNOST);
		Saglasnost saglasnostFromXml = saglasnostXMLReaderWriter.readFromXml("data/xml_example/saglasnost_za_imunizaciju.xml");

		GenericXMLReaderWriter<PotvrdaOVakcinaciji> potvrdaXMLReaderWriter = new GenericXMLReaderWriter<>(PACKAGE_PATH_POTVRDA, XML_SCHEMA_PATH_POTVRDA);
		PotvrdaOVakcinaciji potvrdaOVakcinacijiFromXml = potvrdaXMLReaderWriter.readFromXml("data/xml_example/potvrda_o_vakcinaciji.xml");

		zahtevXMLReaderWriter.writeToXml(zahtevFromXml, XML_WRITE_BASE_PATH);
		interesovanjeXMLReaderWriter.writeToXml(interesovanjeFromXml, XML_WRITE_BASE_PATH);
		saglasnostXMLReaderWriter.writeToXml(saglasnostFromXml, XML_WRITE_BASE_PATH);
		potvrdaXMLReaderWriter.writeToXml(potvrdaOVakcinacijiFromXml, XML_WRITE_BASE_PATH);

		StoreRetrieveXMLRepository.registerDatabase();
		IdGeneratorPosInt idGeneratorPosInt = new IdGeneratorPosInt();

		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();

		// EKSTRAKCIJA POTVRDA
//		PotvrdaExtractMetadata potvrdaExtractMetadata = new PotvrdaExtractMetadata(conn);
//		GenericXMLRepository<PotvrdaOVakcinaciji> potvrdaOVakcinacijiGenericXMLRepository =
//				new GenericXMLRepository<PotvrdaOVakcinaciji>(PACKAGE_PATH_POTVRDA,
//						COLLECTION_PATH_POTVRDA, idGeneratorPosInt);
//		PotvrdaOVakcinaciji potvrda = potvrdaOVakcinacijiGenericXMLRepository.retrieveXML("532235532.xml");
//
//		potvrdaExtractMetadata.extract(potvrda);

		// EKSTRAKCIJA ZAHTJEV
//		ZahtevExtractMetadata zahtevExtractMetadata = new ZahtevExtractMetadata(conn);
//
//		GenericXMLRepository<Zahtev> zahtevGenericXMLRepository =
//				new GenericXMLRepository<Zahtev>(PACKAGE_PATH_ZAHTEV_DZS,
//						COLLECTION_PATH_ZAHTEV_DZS, idGeneratorPosInt);
//		Zahtev zahtev = zahtevGenericXMLRepository.retrieveXML("978989686.xml");
//
//		zahtevExtractMetadata.extractData(zahtev);

		// EKSTRAKCIJA INTERESOVANJE
		InteresovanjeExtractMetadata interesovanjeExtractMetadata = new InteresovanjeExtractMetadata(conn);

		GenericXMLRepository<Interesovanje> interesovanjeRepository =
				new GenericXMLRepository<Interesovanje>(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE,
						idGeneratorPosInt);

		Interesovanje i = interesovanjeRepository.retrieveXML("2312312.xml");
		interesovanjeExtractMetadata.extract(i);
//
//		// EKSTRAKCIJA SAGLASNOST
		SaglasnostExtractMetadata saglasnostExtractMetadata = new SaglasnostExtractMetadata(conn);

		GenericXMLRepository<Saglasnost> saglasnostGenericXMLRepository =
				new GenericXMLRepository<>(PACKAGE_PATH_SAGLASNOST, COLLECTION_PATH_SAGLASNOST,
						idGeneratorPosInt);

		Saglasnost saglasnost = saglasnostGenericXMLRepository.retrieveXML("4125125.xml");
		saglasnostExtractMetadata.extract(saglasnost);

		saglasnost = saglasnostGenericXMLRepository.retrieveXML("7654321.xml");
		saglasnostExtractMetadata.extract(saglasnost);

		// EKSTRAKCIJA POTVRDA
		PotvrdaExtractMetadata potvrdaExtractMetadata = new PotvrdaExtractMetadata(conn);

		GenericXMLRepository<PotvrdaOVakcinaciji> potvrdaOVakcinacijiGenericXMLRepository =
				new GenericXMLRepository<>(PACKAGE_PATH_POTVRDA, COLLECTION_PATH_POTVRDA,
						idGeneratorPosInt);

		PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiGenericXMLRepository.retrieveXML("1245125.xml");
		potvrdaExtractMetadata.extract(potvrdaOVakcinaciji);

		// EKSTRAKCIJA ZAHTEV
		ZahtevExtractMetadata zahtevExtractMetadata = new ZahtevExtractMetadata(conn);

		GenericXMLRepository<Zahtev> zahtevGenericXMLRepository =
				new GenericXMLRepository<Zahtev>(PACKAGE_PATH_ZAHTEV_DZS,
						COLLECTION_PATH_ZAHTEV_DZS, idGeneratorPosInt);
		Zahtev zahtev = zahtevGenericXMLRepository.retrieveXML("978989686.xml");

		zahtevExtractMetadata.extractData(zahtev);


//		// INTERESOVANJE REPO
//		GenericXMLRepository<Interesovanje> interesovanjeRepository =
//				new GenericXMLRepository<Interesovanje>(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE,
//						idGeneratorPosInt);
//		// CITANJE
//		Interesovanje i = interesovanjeRepository.retrieveXML("2312312.xml");
//		// EDITOVANJE - KREIRANJE NOVOG
//		i.setIme("Nikola");
//		// PISANJE
//		interesovanjeRepository.storeXML(i, true);
//
//		// POTVRDA REPO
//		GenericXMLRepository<PotvrdaOVakcinaciji> potvrdaOVakcinacijiGenericXMLRepository =
//				new GenericXMLRepository<PotvrdaOVakcinaciji>(PACKAGE_PATH_POTVRDA,
//						COLLECTION_PATH_POTVRDA, idGeneratorPosInt);
//		// CITANJE
//		PotvrdaOVakcinaciji pov = potvrdaOVakcinacijiGenericXMLRepository.retrieveXML("532235532.xml");
//		// EDITOVANJE - KREIRANJE NOVOG
//		PotvrdaOVakcinaciji.PodaciOPotvrdi podaciOPotvrdi = pov.getPodaciOPotvrdi();
//		podaciOPotvrdi.setQRCode("TEST");
//		pov.setPodaciOPotvrdi(podaciOPotvrdi);
//		// PISANJE
//		potvrdaOVakcinacijiGenericXMLRepository.storeXML(pov, true);


//		// SAGLASNOST REPO
//		GenericXMLRepository<Saglasnost> saglasnostGenericXMLRepository =
//				new GenericXMLRepository<Saglasnost>(PACKAGE_PATH_SAGLASNOST,
//						COLLECTION_PATH_SAGLASNOST, idGeneratorPosInt);
//		// CITANJE
//		Saglasnost saglasnost = saglasnostGenericXMLRepository.retrieveXML("4125125.xml");
//		// EDITOVANJE - KREIRANJE NOVOG
//		Drzavljanstvo novoDrzavljanstvo = saglasnost.getDrzavljanstvo();
//		novoDrzavljanstvo.setJMBG("0201999404404");
//		saglasnost.setDrzavljanstvo(novoDrzavljanstvo);
//		// PISANJE
//		saglasnostGenericXMLRepository.storeXML(saglasnost, false);

//		// SAGLASNOST REPO
//		GenericXMLRepository<Zahtev> zahtevGenericXMLRepository =
//				new GenericXMLRepository<Zahtev>(PACKAGE_PATH_ZAHTEV_DZS,
//						COLLECTION_PATH_ZAHTEV_DZS, idGeneratorPosInt);
//		// CITANJE
//		Zahtev zahtev = zahtevGenericXMLRepository.retrieveXML("978989686.xml");
//		// EDITOVANJE - KREIRANJE NOVOG
//		String razlog = "Da nemam";
//		zahtev.setRazlog(razlog);
//		// PISANJE
//		zahtevGenericXMLRepository.storeXML(zahtev, false);

		/** FUSEKI SELECT PRIMERI NA DOLE **/
//		// Issuing a simple SPARQL query to make sure the changes were made...
//		System.out.println("[INFO] Making sure the changes were made in the named graph \"" + ZAHTEV_NAMED_GRAPH_URI + "\".");
//		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + ZAHTEV_NAMED_GRAPH_URI, "?s ?p ?o");
//
//		// Create a QueryExecution that will access a SPARQL service over HTTP
//		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
//
//		// Query the collection, dump output response with the use of ResultSetFormatter
//		ResultSet results = query.execSelect();
//		ResultSetFormatter.out(System.out, results);
//
//		query.close();
//

		// OVO NE BRISATI
//      Issuing a simple SPARQL query to make sure the changes were made...
//		System.out.println("[INFO] Making sure the changes were made in the named graph \"" + ZAHTEV_NAMED_GRAPH_URI + "\".");
//		String sparqlQuery = "SELECT *\n" +
//				"FROM <http://localhost:8083/ZahtevDataset/data/zahtev/metadata>\n" +
//				"FROM NAMED <http://localhost:8083/ZahtevDataset/data/person/metadata>\n" +
//				"WHERE {\n" +
//				"?zahtev <http://www.vakc-sistem.rs/predicate/createdBy> ?person .\n" +
//				"GRAPH <http://localhost:8083/ZahtevDataset/data/person/metadata> " +
//				"{ ?person <http://www.vakc-sistem.rs/predicate/identifiedWith> ?id} .\n" +
//				"}\n";
//
//
//		// Create a QueryExecution that will access a SPARQL service over HTTP
//		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
//
//		// Query the collection, dump output response with the use of ResultSetFormatter
//		ResultSet results = query.execSelect();
//		ResultSetFormatter.out(System.out, results);
//
//		query.close();
//
		SpringApplication.run(ImunizacijaAppApplication.class, args);
	}

}