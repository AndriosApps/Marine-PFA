package com.andrios.marinepft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class PFTActivity extends Activity {
	
	RadioButton maleRDO, femaleRDO;
	CheckBox weightCheckBox, SitReachCheckBox;
	SeekBar ageSeekBar, pullupSeekBar, situpSeekBar, runSeekBar;
	TextView ageLBL, pullupLBL, pullupTXTLBL, situpLBL, minutesLBL, runLBL, scoreLBL;
	Button minuteUpBTN, minuteDownBTN, secondUpBTN, secondDownBTN;
	Button ageUpBTN, ageDownBTN, pushupUpBTN, pushupDownBTN, situpUpBTN, situpDownBTN;
	AndriosData mData;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	boolean pushupchanged = false, situpchanged = false, runchanged = false;
	
	int age = 18, pullups = 0, situps = 0, runtime = 0, minutes, seconds;
	boolean male;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pftactivity);
        
        getExtras();
        setConnections();
        setOnClickListeners();
        setTracker();
    }



	private void setTracker() {
		tracker = GoogleAnalyticsTracker.getInstance();

	    // Start the tracker in manual dispatch mode...
	    tracker.start("UA-23366060-2", this);
	    
		
	}



	private void getExtras() {
		Intent intent = this.getIntent();
		
		mData = (AndriosData) intent.getSerializableExtra("data");
		
	}

	private void setConnections() {


		maleRDO  = (RadioButton) findViewById(R.id.calculatorMaleRDO); 
		femaleRDO  = (RadioButton) findViewById(R.id.calculatorFemaleRDO);

		weightCheckBox  = (CheckBox) findViewById(R.id.calculatorWeightCheckBox);

		ageSeekBar = (SeekBar) findViewById(R.id.calculatorAgeSeekBar); 
		pullupSeekBar = (SeekBar) findViewById(R.id.calculatorPullupSeekBar); 
		situpSeekBar = (SeekBar) findViewById(R.id.calculatorSitUpSeekBar);
		runSeekBar = (SeekBar) findViewById(R.id.calculatorRunTimeSeekBar);
		 
		scoreLBL = (TextView) findViewById(R.id.calculatorScoreLBL);
		ageLBL = (TextView) findViewById(R.id.calculatorAgeLBL);
		pullupLBL = (TextView) findViewById(R.id.calculatorPullUpLBL);
		pullupTXTLBL = (TextView) findViewById(R.id.calculatorPullUpTXTLBL);
		situpLBL = (TextView) findViewById(R.id.calculatorSitUpLBL);
		runLBL = (TextView) findViewById(R.id.calculatorRunLBL);
		

		ageUpBTN = (Button) findViewById(R.id.calculatorAgeUpBTN);
		pushupUpBTN = (Button) findViewById(R.id.calculatorPushupsUpBTN);
		situpUpBTN = (Button) findViewById(R.id.calculatorSitupsUpBTN);
		secondUpBTN = (Button) findViewById(R.id.calculatorSecondsUpBTN);

		ageDownBTN = (Button) findViewById(R.id.calculatorAgeDownBTN);
		pushupDownBTN = (Button) findViewById(R.id.calculatorPushupsDownBTN);
		situpDownBTN = (Button) findViewById(R.id.calculatorSitupsDownBTN);
		secondDownBTN = (Button) findViewById(R.id.calculatorSecondsDownBTN);
		
		ageSeekBar.setMax(80);//max age 100
		ageSeekBar.setProgress(18);
		
		pullupSeekBar.setMax(25);//max pullups is 20 FAH 70 sec
		situpSeekBar.setMax(100);//max situps is 100 male / female
		runSeekBar.setMax(2100);//max runtime 29:10 (female) 35 min * 60 = 2100
		
		adView = (AdView)this.findViewById(R.id.homeAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
	}
	
	private void setOnClickListeners() {
		ageSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				age = arg1;
				ageLBL.setText(Integer.toString(age));
				calculateScore();
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
		});
		
		pullupSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				pullups = arg1;
				pullupLBL.setText(Integer.toString(pullups));
				pushupchanged = true;
				calculateScore();
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
		});
		
		situpSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				situps = arg1;
				situpLBL.setText(Integer.toString(situps));
				situpchanged = true;
				calculateScore();
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
		});
		
		runSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				runtime = arg1;
				minutes = (Integer) runtime / 60;
				seconds = runtime % 60;
				formatTimer();
				runchanged = true;
				calculateScore();
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
		});
		ageUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				age += 1;
				if(age > 80){
					age = 80;
				}
				
				ageSeekBar.setProgress(age);
				
				calculateScore();
			}
			
		});
		
		pushupUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				pullups += 1;
				if(pullups > 100){
					pullups = 100;
				}
				
				pullupSeekBar.setProgress(pullups);
				
				calculateScore();
			}
			
		});
		
		situpUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				situps += 1;
				if(situps > 110){
					situps = 110;
				}
				
				situpSeekBar.setProgress(situps);
				
				calculateScore();
			}
			
		});

		secondUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				runtime += 1;
				if(runtime > 1080){
					runtime = 1080;
				}
				minutes = (Integer) runtime / 60;
				seconds = runtime % 60;
				formatTimer();
				runSeekBar.setProgress(runtime);
				runchanged = true;
				calculateScore();
			}
			
		});
		
		ageDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				age -= 1;
				if(age < 0){
					age = 0;
				}
				
				ageSeekBar.setProgress(age);
				
				calculateScore();
			}
			
		});
		
		pushupDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				pullups -= 1;
				if(pullups < 0){
					pullups = 0;
				}
				
				pullupSeekBar.setProgress(pullups);
				
				calculateScore();
			}
			
		});
		
		situpDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				situps -= 1;
				if(situps < 0){
					situps = 0;
				}
				
				situpSeekBar.setProgress(situps);
				
				calculateScore();
			}
			
		});
		
		secondDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				runtime -= 1;
				if(runtime < 0){
					runtime = 0;
				}
				minutes = (Integer) runtime / 60;
				seconds = runtime % 60;
				formatTimer();
				runSeekBar.setProgress(runtime);
				runchanged = true;
				calculateScore();
			}
			
		});
		
		maleRDO.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(maleRDO.isChecked()){
					pullupTXTLBL.setText("Pullups (Reps)");
					if(pullupSeekBar.getProgress() > 25){
						pullups = 0;
						pullupSeekBar.setProgress(pullups);
						pullupLBL.setText(Integer.toString(pullups));
					}
					pullupSeekBar.setMax(25);
				}else{
					pullupTXTLBL.setText("Flexed Arm Hang (Seconds)");
					if(pullupSeekBar.getProgress() > 25){
						pullups = 0;
						pullupSeekBar.setProgress(pullups);
						pullupLBL.setText(Integer.toString(pullups));
					}
					pullupSeekBar.setMax(75);
				}
			
				
				calculateScore();
				
			}
			
		});
		weightCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				calculateScore();
				
			}
			
		});

		
		
	}
	
	private void formatTimer(){
		String minutesTXT, secondsTXT;
		if(minutes < 10){
			minutesTXT = "0"+Integer.toString(minutes);
		}else{
			minutesTXT = Integer.toString(minutes);
		}
		if(seconds < 10){
			secondsTXT = "0"+Integer.toString(seconds);
		}else{
			secondsTXT = Integer.toString(seconds);
		}
		runLBL.setText(minutesTXT + ":" + secondsTXT);
		
		
	}
	
	private void calculateScore(){
		if(maleRDO.isChecked()){
			if(age < 20){
				calculateMale(mData.pushupMale17, mData.situpMale17, mData.runMale17);
			}else if(age < 25){
				calculateMale(mData.pushupMale20, mData.situpMale20, mData.runMale20);
			}else if(age < 30){
				calculateMale(mData.pushupMale25, mData.situpMale25, mData.runMale25);
			}else if(age < 35){
				calculateMale(mData.pushupMale30, mData.situpMale30, mData.runMale30);
			}else if(age < 40){
				calculateMale(mData.pushupMale35, mData.situpMale35, mData.runMale35);
			}else if(age < 45){
				calculateMale(mData.pushupMale40, mData.situpMale40, mData.runMale40);
			}else if(age < 50){
				calculateMale(mData.pushupMale45, mData.situpMale45, mData.runMale45);
			}else if(age < 55){
				calculateMale(mData.pushupMale50, mData.situpMale50, mData.runMale50);
			}else if(age < 60){
				calculateMale(mData.pushupMale55, mData.situpMale55, mData.runMale55);
			}else if(age < 65){
				calculateMale(mData.pushupMale60, mData.situpMale60, mData.runMale60);
			}else{
				calculateMale(mData.pushupMale65, mData.situpMale65, mData.runMale65);
			}
			
		}else{
			if(age < 20){
				calculateFemale(mData.pushupFemale17, mData.situpFemale17, mData.runFemale17);
			}else if(age < 25){
				calculateFemale(mData.pushupFemale20, mData.situpFemale20, mData.runFemale20);
			}else if(age < 30){
				calculateFemale(mData.pushupFemale25, mData.situpFemale25, mData.runFemale25);
			}else if(age < 35){
				calculateFemale(mData.pushupFemale30, mData.situpFemale30, mData.runFemale30);
			}else if(age < 40){
				calculateFemale(mData.pushupFemale35, mData.situpFemale35, mData.runFemale35);
			}else if(age < 45){
				calculateFemale(mData.pushupFemale40, mData.situpFemale40, mData.runFemale40);
			}else if(age < 50){
				calculateFemale(mData.pushupFemale45, mData.situpFemale45, mData.runFemale45);
			}else if(age < 55){
				calculateFemale(mData.pushupFemale50, mData.situpFemale50, mData.runFemale50);
			}else if(age < 60){
				calculateFemale(mData.pushupFemale55, mData.situpFemale55, mData.runFemale55);
			}else if(age < 65){
				calculateFemale(mData.pushupFemale60, mData.situpFemale60, mData.runFemale60);
			}else{
				calculateFemale(mData.pushupFemale65, mData.situpFemale65, mData.runFemale65);
			}
		}
		
		if(!weightCheckBox.isChecked() || !SitReachCheckBox.isChecked()){
			if(changed()){
				scoreLBL.setText("Fail");
			}
		}
		
	}



	private void calculateMale(int[] pushupMale, int[] situpMale, int[] runMale) {
		int totalScore = 0;
		int[] Scores = {45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
		boolean fail0 = true;//pullups
		boolean fail1 = true;//situps
		boolean fail2 = true;//run
		for(int i = 11; i >= 0; i--){
			if(pullups >= pushupMale[i]){
				if(i > 0){
					totalScore += Scores[i];
					fail0 = false;
				}
				
				break;
			}
		}
		for(int i = 11; i >= 0; i--){
			if(situps >= situpMale[i]){
				if(i > 0){
					totalScore += Scores[i];
					fail1 = false;
				}	
			
				break;
			}
		}
		for(int i = 11; i >= 0; i--){
			if(runtime <= runMale[i]){
				if(i > 0){
					totalScore += Scores[i];
					fail2 = false;
				}
				break;
			}
		}
		
		if(!fail0 && !fail1 && !fail2 && changed()){

			if((totalScore / 3) < 50){
				scoreLBL.setText("Probationary");
			}else if((totalScore / 3) < 55){
				scoreLBL.setText("Satisfactory Medium");
			}else if((totalScore / 3)< 60){
				scoreLBL.setText("Satisfactory High");
			}else if((totalScore / 3)< 65){
				scoreLBL.setText("Good Low");
			}else if((totalScore / 3)< 70){
				scoreLBL.setText("Good Medium");
			}else if((totalScore / 3)< 75){
				scoreLBL.setText("Good High");
			}else if((totalScore / 3)< 80){
				scoreLBL.setText("Excellent Low");
			}else if((totalScore / 3)< 85){
				scoreLBL.setText("Excellent Medium");
			}else if((totalScore / 3)< 90){
				scoreLBL.setText("Excellent High");
			}else if((totalScore / 3) < 95){
				scoreLBL.setText("Outstanding Low");
			}else if((totalScore / 3) < 100){
				scoreLBL.setText("Outstanding Medium");
			}else if((totalScore / 3) == 100){
				scoreLBL.setText("Outstanding High");
			}
		}else if(changed()){
			scoreLBL.setText("Fail");
		}
	
		
		
	}



	private void calculateFemale(int[] pushupFemale, int[] situpFemale, int[] runFemale) {
		int totalScore = 0;
		int[] Scores = {45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
		boolean fail0 = true;//pullups
		boolean fail1 = true;//situps
		boolean fail2 = true;//run
		for(int i = 11; i >= 0; i--){
			if(pullups >= pushupFemale[i]){
				if(i > 0){
					totalScore += Scores[i];
					fail0 = false;
				}
				break;
			}
		}
		for(int i = 11; i >= 0; i--){
			if(situps >= situpFemale[i]){
				if(i > 0){
					totalScore += Scores[i];
					fail1=false;
				}	
				
				break;
			}
		}
		for(int i = 11; i >= 0; i--){
			if(runtime <= runFemale[i]){
				if(i > 0){
					totalScore += Scores[i];
					fail2 = false;
				}
				break;
			}
		}
		
		if(!fail0 && !fail1 && !fail2 && changed()){

			if((totalScore / 3) < 50){
				scoreLBL.setText("Probationary");
			}else if((totalScore / 3) < 55){
				scoreLBL.setText("Satisfactory Medium");
			}else if((totalScore / 3)< 60){
				scoreLBL.setText("Satisfactory High");
			}else if((totalScore / 3)< 65){
				scoreLBL.setText("Good Low");
			}else if((totalScore / 3)< 70){
				scoreLBL.setText("Good Medium");
			}else if((totalScore / 3)< 75){
				scoreLBL.setText("Good High");
			}else if((totalScore / 3)< 80){
				scoreLBL.setText("Excellent Low");
			}else if((totalScore / 3)< 85){
				scoreLBL.setText("Excellent Medium");
			}else if((totalScore / 3)< 90){
				scoreLBL.setText("Excellent High");
			}else if((totalScore / 3) < 95){
				scoreLBL.setText("Outstanding Low");
			}else if((totalScore / 3) < 100){
				scoreLBL.setText("Outstanding Medium");
			}else if((totalScore / 3) == 100){
				scoreLBL.setText("Outstanding High");
			}
		}else if(changed()){
			scoreLBL.setText("Fail");
		}
	
		
		
	}
	
	private boolean changed(){
		boolean changed = false;
		if(runchanged && situpchanged && pushupchanged){
			changed = true;
		}
		
		return changed;
	}
	public void onResume(){
		super.onResume();
		tracker.trackPageView("PRT");
	}
	
	public void onPause(){
		super.onPause();
		tracker.dispatch();
	}

}
