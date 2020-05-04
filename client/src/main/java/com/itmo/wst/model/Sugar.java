package com.itmo.wst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
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
