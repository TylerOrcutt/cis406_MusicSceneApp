package edu.cis406.musicsceneapp_orcutt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        parser =new RSSParser(this,feedUrl);
        parser.DownloadRSS();
      while(!parser.threadComplete){}
        parser.DownloadImages();
        while(!parser.threadComplete){}
        ArrayList<String> listitems = new ArrayList<String>();
        listitems.add("tst");
        UpdateList();


    }
    public void UpdateList(){
       // ArrayList<String> listitems = new ArrayList<String>();
      //  listitems.add("tst");

        Log.i("MAIN","UPDATING LIST");
        ListView listView = (ListView)findViewById(R.id.listViewMain);
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        ArrayList<RSSObject> rssObjects = parser.getRSSObjects();

                for(int i=0;i<rssObjects.size();i++){
                    items.add(new ListItem(rssObjects.get(i).title,rssObjects.get(i).imageUrl,rssObjects.get(i).bitmap));

                }



        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.listViewTextView);
        ListItemAdapter adapter = new ListItemAdapter(this,R.layout.list_item,items);
         listView.setAdapter(adapter);


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
