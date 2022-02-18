<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:b="http://www.vakc-sistem.rs/digitalni-zeleni-sertifikat"
    xmlns:util="http://www.vakc-sistem.rs/util"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="dzs-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="dzs-page">
                <fo:flow flow-name="xsl-region-body">
                    <xsl:variable name="razmak" select="'&#xA0;'"/>

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
                                        <fo:external-graphic src="url('qr-code.png')" padding-top="20pt" padding-right="30pt" content-height="scale-to-fit" height="1.4in"  content-width="1.1in" scaling="non-uniform"/>
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
                                    <fo:block/>
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
                        Digitalni zeleni sertifikat
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-bottom="15pt"/>

                    <fo:block font-family="Times" font-size="20px" padding="10px">
                        <fo:inline font-weight="bold"> Podaci o primaocu </fo:inline>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Ime i prezime: </fo:inline>
                        <xsl:value-of select="//util:Ime"/>
                        <xsl:value-of select="$razmak"/>
                        <xsl:value-of select="//util:Prezime"/>
                    </fo:block>

                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <xsl:choose>
                            <xsl:when test="//util:JMBG">
                                <fo:inline font-weight="bold">Državljanstvo - JMBG: </fo:inline> <xsl:value-of select="//util:JMBG"/>
                            </xsl:when>
                            <xsl:when test="//util:Evidencioni_broj_stranca">
                                <fo:inline font-weight="bold">Državljanstvo - Evidencioni broj:</fo:inline> <xsl:value-of select="//util:Evidencioni_broj_stranca"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <fo:inline font-weight="bold">Državljanstvo - Broj pasoša:</fo:inline> <xsl:value-of select="//util:Br_pasosa"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>

                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum rodjenja: </fo:inline> <xsl:value-of select="format-date(//util:Datum_rodjenja, '[D1].[M1].[Y1].')"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Pol: </fo:inline>
                        <xsl:choose>
                            <xsl:when test="//util:Pol = 'Musko'">
                                Muški
                            </xsl:when>
                            <xsl:otherwise>
                                Ženski
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>

                    <fo:block font-family="Times" font-size="20px" padding="10px">
                        <fo:inline font-weight="bold"> Podaci o sertifikatu </fo:inline>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Broj sertifikata: </fo:inline> <xsl:value-of select="//b:Broj_sertifikata"/>
                    </fo:block>

                    <fo:block font-family="Times" font-size="20px" padding="10px">
                        <fo:inline font-weight="bold"> Primljene doze </fo:inline>
                    </fo:block>

                    <fo:table border="solid 0.1mm black" >
                        <fo:table-body>
                            <fo:table-row font-size="12px">

                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block text-align="left">
                                        <fo:inline font-weight="bold"> Redni broj doze </fo:inline>
                                    </fo:block>
                                </fo:table-cell>

                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block>
                                        <fo:inline font-weight="bold"> Datum primanja </fo:inline>
                                    </fo:block>
                                </fo:table-cell>

                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block>
                                        <fo:inline font-weight="bold"> Serija </fo:inline>
                                    </fo:block>
                                </fo:table-cell>

                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block>
                                        <fo:inline font-family="Times" font-weight="bold"> Proizvođač </fo:inline>
                                    </fo:block>
                                </fo:table-cell>

                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block>
                                        <fo:inline font-weight="bold"> Tip </fo:inline>
                                    </fo:block>
                                </fo:table-cell>

                                <fo:table-cell border-style="solid" border-width="2pt">
                                    <fo:block>
                                        <fo:inline font-weight="bold"> Ustanova </fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>

                            <xsl:for-each select="//b:Doza">

                                <fo:table-row>
                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="@Redni_broj"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="format-date(//util:Datum, '[D1].[M1].[Y1].')"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="util:Serija"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="util:Proizvodjac"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="util:Tip"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell border-style="solid" border-width="2pt">
                                        <fo:block font-size="10px">
                                            <xsl:value-of select="util:Ustanova"/>
                                        </fo:block>
                                    </fo:table-cell>

                                </fo:table-row>
                            </xsl:for-each>

                        </fo:table-body>
                    </fo:table>

                    <fo:block border-top-style="solid" text-align="center" margin-top="15px"/>
                    <fo:block text-align="right" font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum izdavanja: </fo:inline> <xsl:value-of select="format-date(//b:Datum_izdavanja_sertifikata, '[D1].[M1].[Y1].')"/>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>