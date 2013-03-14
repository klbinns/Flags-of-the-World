package com.binns.flagsoftheworld;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	
	public void startEasyGame(View theButton){
		
		Intent intent = new Intent(this, EasyGameActivity.class);
		startActivity(intent);
		
	}
	
	public void startDifficultGame(View theButton){
		
		Toast.makeText(
				getApplicationContext(),
				"The difficult game hasn't been implemented yet.", 
				Toast.LENGTH_SHORT).show();
		
	}

}