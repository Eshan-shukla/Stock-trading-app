package org.openjfx.stocktradingapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.reflect.Array.get;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author eshan
 */
public class StocInfoController implements Initializable {

    @FXML
    private Button buy;
    @FXML
    private Button sell;
    @FXML
    private Label cName;
    @FXML
    private Label cSymbol;
    @FXML
    private Label cPrice;
    @FXML
    private Label CNumShares;
    @FXML
    private Label cDate;
    @FXML
    private Label cCurrency;
    private StockData stock;
    private String cur;

    public void setCur(String cur) {
        this.cur = cur;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //return the companySymbol by scanning the JSON file
    private String getCompanySymbol(String companyName){
        File file = new File("/home/eshan/Desktop/shareInfo.json");
        String symbol = "";
        if(file.exists()){
            String json = "";
            try{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                while((line = br.readLine())!=null){
                    json = json + line;
                }
                //create JSONObject of the string
                JSONObject jobj = new JSONObject(json);
                JSONArray jarr = jobj.getJSONArray("Shares");
                
                //iterate through all the objects
                int length = jarr.length();
                for(int i = 0; i < length; ++i){
                    JSONObject current = jarr.getJSONObject(i);
                    String name = current.getString("company_name");
                    //if name is same as companyName get the companySymbol
                    if(companyName.equalsIgnoreCase(name)){
                        symbol = current.getString("company_symbol");
                        break;
                    }
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StocInfoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StocInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return symbol;
    }
    
    /*
    Sets the stage with all the data of the company
    return true if companySymbol was found otherwise false
    */
    public boolean setStageStockData(String companyName, String currency){
        boolean answer = false;
        //go and get me the companySymbol from thefunction
        String companySymbol = getCompanySymbol(companyName);
        
        //if companySymbol is empty string that means company doesn't exists
        //in the database 
        if(companySymbol.equals("")){
            
        }else{
            stock = GetStockInfoFromServer.getStockInfo(companySymbol, currency);
            
            //from this StockData variable extract all the data and set text
            cName.setText(stock.getName());
            cSymbol.setText(stock.getSymbol());
            CNumShares.setText(String.valueOf(stock.getNumberOfShare()));
            cPrice.setText(String.valueOf(stock.getPrice()));
            cDate.setText(stock.getDate());
            cCurrency.setText(currency);
            
            
            answer = true;
        }
        
        
        
        return answer;
        
    }
    public void refreshThisPage(String companyName, String curr){
        File file = new File("/home/eshan/Desktop/shareInfo.json");
        String symbol = "";
        if(file.exists()){
            String json = "";
            try{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                while((line = br.readLine())!=null){
                    json = json + line;
                }
                //create JSONObject of the string
                JSONObject jobj = new JSONObject(json);
                JSONArray jarr = jobj.getJSONArray("Shares");
                
                //iterate through all the objects
                int length = jarr.length();
                for(int i = 0; i < length; ++i){
                    JSONObject current = jarr.getJSONObject(i);
                    String name = current.getString("company_name");
                    //if name is same as companyName get the companySymbol
                    if(companyName.equalsIgnoreCase(name)){
                        cName.setText(current.getString("company_name"));
                        cSymbol.setText(current.getString("company_symbol"));
                        CNumShares.setText(String.valueOf(current.getInt("num_of_shares")));
                        cPrice.setText(String.valueOf(current.getDouble("price")));
                        cDate.setText(current.getString("last_updated"));
                        cCurrency.setText(cur);
                        break;
                    }
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StocInfoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StocInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @FXML
    private void onClickBuy(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("buy.fxml"));
        Parent root = fxml.load();
        BuyController bc = fxml.getController();
        bc.setCompanyName(cName.getText());
        bc.setNumOfShares(Integer.parseInt(CNumShares.getText()));
        bc.setCurr(cur);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Stage s = (Stage)buy.getScene().getWindow();
        s.close();
    }

    @FXML
    private void onClickSell(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("sell.fxml"));
        Parent root = fxml.load();
        SellController sc = fxml.getController();
        sc.setCompanyName(cName.getText());
        sc.setNumOfShares(Integer.parseInt(CNumShares.getText()));
        sc.setCurr(cur);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Stage s = (Stage)sell.getScene().getWindow();
        s.close();
    }
    
}
