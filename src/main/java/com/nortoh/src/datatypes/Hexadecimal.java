package com.nortoh.src.datatypes;

/**
 * Hexadecimal representation class
 * 
 * @author Christian Horton
 *
 */
public class Hexadecimal extends NumericSystem {

	private int[] hexadecimalRep;
	
	public Hexadecimal(int value) {
		super(16, value);
		
		hexadecimalRep = convertDecToHex();
	}
	
	/**
	 * Convert a decimal to a hexadecimal integer array
	 * @param dec
	 * @param size
	 * @return int[size] 
	 */
	public int[] convertDecToHex() {
		int size = getRequiredHexSize();
		int[] bin = new int[size];
		
		int index = bin.length - 1;
		do { 
			bin[index--] = value % base;
			value = value / base;
		} while (value > 0 && !(index < 0));
		
		return bin;
	}
	
	/**
	 * Returns the maximum size for a hexadecimal value
	 * 
	 * Formula: (16^n) - 1 where n > 0
	 * 
	 * @param decimal
	 * @return maximum size for array
	 */
	private int getRequiredHexSize() {
		int size = 0;
		int count = 0;
		while(count < value) {
			count += Math.pow(base, size);
			size++;
		}
		
		// limit to 8
		if(size < 4) size = 4;
		if(size < 8 && size > 4) size = 8;
		
		return size;
	}
	
	public int asDecimal() {
		int value = 0;
		String hex = toString().substring(2, toString().length());
		for(int i = 0; i <= hex.length(); i++) {
			int curHex = hexToDecInt(hex.substring(i));
			value += curHex * Math.pow(16, i);
		}
		
		return value;
	}
	
	public Binary asBinary() {
		Binary binary = null;
		
		return binary;
	}
	
	/**
	 * Return hexadecimal char 
	 * @param d
	 * @return
	 */
	private String decToHexChar(int d) {
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
	
	/**
	 * Return hexadecimal value 
	 * @param d
	 * @return
	 */
	private int hexToDecInt(String s) {
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

	/**
	 * Return the string representation of a hexadecimal value
	 */
	@Override
	public String toString() {
		StringBuilder hexString = new StringBuilder();
		hexString.append("0x");
		
		for(int i = 0; i < hexadecimalRep.length; i++) { 
			hexString.append(decToHexChar(hexadecimalRep[i]));
		}
		
		return hexString.toString();
	}
}
