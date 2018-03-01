package io.github.vrchatapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class User {
	
	class PastDisplayName {
		private String displayName;
		private Date updatedAt;
		
		public PastDisplayName(String displayName, Date updatedAt) {
			this.displayName = displayName;
			this.updatedAt = updatedAt;
		}

		@Override
		public String toString() {
			return "PastDisplayName [displayName=" + displayName + ", updatedAt=" + updatedAt + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
			result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PastDisplayName other = (PastDisplayName) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (displayName == null) {
				if (other.displayName != null)
					return false;
			} else if (!displayName.equals(other.displayName))
				return false;
			if (updatedAt == null) {
				if (other.updatedAt != null)
					return false;
			} else if (!updatedAt.equals(other.updatedAt))
				return false;
			return true;
		}

		public String getDisplayName() {
			return displayName;
		}
		
		public Date getUpdatedAt() {
			return updatedAt;
		}

		private User getOuterType() {
			return User.this;
		}
	}
	
	private boolean inited = false;
	
	private String id;
	private String username;
	private String displayName;
	private List<PastDisplayName> pastDisplayNames = new ArrayList<>();
	private DeveloperType developerType;
	private boolean emailVerified;
	private boolean hasBirthday;
	private boolean unsubscribe;
	private List<User> friends;
	private String currentAvatar; // TODO: replace with avatar object
	private String currentAvatarImageUrl;
	private String currentAvatarAssetUrl;
	private String currentAvatarThumbnailImageUrl;	
	private int acceptedTOSVersion;	
	private boolean hasLoggedInFromClient;
	private String authToken;
	
	private String worldId;
	private String instanceId;
	private String location;
	
	private JSONObject data;

	public User(String id, boolean init) {
		this.id = id;
		if(init) init();
	}

	
	public User(String id) {
		this(id, true);
	}
	
	private void init() {
		if(inited) return;
		
		developerType = DeveloperType.valueOf(data.optString("developerType").toUpperCase());
		
		JSONArray friendsArr = data.optJSONArray("friends");
		if(friendsArr != null) {
			for(Object obj : friendsArr) {
				if(obj instanceof String) {
					
				}
			}
		}
		
		username = data.optString("username");
		displayName = data.optString("displayName");
		emailVerified = data.optBoolean("emailVerified");
		hasBirthday = data.optBoolean("hasBirthday");
		unsubscribe = data.optBoolean("unsubscribe");
		currentAvatar = data.optString("currentAvatar");
		currentAvatarImageUrl = data.optString("currentAvatarImageUrl");
		currentAvatarAssetUrl = data.optString("currentAvatarAssetUrl");
		currentAvatarThumbnailImageUrl = data.optString("currentAvatarThumbnailImageUrl");
		acceptedTOSVersion = data.optInt("acceptedTOSVersion");
		hasLoggedInFromClient = data.optBoolean("hasLoggedInFromClient");
		authToken = data.optString("authToken");
		worldId = data.optString("worldId");
		instanceId = data.optString("instanceId");
		location = data.optString("location");
		inited = true;
	}
	
	public boolean IsLoggedIn() {
		return authToken != null;
	}

	public String getId() {
		init();
		return id;
	}

	public String getUsername() {
		init();
		return username;
	}

	public String getDisplayName() {
		init();
		return displayName;
	}

	public List<PastDisplayName> getPastDisplayNames() {
		init();
		return pastDisplayNames;
	}

	public DeveloperType getDeveloperType() {
		init();
		return developerType;
	}

	public boolean isEmailVerified() {
		init();
		return emailVerified;
	}

	public boolean isHasBirthday() {
		init();
		return hasBirthday;
	}

	public boolean isUnsubscribe() {
		init();
		return unsubscribe;
	}

	public List<User> getFriends() {
		init();
		return friends;
	}

	public String getCurrentAvatar() {
		init();
		return currentAvatar;
	}

	public String getCurrentAvatarImageUrl() {
		init();
		return currentAvatarImageUrl;
	}

	public String getCurrentAvatarAssetUrl() {
		init();
		return currentAvatarAssetUrl;
	}

	public String getCurrentAvatarThumbnailImageUrl() {
		init();
		return currentAvatarThumbnailImageUrl;
	}

	public int getAcceptedTOSVersion() {
		init();
		return acceptedTOSVersion;
	}

	public boolean isHasLoggedInFromClient() {
		init();
		return hasLoggedInFromClient;
	}

	public String getAuthToken() {
		init();
		return authToken;
	}

	public String getWorldId() {
		init();
		return worldId;
	}

	public String getInstanceId() {
		init();
		return instanceId;
	}

	public String getLocation() {
		init();
		return location;
	}
	
	
}
