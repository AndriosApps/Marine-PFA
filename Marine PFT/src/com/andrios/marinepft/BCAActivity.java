package com.andrios.marinepft;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.os.Bundle;

public class BCAActivity extends Activity {

	AndriosData mData;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bcaactivity);
	        
	        getExtras();
	        setConnections();
	        setOnClickListeners();
	        setTracker();
	    }
	
		private void getExtras() {
			// TODO Auto-generated method stub
			
		}

			private void setConnections() {
			// TODO Auto-generated method stub
			
		}

			private void setOnClickListeners() {
			// TODO Auto-generated method stub
			
		}

			private void setTracker() {
				tracker = GoogleAnalyticsTracker.getInstance();

			    // Start the tracker in manual dispatch mode...
			    tracker.start("UA-23366060-2", this);
			    
				
			}
			
			public void onResume(){
				super.onResume();
				tracker.trackPageView("CFT");
			}
			
			public void onPause(){
				super.onPause();
				//tracker.dispatch();
			}
	 
}
