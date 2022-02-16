<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xmlns:b ="http://www.vakc-sistem.rs/potvrda-o-vakcinaciji"
    xmlns:util ="http://www.vakc-sistem.rs/util">
    <xsl:template match="/">
        <html>
            <head>
                <title>Potvrda (XSLT)</title>
            </head>
            <body>
                <h3>Dokument potvrde o vakcinaciji</h3>
                <p>JMBG: <xsl:value-of select="//util:JMBG"/></p>
                <p>Ime: <xsl:value-of select="//util:Ime"/></p>
                <p>Prezime: <xsl:value-of select="//util:Prezime"/></p>
                <p>Datum rodjenja: <xsl:value-of select="//util:Datum_rodjenja"/></p>
                <p>Pol: <xsl:value-of select="//util:Pol"/></p>
                <p>O primljenoj vakcini:</p>
                <p> - Redni broj: <xsl:value-of select="//b:Doza/@Redni_broj"/></p>
                <p> - Datum primanja: <xsl:value-of select="//b:Doza//util:Datum"/></p>
                <p> - Serija: <xsl:value-of select="//b:Doza//util:Serija"/></p>
                <p> - Proizvodjac: <xsl:value-of select="//b:Doza//util:Proizvodjac"/></p>
                <p> - Tip: <xsl:value-of select="//b:Doza//util:Tip"/></p>
                <p> - Ustanova: <xsl:value-of select="//b:Doza//util:Ustanova"/></p>
                <p>Datum izdavanja potvrde: <xsl:value-of select="//b:Datum_izdavanja"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>