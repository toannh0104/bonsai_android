package runsystem.net.global_hr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.util.Date;
import java.util.HashMap;

import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.common.LogoutService;
import runsystem.net.global_hr.network.NetworkUtils;
import runsystem.net.global_hr.webview.CWebViewClient;


public class LearnActivity extends BaseActivity {
    public static Activity activity = null;
    private LogoutService service = null;
    private WebView webview;
    private String url;
    private ImageButton btnHome, btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_learn);
        activity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnLogout = (ImageButton) findViewById(R.id.btn_logout);
        btnHome.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        webview = (WebView) findViewById(R.id.learn_webview);
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    closeLoading();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // do something
            }
        });

        webview.setWebViewClient(new CWebViewClient(this));
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new WebRecordInterface(this), "Android");

    }
    @Override
    protected void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url").trim();
            // Check network
            if (NetworkUtils.isNetworkConnected(this)) {
                webview.loadUrl(url);
            } else {
                // No connect internet
                showAlertInfo(this, " ", getString(R.string.mess_no_network), false);
            }
        } else {
            showAlertInfo(this, " ", "Error URL", true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (service == null)
            service = new LogoutService(this, this);
        service.getTimer().cancel();
        service.getTimer().start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if( ev.getAction() == MotionEvent.ACTION_UP){
            if (service != null){
                service.getTimer().cancel();
                service.getTimer().start();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (service != null){
            service.getTimer().cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == DialogRecorderActivity.DIALOG_RECORD_REQUEST) {
            // Make sure the request was successful
            if(resultCode == Activity.RESULT_OK){

                String index = data.getStringExtra("index");
                String content = data.getStringExtra("content");
                String message = data.getStringExtra("message");
                //webview.loadUrl("javascript:showSpeakResultMessage(\""+message+"\")");
                webview.loadUrl("javascript:fillSpeakResult(\""+index+"\", \""+content+"\")");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                String message = data.getStringExtra("message");
                if (!message.equals(""))
                    webview.loadUrl("javascript:showSpeakResultMessage(\""+message+"\")");
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_home:
                gotoHome();
                break;
            case R.id.btn_logout:
                processLogout(this);
                break;
        }
    }

    /**
     * gotoHome
     */
    private void gotoHome(){
        clearAllActivityTop(false);
    }

    /*=================== METHOD ===================*/

    /**
     * startActivity
     *
     * @param context
     */
    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, LearnActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}
