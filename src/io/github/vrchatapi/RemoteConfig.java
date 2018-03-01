package io.github.vrchatapi;

import java.util.logging.Logger;

import org.json.JSONObject;

public class RemoteConfig {
	
	private static Logger log = Logger.getLogger(RemoteConfig.class.getSimpleName());
	
	private static JSONObject config;
	
	static {
		config = HttpUtil.get("config");
		log.info("RemoteConfig: ");
		log.info("\tclientApiKey: " + getClientApiKey());
		log.info("\treleaseSdkUrl: " + getReleaseSdkVersion());
	}
	
	public static String getClientApiKey() {
		return config.optString("clientApiKey");
	}
	
	public static String getReleaseSdkVersion() {
		return config.optString("releaseSdkVersion");
	}
	
	public static JSONObject getConfig() {
		return config;
	}
	
}
