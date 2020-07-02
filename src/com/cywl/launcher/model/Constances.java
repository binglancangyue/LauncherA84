package com.cywl.launcher.model;

import android.R.string;

import com.cywl.launcher.R;

import android.os.SystemProperties;

public class Constances {
    public static final int TYPE_LAYOUT_KAKA = 0;
    public static final int TYPE_LAYOUT_BLUESKY = 1;
    public static final int TYPE_LAYOUT_MAIZELONG = 2;
    public static final int TYPE_LAYOUT_TAIWAN = 3;
    public static final int TYPE_LAYOUT_WANFANGMAI = 4;
    public static final int TYPE_LAYOUT_WANFANGMAI_EN = 5;
    public static final int TYPE_LAYOUT_988 = 6;
    public static final int TYPE_LAYOUT_BAOHEIZI = 7;
    public static final int TYPE_LAYOUT_80HOU = 8;
    public static final int TYPE_LAYOUT_QC7_92 = 9;
    public static final int TYPE_LAYOUT_DINGWEITE = 10;
    public static final int TYPE_LAYOUT_QC7 = 20;
    public static final int TYPE_LAYOUT_QC7_BEISITE = 21;
    public static final int TYPE_LAYOUT_QC7_EN = 22;
    public static final int TYPE_LAYOUT_QC7_CYS = 23;
    public static final int TYPE_LAYOUT_QC7_QZ = 24;
    public static final int TYPE_LAYOUT_QC7_SPLITE = 25;
    public static final int TYPE_LAYOUT_QC7_SPLITE_HULIAN = 26;
    public static final int TYPE_LAYOUT_QC7_SPLITE_YISHENGYUAN = 27;
    public static final int TYPE_LAYOUT_QC7_QS = 28;
    public static final int TYPE_LAYOUT_9TRUCK4 = 29;
    public static final int TYPE_LAYOUT_QC7_SPLITE_WEIJIA = 30;
    public static final int TYPE_LAYOUT_FOUR_784 = 40;
    public static final int TYPE_LAYOUT_FOUR_988_COMMON = 41;
    public static final int TYPE_LAYOUT_FOUR_988_KAKA = 42;
    public static final int TYPE_LAYOUT_FOUR_935 = 43;
    public static final int TYPE_LAYOUT_THREE_9_NOSPLITE = 44;
    public static final int TYPE_LAYOUT_T9PLUS_BEI_SI_TE = 45;

    public static final int SPLITE_PAGE_COUNT = 3;
    public static final int FULL_PAGE_COUNT = 5;
    public static final String SYSTEM_REASON = "reason";
    public static final String SYSTEM_HOME_KEY = "homekey";
    public static final String SYSTEM_HOME_KEY_LONG = "recentapps";
    public static final String PASSWORD = "start666";

    // 开机默认导航
    public static final String MAP_GAODE = "GAODE_MAP_CAR";
    public static final String MAP_CLD = "KAILIDE_NAV";
    public static final String MAP_GOOGLE = "GOOGLE_MAP_CAR";
    public static final String MAP_SYGIC = "SYGIC_MAP";
    public static final String MAP_LEKE = "LEKE_MAP";

    public static String DEFAULT_MAP = SystemProperties.get("persist.default.map", MAP_CLD);
    // 海外版本
    // public static final boolean isOversea =
    // "yes".equals(SystemProperties.get("ro.sys.oversea", "no")) ? true : false;
    public static final boolean isOversea = true;

    public static final String KEY_DEFAULT_MAP_PACKAGE_NAME = "default_package";
    public static boolean isCustomMap = SystemProperties.getBoolean("ro.se.qchome.iscustommap",
            true);// 是否支持长按选择地图
    public static final String ACTION_NAME_ONLY_ONE_CAN_START = "com.zqc.action.only.one.between" +
            ".gaode.and.kailide"; // 凯立德
    public static int LAYOUT_TYPE = SystemProperties.getInt("ro.se.qchome.layouttype", 0);// 配置加载布局
    public static final boolean IS_ENGLISH_APPLIST = SystemProperties.getBoolean("persist.sys" +
            ".isEn_applist", false);
    public static final boolean IS_NEED_PASSWD_ENTER_STORE = SystemProperties.getBoolean("persist" +
                    ".sys.isneedpasswd",
            false);

