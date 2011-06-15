package com.andrios.marinepft;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.widget.Toast;


public class AndriosData implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6149357292077176082L;

	
	
	//Male
	
	int[] weightMale = {131, 136, 141, 145, 150, 155,
			160, 165, 170, 175, 180, 186, 191, 197, 202, 208, 214, 220, 225, 231, 237,
			244, 250};
			
	int[] pullupMale = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	
	
	
	
	// 1  2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18   19    20   21
	//60 120 180 240 300 360 420 480 540 600 660 720 780 840 900 960 1020 1080 1140 1200 1260
	

	//Female
	int[] weightFemale = {119, 124, 128, 132, 136, 141, 145,
			150, 155, 159, 164, 169, 174, 179, 184, 189, 194, 200, 205, 210, 216, 221, 227};
	
	
	int[] pushupFemale17 = {19, 20, 22, 24, 60 ,36, 42, 43, 45, 47, 50, 51};
	int[] pushupFemale20 = {16, 17, 20, 21, 28, 33, 39, 40, 43, 44, 47, 48};
	int[] pushupFemale25 = {13, 15, 18, 19, 26, 30, 37, 39, 41, 43, 45, 46};
	int[] pushupFemale30 = {11, 13, 15, 17, 24, 28, 35, 37, 39, 41, 43, 44};
	int[] pushupFemale35 = {9, 11, 13, 14, 22, 26, 34, 35, 37, 39, 42, 43};
	int[] pushupFemale40 = {7, 9, 11, 12, 20, 24, 32, 33, 35, 37, 40, 41};
	int[] pushupFemale45 = {5, 7, 8, 11, 18, 22, 30, 32, 33, 35, 39, 40};
	int[] pushupFemale50 = {2, 5, 6, 10, 16, 20, 28, 30, 31, 33, 37, 38};
	int[] pushupFemale55 = {2, 3, 5, 6, 10, 16, 20, 22, 24, 26, 28, 30};
	int[] pushupFemale60 = {2, 3, 4, 5, 8, 12, 16, 18, 20, 22, 24, 26};
	int[] pushupFemale65 = {1, 2, 3, 4, 6, 9, 12, 14, 16, 18, 20, 22};
	
	int[] situpFemale17 = {50, 54, 59, 62, 71, 81, 90, 93, 98, 102, 107, 109};
	int[] situpFemale20 = {46, 50, 54, 58, 66, 78, 87, 90, 94, 98, 103, 105};
	int[] situpFemale25 = {13, 17, 50, 54, 62, 75, 84, 87, 91, 95, 100, 101};
	int[] situpFemale30 = {40, 44, 47, 51, 59, 73, 81, 85, 88, 92, 97, 98};
	int[] situpFemale35 = {37, 40, 43, 47, 55, 70, 78, 83, 85, 88, 93, 95};
	int[] situpFemale40 = {35, 37, 39, 44, 51, 68, 76, 80,83, 85, 90, 92};
	int[] situpFemale45 = {31, 33, 35, 40, 47, 65, 73, 78, 80, 81, 86, 88};
	int[] situpFemale50 = {29, 30, 32, 37, 44, 63, 71, 76, 77, 78, 84, 85};
	int[] situpFemale55 = {26, 28, 30, 36, 40, 54, 62, 66, 70, 74, 80, 81};
	int[] situpFemale60 = {20, 22, 24, 26, 32, 40, 56, 62, 66, 70, 74, 75};
	int[] situpFemale65 = {10, 13, 17, 20, 28, 36, 44, 50, 55, 60, 64, 65};
	
	// 1  2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18   19    20   21
	//60 120 180 240 300 360 420 480 540 600 660 720 780 840 900 960 1020 1080 1140 1200 1260
	int[] runFemale17 = {900, 885, 855, 810, 780, 765, 750, 720, 705, 690, 675, 569};
	int[] runFemale20 = {930, 915, 900, 855, 825, 810, 795, 765, 735, 690, 675, 587};
	int[] runFemale25 = {968, 945, 923, 893, 870, 840, 803, 780, 750, 705, 690, 617};
	int[] runFemale30 = {1005, 975, 945, 930, 915, 870, 810, 795, 765, 720, 705, 646};
	int[] runFemale35 = {1020, 998, 975, 953, 930, 878, 825, 803, 773, 728, 713, 651};
	int[] runFemale40 = {1035, 1020, 1005, 975, 945, 885, 840, 810, 780, 735, 720, 656};
	int[] runFemale45 = {1043, 1028, 1013, 990, 953, 900, 848, 825, 795, 750, 728, 658};
	int[] runFemale50 = {1050, 1035, 1020, 1005, 960, 915, 855, 840, 810, 765, 735, 660};
	int[] runFemale55 = {1123, 1098, 1083, 1068, 1018, 969, 920, 993, 865, 837, 819, 743};
	int[] runFemale60 = {1183, 1165, 1148, 1131, 1086, 1037, 985, 960, 934, 908, 890, 814};
	int[] runFemale65 = {1252, 1231, 1213, 1194, 1146, 1098, 1050, 1027, 1003, 979, 961, 885};
	
	
	public AndriosData(){
		
		
	}
	

	/**
	 * Getter Methods
	 */
	
	
	
	
	/**
	 * Setter Methods
	 */
	

	
	


	
	public void write(Context ctx){
		AndriosData writableData = null;
		try {
			writableData = (AndriosData) this.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		
		
		try {
		
			FileOutputStream fos;
			fos = ctx.openFileOutput("data", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(writableData);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Error: Writing to file",
					Toast.LENGTH_SHORT).show();
		}
	}













	
}
