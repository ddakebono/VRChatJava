package io.github.vrchatapi;

import org.json.JSONObject;

public class VRCAnalytics {

    protected VRCAnalytics() {}

    public static int getUserCount(){
        JSONObject count = ApiModel.sendGetRequest("visits", null);
        return Integer.parseInt(count.toString());
    }

}
