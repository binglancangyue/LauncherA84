package com.cywl.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cywl.launcher.MyApplication;

/**
 * @author Altair
 * @date :2020.03.28 下午 11:59
 * @description:
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentGoHome = new Intent();
        intentGoHome.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intentGoHome.addCategory(Intent.CATEGORY_HOME); //"android.intent.category
        intentGoHome.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        // .HOME"
        Log.d("BootCompletedReceiver", "BootCompletedReceiver onReceive: ");
        MyApplication.application.startActivity(intentGoHome);
    }
}
