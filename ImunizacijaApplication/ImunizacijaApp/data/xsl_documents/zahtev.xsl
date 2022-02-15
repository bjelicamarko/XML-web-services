<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xmlns:b ="http://www.vakc-sistem.rs/zahtev-dzs"
    xmlns:util ="http://www.vakc-sistem.rs/util">
    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev DZS (XSLT)</title>
            </head>
            <body>
                <h3>Dokument zahteva za digitalni zeleni sertifikat</h3>
                <xsl:choose>
                    <xsl:when test="//util:JMBG != '0101901404404'">
                        <p>JMBG: <xsl:value-of select="//util:JMBG"/></p>
                    </xsl:when>
                    <xsl:otherwise/>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="//util:Broj_pasosa != 'ZZZZZZ'">
                        <p>Broj pasosa: <xsl:value-of select="//util:Broj_pasosa"/></p>
                    </xsl:when>
                    <xsl:otherwise/>
                </xsl:choose>
                <p>Ime: <xsl:value-of select="//util:Ime"/></p>
                <p>Prezime: <xsl:value-of select="//util:Prezime"/></p>
                <p>Datum rodjenja: <xsl:value-of select="//util:Datum_rodjenja"/></p>
                <p>Pol: <xsl:value-of select="//util:Pol"/></p>
                <p>Razlog: <xsl:value-of select="//b:Razlog"/></p>
                <p>Mesto: <xsl:value-of select="//b:Mesto"/></p>
                <p>Datum: <xsl:value-of select="//b:Datum"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>