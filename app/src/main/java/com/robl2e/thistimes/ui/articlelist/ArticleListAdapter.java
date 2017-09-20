package com.robl2e.thistimes.ui.articlelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by robl2e on 9/19/17.
 */

class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder>{
    private List<ArticleItemViewModel> articleItems;

    public ArticleListAdapter(Context context, List<ArticleItemViewModel> articleItems) {
        this.articleItems = articleItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return articleItems.size();
    }

    public ArticleItemViewModel getItem(int position) {
        return articleItems.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
