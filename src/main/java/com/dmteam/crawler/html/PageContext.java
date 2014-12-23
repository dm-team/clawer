package com.dmteam.crawler.html;

import java.io.File;
import java.util.Date;

/**
 * Created by Administrator on 2014/11/27.
 */
public class PageContext {

    public PageContext(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String pageUrl;

    public Date pageDate;

    public boolean isArticle;

    public File desFile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageContext that = (PageContext) o;

        if (!pageUrl.equals(that.pageUrl)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pageUrl.hashCode();
    }
}
