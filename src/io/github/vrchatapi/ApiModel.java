package io.github.vrchatapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import io.github.vrchatapi.logging.Log;

public class ApiModel {
	
	private static final String API_URL = "https://api.vrchat.cloud/api/1/";
	protected static String apiKey;
	
	static {
		// TODO: Maybe move this to somewhere else?
		fetchApiKey();
	}
	
	private static void fetchApiKey() {
		if(!VRCRemoteConfig.isInitialized()) {
			VRCRemoteConfig.init();
		}
		if(VRCRemoteConfig.isInitialized()) {
			apiKey = VRCRemoteConfig.getString("clientApiKey");
			if(apiKey == null || apiKey.isEmpty()) {
				Log.ERROR("Could not fetch client api key - unknown error.");
			}
		}else {
			Log.ERROR("Could not fetch client api key - config not initialized");
		}
	}
	
	public static JSONObject sendGetRequest(String endpoint, Map<String, Object> requestParams) {
		return sendRequest(endpoint, "get", requestParams);
	}
	
	public static JSONObject sendPostRequest(String endpoint, Map<String, Object> requestParams) {
		return sendRequest(endpoint, "post", requestParams);
	}

	public static JSONObject sendPutRequest(String endpoint, Map<String, Object> requestParams) {
		return sendRequest(endpoint, "put", requestParams);
	}
	
	protected static JSONObject sendRequest(String endpoint, String method, Map<String, Object> requestParams) {
		JSONObject resp = null;
		String apiUrl = getApiUrl();
		String uri = apiUrl + endpoint;
		boolean hasOne = false;
		if(apiKey != null) {
			uri += "?apiKey=" + apiKey;
			hasOne = true;
		}
		String requestText = "";
		if(requestParams != null) {
			if(method.equalsIgnoreCase("get")) {
				uri += (hasOne ? "&" : "?") + HttpUtil.urlEncode(requestParams);
			}else {
				requestText = new JSONObject(requestParams).toString();
			}
		}
		Log.INFO("Sending " + method + " request to " + uri);
		try {
			URL url = new URL(uri);
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod(method.toUpperCase());
			conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			conn.setRequestProperty("Content-Type", (requestText.isEmpty()) ? "application/x-www-form-urlencoded" : "application/json");
			conn.setRequestProperty("user-agent", "VRChatJava");
			if(VRCCredentials.getWebCredentials() != null) {
				conn.setRequestProperty("Authorization", VRCCredentials.getWebCredentials());
			}
			if(VRCCredentials.getAuthToken() != null) {
				conn.setRequestProperty("Cookie", "auth=" + VRCCredentials.getAuthToken());
			}
			if(!requestText.isEmpty()) {
				conn.setDoOutput(true);
				conn.getOutputStream().write(requestText.getBytes());
			}
			StringBuilder result = new StringBuilder();
			BufferedReader rd = null;
			if(conn.getResponseCode() != 200) {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}else {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			resp = new JSONObject(result.toString());
			if(conn.getResponseCode() != 200) {
				String error = resp.getJSONObject("error").getString("message");
				Log.ERROR("Error sending request - " + error);
				throw new VRCException(error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	public static String getApiUrl() {
		return API_URL;
	}
	
}
