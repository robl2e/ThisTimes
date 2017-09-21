
package com.robl2e.thistimes.data.model.article;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Byline {

    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("person")
    @Expose
    private List<String> person = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Byline() {
    }

    /**
     * 
     * @param person
     * @param organization
     * @param original
     */
    public Byline(String organization, String original, List<String> person) {
        super();
        this.organization = organization;
        this.original = original;
        this.person = person;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<String> getPerson() {
        return person;
    }

    public void setPerson(List<String> person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
