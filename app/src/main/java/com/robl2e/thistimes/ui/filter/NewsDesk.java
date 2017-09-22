package com.robl2e.thistimes.ui.filter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by robl2e on 9/21/17.
 */

public enum NewsDesk {
    ARTS("Arts"),
    FASHION_AND_STYLE("Fashion & Style"),
    SPORTS("Sports"),
    NONE("none");

    private String value;

    NewsDesk(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    public static NewsDesk fromValue(String value) {
        if (TextUtils.isEmpty(value)) return NONE;

        for(NewsDesk item : NewsDesk.values()) {
            if(item.getValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return NONE;
    }
}
