package edu.cis406.musicsceneapp_orcutt;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Tyler Orcutt on 9/6/2015.
 */
public class ListItem {
    private String title,ImageUrl;
    private Bitmap image=null;

    private String description="";
    private String linkUrl="";
    public ListItem(String title,String imgUrl, Bitmap bit, String desc, String link){
        this.title=title;
        this.ImageUrl=imgUrl;
        image=bit;
        this.description=desc;
        this.linkUrl=link;

    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }
    public String getImageUrl(){
        return ImageUrl;
    }


    public void setImage(Bitmap img){
        this.image=img;
    }
    public Bitmap getImage(){
        return image;
    }
public String getDescription(){
    return  description;
}
    public String getLinkUrl(){
        return linkUrl;
    }
}
