package com.chnchat.client.user;

import com.google.gson.annotations.Expose;

/**
 * @author luan
 * @package com.chnchat.client.user
 * @date 2020/9/25
 * @des
 */
public class UserBean {
    @Expose
    public String uuid;

    /**
     * userInfo : {"expireTime":"exp date: 2022.01.01","avatarUrl":"http://xzw-audio.oss-cn-hongkong.aliyuncs.com/1588349089629.jpg","level":1,"nickName":"figo","integral":25,"count":1,"disabled":true,"timeStr":"2022-01-01","isTourist":false,"studentNum":3}
     */

    public UserInfoBean userInfo;

    public static class UserInfoBean {
        /**
         * expireTime : exp date: 2022.01.01
         * avatarUrl : http://xzw-audio.oss-cn-hongkong.aliyuncs.com/1588349089629.jpg
         * level : 1
         * nickName : figo
         * integral : 25
         * count : 1
         * disabled : true
         * timeStr : 2022-01-01
         * isTourist : false
         * studentNum : 3
         */

        public String expireTime;
        public String avatarUrl;
        public int level;
        public String nickName;
        public int integral;
        public int count;
        public boolean disabled;
        public String timeStr;
        public boolean isTourist;
        public int studentNum;
    }
}
