package runsystem.net.global_hr.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.objects.ApiResponse;
import runsystem.net.global_hr.objects.AudioResult;

public class APICheckAudio {


	public static String KEY_RESULT = "resultDto";
	public static String KEY_SIZE = "size";
	public static String KEY_FILE_NAME = "fileName";
	public static String KEY_KANA = "kana";
	public static String KEY_KANJI = "kanji";
	public static String KEY_ROMAJI = "romaji";

	public String lessonId = "";
	public String txtQuestion = "";
	public int modeLanguage = Constant.MODE_LANG_KANA;
	public String fileName = "";
	public File file = null;
	public String token = "";
	
	// Header
	private APICheckAudioHeader header;
	private CheckAudioOnResult onResult;
	private RequestQueue queue = null;
	private String url = "";
	/**
	 * Interface
	 *
	 */
	public interface CheckAudioOnResult {
		void onCheckAudioOnResult(APICheckAudioHeader header);
	}
	
	/**
	 * Result
	 * @param result
	 */
	public void setCheckAudioOnResult(CheckAudioOnResult result) {
		this.onResult = result;
	}
	
	/**
	 * Start API
	 * @param context
	 */
	public void startAPI(Context context) {
		// Instantiate the RequestQueue
		queue = Volley.newRequestQueue(context);
		// URL
		url = Constant.LINK_SERVER + Constant.API_POST_AUDIO;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lessonId", lessonId);
		params.put("txtQuestion", txtQuestion);
		params.put("modeLanguage", String.valueOf(modeLanguage));

		// Headers
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(Constant.PREF_KEY_TOKEN_AUTH, token);

		Response.Listener<String> mListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				{
					stopRequest();
					Log.e("API_POST_AUDIO", response);
					// Response
					header = new APICheckAudioHeader();
					try {
						JSONObject jsonObject = new JSONObject(response);
						ApiResponse apiResponse = new ApiResponse();
						apiResponse.setStatus(jsonObject.getString(Constant.KEY_STATUS));
						apiResponse.setMessage(jsonObject.getString(Constant.KEY_MESSAGE));
						header.setResponse(apiResponse);
						if (header.getResponse().getStatus().equalsIgnoreCase(Constant.STATUS_OK)) {
							AudioResult audioResult = new AudioResult();
							JSONObject json = new JSONObject(jsonObject.getString(KEY_RESULT));
							audioResult.setSize(json.getString(KEY_SIZE));
							audioResult.setFileName(json.getString(KEY_FILE_NAME));
							audioResult.setKana(json.getString(KEY_KANA));
							audioResult.setKanji(json.getString(KEY_KANJI));
							audioResult.setRomaji(json.getString(KEY_ROMAJI));
							header.setResult(audioResult);
						} else {
							header.setResult(null);
						}
						onResult.onCheckAudioOnResult(header);
					} catch (JSONException e) {
						Log.e("onResponse Error", e.getMessage());
						onResult.onCheckAudioOnResult(null);
					}
				}
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// Error
				onResult.onCheckAudioOnResult(null);
			}
		};
		MultipartRequest request = new MultipartRequest(url, mListener, errorListener, file, fileName, params, headers);
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
	public void stopRequest(){
		if (queue != null) {
			queue.stop();
		}
	}
}
