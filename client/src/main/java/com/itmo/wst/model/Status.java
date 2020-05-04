package com.itmo.wst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


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
@JsonIgnoreProperties(ignoreUnknown = true)
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
