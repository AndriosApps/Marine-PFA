package com.andrios.marinepft;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class CftLogViewActivity extends Activity {
	

	protected static final int CFTACTIVITY = 0;
	static final int DATE_DIALOG_ID = 1;
	AndriosData mData;
	ArrayList<LogEntry> logList;
	int index, mMonth, mDay, mYear;
	CftEntry entry;
	TextView dateLBL;
	TextView MTCLBL, ACLLBL, MUFLBL, cardioLBL;
	TextView MTCcoreLBL, ACLcoreLBL, MUFScoreLBL, totalScoreLBL;
	Profile profile;
	
	Button saveBTN;
	Button shareBTN;
	OnClickListener myOnClickListener;
	GoogleAnalyticsTracker tracker;
	Spinner moodSpinner;
	CheckBox officialCheckBox;
	boolean changes;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cftlogviewactivity);
        
     
        getExtras();
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
    
	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		logList = (ArrayList<LogEntry>) intent.getSerializableExtra("list");
		index = intent.getIntExtra("index", -1);
		mData = (AndriosData) intent.getSerializableExtra("data");
		if(index == -1){
			Intent prtIntent = new Intent(CftLogViewActivity.this.getBaseContext(), CFTActivity.class);
			
			prtIntent.putExtra("log", true);
			prtIntent.putExtra("premium", true);
			prtIntent.putExtra("data", mData);
			startActivityForResult(prtIntent, CFTACTIVITY);
		
		}else{
			entry = (CftEntry) logList.get(index);
			changes = false;
	        setConnections();
	        setOnClickListeners();
		}
		
	}
	
	private void setConnections() {
		
		
		MTCLBL = (TextView)findViewById(R.id.logViewActivityMTCLBL);
		MTCLBL.setText(entry.getMTC());
		ACLLBL = (TextView)findViewById(R.id.logViewActivityACLLBL);
		ACLLBL.setText(entry.getACL());
		MUFLBL = (TextView)findViewById(R.id.logViewActivityMUFLBL);
		MUFLBL.setText(entry.getMUF());

		MTCcoreLBL = (TextView)findViewById(R.id.logViewActivityMTCcoreLBL);
		MTCcoreLBL.setText(entry.getMTCScore());
		ACLcoreLBL = (TextView)findViewById(R.id.logViewActivityACLcoreLBL);
		ACLcoreLBL.setText(entry.getACLScore());
		MUFScoreLBL = (TextView)findViewById(R.id.logViewActivityMUFScoreLBL);
		MUFScoreLBL.setText(entry.getMUFScore());

		totalScoreLBL = (TextView)findViewById(R.id.logViewActivityTotalScoreLBL);
		totalScoreLBL.setText(entry.getTotalScore());
		
		/*
		array_spinner=new String[8];
		array_spinner[0]="Excited";
		array_spinner[1]="Happy";
		array_spinner[2]="Scared";
		array_spinner[3]="Hopeful";
		array_spinner[4]="Worried";
		array_spinner[5]="Crying";
		array_spinner[6]="Uncertain";
		array_spinner[7]="Mad";
		moodSpinner = (Spinner) findViewById(R.id.journalEntryViewActivityMoodSpinner);
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, array_spinner);
		moodSpinner.setAdapter(adapter);
		moodSpinner.setSelection(entry.getMood());
		*/
		saveBTN = (Button) findViewById(R.id.journalEntryViewActivitySaveBTN);
		shareBTN = (Button) findViewById(R.id.journalEntryViewActivityShareBTN);
		
		dateLBL = (TextView) findViewById(R.id.journalEntryViewActivityDateLBL);
		dateLBL.setText(entry.getDateString());
		
		
		officialCheckBox = (CheckBox) findViewById(R.id.journalEntryViewActivityImportantCheckBox);
		officialCheckBox.setChecked(entry.isOfficial());
		setOnClickListeners();
		
	}
	
	private void setOnClickListeners(){
		officialCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				changes = true;
				
			}
			
		});
		dateLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				
			}
			
		});
		
		saveBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(checkFormat()){
					Intent intent = new Intent();
			
					entry.setOfficial(officialCheckBox.isChecked());
					
					if(index == -1){
						logList.add(entry);
					}else{
						logList.set(index, entry);
					}
					
					
					intent.putExtra("list", logList);
					CftLogViewActivity.this.setResult(RESULT_OK, intent);
					CftLogViewActivity.this.finish();
				}
				
			}

			

			
			
		});
		
		shareBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				shareLog();
				
			}

			

			
			
		});
	}
	
	private void createExitDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Quit Without Saving?");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				CftLogViewActivity.this.finish();
				
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
	}
	
	private boolean checkFormat() {
		// TODO Auto-generated method stub
		return true;
	}
	
	  @Override
	    protected Dialog onCreateDialog(int id) {
	            switch (id) {

	            case DATE_DIALOG_ID:
	                    return new DatePickerDialog(this,
	                            mDateSetListener,
	                            entry.getDate().get(Calendar.YEAR), 
	                            entry.getDate().get(Calendar.MONTH), 
	                            entry.getDate().get(Calendar.DAY_OF_MONTH));
	            }
	            return null;
	    }
	    protected void onPrepareDialog(int id, Dialog dialog) {
	            switch (id) {

	            case DATE_DIALOG_ID:
	                    ((DatePickerDialog) dialog).updateDate(
	                    		entry.getDate().get(Calendar.YEAR), 
	                            entry.getDate().get(Calendar.MONTH), 
	                            entry.getDate().get(Calendar.DAY_OF_MONTH));
	                    break;
	            }
	    }    
	   
	    private DatePickerDialog.OnDateSetListener mDateSetListener =
	            new DatePickerDialog.OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						
	                    entry.setDate(dayOfMonth, monthOfYear, year);
	                    dateLBL.setText(entry.getDateString());
						
					}

	          
	    };

		@Override
	    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			
	    	if (requestCode == CFTACTIVITY) {
	    		if (resultCode == RESULT_OK) {
	    			entry = (CftEntry) intent.getSerializableExtra("entry");
	    			setConnections();
	    			changes = true;
	    		} else {
	    			CftLogViewActivity.this.finish();
	    			
	    		}
	    	}
	    }

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event)  {
		    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		        if(changes){
		        	createExitDialog();
		        }else{
		        	CftLogViewActivity.this.finish();
		        }
		        return true;
		    }

		    return super.onKeyDown(keyCode, event);
		}
		
