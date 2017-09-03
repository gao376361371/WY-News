package bean;

import java.util.List;

/**
 * Created by 17612 on 2017/7/27.
 */

public class NewsCommentBean {
    private int favCount;
    private int against;
    private boolean unionState;
    private String ip;
    private String siteName;
    private String postId;
    private String source;
    private String productKey;
    private String content;
    private DeviceInfoEntity deviceInfo;
    private int shareCount;
    private int buildLevel;
    private String createTime;
    private boolean anonymous;
    private int commentId;
    private boolean isDel;
    private UserEntity user;
    private int vote;

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public int getAgainst() {
        return against;
    }

    public void setAgainst(int against) {
        this.against = against;
    }

    public boolean isUnionState() {
        return unionState;
    }

    public void setUnionState(boolean unionState) {
        this.unionState = unionState;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getBuildLevel() {
        return buildLevel;
    }

    public void setBuildLevel(int buildLevel) {
        this.buildLevel = buildLevel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public static class DeviceInfoEntity {
        /**
         * deviceName : iPhone 7 Plus
         */
        private String deviceName;

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceName() {
            return deviceName;
        }
    }
    public static class UserEntity {
        /**
         * vipInfo :
         * nickname : 华为手机中国骄傲
         * location : 广东省湛江市
         * avatar : http://mobilepics.nosdn.127.net/ChEPlqU77f503VUqLTmHNRFLAn8Mc86t513378902
         * id : Y2hhb3JlbnlpamluZ0AxNjMuY29t
         * redNameInfo : []
         * userId : 3626956
         */
        private String vipInfo;
        private String nickname;
        private String location;
        private String avatar;
        private String id;
        private List<?> redNameInfo;
        private int userId;

        public void setVipInfo(String vipInfo) {
            this.vipInfo = vipInfo;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setRedNameInfo(List<?> redNameInfo) {
            this.redNameInfo = redNameInfo;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getVipInfo() {
            return vipInfo;
        }

        public String getNickname() {
            return nickname;
        }

        public String getLocation() {
            return location;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getId() {
            return id;
        }

        public List<?> getRedNameInfo() {
            return redNameInfo;
        }

        public int getUserId() {
            return userId;
        }
    }
}
