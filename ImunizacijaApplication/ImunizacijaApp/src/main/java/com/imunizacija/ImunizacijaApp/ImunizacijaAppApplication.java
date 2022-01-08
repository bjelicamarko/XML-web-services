package com.imunizacija.ImunizacijaApp;

import com.imunizacija.ImunizacijaApp.repository.StoreRetrieveXMLRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImunizacijaAppApplication {

	public static void main(String[] args) {
		StoreRetrieveXMLRepository.registerDatabase();
		//todo implementirati identifiableentity u svaki entitet
		//	implementirati get/set, pogledati primer sa drugog backend-a
		//	testirati sve
		//	gg ez

		SpringApplication.run(ImunizacijaAppApplication.class, args);
	}

}
