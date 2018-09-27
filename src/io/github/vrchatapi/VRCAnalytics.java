package io.github.vrchatapi;

public class VRCAnalytics {

    protected VRCAnalytics() {}

    public static int getUserCount(){
        String count = ApiModel.sendGetRequestRaw("visits", null);
        return Integer.parseInt(count);
    }

}
