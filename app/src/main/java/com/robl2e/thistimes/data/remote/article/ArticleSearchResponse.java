
package com.robl2e.thistimes.data.remote.article;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.robl2e.thistimes.data.model.article.Article;
import com.robl2e.thistimes.data.model.article.Meta;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ArticleSearchResponse {

    @SerializedName("docs")
    @Expose
    private List<Article> articles = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ArticleSearchResponse() {
    }

    /**
     * 
     * @param articles
     * @param meta
     */
    public ArticleSearchResponse(List<Article> articles, Meta meta) {
        super();
        this.articles = articles;
        this.meta = meta;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
