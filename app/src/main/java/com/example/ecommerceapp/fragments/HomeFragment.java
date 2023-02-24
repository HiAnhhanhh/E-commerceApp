package com.example.ecommerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.ShowAllActivity;
import com.example.ecommerceapp.adapter.CategoryAdapter;
import com.example.ecommerceapp.adapter.NewProductsAdapter;
import com.example.ecommerceapp.adapter.PopularProductsAdapter;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView catShowAll, popularShowAll, newProductShowAll;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    ImageSlider imageSlider;
    RecyclerView cat_rec, popularRecyclerView,new_product_rec;
    CategoryAdapter categoryAdapter;
    List <CategoryModel> categoryModelList;

    List<NewProductsModel> newProductsModelList;
    NewProductsAdapter newProductsAdapter;

    //
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    FirebaseFirestore db;


    public HomeFragment () {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cat_rec = root.findViewById(R.id.rec_category);
        new_product_rec = root.findViewById(R.id.new_product_rec);
        popularRecyclerView= root.findViewById(R.id.popular_rec);
        progressDialog = new ProgressDialog(getActivity());
        linearLayout = root.findViewById(R.id.home_layout);

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll= root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        linearLayout.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();

        progressDialog.setTitle("Well come to my App");
        progressDialog.setMessage("please wait ....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        imageSlider =  root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount Shoe Items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount on fume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"70% off", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        //

        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        cat_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        cat_rec.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                linearLayout.setVisibility(View.VISIBLE);
                            }
                        } else {

                        }

                    }
                });


        //
        

        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        new_product_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        new_product_rec.setAdapter(newProductsAdapter);


        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });



        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularRecyclerView.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        } else {

                        }

                    }
                });




        return root;
    }
}