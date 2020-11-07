package com.example.smd.project.CartPage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class Receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "New intent received.", Toast.LENGTH_SHORT).show();

         if (intent.getAction().equals(CartActivity.ACTION2)) {
            Toast.makeText(context, "Please proceed to checkout.", Toast.LENGTH_SHORT).show();
        }
         else if(intent.getAction().equals(CartActivity.ACTION1)) {
             Toast.makeText(context, "Internet connectivity inst available.", Toast.LENGTH_SHORT).show();
         }
    }
}
