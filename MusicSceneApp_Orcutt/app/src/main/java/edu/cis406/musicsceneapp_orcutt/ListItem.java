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
    private int type;
    public ListItem(String title,String imgUrl, Bitmap bit){
        this.title=title;
        this.ImageUrl=imgUrl;
        image=bit;

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

    public int getType(){
        return type;
    }
    public void setType(int type){
       this.type=type;
    }
    public void setImage(Bitmap img){
        this.image=img;
    }
    public Bitmap getImage(){
        return image;
    }

}
