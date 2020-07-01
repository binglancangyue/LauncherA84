package com.cywl.launcher;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import cn.kuwo.autosdk.api.KWAPI;
import cn.kuwo.autosdk.api.OnEnterListener;
import cn.kuwo.autosdk.api.OnExitListener;
import cn.kuwo.autosdk.api.PlayState;
import utils.LaunchApp;
import utils.SplitUtil;
import utils.Utils;

import com.cywl.launcher.R;
import com.cywl.launcher.R.string;
import com.cywl.launcher.adapter.Qc7SpliteGridViewAdapter;
import com.cywl.launcher.model.Constances;
import com.cywl.launcher.model.ItemData;
import com.cywl.launcher.view.AppStartWindow;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

@SuppressLint("NewApi")
public class HomeActivity extends Activity implements OnEnterListener, OnExitListener {
    private static final String TAG = "HomeActivity";
    public static final int CAMERA_ID_RIGHT = 2;
    public static final int RESULT_CODE = 100;
    private Qc7SpliteGridViewAdapter qc7SpliteGridViewAdapter;
    private GridView qc9ThreeGridView;
    public static final String ISCLICKRIGHT = "is_click_right";
    private static final String ACTION_LOCATION_PRESS_DOWN = "RESTOR_PREES_DOWN";
    private static final String ACTION_RECORDER_SHOWCAMERA = "com.cywl.recorder.showcamera";
    private static final String ACTION_APP_SHOW = "com.cywl.appshow";
    private static final String ACTION_CLOSE_KUWO = "com.cywl.close.muics";
    public static final int MSG_UPDATE_BACK_PREVIEW = 0;
    public static final int MSG_UPDATE_FRONT_PREVIEW = 1;
    public static final int MSG_UPDATE_RIGHT_PREVIEW = 2;
    private AudioManager audioManager;
    // private MyPreference mPref;

    private static final String ACTION_SPLIT_WINDOW_HAS_CHANGE = "android.intent.action.SPLIT_WINDOW_HAS_CHANGED";
    private ActivityManager mActivityManager;
    public static final int MW_NORMAL_STACK_WINDOW = 0;// 分屏
    public static final int MW_MAX_STACK_WINDOW = 1;// 全屏
    public static final int MW_DEFAULT_STACK_WINDOW = MW_NORMAL_STACK_WINDOW;// 默认分屏
    private static final int MSG_UPDATE_VIEWS = 0;


    String txzVolumeUp = "volume.up";// 增加音量
    String txzVolumeDown = "volume.down";// 降低音量

    private MyApplication myApplication;
    private AppStartWindow mAppStartWindow;
    private KWAPI mKwapi;
    private Context mContext;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_VIEWS:
                    if (qc7SpliteGridViewAdapter != null) {
                        qc7SpliteGridViewAdapter.updateViews(msg.arg1);
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getWindow().getDecorView();
        int visibility = View.STATUS_BAR_TRANSIENT | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        view.setSystemUiVisibility(visibility);
        setContentView(R.layout.activity_home_recorder);
        this.mContext = this;
        myApplication = (MyApplication) getApplication();
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        initKuwoApi();
        registerBroadCastReceiver();
        // mPref = MyPreference.getInstance(HomeActivity.this);
//		Intent intent = new Intent(this, RecordService.class);
//        startService(intent);
//		bindService(new Intent(this, RecordService.class), mRecConnection, Context.BIND_AUTO_CREATE);
        initView();
        initData();
        // <zhuangdt> <20190516> <增加语音唤醒词修改> begin
        intTXZWakeUpName();
        // <zhuangdt> <20190516> <增加语音唤醒词修改> end
    }

    private void registerBroadCastReceiver() {
        // TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_LOCATION_PRESS_DOWN);
//		filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
//		filter.addAction(RecordService.ACTION_STOP_APP);
//		filter.addAction(ACTION_RECORDER_SHOWCAMERA);
        filter.addAction(ACTION_SPLIT_WINDOW_HAS_CHANGE);
        filter.addAction(ACTION_APP_SHOW);
        filter.addAction(ACTION_CLOSE_KUWO);

        filter.addAction(ACTION_TXZ_SEND);
//        filter.addAction(Constances.KUWO_ACTION);
        registerReceiver(myReceiver, filter);
    }

    private void initView() {
        qc9ThreeGridView = (GridView) findViewById(R.id.gridview_9three_nosplite);
        mAppStartWindow = myApplication.getAppStartWindow();
    }

