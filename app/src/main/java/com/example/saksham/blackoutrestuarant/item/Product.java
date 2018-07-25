package com.example.saksham.blackoutrestuarant.item;


public class Product {

    private String Name;
    private String Image;
    private String ItemId;
    private String Description;
    private String Price;
    private String Quantity;

    public Product()
    {

    }
    public Product(String name, String image, String itemId, String description,String price,String quantity) {
        Name = name;
        Image = image;
        ItemId = itemId;
        Description = description;
        Price=price;
        Quantity=quantity;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getItemId() {
        return ItemId;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }


    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
