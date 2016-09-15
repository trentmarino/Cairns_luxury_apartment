package com.example.trentmarino.cairns_luxury_apartment.pageContent;

import android.util.Log;

import org.json.JSONObject;

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


    public static ArrayList<PageContent> createPage(int numBlocks, String s1 ) {
        ArrayList<PageContent> page = new ArrayList<>();
        for (int i = 1; i <= numBlocks; i++) {
            PageContent pageContent = new PageContent();
            pageContent.setContent(s1);
            Log.i("comon", pageContent.toString());
            page.add(pageContent);
           // page.add(new pageData(s2,s1,s));
        }
        Log.i("page", page.toString());
        return page;
    }
}
