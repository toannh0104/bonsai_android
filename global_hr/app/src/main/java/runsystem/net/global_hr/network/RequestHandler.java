package runsystem.net.global_hr.network;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import runsystem.net.global_hr.common.Constant;

public class RequestHandler {
    private static final String LOG_TAG = "RequestHandler";
    private Context mContext;
    private Activity mActivity;
    private static String mFileName = null;
    public RequestHandler( Activity activity) {
        //this.mContext = mContext;
        this.mActivity = activity;
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecord_global.wav";
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @SuppressWarnings("deprecation")
    public void execute() {
        //Toast.makeText(mContext,"POST", Toast.LENGTH_SHORT).show();

        HttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Constant.LINK_SERVER + Constant.API_POST_AUDIO);

        File file = new File(mFileName);
        file.exists();

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.setCharset(Charset.forName("UTF-8"));

        /* example for setting a HttpMultipartMode */
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        /* example for adding an file part */
        FileBody fileBody = new FileBody(file);
        builder.addPart("file", fileBody);

        builder.addPart("lessonId", new StringBody("1", ContentType.TEXT_PLAIN));

        try {
            builder.addPart("txtQuestion", new StringBody("家内は学生ですか", Charset.forName("UTF-8")));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        httppost.setEntity(builder.build());

        HttpResponse response;
        try {
            response = client.execute(httppost);
            // Log.d(TAG, "UPLOAD: executed");
            HttpEntity resEntity = response.getEntity();
            // Log.d(TAG, "UPLOAD: respose code: " +
            // response.getStatusLine().toString());
            Log.e(LOG_TAG, "OK");
            if (resEntity != null) {
                String retSrc = EntityUtils.toString(resEntity);
                System.out.println(retSrc);
                JSONObject jsonObject = new JSONObject(retSrc);
                String fileName = jsonObject.getString("fileName");
                this.mActivity.finish();
                Log.e(LOG_TAG, fileName);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.toString());
        }
    }
}
