package com.android.prowarenesstestapp.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.prowarenesstestapp.R;
import com.android.prowarenesstestapp.utils.AppConstant;


public class BaseToolbar extends AppCompatActivity {

    private Typeface fontBold;
    protected ImageView ivRefresh;
    protected Toolbar toolbar;

    protected void addToolbar(String title, final Context context) {
        fontBold = Typeface.createFromAsset(context.getAssets(), AppConstant.FONT_LARKE_NEUE_BOLD);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.toolBarHeader);
        ivRefresh = (ImageView) toolbar.findViewById(R.id.ivRefresh);
        tvTitle.setText(title);
        tvTitle.setTypeface(fontBold);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

}
