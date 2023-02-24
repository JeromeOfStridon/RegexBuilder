package app.regexBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.regexBuilder.ClassMatch.CharacterClass;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class Node implements Serializable{
	
	protected Integer minSize = 1;
	protected Integer maxSize = 1;
	
	protected boolean lazy = false;
	
	public String renderSize() {
		if(minSize == null) {
			minSize = 0;
		}
		
		if(minSize == 1 && maxSize != null && maxSize == 1) {
			return "";
		}
		if(minSize == 0 && maxSize != null && maxSize == 1) {
			return "?"+(lazy?"?":"");
		}
		if(minSize == 1 && maxSize == null) {
			return "+"+(lazy?"?":"");
		}
		if(minSize == 0 && maxSize == null) {
			return "*"+(lazy?"?":"");
		}
		if(minSize.equals(maxSize)) {
			return "{"+minSize+"}"+(lazy?"?":"");
		}
		
		return "{"+minSize+","+(maxSize == null ? "": maxSize)+"}"+(lazy?"?":"");
		
		
	}
	
	
	
	protected void times(Integer minSize, Integer maxSize) {
		this.minSize = minSize;
		this.maxSize = maxSize;
	}

	public abstract String toString();





	
}
