package com.example.android.belidiet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.belidiet.R;

public class MiniDoc extends AppCompatActivity {

    Button jbtnSend;
    TextView jtvDoc, jtvUser;
    EditText jetMsg;
    String sUserMsg, sDocMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_doc_activity);

        jtvDoc = findViewById(R.id.tvDoc);
        jtvUser = findViewById(R.id.tvUser);

        jetMsg = findViewById(R.id.etMessage);
        jbtnSend = findViewById(R.id.btnSend);

        jbtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = false;
                flag = getMsg();
                if (flag)
                {
                    jetMsg.setText("");
                    checkMsg();
                }
            }
        });
        findViewById(R.id.btnQ1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserMsg = "  How to loose weight ?  ";
                checkMsg();
            }
        });
        findViewById(R.id.btnQ2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserMsg = "  How to gain weight ?  ";
                checkMsg();
            }
        });
        findViewById(R.id.btnQ3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserMsg = "  How many glasses of water to drink daily ?  ";
                checkMsg();
            }
        });
        findViewById(R.id.btnQ4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserMsg = "  How much sleep is required ?  ";
                checkMsg();
            }
        });
        findViewById(R.id.btnQ5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserMsg = "  Gym / Sports ?  ";
                checkMsg();
            }
        });
    }

    private void checkMsg() {
        jtvUser.setText(sUserMsg);
        sUserMsg = sUserMsg.toLowerCase();
        if (sUserMsg.contains("hi")||sUserMsg.contains("hey"))
            sDocMsg = "Hi";
        else if (sUserMsg.contains("how are you"))
            sDocMsg = "Happy and ready to help you";
        else if(sUserMsg.contains("gain"))
            sDocMsg = "1.Eat more often and use bigger plates.\n2.Lift Heavy Weights and Improve Your Strength.\n3.Follow our WEIGHT TRAINING AND WORKOUT MEAL PLAN ";
        else if(sUserMsg.contains("loose") || sUserMsg.contains("weight loss"))
            sDocMsg = "1. Eat high-fibre foods.\n2.Get more active.\n3.Follow our WEIGHT LOSS MEAL PLAN or\n4.KETO MEAL PLAN ";
        else if(sUserMsg.contains("water"))
            sDocMsg = "1. 8-10 glasses.\n2.Whenever you’re thirsty.\n3.During high heat and exercise, make sure to drink enough to compensate for the lost fluids.";
        else if(sUserMsg.contains("sleep"))
            sDocMsg = "7-8 Hours minimum.";
        else if(sUserMsg.contains("gym") || sUserMsg.contains("sports"))
            sDocMsg = "Sweat more.";
        else
            sUserMsg = "We'll get back to you soon...";

        jtvDoc.setText(sDocMsg);
    }

    private boolean getMsg() {
        try {
            sUserMsg = jetMsg.getText().toString();
            if (sUserMsg.length() > 0) {
                return true;
            }
            else
            {
                Toast.makeText(this, "Type something before sending", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e){}
        return false;
    }
}
