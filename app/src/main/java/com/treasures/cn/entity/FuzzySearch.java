package com.treasures.cn.entity;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.entity
 * @ClassName: FuzzySearch
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-14 11:54
 * */
public class FuzzySearch {
    private String searchContent = "";
    private int yearStatus = -1; //-1->空 0->降序 1->升序
    private int priceStatus = -1; //-1->空 0->降序 1->升序

    private int soldType = -2;//是否可售 -1不可售 0可售 1已售 -2 未选
    private String keyword = ""; //关键词筛选
    private int categoryTypeId = 0;

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public int getYearStatus() {
        return yearStatus;
    }

    public void setYearStatus(int yearStatus) {
        this.yearStatus = yearStatus;
    }

    public int getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(int priceStatus) {
        this.priceStatus = priceStatus;
    }

    public int getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(int categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public int getSoldType() {
        return soldType;
    }

    public void setSoldType(int soldType) {
        this.soldType = soldType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
