package com.chnchat.client.net.param;

/**
 * @author luan
 * @package com.chnchat.client.frame.net.param
 * @date 2020/6/29
 * @des
 */
public class LoginWXParam {
     String openid;
     String unionid;
     String nickName;
     String avatarUrl;
     int sexType;

    public LoginWXParam() {
    }

    public LoginWXParam(String openid, String unionid, String nickName, String avatarUrl, int sexType) {
        this.openid = openid;
        this.unionid = unionid;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.sexType = sexType;
    }
}
