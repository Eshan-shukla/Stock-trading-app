/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.stocktradingapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eshan
 */
public class FileHandling {
    
    
    public FileHandling(){
        
    }
    
    //subtract num of shares from the JSON file (BUYING)
    public static void EditShares(int num, String companyName, String action){
        File file = new File("/home/eshan/Desktop/shareInfo.json");
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
                    int numOfShares = current.getInt("num_of_shares");
                    //if name is same as companyName get the companySymbol
                    if(companyName.equalsIgnoreCase(name)){
                        if(action.equals("subtract")){
                            current.put("num_of_shares", (numOfShares-num));
                        }else{
                            current.put("num_of_shares", (numOfShares+num));
                        }
                        break;
                    }
                }
                //write the updated array back to the local currency rates dile
                JSONObject rootJobj = new JSONObject();
                rootJobj.put("Shares", jarr);
                
                FileWriter filew = new FileWriter(file);
                filew.write(rootJobj.toString());
                filew.flush();
                filew.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StocInfoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StocInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
