package edu.cis406.musicsceneapp_orcutt.RSS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.XMLFormatter;

import javax.net.ssl.HttpsURLConnection;

import edu.cis406.musicsceneapp_orcutt.MainActivity;

/**
 * Created by Tyler Orcutt on 9/6/2015.
 */
public class RSSParser {
    private ArrayList<RSSObject>  newsStories;
    private String feedURL="";
    public boolean threadComplete=false;
    private boolean threadError=false;
    private XmlPullParserFactory xml;
    private MainActivity main;
    public RSSParser(MainActivity main, String url){
        newsStories = new ArrayList<RSSObject>();
        feedURL=url;
        this.main=main;

    }

public void printContent(){
    for(int i=0;i<newsStories.size();i++){
        Log.i("Parser",newsStories.get(i).title + "\n");
    }
}
    public ArrayList<RSSObject> getRSSObjects(){
        return newsStories;
    }
    public ArrayList<String> getTitleArray(){
        ArrayList<String> listitems = new ArrayList<String>();
        for(int i=0;i<newsStories.size();i++){
           // Log.i("Parser",newsStories.get(i).title + "\n");
            listitems.add(newsStories.get(i).title);
        }

        listitems.add("Articles from rollingstone.com");
        return  listitems;
    }
    public void DownloadRSS() {
        Log.i("Parser","DOWNLOADING RSS");
        if (feedURL.equalsIgnoreCase("")) {
            threadComplete=true;
            threadError=true;
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadComplete=false;
                threadError=false;

                try{
                   Log.i("PARSER",feedURL);
                   URL url = new URL(feedURL);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                   connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");
                   connection.setDoInput(true);
                    connection.setChunkedStreamingMode(7000);
                 //  connection.setChunkedStreamingMode(70000);

                    connection.connect();

                    InputStream input = connection.getInputStream();
                       xml = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = xml.newPullParser();
                      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(input, null);
                        int event = parser.getEventType();
                        String txt = "";
                        String title = "";
                        String Surl = "";
                    String desc="";
                    String imgUrl="";
                        RSSObject obj = null;
                        Log.i("Parser", "PARSING XML");

                    while ((event = parser.next()) != parser.END_DOCUMENT) {
                            String tag = parser.getName();
                            if (event == XmlPullParser.START_TAG) {

                                continue;
                            }

                            if (event == XmlPullParser.TEXT) {
                                txt = parser.getText();

                                continue;
                            }
                            if (event == XmlPullParser.END_TAG) {
                                if (tag.equalsIgnoreCase("title")) {
                                    title = txt;
                                    continue;
                                }
                                if (tag.equalsIgnoreCase("description")) {
                                    desc = txt;
                                    continue;
                                }
                                if (tag.equalsIgnoreCase("link")) {
                                    // if(title.contains("RollingStone.com")){
                                    //  continue;
                                    //  }
                                    Surl = txt;

                                }
                                if (tag.equalsIgnoreCase("enclosure")) {
                                    //Log.i("parser",parser.getAttributeValue(null,"url"));
                                    imgUrl=parser.getAttributeValue(null,"url");
                                    obj = new RSSObject();
                                    obj.title = title;
                                    obj.url = Surl;
                                    obj.imageUrl=imgUrl;
                                    obj.description =desc;
                                    newsStories.add(obj);
                                    obj = null;

                                }


                            }

                        }//
                    input.close();//*/

                // connection.disconnect();
                }catch (Exception e){
                //    threadComplete=true;
                    threadError=true;
                    Log.i("Parser", "RSS Download FAIL");
             //      Log.i("Parser",e.getMessage().toString());
                }
                //threadComplete=true;
                threadError=false;
     //  printContent();
             // DownloadImages();
                threadComplete=true;
            }
        }).start();


    }
  public  void DownloadImages() {
      threadComplete=false;
      new Thread(new Runnable() {
          @Override
          public void run() {
              threadComplete=false;
              threadError=false;
Log.i("Parser","Downloading bitmaps " + newsStories.size());
                for (int i = 0; i < newsStories.size(); i++) {
                    try {
                        Bitmap bit = null;
                        Log.i("Parser","Downloading bitmap:" + newsStories.get(i).imageUrl);
                        URL url = new URL(newsStories.get(i).imageUrl);
                       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                        connection.setReadTimeout(10000);
                        connection.setConnectTimeout(15000);
                        connection.setRequestMethod("GET");
                      //  connection.setDoInput(true);
                        connection.setChunkedStreamingMode(7000);

                       connection.connect();

                        InputStream input = connection.getInputStream();
                        bit=BitmapFactory.decodeStream(input);
                        newsStories.get(i).bitmap = bit;
                        input.close();
                    } catch (Exception e)

                    {
                        Log.i("parser", "Failed to download image " +newsStories.get(i).imageUrl );
                    }


                }
               threadComplete=true;

    }}).start();
  }


}

