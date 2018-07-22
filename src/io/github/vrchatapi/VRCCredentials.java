package io.github.vrchatapi;

import java.time.LocalDateTime;
import java.util.Base64;

public class VRCCredentials {
	
	private static String username;
	private static String password;
	private static String webCredentials;
	private static String authToken;
	private static LocalDateTime tokenGranted;
	
	public static void setUser(String user, String pass) {
		VRCCredentials.username = user;
		VRCCredentials.password = pass;
		VRCCredentials.webCredentials = "basic " + Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
		VRCCredentials.authToken = null;
	}
	
	public static void clear() {
		VRCCredentials.username = null;
		VRCCredentials.password = null;
		VRCCredentials.webCredentials = null;
		VRCCredentials.authToken = null;
		VRCCredentials.tokenGranted = null;
	}
	
	public static void setAuthToken(String token) {
		VRCCredentials.username = null;
		VRCCredentials.password = null;
		VRCCredentials.authToken = token;
		VRCCredentials.tokenGranted = LocalDateTime.now();
	}
	
	public static String getWebCredentials() {
		return webCredentials;
	}
	
	public static String getAuthToken() {
		if(isTokenExpired() && tokenGranted!=null){
			//Token invalid, renew auth token
			authToken = null;
			tokenGranted = null;
			VRCUser.login();
		}
		return authToken;
	}

	public static boolean isTokenExpired(){
		if(tokenGranted!=null)
			return LocalDateTime.now().isAfter(tokenGranted.plusHours(1));
		else
			return true;
	}
	
	public static String getUsername() {
		return username;
	}
	
	protected static String getPassword() {
		return password;
	}
	
}
