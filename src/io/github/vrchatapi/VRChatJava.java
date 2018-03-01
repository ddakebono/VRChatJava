package io.github.vrchatapi;

public class VRChatJava {
	
	private static final String VERSION = "2018.02.23.18.45";
	
	/**
	 * 
	 * A method to check if the Library's version matches the SDK version.
	 * 
	 * @return
	 */
	public static boolean checkVersion() {
		return VERSION.equals(RemoteConfig.getReleaseSdkVersion());
	}
	
}
