package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;
import com.sluzbenik.SluzbenikApp.repository.DigitalniZeleniSertifikatRepository;
import com.sluzbenik.SluzbenikApp.repository.IzvestajRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;

@SpringBootApplication
public class SluzbenikAppApplication {

	public static void main(String[] args) {
//		// Citanje
//		DigitalniZeleniSertifikatRepository digitalniZeleniSertifikatRepository = new DigitalniZeleniSertifikatRepository();
//		DigitalniZeleniSertifikat dzs = digitalniZeleniSertifikatRepository.retrieveXML("1234567.xml");
////		System.out.println(dzs);
//		// Pisanje
//		DigitalniZeleniSertifikat.PodaciOSertifikatu podaci = dzs.getPodaciOSertifikatu();
//		podaci.setQrKod("idegasssssssssssssssssssssssssssssss");
//		podaci.setBrojSertifikata("7654321");
//		dzs.setPodaciOSertifikatu(podaci);
//		digitalniZeleniSertifikatRepository.storeXML(dzs);

//		//		// Citanje
//		IzvestajRepository izvestajRepository = new IzvestajRepository();
//		Izvestaj izvestaj = izvestajRepository.retrieveXML("123123.xml");
////		System.out.println(dzs);
//		// Pisanje
//		izvestaj.setBrojPrimljenihVakcina(30);
//		izvestaj.setId(new BigInteger("321312"));
//		izvestajRepository.storeXML(izvestaj);
		SpringApplication.run(SluzbenikAppApplication.class, args);
	}
}
