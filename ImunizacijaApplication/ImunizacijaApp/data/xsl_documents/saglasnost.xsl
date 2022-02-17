<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xmlns:b ="http://www.vakc-sistem.rs/saglasnost-za-imunizaciju"
    xmlns:util ="http://www.vakc-sistem.rs/util">
    <xsl:template match="/">
        <html>
            <head>
                <title>Saglasnost (XSLT)</title>
            </head>
            <body>
                <h3>Dokument saglasnosti</h3>
                <xsl:choose>
                    <xsl:when test="//util:JMBG">
                        <p>Drzavljanstvo - JMBG: <xsl:value-of select="//util:JMBG"/></p>
                    </xsl:when>
                    <xsl:when test="//util:Evidencioni_broj_stranca">
                        <p>Drzavljanstvo - Evidencioni broj: <xsl:value-of select="//util:Evidencioni_broj_stranca "/></p>
                    </xsl:when>
                    <xsl:otherwise>
                        <p>Drzavljanstvo - Broj pasoša: <xsl:value-of select="//util:Br_pasosa"/></p>
                    </xsl:otherwise>
                </xsl:choose>
                <p>Ime: <xsl:value-of select="//util:Ime"/></p>
                <p>Prezime: <xsl:value-of select="//util:Prezime"/></p>
                <p>Datum rodjenja: <xsl:value-of select="//util:Datum_rodjenja"/></p>
                <p>Pol: <xsl:value-of select="//util:Pol"/></p>
                <p>Ime roditelja: <xsl:value-of select="//util:Ime_roditelja"/></p>
                <p>Adresa:</p>
                <p> - Ulica: <xsl:value-of select="//util:Naziv"/> <xsl:value-of select="concat(' ', //util:Broj)"/></p>
                <p> - Naselje: <xsl:value-of select="//util:Naselje"/></p>
                <p> - Mesto: <xsl:value-of select="//util:Mesto"/></p>
                <p> - Opština: <xsl:value-of select="//util:Opstina"/></p>
                <p> - Grad: <xsl:value-of select="//util:Grad"/></p>

                <p>Kontakt:</p>
                <p> - Broj telefona: <xsl:value-of select="//util:Broj_telefona"/></p>
                <p> - Broj fiksnog telefona: <xsl:value-of select="//util:Broj_fiksnosg_telefona"/></p>
                <p> - Email adresa: <xsl:value-of select="//util:Email_adresa"/></p>
                <p>Zaposlenje: <xsl:value-of select="//b:Radni_status"/> - <xsl:value-of select="//b:Zanimanje"/></p>
                <xsl:choose>
                    <xsl:when test="//b:Socijalna_zastita">
                        <p>Socijalna zastita: <xsl:value-of select="//b:Naziv_sedista"/> - <xsl:value-of select="//b:Opstina_sedista"/></p>
                    </xsl:when>
                    <xsl:otherwise/>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="//b:Saglasan = 'Da'">
                        <p>Gore navedeni <xsl:value-of select="//util:Ime"/><xsl:value-of select="concat(' ', //util:Prezime)"/> je saglasan da se vakcinise <xsl:value-of select="//b:Imunoloski_lek"/> vakcinom.</p>
                    </xsl:when>
                    <xsl:otherwise>
                        <p>Gore navedeni <xsl:value-of select="//util:Ime"/><xsl:value-of select="concat(' ', //util:Prezime)"/> nije saglasan da se vakcinise <xsl:value-of select="//b:Imunoloski_lek"/> vakcinom.</p>
                    </xsl:otherwise>
                </xsl:choose>
                <p>Datum podnosenja saglasnosti: <xsl:value-of select="//b:Saglasnost/b:Datum"/></p>
                <p>##########################################################</p>
                <p>Zdravstvena ustanova: <xsl:value-of select="//b:Zdravstvena_ustanova"/> </p>
                <p>Punkt: <xsl:value-of select="//b:Punkt"/></p>
                <p>Podaci o lekaru koji je izvrsio vakcinaciju:</p>
                <p> - Ime: <xsl:value-of select="//b:Podaci_o_lekaru/b:Ime"/></p>
                <p> - Prezime: <xsl:value-of select="//b:Podaci_o_lekaru/b:Prezime"/></p>
                <p> - Faksimil: <xsl:value-of select="//b:Podaci_o_lekaru/b:Faksimil"/></p>
                <p> - Broj telefona: <xsl:value-of select="//b:Podaci_o_lekaru/b:Broj_telefona"/></p>
                <p>Doza:</p>
                <p> - Datum: <xsl:value-of select="//b:Doza/util:Datum"/></p>
                <p> - Serija: <xsl:value-of select="//util:Serija"/></p>
                <p> - Proizvodjac: <xsl:value-of select="//util:Proizvodjac"/></p>
                <p> - Tip: <xsl:value-of select="//util:Tip"/></p>
                <p> - Ekstremitet: <xsl:value-of select="//util:Ekstremitet"/></p>
                <p> - Nezeljena rekacija: <xsl:value-of select="//util:Nezeljena_rekacija"/></p>
                <p>Kontraindikacije:</p>
                <p> - Dijagnoza: <xsl:value-of select="//b:Privremena_kontraindikacija/b:Dijagnoza"/></p>
                <p> - Datum: <xsl:value-of select="//b:Privremena_kontraindikacija/b:Datum"/></p>
                <p> - Odluka komisije o trajnim kontraindikacijama: <xsl:value-of select="//b:Kontraindikacije/b:Odluka_komisije_o_trajnim"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>