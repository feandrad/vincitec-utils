package br.com.pontualmobile.utils.string;

/**
 * Created by feandrad on 27/09/16.
 */
public class StringUtils {
	
	public static boolean hasContent(String str) {
		return str != null && !isEmpty(str) && !isBlank(str);
	}
	
	public static boolean isEmpty(String str) {
		return str == null || !(str.trim().length() > 0);
	}
	
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
}
