package com.demo.bankingproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ATMFinder extends AppCompatActivity {

    BankAdapter bankAdapter;
    RecyclerView recyclerView;
    ArrayList<String> bannamelist = new ArrayList<>();
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmfinder);
        recyclerView = findViewById(R.id.recyclerview);


        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ATMFinder.this, LoginActivity.class));
                    finish();
                }
            }
        };

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
                        Toast.makeText(ATMFinder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String BankName = jsonObject1.getString("BankName");
                    bannamelist.add(BankName);
                }


                bankAdapter = new BankAdapter(this, bannamelist);
                recyclerView.setAdapter(bankAdapter);
                hideProgressDialog();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (final JSONException e) {
            Log.e("tagg", "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ATMFinder.this,
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
