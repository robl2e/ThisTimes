package com.robl2e.thistimes.ui.articlelist;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.robl2e.thistimes.R;
import com.robl2e.thistimes.data.model.article.Article;
import com.robl2e.thistimes.data.remote.ErrorCodes;
import com.robl2e.thistimes.data.remote.GenericResponse;
import com.robl2e.thistimes.data.remote.article.ArticleSearchClientApi;
import com.robl2e.thistimes.data.remote.article.ArticleSearchResponse;
import com.robl2e.thistimes.ui.common.EndlessRecyclerViewScrollListener;
import com.robl2e.thistimes.ui.common.ItemClickSupport;
import com.robl2e.thistimes.ui.filter.FilterSettings;
import com.robl2e.thistimes.ui.filter.FilterSettingsBottomDialog;
import com.robl2e.thistimes.ui.util.WebUtil;
import com.robl2e.thistimes.util.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ArticleListActivity extends AppCompatActivity {
    private static final String TAG = ArticleListActivity.class.getSimpleName();

    private static final int NUM_COLUMNS = 2;
    private static final int MAX_NUM_PAGES = 100;
    private static final int REQUEST_TIME_DELAY = 500; //milliseconds

    private Toolbar toolbar;
    private RecyclerView articleListView;
    private ArticleListAdapter listAdapter;
    private MaterialSearchView searchView;
    private FilterSettings filterSettings;
    private FilterSettingsBottomDialog filterDialog;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private Handler handler;
    private String currentQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        handler = new Handler();
        bindViews();
        setupSearchView();
        initializeList();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArticleListActivity.this.currentQuery = query;

                ArticleSearchClientApi.getInstance().articleSearchRequest(query, filterSettings, new ArticleSearchCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String rawString = response.body().string();
                        Log.d(TAG, rawString);
                        Log.d(TAG, "code = " + response.code() + " message = " + response.message());

                        if (response.code() == ErrorCodes.API_TOO_MANY_REQUESTS) {
                            showErrorToastMessage(getString(R.string.error_max_num_pages, MAX_NUM_PAGES));
                            return;
                        }

                        GenericResponse searchResponse = JsonUtils.fromJson(rawString
                                , GenericResponse.class);

                        ArticleSearchResponse articleSearchResponse = searchResponse.getResponse();
                        if (articleSearchResponse == null) {
                            showErrorToastMessage(getString(R.string.generic_request_error));
                            return;
                        }

                        List<ArticleItemViewModel> itemViewModels = buildViewModels(articleSearchResponse);
                        listAdapter.setItems(itemViewModels);
                        endlessScrollListener.resetState();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateListAdapter();
                            }
                        });

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_list, menu);
        MenuItem searchActionMenuItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchActionMenuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_filter:
                showFilterDialog();
                return true;
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        if (filterDialog == null) {
            filterDialog = FilterSettingsBottomDialog.newInstance(this, filterSettings, new FilterSettingsBottomDialog.Listener() {
                @Override
                public void onFinishSave(FilterSettings filterSettings) {
                    filterDialog = null;
                    ArticleListActivity.this.filterSettings = filterSettings;
                }

                @Override
                public void onCancel() {
                    filterDialog = null;
                }
            });
            filterDialog.show();
        }
    }

    private void bindViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_articles);
        articleListView = (RecyclerView) findViewById(R.id.list_articles);
    }

    private void initializeList() {
        listAdapter = new ArticleListAdapter(this, new ArrayList<ArticleItemViewModel>());
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        articleListView.setLayoutManager(layoutManager);
        articleListView.setAdapter(listAdapter);

        endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(final int page, final int totalItemsCount, RecyclerView view) {
                if (page > MAX_NUM_PAGES) {
                    showErrorToastMessage(getString(R.string.error_max_num_pages, MAX_NUM_PAGES));
                    return;
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        performLoadMoreArticlesRequest(page, totalItemsCount);
                    }
                }, REQUEST_TIME_DELAY);
            }
        };
        // Adds the scroll listener to RecyclerView
        articleListView.addOnScrollListener(endlessScrollListener);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(articleListView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ArticleItemViewModel viewModel = listAdapter.getItem(position);
                if (viewModel == null) return;

                WebUtil.launchWebUrl(v.getContext(), viewModel.getWebUrl());
            }
        });
    }

    private void performLoadMoreArticlesRequest(int page, final int totalItemsCount) {
        ArticleSearchClientApi.getInstance().articleSearchRequest(currentQuery, filterSettings, page, new ArticleSearchCallback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rawString = response.body().string();
                Log.d(TAG, rawString);
                Log.d(TAG, "code = " + response.code() + " message = " + response.message());

                if (response.code() == ErrorCodes.API_TOO_MANY_REQUESTS) {
                    showErrorToastMessage(getResources().getString(R.string.error_too_many_requests));
                    return;
                }

                GenericResponse searchResponse = JsonUtils.fromJson(rawString
                        , GenericResponse.class);

                ArticleSearchResponse articleSearchResponse = searchResponse.getResponse();
                if (articleSearchResponse == null) {
                    showErrorToastMessage(getString(R.string.generic_request_error));
                    return;
                }

                List<ArticleItemViewModel> itemViewModels = buildViewModels(articleSearchResponse);
                listAdapter.addItems(itemViewModels);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateListAdapterWithNewItems(totalItemsCount);
                    }
                });
            }
        });
    }

    private List<ArticleItemViewModel> buildViewModels(ArticleSearchResponse articleSearchResponse ) {
        List<Article> articleList = articleSearchResponse.getArticles();
        if (articleList == null) articleList = Collections.emptyList();

        List<ArticleItemViewModel> itemViewModels = new ArrayList<>();
        for (Article article : articleList) {
            itemViewModels.add(ArticleItemViewModel.convert(article));
        }
        return itemViewModels;
    }

    private class ArticleSearchCallback implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            showErrorToastMessage(e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    }

    private void showErrorToastMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ArticleListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }

    private void updateListAdapterWithNewItems(int itemsToInsert) {
        int currentItemCount = listAdapter.getItemCount();
        listAdapter.notifyItemRangeInserted(currentItemCount, itemsToInsert);
    }
}
