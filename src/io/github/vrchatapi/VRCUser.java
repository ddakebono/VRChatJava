package io.github.vrchatapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.vrchatapi.logging.Log;

public class VRCUser {
	
	protected static VRCUser currentUser;

	public static boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public static VRCUser getCurrentUser() {
		return currentUser;
	}
	
	public static void logout() {
		VRCCredentials.clear();
		currentUser = null;
	}
	
	public static void login() {
		JSONObject obj = ApiModel.sendGetRequest("auth/user", null);
		if(obj.has("error")) {
			Log.FATAL("NOT Authenticated");
			return;
		}
		VRCUser user = new VRCUser();
		user.init(obj);
		currentUser = user;
	}
	
	public static void login(String username, String password) {
		VRCCredentials.setUser(username, password);
		login();
	}
	
	public static VRCUser fetch(String id) {
		JSONObject obj = ApiModel.sendGetRequest("users/" + id, null);
		if(obj == null || !obj.has("id")) {
			Log.WARN("Invalid user record received.");
			return null;
		}
		VRCUser user = new VRCUser();
		user.initBrief(obj);
		return user;
	}

	public static List<VRCUser> list(int offset, int count, boolean activeOnly, String search) {
		String endpoint = activeOnly ? "users/active" : "users";
		HashMap<String, Object> values = new HashMap<>();
		values.put("search", search);
		values.put("n", count);
		values.put("offset", offset);
		List<VRCUser> users = new ArrayList<>();
		ApiModel.sendGetRequestArray(endpoint, values).forEach((o) -> {
			if(o instanceof JSONObject) {
				JSONObject obj = (JSONObject)o;
				VRCUser user = new VRCUser();
				user.initBrief(obj);
				users.add(user);
			}
		});
		return users;
	}
	
	protected String id;
	protected String displayName;
	protected String username;
	protected String password;
	protected String avatarId;
	protected boolean verified;
	protected boolean hasEmail;
	protected boolean hasBirthday;
	protected int acceptedTOSVersion;
	protected DeveloperType developerType;
	protected String location;
	protected String worldID;
	protected List<String> friends = new ArrayList<>();
	protected String currentAvatarImageUrl;
	protected String currentAvatarThumbnailImageUrl;
	
	protected VRCUser() {}
	
	public void init(JSONObject json) {
		this.id = json.getString("id");
		this.username = json.getString("username");
		this.displayName = json.getString("displayName");
		this.verified = json.getBoolean("emailVerified");
		this.hasEmail = json.optBoolean("hasEmail", false);
		this.hasBirthday = json.optBoolean("hasBirthday", false);
		this.acceptedTOSVersion = json.optInt("acceptedTOSVersion", 0);
		this.avatarId = json.optString("currentAvatar", null);
		this.developerType = DeveloperType.valueOf(json.optString("developerType", "none").toUpperCase());
		JSONArray arr = json.optJSONArray("friends");
		if(arr != null) arr.forEach((obj) -> friends.add(obj.toString()));
	}
	
	public void initBrief(JSONObject json) {
		this.id = json.getString("id");
		this.username = json.getString("username");
		this.displayName = json.getString("displayName");
		this.avatarId = json.optString("currentAvatar", null);
		this.currentAvatarImageUrl = json.optString("currentAvatarImageUrl", null);
		this.currentAvatarThumbnailImageUrl = json.optString("currentAvatarThumbnailImageUrl", null);
		this.developerType = DeveloperType.valueOf(json.optString("developerType", "none").toUpperCase());
		this.worldID = json.optString("worldId", null);
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getAvatarId() {
		return avatarId;
	}
	
	public boolean isVerified() {
		return verified;
	}
	
	public boolean hasBirthday() {
		return hasBirthday;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}
	
	public int getAcceptedTOSVersion() {
		return acceptedTOSVersion;
	}
	
	public DeveloperType getDeveloperType() {
		return developerType;
	}
	
	public String getLocation() {
		return location;
	}

	public String getWorldID() {
		return worldID;
	}

	public String getId() {
		return id;
	}

	public String getCurrentAvatarImageUrl() {
        return currentAvatarImageUrl;
    }

    public String getCurrentAvatarThumbnailImageUrl() {
        return currentAvatarThumbnailImageUrl;
    }

    public boolean hasModerationPowers() {
		return developerType == DeveloperType.MODERATOR || developerType == DeveloperType.INTERNAL;
	}
	
	public boolean isFriend() {
		return getCurrentUser().friends.contains(id);
	}
	
	@Override
	public String toString() {
		return "VRCUser [Username: " + username + ", Displayname: " + displayName + "]";
	}
	
}
