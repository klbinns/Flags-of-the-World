package com.binns.flagsoftheworld;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends Activity {

	private TextView scoreView;
	private TextView scoreResponseView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);
		
		scoreView = (TextView) findViewById(R.id.score_view);
		scoreResponseView = (TextView) findViewById(R.id.score_response_view);
		
		int score = getIntent().getIntExtra("questionsCorrect", 0);
		int questions = getIntent().getIntExtra("questionsAsked", 0);
		float grade = score / questions;
		
		scoreView.setText(String.valueOf(score) + "/" + String.valueOf(questions));
		
		// TODO externalize score strings
		if(grade < 0.33){
			scoreView.setTextColor(Color.RED);
			scoreResponseView.setText("You need practicing!");
		} else if(grade < 0.66){
			scoreView.setTextColor(Color.YELLOW);
			scoreResponseView.setText("Ok...not bad!");
		} else if(grade < 1.0){
			scoreView.setTextColor(Color.GREEN);
			scoreResponseView.setText("Good job!");
		} else {
			scoreView.setTextColor(Color.GREEN);
			scoreResponseView.setText("Wow!! Perfect Score!");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end_game, menu);
		return true;
	}
	
	
	public void mainMenu(View theButton){
		// TODO
		// go to main menu, forget rest of activities in stack
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}

}
