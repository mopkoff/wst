//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.01 at 02:15:47 AM MSK 
//


package com.itmo.wst.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for status.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="status"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="FAILED"/&gt;
 *     &lt;enumeration value="SUCCESS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "status")
@XmlEnum
public enum Status {

    FAILED,
    SUCCESS;

    public String value() {
        return name();
    }

    public static Status fromValue(String v) {
        return valueOf(v);
    }

}