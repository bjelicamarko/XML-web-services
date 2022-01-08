package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;
import com.sluzbenik.SluzbenikApp.repository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.StoreRetrieveXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.id_generator.IdGeneratorDZS;
import com.sluzbenik.SluzbenikApp.repository.id_generator.IdGeneratorPosInt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

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
