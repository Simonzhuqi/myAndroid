package com.itrun.wenhuav2.utils;
/**
 * 限定文字的长度，如果超过18，后面加省略号
 * @author Administrator
 *
 */
public class StringUtils {
	public static String titleLimit(String str){
		
		if(str != null){
			if(str.length() > 20){
				str = str.substring(0,18)+"...";
			}
		}
		return str;
		
	}
}
