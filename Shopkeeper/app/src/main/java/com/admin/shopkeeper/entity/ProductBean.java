package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/19.
 */

public class ProductBean implements Serializable {
    //{\"ID\":\"42bd5036-0a7b-4db6-8616-34c81db8c5ce\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"PackageName\":\"一人套餐\",
    // \"Types\":\"0\",
    // \"SaveDate\":\"2017-08-10 16:47:05\",
    // \"Price\":28.00,
    // \"Status\":1,
    // \"Info\":\"\",
    // \"Candiscount\":0,
    // \"ProductTypes\":\"5DCC27B5-5ECA-43FA-8166-4E481FE0783A\",
    // \"MemberPice\":28.00,\"PackageImgName\":\"\"}

    @SerializedName("ID")
    private String id;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("PackageName")
    private String name;
    @SerializedName("Types")
    private String type;
    @SerializedName("SaveDate")
    private String saveData;
    @SerializedName("Price")
    private double price;
    @SerializedName("Status")
    private int status;
    @SerializedName("Info")
    private String info;
    @SerializedName("Candiscount")
    private int candiscount;
    @SerializedName("ProductTypes")
    private String productTypes;
    @SerializedName("MemberPice")
    private double memberPice;
    @SerializedName("PackageImgName")
    private String imgName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSaveData() {
        return saveData;
    }

    public void setSaveData(String saveData) {
        this.saveData = saveData;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCandiscount() {
        return candiscount;
    }

    public void setCandiscount(int candiscount) {
        this.candiscount = candiscount;
    }

    public String getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(String productTypes) {
        this.productTypes = productTypes;
    }

    public double getMemberPice() {
        return memberPice;
    }

    public void setMemberPice(double memberPice) {
        this.memberPice = memberPice;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
