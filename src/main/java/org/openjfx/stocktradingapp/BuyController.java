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
public class BuyController implements Initializable {

    @FXML
    private TextField numberOfShares;
    @FXML
    private Button btnBuy;
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
    private void onClickBuy(ActionEvent event) throws IOException {
        String n = numberOfShares.getText();
        int numShares = Integer.parseInt(n);
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("StocInfo.fxml"));
        Parent root = fxml.load();
        
        if(numShares > numOfShares){
            //alert not possible
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Cannot be greater than available shares!");
            alert.setHeaderText("Enter less than available shares.");
            alert.showAndWait();
        }else{
            FileHandling.EditShares(numShares, companyName, "subtract");  //edit file
            Stage stage = (Stage)btnBuy.getScene().getWindow();
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
