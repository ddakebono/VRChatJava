package io.github.vrchatapi;

import java.util.Base64;

public class VRCCredentials {
	
	private static String username;
	private static String password;
	private static String webCredentials;
	private static String authToken;
	
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
	}
	
	public static void setAuthToken(String token) {
		VRCCredentials.username = null;
		VRCCredentials.password = null;
		VRCCredentials.webCredentials = null;
		VRCCredentials.authToken = token;		
	}
	
	public static String getWebCredentials() {
		return webCredentials;
	}
	
	public static String getAuthToken() {
		return authToken;
	}
	
	public static String getUsername() {
		return username;
	}
	
	protected static String getPassword() {
		return password;
	}
	
}
