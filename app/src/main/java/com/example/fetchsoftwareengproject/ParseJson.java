package com.example.fetchsoftwareengproject;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParseJson {
    private List<ListItem> order = new ArrayList<>();
    private URL url;

    public ParseJson(URL url) throws IOException {
        this.url = url;
    }

    public URL getUrl() {
        return this.url;
    }

    public List<ListItem> getOrder() {
        return this.order;
    }

    public String print() {
        String output = "";
        for (int i = 0; i < this.order.size(); i++) {
            output += this.order.get(i).getListId() + " ";
            output += this.order.get(i).getName() + " ";
            output += this.order.get(i).getId() + "    " + "\n";
        }
        return output;
    }

    public void readList(URL url) throws IOException {
        InputStream input = url.openStream();
        JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
        ListItem currItem;
        reader.beginArray();
        while(reader.hasNext()) {
            currItem = readItem(reader);
            if (currItem.getName() != null) {
                this.order.add(currItem);
            }
            currItem = null;
        }
        reader.endArray();
        reader.close();
    }

    public ListItem readItem(JsonReader reader) throws IOException {
        int id = 0;
        int listId = 0;
        String name = null;
        String label;
        reader.beginObject();
        while(reader.hasNext()) {
            label = reader.nextName();
            if (label.equals("id")) {
                id = reader.nextInt();
            }
            else if (label.equals("listId")) {
                listId = reader.nextInt();
            }
            else if (label.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    name = reader.nextString();
                    if (name.equals("")) {
                        name = null;
                    }
                }
                else {
                    name = null;
                    reader.nextNull();
                }
            }
        }
        reader.endObject();
        return new ListItem(id, listId, name);
    }
}
