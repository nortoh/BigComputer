package com.nortoh.src.datatypes;

import com.nortoh.src.input.InputHandler.InputType;

/**
 * TODO:
 * Set the two's complement when the value passed through
 * the constructor is negative. Achieve this with sign 
 * checking and Math.abs
 * 
 * Incorporate the use of InputType
 * 
 * 
 * 
 * @author christian
 *
 */
public class Binary extends NumericSystem {
	
	private int[] binaryRep;
	private boolean carryFlag;
	
	/**
	 * Binary Constructor
	 * 
	 * @param value
	 */
	public Binary(int value, InputType type) {
		super(2, value);
		
		if(type.equals(InputType.DECIMAL)) {
			this.binaryRep = convertDecToBin();
		} else if(type.equals(InputType.BINARY)) {
			this.binaryRep = convertBinToBin(); 
		}
		
		this.carryFlag = false;
		if(isNeg) apply2sComplement();
	}
	
	/**
	 * Convert a decimal to a binary integer array.
	 * 
	 * @param dec
	 * @param size
	 * @return int[size]
	 */
	private int[] convertDecToBin() {
		int size = getRequriedBinarySize();
		int[] bin = new int[size];
		
		int index = bin.length - 1;
		do { 
			bin[index--] = value % base;
			value = value / base;
		} while (value > 0 && !(index < 0));

		return bin;
	}
	
	private int[] convertBinToBin() {
		int size = Integer.toString(value).length(); // soon to be an object cast
		int[] bin = new int[size];
		
		for(int index = 0; index < size; index++) {
			int dec = Integer.parseInt(Character.toString(String.valueOf(value).charAt(index))); // LOL?! BUT IT WORKS!... for now
			bin[index] = dec;
		}
		
		return bin;
	}
	
	/**
	 * Returns the maximum size for a binary value.
	 * 
	 * Formula: (2^n) - 1 where n > 0
	 * 
	 * @param value
	 * @return maximum size for array
	 */
	private int getRequriedBinarySize() {
		int size = 0;
		int count = 0;
		while(count < value) {
			count += Math.pow(base, size);
			size++;
		}
		
		// limit to 16
		if(size < 8) size = 8;
		if(size < 16 && size > 8) size = 16;
		
		return size;
	}
	
	/**
	 * Converts a binary value to a decimal value.
	 * 
	 * @param bin
	 * @return
	 */
	public int asDecimal() {
		int value = 0;
			
		for(int i = binaryRep.length - 1; i >= 0; i--) {
			value += binaryRep[i] * Math.pow(2, i);
		}
		
		if(isNeg) value = value * -1;
		
		return value;
	}
	
	/**
	 * Applies 2's complement to the int array
	 * 
	 * TODO: fix bug with HSB being 0 (or do we?)
	 * 
	 * i.e. 2^8 - 256
	 * 
	 * ... but on an 8-bit negative supported machine
	 * 
	 * Max Positive: 127
	 * Max Negative: -128
	 * 
	 * @param bin
	 * @return
	 */
	private void apply2sComplement() {
		// inverse all the bits
		for(int i = 0; i < binaryRep.length; i++) {
			binaryRep[i] = flipBit(binaryRep[i]);
		}
		
		Binary addOne = addBinary(new Binary(1, InputType.DECIMAL));
		binaryRep[0] = 1; // bug?
		binaryRep = addOne.binaryRep;
	}
	
	/**
	 * Add a binary value.
	 * 
	 * @param binary
	 * @return 
	 */
	public Binary addBinary(Binary binary) {
		int[] master = this.getBinaryArray();
		int[] addr = binary.getBinaryArray();
		int[] result = new int[master.length];
		
		/* 
		 * TODO: 
		 * We want to make the arrays the same size to prevent IndexOutOfBounds.
		 * 
		 * If the master bin is larger than the addr bin, we must copy addr to
		 * a new array with the same size as the master bin.
		 * 
		 * If the addr bin is larger than the master bin, we must copy master to
		 * a new array with the same size as the addr bin.
		 * 
		 * Add carry flag for when overflow occurs. Eventually changing name to 
		 * overflow flag.
		 * 
		 * Maybe set the result[] length where the size is checked
		 * 
		 */
		
		if(addr.length < master.length) {
			int[] tempAddr = new int[master.length];
			System.arraycopy(addr, 0, tempAddr, 0, master.length);
			addr = tempAddr;
		} else if(addr.length > master.length) {
			int[] tempMaster = new int[addr.length];
			System.arraycopy(master, 0, tempMaster, 0, addr.length);
			master = tempMaster;
		}
		
		int carry = 0;
		
		for(int index = master.length - 1; index > 0; index--) {
			int tempA = master[index]; 							// store the contents of the master into tempA
			int tempB = addr[index];							// store the contents of the addr into tempB
			int tempResult = 0;									// result of operation
			
			tempResult = tempA + tempB + carry; 				// add both tempA and tempB and the carry bit
			if(tempResult % 2 == 0 && tempResult != 0) { 		// when 1 + 1 = 2
				carry = 1; 										// set the carry to 1
				tempResult = 0; 								// set the temp result to 0
			} else if(tempResult % 3 == 0 && tempResult != 0) { // when 1 + 1 + 1 = 3
				carry = 1;										// set the carry to one
				tempResult = 1;									// set the temp result to 1
			} else {
				carry = 0;										// set the carry to 0 if neither (i.e. 1 + 0 = 1)
			}
			result[index] = tempResult;
		}
		
		//TODO: Fix carry flag
//		this.carryFlag = (carry == 1);
		this.binaryRep = result;
		return this;
	}
	
	/**
	 * TODO:
	 * Can be used to set certain bits to be variable bits
	 * 
	 * Checks to see if the bit at that index of the array is a One
	 * @param index
	 * @return
	 */
	private boolean isOne(int[] bin, int index) {
		if(bin[index] == 1) return true;
		
		return false;
	}
	
	/**
	 * Return the flipped bit
	 * 
	 * @param bit
	 * @return
	 */
	private int flipBit(int bit) {
		if(bit == 0) return 1;
		if(bit == 1) return 0;
		return bit;
	}
	
	/**
	 * Return the carry flag.
	 * 
	 * @return
	 */
	public boolean carryFlag() {
		return this.carryFlag;
	}
	
	/**
	 * Return the bin array.
	 * 
	 * @return
	 */
	public int[] getBinaryArray() {
		return this.binaryRep;
	}
	
	/**
	 * Returns the binary as a hexadecimal value;
	 * 
	 * @return
	 */
	public Hexadecimal asHex() {
		return new Hexadecimal(value);
	}
	
	/**
	 * Return the string representation of a binary value.
	 */
	@Override
	public String toString() {
		StringBuilder binaryString = new StringBuilder();
		binaryString.append("b");
		
		for(int i = 0; i < binaryRep.length; i++) { 
			binaryString.append(binaryRep[i]);
		}
		
		return binaryString.toString();
	}
}
