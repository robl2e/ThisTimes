package com.robl2e.thistimes.ui.articlelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.robl2e.thistimes.R;
import com.robl2e.thistimes.data.model.article.Article;
import com.robl2e.thistimes.data.remote.GenericResponse;
import com.robl2e.thistimes.data.remote.article.ArticleSearchClientApi;
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
    private Toolbar toolbar;
    private RecyclerView articleListView;
    private ArticleListAdapter listAdapter;
    private MaterialSearchView searchView;
    private FilterSettings filterSettings;
    private FilterSettingsBottomDialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        bindViews();
        setupSearchView();
        initializeList();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArticleSearchClientApi.getInstance().articleSearchRequest(query, filterSettings, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String rawString = response.body().string();
                        Log.d(TAG, rawString);

                        GenericResponse searchResponse = JsonUtils.fromJson(rawString
                                , GenericResponse.class);

                        List<Article> articleList = searchResponse.getResponse().getArticles();
                        if (articleList == null) articleList = Collections.emptyList();

                        List<ArticleItemViewModel> itemViewModels = new ArrayList<>();
                        for (Article article : articleList) {
                            itemViewModels.add(ArticleItemViewModel.convert(article));
                        }
                        listAdapter.setItems(itemViewModels);

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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        articleListView.setLayoutManager(layoutManager);
        articleListView.setAdapter(listAdapter);
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

    private void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }
}
