package com.imunizacija.ImunizacijaApp;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.InteresovanjeExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.PotvrdaExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.SaglasnostExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.StoreRetrieveXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@SpringBootApplication
@EnableScheduling
public class ImunizacijaAppApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	private static void fillRDFDatabase(){
		/* PRE POKRETANJA UBACITI
		 *     978989686.xml U ZAHTEV
		 *     2312312.xml U INTERESOVANJE
		 *     7654321.xml U ZAGLASNOST
		 *     1245125.xml U POTVRDU
		 *  */
		IdGeneratorPosInt idGeneratorPosInt = new IdGeneratorPosInt();

		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();

		// EKSTRAKCIJA ZAHTJEV
		ZahtevExtractMetadata zahtevExtractMetadata = new ZahtevExtractMetadata(conn);

		GenericXMLRepository<Zahtev> zahtevGenericXMLRepository =
				new GenericXMLRepository<Zahtev>();
		zahtevGenericXMLRepository.setRepositoryParams(PACKAGE_PATH_ZAHTEV_DZS,
				COLLECTION_PATH_ZAHTEV_DZS, idGeneratorPosInt);
		Zahtev zahtev = zahtevGenericXMLRepository.retrieveXML("978989686.xml");

		zahtevExtractMetadata.extractData(zahtev);

		// EKSTRAKCIJA INTERESOVANJE
		InteresovanjeExtractMetadata interesovanjeExtractMetadata = new InteresovanjeExtractMetadata(conn);

		GenericXMLRepository<Interesovanje> interesovanjeRepository =
				new GenericXMLRepository<Interesovanje>();
		interesovanjeRepository.setRepositoryParams(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE,
				idGeneratorPosInt);

		Interesovanje i = interesovanjeRepository.retrieveXML("2312312.xml");
		interesovanjeExtractMetadata.extract(i);

		// EKSTRAKCIJA SAGLASNOST
		SaglasnostExtractMetadata saglasnostExtractMetadata = new SaglasnostExtractMetadata(conn);

		GenericXMLRepository<Saglasnost> saglasnostGenericXMLRepository =
				new GenericXMLRepository<>();
		saglasnostGenericXMLRepository.setRepositoryParams(PACKAGE_PATH_SAGLASNOST, COLLECTION_PATH_SAGLASNOST,
				idGeneratorPosInt);


		Saglasnost saglasnost = saglasnostGenericXMLRepository.retrieveXML("7654321.xml");
		saglasnostExtractMetadata.extract(saglasnost);

		// EKSTRAKCIJA POTVRDA
		PotvrdaExtractMetadata potvrdaExtractMetadata = new PotvrdaExtractMetadata(conn);

		GenericXMLRepository<PotvrdaOVakcinaciji> potvrdaOVakcinacijiGenericXMLRepository =
				new GenericXMLRepository<>();
		potvrdaOVakcinacijiGenericXMLRepository.setRepositoryParams(PACKAGE_PATH_POTVRDA, COLLECTION_PATH_POTVRDA,
				idGeneratorPosInt);

		PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiGenericXMLRepository.retrieveXML("1245125.xml");
		potvrdaExtractMetadata.extract(potvrdaOVakcinaciji);
	}

	private void queryExample(){
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

		//fixovan ovaj upit
//		SELECT *
//				FROM <http://localhost:8083/ImunizacijaDataset/data/zahtev/metadata>
//		FROM NAMED <http://localhost:8083/ImunizacijaDataset/data/osoba/metadata>
//		WHERE {
//			?zahtev <http://www.vakc-sistem.rs/predicate/createdBy> ?person .
//			GRAPH <http://localhost:8083/ImunizacijaDataset/data/osoba/metadata>
//			{ ?person <http://www.vakc-sistem.rs/predicate/hasEmail> ?email} .
//			}

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
//		GenericXMLRepository<Interesovanje> interesovanjeRepository =
//				new GenericXMLRepository<Interesovanje>(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE,
//						new IdGeneratorPosInt());
//		// CITANJE
//		Interesovanje i = interesovanjeRepository.retrieveXML("2312312.xml");
	}

	public static void main(String[] args) {
		StoreRetrieveXMLRepository.registerDatabase();
		//fillRDFDatabase();
		SpringApplication.run(ImunizacijaAppApplication.class, args);
	}

}