package com.wizag.unicorn.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wizag.unicorn.R;
import com.wizag.unicorn.model.VehicleModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private List<VehicleModel> vehicleData;
    Context context;
    public CarAdapterListener onClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView car_name;
        TextView daily_rate;
        CardView card;
        ImageView car_image;
        TextView weekly_rate;
        TextView monthly_rate;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.car_name = itemView.findViewById(R.id.car_name);
            this.daily_rate = itemView.findViewById(R.id.daily_rate);
            this.car_image = itemView.findViewById(R.id.car_image);
            this.card = itemView.findViewById(R.id.card);
            this.weekly_rate = itemView.findViewById(R.id.weekly_rate);
            this.monthly_rate = itemView.findViewById(R.id.monthly_rate);


        }
    }

    public CarAdapter(List<VehicleModel> data, Context context, CarAdapterListener listener) {
        this.vehicleData = data;
        this.context = context;
        this.onClickListener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cars_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        final TextView car_name = holder.car_name;
        TextView daily_amount = holder.daily_rate;
        ImageView car_image = holder.car_image;
        CardView card = holder.card;
        TextView weekly_rate = holder.weekly_rate;
        TextView monthly_rate = holder.monthly_rate;

        car_name.setText(vehicleData.get(listPosition).getCarMake() + " " + vehicleData.get(listPosition).getCarModel());

        if (vehicleData.get(listPosition).getDailyPricing() != null) {


            double dailyRate = Double.parseDouble(vehicleData.get(listPosition).getDailyPricing());
            daily_amount.setText("Ksh " + NumberFormat.getNumberInstance(Locale.getDefault()).format(dailyRate));
        }

        weekly_rate.setText(vehicleData.get(listPosition).getWeeklyPricing());
        monthly_rate.setText(vehicleData.get(listPosition).getMonthlyPricing());

        Glide.with(context).load("https://unicorn.wizag.co.ke/cartypes/" + vehicleData.get(listPosition).getCarImage()).into(car_image);

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


    public interface CarAdapterListener {

        void fabOnClick(View v, int position);


    }

}