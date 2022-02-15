<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:b="http://www.vakc-sistem.rs/izvestaj"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="izvestaj-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>


            <fo:page-sequence master-reference="izvestaj-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:table>
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <fo:external-graphic src="url('grb-srbije.png')" padding-left="30pt" content-height="scale-to-fit" height="1.7in"  content-width="1.4in" scaling="non-uniform"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <fo:external-graphic src="url('batut-grb.png')" padding-top="20pt" padding-right="30pt" content-height="scale-to-fit" height="1.4in"  content-width="1.1in" scaling="non-uniform"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-size="10px" font-weight="bold" margin-left="32.5pt" >
                                        REPUBLIKA SRBIJA
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-size="10px" margin-right="15pt" text-align="right">
                                        Institut za javno zdravlje
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block/>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-size="10px" margin-right="57pt" text-align="right">
                                        Srbije
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block/>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Times" font-size="10px" margin-right="10pt" text-align="right">
                                        "Dr. Milan Jovanović Batut"
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <fo:block text-align="center" font-family="Times" font-size="24px" font-weight="bold" padding="10px">
                        Izveštaj o imunizaciji
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-bottom="15pt"/>

                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Broj dokumenata o interesovanju: </fo:inline> <xsl:value-of select="//b:Broj_dokumenata_o_interesovanju"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Broj pristiglih zahteva za DZS: </fo:inline> <xsl:value-of select="//b:Broj_pristiglih_zahteva_za_DZS"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Broj izdatih zahteva za DZS: </fo:inline> <xsl:value-of select="//b:Broj_izdatih_zahteva_za_DZS"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Broj primljenih vakcina: </fo:inline> <xsl:value-of select="//b:Broj_primljenih_vakcina"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Broj primljenih novovakcinisanih: </fo:inline> <xsl:value-of select="//b:Broj_primnljenih_novovakcinisanih"/>
                    </fo:block>

                    <fo:block font-family="Times" font-size="20px" padding="10px">
                        <fo:inline font-weight="bold"> Broj datih vakcina svakog od proizvođača </fo:inline>
                    </fo:block>
                    <fo:table border="solid 0.1mm black">
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block text-align="left">
                                        <fo:inline font-weight="bold"> Naziv proizvođača </fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block>
                                        <fo:inline font-weight="bold"> Količina </fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>

                            <xsl:for-each select="b:Izvestaj//b:Vakcina">

                                <fo:table-row>
                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="@Naziv_proizvodjaca"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="text()"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:for-each>

                        </fo:table-body>
                    </fo:table>

                    <fo:block border-top-style="solid" text-align="center"/>
                    <fo:block text-align="right" font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum: </fo:inline> <xsl:value-of select="format-date(//b:Datum, '[D1].[M1].[Y1].')"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>