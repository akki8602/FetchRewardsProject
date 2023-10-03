package com.example.fetchsoftwareengproject;

public class ListItem {
    int id;
    int listId;
    String name;

    public ListItem(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public int getListId() {
        return this.listId;
    }

    public String getName() {
        return this.name;
    }
}
