package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.repository.rdfRepository.DzsExtractMetadata;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.StoreRetrieveXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorDZS;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

@SpringBootApplication
public class SluzbenikAppApplication {

	public static void main(String[] args) {
//		StoreRetrieveXMLRepository.registerDatabase();
//		IdGeneratorDZS idGeneratorDZS = new IdGeneratorDZS();
//
//		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
//		DzsExtractMetadata dzsExtractMetadata = new DzsExtractMetadata(conn);
//
//		GenericXMLRepository<DigitalniZeleniSertifikat> dzsGenericXMLRepository =
//				new GenericXMLRepository<>(PACKAGE_PATH_DZS,
//						COLLECTION_PATH_DZS, idGeneratorDZS);
//		DigitalniZeleniSertifikat dzs = dzsGenericXMLRepository.retrieveXML("1234567.xml");
//
//		dzsExtractMetadata.extract(dzs, "0102998543435");

		SpringApplication.run(SluzbenikAppApplication.class, args);
	}
}
