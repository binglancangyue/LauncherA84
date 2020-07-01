package com.cywl.launcher.receiver;

import com.cywl.launcher.MyApplication;
import com.cywl.launcher.model.Constances;
import android.annotation.SuppressLint;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import utils.LaunchApp;
import utils.SplitUtil;

@SuppressLint("NewApi")
public class MyBroadcastReceiver extends BroadcastReceiver {
    
    public static final int FLAG_ACTIVITY_RUN_IN_RIGHT_WINDOW = 0x00000200;
    public static final String POWER_ON_START = "powerOnStart";
    public static final String EXTRA_CAM_TYPE = "REQ_CAM_TYPE";
    public static final String ACTION_HOME_PRESS = "android.intent.action.HOME_PRESS";
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    private static final String TAG = "LauncherReceive";

    private static final String ACTION_TXZ_SEND = "com.txznet.adapter.send";
    private static final String OPEN_BT = "bluetooth.open";
    private static final String CLOSE_BT = "bluetooth.close";
    private static final String OPEN_EDOG = "e-dog.open";
    private static final String CLOSE_EDOG = "e-dog.close";
    private static final String OPEN_CLD = "cld.open";
    private static final String CLOSE_CLD = "cld.close";
    private MyApplication myApplication;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            Log.d(TAG, "context =" + context + ";intent=" + intent);
            return;
        }
        
        Log.i(TAG, "action = " + intent.getAction());
        if (intent.getAction().equals(ACTION)) {
           Log.i(TAG, "Receive BOOT_COMPLETED!");
   /*         if (MyPreference.isBootUp()) {
                Intent it = new Intent(context, RecorderActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra(POWER_ON_START, true);
                it.addFlags(FLAG_ACTIVITY_RUN_IN_RIGHT_WINDOW);
                it.putExtra(EXTRA_CAM_TYPE, CameraInfo.CAMERA_FACING_FRONT);
                context.startActivity(it);
            }     */
             
        }
//        else if (/*intent.getAction().equals(ACTION_HOME_PRESS)
//                || */intent.getAction().equals(RecordService.ACTION_START_APP)) {
//            Log.i(TAG, "Receive ACTION_HOME_PRESS!");
//            Intent it = new Intent(context, RecorderActivity.class);
//            it.putExtra(POWER_ON_START, true);
//            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            it.addFlags(FLAG_ACTIVITY_RUN_IN_RIGHT_WINDOW);
//            it.putExtra(EXTRA_CAM_TYPE, CameraInfo.CAMERA_FACING_FRONT);
//            context.startActivity(it);
//        }
        else if (intent.getAction().equals(ACTION_TXZ_SEND)) {
        	Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int type = bundle.getInt("key_type");
				String  actionStirng = bundle.getString("action");
				Log.i(TAG, "TXZ_ACTION, type: " + type + ", actionStirng: " + actionStirng);

				if (type == 1040){ //蓝牙控制
					if(OPEN_BT.equals(actionStirng)) {
						LaunchApp.launch(context, Constances.PACKAGE_NAME_BLUETOOTH);
					} else if(CLOSE_BT.equals(actionStirng)) {
						//LaunchApp.forceStopPackage(context, Constances.PACKAGE_NAME_BLUETOOTH);
					}
				} else if (type == 1071) {
					if(OPEN_EDOG.equals(actionStirng)) { //打开电子狗
						LaunchApp.launch(context, Constances.PACKAGE_NAME_DEAULT_EDOG);
					} else if(CLOSE_EDOG.equals(actionStirng)) { //关闭电子狗
						int leftStack = SplitUtil.getLeftStackId(context);
						Log.d(TAG, "leftStack = " + leftStack);
						RecentTaskInfo taskInfo = SplitUtil.getTopTaskOfStack(context, leftStack);
						
						if (taskInfo != null) {
							Intent it = taskInfo.baseIntent;
							if (Constances.PACKAGE_NAME_DEAULT_EDOG.equals(it.getComponent().getPackageName())) {
								int persistentId = taskInfo.persistentId;
								Log.d(TAG, "persistentId = " + persistentId);
								LaunchApp.killPkgByPersistentId(context, persistentId);
							}
						}
					} else if (OPEN_CLD.equals(actionStirng)) { //打开凯立德
						LaunchApp.launch(context, Constances.PACKAGE_NAME_CLD_2);
					} else if (CLOSE_CLD.equals(actionStirng)) { //关闭凯立德
						int leftStack = SplitUtil.getLeftStackId(context);
						Log.d(TAG, "leftStack = " + leftStack);
						RecentTaskInfo taskInfo = SplitUtil.getTopTaskOfStack(context, leftStack);
						
						if (taskInfo != null) {
							Intent it = taskInfo.baseIntent;
							if (Constances.PACKAGE_NAME_CLD_2.equals(it.getComponent().getPackageName())) {
								int persistentId = taskInfo.persistentId;
								Log.d(TAG, "persistentId = " + persistentId);
								LaunchApp.killPkgByPersistentId(context, persistentId);
							}
						}
					}
				}
			}
        }else if(Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())){
        	System.exit(0);
		}
    }
    
}
