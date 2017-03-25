package com.android.prowarenesstestapp.bean;

import java.io.Serializable;

/**
 * Created by igaur on 25-03-2017.
 */

public class ContactResultModel implements Serializable{

    String name;
    String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
