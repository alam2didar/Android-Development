/**
 *   Disclaimer: ******NOTE*****: All the codes and knowledge regarding fragment, Viewpager, MyFrgmentSlide.java, ScreenSlidePagerAdapter and ZoomOutPageTransformer class are referenced from and with the help of
 *  Android Developer web page and open source project -- URL: http://developer.android.com/training/animation/screen-slide.html#pagetransformer 
 *  Copyright 2012 The Android Open Source Project  ******* 
 *  
 *  MyFragmentSlider.java
 *  extends Fragment
 * Author: MIDN Alam
 * Date: 30 APR 2014
 * This activity populates five fragments pages and populates with different  sports news collected from sports channel 
 * via RSS feed on each fragment.*/


package edu.usna.cs.alam_project;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MyFragmentSlider extends Fragment {
	
	public static final String ARG_PAGE = "page";
	
	private int mPageNumber;
	
	
	private ListView newsListView;  //a list view to present the data 
	private TextView header;
	private ArrayAdapter<RssItem> adapter;  //customized array adapter
	public List<RssItem> rssItemList = new ArrayList<RssItem>(); //List with RssItem object
	
	
	
	public static MyFragmentSlider create(int pageNumber) {
		MyFragmentSlider fragment = new MyFragmentSlider();
        Bundle args = new Bundle();
        
        fragment.setArguments(args);
        Log.e("ALAM", "Page:"+pageNumber);
        if(pageNumber == 0){
    		
    		MainActivity.feedUrl = "http://www.espncricinfo.com/rss/content/story/feeds/0.xml";
    		MainActivity.FILENAME = "globalCricket.txt";
    		MainActivity.header = "Global Cricket News";
    		
        }if(pageNumber == 1){
    		
    		MainActivity.feedUrl = "http://static.cricinfo.com/rss/livescores.xml";
    		MainActivity.FILENAME = "liveCricket.txt";
    		MainActivity.header = "LiveCricket";
        }if(pageNumber == 2){
    		
    		MainActivity.feedUrl = "https://www.facebook.com/feeds/page.php?id=94274618090&format=rss20";
    		MainActivity.FILENAME = "ausCricket.txt";
    		MainActivity.header = "Cricket Australia";
    		
        }if(pageNumber == 3){
    		
    		MainActivity.feedUrl = "http://www.espncricinfo.com/rss/content/story/feeds/25.xml";
    		MainActivity.FILENAME = "banCricket.txt";
    		MainActivity.header = "Cricket Bangladesh";
    		
        }if(pageNumber == 4){
    		
        	MainActivity.feedUrl = "http://www.espncricinfo.com/rss/content/story/feeds/6.xml";
    		MainActivity.FILENAME = "indCricket.txt";
    		MainActivity.header = "";
    		
    		
        }
                
        return fragment;
    }
	
	
	public  MyFragmentSlider() {
    }

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //mPageNumber = getArguments().getInt(ARG_PAGE);
	}
	
	
	/**This method gets call on create the fragment Auto generated method for fragments*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_main, container, false);
        
      //get the listView
      		newsListView = (ListView) rootView.findViewById(R.id.sportsList);
      		header = (TextView) rootView.findViewById(R.id.welcome);
      		header.setText(MainActivity.header);
      		
      		 //read file and sleep to avoid race conditions and Null pointer exception
      		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		     		
      		
      		rssItemList.addAll(getObjectFromFile(getActivity(), MainActivity.FILENAME));
        	 
            Toast.makeText(getActivity(), "Updated!",
                     Toast.LENGTH_LONG).show();
      		
          	//rssItemList = (getObjectFromFile(getActivity(), MainActivity.FILENAME));
            
            //if it is still NULL
            if(rssItemList==null){
          		Log.e("Alam", "List empty");
          		RssItem item = new RssItem();
          		rssItemList.add(item);
        	}
          	
         
          	//set the adapter and OnItemClickListener
          	adapter = new RssItemAdapter( getActivity(), android.R.layout.simple_list_item_1, rssItemList);
          	newsListView.setAdapter(adapter);
          	newsListView.setOnItemClickListener((OnItemClickListener) getActivity());
          	adapter.notifyDataSetChanged();

        return rootView;
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
	        Log.e("LOG_TAG", "getBookmarksFromFile Exception: " + e.getMessage());
	        return null;
	    }
	}

	
	
}