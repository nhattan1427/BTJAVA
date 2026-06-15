<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="/books">
        <table>
            <xsl:for-each select="book">
                <tr>
                    <td><xsl:value-of select="title"/></td>

                    <td>
                        <xsl:choose>
                            <xsl:when test="price &gt; 60">
                                <xsl:attribute name="style">color:red</xsl:attribute>
                            </xsl:when>
                        </xsl:choose>

                        <xsl:value-of select="price"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>