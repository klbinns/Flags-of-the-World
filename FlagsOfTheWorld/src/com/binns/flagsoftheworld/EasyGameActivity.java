package com.binns.flagsoftheworld;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class EasyGameActivity extends Activity implements OnCheckedChangeListener {

	private Random rand = new Random();
	private int count = 1;
	private int questionsAsked, questionsCorrect = 0;
	
	private ImageView flagImage;
	private RadioGroup radioGroup;
	private RadioButton[] radioChoices = new RadioButton[4];
	private TextView correctVal;
	private TextView askedVal;

	private Set<Country> usedCountries = new HashSet<Country>();
	Country correctCountry;
	
	private Button submitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy_game);
		
		// grab resources
		flagImage = (ImageView) findViewById(R.id.imageView1);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioChoices);
		radioGroup.setOnCheckedChangeListener(this);

		radioChoices[0] = (RadioButton) findViewById(R.id.radioChoice1);
		radioChoices[1] = (RadioButton) findViewById(R.id.radioChoice2);
		radioChoices[2] = (RadioButton) findViewById(R.id.radioChoice3);
		radioChoices[3] = (RadioButton) findViewById(R.id.radioChoice4);
		
		correctVal = (TextView) findViewById(R.id.correctValView);
		askedVal = (TextView) findViewById(R.id.askedValView);
		
		submitButton = (Button) findViewById(R.id.submit_button);
		
		TypedArray flags = getResources().obtainTypedArray(R.array.flags);
		count = flags.length();
		flags.recycle();
			
		setupNextQuestion();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy_game, menu);
		return true;
	}
	
	/**
	 * Processes the action when the submit button is pressed
	 * @param theButton
	 */
	public void submit(View theButton){
		
		// check answer
		RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
		
		String toastText;
		if(r.getText().equals(correctCountry.getCountryName())){
			Log.d("KYLE", "Correct:" + r.getText() + ":" + correctCountry.getCountryName());
			toastText = "Correct!";
			questionsCorrect++; // increase the number of questions answered correctly
			
		} else {
			Log.d("KYLE", "Incorrect:" + r.getText() + ":" + correctCountry.getCountryName());
			toastText = "Sorry! Wrong!";
		}
		
		questionsAsked++; // increase the number of questions asked
		
		// update score
		correctVal.setText(String.valueOf(questionsCorrect));
		askedVal.setText(String.valueOf(questionsAsked));
		
		
		Toast.makeText(
				getApplicationContext(),
				toastText, 
				Toast.LENGTH_SHORT).show();
		
		// exit game after so many questions		
		if(questionsAsked < 10){
				
			// uncheck radio buttons and set button to disabled
			radioGroup.clearCheck();
			submitButton.setEnabled(false);
			
			
			// set up next question
			setupNextQuestion();
		
		} else { // exit game
			
			// FIXME drop current Activity from stack so user doesn't go
			// 'back' into the finished game
			
			Intent intent = new Intent(this, EndGameActivity.class);
			intent.putExtra("questionsAsked", questionsAsked);
			intent.putExtra("questionsCorrect", questionsCorrect);
			startActivity(intent);
		
		}
		
	}
	
	private void setupNextQuestion(){
		
		List<Country> countries = new LinkedList<Country>();
		
		Country country;
		
		// FIXME this doesn't quite work. will repeat questions
		do {
			// get a country for question
			correctCountry = getRandomCountry();
			
		// if the country as already been used before, grab another one
		} while(usedCountries.contains(correctCountry));
		
		countries.add(correctCountry);
		
		
		while(countries.size() < 4 ) {
			
			// get three other countries as 'incorrect' answers
			do {
				// get an incorrect country
				country = getRandomCountry();
				
			// if the country as already party of the current question, grab another one
			} while(countries.contains(country));
			
			countries.add(country);
		
		}
		
		// set the flag image
		flagImage.setImageDrawable(countries.get(0).getCountryFlag());
		
		// randomize where the correct answer goes
		Collections.shuffle(countries);
		
		for(int i = 0; i < countries.size(); i++){
			radioChoices[i].setText(countries.get(i).getCountryName());
		}
		
	 	// add this country to the 'used' set
	    usedCountries.add(correctCountry);
	}

	private Country getRandomCountry(){
		
		int index = rand.nextInt(count);
		
		// these resources will be accessed a lot. is there an optimization opportunity here?
		// maybe load all Country objs in memory but only load memory intensive images on demand
		Resources resources = getResources();
	    TypedArray countryName = resources.obtainTypedArray(R.array.countries);
	    TypedArray countryFlag = resources.obtainTypedArray(R.array.flags);

	    Country countryObj = 
	    		new Country(countryName.getString(index), countryFlag.getDrawable(index));

	    // recycling resources
	    countryName.recycle();
	    countryFlag.recycle();

	    return countryObj;
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		submitButton.setEnabled(true);
	}
	
}