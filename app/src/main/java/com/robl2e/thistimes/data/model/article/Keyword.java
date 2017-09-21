
package com.robl2e.thistimes.data.model.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Keyword {

    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     *
     */
    public Keyword() {
    }

    /**
     *
     * @param rank
     * @param name
     * @param value
     */
    public Keyword(String rank, String name, String value) {
        super();
        this.rank = rank;
        this.name = name;
        this.value = value;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
