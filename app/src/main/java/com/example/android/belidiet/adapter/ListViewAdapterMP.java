package com.example.android.belidiet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.activity.Meals;
import com.example.android.belidiet.activity.MenuMeal;
import com.example.android.belidiet.R;
import com.example.android.belidiet.model.ModelMP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterMP extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<ModelMP> modelList;
    ArrayList<ModelMP> arrayList;
    Boolean clicked = true;

    public ListViewAdapterMP(Context context, List<ModelMP> modelList) {
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
        Button mPackView, mPackMainDrop, mPackMenu;
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
        final ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate( R.layout.sticker_main_page,  null);

            holder.mPackName =  view.findViewById( R.id.tvMPackName );
            holder.mPackDesc = view.findViewById( R.id.tvMPackDesc );
            holder.mPackMainDesc = view.findViewById( R.id.tvMMainDesc );
            holder.mPackMainDrop = view.findViewById( R.id.btnDrop );
            holder.mPackImg = view.findViewById( R.id.imgMPack );
            holder.mPackView = view.findViewById(R.id.btnView);
            holder.mPackMenu = view.findViewById(R.id.btnPackMenu);

            view.setTag( holder );

        }
        else
        {
            holder = (ViewHolder)view.getTag();

        }
        holder.mPackName.setText( modelList.get(position).getPackName() );
        holder.mPackMainDesc.setText( modelList.get( position ).getPackMainDesc() );
        Picasso.get().load(modelList.get( position ).getPackImg()  ).into( holder.mPackImg );
        holder.mPackMainDrop.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        holder.mPackView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( mContext, Meals.class );
                Constants.PACK_NAME = modelList.get( position ).getPackName();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        } );
        holder.mPackMainDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked) {
                    holder.mPackDesc.setText(modelList.get(position).getPackDesc());
                    clicked = false;
                    holder.mPackMainDrop.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                else {
                    holder.mPackDesc.setText("");
                    clicked = true;
                    holder.mPackMainDrop.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }

            }
        });

        holder.mPackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MenuMeal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}