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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 */
@Controller
public class RiskPlayScreenController implements Initializable{

	@FXML
	private ListView<?> lvTerritoryList;

	@FXML
	private Button btnAttack;

	@FXML
	private ListView<?> lvTerritoryPlayerArmy;

	@FXML
	private Button btnExecute;

	@FXML
	private Button btnReinforcement;

	@FXML
	private Button btnFortify;

	@FXML
	private ListView<?> lvCurrentLog;

	@FXML
	private ListView<?> lvContinent;

	@FXML
	private Button btnEndTurn;

	@FXML
	private ListView<?> lvLog;

	@FXML
	private ListView<?> lvAdjTerritoryList;

	@FXML
	private ComboBox<?> cbAdjTerritoryList;

	@FXML
	private TextField txtCommandLine;

	@FXML
	private TextField txtMoveNumArmy;

	@FXML
	private ComboBox<?> cbTerritoryList;

	@FXML
	private TextArea txtConsoleLog;

	@FXML
	private Button btnExit;

	@FXML
	void placeReinforcement(ActionEvent event) {

	}

	@FXML
	void attack(ActionEvent event) {

	}

	@FXML
	void fortify(ActionEvent event) {

	}

	@FXML
	void endTurn(ActionEvent event) {

	}

	@FXML
	void fireCommand(ActionEvent event) {

	}
	@Lazy
	@Autowired
	private StageManager stageManager;
	@FXML
	void exitGame(ActionEvent event) {
		stageManager.switchScene(FxmlView.WELCOME);
	}

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
