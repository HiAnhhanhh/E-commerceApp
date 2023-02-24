package com.example.ecommerceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.MyCartModel;

import org.w3c.dom.Text;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> list;

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.mycart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.currentTime.setText(list.get(position).getCurrentTime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView currentDate, currentTime, totalPrice, totalQuantity, productName, productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currentDate = itemView.findViewById(R.id.current_date);
            currentTime = itemView.findViewById(R.id.current_time);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