    /**
     * 将GridView改成单行横向布局
     */
    private void changeGridView() {
        // item宽度
        int itemWidth = dip2px(this, 198);
        // item之间的间隔
//        int itemPaddingH = dip2px(this, 10);
        int itemPaddingH = 10;

        int size = Constances.m9ThreePackageOversea.length;
        // 计算GridView宽度
        int gridviewWidth = size * (itemWidth + itemPaddingH);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        qc9ThreeGridView.setLayoutParams(params);
        qc9ThreeGridView.setColumnWidth(itemWidth);
        qc9ThreeGridView.setGravity(Gravity.CENTER_VERTICAL);
        qc9ThreeGridView.setHorizontalSpacing(itemPaddingH);
        qc9ThreeGridView.setStretchMode(GridView.NO_STRETCH);
        qc9ThreeGridView.setNumColumns(size);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void initData() {
//        changeGridView();
        qc7SpliteGridViewAdapter = new Qc7SpliteGridViewAdapter(this, getQc7SpliteItemData());
        qc9ThreeGridView.setAdapter(qc7SpliteGridViewAdapter);
        qc9ThreeGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packetName = qc7SpliteGridViewAdapter.getItem(position).getmPackage();
                String defaultMapPkg;
                String settingsPackageName;
                Log.i(TAG, "packetName: " + packetName + "position" + position);
                String defaultMap;
                switch (packetName) {
                    case Constances.PACKAGE_NAME_APPLIST:
                        Intent intent = new Intent(HomeActivity.this, AppListActivity.class);
                        intent.putExtra("choose_package", false);
                        HomeActivity.this.startActivity(intent);
                        break;
                    case Constances.PACKAGE_NAME_AUTOLITE:
                        defaultMapPkg = Constances.PACKAGE_NAME_AUTOLITE;
                        if (Constances.DEFAULT_MAP.equals(Constances.MAP_CLD)) {
                            defaultMapPkg = Constances.PACKAGE_NAME_CLD_2;
                        }
                        defaultMap = Utils.getUtilInstance(HomeActivity.this)
                                .getString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME, defaultMapPkg);
                        Log.d(TAG, "defaultMapPkg: " + defaultMapPkg + " , defaultMap: " + defaultMap);
                        if (Constances.PACKAGE_NAME_AUTOLITE.equals(defaultMap)) {
                            boolean isFullScreen = SplitUtil.isFullWindow(HomeActivity.this);
                            int delay;
                            if (myApplication.getFirstClickGaode()) {
                                delay = 2000;
                                myApplication.setFirstClickGaodeFalse();
                                Log.d(TAG, "First Click Gaode, delay: " + delay);
                            } else {
                                boolean isGaodeRunning = LaunchApp.isAppRunning(HomeActivity.this,
                                        Constances.PACKAGE_NAME_AUTOLITE);
                                delay = isGaodeRunning ? 500 : 2000;
                                Log.d(TAG, "isGaodeRunning: " + isGaodeRunning + ", delay: " + delay);
                            }
                            if (mAppStartWindow != null) {
                                mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_AUTOLITE, delay);
                            }
                            launchByPackageName(packetName);
                        } else if (Constances.PACKAGE_NAME_CLD_2.equals(defaultMap)) {
                            launchByPackageName(Constances.PACKAGE_NAME_CLD_2);
                        }
                        break;
                    case Constances.PACKAGE_NAME_KUWO_MUSIC:
                        if (!Constances.isOversea) {
                            boolean isFullScreen = SplitUtil.isFullWindow(HomeActivity.this);
                            if (mAppStartWindow != null) {
                                mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_KUWO_MUSIC, 500);
                            }
                            launchByPackageName(packetName);
                        } else {
                            launchByPackageName(packetName);
                        }
                        break;
                    case Constances.PACKAGE_NAME_SYGIC_MAP:
                        defaultMapPkg = Constances.DEFAULT_MAP;
                        settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");
                        if (settingsPackageName.equals(Constances.PACKAGE_NAME_AUTOLITE)) {
                            if (defaultMapPkg.equals(Constances.MAP_GOOGLE)) {
                                launchByPackageName(Constances.PACKAGE_NAME_GOOGLE_MAP);
                                Log.d(TAG, Constances.MAP_GOOGLE);
                            } else {
                                Log.d(TAG, defaultMapPkg);
                                launchByPackageName(Constances.PACKAGE_NAME_SYGIC_MAP);
                            }
                        } else {
                            launchByPackageName(settingsPackageName);
                        }
                        break;
                    case Constances.PACKAGE_NAME_LEKE_MAP:
                        defaultMapPkg = Constances.DEFAULT_MAP;
                        settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");
//						String settingsPackageName=	Utils.getUtilInstance(HomeActivity.this)
//								.getString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME, Constances.PACKAGE_NAME_LEKE_MAP);

