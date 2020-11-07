package com.example.smd.project.HomePage.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smd.project.CartPage.CartActivity;
import com.example.smd.project.R;
import com.example.smd.project.controller.ShoppingCartItem;
import com.example.smd.project.model.Food;


public class FoodDetailFragment extends Fragment {
    TextView mTextId, mTextRecipe, mTextPrice;
    Button mButtonAdd;
    ImageView mImageView;
    Food food;
    final private String TAG = "FoodDetail";
    CollapsingToolbarLayout collapsingToolbarLayout;


    View view;

    public FoodDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        initView();
        initFoodInfo();

        setButtonListener();


        return view;
    }

    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.food_detail_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Food Name");
        mTextId = (TextView) view.findViewById(R.id.food_detail_id);
        mTextRecipe = (TextView) view.findViewById(R.id.food_detail_recipe);
        mTextPrice = (TextView) view.findViewById(R.id.food_detail_price);
        mButtonAdd = (Button) view.findViewById(R.id.food_detail_add);
        mImageView = (ImageView) view.findViewById(R.id.food_detail_image);
    }

    private void initFoodInfo(){
        food = new Food();
        food.setName(getArguments().getString("foodName"));
        food.setId(getArguments().getInt("foodId"));
        food.setPrice(getArguments().getDouble("foodPrice"));
        food.setRecepiee(getArguments().getString("foodRec"));
        food.setImageUrl(getArguments().getString("foodImage"));
        mTextId.setText(String.valueOf(food.getId()));
        mTextRecipe.setText(food.getRecepiee());
        mTextPrice.setText(String.valueOf(food.getPrice()));
        collapsingToolbarLayout.setTitle(food.getName());

        String thumb= food.getImageName();

        int resID = getResources().getIdentifier(thumb , "drawable", getActivity().getPackageName());
        food.setImage(resID);
        mImageView.setImageResource(food.getImage());

    }

    private void setButtonListener(){
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShoppingCartItem.getInstance(getContext()).addToCart(food);
                TextView cartNumber = (TextView)getActivity().findViewById(R.id.cart_item_number);
                cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getSize()));

                new AlertDialog.Builder(getActivity()).setTitle("Successful!").setIcon(
                        android.R.drawable.ic_dialog_info)
                        .setMessage("Add 1 " + food.getName() + " to cart!")
                        .setPositiveButton("Jump to cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent cartAct = new Intent(getActivity(), CartActivity.class);
                                startActivity(cartAct);
                            }
                        })
                        .setNegativeButton("Continue", null).show();
            }
        });
    }

}
