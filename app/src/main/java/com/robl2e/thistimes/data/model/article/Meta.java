
package com.robl2e.thistimes.data.model.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Meta {

    @SerializedName("hits")
    @Expose
    private Integer hits;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Meta() {
    }

    /**
     * 
     * @param time
     * @param hits
     * @param offset
     */
    public Meta(Integer hits, Integer time, Integer offset) {
        super();
        this.hits = hits;
        this.time = time;
        this.offset = offset;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
