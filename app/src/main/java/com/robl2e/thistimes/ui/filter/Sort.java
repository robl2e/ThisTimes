package com.robl2e.thistimes.ui.filter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by robl2e on 9/21/17.
 */

public enum Sort {
    NEWEST("newest"),
    OLDEST("oldest");

    private String value;

    Sort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    public static Sort fromValue(String value) {
        if (TextUtils.isEmpty(value)) return NEWEST;

        for(Sort item : Sort.values()) {
            if(item.getValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return NEWEST;
    }
}
