package com.example.saksham.blackoutrestuarant.item;

/**
 * Created by saksham_ on 02-Apr-18.
 */

public class Quant {

   private String Full,Half,Quarter;

    public Quant(String full, String half, String quarter) {
        Full = full;
        Half = half;
        Quarter = quarter;
    }

    public Quant()
    {

    }

    public void setFull(String full) {
        Full = full;
    }

    public void setHalf(String half) {
        Half = half;
    }

    public void setQuarter(String quarter) {
        Quarter = quarter;
    }

    public String getFull() {
        return Full;
    }

    public String getHalf() {
        return Half;
    }

    public String getQuarter() {
        return Quarter;
    }
}
