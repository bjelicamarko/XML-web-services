package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;

public interface PotvrdaService {
    PotvrdaOVakcinaciji findOneById(String id);

    byte[] generateInteresovanjePDF(String id) throws Exception;

    String generateInteresovanjeHTML(String id) throws Exception;
}
