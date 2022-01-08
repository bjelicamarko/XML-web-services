package com.sluzbenik.SluzbenikApp.repository;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;

import com.sluzbenik.SluzbenikApp.repository.id_generator.IdGeneratorDZS;
import com.sluzbenik.SluzbenikApp.repository.id_generator.IdGeneratorInterface;
import org.exist.xmldb.EXistResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities.ConnectionProperties;


public abstract class StoreRetrieveXMLRepository {

	protected static ConnectionProperties connectionProp;

	protected IdGeneratorInterface idGenerator;

	public StoreRetrieveXMLRepository(IdGeneratorInterface idGenerator) {
		this.idGenerator = idGenerator;
	}

	public static void registerDatabase(){
		try {
			// load properties
			connectionProp = AuthenticationUtilities.setUpProperties();

			System.out.println("[INFO] Loading driver class: " + connectionProp.driver);
			Class<?> cl = Class.forName(connectionProp.driver);


			// encapsulation of the database driver functionality
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// entry point for the API which enables you to get the Collection reference
			DatabaseManager.registerDatabase(database);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | XMLDBException | IOException e) {
			e.printStackTrace();
		}
	}

//	protected void storeXML(String collectionId, String documentId, OutputStream document) {
//
//		// a collection of Resources stored within an XML database
//        Collection col = null;
//        XMLResource res = null;
//
//        try {
//
//        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
//            col = getOrCreateCollection(collectionId);
//
//            System.out.println("[INFO] Inserting the document: " + documentId);
//            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
//
//            res.setContent(document);
//            System.out.println("[INFO] Storing the document: " + res.getId());
//
//            col.storeResource(res);
//            System.out.println("[INFO] Done.");
//
//        } catch (XMLDBException e) {
//			e.printStackTrace();
//		} finally {
//			closeResources(col, res);
//		}
//	}

	protected Node retrieveXML(String collectionId, String documentId) {
		Collection col = null;
		XMLResource res = null;
		Node doc = null;

		try {
			// get the collection
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = DatabaseManager.getCollection(connectionProp.uri + collectionId);
			col.setProperty(OutputKeys.INDENT, "yes");

			System.out.println("[INFO] Retrieving the document: " + documentId);
			res = (XMLResource)col.getResource(documentId);

			if(res == null) {
				System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
			} else {
				doc = res.getContentAsDOM();
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		} finally {
			//don't forget to clean up!
			closeResources(col, res);
		}
		return doc;
	}

	protected Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }
    
	protected Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {
        
        Collection col = DatabaseManager.getCollection(connectionProp.uri + collectionUri, connectionProp.user, connectionProp.password);
        
        // create the collection if it does not exist
        if(col == null) {
        
         	if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }
            
        	String[] pathSegments = collectionUri.split("/");
            
        	if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();
            
                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/").append(pathSegments[i]);
                }
                
                Collection startCol = DatabaseManager.getCollection(connectionProp.uri + path, connectionProp.user, connectionProp.password);
                
                if (startCol == null) {
                	
                	// child collection does not exist
                    
                	String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(connectionProp.uri + parentPath, connectionProp.user, connectionProp.password);
                    
                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");
                    
                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    
                    col.close();
                    parentCol.close();
                    
                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }

	protected void closeResources(Collection col, XMLResource res) {
		if(res != null) {
			try {
				((EXistResource)res).freeResources();
			} catch (XMLDBException xe) {
				xe.printStackTrace();
			}
		}

		if(col != null) {
			try {
				col.close();
			} catch (XMLDBException xe) {
				xe.printStackTrace();
			}
		}
	}
}
