package com.example.smd.project.HomePage.adapter;

import android.content.Context;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.smd.project.R;
import com.example.smd.project.model.Order;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder>{
    private Context mContext;
    private ArrayList<Order> orders;

    public HistoryAdapter(Context context, ArrayList<Order> orders) {
        mContext = context;
        this.orders = orders;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_history, parent, false);
        HistoryHolder historyHolder = new HistoryHolder(v);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, final int position) {
        holder.mTextId.setText(String.valueOf(orders.get(position).getId()));
        holder.mTextName.setText(orders.get(position).getName());
        holder.mTextQuantity.setText(String.valueOf(orders.get(position).getQuantity()));
        holder.mTextTotal.setText(String.valueOf(orders.get(position).getTotal()));
        holder.mTextDate.setText(String.valueOf(orders.get(position).getDate()));
        holder.mTextAddress.setText(orders.get(position).getAddress());

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(orders.get(position).getId()));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, orders.get(position).getName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }

    public void notifyData(ArrayList<Order> orders) {
//        Log.d("notifyData ", foods.size() + "");
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}

class HistoryHolder extends RecyclerView.ViewHolder {
    TextView mTextId, mTextName, mTextQuantity, mTextTotal, mTextDate, mTextAddress, mTextStatus;
    TextView btn_track, btn_update, btn_cancel;
    public HistoryHolder(View itemView) {
        super(itemView);
        mTextId = (TextView) itemView.findViewById(R.id.history_id);
        mTextName = (TextView) itemView.findViewById(R.id.history_name);
        mTextQuantity = (TextView) itemView.findViewById(R.id.history_quantity);
        mTextTotal = (TextView) itemView.findViewById(R.id.history_total);
        mTextDate = (TextView) itemView.findViewById(R.id.history_date);
        mTextAddress = (TextView) itemView.findViewById(R.id.history_address);



    }
}
