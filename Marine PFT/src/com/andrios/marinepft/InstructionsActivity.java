package com.andrios.marinepft;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class InstructionsActivity extends Activity {

	private static String MCO610013 = "MCO_6100.13_W_CH_1.pdf";
	private static String MCO610013URL = "http://www.marines.mil/news/publications/Documents/MCO%206100.13%20W_CH%201.pdf";
	
	private static String MCO61103 = "MCO_6110.3_W_CH_1.pdf";
	private static String MCO61103URL = "http://www.marines.mil/news/publications/Documents/MCO%206110.3%20W%20CH%201.pdf";
	
	Button mcoBTN, bcaBTN, tecomBTN;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	boolean isPremium;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.instructionsactivity);
        
        getExtras();
        setConnections();
        setOnClickListeners();
        setTracker();
    }
    
	private void getExtras() {
		Intent intent = this.getIntent();
		isPremium = intent.getBooleanExtra("premium", false);	
		
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
    

	private void setConnections() {
		tecomBTN = (Button) findViewById(R.id.instructionActivityTECOMBTN);
		mcoBTN = (Button) findViewById(R.id.instructionActivityMCO1BTN);
		bcaBTN = (Button) findViewById(R.id.instructionActivityMCO2BTN);
		
		adView = (AdView)this.findViewById(R.id.instructionsAdView);
	      
		if(!isPremium){
			 request = new AdRequest();
				request.setTesting(false);
				adView.loadAd(request);
		}else{
			adView.setVisibility(View.INVISIBLE);
			
		}
	
	    
	}

	private void setOnClickListeners() {
		mcoBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 tracker.trackEvent(
				            "Clicks",  // Category
				            "Link",  // Action
				            "MCO 6100.13", // Label
				            0);       // Value
				 try{
					 open(MCO610013);
				 }catch(Exception e){
					 Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(MCO610013URL));
					startActivity(browserIntent);
				 }
				
			}
			
		});
		
		bcaBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 tracker.trackEvent(
				            "Clicks",  // Category
				            "Link",  // Action
				            "MCO 6110.3", // Label
				            0);       // Value
				 try{
					 open(MCO61103);
				 }catch(Exception e){
					 Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(MCO61103URL));
					startActivity(browserIntent);
				 }
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
	
private void open(String filename){
		
		String PATH = Environment.getExternalStorageDirectory()
                + "/download/";
		
		File file = new File(PATH + filename);
		if (file.exists()) {
			 System.out.println("file exists");
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");

            try {
            	System.out.println("Start Activity");
                startActivity(intent);
            } 
            catch (ActivityNotFoundException e) {
                Toast.makeText(InstructionsActivity.this, 
                    "No Application Available to View PDF", 
                    Toast.LENGTH_SHORT).show();
            }
		}else{
			float f = 1/0;
		}
	}
	
}

