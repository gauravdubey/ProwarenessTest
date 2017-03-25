package com.android.prowarenesstestapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.prowarenesstestapp.R;
import com.android.prowarenesstestapp.bean.ContactResultModel;
import com.android.prowarenesstestapp.tables.ContactTableUtils;
import com.android.prowarenesstestapp.utils.AppConstant;
import com.android.prowarenesstestapp.utils.FontHelper;
import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListRowHolder> {

    private Context mContext;
    private ArrayList<ContactResultModel> aContactListModel;
    private Typeface fontLarkeNeueBold;

    public ContactListAdapter(Context context, ArrayList<ContactResultModel> listContact) {
        this.aContactListModel = listContact;
        this.mContext = context;
        fontLarkeNeueBold = Typeface.createFromAsset(context.getAssets(), AppConstant.FONT_LARKE_NEUE_BOLD);
    }

    @Override
    public ContactListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_inflater, viewGroup, false);
        ContactListRowHolder rowHolder = new ContactListRowHolder(v);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(ContactListRowHolder rowHolder, int i) {
        FontHelper.applyFont(mContext, rowHolder.layoutContact, AppConstant.FONT_LARKE_NEUE);
        rowHolder.tvContactName.setText(aContactListModel.get(i).getName());
        rowHolder.tvContactId.setText(aContactListModel.get(i).getUid());
    }

    @Override
    public int getItemCount() {
        return (null != aContactListModel ? aContactListModel.size() : 0);
    }


    public class ContactListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView tvContactName, tvContactId;
        protected RelativeLayout layoutContact;
        protected ImageView ivDelete;

        public ContactListRowHolder(View view) {
            super(view);
            layoutContact = (RelativeLayout) view.findViewById(R.id.layoutContact);
            tvContactName = (TextView) view.findViewById(R.id.tvContactName);
            tvContactId = (TextView) view.findViewById(R.id.tvContactId);
            ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
            tvContactName.setTypeface(fontLarkeNeueBold);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivDelete:
                    ContactTableUtils.updateContact(mContext,aContactListModel.get(getAdapterPosition()).getUid());
                    aContactListModel.remove(getAdapterPosition());
                    notifyDataSetChanged();

                    break;
            }
        }
    }
}
