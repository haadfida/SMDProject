package com.example.smd.project.CartPage;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.smd.project.CartPage.fragment.CartFragment;
import com.example.smd.project.R;

public class CartActivity extends AppCompatActivity {
    public static final String ACTION1 = "com.example.smd.project.mydynamicevent";
    public static final String ACTION2 = "com.example.smd.project.mydyanicevent";

    String TAG = "MainActivity";

    Receiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if(findViewById(R.id.cart_container) != null) {
            CartFragment cartFragment = new CartFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.cart_container, cartFragment).commit();
        }
        mReceiver = new Receiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register mReceiver to receive messages.  This is using the local broadcast, instead of a global.
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ACTION2));
        //the one below registers a global receiver.
        //registerReceiver(mReceiver, new IntentFilter(ACTION2));
        //Log.v(TAG, "receiver should be registered");
    }

    @Override
    protected void onPause() {  //or onDestory()
        // Unregister since the activity is not visible.  This is using the local broadcast, instead of a global.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        //the one below unregisters a global receiver.
        //unregisterReceiver(mReceiver);
        Log.v(TAG, "receiver should be unregistered");
        super.onPause();

    }
}
