package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.IzvestajDTO;

public interface IzvestajiService {

    IzvestajDTO createReport(String dateTo, String dateFrom);
}