public void shareLog(){
			
			LayoutInflater inflater = LayoutInflater.from(this);	
			final View share_card_layout = inflater.inflate(R.layout.share_card_cft_view, null);
			
			// Populate Data
			

			final TextView dateLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_dateLBL);
			dateLBL.setText(entry.getDateString());
			
			// Move to Contact
			final TextView pullupLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_pullupLBL);
			pullupLBL.setText("Move to Contact");
			
			final TextView pushupMetricLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_pullups_metricLBL);
			pushupMetricLBL.setText(entry.getMTC());


			final TextView pushupScoreLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_pullups_scoreLBL);
			pushupScoreLBL.setText(entry.getMTCScore());
			
			
			// Ammo Can Lifts
			final TextView ammoCanLiftLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_crunchesLBL);
			ammoCanLiftLBL.setText("Ammo Can Lifts");
			

			final TextView crunchesMetricLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_crunches_metricLBL);
			crunchesMetricLBL.setText(entry.getACL());


			final TextView crunchScoreLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_crunches_scoreLBL);
			crunchScoreLBL.setText(entry.getACLScore());
			
			
			// Manuever Under Fire
			final TextView mufLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_cardioLBL);
			mufLBL.setText("Manuever Under Fire");
			
			final TextView cardioMetricLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_cardio_metricLBL);
			cardioMetricLBL.setText(entry.getMUF());



			final TextView cardioScoreLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_cardio_scoreLBL);
			cardioScoreLBL.setText(entry.getMUFScore());

			final TextView totalScoreLBL = (TextView) share_card_layout.findViewById(R.id.share_card_prt_total_scoreLBL);
			totalScoreLBL.setText(entry.getTotalScore());

			
			
			
			
			
			
			//End Populate Data
			share_card_layout.setDrawingCacheEnabled(true);
			share_card_layout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
		            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

			share_card_layout.layout(0, 0, share_card_layout.getMeasuredWidth(), share_card_layout.getMeasuredHeight());
			share_card_layout.buildDrawingCache(true);

			saveBTN.setVisibility(View.GONE);
			
			
			File root = android.os.Environment.getExternalStorageDirectory();               

			 File dir = new File (root.getAbsolutePath() + "/download/");
			try {
				Bitmap bitmap = Bitmap.createBitmap(share_card_layout.getDrawingCache());

				share_card_layout.setDrawingCacheEnabled(false);
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(dir+"CFT.png"));
				Toast.makeText(this, "Saved Image to " + dir,Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(this, "Image Output failed",Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			saveBTN.setVisibility(View.VISIBLE);
			Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);  
			picMessageIntent.setType("image/jpeg");  
			
			File downloadedPic =  new File(  
					dir+"CFT.png");  
			  
			picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(downloadedPic)); 
			picMessageIntent.putExtra(Intent.EXTRA_SUBJECT, "AndriOS Apps Marine PFT app for Android");
			picMessageIntent.putExtra(Intent.EXTRA_TEXT, "CFT Score: " + entry.getTotalScore() + " @Andrios_Apps http://bit.ly/q83uCr #USMC");
			 tracker.trackEvent(
			            "Social",  // Category
			            "Share",  // Action
			            "Share CFT", // Label
			            0);       // Value
		    startActivity(Intent.createChooser(picMessageIntent, "Share Your PFT Score:"));
		    
		}
}
