package com.example.smd.project.HomePage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smd.project.HomePage.HomePageActivity;
import com.example.smd.project.HomePage.adapter.AllFoodAdapter;
import com.example.smd.project.R;
import com.example.smd.project.model.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AllTabFragment extends Fragment {


    private String TAG = "ALLFOOD";
    private List<String> foodNames = new ArrayList<>();
    private List<String> imageNames = new ArrayList<>();
    private List<String> foodPrice= new ArrayList<>();

    private void fillFoodList() {
        foodNames.addAll(Arrays.asList("Chicken Karahi",
                "Chicken Makhani",
                "Daal",
                "French Fries",
                "Gol Gappay",
                "Halwa Puri",
                "Kebab",
                "Pakora",
                "Pulao",
                "Samosa"
        ));
    }

    private void fillPriceList() {
        foodPrice.addAll(Arrays.asList("500",
                "300",
                "200",
                "50",
                "30",
                "80",
                "100",
                "50",
                "200",
                "30"
        ));
    }

        private void fillImageList() {
            imageNames.addAll(Arrays.asList("chicken_karahi", "chicken_makhani",
                    "daal", "fries","gol_gappay", "halwapuri","kebab", "pakora", "pulao", "samosa"
            ));
        }


    ArrayList<Food> foods = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private AllFoodAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    public AllTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_tab, container, false);

        fillFoodList();
        fillPriceList();
        fillImageList();


        // Request Data From Web Service
        if (foods.size()==0){
            objRequestMethod();
        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_all);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AllFoodAdapter(getActivity(), foods);
        adapter.setOnItemClickListener(new AllFoodAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Bundle itemInfo = new Bundle();
                for (int i=0; i<foods.size(); i++){
                    if (foods.get(i).getId() == Integer.valueOf(data)){
                        itemInfo.putInt("foodId", foods.get(i).getId());
                        itemInfo.putString("foodName", foods.get(i).getName());
                        itemInfo.putString("foodRec", foods.get(i).getRecepiee());
                        itemInfo.putDouble("foodPrice", foods.get(i).getPrice());
                        itemInfo.putString("foodImage", foods.get(i).getImageName());
                        break;
                    }
                }
                FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
                foodDetailFragment.setArguments(itemInfo);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, foodDetailFragment)
                        .addToBackStack(AllTabFragment.class.getName())
                        .commit();
            }
        });
        mRecyclerView.setAdapter(adapter);
        return view;
    }


    private void objRequestMethod(){

                    for (int i = 0; i < 10; i++) {
                        String id = String.valueOf(i);
                        String name = foodNames.get(i);
                        String price = foodPrice.get(i);
                        String thumb = imageNames.get(i);
                        final Food curFood = new Food();
                        curFood.setName(name);
                        curFood.setPrice(Double.valueOf(price));
                        curFood.setId(Integer.valueOf(id));
                        curFood.setImageUrl(thumb);

                        foods.add(curFood);

                        int resID = getResources().getIdentifier(thumb , "drawable", getActivity().getPackageName());
                        curFood.setImage(resID);
                        foods.get(i).setImage(resID);
                    }

    }



}
