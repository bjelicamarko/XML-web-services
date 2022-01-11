package com.imunizacija.ImunizacijaApp.repository;

public class Constants {
    public static final String PACKAGE_PATH_INTERESOVANJE = "com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje";
    public static final String COLLECTION_PATH_INTERESOVANJE = "db/interesovanje";

    public static final String PACKAGE_PATH_POTVRDA = "com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji";
    public static final String COLLECTION_PATH_POTVRDA = "db/potvrda_o_vakcinaciji";

    public static final String PACKAGE_PATH_SAGLASNOST = "com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju";
    public static final String COLLECTION_PATH_SAGLASNOST = "db/saglasnost_za_imunizaciju";

    public static final String PACKAGE_PATH_ZAHTEV_DZS = "com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs";
    public static final String COLLECTION_PATH_ZAHTEV_DZS = "db/zahtev_dzs";


    // RDF
    public static final String ROOT_PATH = "http://www.vakc-sistem.rs/";

    public static final String OSOBA_NAMESPACE_PATH = ROOT_PATH + "osoba/";

    public static final String DOZA_NAMESPACE_PATH = ROOT_PATH + "doza/";

    public static final String POTVRDA_NAMESPACE_PATH = ROOT_PATH + "potvrda-o-vakcinaciji/";

    public static final String PREDICATE_NAMESPACE = ROOT_PATH + "predicate/";

    public static final String POTVRDA_NAMED_GRAPH_URI = "/potvrda_o_vakcinaciji/metadata";

    public static final String OSOBA_NAMED_GRAPH_URI = "/osoba/metadata";

    public static final String DOZA_NAMED_GRAPH_URI = "/doza/metadata";
}
