package com.admin.shopkeeper.entity;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/1.
 */

public class GuizeBean implements Serializable {
    String type;
    String value;
    String start;
    String end;

    public GuizeBean() {

    }

    public static GuizeBean getVoidBean() {
        return new GuizeBean("", "", "", "");
    }

    public GuizeBean(String type, String value, String start, String end) {
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
