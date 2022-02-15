<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:b="http://www.vakc-sistem.rs/potvrda-o-vakcinaciji"
    xmlns:util="http://www.vakc-sistem.rs/util"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="potvrda-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="potvrda-page">
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
                        Potvrda o vakcinaciji (COVID-19)
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-bottom="15pt"/>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold">JMBG: </fo:inline> <xsl:value-of select="//util:JMBG"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Ime i prezime: </fo:inline> <xsl:value-of select="//util:Ime"/> <xsl:value-of select="concat(' ', //util:Prezime)"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum rodjenja: </fo:inline> <xsl:value-of select="format-date(//util:Datum_rodjenja, '[D1].[M1].[Y1].')"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <xsl:choose>
                            <xsl:when test="//util:Pol = 'Musko'">
                                <fo:inline font-weight="bold"> Pol: </fo:inline> Muški
                            </xsl:when>
                            <xsl:otherwise>
                                <fo:inline font-weight="bold"> Pol: </fo:inline> Ženski
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>
<!--                    O primljenoj dozi -->

                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold">Primljene doze: </fo:inline>
                    </fo:block>
                    <fo:table>
                        <fo:table-column column-width="25mm"/>
                        <fo:table-column column-width="40mm"/>
                        <fo:table-column column-width="25mm"/>
                        <fo:table-column column-width="31.5mm"/>
                        <fo:table-column column-width="25mm"/>
                        <fo:table-column column-width="25mm"/>
                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="2pt" >
                                    <fo:block font-weight="bold">Redni broj</fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="center" border-style="solid" border-width="2pt" >
                                    <fo:block font-weight="bold">Datum primanja</fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="center" border-style="solid" border-width="2pt" >
                                    <fo:block font-weight="bold">Serija</fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="center" border-style="solid" border-width="2pt" >
                                    <fo:block font-weight="bold">Proizvodjac</fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="center" border-style="solid" border-width="2pt" >
                                    <fo:block font-weight="bold">Tip</fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="center" border-style="solid" border-width="2pt" >
                                    <fo:block font-weight="bold">Ustanova</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <xsl:for-each select="//b:Podaci_o_vakcini/b:Doza">
                                <fo:table-row>
                                    <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="1pt" >
                                        <fo:block><xsl:value-of select="@Redni_broj"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="1pt" >
                                        <fo:block><xsl:value-of select="util:Datum"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="1pt" >
                                        <fo:block><xsl:value-of select="util:Serija"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="1pt" >
                                        <fo:block><xsl:value-of select="util:Proizvodjac"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="1pt" >
                                        <fo:block><xsl:value-of select="util:Tip"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="center" display-align="center" border-style="solid" border-width="1pt" >
                                        <fo:block><xsl:value-of select="util:Ustanova"/></fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:for-each>
                            </fo:table-body>
                    </fo:table>
                    <xsl:choose>
                        <xsl:when test="//b:Datum_naredne_doze">
                            <fo:block font-family="Times" font-size="15px" padding="10px">
                                <fo:inline font-weight="bold"> Datum naredne vakcinacije: </fo:inline> <xsl:value-of select="format-date(//b:Datum_naredne_doze, '[D1].[M1].[Y1].')"/>
                            </fo:block>
                        </xsl:when>
                        <xsl:otherwise>
                            <fo:block/>
                        </xsl:otherwise>
                    </xsl:choose>
                    <fo:block border-top-style="solid" text-align="center" margin-top="10px"/>
                    <fo:block text-align="right" font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum: </fo:inline> <xsl:value-of select="format-date(//b:Datum_izdavanja, '[D1].[M1].[Y1].')"/>
                    </fo:block>
<!--                    Add QRCode -->
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>