    /*--------------------broadcast action ----------------------------------*/
    public static final String ACTION_NAME_SPLITE = "android.intent.action" +
            ".SPLIT_WINDOW_HAS_CHANGED";
    public static final String ACTION_NAME_NAVI_CLICK = "RESTOR_PREES_DOWN";
    public static final String ACTION_NAME_CLOSE_APPLIST = "com.zqc.action.close.applist";
    public static final String ACTION_NAME_OPEN_APPLIST = "com.zqc.action.launche.applist";
    public static final String ACTION_NAME_LAUNCH_BROWSER = "com.zqc.action.launch_browser";
    public static final String APPLIST_CLASSNAME = "com.zqc.launcher.AppListActivity";
    public static final String ACTION_NAME_IS_AUTOLITE = "com.zqc.action.isautolite";
    public static final String ACTION_LOCATION = "LRA308";
    public static final String ACTION_DEFAULT_MAP = "com.action.default.map";

    /*--------------------package name----------------------------------*/
    public static final String PACKAGE_NAME_DEAULT_EDOG = "com.hdsc.edog";
    public static final String PACKAGE_NAME_ANANEDOG = "com.sxprd.radarspeed";
    public static final String PACKAGE_NAME_KUWO_MUSIC = "cn.kuwo.kwmusiccar";
    public static final String PACKAGE_NAME_TONGTING_MUSIC = "com.txznet.music";
    public static final String PACKAGE_NAME_YOUTOBE_MUSIC = "com.google.android.youtube";
    public static final String PACKAGE_NAME_AUTOLITE = "com.autonavi.amapautolite";
    public static final String PACKAGE_NAME_AMAPAUTO = "com.autonavi.amapauto";
    public static final String PACKAGE_NAME_CLD_2 = "cld.navi.STD.mainframe";
    public static final String PACKAGE_NAME_CLD_3 = "cld.navi.kaimap.h4139.mainframe";

    // public static final String PACKAGE_NAME_CLD = "cld.navi.k3618.mainframe";
    public static final String PACKAGE_NAME_BLUETOOTH = "com.cywl.bt.activity";
    public static final String PACKAGE_NAME_TIANDIDA = "cn.jyuntech.map";
    public static final String PACKAGE_NAME_VIETMAP = "com.vietmap.s1OBU";
    public static final String PACKAGE_NAME_ANDROID_MUSIC = "com.android.music";
    public static final String PACKAGE_NAME_GOOGLE_MAP = "com.google.android.apps.maps";
    public static final String PACKAGE_NAME_SYGIC_MAP = "com.sygic.aura";
    public static final String PACKAGE_NAME_GOOGLESTORE = "com.android.vending";
    public static final String PACKAGE_NAME_GPS_TEST = "com.chartcross.gpstestplus";
    public static final String PACKAGE_NAME_DEFAULT_MOBILE_LINK = "com.ligo.appshare_qichen";
    public static final String PACKAGE_NAME_WEIXIN_ASSIT = "com.txznet.webchat";
    public static final String PACKAGE_NAME_PP_VIDEO = "com.zhiyang.connectphone";
    public static final String PACKAGE_NAME_DEFAULT_VIDEO = "com.zqc.videolisttest"; // 视频
    //	public static final String PACKAGE_NAME_DEFAULT_VIDEO = "com.ligo.appshare_qichen"; //视频
    public static final String PACKAGE_NAME_BROWSER = "com.android.browser";
    public static final String PACKAGE_NAME_KAILIDE = "cld.navi.STD.mainframe";
    public static final String PACKAGE_NAME_WIFI_HOT = "com.android.settings" +
            ".Settings$TetherSettingsActivity";
    // 万能钥匙
    public static final String PACKAGE_NAME_UNIVERSAL_KEY = "com.snda.wifilocating";
    // 云服务
    public static final String PACKAGE_NAME_CLOUND_SERVICE = "com.yichengyuan.uploadgps";
    // 网络电视
    public static final String PACKAGE_NAME_INTERNET = "org.fungo.carpad";
    public static final String PACKAGE_NAME_NAVI = SystemProperties.get("ro.se.map_package_name",
            PACKAGE_NAME_AUTOLITE);
    public static final String PACKAGE_NAME_CAMERA = "com.android.camera2";
    //	public static final String PACKAGE_NAME_MUSIC = SystemProperties.get("ro.se
    //	.music_package_name",
//			PACKAGE_NAME_KUWO_MUSIC);
    public static final String PACKAGE_NAME_MUSIC = SystemProperties.get("ro.se.music_package_name",
            PACKAGE_NAME_KUWO_MUSIC);
    public static final String PACKAGE_NAME_EDOG = SystemProperties.get("ro.se.edog_package_name",
            PACKAGE_NAME_DEAULT_EDOG);
    public static final String PACKAGE_NAME_GPS = SystemProperties.get("ro.se.edog_package_name",
            PACKAGE_NAME_GPS_TEST);
    public static final String PACKAGE_NAME_FM = "com.example.administrator.fm";
    public static final String PACKAGE_NAME_SETTINGS = "com.android.settings";
    // public static final String PACKAGE_NAME_VIDEO =
    // SystemProperties.get("ro.se.video_package_name",PACKAGE_NAME_DEFAULT_VIDEO);
    // public static final String PACKAGE_NAME_VIDEO = "com.ligo.appshare_qichen";
    public static final String PACKAGE_NAME_VIDEO = "com.zqc.videolisttest";

