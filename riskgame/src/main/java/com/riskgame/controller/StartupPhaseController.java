/**
 * 
 */
package com.riskgame.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.riskgame.config.StageManager;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.PlayerHandlerImpl;
import com.riskgame.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

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
	private ComboBox<String> comboBoxchosenMap;

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
	private TextField playerNameText;

	@FXML
	private TableView<Player> playertable;

	@FXML
	private TableColumn<Player, Integer> id;

	@FXML
	private TableColumn<Player, String> name;

	@FXML
	private TableColumn<Player, Integer> army;

	@FXML
	private TableColumn<Player, Boolean> view;

	@FXML
	private Button btnAddPlayer;

	private static int playerId = 1;

	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private MapManagementImpl mapManagementImpl;
	
	@Autowired
	private PlayerHandlerImpl playerHandlerImpl;

	private ObservableList<Player> playerList = FXCollections.observableArrayList();
	private ObservableList<String> mapComboValue = FXCollections.observableArrayList();
	
	private GamePlayPhase globGamePlayPhase;
	
	@FXML
    private Button btnReset;
	
	private String mapFileName;
	
	public static final String NEUTRAL = "NEUTRAL";
	
	public boolean startGame = false;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		playertable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		setPlayerTableColumnProperties();
		playerList.clear();
		loadPlayerDetails();
		
		mapComboValue.clear();
		List<String> mapNameList = mapManagementImpl.getAvailableMap();
		mapComboValue.addAll(mapNameList);
		comboBoxchosenMap.setItems(mapComboValue);
		
		startGame = false;
		mapFileName = "";

	}

	private void setPlayerTableColumnProperties() {

		id.setCellValueFactory(new PropertyValueFactory<>("playerId"));
		name.setCellValueFactory(new PropertyValueFactory<>("playerName"));
		army.setCellValueFactory(new PropertyValueFactory<>("armyOwns"));
		view.setCellFactory(playerViewCellFactory);

	}

	Callback<TableColumn<Player, Boolean>, TableCell<Player, Boolean>> playerViewCellFactory = new Callback<TableColumn<Player, Boolean>, TableCell<Player, Boolean>>() {
		@Override
		public TableCell<Player, Boolean> call(final TableColumn<Player, Boolean> param) {
			final TableCell<Player, Boolean> cell = new TableCell<Player, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Player player = getTableView().getItems().get(getIndex());
							viewPlayer(player);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void viewPlayer(Player player) {

					List<PlayerTerritory> ptList = player.getPlayerterritories();
					StringBuilder builder = new StringBuilder();
					for (PlayerTerritory pTerritory : ptList) {
						if(pTerritory != null) {
							builder.append("Continent:=> "+pTerritory.getContinentName()).append(" Territory:=> "+pTerritory.getTerritoryName()).append(" Army:=> "+pTerritory.getArmyOnterritory()).append(System.getProperty("line.separator"));
						}
					}
					txtConsoleLog.clear();
					txtConsoleLog.setText(builder.toString());
					
					
				}
			};
			return cell;
		}
	};

	private void loadPlayerDetails() {
		playertable.setItems(playerList);
	}

	@FXML
	void startGame(ActionEvent event) throws IOException {
		
		if(startGame) {
			
			
			GamePlayPhase gamePlayPhase = new GamePlayPhase();
			gamePlayPhase.setGameState(playerList);
			gamePlayPhase.setGamePhase("Startup");
			if(mapFileName != null && !mapFileName.isEmpty()) {
				gamePlayPhase.setFileName(mapFileName);
			}else {
				gamePlayPhase.setFileName(comboBoxchosenMap.getSelectionModel().getSelectedItem());
			}
			
			stageManager.switchScene(FxmlView.PLAYGAME,gamePlayPhase);
			
			
		}else {
			alertMesage("Please finish startup phase");
		}
		
	}

	@FXML
	void backToMainPage(ActionEvent event) {
		stageManager.switchScene(FxmlView.MAP,null);
	}

	@FXML
	void fireCommand(ActionEvent event) {

		txtConsoleLog.clear();
		String message = "";
		try {
			String command = txtCommandLine.getText();
			if (command != null && !command.isEmpty()) {

				if (command.startsWith("showmap")) {
					GamePlayPhase gamePlayPhase = new GamePlayPhase();
					gamePlayPhase.setGameState(playerList);
					gamePlayPhase.setGamePhase("Startup");
					gamePlayPhase.setFileName(mapFileName);
					txtConsoleLog.setText(gamePlayPhase.toString());
					
				} else if (command.startsWith("loadmap")) {

					txtConsoleLog.setText(commandloadMapCommand(command));
										
				} else if (command.startsWith("gameplayer")) {
					txtConsoleLog.setText(commandGamePlayer(command));
				} else if (command.startsWith("populatecountries")) {
					txtConsoleLog.setText(commandPopulateCountries());
				} else if (command.startsWith("placearmy")) {
					
				} else if (command.startsWith("placeall")) {
					
					if(globGamePlayPhase != null) {
						
						commonPlaceAllArmy();
						txtConsoleLog.setText("Armies are assigned to players. Please click on view column on usertable");
						
					}else {
						txtConsoleLog.setText("Please fire populatecountries command first");
					}
					
					
				} else {
					txtConsoleLog.setText("Please enter valid command");
				}

			} else {
				txtConsoleLog.setText("Please enter valid command");
			}
		} catch (Exception e) {
			e.printStackTrace();
			txtConsoleLog.setText("Please enter valid command");
		}

	}



	private String commandloadMapCommand(String commandLine) {
		String result = "";
		List<String> command = Arrays.asList(commandLine.split(" "));
		String fileName = command.get(1);

		List<String> mapNameList = mapManagementImpl.getAvailableMap();

		if (mapNameList.contains(fileName.toLowerCase() + ".map")) {

			//RiskMap map = mapManagementImpl.readMap(fileName + ".map");
			//result = map.toString();
			result = fileName + " map loaded successfully!";
			mapFileName = fileName+".map";

		} else {
			result = "Map not found in system Please enter valid name";

		}

		return result;
	}

	private String commandGamePlayer(String commandLine) {

		StringBuilder result = new StringBuilder();
		String playerName = "";
		List<String> command = Arrays.asList(commandLine.split(" "));

		for (int i = 0; i < command.size(); i++) {

			if (command.get(i).equalsIgnoreCase("-add")) {
				playerName = command.get(i + 1);
				String message = "-add " + playerName + " :=> ";
				if (validateInput(playerName, "[a-zA-Z]+")) {
					if(!playerName.equalsIgnoreCase(NEUTRAL)) {
						boolean isvalidName = true;
						for (Player player : playerList) {
							if (player != null && playerName.equalsIgnoreCase(player.getPlayerName())) {
								result.append(message + playerName + " player name already exists")
										.append(System.getProperty("line.separator"));
								isvalidName = false;
								break;
							}
						}
						if (isvalidName) {
							if(playerList.size()<=5) {
								saveCommonPlayer(playerName);
								result.append(message + playerName + " player saved successfully")
										.append(System.getProperty("line.separator"));
							}else {
								result.append(message + playerName + " Max 6 Playes are allowed in the game")
								.append(System.getProperty("line.separator"));
							}
							
						}
					}else {
						result.append(message +playerName+ " playername not allowed. This is system generated user")
						.append(System.getProperty("line.separator"));
					}
					
					

				} else {
					result.append(message + "Please enter valid PlayerName")
							.append(System.getProperty("line.separator"));
				}

			} else if (command.get(i).equalsIgnoreCase("-remove")) {
				playerName = command.get(i + 1);
				String message = "-remove " + playerName + " :=> ";
				if (validateInput(playerName, "[a-zA-Z]+")) {

					if (deleteCommonplayer(playerName)) {
						result.append(message + playerName + " player removed successfully")
								.append(System.getProperty("line.separator"));
					} else {
						result.append(message + playerName + " player not found")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + playerName + " Please enter valid player Name")
							.append(System.getProperty("line.separator"));
				}
			}

		}

		return result.toString();

	}



	@FXML
	void addPlayer(ActionEvent event) {

		if (validate("Player Name", playerNameText.getText(), "[a-zA-Z]+")
				&& isValidPlayerName(playerNameText.getText())) {
			if(!playerNameText.getText().equalsIgnoreCase(NEUTRAL)) {
					
				if(playerList.size()<=5) {
					saveCommonPlayer(playerNameText.getText());
					alertMesage("Player saved successfully");
					clearPlayerFields();
				}else {
					alertMesage("Max 6 Playes are allowed in the game");
				}
				
				
			}else {
				alertMesage(NEUTRAL+ " name not allowed. this is system generated user");
			}
			
		}

	}

	private void saveCommonPlayer(String name) {
		Player player = new Player();
		player.setPlayerId(playerId);
		playerId++;
		player.setPlayerName(name);
		player.setArmyOwns(0);
		playerList.add(player);
		loadPlayerDetails();
	}

	private void clearPlayerFields() {
		playerNameText.clear();

	}

	private boolean isValidPlayerName(String playerName) {
		boolean isValid = true;

		for (Player player : playerList) {

			// for create
			if (playerName.equalsIgnoreCase(player.getPlayerName())) {
				alertMesage("Player name already exists");
				isValid = false;
				break;

			}

		}

		return isValid;
	}

	private

	@FXML void deletePlayer(ActionEvent event) {

		List<Player> playerList = playertable.getSelectionModel().getSelectedItems();
		Player player = playerList.get(0);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {

			deleteCommonplayer(player.getPlayerName());
			clearPlayerFields();
			alertMesage("Player deleted successfully");

		}

	}

	private boolean deleteCommonplayer(String playerName) {
		boolean result = false;

		for (int i = 0; i < playerList.size(); i++) {

			if (playerList.get(i).getPlayerName().equalsIgnoreCase(playerName)) {
				playerList.remove(i);
				result = true;
				break;
			}
			if (result) {
				loadPlayerDetails();
				clearPlayerFields();
			}

		}
		return result;
	}

	/*
	 * Validations
	 */
	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	/*
	 * Validations
	 */
	private boolean validateInput(String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void alertMesage(String alertMessage) {

		Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("Saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(alertMessage);
		alert.showAndWait();
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (empty)
			alert.setContentText("Please Select or Enter valid " + field);
		else
			alert.setContentText("Please Select or Enter Valid " + field);

		alert.showAndWait();
	}
	
	@FXML
	void populateCountries(ActionEvent event) {
		
		

		if(emptyValidation("Map", comboBoxchosenMap.getSelectionModel().getSelectedItem() == null)) {
			
			if(playerList.size()>=2) {
				
				
				commonPopulateCountries();
				
				
				alertMesage("All countries are randomly assigned to players. Please click on view column on usertable");
				
				
			}else {
				alertMesage("Atleast 2 pleayers are requiredd for the game");
			}
			
		}else {
			//alertMesage("Please select map first");
		}
		
	}
	
	private void commonPopulateCountries() {
		
		if(playerList.size()==2 && !playerList.stream().anyMatch(p->p.getPlayerName().equalsIgnoreCase(NEUTRAL)) ){
			
			Player player = new Player();
			player.setPlayerId(playerId);
			player.setPlayerName(NEUTRAL);
			player.setArmyOwns(0);
			playerList.add(player);
			
		}
		
		GamePlayPhase playPhase = new GamePlayPhase();
		
		playerList.forEach(player->player.getPlayerterritories().clear());
		
		playPhase.setGameState(playerList);
		
		if(mapFileName != null && !mapFileName.isEmpty()) {
			playPhase.setFileName(mapFileName);
		}else {
			playPhase.setFileName(comboBoxchosenMap.getSelectionModel().getSelectedItem());
		}
		
		playPhase = playerHandlerImpl.populateTerritoriesByRoundRobbin(playPhase);
		
		playerList = FXCollections.observableArrayList();
		playerList.addAll(playPhase.getGameState());
		playertable.getItems().clear();
		playertable.setItems(playerList);
		playerId = playerList.size()+1;
		
		globGamePlayPhase = playPhase;
		
		
	}
	
	private String commandPopulateCountries() {
		String result = "";
		
		if(mapFileName != null && !mapFileName.isEmpty()) {
			if(playerList.size()>=2) {
				
				commonPopulateCountries();
				result = "All countries are randomly assigned to players. Please fire showmap command to view";
				
			}else {
				result = "Atleast 2 pleayers are requiredd for the game please add players";
			}
		}else {
			result = "Please fire loadmap command first to load your map";
		}
		
		return result;
	}
	
	@FXML
	void placeAllArmy(ActionEvent event) {

		if(globGamePlayPhase != null) {
			
		
			commonPlaceAllArmy();
			
			comboBoxchosenMap.setDisable(true);
			playerNameText.setDisable(true);
			btnAddPlayer.setDisable(true);
			btnPopulatecountry.setDisable(true);
			btnPlaceAll.setDisable(true);
			
			
			
			alertMesage("Armies are assigned to players. Please click on view column on usertable");
			
			
			
			
		}else {
			alertMesage("Please click on populate country first");
		}
		
	}
	
	private void commonPlaceAllArmy() {
		
		GamePlayPhase playPhase = new GamePlayPhase();
		
		//playerList.forEach(player->player.getPlayerterritories().clear());
		
		playPhase.setGameState(playerList);
		if(mapFileName != null && !mapFileName.isEmpty()) {
			playPhase.setFileName(mapFileName);
		}else {
			playPhase.setFileName(comboBoxchosenMap.getSelectionModel().getSelectedItem());
		}
		
		playPhase = playerHandlerImpl.placeAll(playPhase);
		
		playerList = FXCollections.observableArrayList();
		playerList.addAll(playPhase.getGameState());
		playertable.getItems().clear();
		playertable.setItems(playerList);
		playerId = playerList.size()+1;
		
		startGame = true;
		
	}
	
	@FXML
    void btnReset(ActionEvent event) {

		
		comboBoxchosenMap.setDisable(false);
		playerNameText.setDisable(false);
		btnAddPlayer.setDisable(false);
		btnPopulatecountry.setDisable(false);
		btnPlaceAll.setDisable(false);
		
		mapFileName = "";
		
		playerList.clear();
		loadPlayerDetails();
		
		
		
    }


}
