
package com.robl2e.thistimes.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.robl2e.thistimes.data.remote.article.ArticleSearchResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GenericResponse {

    @SerializedName("response")
    @Expose
    private ArticleSearchResponse response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GenericResponse() {
    }

    /**
     * 
     * @param response
     */
    public GenericResponse(ArticleSearchResponse response) {
        super();
        this.response = response;
    }

    public ArticleSearchResponse getResponse() {
        return response;
    }

    public void setResponse(ArticleSearchResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
