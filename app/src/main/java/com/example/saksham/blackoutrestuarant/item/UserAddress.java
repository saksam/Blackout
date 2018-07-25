package com.example.saksham.blackoutrestuarant.item;

import java.util.List;

/**
 * Created by saksham_ on 27-Apr-18.
 */

public class UserAddress {


    private List<UserAddressItem> addressList;

    public UserAddress()
    {

    }

    public UserAddress(List<UserAddressItem> addressList) {

        this.addressList=addressList;
    }

    public List<UserAddressItem> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<UserAddressItem> addressList) {
        this.addressList = addressList;
    }
}
