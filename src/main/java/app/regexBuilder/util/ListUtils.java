package app.regexBuilder.util;

import java.util.List;

public class ListUtils {
	
	public static boolean addIfNotNull(List<Object> list, Object object) {
		if(object != null) {
			list.add(object);
			return true;
		}
		return false;
	}
}
