package runsystem.net.global_hr;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by LuanDang on 11/4/2015.
 */
public class WebRecordInterface {
    private Activity mActivity;
    private static final String LOG_TAG = "WebRecordInterface";

    /** Instantiate the interface and set the context */
    WebRecordInterface(Activity activity) {
        mActivity = activity;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void getSpeakerAnswer(String userId, String index, String jSonString, String lessonNo, String modeLanguage){
        Log.d(LOG_TAG, "getSpeakerAnswer(" + userId + ", "+ index + ", " + jSonString + ", " + lessonNo + ", " + modeLanguage + ")");
        DialogRecorderActivity.startActivity(mActivity, userId, index, jSonString, lessonNo, modeLanguage);
    }

    /**
     * Logout
     * @param userId
     * @param index
     * @param jSonString
     * @param lessonNo
     * @param modeLanguage
     */
    @JavascriptInterface
    public void goLogin(){
        Log.d(LOG_TAG, "Logout");
        ((LearnActivity)mActivity).removeProfile();
        LoginActivity.startActivity(mActivity);
        ((LearnActivity)mActivity).clearAllActivityTop(true);
    }
}
