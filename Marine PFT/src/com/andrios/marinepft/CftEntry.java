package com.andrios.marinepft;

import java.util.Calendar;

public class CftEntry extends LogEntry{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1538116430747812374L;
	boolean isMTCWaived;
	boolean isACLWaived;
	boolean isMUFWaived;
	boolean isWaived0;
	boolean isWaived1;
	boolean isWaived2;
	boolean isWaived3;
	boolean isWaived4;
	boolean isWaived5;
	boolean isWaived6;
	
	String age;
	String gender;
	String MTC;
	String ACL;
	String MUF;
	String MTCScore;
	String ACLScore;
	String MUFScore;
	String totalScore;
	
	
	
	public CftEntry(String MTC, String ACL, String MUF, String MTCScore,
		String ACLScore, String MUFScore, String totalScore){
		this.name = "CFT";
		this.c = Calendar.getInstance();
		this.MTC = MTC;
		this.ACL = ACL;
		this.MUF = MUF;
		this.MTCScore = MTCScore;
		this.ACLScore = ACLScore;
		this.MUFScore = MUFScore;
		this.totalScore = totalScore;
		this.layout = R.layout.list_item_cft_entry;
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
	
	public String getMTC(){
		return MTC;
	}
	
	public String getACL(){
		return ACL;
	}
	
	public String getMUF(){
		return MUF;
	}
	
	public String getACLScore(){
		return ACLScore;
	}
	
	public String getMTCScore(){
		return MTCScore;
	}
	
	public String getMUFScore(){
		return MUFScore;
	}
	
	public String getTotalScore(){
		return totalScore;
	}
	
	
	
	/*
	 * Setter Methods
	 */
	
	

}
