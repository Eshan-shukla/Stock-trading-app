/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.stocktradingapp;

import java.util.ArrayList;

/**
 *
 * @author eshan
 */
public class StockData {
    private String symbol;
    private String name;
    private double price;
    private String Date;
    private int numberOfShare;
    private String currency;

    public StockData(String symbol, String name, double price, String Date, int numberOfShare, String currency) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.Date = Date;
        this.numberOfShare = numberOfShare;
        this.currency = currency;
    }
    
    
    

    public int getNumberOfShare() {
        return numberOfShare;
    }

    public void setNumberOfShare(int numberOfShare) {
        this.numberOfShare = numberOfShare;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    

    public StockData(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        //this.Date = Date;
    }
  

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    
    //access the local Json file and create a list of those objects
    //and return the list
//    public static ArrayList<StockData> getListFromJson(){
//        File 
//    }
    
    
    
    
}
