package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class BussinessBean implements Serializable {
    //{\"PERSONCOUNT\":12,\"CHARGEMONEY\":0.00,\"FREEMONEY\":0.00,\"Count\":0.000000}
    @SerializedName("PERSONCOUNT")
    private int personCount;
    @SerializedName("CHARGEMONEY")
    private double chargeMoney;
    @SerializedName("FREEMONEY")
    private double freeMoney;
    @SerializedName("Count")
    private double count;

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public double getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
