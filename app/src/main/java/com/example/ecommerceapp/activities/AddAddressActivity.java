package com.example.ecommerceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddAddressActivity extends AppCompatActivity {

    EditText name, addressLane, city, postalCode, phoneNumber ;
    Button btn_addAddress;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        name = findViewById(R.id.name_address);
        city = findViewById(R.id.city);
        addressLane = findViewById(R.id.address_lane);
        postalCode = findViewById(R.id.postal_code);
        phoneNumber = findViewById(R.id.phone_number);
        btn_addAddress = findViewById(R.id.btn_add_address);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        btn_addAddress.setOnClickListener(v -> addAddress());

    }

    private void addAddress(){
        String userName = name.getText().toString();
        String userCity = city.getText().toString();
        String userAddress = addressLane.getText().toString();
        String userCode = postalCode.getText().toString();
        String userPhone = phoneNumber.getText().toString();
        String finalAddress = "";

        if (!userName.isEmpty()){
            finalAddress += userName + ", ";
        }
        if (!userCity.isEmpty()){
            finalAddress += userCity + ", ";
        }
        if (!userAddress.isEmpty()){
            finalAddress += userAddress + ", ";
        }
        if (!userCode.isEmpty()){
            finalAddress += userCode + ", ";
        }
        if (!userPhone.isEmpty()){
            finalAddress += userPhone + ", ";
        }
        if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userPhone.isEmpty() ){

            Map<String, String> map = new HashMap<>();
            map.put("userAddress", finalAddress);

            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                    .collection("Address")
                    .add(map)
                    .addOnCompleteListener(task -> {
                        if( task.isSuccessful()){
                            Toast.makeText(AddAddressActivity.this, "Added Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddAddressActivity.this,DetailedActivity.class));
                            finish();
                        }
                    });
        }
    }
}