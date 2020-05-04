package com.itmo.wst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWineResponse {
    protected List<Wine> wine;

    /**
     * Gets the value of the wine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wine }
     * 
     * 
     */
    public List<Wine> getWine() {
        if (wine == null) {
            wine = new ArrayList<Wine>();
        }
        return this.wine;
    }

}
