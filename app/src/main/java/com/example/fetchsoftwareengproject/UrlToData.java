//package com.example.fetchsoftwareengproject;
//
//import android.os.AsyncTask;
//import java.net.URL;
//import java.util.List;
//
//class UrlToData extends AsyncTask<URL, void, String> {
//    @Override
//    protected String doInBackground(URL url) {
//        String output = "";
//        try {
//            ParseJson json = new ParseJson(new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json"));
//            json.readList(json.getUrl());
//            List<ListItem> orderList = json.getOrder();
//            output = json.print();
//        } catch (Exception e){
//            System.out.print("json url parson might not have worked");
//        }
//        return output;
//    }
//}
