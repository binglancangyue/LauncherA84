package com.cywl.launcher.view;

import com.cywl.launcher.R;
import com.cywl.launcher.model.Constances;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import utils.Utils;

@SuppressLint("NewApi")
public class AppStartWindow {
    private final String TAG = "AppStartWindow";
    private boolean isShow = false;

    private static final int MSG_CLOSE_WINDOW = 0;
    private static final int MSG_CLOSE_MYSELF = 1;
    private static final String ACTION_APP_SHOW = "com.cywl.appshow";

    private String mCurrentPak;
    private int mCloseDelay = 500;

    private WindowManager.LayoutParams mWmParams;
    private WindowManager mWindowManager;

    //private Activity mActivity;
    private Application mActivity;
    private View mView;
    private ImageView startView;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_CLOSE_MYSELF:
                    Log.d(TAG, "close myself...");
                    closeWindow();
                    break;

                case MSG_CLOSE_WINDOW:
                    Log.d(TAG, "close window...");
                    closeWindow();
                    break;
            }
            super.dispatchMessage(msg);
        }
    };

    public AppStartWindow(/*Activity*/ Application activit) {
        this.mActivity = activit;
        //mWindowManager = (WindowManager) activit.getApplication().getSystemService(Context.WINDOW_SERVICE);
        mWindowManager = (WindowManager) activit.getSystemService(Context.WINDOW_SERVICE);

		/*IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_APP_SHOW);
		mActivity.registerReceiver(receiver, intentFilter);*/
    }

    public void showWindow(boolean isFull, String pkgName, int delay) {
        if (!isShow) {
            Log.d(TAG, "show win, pkg: " + pkgName);
            isShow = true;
            if (Constances.IS_ZKJA) {
                isFull = true;
            }
            //LayoutInflater inflater = LayoutInflater.from(mActivity.getApplication());
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            if (mView == null) {
                mView = (RelativeLayout) inflater.inflate(R.layout.window_app_start, null);
            }

            mCurrentPak = pkgName;
            mCloseDelay = delay;
            startView = (ImageView) mView.findViewById(R.id.start_view);
            if (Constances.PACKAGE_NAME_AUTOLITE.equals(pkgName)) {
                startView.setBackgroundResource(isFull ? R.drawable.start_gaode_full2 : R.drawable.start_gaode_split2);
            } else if (Constances.PACKAGE_NAME_KUWO_MUSIC.equals(pkgName)) {
                startView.setBackgroundResource(isFull ? R.drawable.start_kuwo_full : R.drawable.start_kuwo_split);
            } else if (Constances.PACKAGE_NAME_DSJ.equals(pkgName)) {
                startView.setBackgroundResource(isFull ? R.drawable.start_dsj_full : R.drawable.start_dsj_split);
            }

			/*startView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					closeWindow();
				}
			});*/
            startView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.d(TAG, "startView onGlobalLayout...");
                    int delay = (Constances.PACKAGE_NAME_AUTOLITE.equals(mCurrentPak)
                            || Constances.PACKAGE_NAME_DSJ.equals(mCurrentPak)) ? 12000 : 6000;
                    mHandler.removeMessages(MSG_CLOSE_MYSELF);
                    mHandler.sendEmptyMessageDelayed(MSG_CLOSE_MYSELF, delay);
                    //处理完后remove掉
                    startView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

            mWmParams = new WindowManager.LayoutParams();
            mWmParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
            mWmParams.format = PixelFormat.RGBA_8888;
            mWmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
            mWmParams.gravity = Gravity.TOP | Gravity.LEFT;
            mWmParams.x = 0;
            mWmParams.y = Constances.PACKAGE_NAME_AUTOLITE.equals(pkgName) ? 0 : 30;
            Log.d(TAG, "mWmParams.y: " + mWmParams.y);
            if (isFull) {
                mWmParams.width = 1024;
            } else {
                mWmParams.width = Constances.PACKAGE_NAME_AUTOLITE.equals(pkgName) ? 657 : 654;
            }
            mWmParams.height = Constances.PACKAGE_NAME_AUTOLITE.equals(pkgName) ? 517 : 487;

            mWindowManager.addView(mView, mWmParams);

            if (Constances.PACKAGE_NAME_DSJ.equals(pkgName)) {
                if (!Utils.isNetworkConnected(mActivity)) {
                    Log.d(TAG, "network not connect...");
                    closeWindow();
                }
            }
        }
    }

    public void closeWindowDelayed() {
        Log.d(TAG, "close window delayed : " + mCloseDelay);
//		if (Constances.PACKAGE_NAME_AUTOLITE.equals(mCurrentPak)) {
//			
//		} else if (Constances.PACKAGE_NAME_DSJ.equals(mCurrentPak)) {
//			
//		} else {
//			mHandler.sendEmptyMessageDelayed(MSG_CLOSE_WINDOW, 500);
//		}
        mHandler.sendEmptyMessageDelayed(MSG_CLOSE_WINDOW, mCloseDelay);
    }

    public void closeWindow() {
        //unRegisterReceiver();
        mHandler.removeMessages(MSG_CLOSE_MYSELF);
        mCurrentPak = "";
        mCloseDelay = 500;
        if (mView != null) {
            mWindowManager.removeView(mView);
            mView = null;
        }
        isShow = false;
    }

	/*public void unRegisterReceiver() {
		if (receiver != null) {
			mActivity.unregisterReceiver(receiver);
			receiver = null;
		}
	}*/

    public boolean isShow() {
        return isShow;
    }

	/*private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			if (ACTION_APP_SHOW.equals(intent.getAction())) {
            	Log.d(TAG, "ACTION_APP_SHOW...");
            	mHandler.sendEmptyMessageDelayed(MSG_CLOSE_WINDOW, 500);
            }
		}
	};*/
}
