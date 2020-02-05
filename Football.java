/*Honor Statement*/
/* I certify that this assignment was completed individually, with no outside assistance.  
 *  I further acknowledge that plagiarism and/or unauthorized collaboration directly conflicts with the values expected of future Naval Officers, and is a violation of my personal integrity.
 *  I understand that any suspected honor violations will be investigated and processed in accordance with USNA Instruction 1610.3H.
 *  *  Disclaimer: ******NOTE*****: All the codes and knowledge regarding fragment, Viewpager, MyFrgmentSlide.java, ScreenSlidePagerAdapter and ZoomOutPageTransformer class are referenced from and with the help of
 *  Android Developer web page and open source project -- URL: http://developer.android.com/training/animation/screen-slide.html#pagetransformer 
 *  Copyright 2012 The Android Open Source Project  ******* */

  
/**Football.java  
 * extends FragmentActivity
 * Implements OnItemClickListener
 * Author:      Didar Alam, m150084@usna.edu
 * Date:        30 APR 2014
 * Description: an application that fetches a news feed from sports channel rss feed 
 * retrieving updates (via RSS) both manually(options menu "Update FEEDBACK") and automatically(Using Alarm to trigger intent service to update the page on 1 minute interval).
 *
 */

package edu.usna.cs.alam_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Football extends FragmentActivity implements OnItemClickListener{

     // Global Variables         
	private ArrayAdapter<RssItem> adapter;  //customized array adapter
	public List<RssItem> rssItemList = new ArrayList<RssItem>(); //List with RssItem object
	private AlarmManager alarmManager;     //Alarm and Intents
	private Intent intent;
	private PendingIntent pendingIntent;
	
	
	private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager myPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter myPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slider);
		// Instantiate a ViewPager and a PagerAdapter.
		myPager = (ViewPager) findViewById(R.id.pager);
		myPager.setPageTransformer(true, new ZoomOutPageTransformer());
		myPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		myPager.setAdapter(myPagerAdapter);

		myPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
		    @Override
		    public void onPageSelected(int position) {
		    	Log.e("ALAM", "In Mpage for every page change"+position);
		    	if(position == 0){
		    		
		    		MainActivity.feedUrl = "http://sports.espn.go.com/espn/rss/nfl/news";
		    		MainActivity.FILENAME = "nfl.txt";
		    		MainActivity.header = "NFL Headlines";
		    		File file = new File(MainActivity.FILENAME);
		    		if(file.exists()){
		    		//do nothing	
		    			
		    		}else{
		    			startService(new Intent(getBaseContext(), MyIntentService.class));	
		    		}
		    			    		
		    	}else if(position == 1){
		    		
		    		MainActivity.feedUrl = "http://sports.espn.go.com/espn/rss/ncf/news";
		    		MainActivity.FILENAME = "collegefball.txt";
		    		MainActivity.header = "College Football";
		    		File file = new File(MainActivity.FILENAME);
		    		if(file.exists()){
		    		//do nothing	
		    			
		    		}else{
		    			startService(new Intent(getBaseContext(), MyIntentService.class));	
		    		}
		    	}else if(position == 2){
		    		
		    		MainActivity.feedUrl = "http://www.cbssports.com/partners/feeds/rss/nfl_news";
		    		MainActivity.FILENAME = "nflCbss.txt";
		    		MainActivity.header = "NFL CBSSports";
		    		File file = new File(MainActivity.FILENAME);
		    		if(file.exists()){
		    		//do nothing	
		    			
		    		}else{
		    			startService(new Intent(getBaseContext(), MyIntentService.class));	
		    		}
		    		
		    	}else if(position == 3){
		    		
		    		MainActivity.feedUrl = "http://www.cbssports.com/partners/feeds/rss/nfl_nfldr_rapidreports";
		    		MainActivity.FILENAME = "nflDraft.txt";
		    		MainActivity.header = "NFL Draft";
		    		File file = new File(MainActivity.FILENAME);
		    		
		    		if(file.exists()){
		    		//do nothing	
		    			
		    		}else{
		    			startService(new Intent(getBaseContext(), MyIntentService.class));	
		    		}
		    		
		    	}else if(position == 4){
		    		
		    		MainActivity.feedUrl = "http://www.cbssports.com/partners/feeds/rss/nflplayerupdates";
		    		MainActivity.FILENAME = "nflPlupdates.txt";
		    		MainActivity.header = "NFL Player Updates";
		    		File file = new File(MainActivity.FILENAME);
		    		if(file.exists()){
		    		//do nothing	
		    			
		    		}else{
		    			startService(new Intent(getBaseContext(), MyIntentService.class));	
		    		}
		    		
		    	}
		       
		        invalidateOptionsMenu();
		    }
		});


		//check connectivity
		if(!(isConnectedToInternet(this))){
			
			Toast.makeText(getBaseContext(), "Not Connected to Network. Closing the app...!",Toast.LENGTH_LONG).show();
			
			finish();
		}
				
		//Alarm initiation	
		alarmManager = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
		intent = new Intent(getBaseContext(), AlarmReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
		 
		//Repeating every after 1 minute, -1 : indicates time in the past causing the alarm going of immediately	
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, -1, 30*10000, pendingIntent );
		Log.i("ALAM", "Alarm set!" );
		

	
	    // create a new intent filter 
	IntentFilter intentFilter = new IntentFilter();
       intentFilter.addAction("WORK_COMPLETE_ACTION");

        // register the broadcast receiver to listen using 
      // the intent filter defined above
      registerReceiver(intentReceiver, intentFilter);
                    

      		
      	 
	}
	
