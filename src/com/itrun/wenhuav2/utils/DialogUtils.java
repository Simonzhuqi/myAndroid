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
	public static ProgressDialog createDialog(Context context, ProgressDialog dialog){
	
			dialog = ProgressDialog.show(context, "", "���ڼ����У����Ժ�...", true, true);
			dialog.setCanceledOnTouchOutside(false);
			return dialog;
	}
	
}
