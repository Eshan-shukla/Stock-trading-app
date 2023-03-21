/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.stocktradingapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eshan
 */
public class SellController implements Initializable {

    @FXML
    private TextField sell;
    @FXML
    private Button btnSell;
    private String companyName;
    private int numOfShares;
    private String curr;

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickSell(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("StocInfo.fxml"));
        Parent root = fxml.load();
        String n = sell.getText();
        if(n.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Fields cannot be empty!");
            alert.setHeaderText("Enter again");
            alert.showAndWait();
        }else{
            int numShares = Integer.parseInt(n);
            int sharesHeld = 100000 - numOfShares;
            //System.out.println(sharesHeld+" num of available shares to purchase " + numOfShares);
            if(numShares > sharesHeld){
                //Alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("You don't have this many shares.");
                alert.setHeaderText("Enter less than available shares.");
                alert.showAndWait();
            }else{
                FileHandling.EditShares(numShares, companyName, "add");
                Stage stage = (Stage)btnSell.getScene().getWindow();
                stage.close();
                StocInfoController sc = fxml.getController();
                sc.setCur(curr);
                sc.refreshThisPage(companyName, curr);
                Stage stg = new Stage();  
                Scene scene = new Scene(root);
                stg.setScene(scene);
                stg.setTitle("Stock Info");
                stg.show();
        }
        }
        
        
        
        
    }
    
}
