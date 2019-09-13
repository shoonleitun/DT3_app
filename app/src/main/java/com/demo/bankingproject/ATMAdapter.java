package com.demo.bankingproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ATMAdapter extends RecyclerView.Adapter<ATMAdapter.ViewHolder> {


    ArrayList<ATMmodel> atmlist;
    ATMActivity context;

    public ATMAdapter(ATMActivity context, ArrayList<ATMmodel> atmlist) {
        this.context = context;
        this.atmlist = atmlist;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_atmname, parent, false);
        return new ViewHolder(itemView, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_name.setText(atmlist.get(position).getAtmname());
        holder.tv_address.setText(atmlist.get(position).getAddress());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MapActivity.class);
                i.putExtra("lat", atmlist.get(position).getLat());
                i.putExtra("lng", atmlist.get(position).getLng());
                i.putExtra("title", atmlist.get(position).getAtmname());

                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return atmlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name, tv_address;
        CardView card;


        public ViewHolder(View itemView, int type) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_atmname);
            tv_address = itemView.findViewById(R.id.tv_address);
            card = itemView.findViewById(R.id.card);
        }
    }
}