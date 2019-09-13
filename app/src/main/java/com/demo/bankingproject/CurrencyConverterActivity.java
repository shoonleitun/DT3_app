package com.demo.bankingproject;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class CurrencyConverterActivity extends AppCompatActivity {


    EditText dataet, resultet;
    Spinner fromspinner, tospinner;
    Button convertbtn;

    public static String arrayref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);


        dataet = findViewById(R.id.dataet);
        resultet = findViewById(R.id.resultet);
//        resultet.setEnabled(false);
        fromspinner = findViewById(R.id.fromspinner);
        tospinner = findViewById(R.id.tospinner);
        convertbtn = findViewById(R.id.convertbtn);


        convertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String from = fromspinner.getSelectedItem().toString();
                String to = tospinner.getSelectedItem().toString();

                arrayref = from + "_" + to;


                makeJsonObjectRequest("http://free.currencyconverterapi.com/api/v5/convert?q=" + from + "_" + to + "&compact=y");

            }
        });

    }


    private void makeJsonObjectRequest(String url) {


        StringRequest jsonObjReq = new StringRequest(com.android.volley.Request.Method.GET,
                url, new Response.Listener<String>() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE", response);

                parseActivityName(response);

            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("tagg", "Error: " + error.getMessage());
                    }
                });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void parseActivityName(String res) {
        try {

            JSONObject jsonObject = new JSONObject(res);
            JSONObject jsonObject1 = jsonObject.getJSONObject(arrayref);


            String value1 = jsonObject1.getDouble("val") + "";
            String value2 = dataet.getText().toString();


            BigDecimal v1 = new BigDecimal(value1);
            BigDecimal v2 = new BigDecimal(value2);

            BigDecimal finalres = v1.multiply(v2);


            resultet.setText(finalres + "");


        } catch (final JSONException e) {
            Log.e("tagg", "Json parsing error: " + e.getMessage());
            CurrencyConverterActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CurrencyConverterActivity.this,
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });


        }
    }


}
