package com.chnchat.client.net.param;

/**
 * @author luan
 * @package com.chnchat.client.frame.net.param
 * @date 2020/6/29
 * @des
 */
public class LoginFBParam {
     String facebook;
     String nickName;
     String avatarUrl;
     int sexType;

    public LoginFBParam() {
    }

    public LoginFBParam(String facebook, String nickName, String avatarUrl, int sexType) {
        this.facebook = facebook;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.sexType = sexType;
    }
}
