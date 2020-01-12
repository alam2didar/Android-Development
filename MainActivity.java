package edu.usna.cs.rssfeed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	public ListView newsListView;
	public ArrayAdapter<RssItem> adapter;
	public List<RssItem> rssItemList = new ArrayList<RssItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		newsListView = (ListView) findViewById(R.id.newsListView);
		adapter = new RssItemAdapter(this, android.R.layout.simple_list_item_1, rssItemList);
		newsListView.setAdapter(adapter);

		String siteURL = "http://www.npr.org/rss/rss.php?id=1001";
		new RetrieveFeedTask().execute(siteURL);
	}

	public List<RssItem> parseRSS(URL feedURL)
			throws XmlPullParserException, IOException {
				
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(feedURL.openStream(), null);
		
		int eventType = parser.getEventType();
				
		boolean done = false;
		
		RssItem currentRSSItem= new RssItem();
		
		while (eventType != XmlPullParser.END_DOCUMENT && !done) {
		    String name = null;
		    switch (eventType) {
		    case XmlPullParser.START_TAG:
		    name = parser.getName();
		    if (name.equalsIgnoreCase("item")) {
			// a new item element
			currentRSSItem = new RssItem();
		    } else if (currentRSSItem != null) {
			if (name.equalsIgnoreCase("link")) {
			    currentRSSItem.setLink(parser.nextText());
			} else if (name.equalsIgnoreCase("description")) {
			    currentRSSItem.setDescription(parser.nextText());
			} else if (name.equalsIgnoreCase("pubDate")) {
			    currentRSSItem.setPubDate(parser.nextText());
			} else if (name.equalsIgnoreCase("title")) {
			    currentRSSItem.setTitle(parser.nextText());
			}
		    }
		    break;
		    case XmlPullParser.END_TAG:
			name = parser.getName();
			if (name.equalsIgnoreCase("item") && currentRSSItem != null) {
			    rssItemList.add(currentRSSItem);
			} else if (name.equalsIgnoreCase("channel")) {
			    done = true;
			}
			break;
		    }
		    eventType = parser.next();
		}
		return rssItemList;
	}
	
        //------- AsyncTask ------------//
	class RetrieveFeedTask extends AsyncTask<String, Integer, Integer> {

	    protected Integer doInBackground(String... urls) {
	    	try {
			URL feedURL = new URL(urls[0]);
			rssItemList = parseRSS(feedURL);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	return 0;
	    }

	    protected void onPostExecute(Integer result) {
	        adapter.notifyDataSetChanged();
	    }
	}

}
