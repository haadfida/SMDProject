package com.example.smd.project.CartPage.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smd.project.CartPage.adapter.CheckoutAdapter;
import com.example.smd.project.HomePage.HomePageActivity;
import com.example.smd.project.R;
import com.example.smd.project.controller.DBManipulation;
import com.example.smd.project.controller.SPManipulation;
import com.example.smd.project.controller.historyDBManipulation;
import com.example.smd.project.controller.ShoppingCartItem;
import com.example.smd.project.historyContentProvider;
import com.example.smd.project.model.Food;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.paypal.android.sdk.payments.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.smd.project.controller.historyDB.IMAGENAME;
import static com.example.smd.project.controller.historyDB.ITEMNAME;
import static com.example.smd.project.controller.historyDB.ORDERDATE;
import static com.example.smd.project.controller.historyDB.ORDERSTATUS;
import static com.example.smd.project.controller.historyDB.QUANTITY;
import static com.example.smd.project.controller.historyDB.TABLENAME;
import static com.example.smd.project.controller.historyDB.TOTALORDER;

public class CheckoutFragment extends Fragment {



    // Fragment Component Initialization
    private RecyclerView mRecyclerView;
    private TextView mTextMobile, mTextTotal, mTextEditAddress, mTextEditMobil;
    public static TextView mTextAddress;
    private Button mButtonCheckout, mButtonCancel;


    public CheckoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_checkout);
        mRecyclerView.setAdapter(new CheckoutAdapter(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // initial button
        mButtonCheckout = (Button) view.findViewById(R.id.checkout_pay);
        mButtonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "checkout", Toast.LENGTH_LONG).show();
                payOrder();
            }
        });
        mButtonCancel = (Button) view.findViewById(R.id.checkout_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        // initial text
        mTextMobile = (TextView) view.findViewById(R.id.checkout_mobile);
        mTextMobile.setText(SPManipulation.getInstance(getActivity()).getMobile());
        mTextAddress = (TextView) view.findViewById(R.id.checkout_address);
        mTextAddress.setText(SPManipulation.getInstance(getContext()).getAddress());
        mTextTotal = (TextView) view.findViewById(R.id.checkout_total);
        mTextEditMobil = (TextView) view.findViewById(R.id.checkout_edit_mobile);
        mTextEditMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                Toast.makeText(getContext(), "Edit Number", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_mobile,(ViewGroup) view.findViewById(R.id.dialog_mobile));
                new AlertDialog.Builder(getActivity()).setTitle("Please Input Contact Information").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputMobile = (EditText) dialog.findViewById(R.id.dialog_et_mobile);
                        if (inputMobile.getText().toString().isEmpty()){
                            return;
                        }
                        try{
                            long number = Long.valueOf(inputMobile.getText().toString());
                            SPManipulation.getInstance(getActivity()).setMobile(inputMobile.getText().toString());
                            mTextMobile.setText(inputMobile.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Please Input Correct Phone Number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });
        mTextEditAddress = (TextView) view.findViewById(R.id.checkout_edit_addr);

        mTextEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_location,(ViewGroup) view.findViewById(R.id.dialog_location));
                new AlertDialog.Builder(getActivity()).setTitle("Please Input Delivery Location").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputLocation = (EditText) dialog.findViewById(R.id.dialog_et_location);
                        if (inputLocation.getText().toString().isEmpty()){
                            return;
                        }
                        mTextAddress.setText(inputLocation.getText().toString());
                    }
                })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        mTextTotal.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getPrice()));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

                ShoppingCartItem.getInstance(getContext()).placeOrder(mTextAddress.getText().toString(), mTextMobile.getText().toString());
                ShoppingCartItem.getInstance(getContext()).clear();
                DBManipulation.getInstance(getActivity()).deleteAll();
                HomePageActivity.cartNumber.setText("0");
                getActivity().finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getContext(), PayPalService.class));
    }

    private void payOrder() {
        ShoppingCartItem.getInstance(getContext()).placeOrder(mTextAddress.getText().toString(), mTextMobile.getText().toString());
        ArrayList<Integer> foodsList = ShoppingCartItem.getInstance(getContext()).getFoodInCart();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        for (Integer i : foodsList){
            Food curFood = ShoppingCartItem.getInstance(getContext()).getFoodById(i);

            ContentValues values = new ContentValues();
            values.put(historyContentProvider.ITEMNAME, curFood.getName());
            values.put(historyContentProvider.QUANTITY, ShoppingCartItem.getInstance(getContext()).getFoodNumber(curFood));
            values.put(historyContentProvider.TOTALORDER, curFood.getPrice()*ShoppingCartItem.getInstance(getContext()).getFoodNumber(curFood));
            values.put(historyContentProvider.ORDERDATE, formattedDate);
            values.put(historyContentProvider.ORDERSTATUS, "approved");
            values.put(historyContentProvider.IMAGENAME, curFood.getImageName());
            Uri uri = getActivity().getContentResolver().insert(
                    historyContentProvider.CONTENT_URI, values);

            Toast.makeText(getContext(),
                    uri.toString(), Toast.LENGTH_LONG).show();

        }

        ShoppingCartItem.getInstance(getContext()).clear();
        DBManipulation.getInstance(getActivity()).deleteAll();

        HomePageActivity.cartNumber.setText("0");
        getActivity().finish();



    }


}
