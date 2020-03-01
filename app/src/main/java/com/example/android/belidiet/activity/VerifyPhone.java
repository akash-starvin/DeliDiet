package com.example.android.belidiet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    private String mVerificationId;
    private FirebaseAuth mAuth;
    EditText jetCode;
    Button jbtnVerify;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_activity);

        jetCode = findViewById(R.id.etVCode);
        jbtnVerify = findViewById(R.id.btnVerify);
        mAuth = FirebaseAuth.getInstance();
        sendVerificationCode(Constants.PHONE);

        jbtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (jetCode.getText().length() == 6)
                        verifyVerificationCode(code);
                    else
                        jetCode.setError("Enter valid OTP");
                }
                catch (Exception e)
                {}
            }
        });
        TextView jtv = findViewById(R.id.textView15);
        jtv.setText("OTP has been sent to your number +91 "+Constants.PHONE);
        verifyVerificationCode(code);
    }
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                jetCode.setText(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String otp) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

            if (jetCode.getText().toString().equals(code)) {
                Intent intent = new Intent(getApplicationContext(),ConfirmAddress.class);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){}
    }

}
