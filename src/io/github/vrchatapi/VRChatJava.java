package io.github.vrchatapi;

public class VRChatJava {
	
	private static final String VERSION = "2018.02.23.18.45";
	
	private static User globalUser;
	
	/**
	 * 
	 * A method to check if the Library's version matches the SDK version.
	 * 
	 * @return
	 */
	public static boolean checkVersion() {
		return VERSION.equals(RemoteConfig.getReleaseSdkVersion());
	}
	
	public static User getGlobalUser() {
		return globalUser;
	}
	
	public static void login(String username, String password) {
		globalUser = new User(username, password);
	}
	
}
