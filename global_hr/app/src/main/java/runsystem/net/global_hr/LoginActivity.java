package runsystem.net.global_hr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import runsystem.net.global_hr.api.APIGetAddress;
import runsystem.net.global_hr.api.APILogin;
import runsystem.net.global_hr.api.APILoginHeader;
import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.network.NetworkUtils;

public class LoginActivity extends BaseActivity implements APILogin.LoginOnResult , APIGetAddress.GetAddressOnResult{

    private Button btnLogin;
    private EditText edtId, edtPass;

    private String uuid = "";
    private String id = "";
    private String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        edtId = (EditText) findViewById(R.id.edt_login_id);
        edtPass = (EditText) findViewById(R.id.edt_login_pass);
        btnLogin.setOnClickListener(this);
        edtId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else {
                    showKeyboard(v);
                }
            }
        });
        edtPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else {
                    showKeyboard(v);
                }
            }
        });

        id = getRegistrationId(this);
        if (!id.equals("")){
            edtId.setText(id);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                processLogin();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isFinishing()) return;
        tryFinish();
    }

    @Override
    public void onLoginOnResult(APILoginHeader header) {

        // Hide loading
        closeLoading();
        if (header != null) {
            String message = header.getResponse().getMessage();
            if (header.getResponse().getStatus().equalsIgnoreCase(Constant.STATUS_OK)
                    && header.getResult() != null) {
                // Save pre
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
                showAlertInfo(this, " ", message, false);
            }
        } else {
            showAlertInfo(this, " ", getString(R.string.mess_server_error), false);
        }
    }

    /**
     * processLogin
     */
    private void processLogin() {
        if (NetworkUtils.isNetworkConnected(this)) {
            id = edtId.getText().toString().trim();
            pass = edtPass.getText().toString().trim();
            if (id.equals("") || pass.equals("")){
                showAlertInfo(this, " ",getString(R.string.mess_no_input), false);
            } else {
                getAddress();
            }
        } else {
            // No connect internet
            showAlertInfo(this, " ", getString(R.string.mess_no_network), false);
        }
    }

    private void getAddress(){

        showLoading(getText(R.string.mes_startup_login).toString());
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        APIGetAddress api = new APIGetAddress();
        api.latlong = latitude + "," +longitude;
        api.startAPI(this);
        api.setAddressOnResult(this);
    }

    @Override
    public void onAddressOnResult(String address) {
        // Get UUID
        uuid = getRegistrationUUID(this);
        if(uuid.equals("")){
            uuid = UUID.randomUUID().toString();
        }

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

    /**
     * startActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
