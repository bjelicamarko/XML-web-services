package com.sluzbenik.SluzbenikApp.model.dto.user_dto;


import com.sluzbenik.SluzbenikApp.model.vakc_sistem.korisnik.Korisnik;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
public class KorisniciListDTO {
    private List<KorisnikBasicInfoDTO> korisnikBasicInfoDTOList = new ArrayList<>();

    public KorisniciListDTO(){}

    public List<KorisnikBasicInfoDTO> getKorisnikBasicInfoDTOList() {
        return korisnikBasicInfoDTOList;
    }

    public void setKorisnikBasicInfoDTOList(List<KorisnikBasicInfoDTO> korisnikBasicInfoDTOList) {
        this.korisnikBasicInfoDTOList = korisnikBasicInfoDTOList;
    }

    public void setKorisnikBasicInfoDTOListFromKorisnici(List<Korisnik> korisnici) {
        korisnici.forEach(korisnik -> korisnikBasicInfoDTOList.add(new KorisnikBasicInfoDTO(korisnik)));
    }
}
