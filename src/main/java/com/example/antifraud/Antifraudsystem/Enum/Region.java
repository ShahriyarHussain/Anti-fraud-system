package com.example.antifraud.Antifraudsystem.Enum;

public enum Region {
    EAP("East Asia and Pacific"),
    ECA("Europe and Central Asia"),
    HIC("High-Income countries"),
    LAC("Latin America and the Caribbean"),
    MENA("The Middle East and North Africa"),
    SA("South Asia"),
    SSA("Sub-Saharan Africa");

    private final String region;

    Region(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return this.region;
    }
}