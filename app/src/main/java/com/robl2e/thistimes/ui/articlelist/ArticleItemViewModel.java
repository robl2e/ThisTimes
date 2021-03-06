package com.robl2e.thistimes.ui.articlelist;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.robl2e.thistimes.data.model.article.Article;
import com.robl2e.thistimes.data.model.article.Multimedia;
import com.robl2e.thistimes.ui.filter.NewsDesk;

import java.util.Collections;
import java.util.List;

/**
 * Created by robl2e on 9/19/17.
 */

class ArticleItemViewModel {
    private static final String IMAGE_BASE_URL = "http://www.nytimes.com/";
    private String headline;
    private String webUrl;
    private String summary;
    private Multimedia multimedia;
    private String publishedDate;

    private String newsDesk;

    private ArticleItemViewModel(String headline, String webUrl, String summary, Multimedia multimedia, String publishedDate, String newsDesk) {
        this.headline = headline;
        this.webUrl = webUrl;
        this.summary = summary;
        this.multimedia = multimedia;
        this.publishedDate = publishedDate;
        this.newsDesk = newsDesk;
    }

    public String getHeadline() {
        return headline;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSummary() {
        return summary;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    @Nullable
    public String getImageUrl() {
        if (multimedia == null) return null;
        return IMAGE_BASE_URL + multimedia.getUrl();
    }

    public String getPublishedDateWithoutTime() {
        if (TextUtils.isEmpty(publishedDate)) return null;

        int index = publishedDate.indexOf("T");
        if (index == -1) return publishedDate;

        String dateOnly = publishedDate.substring(0, index);
        return dateOnly;
    }

    public int resolveColorResForNewsDeskType(String newsDesk) {
        if (TextUtils.isEmpty(newsDesk)) return 0;

        if (NewsDesk.ARTS.getValue().equalsIgnoreCase(newsDesk)) {
            return android.R.color.holo_red_light;
        } else if (NewsDesk.SPORTS.getValue().equalsIgnoreCase(newsDesk)) {
            return android.R.color.holo_blue_light;
        } else if (NewsDesk.FASHION_AND_STYLE.getValue().equalsIgnoreCase(newsDesk)) {
            return android.R.color.holo_purple;
        }
        return 0;
    }

    public static ArticleItemViewModel convert(Article article) {
        Multimedia multimedia = null;


        // Get first entry
        List<Multimedia> multimediaList = article.getMultimedia();
        if (article.getMultimedia() == null) {
            multimediaList = Collections.emptyList();
        }
        for (Multimedia item : multimediaList) {
            if ("thumbnail".equalsIgnoreCase(item.getSubtype())) {
                multimedia = item;
            }
        }
        return new ArticleItemViewModel(
                article.getHeadline().getMain(),
                article.getWebUrl(),
                article.getSnippet(),
                multimedia,
                article.getPubDate(),
                article.getNewsDesK()
                );
    }
}
