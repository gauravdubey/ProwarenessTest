package com.android.prowarenesstestapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.prowarenesstestapp.R;
import com.android.prowarenesstestapp.adapter.ContactListAdapter;
import com.android.prowarenesstestapp.api_interfaces.Api_Interface;
import com.android.prowarenesstestapp.bean.ContactListResponseModel;
import com.android.prowarenesstestapp.bean.ContactResultModel;
import com.android.prowarenesstestapp.tables.ContactTableUtils;
import com.android.prowarenesstestapp.utils.AppConstant;
import com.android.prowarenesstestapp.utils.FontHelper;
import com.android.prowarenesstestapp.utils.SpacesItemDecoration;
import com.android.prowarenesstestapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContactListActivity extends BaseToolbar implements View.OnClickListener{

    private Context mContext;
    private TextView tvNoData;
    private RecyclerView rvContactList;
    private RelativeLayout layoutMain, vgNoInternet;
    private Dialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_contact_list);
        initiateView();
    }

    private void initiateView() {

        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
        rvContactList = (RecyclerView) findViewById(R.id.rvContactList);
        tvNoData = (TextView) findViewById(R.id.tvNoData);
        vgNoInternet = (RelativeLayout) findViewById(R.id.vgNoInternet);
        Button btnTryAgain = (Button)findViewById(R.id.btnTryAgain);

        FontHelper.applyFont(mContext, layoutMain, AppConstant.FONT_LARKE_NEUE);

        rvContactList.setHasFixedSize(true);
        rvContactList.addItemDecoration(new SpacesItemDecoration(5));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvContactList.setLayoutManager(mLayoutManager);

        if (Utils.isNetWorkAvailable(mContext)){
            getContactList(false);
        }else {
            Snackbar.make(layoutMain, R.string.no_internet_message,Snackbar.LENGTH_SHORT).show();
            vgNoInternet.setVisibility(View.VISIBLE);
            rvContactList.setVisibility(View.GONE);
        }
        addToolbar(getString(R.string.contactListHeader), mContext);
        btnTryAgain.setOnClickListener(this);
        ivRefresh.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnTryAgain:

                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);

                break;
            case R.id.ivRefresh:

                if (Utils.isNetWorkAvailable(mContext)){
                    getContactList(true);
                }else {
                    Snackbar.make(layoutMain, R.string.no_internet_message,Snackbar.LENGTH_SHORT).show();
                    vgNoInternet.setVisibility(View.VISIBLE);
                    rvContactList.setVisibility(View.GONE);
                }
                break;
        }
    }



    /**
     * Call Vehicle List
     */
    private void getContactList(boolean isRefresh) {

        mProgressDialog= Utils.showProgressDialog(mContext);
        //create an adapter for retrofit with base url
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConstant.BASE_URL).build();

        //creating a service for adapter with our POST class
        Api_Interface git = restAdapter.create(Api_Interface.class);
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put(AppConstant.TOKEN, AppConstant.TOKEN_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        git.getContactList(params, new Callback<ContactListResponseModel>() {
            @Override
            public void success(ContactListResponseModel contactListResponseModel, Response response) {

                Utils.cancelProgressDialog(mProgressDialog);

                if (contactListResponseModel.getStatus().equalsIgnoreCase(AppConstant.SUCCESS)) {
                    insertCityList(contactListResponseModel.getResult(),isRefresh);
                } else {
                    noDataAvailable();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Utils.cancelProgressDialog(mProgressDialog);
                Utils.showSnackBar(layoutMain, getString(R.string.error_message));
            }
        });
    }

    /**
     * Insert City List
     */
    private void insertCityList(ArrayList<ContactResultModel>list,boolean isRef){
        new Thread(){
            @Override
            public void run() {
                if (list!=null && list.size()>0){
                    if (isRef){
                        ContactTableUtils.updateRecords(mContext,list);
                    }else {
                        ContactTableUtils.saveContactList(mContext,list);
                    }
                }
                ArrayList<ContactResultModel>aContactList=ContactTableUtils.getContactList(mContext);

                runOnUiThread(() -> {
                    try {
                        if (aContactList!=null && aContactList.size()>0){
                            ContactListAdapter contactListAdapter= new ContactListAdapter(mContext, aContactList);
                            rvContactList.setAdapter(contactListAdapter);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        }.start();
    }


    /**
     * Set Contact List
     * @param aContactList
     */
    private void setContactData(ArrayList<ContactResultModel>aContactList){
        try {

            if (aContactList!=null && aContactList.size()>0){
                ContactListAdapter contactListAdapter= new ContactListAdapter(mContext, aContactList);
                rvContactList.setAdapter(contactListAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Set No Data Available
     */
    private void noDataAvailable(){

        if (tvNoData.getVisibility() == View.GONE){

            tvNoData.setVisibility(View.VISIBLE);
            rvContactList.setVisibility(View.GONE);
        }else {
            tvNoData.setVisibility(View.GONE);
            rvContactList.setVisibility(View.VISIBLE);
        }
    }

}
