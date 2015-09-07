package edu.cis406.musicsceneapp_orcutt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.cis406.musicsceneapp_orcutt.RSS.RSSObject;
import edu.cis406.musicsceneapp_orcutt.RSS.RSSParser;

public class MainActivity extends AppCompatActivity {
    private final String feedUrl= "http://rollingstone.com/news.rss";
    RSSParser parser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager con = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = con.getActiveNetworkInfo();
        if(network ==null ){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Internet connection is required for this app.");
            alert.setTitle("Error");
            alert.setPositiveButton("Ok",null);
            alert.create().show();
           // this.onDestroy();
            return;
        }
if(network.isConnected()) {
    parser = new RSSParser(this, feedUrl);
    parser.DownloadRSS();//too lazy to do this async
    while (!parser.threadComplete) {
    }
    parser.DownloadImages();//^
    while (!parser.threadComplete) {
    }

    UpdateList();
}

    }
    public void UpdateList(){
          Log.i("MAIN","UPDATING LIST");
        ListView listView = (ListView)findViewById(R.id.listViewMain);
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        ArrayList<RSSObject> rssObjects = parser.getRSSObjects();

                for(int i=0;i<rssObjects.size();i++){
                    items.add(new ListItem(rssObjects.get(i).title,rssObjects.get(i).imageUrl,rssObjects.get(i).bitmap,rssObjects.get(i).description,rssObjects.get(i).url));

                }


        final ListItemAdapter adapter = new ListItemAdapter(this,R.layout.list_item,items);
         listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nv= new Intent(view.getContext(),ArticleView.class);
                nv.putExtra("title",adapter.getViewItem(position).getTitle());
                nv.putExtra("description", adapter.getViewItem(position).getDescription());
                nv.putExtra("URL", adapter.getViewItem(position).getLinkUrl());
                startActivity(nv);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
