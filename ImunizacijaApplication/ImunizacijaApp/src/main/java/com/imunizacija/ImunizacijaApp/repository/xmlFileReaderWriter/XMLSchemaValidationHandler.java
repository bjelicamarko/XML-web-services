package com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

public class XMLSchemaValidationHandler implements ValidationEventHandler {

    public boolean handleEvent(ValidationEvent event) {

        // Ako nije u pitanju WARNING metoda vraća false
        if (event.getSeverity() != ValidationEvent.WARNING) {
            ValidationEventLocator validationEventLocator = event.getLocator();
            System.out.println("ERROR: Line "
                    + validationEventLocator.getLineNumber() + ": Col"
                    + validationEventLocator.getColumnNumber() + ": "
                    + event.getMessage());

            // Dalje izvršavanje se prekida
            return false;
        } else {
            ValidationEventLocator validationEventLocator = event.getLocator();
            System.out.println("WARNING: Line "
                    + validationEventLocator.getLineNumber() + ": Col"
                    + validationEventLocator.getColumnNumber() + ": "
                    + event.getMessage());

            // Nastavlja se dalje izvršavanje
            return true;
        }
    }
}
