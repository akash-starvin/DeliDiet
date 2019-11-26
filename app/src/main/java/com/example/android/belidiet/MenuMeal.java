package com.example.android.belidiet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MenuMeal extends AppCompatActivity {

    public int[] bf={R.drawable.breakfast2,R.drawable.breakfast3,R.drawable.breakfast1};
    int[] lunch = {R.drawable.lunch1,R.drawable.lunch2,R.drawable.lunch3};
    int [] dinner = {R.drawable.dinner_1,R.drawable.dinner_2,R.drawable.dinner_3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_meal_activity);


        ImageView jiv = findViewById(R.id.menuImg);
        String time = Constants.MENU_DATA;

        if (time.contains("Breakfast"))
            jiv.setImageResource(bf[getRandomIntegerBetweenRange(0, 2)]);
        else if (time.contains("Lunch"))
            jiv.setImageResource(lunch[getRandomIntegerBetweenRange(0, 2)]);
        else if (time.contains("Dinner"))
            jiv.setImageResource(dinner[getRandomIntegerBetweenRange(0, 2)]);
        else
            jiv.setImageResource(R.drawable.snack);
    }
    public static int getRandomIntegerBetweenRange(int min, int max){
        return  (int)(Math.random()*((max-min)+1))+min;
    }

}