                        Log.d(TAG, "settingsPackageName:" + settingsPackageName + ";");
                        if (settingsPackageName == null || settingsPackageName.equals("null")) {
                            launchByPackageName(Constances.PACKAGE_NAME_GOOGLE_MAP);// }
                        } else {
                            launchByPackageName(settingsPackageName);
                        }
                        break;
                    case Constances.PACKAGE_NAME_CLD_2:
                        defaultMapPkg = Constances.DEFAULT_MAP;
                        settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");

                        Log.d(TAG, "settingsPackageName:" + settingsPackageName + ";");
                        if (settingsPackageName == null || settingsPackageName.equals("null")) {
                            launchByPackageName(Constances.PACKAGE_NAME_KAILIDE);
//							}
                        } else {
                            launchByPackageName(settingsPackageName);
                        }
                        break;
                    case Constances.PACKAGE_NAME_AMAPAUTO:
                        defaultMapPkg = Constances.DEFAULT_MAP;
                        settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");

                        Log.d(TAG, "settingsPackageName:" + settingsPackageName + ";");
                        if (settingsPackageName == null || settingsPackageName.equals("null")) {
                            launchByPackageName(Constances.PACKAGE_NAME_AMAPAUTO);
//						}
                        } else {
                            launchByPackageName(settingsPackageName);
                        }
                        break;
                    default:
                        launchByPackageName(packetName);
                        break;
                }

//				if (Constances.PACKAGE_NAME_APPLIST.equals(packetName)) {
//					Intent intent = new Intent(HomeActivity.this, AppListActivity.class);
//					intent.putExtra("choose_package", false);
//					HomeActivity.this.startActivity(intent);
//				} else if (Constances.PACKAGE_NAME_AUTOLITE.equals(packetName)) {
//					 defaultMapPkg = Constances.PACKAGE_NAME_AUTOLITE;
//					if (Constances.DEFAULT_MAP.equals(Constances.MAP_CLD)) {
//						defaultMapPkg = Constances.PACKAGE_NAME_CLD_2;
//					}
//					 defaultMap = Utils.getUtilInstance(HomeActivity.this)
//							.getString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME, defaultMapPkg);
//					Log.d(TAG, "defaultMapPkg: " + defaultMapPkg + " , defaultMap: " + defaultMap);
//					if (Constances.PACKAGE_NAME_AUTOLITE.equals(defaultMap)) {
//						boolean isFullScreen = SplitUtil.isFullWindow(HomeActivity.this);
//						int delay;
//						if (myApplication.getFirstClickGaode()) {
//							delay = 2000;
//							myApplication.setFirstClickGaodeFalse();
//							Log.d(TAG, "First Click Gaode, delay: " + delay);
//						} else {
//							boolean isGaodeRunning = LaunchApp.isAppRunning(HomeActivity.this,
//									Constances.PACKAGE_NAME_AUTOLITE);
//							delay = isGaodeRunning ? 500 : 2000;
//							Log.d(TAG, "isGaodeRunning: " + isGaodeRunning + ", delay: " + delay);
//						}
//						if (mAppStartWindow != null) {
//							mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_AUTOLITE, delay);
//						}
//						launchByPackageName(packetName);
//					} else if (Constances.PACKAGE_NAME_CLD_2.equals(defaultMap)) {
//						launchByPackageName(Constances.PACKAGE_NAME_CLD_2);
//					}
//				} else if (Constances.PACKAGE_NAME_KUWO_MUSIC.equals(packetName)) {
//					if (!Constances.isOversea) {
//						boolean isFullScreen = SplitUtil.isFullWindow(HomeActivity.this);
//						if (mAppStartWindow != null) {
//							mAppStartWindow.showWindow(isFullScreen, Constances.PACKAGE_NAME_KUWO_MUSIC, 500);
//						}
//						launchByPackageName(packetName);
//					} else {
//						launchByPackageName(packetName);
//					}
//				} else if (Constances.PACKAGE_NAME_SYGIC_MAP.equals(packetName)) {
//					 defaultMapPkg = Constances.DEFAULT_MAP;
//					 settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");
//					if (settingsPackageName.equals(Constances.PACKAGE_NAME_AUTOLITE)) {
//						if (defaultMapPkg.equals(Constances.MAP_GOOGLE)) {
//							launchByPackageName(Constances.PACKAGE_NAME_GOOGLE_MAP);
//							Log.d(TAG, Constances.MAP_GOOGLE);
//						} else {
//							Log.d(TAG, defaultMapPkg);
//							launchByPackageName(Constances.PACKAGE_NAME_SYGIC_MAP);
//						}
//					} else {
//						launchByPackageName(settingsPackageName);
//					}
//				} else if (Constances.PACKAGE_NAME_LEKE_MAP.equals(packetName)) {
//					 defaultMapPkg = Constances.DEFAULT_MAP;
//					 settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");
////					String settingsPackageName=	Utils.getUtilInstance(HomeActivity.this)
////							.getString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME, Constances.PACKAGE_NAME_LEKE_MAP);
//
//					Log.d(TAG, "settingsPackageName:" + settingsPackageName + ";");
//					if (settingsPackageName == null || settingsPackageName.equals("null")) {
//						launchByPackageName(Constances.PACKAGE_NAME_GOOGLE_MAP);// }
//					} else {
//						launchByPackageName(settingsPackageName);
//					}
//				} else if (Constances.PACKAGE_NAME_CLD_2.equals(packetName)) {
//					 defaultMapPkg = Constances.DEFAULT_MAP;
//					 settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");
//
//					Log.d(TAG, "settingsPackageName:" + settingsPackageName + ";");
//					if (settingsPackageName == null || settingsPackageName.equals("null")) {
//						launchByPackageName(Constances.PACKAGE_NAME_KAILIDE);
////						}
//					} else {
//						launchByPackageName(settingsPackageName);
//					}
//				} else if (Constances.PACKAGE_NAME_AMAPAUTO.equals(packetName)) {
//					 defaultMapPkg = Constances.DEFAULT_MAP;
//					 settingsPackageName = Settings.System.getString(getContentResolver(), "bar_map_start_pkg");
//
//					Log.d(TAG, "settingsPackageName:" + settingsPackageName + ";");
//					if (settingsPackageName == null || settingsPackageName.equals("null")) {
//						launchByPackageName(Constances.PACKAGE_NAME_AMAPAUTO);
////						}
//					} else {
//						launchByPackageName(settingsPackageName);
//					}
//				} else {
//					launchByPackageName(packetName);
//				}
            }
        });

        qc9ThreeGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
