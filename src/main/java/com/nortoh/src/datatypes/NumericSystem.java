package com.nortoh.src.datatypes;

/**
 * TODO:
 * Eventually make the input Object based to handle checks for the input type. i.e. Binary, Decimal, Hexadecimal
 * 
 * @author christian
 *
 */
public abstract class NumericSystem {

	protected int base;
	protected int value;
	protected boolean isNeg;
	
	public NumericSystem(int base, int value) {
		this.isNeg = value < 0;
		this.base = base;
		this.value = Math.abs(value);
	}
	
	/**
	 * Returns the base
	 * @return
	 */
	public int getBase() {
		return base;
	}
}

