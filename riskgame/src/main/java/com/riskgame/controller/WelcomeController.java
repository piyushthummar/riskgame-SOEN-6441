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

/**
 * This is the main file 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Controller
public class WelcomeController implements Initializable {

	@FXML
	private Button btnPlayGame;

	@FXML
	private Button btnCreateMap;

	@FXML
	private Button btnExit;

	/**
	 * This method will redirect player to create map, where player can create and edit map
	 * @param event will represents value sent from view
	 */
	@FXML
	void createMap(ActionEvent event) {
		stageManager.switchScene(FxmlView.MAP,null);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

    /**
     * This method will redirect the player to startUp phase screen
     * @param event will represents value sent from view
     */
    @FXML
    void playGame(ActionEvent event) {
    		stageManager.switchScene(FxmlView.STARTUPPHASE,null);
    }

	/**
	 * This method will exit the game and close the stage
	 * @param event will represents value sent from view
	 */
	@FXML
	void exitGame(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

	/**
	 * This is the initialization method of this controller
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
