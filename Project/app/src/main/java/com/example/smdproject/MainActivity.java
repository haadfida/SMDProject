package com.example.smdproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        button = (ImageView) findViewById(R.id.menubutton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openMenu();
            }
        });*/
    }

    public void openOrder(){
        Intent intent = new Intent(this, order.class);
        startActivity(intent);
    }

    public void openMyProfile(){
        Intent intent = new Intent(this, myprofile.class);
        startActivity(intent);
    }

    public void openMyAddress(){
        Intent intent = new Intent(this, myAddress.class);
        startActivity(intent);
    }

    public void openPaymentMethods(){
        Intent intent = new Intent(this, paymentmethods.class);
        startActivity(intent);
    }

    public void openContactUs(){
        Intent intent = new Intent(this, contactUs.class);
        startActivity(intent);
    }

    public void openRegisterComplaint(){
        Intent intent = new Intent(this, registerComplaint.class);
        startActivity(intent);
    }

    public void openLogout(){
        Intent intent = new Intent(this, logout.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater    inflater= getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.item1:
                openOrder();
                return true;

            case R.id.item2:
                openMyProfile();
                return true;

            case R.id.item3:
                openMyAddress();
                return true;

            case R.id.item4:
                openPaymentMethods();
                return true;

            case R.id.item5:
                openContactUs();
                return true;

            case R.id.item6:
                openRegisterComplaint();
                return true;

            case R.id.item7:
                openLogout();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
