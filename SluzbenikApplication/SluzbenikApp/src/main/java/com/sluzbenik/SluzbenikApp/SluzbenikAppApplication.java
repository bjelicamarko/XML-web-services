package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.repository.xmlRepository.StoreRetrieveXMLRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SluzbenikAppApplication {

	public static void main(String[] args) {
		StoreRetrieveXMLRepository.registerDatabase();
//		IdGeneratorDZS idGeneratorDZS = new IdGeneratorDZS();
//		IdGeneratorPosInt idGeneratorPosInt = new IdGeneratorPosInt();
//
//		// DZS REPO
//		GenericXMLRepository<DigitalniZeleniSertifikat> digitalniZeleniSertifikatRepository =
//				new GenericXMLRepository<DigitalniZeleniSertifikat>(PACKAGE_PATH_DZS, COLLECTION_PATH_DZS,idGeneratorDZS);
//		// CITANJE
//		DigitalniZeleniSertifikat dzs = digitalniZeleniSertifikatRepository.retrieveXML("1234567.xml");
//		// EDITOVANJE - KREIRANJE NOVOG
//		DigitalniZeleniSertifikat.PodaciOSertifikatu podaci = dzs.getPodaciOSertifikatu();
//		podaci.setQrKod("idegasssssssssssssssssssssssssssssss");
//		podaci.setBrojSertifikata("7654321");
//		dzs.setPodaciOSertifikatu(podaci);
//		// PISANJE
//		digitalniZeleniSertifikatRepository.storeXML(dzs, true);
//
//
//		// IZVESTAJ REPO
//		GenericXMLRepository<Izvestaj> izvestajRepository =
//				new GenericXMLRepository<Izvestaj>(PACKAGE_PATH_IZVESTAJ, COLLECTION_PATH_IZVESTAJ,idGeneratorPosInt);
//		// CITANJE
//		Izvestaj izvestaj = izvestajRepository.retrieveXML("123123.xml");
//		// EDITOVANJE - KREIRANJE NOVOG
//		izvestaj.setBrojPrimljenihVakcina(30);
//		// PISANJE
//		izvestajRepository.storeXML(izvestaj, true);




		SpringApplication.run(SluzbenikAppApplication.class, args);
	}
}