//					Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constances.PACKAGE_NAME_APPLIST);
//					if(launchIntent!=null){
//						Log.d("", "launchIntent!=null");
//						launchIntent.putExtra("choose_package", true);
//						startActivityForResult(launchIntent, 0);
//					}
                    Intent intent = new Intent(HomeActivity.this, AppListActivity.class);
                    intent.putExtra("choose_package", true);
                    startActivityForResult(intent, 0);
                }
                return true;
            }
        });

//		mIv9ThreeSettings.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				launchByPackageName(Constances.PACKAGE_NAME_SETTINGS);
//			}
//		});
//        mIv9ThreeApplists.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				launchByPackageName(Constances.PACKAGE_NAME_APPLIST);
//				HomeActivity.this.startActivity(new Intent(HomeActivity.this,AppListActivity.class));
//			}
//		});
    }

    private void initKuwoApi() {
        // key值不要为空
        mKwapi = KWAPI.createKWAPI(this, "auto");
        mKwapi.registerEnterListener(this);
        mKwapi.registerExitListener(this);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Log.d("lym", "onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
//		if (mRecService != null) {
//			 if (mRecService.getRecordCallback(CameraInfo.CAMERA_FACING_FRONT) == this) {
//				 mRecService.setRecordCallback(CameraInfo.CAMERA_FACING_FRONT, null);
//	            }
//            unbindService(mRecConnection);
//            mRecService = null;
//        }
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
    }

//	private RecordService mRecService = null;
//	private boolean isNeedUpdate = false;
//	private boolean mIsMuteOn;
//	private ServiceConnection mRecConnection = new ServiceConnection() {
//		public void onServiceConnected(ComponentName className, IBinder service) {
//			Log.i(TAG, "---onServiceConnected()");
//			if (mRecService == null) {
//				mRecService = ((RecordService.LocalBinder) service).getService();
//			}
//			
//			if (isNeedUpdate) {
//				updateFrontSurfaceView();
//				updateBackSurfaceView();
//				// remove by zdt
//				//updateRightSurfaceView();
//				isNeedUpdate = false;
//			}
//			
//			if (mRecService != null ) {
//				initRecorder();
//		    }
//		}
//
//		public void onServiceDisconnected(ComponentName className) {
//			if (null != mRecService) {
//				mRecService = null;
//			}
//		}
//	};

    public List<ItemData> getQc7SpliteItemData() {
        List<ItemData> itemData = new ArrayList<ItemData>();
        ItemData data = null;
        if (Constances.isOversea) {
            for (int i = 0; i < Constances.m9ThreePackageOversea.length; i++) {
                data = new ItemData(Constances.m9ThreePackageOversea[i], Constances.img9ThreeNameStringID[i],
                        Constances.img9ThreeImage[i]);
                itemData.add(data);
            }
        } else {
            for (int i = 0; i < Constances.m9ThreePackage.length; i++) {
                data = new ItemData(Constances.m9ThreePackage[i], Constances.img9ThreeNameStringID[i],
                        Constances.img9ThreeImage[i]);
                itemData.add(data);
            }
        }
        return itemData;
    }

    private void launchByPackageName(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Log.i(TAG, "package name is null!");
            return;
        }
//		if (Constances.PACKAGE_NAME_AUTOLITE.equals(packageName)
//				|| Constances.PACKAGE_NAME_KAILIDE.equals(packageName)) {
//			// FunctionCore handle
//			Intent mIntent = new Intent();
//			mIntent.setAction(Constances.ACTION_NAME_ONLY_ONE_CAN_START);
//			mIntent.putExtra(Constances.KEY_WHICH_NAV,
//					packageName.equals(Constances.PACKAGE_NAME_AUTOLITE) ? Constances.KEY_GAODE
//							: Constances.KEY_KAILIDE);
//			sendBroadcast(mIntent);
//		} else {

        try {
            Log.i(TAG, "packageName" + packageName);
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
            startActivity(launchIntent);
        } catch (Exception e) {
            // Toast.makeText(HomeRecorderActivity.this,
            // getText(R.string.tv_app_not_install), Toast.LENGTH_SHORT).show();

        }
//		}

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    private static final String OPEN_CLD = "cld.open";
    private static final String CLOSE_CLD = "cld.close";
    private static final String ACTION_TXZ_SEND = "com.txznet.adapter.send";

    BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onReceive: action " + intent.getAction());
            if (ACTION_LOCATION_PRESS_DOWN.equals(intent.getAction())) {
                String defaultMapPkg = Constances.PACKAGE_NAME_AUTOLITE;
                if (Constances.DEFAULT_MAP.equals(Constances.MAP_CLD)) {
                    defaultMapPkg = Constances.PACKAGE_NAME_CLD_2;
                }
                String packageName = Utils.getUtilInstance(HomeActivity.this)
                        .getString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME, defaultMapPkg);
                Log.d("lym", "packageName " + packageName);
                launchByPackageName(packageName);

            } else if (ACTION_SPLIT_WINDOW_HAS_CHANGE.equals(intent.getAction())) {
                Log.d("", "zdt --- ACTION_SPLIT_WINDOW_HAS_CHANGE");
                /*
                 * if (mActivityManager != null) { int leftStackId =
                 * mActivityManager.getLeftStackId(); int leftStatus =
                 * mActivityManager.getWindowSizeStatus(leftStackId); Log.d("",
                 * "zdt --- leftStackId: " + leftStackId + ", leftStatus: " + leftStatus);
                 * Message msg = new Message(); msg.what = MSG_UPDATE_VIEWS; msg.arg1 =
                 * leftStatus; mHandler.sendMessage(msg); }
                 */
//                int stackId = SplitUtil.getStackBoxId(HomeActivity.this);
//                int leftStatus = SplitUtil.getWindowSizeStatus(HomeActivity.this, stackId);
                boolean isFullScreen = SplitUtil.isFullWindow(context);
                // Log.d("", "zdt --- stackId: " + stackId + ", leftStatus: " + leftStatus);
                Log.d("", "zdt --- isFullScreen: " + isFullScreen);
                Message msg = new Message();
                msg.what = MSG_UPDATE_VIEWS;
                msg.arg1 = isFullScreen ? MW_MAX_STACK_WINDOW : MW_NORMAL_STACK_WINDOW;
                mHandler.sendMessage(msg);

            } else if (ACTION_APP_SHOW.equals(intent.getAction())) {
                Log.d(TAG, "ACTION_APP_SHOW...");
                if (mAppStartWindow != null) {
                    mAppStartWindow.closeWindowDelayed();
                    // mAppStartWindow.closeWindow();
                }
            } else if (ACTION_CLOSE_KUWO.equals(intent.getAction())) {
                Log.d(TAG, "broadcast close kuwo...");
                mKwapi.exitAPP();
            }
            /*
             * else if(Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())){ Log.i(TAG,
             * "onReceive  android.intent.action.MEDIA_MOUNTED  开始录像"); // startRecord();
             * }else if(RecordService.ACTION_STOP_APP.equals(intent.getAction())) {
             * stopRecord(); }else if(ACTION_RECORDER_SHOWCAMERA.equals(intent.getAction()))
             * {
             *
             * boolean showback =(Boolean) intent.getExtra("showback", false); Log.e(TAG,
             * "showback:"+showback); if(showback){
             *
             * frontPreview.setVisibility(View.GONE);
             * backPreview.setVisibility(View.VISIBLE); }else{
             * frontPreview.setVisibility(View.VISIBLE);
             * backPreview.setVisibility(View.GONE); }
             *
             * }
             */
            else if (intent.getAction().equals(ACTION_TXZ_SEND)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    int type = bundle.getInt("key_type");
                    String actionStirng = bundle.getString("action");
                    Log.d(TAG, "TXZ_ACTION, type: " + type + ", actionStirng:" + actionStirng);

                    if (type == 1071) {
                        if (OPEN_CLD.equals(actionStirng)) { // 打开凯立德
                            launchByPackageName(Constances.PACKAGE_NAME_CLD_2);
                            Log.d("aaa", "open cld");
                        } else if (CLOSE_CLD.equals(actionStirng)) { // 关闭凯立德
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
                } else {
                    Log.d(TAG, "TXZ_ACTION, bundle null ");
                }
            } else if (intent.getAction().equals(Constances.KUWO_ACTION)) {
                int msg = intent.getIntExtra("music", 1);
                setKuWoPlayState(msg);
            }
        }

    };

