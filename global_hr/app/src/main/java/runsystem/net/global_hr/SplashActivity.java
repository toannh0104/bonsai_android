package runsystem.net.global_hr;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import runsystem.net.global_hr.api.APIGetAddress;
import runsystem.net.global_hr.api.APILogin;
import runsystem.net.global_hr.api.APILoginHeader;
import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.network.NetworkUtils;

public class SplashActivity extends BaseActivity implements APILogin.LoginOnResult, APIGetAddress.GetAddressOnResult{
    private static final String TAG = SplashActivity.class.getSimpleName();
    private Handler mHandler = new Handler();
    private long SPLASH_LENGTH_MILLIS = 1200;
    private String uuid = "";
    private String id = "";
    private String pass = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initData() {
        super.initData();
        id = getRegistrationId(this);
        pass = getRegistrationPass(this);
        uuid = getRegistrationUUID(this);
        // Auto login
        if(!id.equals("") && !pass.equals("") && !uuid.equals("")){
            // Check network
            if (NetworkUtils.isNetworkConnected(this)) {
                getAddress();
            } else {
                // No connect internet
                showAlertInfo(this, " ",getString(R.string.mess_no_network), false);
            }
        } else {
            // Goto login
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goTo(LoginActivity.class, R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }, SPLASH_LENGTH_MILLIS);
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void getAddress(){

        showLoading(getText(R.string.mes_startup_login).toString());
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double latitude = 0;
        double longitude = 0;
        if(location != null){
            location.getLatitude();
            longitude = location.getLongitude();
        }

        APIGetAddress api = new APIGetAddress();
        api.latlong = latitude + "," +longitude;
        api.startAPI(this);
        api.setAddressOnResult(this);
    }

    @Override
    public void onAddressOnResult(String address) {
        APILogin api = new APILogin();
        if (address.equals("")){
            api.location = getLocaleCountry();
        } else {
            api.location = address;
        }
        api.userName = id;
        api.password = pass;
        api.uuid = uuid;
        api.device_id = getDeviceId();
        api.device_name = getDeviceName();
        api.device_version = getDeviceVersion();
        api.startAPI(this);
        api.setLoginOnResult(this);
    }

    @Override
    public void onLoginOnResult(APILoginHeader header) {
        // Hide loading
        closeLoading();
        if (header != null) {
            if (header.getResponse().getStatus().equalsIgnoreCase(Constant.STATUS_OK)
                    && header.getResult() != null) {
                saveProfile(id, pass, uuid, header.getResult().getToken());
                TopActivity.startActivity(this,
                            header.getResult().getLearningJpBasic(),
                            header.getResult().getLearningSafetyBasic(),
                            header.getResult().getLearningJpAdvance(),
                            header.getResult().getLearningSafetyAdvance(),
                            header.getResult().getLearningCompliance(),
                            header.getResult().getLearningSecurity(),
                            header.getResult().getLearningHarassment(),
                            header.getResult().getLearningMoral(),
                            header.getResult().getLearningGovernance(),
                            header.getResult().getLearningLaborProvisions(),
                            header.getResult().getLearningVoyage(),
                            header.getResult().getLearningJpLife());
                finish();
            } else {
                goTo(LoginActivity.class, R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        } else {
            goTo(LoginActivity.class, R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }
}
