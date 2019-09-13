package com.demo.bankingproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionListActivity extends AppCompatActivity {
    TextView notfound;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SharedPreference sharedPreference;
    ArrayList<BankDetails> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        this.sharedPreference = new SharedPreference();

        notfound=findViewById(R.id.notfound);

        recyclerview=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(TransactionListActivity.this);
        recyclerview.setLayoutManager(layoutManager);

        this.sharedPreference = new SharedPreference();
        arrayList = sharedPreference.getFavorites(TransactionListActivity.this);


        if(arrayList!=null)
        {
            adapter=new FavoriteVideosAdapter(TransactionListActivity.this,arrayList);
            layoutManager=new LinearLayoutManager(TransactionListActivity.this);
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setAdapter(adapter);

            notfound.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);

        }
        else {
            notfound.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
