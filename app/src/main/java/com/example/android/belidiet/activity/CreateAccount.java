package com.example.android.belidiet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.firebase.FBGetterSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    EditText jetCAName, jetCAEmail, jetCAPassword, jetCAConPassword, jetCAPhone;
    Button jbtnCreateAccount;
    TextView jtvHaveAccount;
    ProgressDialog progressBar;

    private String sName="", sEmail="", sPass="", sPhone="", sConfirm="";
    public FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference( Constants.USER_ACCOUNT_PATH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_account);

        getSupportActionBar().setTitle("Create Account");
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        jetCAName = (EditText) findViewById( R.id.etCAName );
        jetCAEmail = (EditText) findViewById( R.id.etCAEmail );
        jetCAPassword = (EditText) findViewById( R.id.etCAPass );
        jetCAConPassword = (EditText) findViewById( R.id.etCAConfirm );
        jetCAPhone = (EditText) findViewById( R.id.etCAPhone );

        jetCAName.addTextChangedListener( createAccountTextWatcher );
        jetCAEmail.addTextChangedListener( createAccountTextWatcher );
        jetCAPassword.addTextChangedListener( createAccountTextWatcher );
        jetCAConPassword.addTextChangedListener( createAccountTextWatcher );
        jetCAPhone.addTextChangedListener( createAccountTextWatcher );

        jbtnCreateAccount = (Button) findViewById( R.id.btnCreateAccount );

        jtvHaveAccount = (TextView) findViewById( R.id.tvHave );
        mAuth = FirebaseAuth.getInstance();

        progressBar = new ProgressDialog(this);

        jbtnCreateAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = checkData();
                if (flag) {
                    progressBar.setMessage("Please Wait...");
                    progressBar.show();
                    registerUser();
                }
            }
        } );

        jtvHaveAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        } );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertFB() {
        sEmail = sEmail.substring( 0,1 ).toUpperCase() + sEmail.substring( 1).toLowerCase();
        FBGetterSetter fbGetterSetter = new FBGetterSetter( sName, sPhone, sEmail,"");
        myRef.child(Constants.USER_ACCOUNT_ID + sPhone).setValue( fbGetterSetter );
    }

    private void clearData() {
        jetCAName.setText( "" );
        jetCAEmail.setText( "" );
        jetCAPassword.setText( "" );
        jetCAConPassword.setText( "" );
        jetCAPhone.setText( "" );

    }

    //Whenever there is any change in text in Edittext this will be triggered
    private TextWatcher createAccountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            sPhone = jetCAPhone.getText().toString();
            sConfirm = jetCAConPassword.getText().toString();
            sPass = jetCAPassword.getText().toString();
            sEmail = jetCAEmail.getText().toString();
            sName = jetCAName.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private boolean checkData() {
        try {
            boolean flag;
            if(sName.equals(""))
            {
                jetCAName.setError("Enter name");
                return false;
            }
            flag = checkEmail();
            if (flag) {
                flag = checkPassword();
                if (flag) {
                    flag = checkPhone();
                    if (flag) {
                        return true;
                    }
                }
            }
        }
        catch (Exception e){}
        return false;
    }

    private boolean checkEmail() {
        if(sEmail.equals("") || !sEmail.contains("@") || !sEmail.contains(".com"))
        {
            jetCAEmail.setError("Enter email");
            return false;
        }
        return true;
    }

    private boolean checkPhone() {
        if (sPhone.length() == 10)
            return true;
        else
            jetCAPhone.setError("Enter a valid 10 digit number");
        return false;
    }
    private boolean checkPassword() {
        if(sPass.length()<8)
            jetCAPassword.setError("Minimum 8 charecters");
        else if (!sPass.equals( sConfirm ))
            jetCAConPassword.setError("Password doesn't match");
        else return true;
        return false;
    }
    public void registerUser() {
        //This will register the user
        mAuth.createUserWithEmailAndPassword( sEmail,sPass).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    insertFB();
                    clearData();
                    Toast.makeText(CreateAccount.this, "Account Created", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);

                } else {
                    Toast.makeText( CreateAccount.this, "Error in registering", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

}