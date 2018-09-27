package io.github.vrchatapi;

import org.json.JSONObject;

import java.util.List;

public class VRCWorldInstance {

	//ID Contains instance ID + tags
	protected String id;
	//Name just has instance ID
	protected String name;
	protected List<VRCUser> privateUsers;
	protected List<VRCUser> friendUsers;
	protected List<VRCUser> users;
	protected String hidden;
	protected String nonce;


	protected VRCWorldInstance() {}
	
	public void init(JSONObject obj) {
		this.id = obj.getString("id");
		this.name = obj.getString("name");
		this.hidden = obj.getString("hidden");
		this.nonce = obj.getString("nonce");
	}

	public static VRCWorldInstance fetch(String worldID, String instanceID){
		JSONObject inst = ApiModel.sendGetRequest("worlds/" + worldID + "/" + instanceID, null);
		VRCWorldInstance instance = new VRCWorldInstance();
		instance.init(inst);
		return instance;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<VRCUser> getPrivateUsers() {
		return privateUsers;
	}

	public List<VRCUser> getFriendUsers() {
		return friendUsers;
	}

	public List<VRCUser> getUsers() {
		return users;
	}

	public String getHidden() {
		return hidden;
	}

	public String getNonce() {
		return nonce;
	}
}
