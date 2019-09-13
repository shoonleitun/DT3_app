package com.demo.bankingproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {


    ArrayList<String> bankname;
    ATMFinder context;

    public BankAdapter(ATMFinder context, ArrayList<String> bankname) {
        this.context = context;
        this.bankname = bankname;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bankname, parent, false);
        return new ViewHolder(itemView, viewType);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_name.setText(bankname.get(position));

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ATMActivity.class);
                i.putExtra("pos", position);
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
        return bankname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name;


        public ViewHolder(View itemView, int type) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);

        }
    }
}