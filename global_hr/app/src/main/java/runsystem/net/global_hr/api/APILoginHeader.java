package runsystem.net.global_hr.api;

import runsystem.net.global_hr.objects.ApiResponse;
import runsystem.net.global_hr.objects.AudioResult;
import runsystem.net.global_hr.objects.LoginResult;

public class APILoginHeader {
	public ApiResponse  response;
	public LoginResult result;

	public APILoginHeader() {
	}

	public ApiResponse getResponse() {
		return response;
	}

	public void setResponse(ApiResponse response) {
		this.response = response;
	}

	public LoginResult getResult() {
		return result;
	}

	public void setResult(LoginResult result) {
		this.result = result;
	}
}
