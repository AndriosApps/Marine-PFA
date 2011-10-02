package com.andrios.marinepft;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class CalculatorTabsActivity extends TabActivity {
    
	private AndriosData mData; //Read in from saved file, passed to all future intents.
	private boolean isPremium;
	
	/** Called when the activity is first created. */
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calculatortabsactivity);
        
        getExtras();
        setConnections();
    }

	private void getExtras() {
		Intent intent = this.getIntent();
		isPremium = intent.getBooleanExtra("premium", false);
		mData = (AndriosData) intent.getSerializableExtra("data");
		
	}
		
		
	

	private void setConnections() {
		TabHost mTabHost = getTabHost();
        Intent intent;
        Resources res = getResources(); 
        
        //Setup for Home Tab (Tab 0)
        intent = new Intent().setClass(this, PFTActivity.class);
        intent.putExtra("premium", isPremium);
        intent.putExtra("data", mData);
        mTabHost.addTab(mTabHost.newTabSpec("PFT").setIndicator("",res.getDrawable(R.drawable.tab_pft))
        		.setContent(intent));
        
        //Setup for Workout Tab (Tab 1)
        intent = new Intent().setClass(this, CFTActivity.class);
        intent.putExtra("premium", isPremium);
        intent.putExtra("data", mData);
        mTabHost.addTab(mTabHost.newTabSpec("CFT").setIndicator("",res.getDrawable(R.drawable.tab_cft))
        		.setContent(intent));
        
        //Setup for Profile Tab (Tab 2)
        intent = new Intent().setClass(this, BCAActivity.class);
        intent.putExtra("premium", isPremium);
        intent.putExtra("data", mData);
        mTabHost.addTab(mTabHost.newTabSpec("BCA").setIndicator("",res.getDrawable(R.drawable.tab_bca))
        		.setContent(intent));
         
       

        //Set Tab host to Home Tab
        mTabHost.setCurrentTab(0);
	}
	
	
	public void onDestroy(){
		super.onDestroy();
		mData.write(this);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  // ignore orientation/keyboard change
	  super.onConfigurationChanged(newConfig);
	}
}