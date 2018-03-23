package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 * {\"DateTimes\":\"2017-10\",\"weixin\":\"500.00\",\"zengson\":\"150.00\",
 * \"diannei\":\"100.00\",\"TOTALPRICE\":\"750.00\"}
 */

public class RechargeDetailTableBean implements Serializable {

    @SerializedName("DateTimes")
    String date;
    @SerializedName("weixin")
    String weixin;
    @SerializedName("zengson")
    String zengsong;
    @SerializedName("diannei")
    String diannei;
    @SerializedName("TOTALPRICE")
    String totalPrice;

    String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getZengsong() {
        return zengsong;
    }

    public void setZengsong(String zengsong) {
        this.zengsong = zengsong;
    }

    public String getDiannei() {
        return diannei;
    }

    public void setDiannei(String diannei) {
        this.diannei = diannei;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
