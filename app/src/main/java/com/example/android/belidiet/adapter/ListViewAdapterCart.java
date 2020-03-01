package com.example.android.belidiet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.model.ModelCart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

//this class is used to fill the List with items
public class ListViewAdapterCart extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<ModelCart> modelList;
    ArrayList<ModelCart> arrayList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(Constants.CART_PATH);

    public ListViewAdapterCart(Context context, List<ModelCart> modelList) {
        this.mContext = context;
        this.modelList = modelList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ModelCart>();
        this.arrayList.addAll(modelList);

    }
    public class ViewHolder
    {
        TextView mPackName,mPackQty, mPackPrice;
        Button mPackDelete;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ListViewAdapterCart.ViewHolder  holder;
        if (view == null) {
            holder = new ListViewAdapterCart.ViewHolder();

            view = inflater.inflate(R.layout.sticker_cart, null);

            holder.mPackName = view.findViewById(R.id.tvCRName);
            holder.mPackQty = view.findViewById(R.id.tvCRQty);
            holder.mPackPrice = view.findViewById(R.id.tvCRPrice);
            holder.mPackDelete = view.findViewById(R.id.btnDelete);
            view.setTag(holder);

        } else {
            holder = (ListViewAdapterCart.ViewHolder) view.getTag();
        }
        holder.mPackName.setText( modelList.get(i).getPackName() );
        holder.mPackQty.setText( modelList.get(i).getPackQty() );
        holder.mPackPrice.setText( modelList.get(i).getPackPrice() );

        holder.mPackDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ref.child(modelList.get(i).getPackId()).removeValue();
            }
        });
        return view;
    }
}
