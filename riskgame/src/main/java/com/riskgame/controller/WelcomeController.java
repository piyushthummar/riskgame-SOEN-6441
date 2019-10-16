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
import javafx.stage.Stage;

@Controller
public class WelcomeController implements Initializable {

	@FXML
	private Button btnPlayGame;

	@FXML
	private Button btnCreateMap;

	@FXML
	private Button btnExit;

	@FXML
	void createMap(ActionEvent event) {
		stageManager.switchScene(FxmlView.MAP,null);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

    @FXML
    void playGame(ActionEvent event) {
    		stageManager.switchScene(FxmlView.STARTUPPHASE,null);
    }

	@FXML
	void exitGame(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
