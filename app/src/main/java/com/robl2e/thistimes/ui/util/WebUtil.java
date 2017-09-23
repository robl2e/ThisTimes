package com.robl2e.thistimes.ui.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.robl2e.thistimes.R;

/**
 * Created by robl2e on 9/23/17.
 */

public class WebUtil {
    private static final int REQUEST_CODE_SHARE = 100;

    public static void launchWebUrl(Context context, String url) {
        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        // set toolbar color
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));

        // add share action to menu list
        builder.addDefaultShareMenuItem();
        builder = addShareAction(context, url, builder);

        // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
        // and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    private static CustomTabsIntent.Builder addShareAction(Context context, String url
            , CustomTabsIntent.Builder builder) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources()
                , R.drawable.ic_share);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                REQUEST_CODE_SHARE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Map the bitmap, text, and pending intent to this icon
        // Set tint to be true so it matches the toolbar color
        builder.setActionButton(bitmap, context.getString(R.string.share_link), pendingIntent, true);
        return builder;
    }
}
