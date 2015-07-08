package com.itrun.wenhuav2.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {

	/**
	 * 创建一个进度框
	 * @param context  上下文
	 * @param dialog    ProgressDialog
	 * @return
	 */
	public static ProgressDialog CreateDialog(Context context, ProgressDialog dialog)
	{
		if(dialog == null)
			dialog = ProgressDialog.show(context, "", "正在加载中，请稍后...", true, true); 
		return dialog;
	}
	
}
