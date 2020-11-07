package com.example.smd.project.StartingPage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smd.project.HomePage.HomePageActivity;
import com.example.smd.project.R;
import com.example.smd.project.controller.SPManipulation;

import com.google.android.gms.common.SignInButton;


public class SignInFragment extends Fragment{

    // Declare all views name;
    View view;
//    Button btn_signIn;
    EditText mobile, password;
    TextView toSignUp;

    SPManipulation mSPManipulation;
    private SignInButton btnSignIn;
    Button btn_signIn, mFbButtonSignIn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }




    private void init(){
        // Init all views
        mobile = (EditText) view.findViewById(R.id.sign_in_username);
        password = (EditText) view.findViewById(R.id.sign_in_pwd);


        toSignUp = (TextView) view.findViewById(R.id.to_sign_up);
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Tap Switch

            }
        });
        btn_signIn = (Button) view.findViewById(R.id.sign_in_btn);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mb= SPManipulation.getInstance(getActivity()).getMobile();
                String pw=SPManipulation.getInstance(getActivity()).getPwd();

                String mobtext= mobile.getText().toString();
                String pwtext= mobile.getText().toString();

                if(mb.equals(mobtext) && pw.equals(pwtext)){
                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


}
