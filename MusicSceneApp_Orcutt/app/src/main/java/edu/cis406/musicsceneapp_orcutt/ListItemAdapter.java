package edu.cis406.musicsceneapp_orcutt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Tyler Orcutt on 9/6/2015.
 */
public class ListItemAdapter extends ArrayAdapter {
    private List<ListItem> items;


    public ListItemAdapter(Context context,int id, List<ListItem> objects){
        super(context,id,objects);
        items=objects;

    }
    public ListItem getViewItem(int pos){
        return items.get(pos);
    }
    @Override
    public int getViewTypeCount(){
        return items.size();
    }
    @Override
    public View getView(int pos, View convertView,ViewGroup parent){
        ListItem citem = items.get(pos);
        int type=getItemViewType(pos);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
        TextView txt = (TextView)convertView.findViewById(R.id.listViewTextView);
         txt.setText(citem.getTitle());
        ImageView img = (ImageView) convertView.findViewById(R.id.listViewImageView);
        img.setImageBitmap(citem.getImage());



        return convertView;

    }
}
