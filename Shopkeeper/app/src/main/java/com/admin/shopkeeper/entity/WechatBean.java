package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/18.
 */

public class WechatBean implements Serializable {
//{\"GUID\":\"a6a70894-58e0-4826-987c-0ca07819ead7\",
// \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
// \"PersonCenter\":3,
// \"WeiXinOrder\":1,
// \"WeiXinYuDin\":\"2\",
// \"WeiXinWaiMai\":\"2\",
// \"WeiXinKuaiCan\":\"1\",
// \"TanDian\":\"1\"}"\"EnableCenter\":\"1\",\"EnableOrder\":\"1\"

    @SerializedName("GUID")
    private String guid;
    @SerializedName("RESTAURANTID")
    private String shopId;
    @SerializedName("PersonCenter")
    private double personCenter;
    @SerializedName("WeiXinOrder")
    private double weixinOrder;
    @SerializedName("WeiXinYuDin")
    private int weixinYuding;
    @SerializedName("WeiXinWaiMai")
    private int weixinWaimai;
    @SerializedName("WeiXinKuaiCan")
    private int weixinKuaican;
    @SerializedName("TanDian")
    private int tandian;
    @SerializedName("EnableCenter")
    private int jifenAdding;
    @SerializedName("EnableOrder")
    private int jifenExchange;

    public int getJifenAdding() {
        return jifenAdding;
    }

    public void setJifenAdding(int jifenAdding) {
        this.jifenAdding = jifenAdding;
    }

    public int getJifenExchange() {
        return jifenExchange;
    }

    public void setJifenExchange(int jifenExchange) {
        this.jifenExchange = jifenExchange;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getPersonCenter() {
        return personCenter;
    }

    public void setPersonCenter(double personCenter) {
        this.personCenter = personCenter;
    }

    public double getWeixinOrder() {
        return weixinOrder;
    }

    public void setWeixinOrder(double weixinOrder) {
        this.weixinOrder = weixinOrder;
    }

    public int getWeixinYuding() {
        return weixinYuding;
    }

    public void setWeixinYuding(int weixinYuding) {
        this.weixinYuding = weixinYuding;
    }

    public int getWeixinWaimai() {
        return weixinWaimai;
    }

    public void setWeixinWaimai(int weixinWaimai) {
        this.weixinWaimai = weixinWaimai;
    }

    public int getWeixinKuaican() {
        return weixinKuaican;
    }

    public void setWeixinKuaican(int weixinKuaican) {
        this.weixinKuaican = weixinKuaican;
    }

    public int getTandian() {
        return tandian;
    }

    public void setTandian(int tandian) {
        this.tandian = tandian;
    }
}