    public static final String PACKAGE_NAME_MOBILE_LINK = SystemProperties.get("ro.se" +
                    ".pc_package_name",
            PACKAGE_NAME_DEFAULT_MOBILE_LINK);
    public static final String PACKAGE_NAME_FILE = "com.softwinner.TvdFileManager";
    public static final String PACKAGE_NAME_APPLIST = "com.zqc.applist";
    public static final String PACKAGE_NAME_DSJ = "com.dianshijia.newlive"; // 电视家3.0
    public static final String PACKAGE_NAME_LEKE_MAP = "com.kingwaytek"; //
    public static final boolean FULL_WINDOW = false;//


    public static final String TXZ_ACTION = "com.txznet.adapter.send";// TXZ语音指令
    public static final String KUWO_ACTION = "com.kuwo.music.change";//切歌广播
//    public static final String PACKAGE_NAME_SETTINGS = "SETTINGS";
    public static final String ACTION_TXZ_ENABLE = "com.cywl.launcher_txz_enable";
    public static final boolean IS_ZKJA = true;


    public static final int KEY_GAODE = 1;
    public static final int KEY_KAILIDE = 2;
    public static final String KEY_WHICH_NAV = "key_which_navi";

    public static int[] img9ThreeImageNoSplite = new int[]{R.drawable.iv_9three_navi,
            R.drawable.iv_9three_video,
            R.drawable.iv_9three_edog, R.drawable.iv_9three_music, R.drawable.iv_9three_record,
            R.drawable.iv_9three_fm,
            R.drawable.iv_9three_bluetooth, R.drawable.iv_9three_file};

    public static String[] m9ThreePackageNoSplite = {PACKAGE_NAME_AUTOLITE, PACKAGE_NAME_VIDEO,
            PACKAGE_NAME_EDOG,
            PACKAGE_NAME_MUSIC, PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM, PACKAGE_NAME_BLUETOOTH,
            PACKAGE_NAME_FILE};

//	public static int[] img9ThreeImage = new int[] { R.drawable.iv_navi_splite_wj, R.drawable
//	.iv_video_splite_wj,
//			R.drawable.iv_edog_splite_wj, R.drawable.iv_music_splite_wj, R.drawable
//			.iv_record_splite_wj,
//			R.drawable.iv_fm_splite_wj, R.drawable.iv_bt_splite_wj, R.drawable
//			.iv_applist_splite_wj};
//	
//	public static int[] img9ThreeImageMax = new int[] { R.drawable.iv_navi_splite_full_wj, R
//	.drawable.iv_video_splite_full_wj,
//			R.drawable.iv_edog_splite_full_wj, R.drawable.iv_music_splite_full_wj, R.drawable
//			.iv_record_splite_full_wj,
//			R.drawable.iv_fm_splite_full_wj, R.drawable.iv_bt_splite_full_wj, R.drawable
//			.iv_applist_splite_full_wj};


    /*------------------------------应用小icon------------------------------*/
//	public static int[] img9ThreeImage = new int[] { R.drawable.iv_navi_splite_wj, R.drawable
//	.iv_video_splite_wj,
//			R.drawable.iv_record_splite_wj,
//			R.drawable.iv_fm_splite_wj, R.drawable.iv_bt_splite_wj, R.drawable
//			.iv_applist_splite_wj};
//	public static int[] img9ThreeImage = new int[] { R.drawable.iv_navi_splite_wj, R.drawable
//	.iv_video_splite_wj,
//			R.drawable.iv_record_splite_wj, R.drawable.iv_fm_splite_wj, R.drawable.iv_bt_splite_wj,
//			R.drawable.iv_applist_splite_wj };
//	public static int[] img9ThreeImage = new int[] { R.drawable.iv_navi_splite_qc7, R.drawable
//	.iv_video_splite_qc7,
//			R.drawable.iv_gps_splite_qc7, R.drawable.iv_music_splite_qc7, R.drawable
//			.iv_record_splite_qc7,
//			R.drawable.iv_fm_splite_qc7, R.drawable.iv_bt_splite_qc7, R.drawable
//			.iv_applist_splite_qc7, };

