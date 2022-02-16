<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xmlns:b ="http://www.vakc-sistem.rs/izvestaj">
    <xsl:template match="/">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <title>Izvestaj</title>
            </head>
            <body>
                <h3>Izvestaj</h3>
                <p>Broj dokumenata o interesovanju: <xsl:value-of select="b:Izvestaj/b:Broj_dokumenata_o_interesovanju"/></p>
                <p>Broj pristiglih zahteva za DZS: <xsl:value-of select="b:Izvestaj/b:Broj_pristiglih_zahteva_za_DZS"/></p>
                <p>Broj izdatih zahteva za DZS: <xsl:value-of select="b:Izvestaj/b:Broj_izdatih_zahteva_za_DZS"/></p>
                <p>Broj primljenih vakcina: <xsl:value-of select="b:Izvestaj/b:Broj_primljenih_vakcina"/></p>
                <p>Broj primnljenih novovakcinisanih: <xsl:value-of select="b:Izvestaj/b:Broj_primnljenih_novovakcinisanih"/></p>

                <h4>Broj datih vakcina svakog od proizvodjaca</h4>
                <table border="1">

                    <tr>
                        <th>Naziv prozivodjaca</th>
                        <th>Kolicina</th>
                    </tr>

                    <xsl:for-each select="b:Izvestaj//b:Vakcina">
                        <tr>
                            <td><xsl:value-of select="@Naziv_proizvodjaca"/></td>
                            <td><xsl:value-of select="text()"/></td>
                        </tr>
                    </xsl:for-each>

                </table>

                <p>Datum: <xsl:value-of select="b:Izvestaj/b:Datum"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>