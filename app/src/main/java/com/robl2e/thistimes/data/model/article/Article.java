
package com.robl2e.thistimes.data.model.article;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Article {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("lead_paragraph")
    @Expose
    private String leadParagraph;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("print_page")
    @Expose
    private String printPage;
    @SerializedName("blog")
    @Expose
    private Blog blog;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("keywords")
    @Expose
    private List<Keyword> keywords;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("news_desK")
    @Expose
    private String newsDesK;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subsection_name")
    @Expose
    private String subsectionName;
    @SerializedName("byline")
    @Expose
    private Byline byline;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("word_count")
    @Expose
    private String wordCount;
    @SerializedName("slideshow_credits")
    @Expose
    private String slideshowCredits;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Article() {
    }

    /**
     * 
     * @param headline
     * @param webUrl
     * @param pubDate
     * @param slideshowCredits
     * @param keywords
     * @param documentType
     * @param leadParagraph
     * @param sectionName
     * @param snippet
     * @param wordCount
     * @param blog
     * @param id
     * @param typeOfMaterial
     * @param multimedia
     * @param byline
     * @param printPage
     * @param source
     * @param newsDesK
     * @param _abstract
     * @param subsectionName
     */
    public Article(String webUrl, String snippet, String leadParagraph, String _abstract, String printPage, Blog blog, String source, Headline headline, List<Keyword> keywords, String pubDate, String documentType, String newsDesK, String sectionName, String subsectionName, Byline byline, String typeOfMaterial, String id, String wordCount, String slideshowCredits, List<Multimedia> multimedia) {
        super();
        this.webUrl = webUrl;
        this.snippet = snippet;
        this.leadParagraph = leadParagraph;
        this._abstract = _abstract;
        this.printPage = printPage;
        this.blog = blog;
        this.source = source;
        this.headline = headline;
        this.keywords = keywords;
        this.pubDate = pubDate;
        this.documentType = documentType;
        this.newsDesK = newsDesK;
        this.sectionName = sectionName;
        this.subsectionName = subsectionName;
        this.byline = byline;
        this.typeOfMaterial = typeOfMaterial;
        this.id = id;
        this.wordCount = wordCount;
        this.slideshowCredits = slideshowCredits;
        this.multimedia = multimedia;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesK() {
        return newsDesK;
    }

    public void setNewsDesK(String newsDesK) {
        this.newsDesK = newsDesK;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    public Byline getByline() {
        return byline;
    }

    public void setByline(Byline byline) {
        this.byline = byline;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getSlideshowCredits() {
        return slideshowCredits;
    }

    public void setSlideshowCredits(String slideshowCredits) {
        this.slideshowCredits = slideshowCredits;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
