package com.example.saksham.blackoutrestuarant;



public class Menu_item {

    private String name;
    private int thumbnail;

    public Menu_item() {
    }

    public Menu_item(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
