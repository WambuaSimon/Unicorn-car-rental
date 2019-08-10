package com.wizag.unicorn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.wizag.unicorn.R;
import com.wizag.unicorn.model.BookingsModel;


import java.util.List;


public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    private List<BookingsModel> bookingsModels;
    Context context;
    public BookingsAdapterListener onClickListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView car;
        TextView pick_up_date;
        TextView pick_up_time;
        TextView drop_off_date;
        TextView drop_off_time;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.car = itemView.findViewById(R.id.car);
            this.pick_up_date = itemView.findViewById(R.id.pick_up_date);
            this.pick_up_time = itemView.findViewById(R.id.pick_up_time);
            this.drop_off_date = itemView.findViewById(R.id.drop_off_date);
            this.drop_off_time = itemView.findViewById(R.id.drop_off_time);
            this.card = itemView.findViewById(R.id.card);


        }
    }

    public BookingsAdapter(List<BookingsModel> data, Context context, BookingsAdapterListener listener) {
        this.bookingsModels = data;
        this.context = context;
        this.onClickListener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        final TextView car = holder.car;
        final TextView pick_up_date = holder.pick_up_date;
        final TextView pick_up_time = holder.pick_up_time;
        final TextView drop_off_date = holder.drop_off_date;
        final TextView drop_off_time = holder.drop_off_time;
        final CardView card = holder.card;


        car.setText(bookingsModels.get(listPosition).getCar());
        pick_up_date.setText(bookingsModels.get(listPosition).getPickup_date());
        pick_up_time.setText(bookingsModels.get(listPosition).getPickup_time());
        drop_off_date.setText(bookingsModels.get(listPosition).getDropoff_date());
        drop_off_time.setText(bookingsModels.get(listPosition).getDropoff_time());


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.cardOnClick(v, listPosition);
                notifyDataSetChanged();


            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingsModels.size();
    }

    public interface BookingsAdapterListener {

        void cardOnClick(View v, int position);


    }
}
