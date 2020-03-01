package com.example.android.belidiet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.firebase.FBGetterSetter;
import com.example.android.belidiet.firebase.FBProfileGetSet;
import com.google.firebase.database.*;

public class Profile extends AppCompatActivity {

    int count=0;
    Button jbtnPUpdate;
    RadioButton jrdoPMale, jrdoPFemale;
    CheckBox jcbDiabetes,jcbThyroid,jcbPCOS,jcbCholesterol,jcbPhysicalInjury, jcbHypertension;
    RadioGroup jrdoGroup;
    EditText jetPName, jetPPhone, jetPAge, jetPWeight, jetPHeight, jetPCity;
    String sName, sGender = " ", sPhone, sAge, sWeight, sHeight, sMedCon = "", sCity;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myProRef = database.getReference(Constants.USER_PROFILE_PATH);
    DatabaseReference myAccRef = database.getReference(Constants.USER_ACCOUNT_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        jrdoGroup = findViewById(R.id.rdoGroup);
        jrdoPMale = findViewById(R.id.rdoMale);
        jrdoPFemale = findViewById(R.id.rdoFemale);


        jcbDiabetes = findViewById(R.id.cbDiabetes);
        jcbThyroid = findViewById(R.id.cbThyroid);
        jcbPCOS = findViewById(R.id.cbPCOS);
        jcbCholesterol = findViewById(R.id.cbCholesterol);
        jcbPhysicalInjury = findViewById(R.id.cbPhysicalInjury);
        jcbHypertension = findViewById(R.id.cbHypertension);

        jbtnPUpdate = findViewById(R.id.btnUpdate);

        jetPName = findViewById(R.id.etPName);
        jetPPhone = findViewById(R.id.etPPhone);
        jetPAge = findViewById(R.id.etPAge);
        jetPWeight = findViewById(R.id.etWeight);
        jetPHeight = findViewById(R.id.etPHeight);
        jetPCity = findViewById(R.id.jetPCity);


        jbtnPUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = getdata();
                if (flag) {
                    FBProfileGetSet fbProfileGetSet = new FBProfileGetSet(sGender, sAge, sWeight, sHeight, sMedCon, sCity, Constants.EMAIL);
                    myProRef.child(Constants.USER_PROFILE_ID + Constants.PHONE).setValue(fbProfileGetSet);

                    FBGetterSetter fbGetterSetter = new FBGetterSetter(sName, sPhone, Constants.EMAIL, Constants.URL);
                    myAccRef.child(Constants.USER_ACCOUNT_ID + Constants.PHONE).setValue(fbGetterSetter);

                    Toast.makeText(Profile.this, "Data updated", Toast.LENGTH_SHORT).show();
                    Constants.PHONE = sPhone;
                }
            }
        });
        jrdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdoMale:
                        sGender = "Male";
                        break;
                    case R.id.rdoFemale:
                        sGender = "Female";
                        break;
                }
            }
        });


        Query accQuery = FirebaseDatabase.getInstance().getReference(Constants.USER_ACCOUNT_PATH).orderByChild("email").equalTo(Constants.EMAIL);
        accQuery.addValueEventListener(accVLE);

        Query proQuery = FirebaseDatabase.getInstance().getReference(Constants.USER_PROFILE_PATH).orderByChild("email").equalTo(Constants.EMAIL);
        proQuery.addValueEventListener(proVLE);

    }
    ValueEventListener proVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sGender = "" + postSnapshot.child("gender").getValue();
                sAge = "" + postSnapshot.child("age").getValue();
                sWeight = "" + postSnapshot.child("weight").getValue();
                sHeight = "" + postSnapshot.child("height").getValue();
                sCity = "" + postSnapshot.child("city").getValue();
                sMedCon = "" + postSnapshot.child("medCon").getValue();
            }
            jetPAge.setText(sAge);
            jetPWeight.setText(sWeight);
            jetPHeight.setText(sHeight);
            jetPCity.setText(sCity);
            if (sGender.equals("Male"))
                jrdoPMale.setChecked(true);
            else
                jrdoPFemale.setChecked(true);
            checkCBMedCon(); // this function will check the CHECKBOXES of medical conditions


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void checkCBMedCon() {
        if(sMedCon.contains("Daibetes"))
            jcbDiabetes.setChecked(true);
        if(sMedCon.contains("Thyroid"))
            jcbThyroid.setChecked(true);
        if(sMedCon.contains("PCOS"))
            jcbPCOS.setChecked(true);
        if(sMedCon.contains("Cholesterol"))
            jcbCholesterol.setChecked(true);
        if(sMedCon.contains("PhysicalInjury"))
            jcbPhysicalInjury.setChecked(true);
        if(sMedCon.contains("Hypertension"))
            jcbHypertension.setChecked(true);
    }

    ValueEventListener accVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sName = "" + postSnapshot.child("accName").getValue();
                sPhone = "" + postSnapshot.child("phone").getValue();
            }
            jetPName.setText(sName);
            jetPPhone.setText(sPhone);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private boolean getdata() {
        boolean flag = true;
        try {
            sName = jetPName.getText().toString();
            sPhone = jetPPhone.getText().toString();
            sAge = jetPAge.getText().toString();
            sWeight = jetPWeight.getText().toString();
            sHeight = jetPHeight.getText().toString();
            sCity = jetPCity.getText().toString();
            if (sName.isEmpty()) {
                jetPName.setError("Please enter name");
                flag = false;
            } else {
                if (sPhone.length() != 10) {
                    jetPPhone.setError("Please enter phone number");
                    flag = false;
                } else {
                    if (sAge.length() == 0 || Integer.parseInt(sAge) >= 130) {
                        jetPAge.setError("Please enter age");
                        flag = false;
                    } else {
                        if (sWeight.isEmpty() || Integer.parseInt(sWeight) >= 500) {
                            jetPWeight.setError("Please enter weight");
                            flag = false;
                        } else {
                            if (sHeight.isEmpty() || sHeight.length() > 4) {
                                jetPHeight.setError("Please enter height");
                                flag = false;
                            } else {
                                if (sCity.isEmpty()) {
                                    jetPCity.setError("Please enter city");
                                    flag = false;
                                } else {
                                    if (sGender.endsWith(" ")) {
                                        Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
                                        flag = false;
                                    }
                                    else
                                    {
                                        flag = getMedCon();
                                        if(flag)
                                        {
                                            flag=true;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {

        }
        return flag;
    }

    private boolean getMedCon() {
        boolean flag;
        count=0;
        if(jcbDiabetes.isChecked())
            count++;
        if(jcbThyroid.isChecked())
            count++;
        if(jcbPCOS.isChecked())
            count++;
        if(jcbCholesterol.isChecked())
            count++;
        if(jcbPhysicalInjury.isChecked())
            count++;
        if(jcbHypertension.isChecked())
            count++;

        if(count>3) {
            Toast.makeText(this, "Maximum medical condition is 3", Toast.LENGTH_SHORT).show();
            flag = false;
        }else
        {
            sMedCon = "";
            if(jcbDiabetes.isChecked())
                sMedCon += "Diabetes , ";
            if(jcbThyroid.isChecked())
                sMedCon += "Thyroid , ";
            if(jcbPCOS.isChecked())
                sMedCon += "PCOS , ";
            if(jcbCholesterol.isChecked())
                sMedCon += "Cholesterol , ";
            if(jcbPhysicalInjury.isChecked())
                sMedCon += "PhysicalInjury , ";
            if(jcbHypertension.isChecked())
                sMedCon += "Hypertension , ";
            flag = true;
        }
        return flag;

    }
    public void onCheckboxClicked(View view) {}
}
