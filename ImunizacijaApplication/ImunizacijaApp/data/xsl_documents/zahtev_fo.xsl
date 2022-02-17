<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:b="http://www.vakc-sistem.rs/zahtev-dzs"
    xmlns:util="http://www.vakc-sistem.rs/util"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zahtev-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="zahtev-page">
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
                        Zahtev za digitalni zeleni sertifikat
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-bottom="15pt"/>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
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
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Razlog: </fo:inline>
                        <xsl:for-each select="tokenize(//b:Razlog, ',')">
                            <xsl:choose>
                                <xsl:when test="contains(., '~+')">
                                    <fo:inline font-weight="bold" font-style="italic">
                                        <xsl:value-of select="substring(.,3,(string-length(.) - 4))"/>
                                    </fo:inline>
                                </xsl:when>
                                <xsl:when test="contains(., '+')">
                                    <fo:inline font-weight="bold">
                                        <xsl:value-of select="substring(.,2,(string-length(.) - 2))"/>
                                    </fo:inline>
                                </xsl:when>
                                <xsl:when test="contains(., '~')">
                                    <fo:inline font-style="italic">
                                        <xsl:value-of select="substring(.,2,(string-length(.) - 2))"/>
                                    </fo:inline>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select='.'/>
                                </xsl:otherwise>
                            </xsl:choose>

                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Mesto: </fo:inline> <xsl:value-of select="//b:Mesto"/>
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-top="10px"/>
                    <fo:block text-align="right" font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum: </fo:inline> <xsl:value-of select="format-date(//b:Datum, '[D1].[M1].[Y1].')"/>
                    </fo:block>
<!--                    Add QRCode -->
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>