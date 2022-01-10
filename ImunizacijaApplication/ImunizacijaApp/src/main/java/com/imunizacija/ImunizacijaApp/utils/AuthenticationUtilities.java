package com.imunizacija.ImunizacijaApp.utils;

import java.util.Properties;

/**
 * Utilities to support and simplify examples.
 */
public class AuthenticationUtilities {
	/**
	 * Connection parameters.
	 */
	static public class ConnectionPropertiesExist {

		public String host;
		public int port = -1;
		public String user;
		public String password;
		public String driver;
		public String uri;

		public ConnectionPropertiesExist(Properties props) {
			super();
			
			user = props.getProperty("conn.user").trim();
			password = props.getProperty("conn.password").trim();

			host = props.getProperty("conn.host").trim();
			port = Integer.parseInt(props.getProperty("conn.port"));

			String connectionUri = "xmldb:exist://%1$s:%2$s/exist/xmlrpc";
			uri = String.format(connectionUri, host, port);
			
			driver = props.getProperty("conn.driver").trim();
		}
	}

	/**
	 * Read the configuration properties for the example.
	 * 
	 * @return the configuration object
	 */
	public static ConnectionPropertiesExist setUpProperties() {
		Properties props = new Properties();
		props.setProperty("conn.user", "admin");
		props.setProperty("conn.password", "");
		props.setProperty("conn.host", "localhost");
		props.setProperty("conn.port", "8082");
		props.setProperty("conn.driver", "org.exist.xmldb.DatabaseImpl");
		return new ConnectionPropertiesExist(props);
	}

	/**
	 * Connection parameters.
	 */
	static public class ConnectionPropertiesFusekiJena {

		public String endpoint;
		public String dataset;

		public String queryEndpoint;
		public String updateEndpoint;
		public String dataEndpoint;

		public ConnectionPropertiesFusekiJena(Properties props) {
			super();

			dataset = props.getProperty("conn.dataset").trim();
			endpoint = props.getProperty("conn.endpoint").trim();

			queryEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.query").trim());
			updateEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.update").trim());
			dataEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.data").trim());

			System.out.println("[INFO] Parsing connection properties:");
			System.out.println("[INFO] Query endpoint: " + queryEndpoint);
			System.out.println("[INFO] Update endpoint: " + updateEndpoint);
			System.out.println("[INFO] Graph store endpoint: " + dataEndpoint);
		}
	}

	/**
	 * Read the configuration properties for the example.
	 *
	 * @return the configuration object
	 */
	public static ConnectionPropertiesFusekiJena setUpPropertiesFusekiJena() {
		Properties props = new Properties();
		props.setProperty("conn.endpoint", "http://localhost:8083");
		props.setProperty("conn.dataset", "ImunizacijaDataset");
		props.setProperty("conn.query", "query");
		props.setProperty("conn.update", "update");
		props.setProperty("conn.data", "data");
		return new ConnectionPropertiesFusekiJena(props);
	}
	
}
