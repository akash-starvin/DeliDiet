package com.example.android.belidiet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddMeal extends AppCompatActivity {

    Button jbtnAdd;
    ImageView jimgAdd;
    EditText jetName, jetCost,jetDec;
    String name, cost, des, url,pack;
    Spinner jspin;
    private StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference( Constants.MEAL_LIST_PATH);

    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal_activity_);

        jbtnAdd = findViewById(R.id.btnAD);
        jetName = findViewById(R.id.etADName);
        jetCost = findViewById(R.id.etADCost);
        jetDec = findViewById(R.id.etADDes);
        jimgAdd = findViewById(R.id.imgAM);
        jspin = findViewById(R.id.spinAD);
        setProductSpinner();
        storageReference = FirebaseStorage.getInstance().getReference("Meal List");

        jimgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will let the user to select image from gallery
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Select Picture" ), 1 );
            }
        });
        jbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = getdata();
                if(flag)
                {
                    uploadImage();
                    //Clearing the textview and imageview
                    jimgAdd.setImageBitmap(null);
                    jspin.setSelection(0);
                    jetName.setText("");
                    jetDec.setText("");
                    jetCost.setText("");
                }
            }
        });
    }
    private void uploadImage() {
        final StorageReference ref = storageReference.child(name);
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri downloadUrl = uri;
                        //Image will be uploaded in Firebase STORAGE and we are saving the DOWNLOAD URL in variable
                        url = downloadUrl.toString();

                        //Inserting data into database
                        FBAddMealsGetSet fbAddMealsGetSet = new FBAddMealsGetSet(name,des,cost,url,pack);
                        myRef.push().setValue(fbAddMealsGetSet);
                        Toast.makeText(getApplicationContext(), "Meal Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "Error while uploading image" + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //to get image from phone storage and setting it into image view
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //setting image into image view
                jimgAdd.setImageBitmap(bitmap);
                jbtnAdd.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean getdata() {
        //Gets data from Edittext and spinnner
        boolean flag = true;
        try {
            name = jetName.getText().toString();
            cost = jetCost.getText().toString();
            des = jetDec.getText().toString();
            pack = jspin.getSelectedItem().toString();
            if (name.equals("")) {
                jetName.setError("Enter name");
                flag = false;
            } else if (cost.equals("")) {
                jetCost.setError("Enter name");
                flag = false;
            } else if (des.equals("")) {
                jetDec.setError("Enter description");
                flag = false;
            }else if (jspin.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Select a pack", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        }catch (Exception e){}

        return flag;
    }
    private void setProductSpinner() {
        //Initialising array items to spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>( getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray( R.array.packs ) );
        jspin.setAdapter( myAdapter );
    }

}
