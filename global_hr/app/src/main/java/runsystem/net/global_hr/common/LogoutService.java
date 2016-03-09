package runsystem.net.global_hr.common;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import runsystem.net.global_hr.BaseActivity;
import runsystem.net.global_hr.LoginActivity;
import runsystem.net.global_hr.api.APILogout;

/**
 * Created by LuanDang on 1/4/2016.
 */
public class LogoutService extends Service {
    private CountDownTimer timer;
    private Context context;

    public LogoutService(final Context context,final Activity activity) {
        this.context = context;

        timer = new CountDownTimer(Constant.APP_TIME_OUT, 60 * 1000) {
            public void onTick(long millisUntilFinished) {
                Log.e("onTick",  millisUntilFinished/60000 + "");
            }

            public void onFinish() {
                Log.e("LogoutService", "Call Logout");
                activity.finish();
                APILogout api = new APILogout();
                api.token = ((BaseActivity)activity).getRegistrationToken();
                api.startAPI(context);
                gotoLogin();
                ((BaseActivity)activity).removeProfile();
                ((BaseActivity)activity).clearAllActivityTop(true);
                stopSelf();
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void gotoLogin(){
        LoginActivity.startActivity(this.context);
    }

    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }
}
