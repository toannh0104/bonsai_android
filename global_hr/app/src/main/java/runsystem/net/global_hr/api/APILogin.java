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

public class APILogin {


    public static String KEY_JP_BASIC = "learningJpBasic";
    public static String KEY_SF_BASIC = "learningSafetyBasic";
    public static String KEY_JP_ADV = "learningJpAdvance";
    public static String KEY_SF_ADV = "learningSafetyAdvance";

    public static String KEY_COMPLIANCE = "learningCompliance";
    public static String KEY_SECURITY = "learningSecurity";
    public static String KEY_HARAS = "learningHarassment";
    public static String KEY_MORAL = "learningMoral";

    public static String KEY_GOVER = "learningGovernance";
    public static String KEY_LB_PRO = "learningLaborProvisions";
    public static String KEY_VOYAGE = "learningVoyage";
    public static String KEY_JP_LIFE = "learningJpLife";
    public static String KEY_TOKEN = "token";

    public String userName = "";
    public String password = "";
    public String uuid = "";
    public String device_id = "";
    public String device_name = "";
    public String device_version = "";
    public String location = "";

    // Header
    private APILoginHeader header;
    private LoginOnResult onResult;
    private RequestQueue queue = null;
    private String url = "";

    /**
     * Interface
     */
    public interface LoginOnResult {
        void onLoginOnResult(APILoginHeader header);
    }

    /**
     * Result
     *
     * @param result
     */
    public void setLoginOnResult(LoginOnResult result) {
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
        String url = Constant.LINK_SERVER + Constant.API_LOGIN;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stopRequest();
                Log.e("API_LOGIN", response);
                // Response
                header = new APILoginHeader();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.setStatus(jsonObject.getString(Constant.KEY_STATUS));
                    apiResponse.setMessage(jsonObject.getString(Constant.KEY_MESSAGE));
                    header.setResponse(apiResponse);
                    if (header.getResponse().getStatus().equalsIgnoreCase(Constant.STATUS_OK)) {
                        LoginResult result = new LoginResult();
                        result.setLearningJpBasic(jsonObject.getString(KEY_JP_BASIC));
                        result.setLearningSafetyBasic(jsonObject.getString(KEY_SF_BASIC));
                        result.setLearningJpAdvance(jsonObject.getString(KEY_JP_ADV));
                        result.setLearningSafetyAdvance(jsonObject.getString(KEY_SF_ADV));

                        result.setLearningCompliance(jsonObject.getString(KEY_COMPLIANCE));
                        result.setLearningSecurity(jsonObject.getString(KEY_SECURITY));
                        result.setLearningHarassment(jsonObject.getString(KEY_HARAS));
                        result.setLearningMoral(jsonObject.getString(KEY_MORAL));

                        result.setLearningGovernance(jsonObject.getString(KEY_GOVER));
                        result.setLearningLaborProvisions(jsonObject.getString(KEY_LB_PRO));
                        result.setLearningVoyage(jsonObject.getString(KEY_VOYAGE));
                        result.setLearningJpLife(jsonObject.getString(KEY_JP_LIFE));
                        result.setToken(jsonObject.getString(KEY_TOKEN));
                        header.setResult(result);
                    } else {
                        header.setResult(null);
                    }
                    onResult.onLoginOnResult(header);
                } catch (JSONException e) {
                    Log.e("onResponse Error", e.getMessage());
                    onResult.onLoginOnResult(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error
                Log.e("onResponse", error.toString());
                onResult.onLoginOnResult(null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set post header
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Create post parameters
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", userName);
                params.put("password", password);
                params.put("uuid", uuid);
                params.put("user_id", "");
                params.put("device_id", device_id);
                params.put("device_name", "Android");
                params.put("device_version", device_version);
                params.put("location", location);
                Log.e("Param", params.toString());
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
