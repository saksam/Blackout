package com.example.saksham.blackoutrestuarant.design;


public class HomeMenuData {

    private String Name;
    private String Image;
    public HomeMenuData()
    {

    }
    public HomeMenuData(String Name,String Image)
    {
        this.Image=Image;
        this.Name=Name;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }
}
