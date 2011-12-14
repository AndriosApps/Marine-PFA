package com.andrios.marinepft;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class PFTActivity extends Activity implements Observer {
	
	SegmentedControlButton maleRDO, femaleRDO;
	SegmentedControlButton age17BTN, age27BTN, age40BTN, age46BTN;
	
	CheckBox SitReachCheckBox;
	SeekBar ageSeekBar, pullupSeekBar, crunchSeekBar, runSeekBar;
	TextView ageLBL, pullupLBL, pullupTXTLBL, pullupScoreLBL, crunchLBL, minutesLBL, runLBL, scoreLBL;
	TextView pullupFailLBL, crunchFailLBL, runFailLBL;
	Button minuteUpBTN, minuteDownBTN, secondUpBTN, secondDownBTN;
	Button ageUpBTN, ageDownBTN, pushupUpBTN, pushupDownBTN, crunchUpBTN, crunchDownBTN;
	Button logBTN;
	AndriosData mData;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	boolean pullupchanged = false, crunchchanged = false, runchanged = false;
	
	int age = 18, pullups = 0, crunches = 0, runtime = 0, minutes, seconds;
	int runScore, pullupScore, crunchScore, totalScore;
	boolean male, isLog, isPremium;
	
	RelativeLayout bottomBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pftactivity);
        
        getExtras();
        setConnections();
        setOnClickListeners();
        finishSetup();
        setTracker();
    }
    
	private void setTracker() {
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.start(this.getString(R.string.ga_api_key),
				getApplicationContext());
	}

	@Override
	public void onResume() {
		super.onResume();
		tracker.trackPageView("/" + this.getLocalClassName());
	}

	@Override
	public void onPause() {
		super.onPause();
		tracker.dispatch();
	}

 
	private void finishSetup() {
		if(age <= 26){
			age17BTN.setChecked(true);
		}else if(age <= 39){
			age27BTN.setChecked(true);
		}else if(age <= 45){
			age40BTN.setChecked(true);
		}else if(age >= 46){
			age46BTN.setChecked(true);
		}
		
		if(!mData.getGender()){
			femaleRDO.setChecked(true);
		}
		
	}


	private void getExtras() {
		Intent intent = this.getIntent();
		isLog = intent.getBooleanExtra("log", false);
		isPremium = intent.getBooleanExtra("premium", false);	
		
		mData = (AndriosData) intent.getSerializableExtra("data");
		if(mData == null){
			mData = new AndriosData();
		}
		mData.addObserver(this);
		age = mData.getAge();
		
	}


	
	private void setConnections() {

		

		maleRDO  = (SegmentedControlButton) findViewById(R.id.pftActivityMaleSegment);
		femaleRDO  = (SegmentedControlButton) findViewById(R.id.pftActivityFemaleSegment); 
		age17BTN  = (SegmentedControlButton) findViewById(R.id.pftActivityAge17Segment); 
		age27BTN  = (SegmentedControlButton) findViewById(R.id.pftActivityAge27Segment); 
		age40BTN  = (SegmentedControlButton) findViewById(R.id.pftActivityAge40Segment); 
		age46BTN  = (SegmentedControlButton) findViewById(R.id.pftActivityAge46Segment); 
		
		
		
		pullupSeekBar = (SeekBar) findViewById(R.id.calculatorPullupSeekBar); 
		crunchSeekBar = (SeekBar) findViewById(R.id.calculatorcrunchSeekBar);
		runSeekBar = (SeekBar) findViewById(R.id.calculatorRunTimeSeekBar);
		 
		scoreLBL = (TextView) findViewById(R.id.calculatorScoreLBL);
		
		pullupLBL = (TextView) findViewById(R.id.calculatorPullUpLBL);
		pullupTXTLBL = (TextView) findViewById(R.id.calculatorPullUpTXTLBL);
		pullupScoreLBL = (TextView) findViewById(R.id.pullupScoreLBL);
		crunchLBL = (TextView) findViewById(R.id.calculatorcrunchLBL);
		runLBL = (TextView) findViewById(R.id.calculatorRunLBL);
		pullupFailLBL = (TextView) findViewById(R.id.calculatorPullupFailLBL);
		crunchFailLBL = (TextView) findViewById(R.id.calculatorCrunchFailLBL);
		runFailLBL = (TextView) findViewById(R.id.calculatorRunFailLBL);
		
		 

		pushupUpBTN = (Button) findViewById(R.id.calculatorPushupsUpBTN);
		crunchUpBTN = (Button) findViewById(R.id.calculatorcrunchsUpBTN);
		secondUpBTN = (Button) findViewById(R.id.calculatorSecondsUpBTN);

		pushupDownBTN = (Button) findViewById(R.id.calculatorPushupsDownBTN);
		crunchDownBTN = (Button) findViewById(R.id.calculatorcrunchsDownBTN);
		secondDownBTN = (Button) findViewById(R.id.calculatorSecondsDownBTN);
		
		
		
		pullupSeekBar.setMax(25);//max pullups is 20 FAH 70 sec
		crunchSeekBar.setMax(100);//max crunchs is 100 male / female
		runSeekBar.setMax(2400);//max runtime 29:10 (female) 35 min * 60 = 2100
								// The actual max runtime for females is 36:00 changed 
								//      max value to 40*60 = 2400.
			
		adView = (AdView)this.findViewById(R.id.homeAdView);
		logBTN = (Button) findViewById(R.id.pftActivityLogBTN);
	  
		
		if(!isPremium){
			 request = new AdRequest();
				request.setTesting(false);
				adView.loadAd(request);
		}else{
			adView.setVisibility(View.INVISIBLE);
			if(!isLog){
				
				bottomBar = (RelativeLayout) findViewById(R.id.pftActivityBottomBar);
				bottomBar.setVisibility(View.GONE);
				
			}else{
				logBTN.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private void setOnClickListeners() {
		
		
		
		pullupSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				pullups = arg1;
				pullupLBL.setText(Integer.toString(pullups));
				pullupchanged = true;
				calculateScore();
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
		});
		
		crunchSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				crunches = arg1;
				crunchLBL.setText(Integer.toString(crunches));
				crunchchanged = true;
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
		
		crunchUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				crunches += 1;
				if(crunches > 110){
					crunches = 110;
				}
				
				crunchSeekBar.setProgress(crunches);
				
				calculateScore();
			}
			
		});

		secondUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				runtime += 1;
				if(runtime > 2100){
					runtime = 2100;
				}
				minutes = (Integer) runtime / 60;
				seconds = runtime % 60;
				formatTimer();
				runSeekBar.setProgress(runtime);
				runchanged = true;
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
		
		crunchDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				crunches -= 1;
				if(crunches < 0){
					crunches = 0;
				}
				
				crunchSeekBar.setProgress(crunches);
				
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
				
				pullups = 0;
				pullupchanged = false;
				if(maleRDO.isChecked()){
					pullupTXTLBL.setText("Pullups");
					pullupScoreLBL.setText("Pullups");
					pullupSeekBar.setProgress(pullups);
					pullupSeekBar.setMax(25);
					pullupLBL.setText(Integer.toString(pullups));
					
					
				}else{
					pullupTXTLBL.setText("FAH");
					pullupScoreLBL.setText("FAH");
					pullupSeekBar.setProgress(pullups);
					pullupSeekBar.setMax(75);
					pullupLBL.setText(Integer.toString(pullups));
				}
			
				mData.setGender(maleRDO.isChecked());
				calculateScore();
				
			}
			
		});
		
		age17BTN.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					age = 17;
					mData.setAge(17);
					calculateScore();
					
				}
				
			}
			
		});
		
		age27BTN.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					age = 27;
					mData.setAge(27);
					calculateScore();
					
				}
				
			}
			
		});
		
		age40BTN.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					age = 40;
					mData.setAge(40);
					calculateScore();
				}
				
			}
			
		});
		
		age46BTN.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					age = 46;
					mData.setAge(46);
					calculateScore();
				}
				
			}
			
		});
	
		logBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(runchanged && pullupchanged && crunchchanged){
					PftEntry p = new PftEntry(
							Integer.toString(pullups), 
							Integer.toString(crunches), 
							formatTimer(), 
							pullupFailLBL.getText().toString(), 
							crunchFailLBL.getText().toString(), 
							runFailLBL.getText().toString(), 
							scoreLBL.getText().toString()
							
					);
					p.setAge(age);
					p.isWaived0 = !mData.getGender();
					Intent intent = new Intent();
					
					
					
					
					
					intent.putExtra("entry", p);
					PFTActivity.this.setResult(RESULT_OK, intent);
					PFTActivity.this.finish();
				}else{
					Toast.makeText(PFTActivity.this, "Enter Required Metrics", Toast.LENGTH_SHORT).show();
				}
				
				
			}
			
		});
		
		
	}
	
	private String formatTimer(){
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
		String timeString = minutesTXT + ":" + secondsTXT;
		runLBL.setText(timeString);
		
		return timeString;
	}
	
	private void calculateScore(){
		
			scoreRun();
			scorePullups();
			scoreCrunches();
		if(pullupchanged && crunchchanged && runchanged){	
			if(runScore == 0 || pullupScore == 0 || crunchScore == 0){
				totalScore = 0;
			}else{
				totalScore = (runScore + pullupScore + crunchScore);
			}
			
			
			
			
			if(totalScore == 0){
				if(changed()){
					scoreLBL.setBackgroundColor(Color.RED);
					

					
					scoreLBL.setText("Fail");
				}
			}else{
				if(age <= 26){
					if(totalScore >= 225){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("1st Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 175){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("2nd Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 135){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("3rd Class: " + Integer.toString(totalScore));
					}else{
						scoreLBL.setBackgroundColor(Color.RED);
						scoreLBL.setText("Fail: " + Integer.toString(totalScore));
					}
				}else if(age <= 39){
					if(totalScore >= 200){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("1st Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 150){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("2nd Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 110){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("3rd Class: " + Integer.toString(totalScore));
					}else{
						scoreLBL.setBackgroundColor(Color.RED);
						scoreLBL.setText("Fail: " + Integer.toString(totalScore));
					}
				}else if(age <= 45){
					if(totalScore >= 175){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("1st Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 125){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("2nd Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 88){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("3rd Class: " + Integer.toString(totalScore));
					}else{
						scoreLBL.setBackgroundColor(Color.RED);
						scoreLBL.setText("Fail: " + Integer.toString(totalScore));
					}
				}else if(age >= 46){
					if(totalScore >= 150){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("1st Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 100){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("2nd Class: " + Integer.toString(totalScore));
					}else if(totalScore >= 65){
						scoreLBL.setBackgroundColor(Color.GREEN);
						scoreLBL.setText("3rd Class: " + Integer.toString(totalScore));
					}else{
						scoreLBL.setBackgroundColor(Color.RED);
						scoreLBL.setText("Fail: " + Integer.toString(totalScore));
					}
				}
			}
			scoreLBL.setTextColor(Color.BLACK);
			scoreLBL.getBackground().setAlpha(100);
		}else{
			
			scoreLBL.setText("");
		}
		
		
		
		
	}




	
	private void scoreRun(){
		if(maleRDO.isChecked()){
			runScore = 10 + (1980-runtime)/(int)10;
			if (runScore>100)
				runScore = 100;
			
//				int time = 1980;
//				runScore = 9;
//				while(runtime <= time ){
//					time =  time - 10;
//					runScore++;
//					if(runScore >= 100){
//						runScore = 100;
//						break;
//					}
//				}
			
		}else{
			runScore = 10 + (2160-runtime)/(int)10;
			if (runScore>100)
				runScore = 100;
			
//				int time = 2160;
//				runScore = 9;
//				while(runtime <= time ){
//					time =  time - 10;
//					runScore++;
//					if(runScore >= 100){
//						runScore = 100;
//						break;
//					}
//				}
			
		}
		scoreMinRun();
		
	}
	
	private void scoreMinRun(){
		if(maleRDO.isChecked()){
			if(age<=26){
				if(runtime > 1680){
					runScore = 0;
				}
			}else if(age <= 39){
				if(runtime > 1740){
					runScore = 0;
				}
			}else if(age <= 45){
				if(runtime > 1800){
					runScore = 0;
				}
			}else if(age > 45){
				if(runtime > 1980){
					runScore = 0;
				}
			}
		}else{
			if(age<=26){
				if(runtime > 1860){
					runScore = 0;
				}
			}else if(age <= 39){
				if(runtime > 1920){
					runScore = 0;
				}
			}else if(age <= 45){
				if(runtime > 1980){
					runScore = 0;
				}
			}else if(age > 45){
				if(runtime > 2160){
					runScore = 0;
				}
			}
		}
		if(!runchanged){
			runFailLBL.setText("");
		}else if(runScore > 0){
			runFailLBL.setText(Integer.toString(runScore));
		}else{
			
			runFailLBL.setText("Fail");
		}
	}
	
	private void scorePullups() {
		if(maleRDO.isChecked()) {
			if (pullups >= 20){
				pullupScore = 100;
			} else if (pullups >= 3){
				pullupScore = pullups*5;
			} else {
				pullupScore = 0;
			}
		} else {
			if (pullups >= 70) {
				pullupScore = 100;
			} else if (pullups > 40) {
				int num = pullups - 40;
				pullupScore = 40+(2*num);
			} else if (pullups >= 15) {
				pullupScore = pullups;
			} else {
				pullupScore = 0;
			}
			
		}
		if(!pullupchanged){
			pullupFailLBL.setText("");
		}else if(pullupScore > 0){
			pullupFailLBL.setText(Integer.toString(pullupScore));
		}else{
			pullupFailLBL.setText("Fail");
		}
	}

	private void scoreCrunches(){
		if(crunches >= 50){
			crunchScore = crunches;
		}else if(crunches >= 45 && age >= 27){
			crunchScore = crunches;
		}else if(crunches >=40 && age >= 46){
			crunchScore = crunches;
		}else{
			crunchScore = 0;
		}
		if(!crunchchanged){
			crunchFailLBL.setText("");
		}else if(crunchScore > 0){
			crunchFailLBL.setText(Integer.toString(crunchScore));
		}else{
			crunchFailLBL.setText("Fail");
		}
	}
	
	private boolean changed(){
		boolean changed = false;
		if(runchanged && crunchchanged && pullupchanged){
			changed = true;
		}
		
		return changed;
	}




	public void update(Observable arg0, Object arg1) {
		System.out.println("UPDATE PFT");
		age = mData.getAge();
		if(age == 17){
			age17BTN.setChecked(true);
		}else if(age == 27){
			age27BTN.setChecked(true);
		}else if(age == 40){
			age40BTN.setChecked(true);
		}else if(age == 46){
			age46BTN.setChecked(true);
		}
		
		
		femaleRDO.setChecked(!mData.getGender());
		maleRDO.setChecked(mData.getGender());
		
	}

}
