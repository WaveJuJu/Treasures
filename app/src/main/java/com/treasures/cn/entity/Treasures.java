package com.treasures.cn.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Treasures implements Serializable {
    private String id = "";
    private String createTime = "";

    private String subTitle = "";
    private List<String> images = new ArrayList<>();//图片
    private int categoryTypeId = 0;//类目
    private String size = "";
    private String year = "";//年份
    private String describe = "";//描述
    private List<String> keywordsArr = new ArrayList<>();//关键词数组
    private String buyTime = "";//购买时间
    private double buyPrice = 0;//购买价格
    private double sellingPrice = 0;//售出价格
    private int soldType = 0;//是否可售 -1不可售 0可售 1已售
    private String remark = "";//备注
    private boolean isEnshrine = false;//是否收藏
    private boolean isUpload = false; //是否上传到云端

    private boolean anyBool1 = false;
    private boolean anyBool2 = false;
    private boolean anyBool3 = false;
    private boolean anyBool4 = false;
    private boolean anyBool5 = false;

    private String anyString1 = "";
    private String anyString2 = "";
    private String anyString3 = "";
    private String anyString4 = "";
    private String anyString5 = "";

    private double anyDouble1 = 0;
    private double anyDouble2 = 0;

    private String anyDate1 = "";
    private String anyDate2 = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(int categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getKeywordsArr() {
        return keywordsArr;
    }

    public void setKeywordsArr(List<String> keywordsArr) {
        this.keywordsArr = keywordsArr;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getSoldType() {
        return soldType;
    }
    public void setSoldType(int soldType) {
        this.soldType = soldType;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnshrine() {
        return isEnshrine;
    }

    public void setEnshrine(boolean enshrine) {
        isEnshrine = enshrine;
    }

    public boolean isAnyBool1() {
        return anyBool1;
    }

    public void setAnyBool1(boolean anyBool1) {
        this.anyBool1 = anyBool1;
    }

    public boolean isAnyBool2() {
        return anyBool2;
    }

    public void setAnyBool2(boolean anyBool2) {
        this.anyBool2 = anyBool2;
    }

    public boolean isAnyBool3() {
        return anyBool3;
    }

    public void setAnyBool3(boolean anyBool3) {
        this.anyBool3 = anyBool3;
    }

    public boolean isAnyBool4() {
        return anyBool4;
    }

    public void setAnyBool4(boolean anyBool4) {
        this.anyBool4 = anyBool4;
    }

    public boolean isAnyBool5() {
        return anyBool5;
    }

    public void setAnyBool5(boolean anyBool5) {
        this.anyBool5 = anyBool5;
    }

    public String getAnyString1() {
        return anyString1;
    }

    public void setAnyString1(String anyString1) {
        this.anyString1 = anyString1;
    }

    public String getAnyString2() {
        return anyString2;
    }

    public void setAnyString2(String anyString2) {
        this.anyString2 = anyString2;
    }

    public String getAnyString3() {
        return anyString3;
    }

    public void setAnyString3(String anyString3) {
        this.anyString3 = anyString3;
    }

    public String getAnyString4() {
        return anyString4;
    }

    public void setAnyString4(String anyString4) {
        this.anyString4 = anyString4;
    }

    public String getAnyString5() {
        return anyString5;
    }

    public void setAnyString5(String anyString5) {
        this.anyString5 = anyString5;
    }

    public double getAnyDouble1() {
        return anyDouble1;
    }

    public void setAnyDouble1(double anyDouble1) {
        this.anyDouble1 = anyDouble1;
    }

    public double getAnyDouble2() {
        return anyDouble2;
    }

    public void setAnyDouble2(double anyDouble2) {
        this.anyDouble2 = anyDouble2;
    }

    public String getAnyDate1() {
        return anyDate1;
    }

    public void setAnyDate1(String anyDate1) {
        this.anyDate1 = anyDate1;
    }

    public String getAnyDate2() {
        return anyDate2;
    }

    public void setAnyDate2(String anyDate2) {
        this.anyDate2 = anyDate2;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }
}
