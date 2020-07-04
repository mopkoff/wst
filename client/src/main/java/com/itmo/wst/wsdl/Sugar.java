//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.18 at 02:12:54 PM MSK 
//


package com.itmo.wst.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sugar.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sugar"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="DRY"/&gt;
 *     &lt;enumeration value="SEMI_DRY"/&gt;
 *     &lt;enumeration value="SEMI_SWEET"/&gt;
 *     &lt;enumeration value="SWEET"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "sugar")
@XmlEnum
public enum Sugar {

    DRY,
    SEMI_DRY,
    SEMI_SWEET,
    SWEET;

    public String value() {
        return name();
    }

    public static Sugar fromValue(String v) {
        return valueOf(v);
    }

}