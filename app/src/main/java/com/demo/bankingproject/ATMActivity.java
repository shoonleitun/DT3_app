package com.demo.bankingproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ATMActivity extends AppCompatActivity {

    BankAdapter bankAdapter;
    RecyclerView recyclerView;
    int pos;
    ATMmodel atMmodel;
    ATMAdapter atmAdapter;
    ArrayList<ATMmodel> atmlist = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmmain);
        recyclerView = findViewById(R.id.recyclerview);

        pos = getIntent().getIntExtra("pos", 0);

        LinearLayoutManager layoutManager_news = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager_news);


        showProgressDialog();
        makeJsonObjectRequestNews("https://abcdxyzadminpanel.000webhostapp.com/bankinglist.json");

    }

    private void makeJsonObjectRequestNews(String url) {

        StringRequest jsonObjReq = new StringRequest(com.android.volley.Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", response);

                parseActivityNameNews(response);

            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("tagg", "Error: " + error.getMessage());
                        Toast.makeText(ATMActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void parseActivityNameNews(String res) {
        try {

            JSONObject jsonObject = new JSONObject(res);
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("Bank");
                JSONObject jsonObject1 = jsonArray.getJSONObject(pos);
                JSONArray jsonArrayatm = jsonObject1.getJSONArray("ATM");

                for (int i = 0; i < jsonArrayatm.length(); i++) {

                    JSONObject jo = jsonArrayatm.getJSONObject(i);
                    String atmname = jo.getString("atmname");
                    String address = jo.getString("address");
                    String lat = jo.getString("lat");
                    String lng = jo.getString("lng");

                    atMmodel = new ATMmodel(atmname, address, lat, lng);
                    atmlist.add(atMmodel);
                }

                hideProgressDialog();
                atmAdapter = new ATMAdapter(ATMActivity.this, atmlist);
                recyclerView.setAdapter(atmAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (final JSONException e) {
            Log.e("tagg", "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ATMActivity.this,
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });


        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