//	private void stopRecord() {
//		if(mIsRecording && mRecService != null) {
//			mRecService.stopRecording();
//			Intent intent3=new Intent();
//            intent3.setAction("CLOSE_VIDEO_APP");
//            mRecService.sendBroadcast(intent3);
//		}
//	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            String packageName = data.getStringExtra("package");
            Intent intent = new Intent(Constances.ACTION_DEFAULT_MAP);
            String startappPackageNameppPackageName = null;
            String appLabel = null;
            Utils.getUtilInstance(HomeActivity.this).putString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME, packageName);
            Log.d(TAG, "defaultMap package: " + packageName);
            if (packageName.equals(Constances.PACKAGE_NAME_CLD_2)) {
//				boolean isAutoLite = false;
//				Intent mIntent = new Intent(Constances.ACTION_NAME_IS_AUTOLITE);
//				mIntent.putExtra("isAutoLite", isAutoLite);
//				sendBroadcast(mIntent);
                appLabel = Constances.MAP_CLD;
                startappPackageNameppPackageName = Constances.PACKAGE_NAME_CLD_2;
            }
            if (packageName.equals(Constances.PACKAGE_NAME_AUTOLITE)) {
//				boolean isAutoLite = true;
//				Intent mIntent = new Intent(Constances.ACTION_NAME_IS_AUTOLITE);
//				mIntent.putExtra("isAutoLite", isAutoLite);
//				sendBroadcast(mIntent);
                appLabel = Constances.MAP_GAODE;
                startappPackageNameppPackageName = Constances.PACKAGE_NAME_AUTOLITE;
            }
            if (packageName.equals(Constances.PACKAGE_NAME_LEKE_MAP)) {
//				boolean isAutoLite = false;
//				Intent mIntent = new Intent(Constances.ACTION_NAME_IS_AUTOLITE);
//				mIntent.putExtra("isAutoLite", isAutoLite);
//				sendBroadcast(mIntent);
                appLabel = Constances.MAP_LEKE;
                startappPackageNameppPackageName = Constances.PACKAGE_NAME_LEKE_MAP;
            }
            if (packageName.equals(Constances.PACKAGE_NAME_GOOGLE_MAP)) {
                appLabel = Constances.MAP_GOOGLE;
                startappPackageNameppPackageName = Constances.PACKAGE_NAME_GOOGLE_MAP;
            }
            if (packageName.equals(Constances.PACKAGE_NAME_SYGIC_MAP)) {
                appLabel = Constances.MAP_SYGIC;
                startappPackageNameppPackageName = Constances.PACKAGE_NAME_SYGIC_MAP;
            }
            if (packageName.equals(Constances.PACKAGE_NAME_AMAPAUTO)) {
                appLabel = Constances.MAP_GAODE;
                startappPackageNameppPackageName = Constances.PACKAGE_NAME_AMAPAUTO;
            }
            // 通知同行者
            intent.putExtra("defaultMap", appLabel);
            sendBroadcast(intent);
            // 通知SystemUI导航栏
            Settings.System.putString(getContentResolver(), "bar_map_start_pkg", startappPackageNameppPackageName);
            Log.d(TAG, "defaultMap appLabel: " + appLabel);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (mAppStartWindow != null) {
            Log.d(TAG, "onResume, close window...");
            mAppStartWindow.closeWindow();
        }
        SharedPreferences preferences = getSharedPreferences("isVideo",
                Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
        Log.i("hy", "HomeRecorderActivity::222222:" + preferences.getBoolean("isVideo", false));

        boolean isFullScreen = SplitUtil.isFullWindow(mContext);
        Log.d("", "zdt --- isFullScreen: " + isFullScreen);
        Message msg = new Message();
        msg.what = MSG_UPDATE_VIEWS;
        msg.arg1 = isFullScreen ? MW_MAX_STACK_WINDOW : MW_NORMAL_STACK_WINDOW;
        mHandler.sendMessage(msg);

    }

