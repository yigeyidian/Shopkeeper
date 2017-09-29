package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 优惠券bean
 * {\"PiCi\":\"17021516464280295\",
 * \"Name\":\"节日优惠券\",
 * \"Price\":5.00,
 * \"SumPrice\":50.00,
 * \"Code\":\"10\",
 * \"BeginTime\":\"2017-02-15 00:00:00\",
 * \"EndTime\":\"2017-05-15 23:59:59\",
 * \"IntergialTypeID\":\"1\",
 * \"counts\":10}
 */

public class CouponManageBean implements Serializable {
    @SerializedName("PiCi")
    private String piCi;
    //名字
    @SerializedName("Name")
    private String name;
    //优惠金额
    @SerializedName("Price")
    private double price;
    //满多少能优惠
    @SerializedName("SumPrice")
    private double sumPrice;

    @SerializedName("Code")
    private String code;

    @SerializedName("BeginTime")
    private String beginTime;

    @SerializedName("EndTime")
    private String endTime;
    //优惠券类型
    @SerializedName("IntergialTypeID")
    private String typeId;
    @SerializedName("counts")
    private String counts;
    @SerializedName("MerchantID")
    private String merchantID;

    private int position;

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPiCi() {
        return piCi;
    }

    public void setPiCi(String piCi) {
        this.piCi = piCi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }
}