    public static String[] m9ThreePackage = {DEFAULT_MAP, PACKAGE_NAME_VIDEO, PACKAGE_NAME_EDOG,
            PACKAGE_NAME_MUSIC, PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM, PACKAGE_NAME_BLUETOOTH,
            PACKAGE_NAME_APPLIST};


    /*------------------------------海外版 应用小icon------------------------------*/
//    public static int[] img9ThreeImage = new int[]{
//            R.drawable.iv_navi_splite_wj, R.drawable.iv_video_splite_wj,
//            R.drawable.iv_edog_splite_wj, R.drawable.iv_music_splite_wj,
//            R.drawable.iv_record_splite_wj, R.drawable.iv_fm_splite_wj,
//            R.drawable.iv_bt_splite_wj, R.drawable.iv_applist_splite_wj};//hp wj version

//    public static int[] img9ThreeImage = new int[]{
//            R.drawable.icon_navigation, R.drawable.icon_video,
//            R.drawable.icon_edog, R.drawable.icon_music,
//            R.drawable.icon_record, R.drawable.icon_fm,
//            R.drawable.icon_bt, R.drawable.icon_app};//hp 东风

//    public static int[] img9ThreeImage = new int[]{R.drawable.iv_navi_splite_qc7,
//            R.drawable.iv_video_splite_qc7,
//            R.drawable.iv_record_splite_qc7, R.drawable.iv_fm_splite_qc7,
//            R.drawable.iv_bt_splite_qc7,
//            R.drawable.iv_applist_splite_qc7,};//hp english version


    public static int[] img9ThreeImage = new int[]{
            R.drawable.iv_navi_zkja, R.drawable.iv_video_zkja,
            R.drawable.iv_record_zkja, R.drawable.iv_fm_zkja,
            R.drawable.iv_bt_zkja, R.drawable.iv_settings_zkja,
            R.drawable.iv_applist_zkja};// zkja

//    public static int[] img9ThreeImage = new int[]{
//            R.drawable.iv_navi_splite_qc7, R.drawable.iv_video_splite_qc7,
//            R.drawable.iv_edog_qc7_splite, R.drawable.iv_music_splite_qc7,
//            R.drawable.iv_record_splite_qc7, R.drawable.iv_fm_splite_qc7,
//            R.drawable.iv_bt_splite_qc7, R.drawable.iv_applist_splite_qc7,};//hp qc7 version

    /*------------------------------海外版 全屏显示icon------------------------------*/

//    public static int[] img9ThreeImageMax = new int[]{
//            R.drawable.iv_navi_splite_full_wj, R.drawable.iv_video_splite_full_wj,
//            R.drawable.iv_record_splite_full_wj, R.drawable.iv_fm_splite_full_wj,
//            R.drawable.iv_bt_splite_full_wj, R.drawable.iv_applist_splite_full_wj};

//    public static int[] img9ThreeImageMax = new int[]{
//            R.drawable.iv_navi_splite_full_qc7, R.drawable.iv_video_splite_full_qc7,
//            R.drawable.iv_gps_splite_full_qc7, R.drawable.iv_music_splite_full_qc7,
//            R.drawable.iv_record_splite_full_qc7, R.drawable.iv_fm_splite_full_qc7,
//            R.drawable.iv_bt_splite_full_qc7, R.drawable.iv_applist_splite_full_qc7};

    public static int[] img9ThreeImageMax = new int[]{
            R.drawable.iv_navi_zkja, R.drawable.iv_video_zkja,
            R.drawable.iv_record_zkja, R.drawable.iv_fm_zkja,
            R.drawable.iv_bt_zkja, R.drawable.iv_settings_zkja,
            R.drawable.iv_applist_zkja};//zkja

//    public static int[] img9ThreeImageMax = new int[]{
//            R.drawable.iv_navi_splite_full_qc7, R.drawable.iv_video_splite_full_qc7,
//            R.drawable.iv_edog_qc7_splite_full, R.drawable.iv_music_splite_full_qc7,
//            R.drawable.iv_record_splite_full_qc7, R.drawable.iv_fm_splite_full_qc7,
//            R.drawable.iv_bt_splite_full_qc7, R.drawable.iv_applist_splite_full_qc7};//hp

//    public static int[] img9ThreeImageMax = new int[]{
//            R.drawable.iv_navi_splite_full_wj, R.drawable.iv_video_splite_full_wj,
//            R.drawable.iv_edog_splite_full_wj, R.drawable.iv_music_splite_full_wj,
//            R.drawable.iv_record_splite_full_wj, R.drawable.iv_fm_splite_full_wj,
//            R.drawable.iv_bt_splite_full_wj, R.drawable.iv_applist_splite_full_wj};//hp wj version

//    public static int[] img9ThreeImageMax = new int[]{
//            R.drawable.icon_navigation, R.drawable.icon_video,
//            R.drawable.icon_edog, R.drawable.icon_music,
//            R.drawable.icon_record, R.drawable.icon_fm,
//            R.drawable.icon_bt, R.drawable.icon_app};//hp 东风


//    public static int[] img9ThreeImageMax = new int[]{R.drawable.iv_navi_splite_full_qc7,
//            R.drawable.iv_video_splite_full_qc7, R.drawable.iv_record_splite_full_qc7,
//            R.drawable.iv_fm_splite_full_qc7, R.drawable.iv_bt_splite_full_qc7,
//            R.drawable.iv_applist_splite_full_qc7};//hp english version



