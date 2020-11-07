package com.example.smd.project.StartingPage;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.smd.project.HomePage.HomePageActivity;
import com.example.smd.project.R;
import com.example.smd.project.controller.SPManipulation;
import com.example.smd.project.controller.ShoppingCartItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private View mContentView;
    private FirebaseAnalytics mFBanalytics;
    private View mControlsView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFBanalytics =FirebaseAnalytics.getInstance(this);

        adView = findViewById(R.id.adView);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        checkConnection();
        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPManipulation.getInstance(MainActivity.this).getMobile() == null)
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                else {
                    startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                }
            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShoppingCartItem.getInstance(MainActivity.this).addToDb(MainActivity.this);
        ShoppingCartItem.getInstance(MainActivity.this).setNull();
    }

    public void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork =manager.getActiveNetworkInfo();

        if(activeNetwork!=null){

            if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(this, "Data Network is functioning.", Toast.LENGTH_SHORT).show();
            }

            else if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this, "Wifi is functioning.", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

}
