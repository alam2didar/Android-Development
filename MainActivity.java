/*Honor Statement*/
/* I certify that this assignment was completed individually, with no outside assistance.  
 *  I further acknowledge that plagiarism and/or unauthorized collaboration directly conflicts with the values expected of future Naval Officers, and is a violation of my personal integrity.
 *  I understand that any suspected honor violations will be investigated and processed in accordance with USNA Instruction 1610.3H.
 *  Disclaimer: ******NOTE*****: All the codes and knowledge regarding fragment, Viewpager, MyFrgmentSlide.java, ScreenSlidePagerAdapter and ZoomOutPageTransformer class are referenced from and with the help of
 *  Android Developer web page and open source project -- URL: http://developer.android.com/training/animation/screen-slide.html#pagetransformer 
 *  Copyright 2012 The Android Open Source Project  ******* */

/**MainActivity.java
 * extends Activity 
 * Implements OnItemClickListener
 * Author: MIDN Alam
 * Date: 30 APR 2014
 * This activity populates a list using a custom List Adapter and launches new activity with fragments containing different sports news collected from sports channel 
 * via RSS feed.*/

package edu.usna.cs.alam_project;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemClickListener {
	
	//static variable changes the value for different fragments and activity
	public static String feedUrl;
	public static String header;
	public static String FILENAME;
	
   //populate the list of titles
	public static final String[] titles = new String[] { "Cricket",
    "BasketBall", "FootBall", "Misc. Sports" };
	//populate the list of description
	public static final String[] descriptions = new String[] {
    "Cricket Scores/Headlines",
    "BasketBall Scores/Headlines",
    "FootBall Scores/Headlines",
    "Top Headlines, Golf, Tennis, Soccer, Olympic" };

	public static final Integer[] images = { R.drawable.cricket, R.drawable.bball, R.drawable.football, R.drawable.misc };

	private ListView listView;
	private List<ListItem> listItems;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//populate the list with pre-declared variables
	listItems = new ArrayList<ListItem>();
	for (int i = 0; i < titles.length; i++) {
		ListItem item = new ListItem(images[i], titles[i], descriptions[i]);
		listItems.add(item);
	}

	listView = (ListView) findViewById(R.id.sportsList);
	//use the list adapter 
	ListItemAdapter adapter = new ListItemAdapter(this, R.layout.listitem_layout, listItems);
	//set adapter
	listView.setAdapter(adapter);
	listView.setOnItemClickListener(this);

}


	/**Auto generated OnItemClick Listener*/
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Toast toast = Toast.makeText(getBaseContext(), ""+listItems.get(position),Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		if(position == 0){
			MainActivity.FILENAME = "globalCricket.txt";
			startActivity(new Intent(getBaseContext(), Cricket.class));
		
		}else if(position == 1){
			MainActivity.FILENAME = "collegeBball.txt";
			startActivity(new Intent(getBaseContext(), Basketball.class));
		}else if(position == 2){
			MainActivity.FILENAME = "nfl.txt";
			startActivity(new Intent(getBaseContext(), Football.class));
		}else if(position == 3){
			MainActivity.FILENAME = "topHeadlines.txt";
			startActivity(new Intent(getBaseContext(), Misc.class));
		}

	
	}
}
