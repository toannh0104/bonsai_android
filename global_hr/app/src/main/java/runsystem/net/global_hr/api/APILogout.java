package runsystem.net.global_hr.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.objects.ApiResponse;
import runsystem.net.global_hr.objects.LoginResult;

public class APILogout {
    public String token = "";

    // Header
    private APILogoutHeader header;
    private LogoutOnResult onResult;
    private RequestQueue queue = null;
    private String url = "";

    /**
     * Interface
     */
    public interface LogoutOnResult {
        void onLogoutOnResult(APILogoutHeader header);
    }

    /**
     * Result
     *
     * @param result
     */
    public void setLogoutOnResult(LogoutOnResult result) {
        this.onResult = result;
    }

    /**
     * Start API
     *
     * @param context
     */
    public void startAPI(Context context) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // URL
        String url = Constant.LINK_SERVER + Constant.API_LOGOUT;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stopRequest();
                Log.e("API_LOGOUT", response);
                // Response
                header = new APILogoutHeader();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.setStatus(jsonObject.getString(Constant.KEY_STATUS));
                    apiResponse.setMessage(jsonObject.getString(Constant.KEY_MESSAGE));
                    header.setResponse(apiResponse);
                    //onResult.onLogoutOnResult(header);
                } catch (JSONException e) {
                    Log.e("onResponse Error", e.getMessage());
                    //onResult.onLogoutOnResult(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error
                Log.e("onResponse", error.toString());
                //onResult.onLogoutOnResult(null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set post header
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.PREF_KEY_TOKEN_AUTH, token);
                Log.e("Headers", params.toString());
                return params;
            }
        };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        Constant.SERVER_TIME_OUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        // Start post
        queue.add(request);
    }

    /**
     * stopRequest
     */
    public void stopRequest() {
        if (queue != null) {
            queue.stop();
        }
    }
}
