package com.example.android.belidiet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.activity.MenuMeal;
import com.example.android.belidiet.R;
import com.example.android.belidiet.activity.Select;
import com.example.android.belidiet.model.ModelMP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterMeals extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<ModelMP> modelList;
    ArrayList<ModelMP> arrayList;


    public ListViewAdapterMeals(Context context, List<ModelMP> modelList) {
        this.mContext = context;
        this.modelList = modelList;
        inflater = LayoutInflater.from( mContext );
        this.arrayList = new ArrayList<ModelMP>(  );
        this.arrayList.addAll( modelList );

    }
    public class ViewHolder
    {
        TextView mPackName,mPackDesc, mPackMainDesc;
        ImageView mPackImg;
        Button mPackBtnSelect, mPackBtnMenu;
    }
    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int i) {
        return modelList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ListViewAdapterMeals.ViewHolder holder;
        if(view == null)
        {
            holder = new ListViewAdapterMeals.ViewHolder();
            view = inflater.inflate( R.layout.sticker_meals,  null);

            holder.mPackName =  view.findViewById( R.id.tvMealName );
            holder.mPackDesc = view.findViewById( R.id.tvMealDesc );
            holder.mPackMainDesc = view.findViewById( R.id.tvMealPrice );
            holder.mPackImg = view.findViewById( R.id.imgMMeal );
            holder.mPackBtnSelect = view.findViewById(R.id.btnSelect);
            holder.mPackBtnMenu = view.findViewById(R.id.btnMenu);
            view.setTag( holder );

        }
        else
        {
            holder = (ListViewAdapterMeals.ViewHolder)view.getTag();

        }
        try {
                holder.mPackName.setText(modelList.get(position).getPackName());
                holder.mPackDesc.setText(modelList.get(position).getPackDesc());
                holder.mPackMainDesc.setText(modelList.get(position).getPackMainDesc());
                Picasso.get().load(modelList.get(position).getPackImg()).into(holder.mPackImg);

        }
        catch (Exception e){}

        holder.mPackBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Select.class);
                Constants.MEAL_NAME = modelList.get(position).getPackName();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        holder.mPackBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MenuMeal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Constants.MENU_DATA = modelList.get(position).getPackName();

                mContext.startActivity(intent);
            }
        });


        return view;
    }
}
