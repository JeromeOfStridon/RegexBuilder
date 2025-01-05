package com.regexbuilder;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class Node implements Serializable{
	
	protected Integer minSize = 1;
	protected Integer maxSize = 1;
	
	protected boolean lazy = false;
	
	String renderSize() {
		
		if(integerEquals(minSize, maxSize, 1)) {
			return "";
		}
		if(integerEquals(minSize, 0) && integerEquals(maxSize, 1)) {
			return "?"+(lazy?"?":"");
		}
		if(integerEquals(minSize, 1) && maxSize == null) {
			return "+"+(lazy?"?":"");
		}
		if((minSize == null || minSize == 0) && maxSize == null) {
			return "*"+(lazy?"?":"");
		}
		
		if(integerEquals(minSize, maxSize)) {
			return "{"+minSize+"}";
		}
		
		return "{"+(minSize != 0 ? minSize : "")+","+(maxSize == null ? "": maxSize)+"}"+(lazy?"?":"");
		
	}
	
	Node setQuantity(Integer minSize, Integer maxSize) {
		this.minSize = minSize;
		this.maxSize = maxSize;
		return this;
	}
	
	Node setLazy(boolean lazy) {
		this.lazy = lazy;
		return this;
	}

	public abstract String toString();


	boolean integerEquals(Integer...integers) {
		for(int i = 1; i < integers.length; i++) {
			if(integers[0] == null && integers[i] != null) {
				return false;
			}
			if(!integers[0].equals(integers[i])) {
				return false;
			}
		}
		return true;
	}


	
}
