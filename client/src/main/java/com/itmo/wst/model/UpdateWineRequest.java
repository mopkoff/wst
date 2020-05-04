package com.itmo.wst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateWineRequest {
    protected Wine wine;

    /**
     * Gets the value of the wine property.
     * 
     * @return
     *     possible object is
     *     {@link Wine }
     *     
     */
    public Wine getWine() {
        return wine;
    }

    /**
     * Sets the value of the wine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wine }
     *     
     */
    public void setWine(Wine value) {
        this.wine = value;
    }

}
