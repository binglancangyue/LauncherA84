package utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by zdt on 2019/5/30.
 */

@SuppressLint("NewApi")
public class LaunchApp {
	private static final String TAG = "LaunchApp";

    /**
     * 根据包名类名启动相应APP  sdk<=19 时使用
     */
    public static void launchForClass(Context context, String pkg){
        if (pkg != null) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            throw new RuntimeException("invalid app type = " + pkg);
        }
    }

    public static void launchForClass(Context context, String pkg, String activity){
        if (pkg != null && activity != null) {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(pkg,activity);
            intent.setComponent(comp);
            intent.setAction("android.intent.action.MAIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            throw new RuntimeException("invalid app type = " + pkg);
        }
    }


    /**
     * 根据包名启动相应的app
     **/
    public static void launch(Context context, String packageName) {
        if (packageName != null) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }


    /**
	 * 需要share systemuid
	 * 需要权限 FORCE_STOP_PACKAGES
	 */
    public static void forceStopPackage(Context context, String pkgName){
        Log.d(TAG, "forceStopPackage_start-pkg = "+pkgName);
        try{
            ActivityManager am = (ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(am, pkgName);
            //am.killBackgroundProcesses(pkgName); 
            //am.forceStopPackage(pkgName);
        }
        catch (Exception e){
        	Log.d(TAG, "forceStopPackage Exception , pkg: "+pkgName);
            e.printStackTrace();
        }
        //Log.d(TAG, "forceStopPackage_end-pkg = "+pkgName);
    }


    /**
     ** 根据包名获取进程PID
     * @param context
     * @param pkgName
     * @return pid
     */
    public static int getPidByPackageName(Context context, String pkgName) {
    	ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
    	List<ActivityManager.RunningAppProcessInfo> mRunningProcess = am.getRunningAppProcesses();
		int pid = -1;
		for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess){
			if(amProcess.processName.equals(pkgName)){
				pid=amProcess.pid;
				break;
			}
		}
		Log.i(TAG, pkgName + " PID: " + pid);
    	return pid;
    }


    /**
     ** 根据persistentId杀死进程
     * @param persistentId
     */
    public static void killPkgByPersistentId(Context context, int persistentId) {
    	ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
    	am.removeTask(persistentId, ActivityManager.REMOVE_TASK_KILL_PROCESS);
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param pkgname 某个APP名称
     */
    public static boolean isAppForeground(Context context, String pkgname) {
        if (context == null || TextUtils.isEmpty(pkgname)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (pkgname.equals(cpn.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    public static ComponentName getAppForegroundList(Context context){
        if (context == null) {
            return null;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
                return cpn;
        }else {
            return null;
        }
    }


    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    Log.d(TAG, "xxxx=" + processInfo.importance + ", processName" + processInfo.processName);
                    return true;
                }

            }
        }
        return false;
    }
}
