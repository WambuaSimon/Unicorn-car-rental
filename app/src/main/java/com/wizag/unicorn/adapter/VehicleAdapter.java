package com.wizag.unicorn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.wizag.unicorn.R;
import com.wizag.unicorn.model.VehicleModel;

import java.util.List;

public class CarGridAdapter extends RecyclerView.Adapter<CarGridAdapter.MyViewHolder> {

    private List<VehicleModel> docsData;
    Context context;
    public CarGridAdapterListener onClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView car_name_featured;
        TextView daily_amount_featured;
       CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.car_name_featured = itemView.findViewById(R.id.car_name_featured);
            this.daily_amount_featured = itemView.findViewById(R.id.daily_amount_featured);
            this.card = itemView.findViewById(R.id.card);


        }
    }

    public CarGridAdapter(List<VehicleModel> data, Context context, CarGridAdapterListener listener) {
        this.docsData = data;
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
        final TextView doc_type = holder.car_name_featured;
        TextView doc_date = holder.daily_amount_featured;
        CardView card = holder.card;


        doc_type.setText(docsData.get(listPosition).getCarMake() + " "+ docsData.get(listPosition).getCarModel());
        doc_date.setText(docsData.get(listPosition).getDailyPricing());
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.fabOnClick(v, listPosition);

                docsData.remove(listPosition);
                notifyDataSetChanged();


            }
        });


    }

    @Override
    public int getItemCount() {
        return docsData.size();
    }

    public interface CarGridAdapterListener {

        void fabOnClick(View v, int position);


    }

}