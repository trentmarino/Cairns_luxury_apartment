package com.example.trentmarino.cairns_luxury_apartment.pageContent;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by trentmarino on 19/05/16.
 */
public class pageData {
    private String pageType,pageContent,pageOrder;

    public pageData(String pageType, String pageContent, String pageOrder){
        this.pageType = pageType;
        this.pageContent = pageContent;
        this.pageOrder = pageOrder;
    }

    public String getPageContent() {
        return pageContent;
    }

    public String getPageOrder() {
        return pageOrder;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public void setPageOrder(String pageOrder) {
        this.pageOrder = pageOrder;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public static ArrayList<PageContent> createPage(int numBlocks, String s1) {
        ArrayList<PageContent> page = new ArrayList<>();
        for (int i = 1; i <= numBlocks; i++) {
            PageContent pageContent = new PageContent();
            pageContent.setContent(s1);
            page.add(pageContent);
           // page.add(new pageData(s2,s1,s));
        }
        Log.i("page", page.toString());
        return page;
    }
}
