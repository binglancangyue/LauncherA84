package com.cywl.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import utils.LaunchApp;
import utils.SplitUtil;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.cywl.launcher.R;
import com.cywl.launcher.model.AppInfo;
import com.cywl.launcher.model.Constances;
import com.cywl.launcher.view.AppStartWindow;
@SuppressLint("NewApi")
public class AppListActivity extends Activity {
	private static final boolean DEBUG = false;
	private static final String TAG = "AppListActivity";

	private static final String ACTION_OPEN_DSJ = "com.cywl.open.dianshijia";
	private static final String ACTION_APP_SHOW = "com.cywl.appshow";

	private GridView gridView;
	private ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
	private MyAdapter adapter;
	private boolean isSelecteDefault = false;

	private MyApplication myApplication;
	private AppStartWindow mAppStartWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_applist);
		myApplication = (MyApplication)getApplication();
		Intent intent = getIntent();
		if (null != intent) {
			isSelecteDefault = intent.getBooleanExtra("choose_package", false);
		}
		initView();
		initData();
	}

	private void initData() {
		getAppList();
	}

	private void getAppList() {
		/*if (appList.size() > 0) {
			appList.clear();
		}
		PackageManager pm = getPackageManager();
		
        Intent main = new Intent(Intent.ACTION_MAIN, null);
        main.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = pm.queryIntentActivities(main, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(pm));
        if (apps != null) {
            for (int i = 0; i < apps.size(); i++) {
            	ResolveInfo resolveinfo = apps.get(i);
               	if (!ignore(this,resolveinfo.activityInfo.packageName)) {
            		AppInfo info = new AppInfo();
            		
            		//cywl
            		if(resolveinfo.activityInfo.packageName.equals("com.ligo.appshare_qichen")){
            			info.setAppName(this.getResources().getString(R.string.mobile_internet));
            		}else {
            			info.setAppName(resolveinfo.loadLabel(pm).toString());
            		}
            		
            		info.setPkgName(resolveinfo.activityInfo.packageName);
            		info.setFlags(resolveinfo.activityInfo.flags);
            		info.setAppIcon(resolveinfo.activityInfo.loadIcon(pm));
            		info.setAppintent(pm.getLaunchIntentForPackage(resolveinfo.activityInfo.packageName));

            		appList.add(info);

               	}
            	
            }
        }*/
		if (myApplication.getShowAppList().size() <= 0) {
			myApplication.initAppList();
		}
		appList = myApplication.getShowAppList();
		adapter.notifyDataSetChanged();
	}
	
    public static boolean ignore(Context context,String pkg){
    	boolean ret = false;
//    	if(pkg.equals("com.tencent.qqmusic")
//			|| pkg.equals("com.iflytek.inputmethod")
//			|| pkg.equals("com.android.settings")
//			|| pkg.equals("se.setting")
//			|| pkg.equals("se.fm")
//			|| pkg.equals("com.sohu.inputmethod.sogou")){
//    		ret = true;
//    	}

    	if (pkg.equals("com.zqc.screenout")
    			|| pkg.equals("com.cywl.launcher")
    			|| pkg.equals("com.android.camera2") 
    			|| pkg.equals("com.android.deskclock")
    			|| pkg.equals("com.zqc.videolisttest")
    			|| pkg.equals("com.ligo.appshare_qichen")
    			|| pkg.equals("com.softwinner.update")) {
    		ret = true;
		}
    	return ret;
    }
	
	
	
	
	@SuppressLint("NewApi")
	private void addApp(PackageManager pm, PackageInfo packageInfo) {
		AppInfo info = new AppInfo();
		info.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
		info.setPkgName(packageInfo.packageName);
		info.setFlags(packageInfo.applicationInfo.flags);
		info.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
		info.setAppintent(pm.getLaunchIntentForPackage(packageInfo.packageName));
		appList.add(info);
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gv);
		mAppStartWindow = myApplication.getAppStartWindow();
		adapter = new MyAdapter();
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (isSelecteDefault) {
					String packageName = appList.get(arg2).getPkgName();
					Intent intent = new Intent();
					intent.putExtra("package", packageName);
					AppListActivity.this.setResult(HomeActivity.RESULT_CODE, intent);
					AppListActivity.this.finish();
				} else {
					if (Constances.IS_NEED_PASSWD_ENTER_STORE && Constances.PACKAGE_NAME_GOOGLESTORE.equals(appList.get(arg2).getPkgName())) {
//						enterPassword(appList.get(arg2).getAppintent());
					}else{
						String mPackName = appList.get(arg2).getPkgName();
                       Log.d(TAG, "mPackName :-->else: " + mPackName);

						if (Constances.PACKAGE_NAME_WIFI_HOT.equals(mPackName)){
							Log.d(TAG, "mPackName :" + mPackName);
							Intent intent = new Intent();
							intent.setComponent(new ComponentName(Constances.PACKAGE_NAME_SETTINGS,
									Constances.PACKAGE_NAME_WIFI_HOT));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						} else if (Constances.PACKAGE_NAME_AUTOLITE.equals(mPackName)){
							Log.d(TAG, "mPackName :-->else2: gaode");
							boolean isFullScreen = SplitUtil.isFullWindow(AppListActivity.this);
							int delay;
							if (myApplication.getFirstClickGaode()) {
								delay = 2000;
								myApplication.setFirstClickGaodeFalse();
								Log.d(TAG, "First Click Gaode, delay: " + delay);
							} else {
								boolean isGaodeRunning = LaunchApp.isAppRunning(AppListActivity.this, Constances.PACKAGE_NAME_AUTOLITE);
								delay = isGaodeRunning ? 500 : 2000;
								Log.d(TAG, "isGaodeRunning: " + isGaodeRunning + ", delay: " + delay);
							}
							if (mAppStartWindow != null) {
								mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_AUTOLITE, delay);
							}
							startActivity(appList.get(arg2).getAppintent());
						} else if (Constances.PACKAGE_NAME_KUWO_MUSIC.equals(mPackName)){
							Log.d(TAG, "mPackName :-->else2: kuwo");
							boolean isFullScreen = SplitUtil.isFullWindow(AppListActivity.this);
							if (mAppStartWindow != null) {
								mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_KUWO_MUSIC, 500);
							}
							startActivity(appList.get(arg2).getAppintent());
						} else if (Constances.PACKAGE_NAME_DSJ.equals(mPackName)){
							Log.d(TAG, "mPackName :-->else2: dianshijia");
							sendBroadcast(new Intent(ACTION_OPEN_DSJ));
							boolean isFullScreen = SplitUtil.isFullWindow(AppListActivity.this);
							int delay;
							if (myApplication.getFirstClickDSJ()) {
								delay = 2000;
								myApplication.setFirstClickDSJFalse();;
								Log.d(TAG, "First Click DSJ, delay: " + delay);
							} else {
								boolean isDSJRunning = LaunchApp.isAppRunning(AppListActivity.this, Constances.PACKAGE_NAME_DSJ);
								delay = isDSJRunning ? 500 : 2000;
								Log.d(TAG, "isDSJRunning: " + isDSJRunning + ", delay: " + delay);
							}
							if (mAppStartWindow != null) {
								mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_DSJ, delay);
							}
							startActivity(appList.get(arg2).getAppintent());
						} else {
							Log.d(TAG, "mPackName :-->else2: " + mPackName);
							startActivity(appList.get(arg2).getAppintent());
						}
					}
				}

			}
		});

		/*gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				AppInfo aInfo = appList.get(arg2);
				if((aInfo.getFlags() & ApplicationInfo.FLAG_SYSTEM) == 0){
					Uri uri = Uri.parse("package:" + aInfo.getPkgName());
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_DELETE);
					intent.setData(uri);
					startActivity(intent);
				}
				return true;
			}

		});*/
		registerReceiver();
	}
	

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constances.ACTION_NAME_CLOSE_APPLIST);
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(ACTION_APP_SHOW);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);
		IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(homeReceiver, homeFilter);
		IntentFilter closeFilter = new IntentFilter(Constances.ACTION_NAME_CLOSE_APPLIST);
		registerReceiver(closeReceiver, closeFilter);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
				if (DEBUG) Log.i(TAG, "action = " + action);
				getAppList();
			} else if(ACTION_APP_SHOW.equals(action)) {
				Log.i(TAG, "ACTION_APP_SHOW...");
				if (mAppStartWindow != null) {
            		mAppStartWindow.closeWindowDelayed();
					//mAppStartWindow.closeWindow();
            	}
			}
		}
	};

	private BroadcastReceiver homeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (DEBUG) Log.i(TAG, "action = "+action);
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				Log.i(TAG, "Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
				String reason = intent.getStringExtra(Constances.SYSTEM_REASON);
				if (TextUtils.equals(reason, Constances.SYSTEM_HOME_KEY)) {
					if (DEBUG) Log.i(TAG, "press home key !");
					AppListActivity.this.finish();
				} else if (TextUtils.equals(reason, Constances.SYSTEM_HOME_KEY_LONG)) {
					if (DEBUG) Log.i(TAG, "long press home key !");
				}
			}

		}
	};
	
	private BroadcastReceiver closeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(Constances.ACTION_NAME_CLOSE_APPLIST)){
				if (DEBUG) Log.i(TAG, "close applist!");
				AppListActivity.this.finish();
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume...");
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		unregisterReceiver(homeReceiver);
		unregisterReceiver(closeReceiver);
		super.onDestroy();
	}

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return appList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(AppListActivity.this, R.layout.item_apps, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.img_apps);
			imageView.setBackground(appList.get(position).getAppIcon());
			TextView textView = (TextView) view.findViewById(R.id.tv_apps);
			if (Constances.PACKAGE_NAME_DEAULT_EDOG.equals(appList.get(position).getPkgName())) {
				textView.setText(getResources().getString(R.string.name_9three_edog));
			} else {
				textView.setText(appList.get(position).getAppName());
			}
			return view;
		}
	}
}
