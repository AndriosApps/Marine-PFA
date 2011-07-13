package com.andrios.marinepft;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CFTActivity extends Activity {

	private static int minMTCTime = 120;
	private static int maxMTCTime = 360;
	private static int minAge = 17;
	private static int maxAge = 65;
	private static int minAmmoLifts = 0;
	private static int maxAmmoLifts = 100;
	
	AndriosData mData;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	
	//Age
	SeekBar  ageSeekBar;
	Button ageUpBTN, ageDownBTN;
	
	//MTC
	SeekBar mtcSeekBar;
	Button mtcUpBTN, mtcDownBTN;
	
	
	
	int age = 17, mtcTime = minMTCTime, ammoLifts = 0, mufTime;
	int mtcScore, ammoLiftScore, mufScore;
	boolean mtcFail, ammoLiftFail, mufFail;
	boolean mtcChanged, ammoLiftChanged, mufChanged;
	
	//Ammo Lift
	TextView ammoScoreLBL, ammoNumLBL;
	SeekBar ammoSeekBar;
	Button ammoUpBTN, ammoDownBTN;
	
	TextView mtcScoreLBL, mtcTimeLBL;
	TextView ageLBL;
	RadioButton maleRDO;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.cftactivity);
	        
	        getExtras();
	        setConnections();
	        setOnClickListeners();
	        setTracker();
	    }

		private void getExtras() {
			Intent intent = this.getIntent();
			
			mData = (AndriosData) intent.getSerializableExtra("data");
		
	}

		private void setConnections() {
		
		//RDO Buttons
		maleRDO = (RadioButton) findViewById(R.id.cftActivityMaleRDO);
			
		// MTC
		mtcSeekBar = (SeekBar) findViewById(R.id.cftActivityMoveToContactSeekBar);
		mtcSeekBar.setMax(maxMTCTime - minMTCTime);
		mtcUpBTN = (Button) findViewById(R.id.cftActivityMoveToContactUpBTN);
		mtcDownBTN = (Button) findViewById(R.id.cftActivityMoveToContactDownBTN);
		mtcScoreLBL = (TextView) findViewById(R.id.cftActivityMoveToContactScoreLBL);

		mtcTimeLBL = (TextView) findViewById(R.id.cftActivityMoveToContactLBL);
		mtcTimeLBL.setText(formatTimer(mtcTime));
		
		// Age Interface
		ageLBL = (TextView) findViewById(R.id.cftActivityAgeLBL);
		ageSeekBar = (SeekBar) findViewById(R.id.cftActivityAgeSeekBar);
		ageSeekBar.setMax(maxAge - minAge);
		ageUpBTN = (Button) findViewById(R.id.cftActivityAgeUpBTN);
		ageDownBTN = (Button) findViewById(R.id.cftActivityAgeDownBTN);
		
		//Ammo Lift Interface
		ammoSeekBar = (SeekBar) findViewById(R.id.cftActivityAmmoCanLiftsSeekBar);
		ammoSeekBar.setMax(maxAmmoLifts - minAmmoLifts);
		ammoUpBTN = (Button) findViewById(R.id.cftActivityAmmoCanLiftsUpBTN);
		ammoDownBTN = (Button) findViewById(R.id.cftActivityAmmoCanLiftsDownBTN);
		ammoScoreLBL = (TextView) findViewById(R.id.cftActivityAmmoCanLiftsScoreLBL);

		ammoNumLBL = (TextView) findViewById(R.id.cftActivityAmmoCanLiftsNumLBL);
		ammoNumLBL.setText(Integer.toString(ammoLifts));
		
	}

		private void setOnClickListeners() {
			maleRDO.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					
					calcScore();
				}
				
			});
		
			// AGE
			ageSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

					public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
						
						age = arg1 + minAge;
						ageLBL.setText(Integer.toString(age));
						calcScore();
						
					}

					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub
						
					}

					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub
						
					}
					 
				 });
			
			ageUpBTN.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					age++;
					if(age > maxAge){
						age = maxAge;
					}
					ageSeekBar.setProgress(age - minAge);
					
					
				}
				
			});
			
			ageDownBTN.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					age--;
					if(age < minAge){
						age = minAge;
					}
					ageSeekBar.setProgress(age - minAge);
					
					
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
		
	}
		
		private void calcScore(){
			if(maleRDO.isChecked()){
				calcMale();
			}else{
				calcFemale();
			}
		}
		private void calcMale(){
			
			if(age <= 26){
				calcMTC(mData.maleMTCMins[0], mData.maleMTC17);
				calcAmmoLift(mData.maleAMMOMax[0], mData.maleAMMO17);
			}else if(age <= 39){
				calcMTC(mData.maleMTCMins[1], mData.maleMTC27);
				calcAmmoLift(mData.maleAMMOMax[1], mData.maleAMMO27);
			}else if(age <= 46){
				calcMTC(mData.maleMTCMins[2], mData.maleMTC40);
				calcAmmoLift(mData.maleAMMOMax[2], mData.maleAMMO40);
			}else{
				calcMTC(mData.maleMTCMins[3], mData.maleMTC46);
				calcAmmoLift(mData.maleAMMOMax[3], mData.maleAMMO46);
			}
		}
		
		private void calcFemale(){
			if(age <= 26){
				calcMTC(mData.femaleMTCMins[0], mData.femaleMTC17);
				calcAmmoLift(mData.femaleAMMOMax[0], mData.femaleAMMO17);
			}else if(age <= 39){
				calcMTC(mData.femaleMTCMins[1], mData.femaleMTC27);
				calcAmmoLift(mData.femaleAMMOMax[1], mData.femaleAMMO27);
			}else if(age <= 46){
				calcMTC(mData.femaleMTCMins[2], mData.femaleMTC40);
				calcAmmoLift(mData.femaleAMMOMax[2], mData.femaleAMMO40);
			}else{
				calcMTC(mData.femaleMTCMins[3], mData.femaleMTC46);
				calcAmmoLift(mData.femaleAMMOMax[3], mData.femaleAMMO46);
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
		
		
}
