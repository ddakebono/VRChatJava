package io.github.vrchatapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class VRCFriends {

	public static List<VRCUser> fetchFriends() { return fetchFriends(FriendStatus.ONLINE); }
	public static List<VRCUser> fetchFriends(FriendStatus status) { return fetchFriends(status, 0); }
	public static List<VRCUser> fetchFriends(FriendStatus status, int from) { return fetchFriends(status, from, 100); }
	public static List<VRCUser> fetchFriends(FriendStatus status, int from, int count) {
		Map<String, Object> requestParams = new HashMap<>();
		requestParams.put("offset", from);
		requestParams.put("n", count);
		JSONArray array = ApiModel.sendGetRequestArray("auth/user/friends" + (status == FriendStatus.ONLINE ? "?offline=false" : "?offline=true"), requestParams);
		if(array == null) return new ArrayList<>();
		List<VRCUser> list = new ArrayList<>();
		for(Object obj : array) {
			if(obj instanceof JSONObject) {
				VRCUser user = new VRCUser();
				user.initBrief((JSONObject)obj);
				list.add(user);
			}
		}
		return list;
	}
	
	public static VRCNotification sendFriendRequest(VRCUser user) {
		JSONObject resp = ApiModel.sendPostRequest("user/" + user.id + "/friendRequest", null);
		VRCNotification notification = new VRCNotification();
		notification.Init(resp);
		return notification;
	}
	
}
