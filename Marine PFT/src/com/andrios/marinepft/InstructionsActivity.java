package com.andrios.marinepft;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class InstructionsActivity extends Activity {

	
	Button mcoBTN, bcaBTN, tecomBTN;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	Button rateBTN;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructionsactivity);
        

        setConnections();
        setOnClickListeners();
        setTracker();
    }
    

	private void setConnections() {
		tecomBTN = (Button) findViewById(R.id.instructionActivityTECOMBTN);
		rateBTN = (Button) findViewById(R.id.instructionActivityRateBTN);
		mcoBTN = (Button) findViewById(R.id.instructionActivityMCO1BTN);
		bcaBTN = (Button) findViewById(R.id.instructionActivityMCO2BTN);
		
		
		adView = (AdView)this.findViewById(R.id.instructionsAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);	
	}

	private void setOnClickListeners() {
		rateBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=com.andrios.marinepft"));
				startActivity(intent);

				
			}
			
		});
		mcoBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 tracker.trackEvent(
				            "Clicks",  // Category
				            "Link",  // Action
				            "MCO 6100.13", // Label
				            0);       // Value
				Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.marines.mil/news/publications/Documents/MCO%206100.13%20W_CH%201.pdf"));
				startActivity(browserIntent);
			}
			
		});
		
		bcaBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 tracker.trackEvent(
				            "Clicks",  // Category
				            "Link",  // Action
				            "MCO 6110.3", // Label
				            0);       // Value
				Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.marines.mil/news/publications/Documents/MCO%206110.3%20W%20CH%201.pdf"));
				startActivity(browserIntent);
			}
			
		});
		tecomBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 tracker.trackEvent(
				            "Clicks",  // Category
				            "Link",  // Action
				            "TECOM - CFL", // Label
				            0);       // Value
				Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.tecom.usmc.mil/CFT/CFT.HTM"));
				startActivity(browserIntent);
			}
			
		});
	

		
	}
	
	private void setTracker() {
		tracker = GoogleAnalyticsTracker.getInstance();

	    // Start the tracker in manual dispatch mode...
	    tracker.start("UA-23366060-3", this);
	}

	
	public void onResume(){
		super.onResume();
		tracker.trackPageView("Instructions");
	}
	
	public void onPause(){
		super.onPause();
		tracker.dispatch();
	}
}

