package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.repository.rdfRepository.DzsExtractMetadata;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.StoreRetrieveXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

@SpringBootApplication
public class SluzbenikAppApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
//		GenericXMLReaderWriter<DigitalniZeleniSertifikat> digitalniZeleniSertifikatGenericXMLReaderWriter
//				= new GenericXMLReaderWriter<>(PACKAGE_PATH_DZS, XML_SCHEMA_PATH_DZS);
//		DigitalniZeleniSertifikat digitalni = digitalniZeleniSertifikatGenericXMLReaderWriter.readFromXml("data/xml_example/digitalni_zeleni_sertifikat.xml");
//
//		GenericXMLReaderWriter<Izvestaj> izvestajGenericXMLReaderWriter = new GenericXMLReaderWriter<>(PACKAGE_PATH_IZVESTAJ, XML_SCHEMA_PATH_IZVESTAJ);
//		Izvestaj izvestaj = izvestajGenericXMLReaderWriter.readFromXml("data/xml_example/izvestaj.xml");
//
//		digitalniZeleniSertifikatGenericXMLReaderWriter.writeToXml(digitalni, XML_WRITE_BASE_PATH);
//		izvestajGenericXMLReaderWriter.writeToXml(izvestaj, XML_WRITE_BASE_PATH);
//		StoreRetrieveXMLRepository.registerDatabase();
//		IdGeneratorDZS idGeneratorDZS = new IdGeneratorDZS();
//
//		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
//		DzsExtractMetadata dzsExtractMetadata = new DzsExtractMetadata(conn);
//
		StoreRetrieveXMLRepository.registerDatabase();

//		IdGeneratorPosInt idGeneratorPosInt = new IdGeneratorPosInt();
//
//		AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
//		GenericXMLRepository<DigitalniZeleniSertifikat> dzsGenericXMLRepository =
//				new GenericXMLRepository<>();
//		dzsGenericXMLRepository.setRepositoryParams(PACKAGE_PATH_DZS, COLLECTION_PATH_DZS, idGeneratorPosInt);
//		DigitalniZeleniSertifikat dzs = dzsGenericXMLRepository.retrieveXML("1234567.xml");
//
//		DzsExtractMetadata dzsExtractMetadata = new DzsExtractMetadata(conn);
//		dzsExtractMetadata.extract(dzs, "1234567.xml");

		SpringApplication.run(SluzbenikAppApplication.class, args);
	}
}
