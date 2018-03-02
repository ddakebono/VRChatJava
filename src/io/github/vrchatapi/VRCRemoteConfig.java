package io.github.vrchatapi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.vrchatapi.logging.Log;

public class VRCRemoteConfig {
	
	private static JSONObject config;
	
	public static void init() {
		Log.INFO("Fetching fresh config");
		JSONObject obj = ApiModel.sendGetRequest("config", null);
		Log.ASSERT(!obj.has("error"), "could not fetch fresh config file.");
		config = obj;
	}
	
	public static boolean hasKey(String key) {
		if(!isInitialized()) {
			init();
		}
		if(isInitialized()) {
			return config.has(key);
		}
		return false;
	}
	
	public static String getString(String key) {
		if(!isInitialized()) {
			init();
		}
		if(isInitialized()) {
			return config.optString(key, null);
		}
		return null;
	}
	
	public static List<String> getList(String key) {
		if(!isInitialized()) {
			init();
		}
		List<String> result = new ArrayList<String>();
		if(isInitialized()) {
			JSONArray value = config.optJSONArray(key);
			if(value != null) {
				value.forEach((obj) -> result.add(obj.toString()));
			}
		}
		return result;
	}
	
	public static boolean isInitialized() {
		return config != null;
	}
	
	private VRCRemoteConfig() {}
	
}
