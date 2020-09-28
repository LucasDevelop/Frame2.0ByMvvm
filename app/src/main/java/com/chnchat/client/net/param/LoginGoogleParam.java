package com.chnchat.client.net.param;

/**
 * @author luan
 * @package com.chnchat.client.frame.net.param
 * @date 2020/6/29
 * @des
 */
public class LoginGoogleParam {
     String google;
     String nickName;
     String avatarUrl;
     int sexType;

    public LoginGoogleParam() {
    }

    public LoginGoogleParam(String google, String nickName, String avatarUrl, int sexType) {
        this.google = google;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.sexType = sexType;
    }
}
