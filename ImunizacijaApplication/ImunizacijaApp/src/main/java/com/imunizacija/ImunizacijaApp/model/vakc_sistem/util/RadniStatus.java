//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.08 at 06:14:28 PM CET 
//


package com.imunizacija.ImunizacijaApp.model.vakc_sistem.util;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Radni_status.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Radni_status"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Zaposlen"/&gt;
 *     &lt;enumeration value="Nezaposlen"/&gt;
 *     &lt;enumeration value="Penzioner"/&gt;
 *     &lt;enumeration value="Ucenik"/&gt;
 *     &lt;enumeration value="Dete"/&gt;
 *     &lt;enumeration value="Student"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Radni_status")
@XmlEnum
public enum RadniStatus {

    @XmlEnumValue("Zaposlen")
    ZAPOSLEN("Zaposlen"),
    @XmlEnumValue("Nezaposlen")
    NEZAPOSLEN("Nezaposlen"),
    @XmlEnumValue("Penzioner")
    PENZIONER("Penzioner"),
    @XmlEnumValue("Ucenik")
    UCENIK("Ucenik"),
    @XmlEnumValue("Dete")
    DETE("Dete"),
    @XmlEnumValue("Student")
    STUDENT("Student");
    private final String value;

    RadniStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RadniStatus fromValue(String v) {
        for (RadniStatus c: RadniStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
