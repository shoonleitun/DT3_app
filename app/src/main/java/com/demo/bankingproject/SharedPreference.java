package com.demo.bankingproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class SharedPreference {
    public static final String FAVORITES = "Stories_Favorite";
    public static final String PREFS_NAME = "Stories";


    public void storebanktransactions(Context context, List<BankDetails> favorites) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(FAVORITES, new Gson().toJson(favorites));

        editor.apply();
    }

    public void addTransactions(Context context, BankDetails bankDetails1) {

        List<BankDetails> bankDetails = getFavorites(context);
        if (bankDetails == null) {
            bankDetails = new ArrayList();
        }
        bankDetails.add(bankDetails1);
        storebanktransactions(context, bankDetails);
    }

    public void removeFavorite(Context context, BankDetails quotes) {
        ArrayList<BankDetails> favorites = getFavorites(context);
        if (favorites != null) {

            favorites.remove(quotes);
            Toast.makeText(context, "Video Removed From Favorites\n Refreshing", Toast.LENGTH_SHORT).show();
            storebanktransactions(context, favorites);
        }
    }

    public boolean check(BankDetails object1, BankDetails object2)
    {
        if (object1.getDateandtime().equals(object2.getDateandtime()))
            return true;
        else
            return false;
    }
    public ArrayList<BankDetails> getFavorites(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        if (!settings.contains(FAVORITES)) {
            Log.d("GetFavourites", settings.toString()+" null");
            return null;
        }
        else
            return new Gson().fromJson(settings.getString(FAVORITES,null), new TypeToken<ArrayList<BankDetails>>(){}.getType());

    }
}
