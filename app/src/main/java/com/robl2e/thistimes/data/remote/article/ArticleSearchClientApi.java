package com.robl2e.thistimes.data.remote.article;

import android.support.annotation.Nullable;
import android.util.Log;

import com.robl2e.thistimes.BuildConfig;
import com.robl2e.thistimes.ui.filter.FilterSettings;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by robl2e on 9/19/17.
 */

public class ArticleSearchClientApi {
    private static final String TAG = ArticleSearchClientApi.class.getSimpleName();

    private static final String BASE_URL = "https://api.nytimes.com";
    private static final String ARTICLE_SEARCH_ENDPOINT = BASE_URL + "/svc/search/v2/articlesearch.json";
    private static final String PARAM_API_KEY = "api-key";
    private static final String PARAM_QUERY = "q";
    private static final String PARAM_BEGIN_DATE = "begin_date";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_FILTER_QUERY = "fq";
    private static final String PARAM_PAGE = "page";


    private static class Holder {
        static final ArticleSearchClientApi articleSearchClientApi = new ArticleSearchClientApi();
    }

    /**
     * Returns the convenience global injector.
     * @return global injector
     */
    public static ArticleSearchClientApi getInstance() {
        return Holder.articleSearchClientApi;
    }

    private OkHttpClient client;

    ArticleSearchClientApi() {
        client = new OkHttpClient();
    }

    public void articleSearchRequest(String searchQuery, @Nullable FilterSettings filterSettings, Integer pageNumber, final Callback responseHandler) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ARTICLE_SEARCH_ENDPOINT).newBuilder();
        urlBuilder.addQueryParameter(PARAM_QUERY, searchQuery);

        if (filterSettings != null) {
            urlBuilder = addBeginDate(filterSettings.getBeginDate(), urlBuilder);
            urlBuilder = urlBuilder.addQueryParameter(PARAM_SORT, filterSettings.getSortOrder().getValue());
            urlBuilder = addFilterQuery(filterSettings, urlBuilder);
        }

        if (pageNumber != null) {
            urlBuilder = urlBuilder.addQueryParameter(PARAM_PAGE, String.valueOf(pageNumber));
        }

        urlBuilder.addQueryParameter(PARAM_API_KEY, BuildConfig.ARTICLE_SEARCH_API_KEY);

        String url = urlBuilder.build().toString();
        Log.d(TAG, "articleSearchRequest - " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (responseHandler == null) return;

                responseHandler.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (responseHandler == null) return;

                responseHandler.onResponse(call, response);
            }
        });
    }

    public void articleSearchRequest(String searchQuery, @Nullable FilterSettings filterSettings, final Callback responseHandler) {
        articleSearchRequest(searchQuery, filterSettings, null, responseHandler);
    }

    private static HttpUrl.Builder addBeginDate(DateTime beginDate, HttpUrl.Builder urlBuilder) {
        String dateStr = beginDate.format("YYYYMMDD");
        urlBuilder.addQueryParameter(PARAM_BEGIN_DATE, dateStr);
        return urlBuilder;
    }

    private static HttpUrl.Builder addBeginDate(Long beginDate, HttpUrl.Builder urlBuilder) {
        DateTime dateTime = null;
        if (beginDate == null) {
            dateTime = DateTime.today(TimeZone.getDefault());
        } else {
            dateTime = DateTime.forInstant(beginDate, TimeZone.getDefault());
        }
        String dateStr = dateTime.format("YYYYMMDD");
        urlBuilder.addQueryParameter(PARAM_BEGIN_DATE, dateStr);
        return urlBuilder;
    }

    private static HttpUrl.Builder addFilterQuery(FilterSettings filterSettings, HttpUrl.Builder urlBuilder) {
        List<String> filterTerms = filterSettings.getNewsDesk();
        if (filterTerms == null || filterTerms.isEmpty()) return urlBuilder;

        StringBuilder query = new StringBuilder();
        query.append("news_desk:(");
        for (int i = 0; i < filterTerms.size(); i++) {

            String term = filterTerms.get(i);
            query.append(term);

            if (filterTerms.size() > 1 && i+1 < filterTerms.size()) {
                query.append(" ");
            }
        }
        query.append(")");
        urlBuilder.addQueryParameter(PARAM_FILTER_QUERY, query.toString());
        return urlBuilder;
    }

}
