package com.robl2e.thistimes.ui.articlelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robl2e.thistimes.R;

import java.util.List;

/**
 * Created by robl2e on 9/19/17.
 */

class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder>{
    private List<ArticleItemViewModel> articleItems;
    private LayoutInflater inflater;

    public ArticleListAdapter(Context context, List<ArticleItemViewModel> articleItems) {
        this.inflater = LayoutInflater.from(context);
        this.articleItems = articleItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_article, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleItemViewModel viewModel = getItem(position);
        holder.bindItem(viewModel);
    }

    @Override
    public int getItemCount() {
        return articleItems.size();
    }

    public ArticleItemViewModel getItem(int position) {
        return articleItems.get(position);
    }

    public void setItems(List<ArticleItemViewModel> items) {
        this.articleItems = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnailImageView;
        private TextView headlineTextView;
        private TextView summaryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.image_article_thumbnail);
            headlineTextView = (TextView) itemView.findViewById(R.id.text_article_headline);
            summaryTextView = (TextView) itemView.findViewById(R.id.text_article_summary);
        }

        private void bindItem(ArticleItemViewModel viewModel) {
            headlineTextView.setText(viewModel.getHeadline());
            summaryTextView.setText(viewModel.getSummary());

            displayThumbnailImageView(viewModel);
        }

        private void displayThumbnailImageView(ArticleItemViewModel viewModel) {
            if (viewModel.getImageUrl() == null) return;

            Glide.with(itemView.getContext())
                    .load(viewModel.getImageUrl())
                    .into(thumbnailImageView);
        }
    }
}
