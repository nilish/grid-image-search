package com.rishaque.gridimagesearch;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.*;


public class ImageDisplayActivity extends Activity {
	
	private EditText searchText;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        setupViews();
        
        //Create the data source
        imageResults = new ArrayList<ImageResult>();
        
         //attache the datasource to the adapter   
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //link the adapter to the adapterview
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
    	 searchText = (EditText)findViewById(R.id.etQuery);
    	 gvResults = (GridView)findViewById(R.id.gvResults);
    	 gvResults.setOnItemClickListener(new OnItemClickListener()
    	 {
    		 public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    			 //lanch hte image display activity
    			 //creating an intent
    			 Intent i = new Intent(ImageDisplayActivity.this, FullImageActivity.class);
    			 
    			 //get the image result into the intent
    			 
    			 ImageResult result = imageResults.get(position);
    			 //pass image result into the intent
    			 i.putExtra("result", result);//need to be serializable or parcelable
    			 //launch the new activity
    			 startActivity(i);
    			 
    			 
    		 }
    		 
    	 });
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
        return true;
    }
    
    
    public void onImageSearch(View v)
    {	
    	String query = searchText.getText().toString();	
    	//debug code
    	if(!query.equals("")){//avoid seaching for empty string
    	Toast.makeText(this, "Search for "+query, Toast.LENGTH_SHORT).show();
    	AsyncHttpClient client = new AsyncHttpClient();
    	String search_url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8";
    	client.get(search_url, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				Log.d("DEBUG", response.toString());
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
	                //When you make to the adapter modify the underlying data
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				}catch(Exception e){
					e.printStackTrace();
				}
				//Log.i("INFO", imageResults.toString());
			}
    	});
    	}
    	else {	
    		System.out.println("please enter something to search");
    	}
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
