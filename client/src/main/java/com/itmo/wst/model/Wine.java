package com.itmo.wst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigInteger;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Wine {
    protected BigInteger id;
    protected String name;
    protected Sugar sugar;
    protected Color color;
    protected Float rating;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sugar property.
     * 
     * @return
     *     possible object is
     *     {@link Sugar }
     *     
     */
    public Sugar getSugar() {
        return sugar;
    }

    /**
     * Sets the value of the sugar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sugar }
     *     
     */
    public void setSugar(Sugar value) {
        this.sugar = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link Color }
     *     
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link Color }
     *     
     */
    public void setColor(Color value) {
        this.color = value;
    }

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRating(Float value) {
        this.rating = value;
    }

}
