package runsystem.net.global_hr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.objects.SkypeContact;
import runsystem.net.global_hr.webview.DialogConfirm;

public class BaseActivity extends Activity implements OnClickListener {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private Dialog progressDialog;
    private Dialog dialog, dialogSkype, dialogLogout;
    private boolean finishFlg = false;
    private static final String KEY_ENTER_ANIM = "enterAnim";
    private static final String KEY_EXIT_ANIM = "exitAnim";
    public AppController mApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = getApp();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    /**
     * getApp
     *
     * @return
     */
    public AppController getApp() {
        return (AppController) getApplication();
    }

    /**
     * Init view
     */
    protected void initView() {

    }

    /**
     * Init data
     */
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void tryFinish() {
        if (finishFlg) {
            finish();
        } else {
            Toast.makeText(this, getString(R.string.info_double_tap_to_finish), Toast.LENGTH_SHORT).show();
            finishFlg = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishFlg = false;
                }
            }, 2000);
        }
    }

    /**
     * processLogout
     */
    public void processLogout(final Context context) {
        FragmentManager fm = getFragmentManager();
        /** Instantiating the DialogFragment */
        DialogConfirm alert = new DialogConfirm(context);
        /** Opening the dialog window */
        alert.show(fm, "Alert_Dialog");
    }

    /**
     * ActivityTransition on new Activity start
     *
     * @param dest      destination Activity
     * @param enterAnim enter animation resId
     * @param exitAnim  exit animation resId
     */
    public void goTo(Class dest, int enterAnim, int exitAnim) {
        if (isFinishing()) return;
        Intent intent = new Intent(this, dest);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        // FLAG_ACTIVITY_REORDER_TO_FRONTの場合にアニメーションが正しく動かない場合があるので
        // 遷移先で実行できるようにintentに入れておく
        intent.putExtra(KEY_ENTER_ANIM, enterAnim);
        intent.putExtra(KEY_EXIT_ANIM, exitAnim);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * clearActivityTop
     */
    public static void clearAllActivityTop(boolean clearTop) {
        if (LearnActivity.activity != null) {
            LearnActivity.activity.finish();
        }
        if (DialogRecorderActivity.activity != null) {
            DialogRecorderActivity.activity.finish();
        }
        if (clearTop && TopActivity.activity != null) {
            TopActivity.activity.finish();
        }
    }

    /**
     * showAlertInfo
     */
    public void showAlertInfo(Context context, String title, String message, final boolean onBack) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                alertDialog.dismiss();
                if (onBack) {
                    onBackPressed();
                }
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    /**
     * showLoading
     */
    public void showLoading(String text) {
        // Create dialog loading
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.ThemeDialogCustom);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.dialog_load);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        TextView txt = (TextView) progressDialog.findViewById(R.id.txtloading);
        txt.setText(text);
        progressDialog.show();
    }

    /**
     * Close dialog loading
     */
    public void closeLoading() {
        try {
            progressDialog.dismiss();
        } catch (Exception ex) {

        }
    }

    /**
     * showDialogInfo
     */
    public void showDialogInfo(String title, String mess, final boolean onBack) {
        // Create dialog
        if (dialog == null) {
            dialog = new Dialog(this, R.style.ThemeDialogCustom);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_info);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        TextView tvTitle = (TextView) dialog.findViewById(R.id.dialog_info_title);
        tvTitle.setText(title);
        TextView tvMess = (TextView) dialog.findViewById(R.id.dialog_info_mess);
        tvMess.setText(mess);
        Button btnOK = (Button) dialog.findViewById(R.id.dialog_info_btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                closeDialog();
                if (onBack) {
                    onBackPressed();
                }
            }
        });
        dialog.show();
    }

    /**
     * Close dialog loading
     */
    public void closeDialog() {
        try {
            dialog.dismiss();
        } catch (Exception ex) {

        }
    }


    /**
     * getRegistrationId
     *
     * @param context
     * @return
     */
    public String getRegistrationId(Context context) {
        String registrationId = AppController.getInstance().getString(Constant.PREF_KEY_REGID);
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        return registrationId;
    }

    /**
     * getRegistrationPass
     *
     * @param context
     * @return
     */
    public String getRegistrationPass(Context context) {
        String registrationPass = AppController.getInstance().getString(Constant.PREF_KEY_REGPASS);
        if (registrationPass.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        return registrationPass;
    }

    /**
     * getRegistrationPass
     *
     * @param context
     * @return
     */
    public String getRegistrationUUID(Context context) {
        String registrationUUID = AppController.getInstance().getString(Constant.PREF_KEY_REGUUID);
        if (registrationUUID.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        return registrationUUID;
    }

    /**
     * getRegistrationToken
     *
     * @param context
     * @return
     */
    public String getRegistrationToken() {
        String token = AppController.getInstance().getString(Constant.PREF_KEY_TOKEN);
        if (token.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        return token;
    }

    /**
     * saveProfile
     */
    public void saveProfile(String id, String pass, String uuid, String token) {
        AppController app = AppController.getInstance();
        app.putString(Constant.PREF_KEY_REGID, id);
        app.putString(Constant.PREF_KEY_REGPASS, pass);
        app.putString(Constant.PREF_KEY_REGUUID, uuid);
        app.putString(Constant.PREF_KEY_TOKEN, token);

        String cookieToken = Constant.PREF_KEY_TOKEN_AUTH + "=" + token;

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        cookieManager.setCookie(Constant.LINK_SERVER, cookieToken);
        CookieSyncManager.getInstance().sync();

        String cookie = cookieManager.getCookie(Constant.LINK_SERVER);
        Log.i("=====Cookie====", cookie);
    }

    /**
     * removeProfile
     */
    public void removeProfile() {
        AppController app = AppController.getInstance();
        //app.remove(Constant.PREF_KEY_REGID);
        app.remove(Constant.PREF_KEY_REGPASS);
        app.remove(Constant.PREF_KEY_REGUUID);
        app.remove(Constant.PREF_KEY_TOKEN);
    }

    /**
     * showDialogSkypeCall
     */
    public void showDialogSkypeCall() {

        // Create dialog
        if (dialogSkype == null) {
            dialogSkype = new Dialog(this, R.style.ThemeDialogCustom);
            dialogSkype.setContentView(R.layout.dialog_skype);
            dialogSkype.setCancelable(true);
            dialogSkype.setCanceledOnTouchOutside(true);
            dialogSkype.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        // Editext
        final AutoCompleteTextView autSkypeId = (AutoCompleteTextView) dialogSkype.findViewById(R.id.edt_skype_id);
        autSkypeId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ArrayList<String> arr = getSkypeContacts();
        autSkypeId.setAdapter(
                new ArrayAdapter<String>
                        (
                                this,
                                android.R.layout.simple_list_item_1,
                                arr
                        ));

        // Call
        ImageButton btnCall = (ImageButton) dialogSkype.findViewById(R.id.btn_skype_call);
        btnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String skypeID = autSkypeId.getText().toString().trim();
                String uri = "";
                if (!skypeID.equals("")) {
                    uri = "skype:" + skypeID + "?call";
                }
                initiateSkypeUri(getBaseContext(), uri);
            }
        });

        // Video call
        ImageButton btnVideo = (ImageButton) dialogSkype.findViewById(R.id.btn_skype_call_video);
        btnVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String skypeID = autSkypeId.getText().toString().trim();
                String uri = "";
                if (!skypeID.equals("")) {
                    uri = "skype:" + skypeID + "?call&video=true";
                }
                initiateSkypeUri(getBaseContext(), uri);
            }
        });

        // Cancel
        Button btnCancel = (Button) dialogSkype.findViewById(R.id.btn_skype_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSkype.dismiss();
            }
        });
        dialogSkype.show();
    }

    /**
     * Initiate the actions encoded in the specified URI.
     */
    public void initiateSkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed.
        if (!isClientInstalled(myContext, Constant.PACKAGE_NAME_SKYPE)) {
            goToMarket(myContext, Constant.PACKAGE_NAME_SKYPE);
            return;
        }

        Intent myIntent;
        // Create the Intent from our Skype URI.
        if (!mySkypeUri.equals("")) {
            myIntent = new Intent(Intent.ACTION_VIEW);
            Uri skypeUri = Uri.parse(mySkypeUri);
            myIntent.setData(skypeUri);
        } else {
            myIntent = new Intent(Intent.CATEGORY_LAUNCHER);
        }

        // Restrict the Intent to being handled by the Skype for Android client only.
        myIntent.setComponent(new ComponentName(Constant.PACKAGE_NAME_SKYPE, "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Initiate the Intent. It should never fail because you've already established the
        // presence of its handler (although there is an extremely minute window where that
        // handler can go away).
        myContext.startActivity(myIntent);

        return;
    }

    /**
     * Determine whether the Skype for Android client is installed on this device.
     *
     * @param myContext
     * @return
     */
    public boolean isClientInstalled(Context myContext, String packageName) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    /**
     * Install the Skype client through the market: URI scheme.
     *
     * @param myContext
     */
    public void goToMarket(Context myContext, String packageName) {
        Uri marketUri = Uri.parse("market://details?id=" + packageName);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);
        return;
    }

    /**
     * getSkypeContacts
     */
    public ArrayList<String> getSkypeContacts() {
        // Get db
        Cursor c = getContentResolver().query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts.SOURCE_ID, ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.skype.contacts.sync"},
                null);

        int contactIdColumn = c.getColumnIndex(ContactsContract.RawContacts.SOURCE_ID);
        //int contactNameColumn = c.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY);

        ArrayList<String> mySkypeContacts = new ArrayList<String>();
        while (c.moveToNext()) {
            /// You can also read RawContacts.CONTACT_ID to query the
            // ContactsContract.Contacts table or any of the other related ones.
            mySkypeContacts.add(c.getString(contactIdColumn));
        }
        return mySkypeContacts;
    }

    /**
     * getDeviceId
     * @return
     */
    public String getDeviceId(){
        String deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    /**
     * getDeviceName
     * @return
     */
    public String getDeviceName(){
        String deviceName = Build.MODEL;
        return deviceName;
    }

    /**
     * getDeviceVersion
     * @return
     */
    public String getDeviceVersion(){
        String deviceVersion = "Android " + Build.VERSION.RELEASE;
        return deviceVersion;
    }

    /**
     * getDeviceVersion
     * @return
     */
    public String getLocaleCountry (){
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Locale loc = new Locale("", mTelephonyManager.getNetworkCountryIso());
        loc.getDisplayCountry();
        return loc.getDisplayCountry();
    }
}