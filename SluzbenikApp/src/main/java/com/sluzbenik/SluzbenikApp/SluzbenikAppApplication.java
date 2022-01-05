package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.repository.DigitalniZeleniSertifikatRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SluzbenikAppApplication {

	public static void main(String[] args) {
//		// Citanje
//		DigitalniZeleniSertifikatRepository digitalniZeleniSertifikatRepository = new DigitalniZeleniSertifikatRepository();
//		DigitalniZeleniSertifikat dzs = digitalniZeleniSertifikatRepository.retrieveXML("7654321");
////		System.out.println(dzs);
//		// Pisanje
//		DigitalniZeleniSertifikat.PodaciOSertifikatu podaci = dzs.getPodaciOSertifikatu();
//		podaci.setQrKod("idegasssssssssssssssssssssssssssssss");
//		podaci.setBrojSertifikata("7654321");
//		dzs.setPodaciOSertifikatu(podaci);
//		digitalniZeleniSertifikatRepository.storeXML(dzs);
		SpringApplication.run(SluzbenikAppApplication.class, args);
	}
}
