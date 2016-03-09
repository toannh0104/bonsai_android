package runsystem.net.global_hr.common;

/**
 * Created by LuanDang on 11/18/2015.
 */
public class Constant {
    public static final String PREF_FILE_NAME_USER_DATA = "hr_user_data";
    public static final String PREF_KEY_REGID = "userName";
    public static final String PREF_KEY_REGPASS = "password";
    public static final String PREF_KEY_REGUUID = "uuid";
    public static final String PREF_KEY_TOKEN = "token";
    public static final String PREF_KEY_TOKEN_AUTH = "X-AUTH-TOKEN";
    public static final int APP_TIME_OUT = 10 * 60 * 1000;
    public static final int SERVER_TIME_OUT = 30 * 1000;
    public static final String KEY_STATUS = "status";
    public static final String KEY_MESSAGE = "message";
    public static final String STATUS_OK = "OK";
    public static final String STATUS_NG = "NG";
    public static final String PACKAGE_NAME_SKYPE = "com.skype.raider";
    public static final String PACKAGE_NAME_GMAIL = "com.google.android.gm";
    public static final int MODE_LANG_ROMAJI = 0;
    public static final int MODE_LANG_KANA = 1;
    public static final int MODE_LANG_KANJI = 2;
    public static final String KEY_AUDIO_FILE = "/audiorecord_global1.wav";
    //public static final String LINK_SERVER = "http://113.161.87.206:17580/";
//    public static final String LINK_SERVER = "http://www.adventures-gate.net/";
    public static final String LINK_SERVER = "http://192.168.0.99:8080/";
    public static final String LINK_TOP_LEARN = "Bonsai/";
    public static final String API_POST_AUDIO = "Bonsai/speech/uploadFileBlob";
    //public static final String API_LOGIN = "GlobalHRService/checkLogin";
    public static final String API_LOGIN = "Bonsai/checkLogin";
    public static final String API_LOGOUT = "Bonsai/logout";
    public static final String API_GET_ADDRESS = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
}
