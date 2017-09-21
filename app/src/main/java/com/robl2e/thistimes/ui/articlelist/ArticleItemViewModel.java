package com.robl2e.thistimes.ui.articlelist;

import android.support.annotation.Nullable;

import com.robl2e.thistimes.data.model.article.Article;
import com.robl2e.thistimes.data.model.article.Multimedia;

/**
 * Created by robl2e on 9/19/17.
 */

class ArticleItemViewModel {
    private static final String IMAGE_BASE_URL = "http://www.nytimes.com/";

    private String headline;
    private String webUrl;
    private String summary;
    private Multimedia multimedia;

    private ArticleItemViewModel(String headline, String webUrl, String summary, Multimedia multimedia) {
        this.headline = headline;
        this.webUrl = webUrl;
        this.summary = summary;
        this.multimedia = multimedia;
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

    @Nullable
    public String getImageUrl() {
        if (multimedia == null) return null;
        return IMAGE_BASE_URL + multimedia.getUrl();
    }

    public static ArticleItemViewModel convert(Article article) {
        Multimedia multimedia = null;
        // Get first entry
        if (article.getMultimedia() != null && !article.getMultimedia().isEmpty()) {
            multimedia = article.getMultimedia().get(0);
        }

        return new ArticleItemViewModel(
                article.getHeadline().getMain(),
                article.getWebUrl(),
                article.getSnippet(),
                multimedia
                );
    }
}
