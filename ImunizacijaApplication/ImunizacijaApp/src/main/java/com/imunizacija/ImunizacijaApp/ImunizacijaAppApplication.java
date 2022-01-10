package com.imunizacija.ImunizacijaApp;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.StoreRetrieveXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.ByteArrayOutputStream;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@SpringBootApplication
public class ImunizacijaAppApplication {

	public static void main(String[] args) {
		StoreRetrieveXMLRepository.registerDatabase();
		IdGeneratorPosInt idGeneratorPosInt = new IdGeneratorPosInt();

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

/** FUSEKI PRIMER NA DOLE **/
		GenericXMLRepository<Zahtev> zahtevGenericXMLRepository =
				new GenericXMLRepository<Zahtev>(PACKAGE_PATH_ZAHTEV_DZS,
						COLLECTION_PATH_ZAHTEV_DZS, idGeneratorPosInt);
		// CITANJE
		Zahtev zahtev = zahtevGenericXMLRepository.retrieveXML("978989686.xml");
		String PREDICATE_NAMESPACE = "http://www.vakc-sistem.rs/predicate/";
		String ZAHTEV_NAMED_GRAPH_URI = "/zahtev/metadata";
		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();


		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", PREDICATE_NAMESPACE);

		Resource resource = model.createResource("http://www.vakc-sistem.rs/zahtev-dzs/" + zahtev.getId());

		Property createdAt = model.createProperty(PREDICATE_NAMESPACE, "createdAt");
		Literal date = model.createLiteral(zahtev.getDatum().toString());

		Property createdIn = model.createProperty(PREDICATE_NAMESPACE, "createdIn");
		Literal place = model.createLiteral(zahtev.getMesto());

		Property createdBy = model.createProperty(PREDICATE_NAMESPACE, "createdBy");
		Resource objectResource = model.createResource("http://www.vakc-sistem.rs/person/" + zahtev.getPodnosilac().getIme()
		+ "_" + zahtev.getPodnosilac().getPrezime());


		// Adding the statements to the model
		Statement statement1 = model.createStatement(resource, createdAt, date);
		Statement statement2 = model.createStatement(resource, createdIn, place);
		Statement statement3 = model.createStatement(resource, createdBy, objectResource);

		model.add(statement1);
		model.add(statement2);
		model.add(statement3);

		model.write(System.out, SparqlUtil.NTRIPLES);

		// Issuing the SPARQL update...
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);

		// Updating the named graph with the triples from RDF model
		System.out.println("[INFO] Inserting the triples to a named graph \"" + ZAHTEV_NAMED_GRAPH_URI + "\".");
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + ZAHTEV_NAMED_GRAPH_URI, out.toString());
		System.out.println(sparqlUpdate);

		HttpClient hc = authHttpClient("admin", "pw123");

		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		// UpdateProcessor sends update request to a remote SPARQL update service.
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint, hc);
		processor.execute();

		personMetadata(idGeneratorPosInt);

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

//      Issuing a simple SPARQL query to make sure the changes were made...
		System.out.println("[INFO] Making sure the changes were made in the named graph \"" + ZAHTEV_NAMED_GRAPH_URI + "\".");
		String sparqlQuery = "SELECT *\n" +
				"FROM <http://localhost:8083/ZahtevDataset/data/zahtev/metadata>\n" +
				"FROM NAMED <http://localhost:8083/ZahtevDataset/data/person/metadata>\n" +
				"WHERE {\n" +
				"?zahtev <http://www.vakc-sistem.rs/predicate/createdBy> ?person .\n" +
				"GRAPH <http://localhost:8083/ZahtevDataset/data/person/metadata> " +
				"{ ?person <http://www.vakc-sistem.rs/predicate/identifiedWith> ?id} .\n" +
				"}\n";


		// Create a QueryExecution that will access a SPARQL service over HTTP
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the collection, dump output response with the use of ResultSetFormatter
		ResultSet results = query.execSelect();
		ResultSetFormatter.out(System.out, results);

		query.close();

		SpringApplication.run(ImunizacijaAppApplication.class, args);
	}

	private static void personMetadata(IdGeneratorPosInt idGeneratorPosInt) {
		GenericXMLRepository<Zahtev> zahtevGenericXMLRepository =
				new GenericXMLRepository<Zahtev>(PACKAGE_PATH_ZAHTEV_DZS,
						COLLECTION_PATH_ZAHTEV_DZS, idGeneratorPosInt);
		// CITANJE
		Zahtev zahtev = zahtevGenericXMLRepository.retrieveXML("978989686.xml");
		String PREDICATE_NAMESPACE = "http://www.vakc-sistem.rs/predicate/";
		String ZAHTEV_NAMED_GRAPH_URI = "/person/metadata";
		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();


		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", PREDICATE_NAMESPACE);

		Resource objectResource = model.createResource("http://www.vakc-sistem.rs/person/" + zahtev.getPodnosilac().getIme()
				+ "_" + zahtev.getPodnosilac().getPrezime());

		Property identifiedWith = model.createProperty(PREDICATE_NAMESPACE, "identifiedWith");
		Literal identifier = model.createLiteral(zahtev.getPodnosilac().getJMBG());

		// Adding the statements to the model
		Statement statement4 = model.createStatement(objectResource, identifiedWith, identifier);

		model.add(statement4);

		model.write(System.out, SparqlUtil.NTRIPLES);

		// Issuing the SPARQL update...
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);

		// Updating the named graph with the triples from RDF model
		System.out.println("[INFO] Inserting the triples to a named graph \"" + ZAHTEV_NAMED_GRAPH_URI + "\".");
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + ZAHTEV_NAMED_GRAPH_URI, out.toString());
		System.out.println(sparqlUpdate);

		HttpClient hc = authHttpClient("admin", "pw123");

		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		// UpdateProcessor sends update request to a remote SPARQL update service.
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint, hc);
		processor.execute();


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
	}

	private static HttpClient authHttpClient(String user, String password) {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		Credentials credentials = new UsernamePasswordCredentials(user, password);
		credsProvider.setCredentials(AuthScope.ANY, credentials);
		return HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider)
				.build();
	}

}