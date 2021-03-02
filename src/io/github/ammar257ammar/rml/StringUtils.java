package io.github.ammar257ammar.rml;

import java.util.ArrayList;
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
	
	public static List<String> cartesianProductOfList(List list1){
		
		List<String> out = new ArrayList<String>();
		
		for(int i=0; i < list1.size(); i++){
			for(int j=0; j < list1.size(); j++){
			
				String item1 = (String) list1.get(i);
				String item2 = (String) list1.get(j);
				
				if(!item1.equals(item2)){
					out.add(item1+"-"+item2);
				}
			}
		}
		
		return out;
	}
}
