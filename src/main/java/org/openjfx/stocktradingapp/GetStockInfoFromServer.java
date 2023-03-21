/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.stocktradingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eshan
 */
public class GetStockInfoFromServer {
    
    //call SharesInfo server to get all the information
    //companySymbol and currency will always be correct
    public static StockData getStockInfo(String companySymbol, String currency){
        StockData ans = null;
        String wurl = "http://localhost:8080/SharesInfo/webresources/sharesinfo?";
        try {
            //construct the REST service query string
            String urlString = wurl + "comname=" + companySymbol + "&curr=" + currency;
            //associate the query string with HTTP Get connection stream
            URL url = new URL(urlString);
            HttpURLConnection connURL = (HttpURLConnection) url.openConnection();
            connURL.setRequestMethod("GET");
            //Call the REST API using HTTP get 
            System.out.println("\nREST API call: " + connURL.getRequestProperties().toString() + "\n");
            connURL.connect();
            //build the JSON response string
            BufferedReader ins = new BufferedReader(new InputStreamReader(connURL.getInputStream()));
            String jsonText = "";
            String line = "";
            //StringBuilder JSONresultStr = new StringBuilder();

            while ((line = ins.readLine()) != null)  {
               jsonText = jsonText + line;
            }
            
           ins.close();
           connURL.disconnect();
           
           JSONObject jObj = new JSONObject(jsonText);
           
           String name = jObj.getString("company_name");
           String symbol = jObj.getString("company_symbol");
           String date = jObj.getString("last_updated");
           int numOfShares = jObj.getInt("num_of_shares");
           double price = jObj.getDouble("price");
           String curr = jObj.getString("currency");

           ans = new StockData(symbol, name, price, date, numOfShares, currency);


        } 
        catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } 
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        return ans;
    }
}
