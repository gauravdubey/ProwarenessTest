package com.android.prowarenesstestapp.api_interfaces;


import com.android.prowarenesstestapp.bean.ContactListResponseModel;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface Api_Interface
{

    @FormUrlEncoded
    @POST("/contactlist.php")
    public void getContactList(@FieldMap Map<String,String> params, Callback<ContactListResponseModel> response);

}
