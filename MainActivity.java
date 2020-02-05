package edu.usna.cs.presidents;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        //create a ListView
        //ListView presidentsListView = (ListView) findViewById(R.id.presidents_list);
        //create a List
        List <String> presidentsList = Arrays.asList((getResources().getStringArray(R.array.presidents)));
        
        
        //create array adapter
        ArrayAdapter <String> presidentsListAdapter = new ArrayAdapter <String> (this,android.R.layout.simple_list_item_1, presidentsList);
        //set the adapter to ListView
        setListAdapter(presidentsListAdapter);
        
        //set ListView object to "onItemClickListener"
        //presidentsListView.setOnItemClickListener(this);
        
        
        
    }

    
    
    @Override  
    public void onListItemClick(ListView parent, View v, int pos, long id) { 
    	
    	String presidentClicked = ((TextView)v).getText().toString();
    	Toast.makeText(getBaseContext(), presidentClicked, Toast.LENGTH_SHORT).show();
    	
    	Intent intent = new Intent(getBaseContext(), WebviewActivity.class);
    	intent.putExtra("president", presidentClicked);
    	startActivity(intent);
    	
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



	
}
