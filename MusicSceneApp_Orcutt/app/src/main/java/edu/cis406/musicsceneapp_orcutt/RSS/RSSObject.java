package edu.cis406.musicsceneapp_orcutt.RSS;

import android.graphics.Bitmap;

/**
 * Created by Tyler Orcutt on 9/6/2015.
 */
public class RSSObject {
    public String title="";
    public String url="";
    public String imageUrl="";
    public String news="";
    public Bitmap bitmap;

    public RSSObject(){}
    public RSSObject(String title, String url, String imageUrl,String news){
        this.title=title;
        this.url=url;
        this.imageUrl=imageUrl;
        this.news=news;
    }
}
