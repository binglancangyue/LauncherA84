package utils;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.RemoteException;
import android.util.Log;

public class Utils {
	private static final String TAG = "";
	private Context mContext;
	private static Utils utils;
	private ActivityManager activityManager;
	private SharedPreferences preferences;
	private PackageManager pm;

	private Utils(Context mContext) {
		this.mContext = mContext;
		activityManager = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
		pm = this.mContext.getPackageManager();
		preferences = this.mContext.getSharedPreferences("QcHome",Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
	}

	public static Utils getUtilInstance(Context mContext) {
		if (null == utils) {
			utils = new Utils(mContext);
		}
		return utils;
	}

	public int getWindowStatus() {
		int leftid = activityManager.getRightStackId();
		return activityManager.getWindowSizeStatus(leftid);
	}

	public boolean isFullWindow() {
		int status = getWindowStatus();
		if (status == 2) {// full window screen
			return true;
		} else {// half window screen
			return false;
		}
	}
	
	private IActivityManager localIActivityManager = null;
    
    public void doSpliteAction(){
    	if (null == localIActivityManager) {
    		localIActivityManager = ActivityManagerNative.getDefault();
		}
    	Intent localIntent = new Intent();
		try {
		      int leftStack = localIActivityManager.getLeftRightStack(true);
		      int rightStack = localIActivityManager.getLeftRightStack(false);
		      if (leftStack < 0){
		    	  leftStack = 0;
		      }
		      int leftWindowStatus = localIActivityManager.getWindowSizeStatus(leftStack);
		      int rightWindowStatus = localIActivityManager.getWindowSizeStatus(rightStack);
		      if ((leftWindowStatus == 0) && (leftStack >= 0)){
		        if ((rightWindowStatus == 0) && (rightStack > 0)){
		        	localIActivityManager.setWindowSize(rightStack, 2);
		        }
		        localIActivityManager.setWindowSize(leftStack, 1);
		      }else if ((leftWindowStatus == 1) && (leftStack >= 0)){
		        localIActivityManager.setWindowSize(leftStack, 0);
		        if ((rightWindowStatus == 2) && (rightStack > 0)){
		        	localIActivityManager.setWindowSize(rightStack, 0);
		        }
		      }else if ((rightWindowStatus == 0) && (rightStack > 0)){
		        localIActivityManager.setWindowSize(rightStack, 2);
		        if ((leftWindowStatus != 2) && (leftStack >= 0)){
		        	localIActivityManager.setWindowSize(leftStack, 1);
		        }
		      }else if ((rightWindowStatus == 2) && (rightStack > 0)){
		        if ((leftWindowStatus != 2) && (leftStack >= 0)){
		        	localIActivityManager.setWindowSize(leftStack, 0);
		        }
		        localIActivityManager.setWindowSize(rightStack, 0);
		      }
		      localIntent.setAction("android.intent.action.SPLIT_WINDOW_HAS_CHANGED");
		      mContext.sendBroadcast(localIntent);
		  }catch (RemoteException localRemoteException){
		      Log.e(TAG, "GET AMS faile!!!", localRemoteException);
		  }
    }
	
	public void launcherApp(String packageName) {
		
	}

	public boolean isAppInstalled(String packageName) {
		boolean installed = false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

	public void putInt(String key, int value) {
		preferences.edit().putInt(key, value).commit();
	}

	public int getInt(String key, int defValue) {
		return preferences.getInt(key, defValue);
	}

	public void putString(String key, String value) {
		preferences.edit().putString(key, value).commit();
	}

	public String getString(String key, String defValue) {
		return preferences.getString(key, defValue);
	}

	public void putBoolean(String key, boolean value) {
		preferences.edit().putBoolean(key, value).commit();
	}

	public boolean getBoolean(String key, boolean defValue) {
		return preferences.getBoolean(key, defValue);
	}


	public static final String COMMAND_SU       = "su";
    public static final String COMMAND_SH       = "sh";
    public static final String COMMAND_EXIT     = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
	/**
     * execute shell commands
     * 
     * @param commands command array
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return <ul>
     *         <li>if isNeedResultMsg is false, {@link CommandResult#successMsg} is null and
     *         {@link CommandResult#errorMsg} is null.</li>
     *         <li>if {@link CommandResult#result} is -1, there maybe some excepiton.</li>
     *         </ul>
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            // get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }

    /**
     * result of command
     * <ul>
     * <li>{@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
     * linux shell</li>
     * <li>{@link CommandResult#successMsg} means success message of command result</li>
     * <li>{@link CommandResult#errorMsg} means error message of command result</li>
     * </ul>
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
     */
    public static class CommandResult {

        /** result of command **/
        public int    result;
        /** success message of command result **/
        public String successMsg;
        /** error message of command result **/
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }


    /**
     ** 执行命令但不关注结果输出
     */
	public static int execRootCmdSilent(String cmd) {
		int result = -1;
		DataOutputStream dos = null;

		try {
			Process p = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(p.getOutputStream());

			Log.i(TAG, cmd);
			dos.writeBytes(cmd + "\n");
			dos.flush();
			dos.writeBytes("exit\n");
			dos.flush();
			p.waitFor();
			result = p.exitValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


	/**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
