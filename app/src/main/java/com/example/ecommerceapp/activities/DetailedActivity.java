package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularProductsModel;
import com.example.ecommerceapp.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCard, buyNow;
    ImageView addItem, removeItem;

    int totalQuantity = 1;
    int totalPrice = 0;


    NewProductsModel newProductsModel = null;
    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null ;


    FirebaseAuth auth;
    private FirebaseFirestore fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        rating = findViewById(R.id.rating);
        price = findViewById(R.id.detailed_price);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        addToCard = findViewById(R.id.Add_to_cart);
        buyNow = findViewById(R.id.Buy_now);

        final Object obj = getIntent().getSerializableExtra("detailed");

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        if(obj instanceof  PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }else if (obj instanceof  NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        if (newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            description.setText(newProductsModel.getDescription());

            totalPrice = newProductsModel.getPrice() * totalQuantity;

        }

        if(popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            description.setText(popularProductsModel.getDescription());

            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }

        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            price.setText(String.valueOf(showAllModel.getPrice()));
            description.setText(showAllModel.getDescription());

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }

        addToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCard();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
//                    if(newProductsModel != null){
//                        totalPrice = newProductsModel.getPrice() * totalQuantity;
//                    }
//                    if(popularProductsModel != null){
//                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
//                    }
//                    if(showAllModel != null){
//                        totalPrice = showAllModel.getPrice() * totalQuantity;
//                    }
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }

    private void addtoCard() {
        String saveCurrentTime, saveCurrentDate ;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap <String,Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("producePrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        fireStore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("Users")
                .add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to a Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}