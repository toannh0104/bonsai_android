package runsystem.net.global_hr.webview;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import runsystem.net.global_hr.BaseActivity;
import runsystem.net.global_hr.R;

/**
 * Created by LuanDang on 11/18/2015.
 */
public class CWebViewClient extends WebViewClient{
    private static final String TAG = "CWebViewClient";
    private BaseActivity activity;

    public CWebViewClient(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        super.shouldOverrideUrlLoading(view, url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.activity.showLoading("");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "Finished loading URL: " + url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

}
