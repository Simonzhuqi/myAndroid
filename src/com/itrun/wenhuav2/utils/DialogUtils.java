package com.itrun.wenhuav2.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {

	/**
	 * ����һ�����ȿ�
	 * @param context  ������
	 * @param dialog    ProgressDialog
	 * @return
	 */
	public static ProgressDialog CreateDialog(Context context, ProgressDialog dialog)
	{
		if(dialog == null)
			dialog = ProgressDialog.show(context, "", "���ڼ����У����Ժ�...", true, true); 
		return dialog;
	}
	
}
