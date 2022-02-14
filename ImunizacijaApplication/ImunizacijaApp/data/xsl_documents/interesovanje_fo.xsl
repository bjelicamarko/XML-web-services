<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:b="http://www.vakc-sistem.rs/interesovanje"
    xmlns:util="http://www.vakc-sistem.rs/util"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="interesovanje-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="interesovanje-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="Times" font-size="24px" font-weight="bold" padding="10px">
                        Interesovanje (XSL-FO)
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center" margin-bottom="15pt"/>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Ime: </fo:inline> <xsl:value-of select="//b:Ime"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Prezime: </fo:inline> <xsl:value-of select="//b:Prezime"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Kontakt: </fo:inline>
                    </fo:block>
                    <fo:block text-indent="30pt" font-family="Times" font-size="15px" padding="10px">
                        - Broj telefona: <xsl:value-of select="//util:Broj_telefona"/>
                    </fo:block>
                    <fo:block text-indent="30pt" font-family="Times" font-size="15px" padding="10px">
                        - Broj fiksnog telefona: <xsl:value-of select="//util:Broj_fiksnosg_telefona"/>
                    </fo:block>
                    <fo:block text-indent="30pt" font-family="Times" font-size="15px" padding="10px">
                        - Email: <xsl:value-of select="//util:Email_adresa"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Opstina vakcinisanja: </fo:inline> <xsl:value-of select="//b:Opstina_vakcinisanja"/>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Tip vakcine: </fo:inline>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <xsl:for-each select="//b:Vakcina">
                            <fo:block text-indent="30pt" > - <xsl:value-of select="@Tip"/></fo:block>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Dobrovoljni davalac: </fo:inline> <xsl:value-of select="//b:Dobrovoljni_davalac"/>
                    </fo:block>
                    <fo:block border-top-style="solid" text-align="center"/>
                    <fo:block text-align="right" font-family="Times" font-size="15px" padding="10px">
                        <fo:inline font-weight="bold"> Datum: </fo:inline> <xsl:value-of select="format-date(//b:Datum, '[D1].[M1].[Y1].')"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
</xsl:stylesheet>