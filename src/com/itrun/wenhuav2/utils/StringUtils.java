package com.itrun.wenhuav2.utils;
/**
 * �޶����ֵĳ��ȣ��������18�������ʡ�Ժ�
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
