package com.cywl.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cywl.launcher.model.AppInfo;
import com.cywl.launcher.model.Constances;
import com.cywl.launcher.view.AppStartWindow;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class MyApplication extends Application {

	private ArrayList<AppInfo> mAppList = new ArrayList<AppInfo>();
	private AppStartWindow mAppStartWindow;
	public static  MyApplication application ;

	private boolean isFirstClickGaode = true;
	private boolean isFirstClickDSJ = true;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initAppList();
        application=this;
		mAppStartWindow = new AppStartWindow(MyApplication.this);
		isFirstClickGaode = true;
		isFirstClickDSJ = true;
	}

	@SuppressLint("NewApi")
	public void initAppList() {
		if (mAppList.size() > 0) {
			mAppList.clear();
		}
		PackageManager pm = getPackageManager();

		Intent main = new Intent(Intent.ACTION_MAIN, null);
		main.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> apps = pm.queryIntentActivities(main, 0);
		Collections.sort(apps, new ResolveInfo.DisplayNameComparator(pm));
		if (apps != null) {

			for (int i = 0; i < apps.size(); i++) {
				ResolveInfo resolveinfo = apps.get(i);
				if (!ignore(this, resolveinfo.activityInfo.packageName)) {
					AppInfo info = new AppInfo();

					// cywl
					if (resolveinfo.activityInfo.packageName.equals("com.ligo.appshare_qichen")) {
						info.setAppName(this.getResources().getString(R.string.mobile_internet));
					} else {
						info.setAppName(resolveinfo.loadLabel(pm).toString());
					}

					info.setPkgName(resolveinfo.activityInfo.packageName);
					info.setFlags(resolveinfo.activityInfo.flags);
					info.setAppIcon(resolveinfo.activityInfo.loadIcon(pm));
					info.setAppintent(pm.getLaunchIntentForPackage(resolveinfo.activityInfo.packageName));
					mAppList.add(info);
				}
			}
		}
	}

	private boolean ignore(Context context, String pkg) {
		boolean ret = false;
		Log.d("lym", "pkg " + pkg);
		if (pkg.equals("com.zqc.screenout") 
				|| pkg.equals(Constances.PACKAGE_NAME_EDOG)
				|| pkg.equals("com.sohu.inputmethod.sogou")
//				|| pkg.equals("com.android.settings") 
				|| pkg.equals("com.cywl.launcher")
				|| pkg.equals("com.android.camera2") 
				|| pkg.equals("com.android.deskclock")
				|| pkg.equals("com.zqc.videolisttest") 
				|| pkg.equals("com.ligo.appshare_qichen")
//				|| pkg.equals("com.example.administrator.fm") 
				|| pkg.equals("com.softwinner.update")
				|| pkg.equals("com.google.android.inputmethod.latin") 
				|| pkg.equals("com.google.android.gms")
				|| pkg.equals("com.cywl.bt.activity") 
				|| pkg.equals(Constances.PACKAGE_NAME_FILE)) {
			ret = true;
		}
		/*
		 * if (pkg.equals("com.zqc.screenout") // ||
		 * pkg.equals(Constances.PACKAGE_NAME_EDOG) // ||
		 * pkg.equals("com.sohu.inputmethod.sogou") // ||
		 * pkg.equals("com.android.settings") || pkg.equals("com.cywl.launcher") ||
		 * pkg.equals("com.android.camera2") || pkg.equals("com.android.deskclock") ||
		 * pkg.equals("com.zqc.videolisttest") || pkg.equals("com.ligo.appshare_qichen")
		 * // || pkg.equals("com.example.administrator.fm") ||
		 * pkg.equals("com.softwinner.update") ||
		 * pkg.equals("com.google.android.inputmethod.latin") // ||
		 * pkg.equals("com.google.android.gms") // || pkg.equals("com.cywl.bt.activity")
		 * || pkg.equals(Constances.PACKAGE_NAME_FILE)) { ret = true; }
		 */
		return ret;
	}

	public ArrayList<AppInfo> getShowAppList() {
		if (mAppList.size() <= 0) {
			initAppList();
		}
		return mAppList;
	}

	public AppStartWindow getAppStartWindow() {
		if (mAppStartWindow == null) {
			synchronized (AppStartWindow.class) {
				if (mAppStartWindow == null) {
					mAppStartWindow = new AppStartWindow(MyApplication.this);
				}
			}
		}
		return mAppStartWindow;
	}

	/*
	 * public void showStartWindow(boolean isFull, String pkgName) { if
	 * (mAppStartWindow != null) { mAppStartWindow.showWindow(isFull, pkgName); } }
	 * 
	 * public void closeStartWindowDelayed() { if (mAppStartWindow != null) {
	 * mAppStartWindow.closeWindowDelayed(); } }
	 */

	public boolean getFirstClickGaode() {
		return isFirstClickGaode;
	}

	public void setFirstClickGaodeFalse() {
		isFirstClickGaode = false;
	}

	public boolean getFirstClickDSJ() {
		return isFirstClickDSJ;
	}

	public void setFirstClickDSJFalse() {
		isFirstClickDSJ = false;
	}
}
