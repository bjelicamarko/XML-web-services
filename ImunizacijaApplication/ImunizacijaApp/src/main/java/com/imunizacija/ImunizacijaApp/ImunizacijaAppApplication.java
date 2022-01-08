package com.imunizacija.ImunizacijaApp;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.repository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.StoreRetrieveXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.id_generator.IdGeneratorPosInt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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


		SpringApplication.run(ImunizacijaAppApplication.class, args);
	}

}
