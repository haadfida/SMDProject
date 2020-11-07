package com.example.smd.project.CartPage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.smd.project.R;
import com.example.smd.project.controller.ShoppingCartItem;
import com.example.smd.project.model.Food;


public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutHolder>{

    private Context mContext;

    public CheckoutAdapter(Context context) {
        mContext = context;
    }


    @Override
    public CheckoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_checkout, parent, false);
        CheckoutHolder checkoutHolder = new CheckoutHolder(view);

        return checkoutHolder;
    }

    @Override
    public void onBindViewHolder(CheckoutHolder holder, int position) {


        int id = ShoppingCartItem.getInstance(mContext).getFoodInCart().get(position);
        final Food curFood = ShoppingCartItem.getInstance(mContext).getFoodById(id);
        final int curFoodNumber = ShoppingCartItem.getInstance(mContext).getFoodNumber(curFood);
        holder.mTextName.setText(curFood.getName());
        holder.mTextPrice.setText(String.valueOf(curFoodNumber * curFood.getPrice()));
        holder.mTextQuantity.setText(String.valueOf(curFoodNumber));
    }

    @Override
    public int getItemCount() {
        return ShoppingCartItem.getInstance(mContext).getFoodTypeSize();
    }
}
class CheckoutHolder extends RecyclerView.ViewHolder {
    TextView mTextName, mTextQuantity, mTextPrice;

    public CheckoutHolder(View itemView) {
        super(itemView);
        mTextName = (TextView) itemView.findViewById(R.id.checkout_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.checkout_price);
        mTextQuantity = (TextView) itemView.findViewById(R.id.checkout_quantity);
    }
}