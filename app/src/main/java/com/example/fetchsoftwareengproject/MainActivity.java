package com.example.fetchsoftwareengproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Implementing background thread to deal with network request
        // and non-UI functions
        Thread backgroundThread = new Thread() {
            public void run() {
                backgroundParsingTask();
            }
        };
        backgroundThread.start();
        try {
            backgroundThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void backgroundParsingTask() {
        try {
            ParseJson json = new ParseJson(new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json"));
            json.readList(json.getUrl());
            List<ListItem> orderList = json.getOrder();
            Collections.sort(orderList, new listIdCompare());

            //To prevent displaying from occurring too quickly on the
            // main thread
            runOnUiThread(new Runnable() {
                public void run() {
                    TableLayout table = (TableLayout) findViewById(R.id.table);
                    List<ListItem> items = json.getOrder();

                    for (int i = 0; i < items.size(); i++) {
                        TableRow row = new TableRow(getApplicationContext());

                        TextView col1 = new TextView(getApplicationContext());
                        col1.setPadding(30, 8, 8, 8);
                        col1.setGravity(Gravity.CENTER_HORIZONTAL);
                        col1.setText(String.valueOf(items.get(i).getListId()));
                        TextView col2 = new TextView(getApplicationContext());
                        col2.setPadding(320, 8, 8, 8);
                        col2.setGravity(Gravity.CENTER_HORIZONTAL);
                        col2.setText(items.get(i).getName());
                        TextView col3 = new TextView(getApplicationContext());
                        col3.setPadding(250, 8, 8, 8);
                        col3.setGravity(Gravity.CENTER_HORIZONTAL);
                        col3.setText(String.valueOf(items.get(i).getId()));

                        row.addView(col1);
                        row.addView(col2);
                        row.addView(col3);
                        table.addView(row);
                    }
                }
            }
            );
        } catch (IOException e) {
            Log.d("exception", "Parsing didn't work");
            throw new RuntimeException(e);
        }
    }
}

// Custom comparator to sort list by listId and then name
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
            // if name needs to be sorted in numerical order of
            // its indicated id instead of its dictionary order,
            // simply use id parameter instead:
            // return item1.getId() < item2.getId ? -1 : 1;
            return item1.getName().compareTo(item2.getName());
        }
        return curr;
    }
}