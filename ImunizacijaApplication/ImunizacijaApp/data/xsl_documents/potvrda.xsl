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
                <p>O primljenim vakcinama:</p>
                <table>
                    <tr>
                        <th>Redni broj</th>
                        <th>Datum</th>
                        <th>Serija</th>
                        <th>Proizvodjac</th>
                        <th>Tip</th>
                        <th>Ustanova</th>
                    </tr>
                    <xsl:for-each select="//b:Podaci_o_vakcini/b:Doza">
                        <tr>
                            <td> - Redni broj: <xsl:value-of select="@Redni_broj"/></td>
                            <td> - Datum primanja: <xsl:value-of select="util:Datum"/></td>
                            <td> - Serija: <xsl:value-of select="util:Serija"/></td>
                            <td> - Proizvodjac: <xsl:value-of select="util:Proizvodjac"/></td>
                            <td> - Tip: <xsl:value-of select="util:Tip"/></td>
                            <td> - Ustanova: <xsl:value-of select="util:Ustanova"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <p>Datum izdavanja potvrde: <xsl:value-of select="b:Datum_izdavanja"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>