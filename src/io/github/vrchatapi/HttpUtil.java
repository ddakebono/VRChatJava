package io.github.vrchatapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.json.JSONObject;

public class HttpUtil {
	
	private static final String API_URL = "https://api.vrchat.cloud/api/1/";
	
	private static Logger log = Logger.getLogger(HttpUtil.class.getSimpleName());
	
	// simplest
	public static JSONObject get(String to) {
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(API_URL + to);
			log.info("Requesting " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("user-agent", "VRChatJava");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			log.info("Result " + result.toString());
			return new JSONObject(result.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static JSONObject apiGet(String endpoint, String params, String auth, boolean basic) {
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(API_URL + endpoint + "?apiKey=" + RemoteConfig.getClientApiKey() + "&" + params);
			log.info("Requesting " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("user-agent", "VRChatJava");
			if(auth != null) {
				if(basic) {
					conn.setRequestProperty("Authorization", "basic " + auth);
				}else {
					conn.setRequestProperty("cookie", "auth=" + auth);
				}
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			log.info("Result " + result.toString());
			return new JSONObject(result.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
