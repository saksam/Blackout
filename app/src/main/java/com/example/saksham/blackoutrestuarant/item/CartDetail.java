package com.example.saksham.blackoutrestuarant.item;

/**
 * Created by saksham_ on 06-Apr-18.
 */

public class CartDetail {

    private String Phone,ItemId,ProductName,Size;
    private double Price;
    private int Quantity;

    public CartDetail()
    {

    }

    public CartDetail( String itemId,String phone, String productName, String size, double price, int quantity) {
        Phone = phone;
        ItemId = itemId;
        ProductName = productName;
        Size = size;
        Price = price;
        Quantity = quantity;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


}
