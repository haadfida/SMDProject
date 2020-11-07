package com.example.smd.project.HomePage.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smd.project.HomePage.adapter.HistoryAdapter;
import com.example.smd.project.R;
import com.example.smd.project.historyContentProvider;
import com.example.smd.project.model.Order;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;

    private ArrayList<Order> orders = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("History");
    }

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        if (orders.size()==0){
            objLoad();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_history);
        adapter = new HistoryAdapter(getContext(), orders);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    private void objLoad(){

        String URL = "com.smd.project.historyContentProvider";

        Uri students = Uri.parse(URL);
        Cursor cursor = getActivity().getContentResolver().query(historyContentProvider.CONTENT_URI, null, null, null, historyContentProvider.ITEMID);

        if (cursor.moveToFirst()) {
            do {

                Order curOrder = new Order();
                curOrder.setId(cursor.getInt(cursor.getColumnIndex(historyContentProvider.ITEMID)));
                curOrder.setName(cursor.getString(cursor.getColumnIndex(historyContentProvider.ITEMNAME)));
                //curOrder.setImageUrl(cursor.getString(cursor.getColumnIndex(IMAGENAME)));
                curOrder.setTotal(cursor.getDouble(cursor.getColumnIndex(historyContentProvider.TOTALORDER)));
                curOrder.setQuantity(cursor.getInt(cursor.getColumnIndex(historyContentProvider.QUANTITY)));
                curOrder.setDate(cursor.getString(cursor.getColumnIndex(historyContentProvider.ORDERDATE)));
                curOrder.setStatus(cursor.getString(cursor.getColumnIndex(historyContentProvider.ORDERSTATUS)));

                orders.add(curOrder);

            } while (cursor.moveToNext());
        }
        cursor.close();
        //adapter.notifyData(orders);
    }


}
