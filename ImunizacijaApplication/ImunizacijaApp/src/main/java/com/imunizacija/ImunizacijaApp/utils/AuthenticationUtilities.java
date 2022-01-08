package com.imunizacija.ImunizacijaApp.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Utilities to support and simplify examples.
 */
public class AuthenticationUtilities {
	/**
	 * Connection parameters.
	 */
	static public class ConnectionProperties {

		public String host;
		public int port = -1;
		public String user;
		public String password;
		public String driver;
		public String uri;

		public ConnectionProperties(Properties props) {
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
	public static ConnectionProperties setUpProperties() throws IOException {
		Properties props = new Properties();
		props.setProperty("conn.user", "admin");
		props.setProperty("conn.password", "");
		props.setProperty("conn.host", "localhost");
		props.setProperty("conn.port", "8082");
		props.setProperty("conn.driver", "org.exist.xmldb.DatabaseImpl");
		return new ConnectionProperties(props);
	}
	
}
