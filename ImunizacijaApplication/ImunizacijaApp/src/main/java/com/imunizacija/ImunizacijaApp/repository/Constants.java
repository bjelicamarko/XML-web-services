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

    public static final String COLLECTION_PATH_TERMIN = "db/termini";
    public static final String XML_TERMIN = "termini.xml";
    public static final String PACKAGE_PATH_KORISNIK = "com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik";
    public static final String COLLECTION_PATH_KORISNIK = "db/korisnik";

    //XML read write
    public static final String XML_SCHEMA_PATH_ZAHTEV = "data/xml_example/zahtev_za_izdavanje_zelenog.xsd";
    public static final String XML_SCHEMA_PATH_INTERESOVANJE = "data/xml_example/interesovanje.xsd";
    public static final String XML_SCHEMA_PATH_SAGLASNOST= "data/xml_example/saglasnost_za_imunizaciju.xsd";
    public static final String XML_SCHEMA_PATH_POTVRDA = "data/xml_example/potvrda_o_vakcinaciji.xsd";

    public static final String XML_WRITE_BASE_PATH = "data/xml_write_test/";


    // RDF
    public static final String ROOT_PATH = "http://www.vakc-sistem.rs/";

    public static final String OSOBA_NAMESPACE_PATH = ROOT_PATH + "osoba/";
    public static final String DOZA_NAMESPACE_PATH = ROOT_PATH + "doza/";
    public static final String POTVRDA_NAMESPACE_PATH = ROOT_PATH + "potvrda-o-vakcinaciji/";
    public static final String ZAHTEV_NAMESPACE_PATH = ROOT_PATH + "zahtev/";
    public static final String INTERESOVANJE_NAMESPACE_PATH = ROOT_PATH + "interesovanje/";
    public static final String SAGLASNOST_NAMESPACE_PATH = ROOT_PATH + "saglasnost-za-imunizaciju/";
    public static final String TERMIN_NAMESPACE_PATH = ROOT_PATH + "termini";
    public static final String KORISNIK_NAMESPACE_PATH = ROOT_PATH + "korisnik";

    public static final String PREDICATE_NAMESPACE = ROOT_PATH + "predicate/";

    public static final String POTVRDA_NAMED_GRAPH_URI = "/potvrda_o_vakcinaciji/metadata";
    public static final String OSOBA_NAMED_GRAPH_URI = "/osoba/metadata";
    public static final String DOZA_NAMED_GRAPH_URI = "/doza/metadata";
    public static final String ZAHTEV_NAMED_GRAPH_URI = "/zahtev/metadata";
    public static final String INTERESOVANJE_NAMED_GRAPH_URI = "/interesovanje/metadata";
    public static final String SAGLASNOST_NAMED_GRAPH_URI="/saglasnost_za_imunizaciju/metadata";

    //predicates
    public static final String ISSUED_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/issued>";
    public static final String ISSUED_TO_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/issuedTo>";
    public static final String CREATED_AT_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/createdAt>";
    public static final String CREATED_BY_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/createdBy>";
}
