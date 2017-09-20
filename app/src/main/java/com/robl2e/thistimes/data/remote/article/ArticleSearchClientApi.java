package com.robl2e.thistimes.data.remote.article;

import com.robl2e.thistimes.BuildConfig;

import java.io.IOException;

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
    private static final String BASE_URL = "https://api.nytimes.com";
    private static final String ARTICLE_SEARCH_ENDPOINT = BASE_URL + "/svc/search/v2/articlesearch.json";
    private static final String PARAM_API_KEY = "api-key";
    private static final String PARAM_QUERY = "q";

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

    public void articleSearchRequest(String searchQuery, final Callback responseHandler) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ARTICLE_SEARCH_ENDPOINT).newBuilder();
        urlBuilder.addQueryParameter(PARAM_API_KEY, BuildConfig.ARTICLE_SEARCH_API_KEY);
        urlBuilder.addQueryParameter(PARAM_QUERY, searchQuery);
        String url = urlBuilder.build().toString();

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

}
