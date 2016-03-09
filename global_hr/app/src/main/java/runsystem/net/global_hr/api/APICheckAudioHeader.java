package runsystem.net.global_hr.api;

import runsystem.net.global_hr.objects.ApiResponse;
import runsystem.net.global_hr.objects.AudioResult;

public class APICheckAudioHeader {
	public ApiResponse  response;
	public AudioResult result;

	public APICheckAudioHeader() {
	}

	public ApiResponse getResponse() {
		return response;
	}

	public void setResponse(ApiResponse response) {
		this.response = response;
	}

	public AudioResult getResult() {
		return result;
	}

	public void setResult(AudioResult result) {
		this.result = result;
	}
}
