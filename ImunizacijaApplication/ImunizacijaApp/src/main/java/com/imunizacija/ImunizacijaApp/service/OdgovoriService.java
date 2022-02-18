package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.VakcinaKolicinaDTO;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;

import javax.mail.MessagingException;

public interface OdgovoriService {

   void dodajOdgovor(OdgovorTerminDTO odgovor);

   void posaljiOdgovore() throws MessagingException;

   void azurirajOdgovor(OdgovorTerminDTO odgovor);

   void izbrisiOdgovor(String email);

   Odgovori.Odgovor vratiOdgovor(String email);

   VakcinaKolicinaDTO vratiDozeUMagacin();

   String generisiTekstOdgovora(OdgovorTerminDTO odgovorTerminDTO);
}
