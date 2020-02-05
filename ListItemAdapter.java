/**ListItemAdapter.java
 * extends ArrayAdapter<ListItem>
 * Author: MIDN Alam
 * Date: 30 APR 2014
 * This activity populates a ListItem list using a custom ListItemAdapter */


package edu.usna.cs.alam_project;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListItemAdapter extends ArrayAdapter<ListItem> {
	 
	    Context context;
	 
	    public ListItemAdapter(Context context, int id, List<ListItem> items) {
	        super(context, id, items);
	        this.context = context;
	    }
	     
	    //Just to hold the view items
	    class ViewHolder {
	        ImageView imageView;
	        TextView title;
	        TextView description;
	    }
	    //////
	    ///
	    public View getView(int position, View finalView, ViewGroup parent) { 
	    	
	    	//create a ViewHolder
	    	ViewHolder myViewContainer = null;
	    	//get list position
	        ListItem listItem = getItem(position);
	         
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        //In case the view is NULL
	        if (finalView == null) {
	        	//use our list_item Layout
	            finalView = mInflater.inflate(R.layout.listitem_layout, null);
	            myViewContainer = new ViewHolder();
	            myViewContainer.description = (TextView) finalView.findViewById(R.id.desc);
	            myViewContainer.title = (TextView) finalView.findViewById(R.id.title);
	            myViewContainer.imageView = (ImageView) finalView.findViewById(R.id.icon);
	            finalView.setTag(myViewContainer);
	        } else
	            myViewContainer = (ViewHolder) finalView.getTag();
	            myViewContainer.description.setText(listItem.getDesc());
	        	myViewContainer.title.setText(listItem.getTitle());
	        	myViewContainer.imageView.setImageResource(listItem.getImageId());
	         
	        return finalView;
	    }
	}

