package com.robl2e.thistimes.ui.filter;

import java.util.List;

/**
 * Created by robl2e on 9/21/17.
 */

public class FilterSettings {
    private final Long beginDate;
    private final Sort sortOrder;
    private final List<String> newsDesk;

    public FilterSettings(Long beginDate, Sort sortOrder, List<String> newsDesk) {
        this.beginDate = beginDate;
        this.sortOrder = sortOrder;
        this.newsDesk = newsDesk;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public Sort getSortOrder() {
        return sortOrder;
    }

    public List<String> getNewsDesk() {
        return newsDesk;
    }
}
