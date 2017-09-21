package com.robl2e.thistimes.ui.articlelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.robl2e.thistimes.R;
import com.robl2e.thistimes.data.remote.GenericResponse;
import com.robl2e.thistimes.data.remote.article.ArticleSearchClientApi;
import com.robl2e.thistimes.ui.common.ItemClickSupport;
import com.robl2e.thistimes.util.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ArticleListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView articleListView;
    private ArticleListAdapter listAdapter;
    private MaterialSearchView searchView;

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
                ArticleSearchClientApi.getInstance().articleSearchRequest(query, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String rawString = response.body().string();
                        System.out.println(rawString);

                        GenericResponse searchResponse = JsonUtils.fromJson(rawString
                                , GenericResponse.class);
                        System.out.println(searchResponse.getResponse().toString());
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
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_articles);
        articleListView = (RecyclerView) findViewById(R.id.list_articles);
    }

    private void initializeList() {
        listAdapter = new ArticleListAdapter(this, new ArrayList<ArticleItemViewModel>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        articleListView.setLayoutManager(layoutManager);
        articleListView.setAdapter(listAdapter);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(articleListView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ArticleItemViewModel viewModel = listAdapter.getItem(position);
                if (viewModel == null) return;
            }
        });
    }

    private void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }
}
