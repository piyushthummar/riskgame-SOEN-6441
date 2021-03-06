package com.riskgame.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.riskgame.adapter.DominationToConquestAdapter;
import com.riskgame.config.StageManager;
import com.riskgame.constant.ComputerStrategy;
import com.riskgame.constant.HumanStrategy;
import com.riskgame.constant.StrategyType;
import com.riskgame.model.Continent;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;
import com.riskgame.service.ConquestMapInterface;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.Impl.ConquestMapImpl;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.PlayerHandlerImpl;
import com.riskgame.strategy.StrategyInterface;
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
 * This class is a controller for the screen which used to choose map, add
 * player, populate countries, place army and command for startup phase. It'll
 * redirect user to main playscreen. This screen got redirected from welcome
 * screen and map create screen both side.
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
	private Button btnTournament;

	@FXML
	private Button btnLoadGame;

	@FXML
	private ComboBox<String> comboBoxchosenMap;

	@FXML
	private ComboBox<String> comboBoxSelectStrategy;

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

	/**
	 * Initialization of state manager
	 */
	@Lazy
	@Autowired
	private StageManager stageManager;

	/**
	 * Initialization of MapManagementImpl
	 */
	@Autowired
	private MapManagementImpl mapManagementImpl;

	@Autowired
	private PlayerHandlerImpl playerHandlerImpl;

	private ObservableList<Player> playerList = FXCollections.observableArrayList();
	private ObservableList<String> mapComboValue = FXCollections.observableArrayList();
	private ObservableList<String> listOfStrategy = FXCollections.observableArrayList();

	private GamePlayPhase globGamePlayPhase;

	@FXML
	private Button btnReset;

	private String mapFileName;

	// public static final String NEUTRAL = "NEUTRAL";

	public boolean startGame = false;

	public boolean placeAll = false;

	private static int playerIndex = -1;

	private String playerName = "";
	private String currentPlayerStrategyName = "";
	private int playerTotalArmies = 0;

	private static String turnStartedMsg = "";
	private static String leftArmyToPlaceMsg = "";
	private static String NEWLINE = System.getProperty("line.separator");
	private StringBuilder sb;
	private static StrategyType[] strategyArray = StrategyType.values();
	private boolean ispopulate = false;
	RiskMap map = null;

	/**
	 * On action method for tournament start button
	 * 
	 * @param event
	 */
	@FXML
	void btnTournament(ActionEvent event) {

		GamePlayPhase gamePlayPhase = new GamePlayPhase();
		gamePlayPhase.setAction("starttournament");

		stageManager.switchScene(FxmlView.PLAYGAME, gamePlayPhase);

	}

	/**
	 * On action method for the button load previous game
	 * 
	 * @param event
	 */
	@FXML
	void btnLoadGame(ActionEvent event) {
		GamePlayPhase gamePlayPhase = new GamePlayPhase();
		gamePlayPhase.setAction("loadpreviousgame");

		stageManager.switchScene(FxmlView.PLAYGAME, gamePlayPhase);
	}

	/**
	 * This method will use to initialize the controller of this class
	 * 
	 * @param location  of the FXML file
	 * @param resources is properties information
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		playertable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		setPlayerTableColumnProperties();
		playerList.clear();
		loadPlayerDetails();

		sb = new StringBuilder();

		mapComboValue.clear();
		listOfStrategy.clear();
		List<String> mapNameList = mapManagementImpl.getAvailableMap();
		List<String> stringListforstrategy = Arrays.asList(StrategyType.values()).stream()
				.map(e -> e.toString().toLowerCase()).collect(Collectors.toList());
		mapComboValue.addAll(mapNameList);
		listOfStrategy.addAll(stringListforstrategy);
		comboBoxchosenMap.setItems(mapComboValue);
		comboBoxSelectStrategy.setItems(listOfStrategy);
		startGame = false;
		mapFileName = "";
		placeAll = false;

	}

	/**
	 * This method will set Column name for player table
	 */
	private void setPlayerTableColumnProperties() {

		id.setCellValueFactory(new PropertyValueFactory<>("playerId"));
		name.setCellValueFactory(new PropertyValueFactory<>("playerName"));
		army.setCellValueFactory(new PropertyValueFactory<>("armyOwns"));
		view.setCellFactory(playerViewCellFactory);

	}

	/**
	 * This method will return selected value from the player table to console log
	 */
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
						if (pTerritory != null) {
							builder.append("Continent:=> " + pTerritory.getContinentName())
									.append(" Territory:=> " + pTerritory.getTerritoryName())
									.append(" Army:=> " + pTerritory.getArmyOnterritory())
									.append(System.getProperty("line.separator"));
						}
					}
					txtConsoleLog.clear();
					txtConsoleLog.setText(builder.toString());
				}
			};
			return cell;
		}
	};

	/**
	 * This method will load the details of the player
	 */
	private void loadPlayerDetails() {
		playertable.setItems(playerList);
	}

	/**
	 * This method will start the game and redirect to playgame screen
	 * 
	 * @param event will represents value sent from view
	 * @throws IOException
	 */
	@FXML
	void startGame(ActionEvent event) throws IOException {

		if (startGame) {
			GamePlayPhase gamePlayPhase = new GamePlayPhase();
			gamePlayPhase.setPlayerList(playerList);
			gamePlayPhase.setGamePhase("Startup");
			gamePlayPhase.setAction("startgame");
			if (mapFileName != null && !mapFileName.isEmpty()) {
				gamePlayPhase.setFileName(mapFileName);
			} else {
				gamePlayPhase.setFileName(comboBoxchosenMap.getSelectionModel().getSelectedItem());
			}

			stageManager.switchScene(FxmlView.PLAYGAME, gamePlayPhase);
		} else {
			alertMesage("Please finish startup phase");
		}
	}

	/**
	 * This method will redirect user to welcome screen
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void backToMainPage(ActionEvent event) {
		stageManager.switchScene(FxmlView.MAP, null);
	}

	/**
	 * This method represent fire command button onAction where all commands got
	 * separated and sent it to respective methods.
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void fireCommand(ActionEvent event) {

		txtConsoleLog.clear();
		String message = "";
		try {
			String command = txtCommandLine.getText();
			if (command != null && !command.isEmpty()) {

				if (command.startsWith("showmap")) {
					GamePlayPhase gamePlayPhase = new GamePlayPhase();
					gamePlayPhase.setPlayerList(playerList);
					gamePlayPhase.setGamePhase("Startup");
					gamePlayPhase.setFileName(mapFileName);
					gamePlayPhase.setRiskMap(map);
					if (gamePlayPhase.getPlayerList().isEmpty()) {
						txtConsoleLog
								.setText("Please add players first to see Continent, countries, ownership and army");
					} else {
						txtConsoleLog.setText(gamePlayPhase.toString());
					}
					System.out.println(gamePlayPhase);
				} else if (command.startsWith("loadmap")) {

					txtConsoleLog.setText(commandloadMapCommand(command));

				} else if (command.startsWith("gameplayer")) {
					txtConsoleLog.setText(commandGamePlayer(command));
				} else if (command.startsWith("populatecountries")) {
					if (!ispopulate) {
						txtConsoleLog.setText(commandPopulateCountries());
						if (ispopulate) {
							txtConsoleLog.appendText(changeUserTurn());
						} else {
							txtConsoleLog.setText(
									"Total players should be less or equal to total countries, Please remove player or load different map");
						}

					} else {
						txtConsoleLog.setText("Populate Countries is done. Go for Placearmy");
					}
				} else if (command.startsWith("placearmy")) {

					if (!placeAll) {
						if (globGamePlayPhase != null) {

							txtConsoleLog.setText(placeArmyInRoundRobin(command));
						} else {
							txtConsoleLog.setText("Please fire populatecountries command first");
						}
					} else {
						txtConsoleLog.setText("Placeall command previously executed. Army already assigned");
					}
				} else if (command.startsWith("placeall")) {

					if (globGamePlayPhase != null) {

						commonPlaceAllArmy();
						txtConsoleLog
								.setText("Armies are assigned to players. Please click on view column on usertable");

					} else {
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

	/**
	 * This is roundrobin algorithm to placearmy in turn.
	 * 
	 * @param commandLine
	 * @return message string to display in console
	 */
	private String placeArmyInRoundRobin(String commandLine) {

		List<String> command = Arrays.asList(commandLine.split(" "));
		String countryName = command.get(1);
		PlayerTerritory pTerritory = null;
		String result = null;
		int totalArmyOfPlayer = 0;

		Player player = playerList.get(playerIndex);
		totalArmyOfPlayer = playerList.get(playerIndex).getArmyOwns();
		List<PlayerTerritory> ptList = player.getPlayerterritories();
		for (PlayerTerritory playerTerritory : ptList) {

			if (playerTerritory.getTerritoryName().equalsIgnoreCase(countryName)) {

				pTerritory = playerTerritory;
			}
		}
		if (pTerritory != null && totalArmyOfPlayer > 0) {

			int army = pTerritory.getArmyOnterritory();
			pTerritory.setArmyOnterritory(army + 1);
			player.setArmyOwns(totalArmyOfPlayer - 1);
			loadPlayerDetails();
			result = "Army Successfully Assign to " + countryName;
			result = result + "\n" + playerName + "'s turn ended.";
			result = result + "\n" + changeUserTurn();
		} else {
			result = "Country not found. Please provide valid country";
		}
		return result;
	}

	/**
	 * This method will change user's turn for placearmy country name command to
	 * placearmy in roundrobin fashion
	 * 
	 * @return message to change user's turn
	 */
	private String changeUserTurn() {

		if (playerIndex < playerList.size() - 1) {
			playerIndex++;
		} else {
			playerIndex = 0;
		}

		txtCommandLine.clear();
		txtConsoleLog.clear();
		playerName = playerList.get(playerIndex).getPlayerName();
		playerTotalArmies = playerList.get(playerIndex).getArmyOwns();
		turnStartedMsg = playerName + "'s turn is started";
		leftArmyToPlaceMsg = playerName + " is left with total " + playerTotalArmies + " armies to place.";
		sb.delete(0, sb.length());
		sb.append(turnStartedMsg).append(NEWLINE).append(leftArmyToPlaceMsg).append(NEWLINE);
		return sb.toString();

	}

	/**
	 * This method will load the map and give the appropriate message string
	 * 
	 * @param commandLine is the loadMap command given from user
	 * @return proper message of result after LoadMap command
	 */
	private String commandloadMapCommand(String commandLine) {
		String result = "";
		List<String> command = Arrays.asList(commandLine.split(" "));
		String fileName = command.get(1);
		List<String> mapNameList = mapManagementImpl.getAvailableMap();
		if (mapNameList.contains(fileName + ".map")) {

			if (mapManagementImpl.isMapConquest(fileName + ".map")) {
				ConquestMapInterface conquestMapInterface = new ConquestMapImpl();
				MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestMapInterface);
				map = mapInterface.readMap(fileName + ".map");
			} else {
				map = mapManagementImpl.readMap(fileName + ".map");
			}
			boolean validMap = mapManagementImpl.validateMap(map);
			if (validMap) {
				result = fileName + " map loaded successfully!";
				mapFileName = fileName + ".map";
			} else {
				result = "Map validation fail. Invalid map loaded. Please correct map";
			}
		} else {
			result = "Map not found in system Please enter valid name";

		}
		return result;
	}

	/**
	 * This method will add player in the game
	 * 
	 * @param commandLine is the command given from user to add user
	 * @return proper message of result after player addition
	 */
	private String commandGamePlayer(String commandLine) {

		StringBuilder result = new StringBuilder();
		String playerName = "";
		List<String> command = Arrays.asList(commandLine.split(" "));
		String strategy = "";
		for (int i = 0; i < command.size(); i++) {
			if (command.get(i).equalsIgnoreCase("-add")) {
				playerName = command.get(i + 1);
				strategy = command.get(i + 2);
				String message = "-add " + playerName + " :=> ";
				if (validateInput(playerName, "[a-zA-Z]+") && ValidateStrategy(strategy)) {
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
						if (playerList.size() <= 5) {

							saveCommonPlayer(playerName, strategy);
							result.append(message + playerName + " player saved successfully")
									.append(System.getProperty("line.separator"));
						} else {
							result.append(message + playerName + " Max 6 Playes are allowed in the game")
									.append(System.getProperty("line.separator"));
						}
					}
				} else {
					result.append(message + "Please enter valid PlayerName or strategy Name")
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

	/**
	 * This method will check if given strategy by command from user is corret or
	 * not.
	 * 
	 * @param strategy
	 * @return true if stratgy is correct
	 */
	private boolean ValidateStrategy(String strategy) {
		if (validateInput(strategy, "[a-zA-Z]+")) {
			List<StrategyType> list = Arrays.asList(strategyArray);
			List<String> stringlist = list.stream().map(e -> e.toString().toLowerCase()).collect(Collectors.toList());
			if (stringlist.contains(strategy.toLowerCase())) {
				return true;
			} else
				return false;
		}
		return false;

	}

	/**
	 * This is onaction method for select strategy combo box
	 * 
	 * @param event
	 */
	@FXML
	void OnActionSelectStrategy(ActionEvent event) {
		currentPlayerStrategyName = comboBoxSelectStrategy.getSelectionModel().getSelectedItem();
	}

	/**
	 * This is onAction method of button addPlayer of GUI. It'll add player
	 * according to the rules
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void addPlayer(ActionEvent event) {

		if (validate("Player Name", playerNameText.getText(), "[a-zA-Z]+")
				&& isValidPlayerName(playerNameText.getText())) {

			if (playerList.size() <= 5) {
				saveCommonPlayer(playerNameText.getText(), currentPlayerStrategyName);
				alertMesage("Player saved successfully");
				clearPlayerFields();
			} else {
				alertMesage("Max 6 Playes are allowed in the game");
			}
		}
	}

	/**
	 * This is the common method for both GUI and command line to save player in the
	 * game
	 * 
	 * @param name     of the player
	 * @param strategy
	 */
	private void saveCommonPlayer(String name, String strategy) {
		Player player = new Player();
		player.setPlayerId(playerId);
		playerId++;
		player.setPlayerName(name);
		player.setArmyOwns(0);
		if (strategy.equalsIgnoreCase(HumanStrategy.HUMAN.toString())) {
			player.setPlayerType(HumanStrategy.HUMAN.toString());
		} else {
			player.setPlayerType(ComputerStrategy.COMPUTER.toString());
		}
		player.setStrategyName(strategy);

		player.setStrategy((StrategyInterface) playerHandlerImpl.getStrategyByName(strategy));

		playerList.add(player);
		loadPlayerDetails();
		System.out.println(player);
	}

	/**
	 * This method will clear add player field after first user
	 */
	private void clearPlayerFields() {
		playerNameText.clear();

	}

	/**
	 * This method validates the name of player i.e. error if already exists
	 * 
	 * @param playerName
	 * @return true if validation is correct
	 */
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

	/**
	 * This method will delete player from the game
	 * 
	 * @param event will represents value sent from view
	 */
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

	/**
	 * This is the common method for both GUI and command line fro delete player
	 * 
	 * @param playerName
	 * @return true is player got deleted successfully
	 */
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

	/**
	 * This is the common method for controller to validate all field passed in it
	 * 
	 * @param field   is the name you want to print in alert box
	 * @param value   is the string you want to validate
	 * @param pattern is regex
	 * @return true is validation got succeed
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

	/**
	 * This method will validate all types of input given by user through
	 * commandLine or GUI
	 * 
	 * @param value   is a string to be matched
	 * @param pattern is a regex
	 * @return
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

	/**
	 * This method will give alertBox in some operation when needed
	 * 
	 * @param alertMessage is message you want to give in alertBox
	 */
	private void alertMesage(String alertMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("Saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(alertMessage);
		alert.showAndWait();
	}

	/**
	 * This method will return true if field is empty and false if not.
	 * 
	 * @param field is a string to be validated
	 * @param empty false if not empty
	 * @return
	 */
	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	/**
	 * This method will be used to give alert message at the time of validation
	 * 
	 * @param field is a string you want to print in alert message
	 * @param empty will return true check if alertContext is settled.
	 */
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

	/**
	 * This method will assign countries to the user in random fashion
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void populateCountries(ActionEvent event) {
		if (emptyValidation("Map", comboBoxchosenMap.getSelectionModel().getSelectedItem() == null)) {
			if (playerList.size() >= 2) {

				RiskMap map = mapManagementImpl.readMap(comboBoxchosenMap.getSelectionModel().getSelectedItem());
				boolean validMap = mapManagementImpl.validateMap(map);

				if (validMap) {

					commonPopulateCountries();
					alertMesage(
							"All countries are randomly assigned to players. Please click on view column on usertable");
				} else {
					alertMesage("Map validation fail. Invalid map selected. Please select validmap");
				}

			} else {
				alertMesage("Atleast 2 pleayers are requiredd for the game");
			}
		} else {
			// alertMesage("Please select map first");
		}
	}

	/**
	 * This is a common method for both GUI and command line to populate countries
	 * to user
	 */
	private void commonPopulateCountries() {

		GamePlayPhase playPhase = new GamePlayPhase();

		playerList.forEach(player -> player.getPlayerterritories().clear());

		playPhase.setPlayerList(playerList);

		if (mapFileName != null && !mapFileName.isEmpty()) {
			playPhase.setFileName(mapFileName);
		} else {
			playPhase.setFileName(comboBoxchosenMap.getSelectionModel().getSelectedItem());
		}

		playPhase = playerHandlerImpl.populateTerritoriesRandomly(playPhase);

		playerList = FXCollections.observableArrayList();
		playerList.addAll(playPhase.getPlayerList());
		playertable.getItems().clear();
		playertable.setItems(playerList);
		playerId = playerList.size() + 1;
		globGamePlayPhase = playPhase;
	}

	/**
	 * It will return total countries in given map
	 * 
	 * @param riskMap
	 * @return total countries
	 */
	private static int getTotalCountries(RiskMap r) {
		Map<Integer, Continent> continentMap = r.getContinents();
		Iterator<Entry<Integer, Continent>> i = continentMap.entrySet().iterator();
		int totalCountries = 0;
		while (i.hasNext()) {
			Entry<Integer, Continent> e = i.next();
			Continent c = e.getValue();
			totalCountries += c.getTerritoryList().size();
		}
		return totalCountries;
	}

	/**
	 * @return string message of appropriate result to above method where this got
	 *         called
	 */
	private String commandPopulateCountries() {
		String result = "";
		int countries = getTotalCountries(map);
		System.out.println(playerList.size());
		System.out.println(countries);
		if (mapFileName != null && !mapFileName.isEmpty()) {
			if (playerList.size() <= countries) {
				if (playerList.size() >= 2) {
					commonPopulateCountries();
					ispopulate = true;
					result = "All countries are randomly assigned to players. Please fire showmap command to view";
				} else {
					result = "Atleast 2 pleayers are requiredd for the game please add players";
				}
			} else {
				result = "Total players should be less or equal to total countries";
			}
		} else {
			result = "Please fire loadmap command first to load your map";
		}
		return result;
	}

	/**
	 * This method will place initial army to all player created in the game at once
	 * 
	 * @param even will represents value sent from view
	 */
	@FXML
	void placeAllArmy(ActionEvent event) {

		if (globGamePlayPhase != null) {
			commonPlaceAllArmy();
			comboBoxchosenMap.setDisable(true);
			comboBoxSelectStrategy.setDisable(true);
			playerNameText.setDisable(true);
			btnAddPlayer.setDisable(true);
			btnPopulatecountry.setDisable(true);
			btnPlaceAll.setDisable(true);
			alertMesage("Armies are assigned to players. Please click on view column on usertable");
		} else {
			alertMesage("Please click on populate country first");
		}
	}

	/**
	 * This is the common method for both GUI and commandLine for place all army at
	 * once.
	 */
	private void commonPlaceAllArmy() {

		for (Player player : playerList) {

			List<PlayerTerritory> ptList = player.getPlayerterritories();
			for (PlayerTerritory playerTerritory : ptList) {
				playerTerritory.setArmyOnterritory(0);

			}
		}

		GamePlayPhase playPhase = new GamePlayPhase();
		playPhase.setPlayerList(playerList);
		if (mapFileName != null && !mapFileName.isEmpty()) {
			playPhase.setFileName(mapFileName);
		} else {
			playPhase.setFileName(comboBoxchosenMap.getSelectionModel().getSelectedItem());
		}
		playPhase = playerHandlerImpl.placeAllArmyByRoundRobin(playPhase);
		playerList = FXCollections.observableArrayList();
		playerList.addAll(playPhase.getPlayerList());
		playertable.getItems().clear();
		playertable.setItems(playerList);
		playerId = playerList.size() + 1;
		startGame = true;
		placeAll = true;

	}

	/**
	 * This method will reset all buttons and fields once it got clicked
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void btnReset(ActionEvent event) {
		comboBoxchosenMap.setDisable(false);
		playerNameText.setDisable(false);
		comboBoxSelectStrategy.setDisable(false);
		btnAddPlayer.setDisable(false);
		btnPopulatecountry.setDisable(false);
		btnPlaceAll.setDisable(false);
		mapFileName = null;
		playerList.clear();
		playerId = 1;
		loadPlayerDetails();
		txtConsoleLog.clear();
		playerIndex = -1;
		ispopulate = false;
		map = null;
	}
}
