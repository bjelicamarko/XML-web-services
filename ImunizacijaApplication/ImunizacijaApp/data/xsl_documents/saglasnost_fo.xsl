<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:b="http://www.vakc-sistem.rs/saglasnost-za-imunizaciju"
    xmlns:util="http://www.vakc-sistem.rs/util"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="saglasnost-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>


            <fo:page-sequence master-reference="saglasnost-page">
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
                        Saglasnost za vakcinaciju COVID-19
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-bottom="15pt"/>
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
                        <fo:inline font-weight="bold"> Ime (Ime roditelja) Prezime: </fo:inline>
                        <xsl:value-of select="//util:Ime"/> (<xsl:value-of select="//util:Ime_roditelja"/>) <xsl:value-of select="//util:Prezime"/>
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
                    <fo:table>
                        <fo:table-column column-number="1"  column-width="256pt" />
                        <fo:table-column column-number="2"  />
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline font-weight="bold"> Adresa: </fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-indent="-30pt" text-align="justify" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline font-weight="bold"> Kontakt: </fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-indent="30pt" text-align="left" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Ulica: <xsl:value-of select="//util:Naziv"/> <xsl:value-of select="concat(' ', //util:Broj)"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="justify" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Tel. mobilni: <xsl:value-of select="//util:Broj_telefona"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-indent="30pt" text-align="left" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Naselje: <xsl:value-of select="//util:Naselje"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="justify" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Tel. fiksni: <xsl:value-of select="//util:Broj_fiksnosg_telefona"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-indent="30pt" text-align="left" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Mesto: <xsl:value-of select="//util:Mesto"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="justify" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Email: <xsl:value-of select="//util:Email_adresa"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-indent="30pt" text-align="left" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Opština: <xsl:value-of select="//util:Opstina"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block/>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-indent="30pt" text-align="left" font-family="Times" font-size="15px" padding="7px">
                                        <fo:inline>- Grad: <xsl:value-of select="//util:Grad"/></fo:inline>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block/>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                    <fo:block font-family="Times" font-size="15px" padding="7px">
                        <xsl:choose>
                            <xsl:when test="//b:Socijalna_zastita">
                                <fo:inline> <fo:inline font-weight="bold">Socijalna zastita:</fo:inline> <xsl:value-of select="concat(' ', //b:Naziv_sedista)"/> - <xsl:value-of select="//b:Opstina_sedista"/></fo:inline>
                            </xsl:when>
                            <xsl:otherwise/>
                        </xsl:choose>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="7px">
                        <xsl:choose>
                            <xsl:when test="//b:Saglasan = 'Da'">
                                <fo:inline>Gore navedeni <fo:inline font-weight="bold"><xsl:value-of select="//util:Ime"/><xsl:value-of select="concat(' ', //util:Prezime)"/></fo:inline> je saglasan da se vakciniše <fo:inline font-weight="bold"><xsl:value-of select="//b:Imunoloski_lek"/></fo:inline> vakcinom.</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                                <fo:inline>Gore navedeni <fo:inline font-weight="bold"><xsl:value-of select="//util:Ime"/><xsl:value-of select="concat(' ', //util:Prezime)"/></fo:inline> nije saglasan da se vakciniše <fo:inline font-weight="bold"><xsl:value-of select="//b:Imunoloski_lek"/></fo:inline> vakcinom.</fo:inline>
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-top="5px"/>
                    <fo:block text-align="right" font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum: </fo:inline> <xsl:value-of select="format-date(//b:Saglasnost/b:Datum, '[D1].[M1].[Y1].')"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>