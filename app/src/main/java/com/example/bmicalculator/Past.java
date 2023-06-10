package com.example.bmicalculator;

import static com.example.bmicalculator.SavingAndLoadingHistory.load;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Past extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);

        Intent fromAct = getIntent();
        Bundle args = fromAct.getBundleExtra("BUNDLE");
        ArrayList<Person> history = (ArrayList<Person>) args.getSerializable("ARRAYLIST");

        RecyclerView recyclerView = findViewById(R.id.historyview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        HistoryAdapter adapter = new HistoryAdapter((Context) this, history);
        recyclerView.setAdapter(adapter);
    }
}