    /*------------------------------海外版 包名------------------------------*/
//	public static String[] m9ThreePackageOversea = { PACKAGE_NAME_GOOGLEMAP, PACKAGE_NAME_VIDEO,
//	PACKAGE_NAME_EDOG,
//			PACKAGE_NAME_MUSIC, PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM, PACKAGE_NAME_BLUETOOTH,
//			PACKAGE_NAME_APPLIST};

//	public static String[] m9ThreePackageOversea = { PACKAGE_NAME_LEKE_MAP, PACKAGE_NAME_VIDEO,
//	PACKAGE_NAME_GPS,
//			PACKAGE_NAME_MUSIC, PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM, PACKAGE_NAME_BLUETOOTH,
//			PACKAGE_NAME_APPLIST };

//        public static String[] m9ThreePackageOversea = {
//            PACKAGE_NAME_CLD_2, PACKAGE_NAME_VIDEO,
//            PACKAGE_NAME_EDOG, PACKAGE_NAME_MUSIC,
//            PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM,
//            PACKAGE_NAME_BLUETOOTH, PACKAGE_NAME_APPLIST};//hp

    public static String[] m9ThreePackageOversea = {
            PACKAGE_NAME_CLD_3, PACKAGE_NAME_VIDEO,
            PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM,
            PACKAGE_NAME_BLUETOOTH,PACKAGE_NAME_SETTINGS,
            PACKAGE_NAME_APPLIST};//zkja

//    public static String[] m9ThreePackageOversea = {
//            PACKAGE_NAME_GOOGLE_MAP, PACKAGE_NAME_VIDEO,
//            PACKAGE_NAME_CAMERA, PACKAGE_NAME_FM,
//            PACKAGE_NAME_BLUETOOTH, PACKAGE_NAME_APPLIST};//hp english


    /*------------------------------应用名------------------------------*/

//	public static int[] img9ThreeNameStringID = new int[] { R.string.name_9three_navi, R.string
//	.name_9three_video,
//			R.string.name_9three_edog, R.string.name_9three_music, R.string.name_9three_dvr,
//			R.string.name_9three_fm, R.string.name_9three_bt, R.string.name_9three_applist};
//	public static int[] img9ThreeNameStringID = new int[] { R.string.name_9three_navi, R.string
//	.name_9three_video,
//			R.string.name_9three_dvr,
//			R.string.name_9three_fm, R.string.name_9three_bt, R.string.name_9three_applist};
//	public static int[] img9ThreeNameStringID = new int[] { R.string.name_9three_navi, R.string
//	.name_9three_video,
//			R.string.name_9three_gps, R.string.name_9three_music, R.string.name_9three_dvr, R
//			.string.name_9three_fm,
//			R.string.name_9three_bt, R.string.name_9three_applist, };

    public static int[] img9ThreeNameStringID = new int[]{
            R.string.name_9three_navi, R.string.name_9three_video,
            R.string.name_9three_edog, R.string.name_9three_music,
            R.string.name_9three_dvr, R.string.name_9three_fm,
            R.string.name_9three_bt, R.string.name_9three_applist};//hp

//    public static int[] img9ThreeNameStringID = new int[]{
//            R.string.null_string, R.string.null_string,
//            R.string.null_string, R.string.null_string,
//            R.string.null_string, R.string.null_string,
//            R.string.null_string, R.string.null_string};//hp dongfeong(no text)

//    public static int[] img9ThreeNameStringID = new int[]{
//            R.string.name_9three_navi, R.string.name_9three_video,
//            R.string.name_9three_dvr, R.string.name_9three_fm,
//            R.string.name_9three_bt, R.string.name_9three_applist};//hp english version
}
