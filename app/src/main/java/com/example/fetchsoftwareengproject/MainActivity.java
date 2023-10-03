package com.example.fetchsoftwareengproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String output = "hoii";

        try {
            Log.d("My tag", "works fine before parsing");
            ParseJson json = new ParseJson(new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json"));
            Log.d("My tag", "works fine after parsing");
            json.readList(json.getUrl());
            Log.d("My tag", "works fine after reading into struct");
            List<ListItem> orderList = json.getOrder();
            Log.d("My tag", "works fine till here");
            Collections.sort(orderList, new listIdCompare());
            output = json.print();
        } catch (IOException e) {
            Log.d("exception", "didn't work");
            throw new RuntimeException(e);
        }
        display = (TextView) findViewById(R.id.display);
        display.setText(output);
    }
}

class listIdCompare implements Comparator<ListItem> {

    public int compare(ListItem item1, ListItem item2) {
        int curr;
        if (item1.getListId() < item2.getListId()) {
            curr = -1;
        }
        else if (item1.getListId() > item2.getListId()) {
            curr = 1;
        }
        else {
            return item1.getName().compareTo(item2.getName());
        }
        return curr;
    }
}