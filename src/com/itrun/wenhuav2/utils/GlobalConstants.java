package com.itrun.wenhuav2.utils;

import com.itrun.wenhuav2.R;

import android.content.Context;


public class GlobalConstants {
	
	/**
	 * ��ȡ�������ĵ�ַ
	 * @param context
	 * @return
	 */
	public static String GetServer(Context context)
	{
		return context.getResources().getString(R.string.server_set);
	}

}
