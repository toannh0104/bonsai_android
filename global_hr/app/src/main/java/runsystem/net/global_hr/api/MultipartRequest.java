package runsystem.net.global_hr.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuanDang on 11/23/2015.
 */
public class MultipartRequest extends Request<String> {
    final Charset ENCODING_TYPE = Charset.forName("UTF-8");
    private MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
    private static final String PARAM_FILE = "file";

    private final Response.Listener<String> mListener;
    private final File file;
    private String fileName;
    private final HashMap<String, String> params;
    private final HashMap<String, String> headers;

    public  MultipartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, File file, String fileName, HashMap<String, String> params, HashMap<String, String> headers ) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        this.file = file;
        this.fileName = fileName;
        this.params = params;
        this.headers = headers;
        buildMultipartEntity();
    }

    /**
     * buildMultipartEntity
     */
    private void buildMultipartEntity() {
        ContentType contentType = ContentType.create("audio/wav");
        // File
        FileBody fileBody = new FileBody(file, contentType, fileName);
        entity.addPart(PARAM_FILE, fileBody);
        for (String key : params.keySet()) {
            StringBody stringBody = null;
            try {
                // param
                stringBody = new StringBody(params.get(key), ENCODING_TYPE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            entity.addPart(key, stringBody);
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.headers;
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
