package com.itmo.wst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public enum Color {

    ROSE,
    WHITE,
    RED;

    public String value() {
        return name();
    }

    public static Color fromValue(String v) {
        return valueOf(v);
    }

}
