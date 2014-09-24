package com.rishaque.gridimagesearch;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class FullImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);
		//String url = getIntent().getStringExtra("url");
		
		//remove the action bar
		getActionBar().hide();
		
		
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		//Picasso.with(this).load(url).into(ivImageResult);
		Picasso.with(this).load(result.fullUrl).into(ivImageResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_image, menu);
		return true;
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
