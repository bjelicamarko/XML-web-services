package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.IzvestajDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;

import javax.xml.datatype.DatatypeConfigurationException;

public interface IzvestajService {

    Izvestaj findOneById(String id);

    byte[] generateIzvestajPDF(String id) throws Exception;

    String generateIzvestajHTML(String id) throws Exception;

    IzvestajDTO createReport(IzvestajDTO izvestajDTO, String dateFrom, String dateTo);

    String generateReport(IzvestajDTO izvjestajDTO, String dateFrom, String dateTo) throws DatatypeConfigurationException;
}
