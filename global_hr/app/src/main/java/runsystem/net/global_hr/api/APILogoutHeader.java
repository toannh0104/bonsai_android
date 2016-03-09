package runsystem.net.global_hr.api;

import runsystem.net.global_hr.objects.ApiResponse;
import runsystem.net.global_hr.objects.LoginResult;

public class APILogoutHeader {
	public ApiResponse  response;

	public APILogoutHeader() {
	}

	public ApiResponse getResponse() {
		return response;
	}

	public void setResponse(ApiResponse response) {
		this.response = response;
	}
}
