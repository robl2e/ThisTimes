package com.robl2e.thistimes.ui.articlelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robl2e.thistimes.R;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
        View itemView = inflater.inflate(viewType, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleItemViewModel viewModel = getItem(position);
        holder.bindItem(viewModel);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.getThumbnailImageView() != null) {
            Glide.clear(holder.getThumbnailImageView());
        }
    }

    @Override
    public int getItemViewType(int position) {
        // ViewType is the layoutIds themselves

        ArticleItemViewModel viewModel = articleItems.get(position);
        if (viewModel == null) return R.layout.item_article;

        if (TextUtils.isEmpty(viewModel.getImageUrl())) {
            return R.layout.item_article_text_only;
        }

        return R.layout.item_article;
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

    public void addItems(List<ArticleItemViewModel> items) {
        if (this.articleItems == null) return;
        this.articleItems.addAll(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnailImageView;
        private TextView headlineTextView;
        private TextView publishDateTextView;
        private TextView summaryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.image_article_thumbnail);
            headlineTextView = (TextView) itemView.findViewById(R.id.text_article_headline);
            publishDateTextView = (TextView) itemView.findViewById(R.id.text_article_publish_date);
            summaryTextView = (TextView) itemView.findViewById(R.id.text_article_summary);
        }

        ImageView getThumbnailImageView() {
            return thumbnailImageView;
        }

        private void bindItem(ArticleItemViewModel viewModel) {
            headlineTextView.setText(viewModel.getHeadline());
            displayPublishDate(viewModel.getPublishedDateWithoutTime());

            summaryTextView.setText(viewModel.getSummary());

            displayThumbnailImageView(viewModel);
        }

        private void displayPublishDate(String publishDate) {
            if (TextUtils.isEmpty(publishDate)) {
                publishDateTextView.setVisibility(View.GONE);
                return;
            } else {
                publishDateTextView.setVisibility(View.VISIBLE);
            }

            if (!DateTime.isParseable(publishDate)) {
                publishDateTextView.setVisibility(View.GONE);
                return;
            }

            DateTime dateTime = new DateTime(publishDate);
            String formattedString = dateTime.format("MMM D, YYYY", Locale.getDefault());
            publishDateTextView.setText(formattedString);
        }

        private void displayThumbnailImageView(ArticleItemViewModel viewModel) {
            if (thumbnailImageView == null) return;
            if (viewModel.getImageUrl() == null) return;

            int radius = itemView.getResources().getDimensionPixelSize(R.dimen.image_corner_radius);

            Glide.with(itemView.getContext())
                    .load(viewModel.getImageUrl())
                    .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(), radius, 0))
                    .into(thumbnailImageView);
        }
    }
}
