/**MyIntentService.java  
 * extends IntentService
 * Author:      Didar Alam, m150084@usna.edu
 * Date:        12 APR 2014
 * Description:  IntentService to retrieve data from internet in the background.
 *
 */

package edu.usna.cs.alam_project;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;



public class MyIntentService extends IntentService {
	
	//Declare variables
	
	final int NOTIFICATION_CODE = 150084;
	public List <RssItem> rssItemList = new ArrayList<RssItem>();
	
	
	//constructor
	public MyIntentService() {
		super("AlamIntent");
	}

	//auto generated method to handle the intent service when triggered
	@Override
	protected void onHandleIntent(Intent intent) {
						
		Log.i("Alam", "Your Intent Service started working");
		//the work to do
		rssItemList = retriveSiteContent(MainActivity.feedUrl);
		
		Log.i("Alam", "Your Intent Service is done working");
		
		//error check
		if(rssItemList==null){
			Log.i("Alam", "Problem Intent");
		}
		
		//save the data in a file
		saveObjectToFile(getBaseContext(), MainActivity.FILENAME, rssItemList);
		
		Log.i("Alam", "file saved");
		
		//broadcast and generate
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("WORK_COMPLETE_ACTION");
        getBaseContext().sendBroadcast(broadcastIntent);
        generarteNotification(this, "Data Updated", "Latest Posts Arrived", NOTIFICATION_CODE);
		
	}

/**This method gets the data (RSS) from the given URL and parse it to list using parseRSS and returns it.
 * 
 * @param String feedUrl
 * @return List<RssItem> List
 * */
	private List<RssItem> retriveSiteContent(String feedUrl) {
		
		URL feedURL;
		List<RssItem> List = new ArrayList<RssItem>();
		
		try {
			feedURL = new URL(feedUrl);
			List = parseRSS(feedURL);
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
			
		return List;
	}
	
	/**Method to build and generate Notification
	 * 
	 *  @param Context context
	 *  @param String title
	 *  @param String message
	 *  @param int notification code
	 *  
	 *  @return void
	 *  */
	public static void  generarteNotification(Context context, String title, String message, int notification_code) {
		int icon = R.drawable.oss;
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		//set all necessary properties
		mBuilder.setSmallIcon(icon)
		        .setContentTitle(title)
		        .setContentText(message)
		        .setAutoCancel(true)
		        .setDefaults(Notification.DEFAULT_ALL)
		        .setTicker("OSS has new Update!");
		
		
		Intent notificationIntent = new Intent (context, Cricket.class); 
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		mBuilder.setContentIntent(intent);
		notificationManager.notify(notification_code, mBuilder.build());
	}

	/**This method parses the data given a URL
	 * 
	 * @param URL feedURL
	 * @return List<RssItem>
	 * */
	public List<RssItem> parseRSS(URL feedURL)
			throws XmlPullParserException, IOException {
		
		List<RssItem> list = new ArrayList<RssItem>();
				
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
			    list.add(currentRSSItem);
			} else if (name.equalsIgnoreCase("channel")) {
			    done = true;
			}
			break;
		    }
		    eventType = parser.next();
		}
		return list;
	}
	
	/** This sample method saves a Java Object to file.
	 * 
	 * @param context base context
	 * @param String fileName FILENAME
	 * @param List  list <RssItem>
	 * 
	 * 
	 * @return void
	 * */
	public static void saveObjectToFile(Context context, String fileName, List<RssItem> list) {
		 
	    try {
	        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(list);//save the object
	        oos.close();

	    } catch (FileNotFoundException e) {
	        Log.e("LOG_TAG", "saveObjectToFile FileNotFoundException: " + e.getMessage());
	    } catch (IOException e) {
	        Log.e("LOG_TAG", "saveObjectToFile IOException: " + e.getMessage());
	    } catch (Exception e) {
	        Log.e("LOG_TAG", "saveObjectToFile Exception: " + e.getMessage());
	    }
	}

}
