package com.andrios.marinepft;

import java.util.Observable;
import java.util.Observer;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CFTActivity extends Activity implements Observer {

	private static int minMTCTime = 120;
	private static int maxMTCTime = 360;
	private static int minAmmoLifts = 0;
	private static int maxAmmoLifts = 100;
	private static int minMUFTime = 120;
	private static int maxMUFTime = 420;
	
	
	AndriosData mData;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	
	
	//MTC
	SeekBar mtcSeekBar;
	Button mtcUpBTN, mtcDownBTN;
	TextView mtcScoreLBL, mtcTimeLBL;
	
	//MUF
	SeekBar mufSeekBar;
	Button mufUpBTN, mufDownBTN;
	TextView mufScoreLBL, mufTimeLBL;
	
	
	
	int age = 17, mtcTime = minMTCTime, ammoLifts = 0, mufTime = minMUFTime;
	int mtcScore, ammoLiftScore, mufScore;
	boolean mtcFail, ammoLiftFail, mufFail;
	boolean mtcChanged, ammoLiftChanged, mufChanged;
	boolean male = true;
	
	//Ammo Lift
	TextView ammoScoreLBL, ammoNumLBL;
	SeekBar ammoSeekBar;
	Button ammoUpBTN, ammoDownBTN;
	
	TextView ageLBL;
	TextView totalScoreLBL;
	SegmentedControlButton maleSegment, femaleSegment, age17Segment, age27Segment, age40Segment, age46Segment;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.cftactivity);
	        
	      
	        setConnections();
	        setOnClickListeners();
	        getExtras();
	        setTracker();
	    }

		private void getExtras() {
			Intent intent = this.getIntent();
			
			mData = (AndriosData) intent.getSerializableExtra("data");
			mData.addObserver(this);
			age = mData.getAge();
			if(age == 17){
				age17Segment.setChecked(true);
			}else if(age == 27){
				age27Segment.setChecked(true);
			}else if(age == 40){
				age40Segment.setChecked(true);
			}else if(age == 46){
				age46Segment.setChecked(true);
			}
			
			if(!mData.getGender()){
				femaleSegment.setChecked(true);
			}
		}

		private void setConnections() {
		
		//Segment Buttons
		maleSegment = (SegmentedControlButton) findViewById(R.id.cftActivityMaleSegment);
		femaleSegment = (SegmentedControlButton) findViewById(R.id.cftActivityFemaleSegment);
		age17Segment = (SegmentedControlButton) findViewById(R.id.cftActivityAge17Segment);
		age27Segment = (SegmentedControlButton) findViewById(R.id.cftActivityAge27Segment);
		age40Segment = (SegmentedControlButton) findViewById(R.id.cftActivityAge40Segment);
		age46Segment = (SegmentedControlButton) findViewById(R.id.cftActivityAge46Segment);
			
		
		
		// MTC
		mtcSeekBar = (SeekBar) findViewById(R.id.cftActivityMoveToContactSeekBar);
		mtcSeekBar.setMax(maxMTCTime - minMTCTime);
		mtcUpBTN = (Button) findViewById(R.id.cftActivityMoveToContactUpBTN);
		mtcDownBTN = (Button) findViewById(R.id.cftActivityMoveToContactDownBTN);
		mtcScoreLBL = (TextView) findViewById(R.id.cftActivityMoveToContactScoreLBL);

		mtcTimeLBL = (TextView) findViewById(R.id.cftActivityMoveToContactLBL);
		mtcTimeLBL.setText(formatTimer(mtcTime));
		
	
		
		//Ammo Lift Interface
		ammoSeekBar = (SeekBar) findViewById(R.id.cftActivityAmmoCanLiftsSeekBar);
		ammoSeekBar.setMax(maxAmmoLifts - minAmmoLifts);
		ammoUpBTN = (Button) findViewById(R.id.cftActivityAmmoCanLiftsUpBTN);
		ammoDownBTN = (Button) findViewById(R.id.cftActivityAmmoCanLiftsDownBTN);
		ammoScoreLBL = (TextView) findViewById(R.id.cftActivityAmmoCanLiftsScoreLBL);

		ammoNumLBL = (TextView) findViewById(R.id.cftActivityAmmoCanLiftsNumLBL);
		ammoNumLBL.setText(Integer.toString(ammoLifts));
		
		// MUF
		mufSeekBar = (SeekBar) findViewById(R.id.cftActivityManueverUnderFireSeekBar);
		mufSeekBar.setMax(maxMUFTime - minMUFTime);
		mufUpBTN = (Button) findViewById(R.id.cftActivityManueverUnderFireUpBTN);
		mufDownBTN = (Button) findViewById(R.id.cftActivityManueverUnderFireDownBTN);
		mufScoreLBL = (TextView) findViewById(R.id.cftActivityManueverUnderFireScoreLBL);
		mufTimeLBL = (TextView) findViewById(R.id.cftActivityManueverUnderFireLBL);
		mufTimeLBL.setText(formatTimer(mufTime));
		
		totalScoreLBL = (TextView) findViewById(R.id.cftActivityTotalScoreLBL);
		
		adView = (AdView)this.findViewById(R.id.CFTAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
	}

		private void setOnClickListeners() {
			maleSegment.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					male = arg1;
					mData.setGender(male);
					calcScore();
				}
				
			});
			
		
		
			// AGE
			
			age17Segment.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if(arg1){
						
						age = 17;
						mData.setAge(17);
						calcScore();
					}
					
					
				}
				
			});
			
			age27Segment.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if(arg1){
						
						age = 27;
						mData.setAge(27);
						calcScore();
					}
					
				}
				
			});
			
			age40Segment.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if(arg1){
						
						age = 40;
						mData.setAge(40);
						calcScore();
					}
					
				}
				
			});
			
			age46Segment.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if(arg1){
						
						age = 46;
						mData.setAge(46);
						calcScore();
					}
					
				}
				
			});
			
			
			//MTC
		 mtcSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				mtcChanged = true;
				mtcTime = arg1 + minMTCTime;
				mtcTimeLBL.setText(formatTimer(mtcTime));
				
				calcScore();
			}

			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			 
		 });
		 
			mtcUpBTN.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					mtcTime++;
					if(mtcTime > maxMTCTime){
						mtcTime = maxMTCTime;
					}
					mtcSeekBar.setProgress(mtcTime - minMTCTime);
					
					
				}
				
			});
			
			mtcDownBTN.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					mtcTime--;
					if(mtcTime < minMTCTime){
						mtcTime = minMTCTime;
					}
					mtcSeekBar.setProgress(mtcTime - minMTCTime);
					
					
					
				}
				
			});
			
			//Ammo Lifts
			 ammoSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					ammoLiftChanged = true;
					ammoLifts = arg1 + minAmmoLifts;
					ammoNumLBL.setText(Integer.toString(ammoLifts));
					
					calcScore();
				}

				public void onStartTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
					
				}

				public void onStopTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
					
				}
				 
			 });
			 
				ammoUpBTN.setOnClickListener(new OnClickListener(){

					public void onClick(View arg0) {
						ammoLifts++;
						if(ammoLifts > maxAmmoLifts){
							ammoLifts = maxAmmoLifts;
						}
						ammoSeekBar.setProgress(ammoLifts - minAmmoLifts);
						
						
					}
					
				});
				
				ammoDownBTN.setOnClickListener(new OnClickListener(){

					public void onClick(View arg0) {
					ammoLifts--;
						if(ammoLifts < minAmmoLifts){
							ammoLifts = minAmmoLifts;
						}
						ammoSeekBar.setProgress(ammoLifts - minAmmoLifts);
						
						
						
					}
					
				});
				
				//MUF
				 mufSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

					public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
						mufChanged = true;
						mufTime = arg1 + minMUFTime;
						mufTimeLBL.setText(formatTimer(mufTime));
						
						calcScore();
					}

					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub
						
					}

					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub
						
					}
					 
				 });
				 
					mufUpBTN.setOnClickListener(new OnClickListener(){

						public void onClick(View arg0) {
							mufTime++;
							if(mufTime > maxMUFTime){
								mufTime = maxMUFTime;
							}
							mufSeekBar.setProgress(mufTime - minMUFTime);
							
							
						}
						
					});
					
					mufDownBTN.setOnClickListener(new OnClickListener(){

						public void onClick(View arg0) {
							mufTime--;
							if(mufTime < minMUFTime){
								mufTime = minMUFTime;
							}
							mufSeekBar.setProgress(mufTime - minMUFTime);
							
							
							
						}
						
					});
		
	}
		
		private void calcScore(){
			if(male){
				calcMale();
			}else{
				calcFemale();
			}
			
			if(mtcChanged && ammoLiftChanged && mufChanged){
				int totalScore = (mtcScore + ammoLiftScore + mufScore);
				totalScoreLBL.setTextColor(Color.BLACK);
				if(mtcFail || ammoLiftFail || mufFail){
					totalScoreLBL.setText("Failed Event(s)");
					totalScoreLBL.setBackgroundColor(Color.RED);
				}else if(totalScore >= 270){
					totalScoreLBL.setText("1st Class: "+ totalScore);
					totalScoreLBL.setBackgroundColor(Color.GREEN);
				}else if(totalScore >= 225){
					totalScoreLBL.setText("2nd Class: "+ totalScore);
					totalScoreLBL.setBackgroundColor(Color.GREEN);
				}else if(totalScore >= 190){
					totalScoreLBL.setText("3rd Class: "+ totalScore);
					totalScoreLBL.setBackgroundColor(Color.GREEN);
				}else{
					totalScoreLBL.setText("Fail: "+ totalScore);
					totalScoreLBL.setBackgroundColor(Color.RED);
				}

				totalScoreLBL.getBackground().setAlpha(100);
			}
		}
		
		
		private void calcMale(){
			
			if(age <= 26){
				calcMTC(mData.maleMTCMins[0], mData.maleMTC17);
				calcAmmoLift(mData.maleAMMOMax[0], mData.maleAMMO17);
				calcMUF(mData.maleMUFMins[0], mData.maleMUF17);
			}else if(age <= 39){
				calcMTC(mData.maleMTCMins[1], mData.maleMTC27);
				calcAmmoLift(mData.maleAMMOMax[1], mData.maleAMMO27);
				calcMUF(mData.maleMUFMins[1], mData.maleMUF27);
			}else if(age <= 45){
				calcMTC(mData.maleMTCMins[2], mData.maleMTC40);
				calcAmmoLift(mData.maleAMMOMax[2], mData.maleAMMO40);
				calcMUF(mData.maleMUFMins[2], mData.maleMUF40);
			}else{
				calcMTC(mData.maleMTCMins[3], mData.maleMTC46);
				calcAmmoLift(mData.maleAMMOMax[3], mData.maleAMMO46);
				calcMUF(mData.maleMUFMins[3], mData.maleMUF46);
			}
		}
		
		private void calcFemale(){
			if(age <= 26){
				calcMTC(mData.femaleMTCMins[0], mData.femaleMTC17);
				calcAmmoLift(mData.femaleAMMOMax[0], mData.femaleAMMO17);
				calcMUF(mData.femaleMUFMins[0], mData.femaleMUF17);
			}else if(age <= 39){
				calcMTC(mData.femaleMTCMins[1], mData.femaleMTC27);
				calcAmmoLift(mData.femaleAMMOMax[1], mData.femaleAMMO27);
				calcMUF(mData.femaleMUFMins[1], mData.femaleMUF27);
			}else if(age <= 45){
				calcMTC(mData.femaleMTCMins[2], mData.femaleMTC40);
				calcAmmoLift(mData.femaleAMMOMax[2], mData.femaleAMMO40);
				calcMUF(mData.femaleMUFMins[2], mData.femaleMUF40);
			}else{
				calcMTC(mData.femaleMTCMins[3], mData.femaleMTC46);
				calcAmmoLift(mData.femaleAMMOMax[3], mData.femaleAMMO46);
				calcMUF(mData.femaleMUFMins[3], mData.femaleMUF46);
			}
		}
		
		private void calcMTC(int min, int[] MTC){
			if(mtcTime <= min){
				mtcScore = 100;
				mtcFail = false;
			}else if((mtcTime - min) > MTC.length){
				mtcFail = true;
			}else{
				mtcScore = MTC[MTC.length - (mtcTime - min)];
				mtcFail = false;
			}
			
			if(!mtcChanged){
				
			}else if(mtcFail){
				mtcScoreLBL.setText("Fail");
				
			}else{
				mtcScoreLBL.setText(Integer.toString(mtcScore));
			}
		}
		
		private void calcAmmoLift(int max, int[] AMMO){
			if(ammoLifts >= max){
		
				ammoLiftScore = 100;
				ammoLiftFail = false;
			}else if((max - ammoLifts) > AMMO.length){
				ammoLiftFail = true;
			}else{
				ammoLiftScore = AMMO[AMMO.length - (max - ammoLifts)];
				ammoLiftFail = false;
			}
			
			if(!ammoLiftChanged){
				
			}else if(ammoLiftFail){
				ammoScoreLBL.setText("Fail");
			}else{
				ammoScoreLBL.setText(Integer.toString(ammoLiftScore));
			}
		}
		
		private void calcMUF(int min, int[] MUF){
			if(mufTime <= min){
		
				mufScore = 100;
				mufFail = false;
			}else if((mufTime - min) > MUF.length){
				mufFail = true;
			}else{
				mufScore = MUF[MUF.length - (mufTime - min)];
				mufFail = false;
			}
			
			if(!mufChanged){
				
			}else if(mufFail){
				mufScoreLBL.setText("Fail");
			}else{
				mufScoreLBL.setText(Integer.toString(mufScore));
			}
		}

		private void setTracker() {
			tracker = GoogleAnalyticsTracker.getInstance();

		    // Start the tracker in manual dispatch mode...
		    //tracker.start("UA-23366060-2", this);
		    
			
		}
		
		public void onResume(){
			super.onResume();
			//tracker.trackPageView("CFT");
		}
		
		public void onPause(){
			super.onPause();
			//tracker.dispatch();
		}
		
		private String formatTimer(int time){
			String minutesTXT, secondsTXT;
			int minutes = (Integer) time / 60;
			int seconds = time % 60;
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
			
			return (minutesTXT + ":" + secondsTXT);
			
		}

		public void update(Observable observable, Object data) {
			System.out.println("UPDATE CFT");
			age = mData.getAge();
			if(age == 17){
				age17Segment.setChecked(true);
			}else if(age == 27){
				age27Segment.setChecked(true);
			}else if(age == 40){
				age40Segment.setChecked(true);
			}else if(age == 46){
				age46Segment.setChecked(true);
			}
			
			
			femaleSegment.setChecked(!mData.getGender());
			maleSegment.setChecked(mData.getGender());
			
		}
		
		
}
