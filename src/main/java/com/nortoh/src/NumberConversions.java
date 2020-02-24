package com.nortoh.src;

/**
 * UNUSED CLASS, METHODS HAVE BEEN USED ON BINARY AND HEXADECIMAL CLASS
 * @author christian
 *
 */
public class NumberConversions {
	
	/**
	 * Convert a decimal to a binary integer array
	 * @param dec
	 * @param size
	 * @return int[size]
	 */
	public static int[] convertDecToBin(int dec) {
		int size = getRequriedBinarySize(dec);
		if(size % 2 != 0) size += 1;
		int[] bin = new int[size];
		
		int index = bin.length - 1;
		do { 
			bin[index--] = dec % 2;
			dec = dec / 2;
		} while (dec > 0 && !(index < 0));

		return bin;
	}
	
	/**
	 * Convert a decimal to a hexadecimal integer array
	 * @param dec
	 * @param size
	 * @return int[size] 
	 */
	public static int[] convertDecToHex(int dec) {
		int size = getRequiredHexSize(dec);
		if(size % 2 != 0) return null;
		int[] bin = new int[size];
		
		int index = bin.length - 1;
		do { 
			bin[index--] = dec % 16;
			dec = dec / 16;
		} while (dec > 0 && !(index < 0));
		
		return bin;
	}
	
	/**
	 * Converts a binary value to a decimal value
	 * @param bin
	 * @return
	 */
	public static int convertBinToDec(int[] bin) {
		int value = 0;
			
		for(int i = bin.length - 1; i >= 0; i--) {
			value += bin[i] * Math.pow(2, i);
		}
		return value;
	}
	
	public static int convertHexToDec(String hex) {
		int value = 0;
		
		for(int i = 0; i < hex.length(); i++) {
			int curHex = hexToDecInt(hex.substring(i));
			value += curHex * Math.pow(16, i);
		}
		
		return value;
	}
	
	/**
	 * Returns the maximum size for a binary value
	 * 
	 * Formula: (2^n) - 1 where n > 0
	 * 
	 * @param decimal
	 * @return
	 */
	private static int getRequriedBinarySize(int decimal) {
		int size = 0;
		int count = 0;
		while(count < decimal) {
			count += Math.pow(2, size);
			size++;
		}
		
		// limit to 16
		if(size < 8) size = 8;
		if(size < 16 && size > 8) size = 16;
		
		return size;
	}
	
	private static int getRequiredHexSize(int decimal) {
		int size = 0;
		int count = 0;
		while(count < decimal) {
			count += Math.pow(16, size);
			size++;
		}
		
		// limit to 8
		if(size < 4) size = 4;
		if(size < 8 && size > 4) size = 8;
		
		return size;
	}
	
	private static String decToHexChar(int d) {
		switch(d) {
		case 10:
			return "A";
		case 11:
			return "B";
		case 12:
			return "C";
		case 13:
			return "D";
		case 14:
			return "E";
		case 15:
			return "F";
		}
		return String.valueOf(d);
	}
	
	private static int hexToDecInt(String s) {
		switch(s) {
		case "A":
			return 10;
		case "B":
			return 11;
		case "C":
			return 12;
		case "D":
			return 13;
		case "E":
			return 14;
		case "F":
			return 15;
		}
		return Integer.parseInt(s);
	}
	
	public static int asInt(int[] val) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < val.length; i++) { 
			sb.append(val[i]);
		}
		return Integer.parseInt(sb.toString());
	}
	
	public static void print(int[] val) {
		System.out.print("0x");
		for(int i = 0; i < val.length; i++) { 
			System.out.print(decToHexChar(val[i]));
		}
		System.out.println();
	}
}
