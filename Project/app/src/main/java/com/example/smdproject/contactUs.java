package com.example.smdproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class contactUs extends AppCompatActivity {

    ImageView image_call,image_mail,image_facebook;;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_contact_us);

        image_call = (ImageView)findViewById(R.id.image_call);
        image_mail = (ImageView)findViewById(R.id.image_mail);

        image_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:03223888884"));
                if (ActivityCompat.checkSelfPermission(contactUs.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                startActivity(intent);

            }
        });

        image_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"haad99@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{""});

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent,"Choose email..."));
            }
        });


    }


}

