package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;

import java.io.StringWriter;

public interface InteresovanjeService {
    Interesovanje findOneById(String id);

    byte[] generateInteresovanjePDF(String id) throws Exception;

    String generateInteresovanjeHTML(String id) throws Exception;
}
