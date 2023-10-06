package com.example.fetchsoftwareengproject;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    // Parses json for each array entry
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

    // Parse each object and creates an ItemList object
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
