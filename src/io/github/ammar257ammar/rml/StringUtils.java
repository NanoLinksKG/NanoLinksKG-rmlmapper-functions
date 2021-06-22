package io.github.ammar257ammar.rml;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
	
	public static String join(String str1, String str2){
		return str1 + str2;
	}
	
	public static boolean stringContains(String str1, String str2){
		return str1.contains(str2);
	}
	
	public static boolean stringNotContains(String str1, String str2){
		return !str1.contains(str2);
	}
	
	public static boolean stringNotContainsExcept(String str1, String str2, String str3){
		
		List<String> list = Arrays.asList(str3.split("#"));
		
		if(!str1.contains(str2)){
			if(list.contains(str1)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

	public static boolean isRangeValue(String str1) {
		
		if((str1.contains("-") && str1.indexOf('-') != 0) || str1.startsWith("<") || str1.startsWith(">")) {
			return true;
		}else {
			return false;
		}
	}
	

	public static boolean isValueWithQualifier(String str1, String str2){
		
		if((!str1.contains("-") || str1.indexOf('-') == 0) && !str1.startsWith("<") && !str1.startsWith(">")) {
			
			if(str2 != null && !"".equals(str2.trim())) {
				return true;
			}else {
				return false;
			}	
		}else {
			return false;
		}		
	}

	public static String constructValueWithQualifier(String str1, String str2){
		
		if(str2 != null && !"".equals(str2.trim())) {
			
			if(str1 != null && !"".equals(str1.trim())) {
				return str1 + " +/- " + str2;
			}else {
				return "";
			}
		}
		
		return "";
	}
	
	public static boolean isValueWithoutQualifier(String str1, String str2){
		
		if((!str1.contains("-") || str1.indexOf('-') == 0) && !str1.startsWith("<") && !str1.startsWith(">")) {
			
			if(str2 != null && !"".equals(str2.trim())) {
				return false;
			}else {
				return true;
			}	
		}else {
			return false;
		}
	}
	
	public static String fixExpressionFloatingPoint(String num) {
		
		num = num.replaceAll("\"", "");
		num = num.replaceAll("'", "");
		num = num.replaceAll(" ", "");

		if(num.contains(",")) {
			num = num.replaceAll(",", ".");
		}
		
		BigDecimal d = new BigDecimal(num);
		
		return d.toPlainString();
	}
}
