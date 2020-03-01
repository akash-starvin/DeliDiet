package com.example.android.belidiet.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView jtvLCreate;
    EditText jetLEmail, jetLPassword;
    String sEmail="", sPassword="" , s;
    Button jbtnLLogin;
    ProgressDialog progressDialog2;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login_activity );

        jtvLCreate = (TextView) findViewById( R.id.tvLCreate );

        jetLEmail = (EditText) findViewById( R.id.etLEmail );
        jetLPassword = (EditText) findViewById( R.id.etLPassword );
        jetLEmail.addTextChangedListener( loginTextWatcher );
        jetLPassword.addTextChangedListener( loginTextWatcher );

        jbtnLLogin = (Button) findViewById( R.id.btnLLogin );

        progressDialog2 = new ProgressDialog( this );
        mAuth = FirebaseAuth.getInstance();

        jtvLCreate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), CreateAccount.class );
                startActivity( intent );
            }
        } );
        jbtnLLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (sEmail.equals("")) {
                        jetLEmail.setError("Enter a valid email");
                    } else if (sPassword.equals("")) {
                        jetLPassword.setError("Enter password");
                    } else {
                        sEmail = sEmail.substring(0, 1).toUpperCase() + sEmail.substring(1).toLowerCase();
                        progressDialog2.setMessage("Loging in, Please Wait...");
                        progressDialog2.show();
                        loginUser();
                    }
                }
                catch (Exception e){}
            }});
    }
    private void loginUser() {
        try {

            //logs the users in
            mAuth.signInWithEmailAndPassword(sEmail, sPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser() != null) {
                                    Constants.EMAIL = sEmail;

                                    //This will take you to admin activity
                                    if (sEmail.equals("Admin@123.com") && sPassword.equals("Admin@1234")) {
                                        Intent intent = new Intent(getApplicationContext(), AddMeal.class);
                                        progressDialog2.dismiss();
                                        startActivity(intent);
                                    }
                                    else {
                                        // it'll take you to main page
                                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                                        progressDialog2.dismiss();
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                progressDialog2.dismiss();
                            }
                        }
                    });
        }catch (Exception e){}
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            sEmail = jetLEmail.getText().toString();
            sPassword = jetLPassword.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