//	private void initRecorder() {
//		 Log.i(TAG, "initRecorder() :setRecordCallback()");
//		 mIsMuteOn = mPref.isMute();
//		 mRecService.setRecordCallback(CameraInfo.CAMERA_FACING_FRONT, HomeActivity.this);
//		 mRecService.setMute(true, CameraInfo.CAMERA_FACING_BACK);
//		 mRecService.setMute(mIsMuteOn, CameraInfo.CAMERA_FACING_FRONT);
//	}
//	private void startRecord() {
//		 if (mRecService != null) {
//			 mRecService.setLockOnce(false);
//                 if (Storage.getTotalSpace() < 0) {
//                     // todo more gentlly hint
//                     Log.d(TAG, "startRecording sd not mounted");
//                     Toast.makeText(HomeActivity.this, R.string.sdcard_not_found,
//                             Toast.LENGTH_LONG).show();
//                     
//                     Intent intent8=new Intent();
//                     intent8.setAction("com.action.other_Text");
//                     intent8.putExtra("otherText", "TF卡不存在");
//                     mRecService.sendBroadcast(intent8);
//                     return;
//                 } else if (Storage.getSdcardBlockSize() < 64 * 1024) {
//                     Log.d(TAG,
//                             "check sdcard failed, sdcard block size "
//                                     + (Storage.getSdcardBlockSize() / 1024) + "k");
//                     
//                     Intent intent8=new Intent();
//                     intent8.setAction("com.action.other_Text");
//                     intent8.putExtra("otherText", "录制不成功，请格式SD卡");
//                     mRecService.sendBroadcast(intent8);
//                     
////                     showFormatMsgDialog();
//                     return;
//                 } else if (mRecService.isMiniMode()) {
//                     Toast.makeText(HomeActivity.this, R.string.device_busy, Toast.LENGTH_LONG)
//                             .show();
//                     return;
//                 }
//                 mIsRecording = true;
//                 Log.e(TAG, "onRecordStart");
//                 mRecService.startRecording();
//			     Intent intent3=new Intent();
//		         intent3.setAction("CLOSE_VIDEO_APP");
//		         mRecService.sendBroadcast(intent3);
//		         String locale = Locale.getDefault().toString();
//		         if (locale.equals("zh_CN") || locale.equals("zh_TW")) {
//		                MediaPlayer mediaPlayer = MediaPlayer.create(mRecService, R.raw.start);
//		                mediaPlayer.start();
//		         }
//         }
//	}

    // <zhuangdt> <20190516> <增加语音唤醒词修改> begin
    private void intTXZWakeUpName() {
        boolean firstInit = Utils.getUtilInstance(HomeActivity.this).getBoolean("first_init_txz", true);
        if (firstInit) {
            String name = SystemProperties.get("ro.txz.wakeup");
            Log.i(TAG, "ro.txz.wakeup: " + name);
            if (TextUtils.isEmpty(name)) {
                name = "东风";
            }
            Log.i(TAG, "wakeup name: " + name);
            Settings.System.putString(getContentResolver(), "cywl_wakeup_keywords", name);
            Utils.getUtilInstance(HomeActivity.this).putBoolean("first_init_txz", false);
        }

        // 设置开机默认导航
        String defaultMapPkg;
        Log.d(TAG, "intTXZWakeUpName:defaultMap1 " + Constances.DEFAULT_MAP);

        if (Constances.DEFAULT_MAP.equals(Constances.MAP_CLD)) {
            defaultMapPkg = Constances.PACKAGE_NAME_CLD_2;
        } else if (Constances.DEFAULT_MAP.equals(Constances.MAP_SYGIC)) {
            defaultMapPkg = Constances.PACKAGE_NAME_SYGIC_MAP;
        } else if (Constances.DEFAULT_MAP.equals(Constances.MAP_LEKE)) {
            defaultMapPkg = Constances.PACKAGE_NAME_LEKE_MAP;
        } else if (Constances.DEFAULT_MAP.equals(Constances.MAP_GOOGLE)) {
            defaultMapPkg = Constances.PACKAGE_NAME_GOOGLE_MAP;
        } else {
            defaultMapPkg = Constances.PACKAGE_NAME_AUTOLITE;
        }
        Log.d(TAG, "intTXZWakeUpName:defaultMapPkg " + defaultMapPkg);
        String defaultMap = Utils.getUtilInstance(HomeActivity.this).getString(Constances.KEY_DEFAULT_MAP_PACKAGE_NAME,
                defaultMapPkg);
        String map;
        String pkg;
        Log.d(TAG, "intTXZWakeUpName:defaultMap " + defaultMap);
        if (Constances.PACKAGE_NAME_CLD_2.equals(defaultMap)) {
            map = Constances.MAP_CLD;
            pkg = Constances.PACKAGE_NAME_CLD_2;
        } else if (Constances.PACKAGE_NAME_LEKE_MAP.equals(defaultMap)) {
            map = Constances.MAP_LEKE;
            pkg = Constances.PACKAGE_NAME_LEKE_MAP;
        } else if (Constances.PACKAGE_NAME_SYGIC_MAP.equals(defaultMap)) {
            map = Constances.MAP_SYGIC;
            pkg = Constances.PACKAGE_NAME_SYGIC_MAP;
        } else if (Constances.PACKAGE_NAME_GOOGLE_MAP.equals(defaultMap)) {
            map = Constances.MAP_GOOGLE;
            pkg = Constances.PACKAGE_NAME_GOOGLE_MAP;
        } else {
            map = Constances.MAP_GAODE;
            pkg = Constances.PACKAGE_NAME_AUTOLITE;
        }
        Log.i(TAG, "default map: " + map + ", pkg: " + pkg);

        // 通知同行者
        Intent intent = new Intent(Constances.ACTION_DEFAULT_MAP);
        intent.putExtra("defaultMap", map);
        sendBroadcast(intent); // 通知SystemUI导航栏
        Settings.System.putString(getContentResolver(), "bar_map_start_pkg", pkg);
    }
    // <zhuangdt> <20190516> <增加语音唤醒词修改> end


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        return super.onKeyUp(keyCode, event);
    }

    //根据按键,切换歌曲
    private void setKuWoPlayState(int state) {
        if (mKwapi != null) {
            if (mKwapi.isKuwoRunning()) {
                if (state == 0) {
                    mKwapi.setPlayState(PlayState.STATE_PRE);
                    Log.d(TAG, "setKuWoPlayState:STATE_PRE ");
                } else {
                    mKwapi.setPlayState(PlayState.STATE_NEXT);
                    Log.d(TAG, "setKuWoPlayState:STATE_NEXT ");
                }
            } else {
                Log.d(TAG, "setKuWoPlayState:isKuwoRunning = false ");
            }

        } else {
            Log.d(TAG, "setKuWoPlayState:mKwapi = null ");
        }
    }

    private void setVolume(int state) {
        Intent intent = new Intent();
        intent.setAction(Constances.TXZ_ACTION);
        if (state == 0) {
            intent.putExtra("txzVolumeUp", txzVolumeUp);
        } else {
            intent.putExtra("txzVolumeDown", txzVolumeDown);
        }
        sendBroadcast(intent);
    }

    @Override
    public void onEnter() {
        Intent intentopen = new Intent("com.cywl.open.dianshijia");
        mContext.sendBroadcast(intentopen);
        Log.d(TAG, "Kuwo onEnter: ");
    }

    @Override
    public void onExit() {

        Intent intentopen = new Intent("com.cywl.close.dianshijia");
        mContext.sendBroadcast(intentopen);
        Log.d(TAG, "Kuwo onExit: ");
    }

    //    private void showTXZView() {
//        Intent it = new Intent("com.txznet.txz.config.change");
//        it.putExtra("type", "screen");
//        it.putExtra("x", 0);
//        it.putExtra("y", 0);
//        it.putExtra("width", 1024);
//        it.putExtra("height", 517);
//        sendBroadcast(it);
//        TXZAsrManager.getInstance().triggerRecordButton();
//    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}
