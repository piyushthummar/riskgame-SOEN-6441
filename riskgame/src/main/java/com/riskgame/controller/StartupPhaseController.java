/**
 * 
 */
package com.riskgame.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.riskgame.config.StageManager;
import com.riskgame.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 
 * @author <a href="mailto:r_istry@encs.concordia.ca">Raj Mistry</a>
 */
@Controller
public class StartupPhaseController implements Initializable {
	@FXML
	private Button btnback;

	@FXML
	private Button btnPlaceAll;

	@FXML
	private ComboBox<?> comboBoxchosenMap;

	@FXML
	private ComboBox<?> comboBoxNoOfPlayer;

	@FXML
	private TextField txtCommandLine;

	@FXML
	private Button btnStartGame;

	@FXML
	private Button btnFireCommand;

	@FXML
	private Button btnPopulatecountry;

	@FXML
	private TextArea txtConsoleLog;

	@FXML
	void startGame(ActionEvent event) {
		stageManager.switchScene(FxmlView.PLAYGAME);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	void backToMainPage(ActionEvent event) {
		stageManager.switchScene(FxmlView.MAP);
	}

	@FXML
	void fireCommand(ActionEvent event) {

	}

	@FXML
	void populateCountries(ActionEvent event) {

	}

	@FXML
	void placeAllArmy(ActionEvent event) {

	}

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}
