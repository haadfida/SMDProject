package com.example.smd.project.StartingPage.fragment;


import android.os.Bundle;
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


import com.example.smd.project.R;
import com.example.smd.project.StartingPage.SignInActivity;
import com.example.smd.project.controller.SPManipulation;


public class SignUpFragment extends Fragment {
    private EditText username, mobile, email, password, repassword, address;
    private Button btn_signUp;
    private TextView toSignIn;

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();
        return view;
    }

    private void init(){
        username = (EditText) view.findViewById(R.id.sign_up_username);
        mobile = (EditText) view.findViewById(R.id.sign_up_mobile);
        email = (EditText) view.findViewById(R.id.sign_up_email);
        password = (EditText) view.findViewById(R.id.sign_up_pwd);
        repassword = (EditText) view.findViewById(R.id.sign_up_pwd2);
        address = (EditText) view.findViewById(R.id.sign_up_address);

        toSignIn = (TextView) view.findViewById(R.id.to_sign_in);
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_signUp = (Button) view.findViewById(R.id.sign_up_btn);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!repassword.getText().toString().equals(password.getText().toString())){
                    Toast.makeText(getActivity(), "Password and confirm password not same. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                SPManipulation.getInstance(getActivity()).setName(username.getText().toString());
                SPManipulation.getInstance(getActivity()).setMobile(mobile.getText().toString());
                SPManipulation.getInstance(getActivity()).setEmail(email.getText().toString());
                SPManipulation.getInstance(getActivity()).setPwd(password.getText().toString());
                SPManipulation.getInstance(getActivity()).setAddress(address.getText().toString());
                registrationMethod();

            }
        });

    }



    private void registrationMethod() {
        getActivity().recreate();

    }
}
