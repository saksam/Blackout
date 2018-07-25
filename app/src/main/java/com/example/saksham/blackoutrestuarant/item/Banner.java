package com.example.saksham.blackoutrestuarant.item;

/**
 * Created by saksham_ on 13-Jul-18.
 */

public class Banner {

    private String Id,Name,Image;

    public Banner(String id, String name, String image) {
        Id = id;
        Name = name;
        Image = image;
    }
    public Banner()
    {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
