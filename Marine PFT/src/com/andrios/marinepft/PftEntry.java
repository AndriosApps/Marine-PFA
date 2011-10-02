package com.andrios.marinepft;

import java.util.Calendar;

public class PftEntry extends LogEntry{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1538116430747812374L;
	boolean isPullupsWaived;
	boolean isCrunchesWaived;
	boolean isCardioWaived;
	boolean isWaived0;
	boolean isWaived1;
	boolean isWaived2;
	boolean isWaived3;
	boolean isWaived4;
	boolean isWaived5;
	boolean isWaived6;
	String alternateCardio;
	String pullups;
	String crunches;
	String run;
	String pullupScore;
	String crunchScore;
	String runScore;
	String totalScore;
	
	
	
	public PftEntry(String pullups, String crunches, String run, String pullupScore,
		String crunchScore, String runScore, String totalScore){
		this.name = "PFT";
		this.c = Calendar.getInstance();
		this.pullups = pullups;
		this.crunches = crunches;
		this.run = run;
		this.pullupScore = pullupScore;
		this.crunchScore = crunchScore;
		this.runScore = runScore;
		this.totalScore = totalScore;
		this.layout = R.layout.list_item_pft_entry;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.andrios.prt.LogEntry#getScoreString()
	 */
	@Override
	public String getScoreString() {
		return getTotalScore();
	}
	
	/*
	 * Getter Methods
	 */
	
	public String getCrunches(){
		return crunches;
	}
	
	public String getPullups(){
		return pullups;
	}
	
	public String getRun(){
		return run;
	}
	
	public String getPullupScore(){
		return pullupScore;
	}
	
	public String getCrunchScore(){
		return crunchScore;
	}
	
	public String getRunScore(){
		return runScore;
	}
	
	public String getTotalScore(){
		return totalScore;
	}
	
	public String getAlternateCardio(){
		return alternateCardio;
	}
	
	/*
	 * Setter Methods
	 */
	
	public void setAlternateCardio(String alternateCardio){
		this.alternateCardio = alternateCardio;
	}

}
