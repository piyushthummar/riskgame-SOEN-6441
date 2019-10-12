package com.riskgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.riskgame.config.StageManager;
import com.riskgame.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@Controller
public class WelcomeController {

	@FXML
	private Button btnEditMap;

	@FXML
	private Button btnCreateMap;

	@FXML
	private Button btnExit;

	@FXML
	void createMap(ActionEvent event) {
		stageManager.switchScene(FxmlView.MAP);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	void editMap(ActionEvent event) {
	}

	@FXML
	void exitGame(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

}
