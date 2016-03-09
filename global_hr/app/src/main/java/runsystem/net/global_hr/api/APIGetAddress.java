package runsystem.net.global_hr.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import runsystem.net.global_hr.common.Constant;

public class APIGetAddress {

    public static String KEY_TOKEN = "token";

    public String latlong = "";

    private GetAddressOnResult onResult;
    private RequestQueue queue = null;
    private String url = "";

    /**
     * Interface
     */
    public interface GetAddressOnResult {
        void onAddressOnResult(String address);
    }

    /**
     * Result
     *
     * @param result
     */
    public void setAddressOnResult(GetAddressOnResult result) {
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
        String url = Constant.API_GET_ADDRESS + latlong;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stopRequest();
                Log.e("API_GET_ADDRESS", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String add = "";
                    if (status.equals(Constant.STATUS_OK)){
                        JSONArray jsonResult = jsonObject.getJSONArray("results");
                        JSONObject jsonValue = jsonResult.getJSONObject(0);
                        add = jsonValue.getString("formatted_address");
                        /*JSONArray jsonInfo = jsonValue.getJSONArray("address_components");
                        String country = "";
                        String political1 = "";
                        String political2 = "";
                        for (int i = 0; i < jsonInfo.length(); i++) {
                            JSONObject object = jsonInfo.getJSONObject(i);
                            JSONArray jsonType = object.getJSONArray("types");
                            for (int j = 0; j < jsonType.length(); j++) {
                                String type  = jsonType.getString(j);
                                if (type.equals("country")){
                                    country = object.getString("long_name");
                                }
                                if (type.equals("administrative_area_level_1")){
                                    political1 = object.getString("long_name");
                                }
                                if (type.equals("administrative_area_level_2")){
                                    political2 = object.getString("long_name");
                                }
                            }
                        }

                        if (!political2.equals(""))
                            add += political2;

                        if (!add.equals(""))
                            add += ", ";
                        add += political1;

                        if (!add.equals(""))
                            add += ", ";
                        add += country;*/
                    } else {
                        onResult.onAddressOnResult(add);
                    }
                    Log.e("Address", add);
                    onResult.onAddressOnResult(add);
                } catch (JSONException e) {
                    Log.e("onResponse Error", e.getMessage());
                    onResult.onAddressOnResult("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error
                Log.e("onResponse", error.toString());
                onResult.onAddressOnResult("");
            }
        });
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
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
