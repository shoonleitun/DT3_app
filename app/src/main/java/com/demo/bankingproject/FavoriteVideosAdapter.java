package com.demo.bankingproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class FavoriteVideosAdapter extends RecyclerView.Adapter<FavoriteVideosAdapter.ViewHolder> {

    SharedPreference sharedPreference;
    Context mContext;
    private ArrayList<BankDetails> mVideoList;

    public FavoriteVideosAdapter(Context mContext, ArrayList<BankDetails> mVideoList) {
        sharedPreference = new SharedPreference();
        this.mVideoList = mVideoList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_status,tv_wallet_balance,tv_amount,tv_description,tv_date;

        public ViewHolder(View v) {
            super(v);
            tv_status=v.findViewById(R.id.tv_status);
            tv_wallet_balance=v.findViewById(R.id.tv_wallet_balance);
            tv_amount=v.findViewById(R.id.tv_amount);
            tv_description=v.findViewById(R.id.tv_description);
            tv_date=v.findViewById(R.id.tv_date);



        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.list_item_transaction, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BankDetails video = mVideoList.get(position);

        holder.tv_status.setText(video.getStatus());
        holder.tv_wallet_balance.setText(video.getWalletbalance()+" SGD");
        holder.tv_amount.setText(video.getTransactionamt()+" SGD");
        holder.tv_description.setText(video.getDescription());
        holder.tv_date.setText(video.getDateandtime());




    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}

