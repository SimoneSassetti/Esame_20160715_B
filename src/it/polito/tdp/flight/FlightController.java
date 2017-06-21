package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Model;
import it.polito.tdp.flight.model.Risultato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		int lung=0;
		try{
			lung=Integer.parseInt(txtDistanzaInput.getText());
		}catch(NumberFormatException n){
			txtResult.appendText("Inserire un valore intero per i km.\n");
			return;
		}
		
		model.getPorti();
		model.getRotte(lung);
		model.creaGrafo();
		if(model.isConnected()){
			txtResult.appendText("\nIl grafo è conesso quindi è possibile raggiungere tutti gli aeroporti da ogni aeroporto.\n");
		}else{
			txtResult.appendText("\nGrafo non connesso.\n");
		}
		
		txtResult.appendText("L'aeroporto non raggiungibile più vicino a Los Angeles Intl è: "+model.aeroportoNonRaggiungibile());
	}

	@FXML
	void doSimula(ActionEvent event) {
		txtResult.clear();
		int pass=0;
		try{
			pass=Integer.parseInt(txtPasseggeriInput.getText());
		}catch(NumberFormatException n){
			txtResult.appendText("Inserire un valore intero per i passeggeri.\n");
			return;
		}
		
		List<Risultato> temp=model.simula(pass);
		txtResult.appendText("Elenco aeroporti raggiunti dai passeggeri:\n");
		for(Risultato r: temp){
			txtResult.appendText(""+r.getA()+" --> "+r.getPasseggeri()+"\n");
		}
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
