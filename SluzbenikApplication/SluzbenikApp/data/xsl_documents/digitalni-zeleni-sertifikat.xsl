<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xmlns:b ="http://www.vakc-sistem.rs/digitalni-zeleni-sertifikat"
    xmlns:util ="http://www.vakc-sistem.rs/util">

    <xsl:template match="/">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <title>Digitalni zeleni sertifikat</title>
            </head>
            <body>
                <h3>Digitalni zeleni sertifikat</h3>

                <h4>Podaci o primaocu</h4>
                <p>Ime i prezime: <xsl:value-of select="//util:Ime"/></p>
                <p>Datum rođenja: <xsl:value-of select="//util:Datum_rodjenja"/></p>
                <p>Pol: <xsl:value-of select="//util:Pol"/></p>
                <p>JMBG: <xsl:value-of select="//util:JMBG"/></p>

                <h4>Podaci o sertifikatu</h4>
                <p>Broj sertifikata: <xsl:value-of select="//b:Broj_sertifikata"/></p>
                <p>Datum izdavanja: <xsl:value-of select="//b:Datum_izdavanja_sertifikata"/></p>

                <h4>Primljene doze</h4>
                <table border="1">

                    <tr>
                        <th>Redni broj doze</th>
                        <th>Datum primanja</th>
                        <th>Serija</th>
                        <th>Proizvođač</th>
                        <th>Tip</th>
                        <th>Ustanova</th>
                    </tr>

                    <xsl:for-each select="//b:Doza">
                        <tr>
                            <td><xsl:value-of select="@Redni_broj"/></td>
                            <td><xsl:value-of select="util:Datum"/></td>
                            <td><xsl:value-of select="util:Serija"/></td>
                            <td><xsl:value-of select="util:Proizvodjac"/></td>
                            <td><xsl:value-of select="util:Tip"/></td>
                            <td><xsl:value-of select="util:Ustanova"/></td>
                        </tr>
                    </xsl:for-each>

                </table>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>