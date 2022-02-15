package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;

import javax.mail.MessagingException;

public interface OdgovoriService {

   void dodajOdgovor(OdgovorTerminDTO odgovor);

   void posaljiOdgovore() throws MessagingException;

   void azurirajOdgovor(OdgovorTerminDTO odgovor);

   void izbrisiOdgovor(OdgovorTerminDTO odgovor);
}