/**This Method works as the receiver and completes the desired action on Receive the Intent 
 * @param none
 * @return void
 * 
 * */	 	
  	
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	rssItemList.clear();
        	rssItemList.addAll(getObjectFromFile(getBaseContext(), MainActivity.FILENAME));
        	adapter = new RssItemAdapter( getBaseContext(), android.R.layout.simple_list_item_1, rssItemList);
        	adapter.notifyDataSetChanged();
            Log.i("ALAM", "Updated!" );
        }
    };
    
/**This Method is used to start the service using the Appropriate class
 * @param View view
 * @return void
 * */
    
	public void startService(View view) {
        // start an IntentService
        startService(new Intent(getBaseContext(), MyIntentService.class)); 	
    }
	
	/**This method is used to unregister the receiver upon closing the app
	 * @param none
	 * @return void 
	 * */
	@Override
    public void onDestroy() {
    	super.onDestroy();
    	// unregister the broadcast receiver
    	unregisterReceiver(intentReceiver);
    finish();
	}
	
	/**This is method reads data from the given filename and returns a List.
	 * 
	 * @param Context the base context
	 * @return list List<RssItem>
	 * */
	
	public static List<RssItem> getObjectFromFile(Context context, String filename) {

	    try {      
	        FileInputStream fis = context.openFileInput(filename);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	       
	   
	        //create a list
	        List <RssItem> list = new ArrayList<RssItem>();
	        
	        //read to list
	        list = (ArrayList<RssItem>) ois.readObject();
	        
	        ois.close();
	               
	      //return
	        return list;

	    } catch (FileNotFoundException e) {
	    	//if file not found then default
	    	List <RssItem> list = new ArrayList<RssItem>();
	    	RssItem item = new RssItem();
	    	list.add(item);
	        Log.e("LOG_TAG", "getObjectFromFile FileNotFoundException: " + e.getMessage());
	        return list;
	    } catch (IOException e) {
	    	List <RssItem> list = new ArrayList<RssItem>();
	    	RssItem item = new RssItem();
	    	list.add(item);

	        Log.e("LOG_TAG", "getObjectFromFile IOException: " + e.getMessage());
	        return list;
	    } catch (ClassNotFoundException e) {
	        Log.e("LOG_TAG", "getObjectFromFile ClassNotFoundException: " + e.getMessage());
	        return null;       
	    } catch (Exception e) {// Catch exception if any
	    	//if file not found then default
	    	List <RssItem> list = new ArrayList<RssItem>();
	    	RssItem item = new RssItem();
	    	list.add(item);
	        Log.e("LOG_TAG", "getBookmarksFromFile Exception: " + e.getMessage());
	        return list;
	    }
	}

	
	// generated method on implementing the OnItemClickListener to launch a web activity on click items
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		
		RssItem item = (RssItem)parent.getItemAtPosition(pos);
		String url = item.getLink();
		Intent launchWeb =  new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(launchWeb);		
		
	}
	
	/***this method check to ensure that the device has an active network connection before attempting to initiate 
	 * network communication
	 * 
	 * @param Context context
	 * 
	 * @return boolean true or false
	 * */
	public static boolean isConnectedToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		Log.e("ALAM", "NetworkUtil - Not Connected to Network");
		return false;
	}

	
	
	/** 
	 * This method is for creating the options Menu.
	 *
	 * @param  Menu     menu
	 * @return boolean  true if menu is present
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

	    menu.findItem(R.id.action_previous).setEnabled(myPager.getCurrentItem() > 0);

	    // Add either a "next" or "finish" button to the action bar, depending on which page
	    // is currently selected.
	    MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
	            (myPager.getCurrentItem() == myPagerAdapter.getCount() - 1)
	                    ? R.string.action_finish
	                    : R.string.action_next);
	    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    return true;
	}

	
	/** 
	 * This method performs the action when menu item/button clicked.
     *
	 * @param  MenuItem  item
	 * @return boolean   true if task is completed
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	         case R.id.action_previous:
	            // Go to the previous step in the wizard. If there is no previous step,
	            // setCurrentItem will do nothing.
	            myPager.setCurrentItem(myPager.getCurrentItem() - 1);
	            return true;

	        case R.id.action_next:
	            // Advance to the next step in the wizard. If there is no next step, setCurrentItem
	            // will do nothing.
	            myPager.setCurrentItem(myPager.getCurrentItem() + 1);
	            return true;
	            
	        case R.id.Update:
	        	startService(new Intent(getBaseContext(), MyIntentService.class));	
	        	rssItemList.clear();
     	        rssItemList.addAll(getObjectFromFile(getBaseContext(), MainActivity.FILENAME));
     	        adapter = new RssItemAdapter( getBaseContext(), android.R.layout.simple_list_item_1, rssItemList);
     	        adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Updated!",Toast.LENGTH_SHORT).show();
            return true;
	        	
	        	
	    }

	    return super.onOptionsItemSelected(item);
	}


	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	    public ScreenSlidePagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	  
		@Override
	    public Fragment getItem(int position) {
			return new MyFragmentSlider ();
	    }

	    @Override
	    public int getCount() {
	        return NUM_PAGES;
	    }
	}

	@Override
	public void onBackPressed() {
	    if (myPager.getCurrentItem() == 0) {
	        // If the user is currently looking at the first step, allow the system to handle the
	        // Back button. This calls finish() on this activity and pops the back stack.
	        super.onBackPressed();
	    } else {
	        // Otherwise, select the previous step.
	        myPager.setCurrentItem(myPager.getCurrentItem() - 1);
	    }
	}

}

	

