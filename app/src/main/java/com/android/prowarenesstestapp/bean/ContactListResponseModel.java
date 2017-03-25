package com.android.prowarenesstestapp.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class ContactListResponseModel implements Serializable{

    String message;
    String status;
    ArrayList<ContactResultModel>result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ContactResultModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<ContactResultModel> result) {
        this.result = result;
    }
}
