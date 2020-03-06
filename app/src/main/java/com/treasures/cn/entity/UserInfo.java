package com.treasures.cn.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfo implements Serializable {
    private long userId = 0;
    private String createTime = "";
    private String niceName = "";
    private String password = "";
    private String headerImgPath = "";
    private String inviteCode = "";
    private boolean isPrivacy = false; //是否同意了隐私权限
    private List<String> inviteCodeArr = new ArrayList<>();

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeaderImgPath() {
        return headerImgPath;
    }

    public void setHeaderImgPath(String headerImgPath) {
        this.headerImgPath = headerImgPath;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public List<String> getInviteCodeArr() {
        return inviteCodeArr;
    }

    public void setInviteCodeArr(List<String> inviteCodeArr) {
        this.inviteCodeArr = inviteCodeArr;
    }

    public boolean isPrivacy() {
        return isPrivacy;
    }

    public void setPrivacy(boolean privacy) {
        isPrivacy = privacy;
    }
}
