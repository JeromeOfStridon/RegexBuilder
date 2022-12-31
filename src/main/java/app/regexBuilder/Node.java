package app.regexBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.regexBuilder.ClassMatch.CharacterClass;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class Node {
	
	protected Integer minSize = 1;
	protected Integer maxSize = 1;
	
	public String renderSize() {
		if(minSize == null) {
			minSize = 0;
		}
		
		if(minSize == 1 && maxSize != null && maxSize == 1) {
			return "";
		}
		if(minSize == 0 && maxSize != null && maxSize == 1) {
			return "?";
		}
		if(minSize == 1 && maxSize == null) {
			return "+";
		}
		if(minSize == 0 && maxSize == null) {
			return "*";
		}
		if(minSize.equals(maxSize)) {
			return "{"+minSize+"}";
		}
		
		return "{"+minSize+","+(maxSize == null ? "": maxSize)+"}";
		
		
	}
	
	
	
	protected void times(Integer minSize, Integer maxSize) {
		this.minSize = minSize;
		this.maxSize = maxSize;
	}

	public abstract String toString();





	
}
