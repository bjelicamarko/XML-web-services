<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xmlns:b ="http://www.vakc-sistem.rs/interesovanje"
    xmlns:util ="http://www.vakc-sistem.rs/util">
    <xsl:template match="/">
        <html>
            <head>
                <title>Interesovanje (XSLT)</title>
            </head>
            <body>
                <h3>Dokument interesovanja</h3>
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
                <p>Ime: <xsl:value-of select="b:Interesovanje/b:Ime"/></p>
                <p>Prezime: <xsl:value-of select="b:Interesovanje/b:Prezime"/></p>
                <p>Kontakt:</p>
                <p> - Broj telefona: <xsl:value-of select="//util:Broj_telefona"/></p>
                <p> - Broj fiksnog telefona: <xsl:value-of select="//util:Broj_fiksnosg_telefona"/></p>
                <p> - Email adresa: <xsl:value-of select="//util:Email_adresa"/></p>
                <p>Opstina vakcinisanja: <xsl:value-of select="b:Interesovanje/b:Opstina_vakcinisanja"/></p>
                <p>Tip odabrane vakcine:</p>
                <ul>
                    <xsl:for-each select="b:Interesovanje/b:Vakcina">
                        <li><xsl:value-of select="@Tip"/></li>
                        <br/>
                    </xsl:for-each>
                </ul>
                <p>Dobrovoljni davalac: <xsl:value-of select="b:Interesovanje/b:Dobrovoljni_davalac"/></p>
                <p>Datum podnosenja interesovanja: <xsl:value-of select="b:Interesovanje/b:Datum"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>