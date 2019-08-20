package com.wizag.unicorn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wizag.unicorn.R;
import com.wizag.unicorn.model.VehicleModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private List<VehicleModel> vehicleData;
    Context context;
    public VehicleAdapterListener onClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView car_name_featured;
        TextView daily_amount_featured;
        CardView card;
        ImageView car_image_featured;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.car_name_featured = itemView.findViewById(R.id.car_name_featured);
            this.daily_amount_featured = itemView.findViewById(R.id.daily_amount_featured);
            this.car_image_featured = itemView.findViewById(R.id.car_image_featured);
            this.card = itemView.findViewById(R.id.card);


        }
    }

    public VehicleAdapter(List<VehicleModel> data, Context context, VehicleAdapterListener listener) {
        this.vehicleData = data;
        this.context = context;
        this.onClickListener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.featured_grid_car_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        final TextView car_name = holder.car_name_featured;
        TextView daily_amount = holder.daily_amount_featured;
        ImageView car_image_featured = holder.car_image_featured;
        CardView card = holder.card;

        car_name.setText(vehicleData.get(listPosition).getCarMake() + " " + vehicleData.get(listPosition).getCarModel());
        double dailyRate = Double.parseDouble(vehicleData.get(listPosition).getDailyPricing());


//        NumberFormat.getNumberInstance(Locale.getDefault()).format(vehicleData.get(listPosition).getDailyPricing());

        daily_amount.setText("Ksh " +NumberFormat.getNumberInstance(Locale.getDefault()).format(dailyRate));

        Glide.with(context).load("https://unicorn.wizag.co.ke/cartypes/" + vehicleData.get(listPosition).getCarImage()).into(car_image_featured);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.fabOnClick(v, listPosition);
                notifyDataSetChanged();


            }
        });


    }

    @Override
    public int getItemCount() {
        return vehicleData.size();
    }

    public interface VehicleAdapterListener {

        void fabOnClick(View v, int position);


    }

}