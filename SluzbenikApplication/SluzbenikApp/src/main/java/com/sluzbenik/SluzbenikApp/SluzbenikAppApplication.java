package com.sluzbenik.SluzbenikApp;

import com.sluzbenik.SluzbenikApp.repository.xmlRepository.StoreRetrieveXMLRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
//		GenericXMLRepository<DigitalniZeleniSertifikat> dzsGenericXMLRepository =
//				new GenericXMLRepository<>(PACKAGE_PATH_DZS,
//						COLLECTION_PATH_DZS, idGeneratorDZS);
//		DigitalniZeleniSertifikat dzs = dzsGenericXMLRepository.retrieveXML("1234567.xml");
//
//		dzsExtractMetadata.extract(dzs);

		StoreRetrieveXMLRepository.registerDatabase();
		SpringApplication.run(SluzbenikAppApplication.class, args);
	}
}
