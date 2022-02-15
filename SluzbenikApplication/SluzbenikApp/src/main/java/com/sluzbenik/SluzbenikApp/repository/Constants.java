package com.sluzbenik.SluzbenikApp.repository;

public class Constants {
    public static final String PACKAGE_PATH_DZS = "com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat";
    public static final String COLLECTION_PATH_DZS = "db/digitalni_zeleni_sertifikat";

    public static final String PACKAGE_PATH_IZVESTAJ = "com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj";
    public static final String COLLECTION_PATH_IZVESTAJ = "db/izvestaj";

    public static final String PACKAGE_PATH_TERMINI = "com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj";
    public static final String COLLECTION_PATH_TERMINI = "db/termini";
    public static final String XML_TERMIN = "termini.xml";

    public static final String PACKAGE_PATH_KORISNIK = "com.sluzbenik.SluzbenikApp.model.vakc_sistem.korisnik";
    public static final String COLLECTION_PATH_KORISNIK = "db/korisnik";

    //XML read write
    public static final String XML_SCHEMA_PATH_DZS = "data/xml_example/digitalni_zeleni_sertifikat.xsd";
    public static final String XML_SCHEMA_PATH_IZVESTAJ = "data/xml_example/izvestaj.xsd";
    public static final String XML_SCHEMA_PATH_TERMINI = "data/xml_example/termini.xsd";

    public static final String XML_WRITE_BASE_PATH = "data/xml_write_test/";

    // RDF
    public static final String ROOT_PATH = "http://www.vakc-sistem.rs/";

    public static final String OSOBA_NAMESPACE_PATH = ROOT_PATH + "osoba/";
    public static final String DZS_NAMESPACE_PATH = ROOT_PATH + "digitalni-zeleni-sertifikat/";
    public static final String ZAHTEV_NAMESPACE_PATH = ROOT_PATH + "zahtev/";
    public static final String TERMIN_NAMESPACE_PATH = ROOT_PATH + "termini";
    public static final String KORISNIK_NAMESPACE_PATH = ROOT_PATH + "korisnik";

    public static final String PREDICATE_NAMESPACE = ROOT_PATH + "predicate/";
    public static final String OSOBA_NAMED_GRAPH_URI = "/osoba/metadata";
    public static final String DZS_NAMED_GRAPH_URI = "/dzs/metadata";

    //predicates
    public static final String ISSUED_TO_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/issuedTo>";
}
