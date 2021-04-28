/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
   // @FXML
//    void doRun(ActionEvent event) {
//    	txtResult.clear();
//    	this.doWorstCase(event);
//    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
        
        
    }
    
    public void doWorstCase(ActionEvent event) {
    	
    	Nerc nerc = cmbNerc.getValue();
    	Integer max_years= Integer.parseInt(txtYears.getText());
    	Integer max_hours= Integer.parseInt(txtHours.getText());
    	
    	Integer max_minutes= max_hours*60;
    	
    	List <PowerOutage> result=  model.doWorstCase(nerc, max_minutes, max_years);
    	
    	if(result==null) {
    		txtResult.setText("OOPS");
    		return;
    	}
    	
    	if(result.size()==0 ) {
    		txtResult.setText("No Power Outage Found for the selected Nerc."); 
    		return;
    	}

    	int tothours=0;
    	int totpeople=0;
    	
    	StringBuilder sb= new StringBuilder();
    	
    	for ( PowerOutage po: result) {
    		tothours+= po.getDuration();
    		totpeople+= po.getCustomer_affected();
    		sb.append(po.getStart()).append(po.getEnd()).append("\n");
    	}
    	
    	tothours=tothours/60;
    	
    	txtResult.setText("Total of people affected : "+totpeople+"\n");
    	txtResult.appendText("Total of hours of Power Outages : "+tothours+"\n");
    	txtResult.appendText(sb.toString());
    	
    	return;
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbNerc.getItems().addAll(model.getNercList());
    }
}
