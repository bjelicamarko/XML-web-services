package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;

import javax.xml.datatype.DatatypeConfigurationException;

public interface DZSService {
    void createDZS(String zahtevID, String idSluzbenika, String potvrdaXML) throws DzsException, DatatypeConfigurationException;
}
