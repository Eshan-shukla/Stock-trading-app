/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.stocktradingapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eshan
 */
public class PrimaryController implements Initializable {


    private ListView<String> list;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<StockData> tableView;
    @FXML
    private TableColumn<StockData, String> symbol;
    @FXML
    private TableColumn<StockData, String> name;
    @FXML
    private TableColumn<StockData, String> price;
    private StockData s = new StockData("AAPL", "Apple", 153);
    private ObservableList<StockData> data;
    private ArrayList<StockData> ele = new ArrayList<StockData>();
    @FXML
    private ChoiceBox<String> checkBox;
    String[] currencies = {"INR","GBP","EUR","JPY","AUD","USD","BRL","CAD","CNY","RUB","QAR"};
    @FXML
    private RadioButton r1;
    @FXML
    private RadioButton r2;
    @FXML
    private RadioButton r3;
    @FXML
    private RadioButton r4;
    @FXML
    private Button filter;
    private ToggleGroup tg;
    @FXML
    private RadioButton r5;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tg= new ToggleGroup();
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);
        r4.setToggleGroup(tg);
        r5.setToggleGroup(tg);
                   
        //list.getItems().addAll("Dashboard","Profile","Settings");
        checkBox.getItems().addAll(currencies);
        
        
        File file = new File("/home/eshan/Desktop/shareInfo.json");
        String jsonString = "";
        //if file exists extract data from it
        if(file.exists()){
            try {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                String line = "";
                while((line = br.readLine()) != null){
                    jsonString = jsonString + line;
                }
                
                //create a jsonobject of the string acquired
                JSONObject jobj = new JSONObject(jsonString);
                JSONArray arrJson = jobj.getJSONArray("Shares");
                
                //iterate through all the objects of the Shares json array and add each object to ele list;
                for(int i = 0; i < arrJson.length(); ++i){
                    JSONObject current = arrJson.getJSONObject(i);
                    String symbol = current.getString("company_symbol");
                    String name = current.getString("company_name");
                    double price = current.getDouble("price");
                    StockData sd = new StockData(symbol, name, price);
                    ele.add(sd);
                }
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println("Some error occured while finding this file.");
        }
        
        //add data into observable list
        data = FXCollections.observableArrayList(ele);
        
        //associate data with columns
        symbol.setCellValueFactory(new PropertyValueFactory<StockData, String>("symbol"));
        name.setCellValueFactory(new PropertyValueFactory<StockData, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<StockData, String>("price"));
        
        tableView.setItems(data);
        
    }    

    @FXML
    private void onClickSearch(ActionEvent event) throws IOException {
        //get search string and currency and send it to another stage
        String companyName = txtSearch.getText();
        String currency = checkBox.getValue();
        FXMLLoader sfxml = new FXMLLoader(getClass().getResource("buy.fxml"));
        Parent r = sfxml.load();
        BuyController bc = sfxml.getController();
        bc.setCurr(currency);
        
        if(currency != null){
            //open a new window
            try{
                FXMLLoader fxml = new FXMLLoader(getClass().getResource("StocInfo.fxml"));
                Parent root = fxml.load();
                StocInfoController sc = fxml.getController();
                sc.setCur(currency);
                //send companyName and currency to next stage for display
                boolean success = sc.setStageStockData(companyName, currency);

                //if company name was found in the databse it is a success show the next stage
                if(success){
                    Stage stage = new Stage();  
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Stock Info");
                    stage.show();
                }else{
                    //dialog box - company name invalid
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Company of this name does not exists!");
                    alert.setHeaderText("Enter again");
                    alert.showAndWait();
                }


            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //dialog box - currency cannot be empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select your currency preference!");
            alert.setHeaderText("Currency field cannot be empty.");
            alert.showAndWait();
        }
        

        
    }
    
    @FXML
    private void onClickFilter(ActionEvent event) {
        
        ArrayList<StockData> list = new ArrayList<StockData>();
        //list = ele;
        int len = ele.size();
        ObservableList<StockData> obs;
        
        if(r1.isSelected()){
            for(int i = 0; i < len; ++i){
                if(ele.get(i).getPrice() > 1 && ele.get(i).getPrice() <= 50){
                    list.add(ele.get(i));
                }
            }
        }else if(r2.isSelected()){
            for(int i = 0; i < len; ++i){
                if(ele.get(i).getPrice() > 50 && ele.get(i).getPrice() <= 100){
                    list.add(ele.get(i));
                }
            }
            
        }else if(r3.isSelected()){
            for(int i = 0; i < len; ++i){
                if(ele.get(i).getPrice() > 100 && ele.get(i).getPrice() <= 150){
                    list.add(ele.get(i));
                }
            }
            
        }else if(r4.isSelected()){
            for(int i = 0; i < len; ++i){
                if(ele.get(i).getPrice() > 150 && ele.get(i).getPrice() <= 200){
                    list.add(ele.get(i));
                }
            }
            
        }else if(r5.isSelected()){
            list = ele;
        }
        obs = FXCollections.observableArrayList(list);
        tableView.getItems().clear();
        tableView.setItems(obs);
    }

    @FXML
    private void onClickRow(MouseEvent event) {
    }

    
    
}
