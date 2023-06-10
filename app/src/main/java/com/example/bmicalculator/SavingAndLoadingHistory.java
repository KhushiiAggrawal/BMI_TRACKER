package com.example.bmicalculator;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavingAndLoadingHistory {

    private static final String BMI_HISTORY = "bmi_history";

    public static void save_data(Context context, ArrayList<Person> history) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(history);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Toast.makeText(context,"save_data",Toast.LENGTH_SHORT).show();
        editor.putString(BMI_HISTORY,json).apply();
    }

    public static ArrayList<Person> load(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPreferences.getString(BMI_HISTORY,"");
        Gson gson = new Gson();
        Type type =new TypeToken<ArrayList<Person>>(){}.getType();
        ArrayList<Person> history= gson.fromJson(json,type);
        return history;

    }

    public static void remove_data(Context context, ArrayList<Person> history) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(history);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        Toast.makeText(context,"remove_data",Toast.LENGTH_SHORT).show();
        editor.putString(BMI_HISTORY,json).apply();
    }
}
