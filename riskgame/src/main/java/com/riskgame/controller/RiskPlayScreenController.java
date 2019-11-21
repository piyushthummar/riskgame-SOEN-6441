package com.riskgame.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.riskgame.config.StageManager;
import com.riskgame.model.Continent;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.model.RiskMap;
import com.riskgame.observerpattern.Observer;
import com.riskgame.observerpattern.Subject;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.RiskPlayImpl;
import com.riskgame.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * This a game playing screen controller, where things like Reinforcement,
 * attack and fortification managed and player can play the game through command
 * line also. This screen got redirected from startup phase controller.
 * 
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 * @see com.riskgame.controller.StartupPhaseController
 * @see com.riskgame.model.Continent
 * @see com.riskgame.model.RiskMap
 * 
 */
@Controller
public class RiskPlayScreenController extends Observer implements Initializable {

	GamePlayPhase gameplayphase = new GamePlayPhase();

	@FXML
	private Button btnFireCommand;

	@FXML
	private TextArea txtPhaseView;

	@FXML
	private TextArea txtPlayerDominationView;

	@FXML
	private TextArea txtCardExchangeView;

	@FXML
	private TextField txtCommandLine;

	@FXML
	private TextArea txtConsoleLog;

	@FXML
	private Button btnExit;

	@FXML
	private TextArea territoryArea;

	@FXML
	private TextArea adjacentTerritoryArea;

	@FXML
	private TextArea currentLog;

	@FXML
	private TextArea phaseviewLog;

	@Autowired
	public MapManagementImpl mapManagementImpl;

	@Autowired
	public RiskPlayImpl riskPlayImpl;

	@Lazy
	@Autowired
	private StageManager stageManager;

	// private GamePlayPhase gamePlayPhase;

	private RiskMap riskMap;

	private StringBuilder sb;

	private int playerIndex = 0;
	private String playerName = "";
	// private int playerLeftArmy = 0;
	//private int playerReinforceArmy = 0;

	private static String turnStartedMsg = "";
	private static String leftArmyMsg = "";

	public static final String NEUTRAL = "NEUTRAL";
	private Player currentPlayer;

	private static String NEWLINE = System.getProperty("line.separator");

	private static boolean mapConquered = false;
	private static boolean countryConquredInSingleAttack = false;
	private static boolean attackphaseEnded = false;
	private static boolean allOutTerritoryConqured = false;
	private static boolean fortificationStarted = false;
	private static int attackerDice;
	private static int defenderDice;

	private Player defenderPlayer;
	private String fromCountryAttack;
	private String toCountryAttack;

	private boolean exchangeRequired = false;

	private boolean attackFire = false;

	private boolean attackMove = false;

	private ObservableList<Player> playerList = FXCollections.observableArrayList();

	private static int totalCountries;

	private List<RiskCard> cardList;

	private StringBuilder phaseView;
	private StringBuilder playerView;
	private StringBuilder excahangeCardView;

	private String currentPhase;

	private boolean gamewin = false;
	
	private Subject subject;

	/**
	 * This is an initialization method for this controller to start.
	 * 
	 * @param location  of the FXML file
	 * @param resources is properties information
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// gamePlayPhase = new GamePlayPhase();
		riskMap = new RiskMap();
		sb = new StringBuilder();
		territoryArea.clear();
		adjacentTerritoryArea.clear();

		phaseView = new StringBuilder();
		playerView = new StringBuilder();
		excahangeCardView = new StringBuilder();
		
		subject = new Subject();
		subject.attach(this);
		
	}

	/**
	 * @param riskMap2
	 */
	private int getTotalCountries(RiskMap r) {
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
	 * This is an onAction method for button fireCommand. It'll take commands from
	 * player and performs action accordingly.
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void fireCommand(ActionEvent event) {

		if (!gamewin) {

			// txtConsoleLog.clear();
			fillTerritoryList();
			fillAdjacentTerritoryList();
			System.out.println("playerIndex => " + playerIndex);
			System.out.println("playerName => " + playerName);
			System.out.println("playerReinforceArmy => " + currentPlayer.getPlayerReinforceArmy());

			System.out.println("Before playerList = > " + playerList);

			try {
				String command = txtCommandLine.getText();
				if (command != null && !command.isEmpty()) {

					// if (!attackFire) {

					// if (!attackMove) {

					if (command.startsWith("reinforce") || command.startsWith("exchangecards")) {
						if (currentPlayer.getPlayerReinforceArmy() != 0) {

							txtConsoleLog.setText(placeReinforcement(command));
						} else {
							sb.append(playerName).append(" 's Reinforcement Phase done.").append(NEWLINE);
							txtConsoleLog.setText(sb.toString());

						}

					} else if ((command.startsWith("attack") || command.startsWith("defend")
							|| command.startsWith("attackmove")) && !fortificationStarted) {

						if (currentPlayer.getPlayerReinforceArmy() == 0) {
							txtConsoleLog.setText(attackPhase(command));

						} else {
							sb.append("you have left ").append(currentPlayer.getPlayerReinforceArmy()).append(" to reinforcement")
									.append(NEWLINE);
							txtConsoleLog.setText(sb.toString());
						}

					} else if (command.startsWith("fortify")) {
						if (currentPlayer.getPlayerReinforceArmy() == 0) {
							if (attackphaseEnded) {

								txtConsoleLog.setText(fortification(command));

							} else {
								sb.append("Please fire \"attack -noattck\" before starting fortification phase")
										.append(NEWLINE);
								txtConsoleLog.setText(sb.toString());
							}
						} else {
							sb.append("you have left ").append(currentPlayer.getPlayerReinforceArmy()).append(" to reinforcement")
									.append(NEWLINE);
							txtConsoleLog.setText(sb.toString());
						}
					} else {
						sb.append("Please Enter Valid phase Command").append(NEWLINE);
						txtConsoleLog.setText(sb.toString());
					}

				} else {
					sb.append("Please Enter Valid phase Command").append(NEWLINE);
					txtConsoleLog.setText(sb.toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
				sb.append("Please Enter Valid phase Command").append(NEWLINE);
				txtConsoleLog.setText(sb.toString());
			}

			System.out.println("After playerList = > " + playerList);

			//printPlayerDominationView();
			subject.setPlayerDominationViewMessage(sb.toString());

		} else {
			sb.append("Game Finished ..!");
		}

	}

	/**
	 * This method is having all subcommands of attackphase and it will call another
	 * methods accordingly
	 * 
	 * @param command
	 * @return message to console regarding what is happening
	 */
	private String attackPhase(String command) {

		if (!attackMove || command.contains("attackmove")) {

			String[] dataArray = command.split(" ");
			List<String> commandData = Arrays.asList(dataArray);
			if (commandData.get(0).equals("attack") && commandData.size() == 4 && commandData.get(3).equals("-allout")
					&& validateInput(commandData.get(1), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
					&& validateInput(commandData.get(2), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {

				fromCountryAttack = commandData.get(1);
				toCountryAttack = commandData.get(2);

				if (riskPlayImpl.validateFromCountry(fromCountryAttack, playerList.get(playerIndex)) && riskPlayImpl
						.validateToCountry(fromCountryAttack, toCountryAttack, riskMap, playerList.get(playerIndex))) {

					int armyOnFromC = riskPlayImpl.getCurrentAramyByCountryName(fromCountryAttack, playerList);

					for (int i = 0; i < armyOnFromC; i++) {

						boolean alloutFinish = alloutAttack();
						if (alloutFinish) {
							break;
						}

					}

				} else {
					sb.append("Invalid attacker's fromCountry or toCountry").append(NEWLINE);
				}

			} else if (commandData.get(0).equals("attack") && commandData.get(1).equals("-noattack")) {

				if (countryConquredInSingleAttack || allOutTerritoryConqured) {
					addRiskCardToPlayer();
				}
				sb.append(playerName + " ").append("decided not to attack anymore.").append(NEWLINE);
				sb.append("Attack phase ended.").append(NEWLINE);
				sb.append("Start with fortification commands").append(NEWLINE);
				attackphaseEnded = true;
				fortificationStarted = true;

				countryConquredInSingleAttack = false;
				allOutTerritoryConqured = false;

				//printPhaseView("FORTIFICATION");
				subject.setPlayerPhaseViewMessage("FORTIFICATION");

			} else if (commandData.get(0).equals("attack") && validateInput(commandData.get(3), "[1-9][0-9]*")
					&& commandData.size() == 4 && validateInput(commandData.get(1), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
					&& validateInput(commandData.get(2), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
					&& (getint(commandData.get(3)) > 0 && getint(commandData.get(3)) <= 3)) {

				fromCountryAttack = commandData.get(1);
				toCountryAttack = commandData.get(2);
				attackerDice = Integer.parseInt(commandData.get(3));

				if (riskPlayImpl.validateFromCountry(fromCountryAttack, playerList.get(playerIndex))
						&& riskPlayImpl.validateToCountry(fromCountryAttack, toCountryAttack, riskMap,
								playerList.get(playerIndex))
						&& riskPlayImpl.validateAttackerDice(attackerDice, fromCountryAttack,
								playerList.get(playerIndex))) {

					defenderPlayer = riskPlayImpl.getPlayerByCountry(toCountryAttack, playerList);

					attackFire = true;

					PlayerTerritory toTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(toCountryAttack,
							defenderPlayer);

					sb.append(defenderPlayer.getPlayerName() + "'s defend turn started..").append(NEWLINE);
					sb.append(defenderPlayer.getPlayerName() + "'s country Name: ").append(toCountryAttack)
							.append(" Army: ").append(toTerritory.getArmyOnterritory()).append(NEWLINE);

				} else {
					sb.append("Invalid attacker's fromCountry or toCountry or dice").append(NEWLINE);
				}

			} else if (commandData.get(0).equals("defend") && validateInput(commandData.get(1), "[1-9][0-9]*")
					&& (getint(commandData.get(1)) > 0 && getint(commandData.get(1)) <= 2) && attackFire) {

				defenderDice = getint(commandData.get(1));

				if (riskPlayImpl.validateDefenderDice(defenderDice, toCountryAttack, defenderPlayer)) {

					attackFire = false;
					// sb.append("Correct ==>").append(NEWLINE);

					decideBattle(attackerDice, defenderDice);
					defenderPlayer = riskPlayImpl.getPlayerByCountry(toCountryAttack, playerList);
					PlayerTerritory toTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(toCountryAttack,
							defenderPlayer);

					if (toTerritory.getArmyOnterritory() == 0) {

						countryConquredInSingleAttack = true;
						attackMove = true;

						moveCountryToWinPlayer(fromCountryAttack, toCountryAttack);
						fillTerritoryList();
						fillAdjacentTerritoryList();

						sb.append(toCountryAttack).append(
								" country has been conquered Please move number of armies >= ").append(attackerDice).append(" to this country from the attacking country")
								.append(NEWLINE);

					}

				} else {
					sb.append("Please do attack first or invalid defender dice").append(NEWLINE);
				}

			} else if (commandData.get(0).equals("attackmove") && validateInput(commandData.get(1), "[1-9][0-9]*")) {

				int armyToMove = getint(commandData.get(1));

				Player player = riskPlayImpl.getPlayerByCountry(fromCountryAttack, playerList);
				PlayerTerritory toTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(fromCountryAttack, player);
				if (armyToMove > 0 && armyToMove < toTerritory.getArmyOnterritory()) {

					if(armyToMove >= attackerDice) {
						moveArmy(fromCountryAttack, toCountryAttack, armyToMove);
						attackMove = false;
					} else {
						sb.append("Army to Move should be greater than or equal to ").append(attackerDice).append(" - that is number of dices rolled by attacker").append(NEWLINE);
					}		

				} else {
					sb.append("Please Enter Valid Army to Move").append(NEWLINE);
				}

			} else {
				sb.append("Please Enter Valid Phase Command").append(NEWLINE);
			}

		} else {
			sb.append("Please enter valid Phase command").append(NEWLINE);
		}

		if (currentPhase.equals("ATTACK")) {
			txtPhaseView.setText(phaseView.append(sb.toString()).toString());
		}

		return sb.toString();
	}

	/**
	 * This method will asign risk card to player after successful conqured
	 */
	private void addRiskCardToPlayer() {

		// add card to player
		for (Player player : playerList) {

			if (player.getPlayerName().equals(playerName)) {

				RiskCard riskCard = cardList.get(0);
				player.getCardListOwnedByPlayer().add(riskCard);
				sb.append(riskCard).append(" Assigned to ").append(playerName).append(NEWLINE);
				break;
			}

		}

		// remove card from cardlist
		// ListIterator<RiskCard> riskcardList =
		// gameplayphase.getRiskCardList().listIterator();

		// List<RiskCard> riskcardList = gameplayphase.getRiskCardList();
		cardList.remove(0);
		gameplayphase.setRiskCardList(cardList);

		// gameplayphase.setRiskCardList(riskcardList);

		System.out.println("Card Size ==> " + gameplayphase.getRiskCardList().size());

		System.out.println("Card ==> " + gameplayphase.getRiskCardList());

		fillTerritoryList();
		fillAdjacentTerritoryList();

	}

	/**
	 * This method will move army from attcker country to conqured country
	 * 
	 * @param fromCountryAttack
	 * @param toCountryAttack
	 * @param armyToMove
	 */
	private void moveArmy(String fromCountryAttack, String toCountryAttack, int armyToMove) {

		for (Player player : playerList) {

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

				if (toCountryAttack.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					playerTerritory.setArmyOnterritory(playerTerritory.getArmyOnterritory() + armyToMove);

					sb.append(armyToMove).append(" Army Moved Successfully on ").append(toCountryAttack)
							.append(" country").append(NEWLINE);

				} else if (fromCountryAttack.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					playerTerritory.setArmyOnterritory(playerTerritory.getArmyOnterritory() - armyToMove);

				}
			}
		}
		fillTerritoryList();
		fillAdjacentTerritoryList();

	}

	/**
	 * This method will move conqured country to attacker
	 * 
	 * @param fromCountryAttack
	 * @param toCountryAttack
	 */
	private void moveCountryToWinPlayer(String fromCountryAttack, String toCountryAttack) {
		Player player = riskPlayImpl.getPlayerByCountry(toCountryAttack, playerList);
		PlayerTerritory playerTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(toCountryAttack, player);

		// Fore removing country
		for (Player playertoLst : playerList) {

			ListIterator<PlayerTerritory> playerTerritories = playertoLst.getPlayerterritories().listIterator();

			while (playerTerritories.hasNext()) {
				if (playerTerritories.next().getTerritoryName().equalsIgnoreCase(playerTerritory.getTerritoryName())) {
					playerTerritories.remove();
				}
			}
		}
		// for Adding country
		for (Player playerFromLst : playerList) {

			ListIterator<PlayerTerritory> playerTerritories = playerFromLst.getPlayerterritories().listIterator();

			while (playerTerritories.hasNext()) {
				if (playerTerritories.next().getTerritoryName().equalsIgnoreCase(fromCountryAttack)) {
					playerTerritories.add(playerTerritory);
				}
			}
		}
	}

	/**
	 * This method will use to attack all possible time on defender country
	 * 
	 * @param fromCountry
	 * @param toCountry
	 */
	private boolean alloutAttack() {

		boolean alloutFinish = false;

		int armyOnFromC = riskPlayImpl.getCurrentAramyByCountryName(fromCountryAttack, playerList);
		int armyOnToC = riskPlayImpl.getCurrentAramyByCountryName(toCountryAttack, playerList);

		attackerDice = riskPlayImpl.getAttackerDiesCount(armyOnFromC);
		defenderDice = riskPlayImpl.getDefenderDiceCount(armyOnToC);

		if (attackerDice > 0 && defenderDice > 0) {

			decideBattle(attackerDice, defenderDice);
			defenderPlayer = riskPlayImpl.getPlayerByCountry(toCountryAttack, playerList);
			PlayerTerritory toTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(toCountryAttack, defenderPlayer);
			if (toTerritory.getArmyOnterritory() == 0) {
				allOutTerritoryConqured = true;
				attackMove = true;
				alloutFinish = true;

				moveCountryToWinPlayer(fromCountryAttack, toCountryAttack);
				fillTerritoryList();
				fillAdjacentTerritoryList();

				sb.append(toCountryAttack).append(
						" country has been conquered Please move number of armies >= ").append(attackerDice).append(" to this country from the attacking country")
						.append(NEWLINE);

			}

		} else {
			alloutFinish = true;
			sb.append("Allout Attack not possible - not an valid army on country").append(NEWLINE);
		}

		return alloutFinish;

	}

	/**
	 * This method will call another method to give generated random dice and after
	 * that this method will decide whos dice is more and update army accordingly.
	 * 
	 * @param attackerDice
	 * @param defenderDice
	 */
	private void decideBattle(int attackerDice, int defenderDice) {

		List<Integer> attackerList = riskPlayImpl.getCountFromDies(attackerDice);
		List<Integer> defenderList = riskPlayImpl.getCountFromDies(defenderDice);
		int min = Math.min(attackerList.size(), defenderList.size());

		for (int i = 0; i < min; i++) {

			if (defenderList.get(i) == attackerList.get(i)) {
				updateArmyAfterBattle(fromCountryAttack, "attacker");
			} else if (defenderList.get(i) > attackerList.get(i)) {
				updateArmyAfterBattle(fromCountryAttack, "attacker");
			} else if (defenderList.get(i) < attackerList.get(i)) {
				updateArmyAfterBattle(toCountryAttack, "defender");
			}
		}
	}

	/**
	 * This method will update number of army from attcker or defender territory
	 * after battle completed everytime
	 * 
	 * @param country
	 * @param name    attcker or defender
	 */
	private void updateArmyAfterBattle(String country, String name) {

		for (Player player : playerList) {

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

				if (country.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					playerTerritory.setArmyOnterritory(playerTerritory.getArmyOnterritory() - 1);
					player.setArmyOwns(player.getArmyOwns() - 1);

					sb.append(name).append(" Country loses 1 army ").append(name).append(" has left with ")
							.append(playerTerritory.getArmyOnterritory()).append(NEWLINE);

					fillTerritoryList();
					fillAdjacentTerritoryList();
					break;

				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.observerpattern.Observer#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		currentLog.setText("");
		currentLog.appendText("Current Player : " + gameplayphase.getPlayerList().get(playerIndex).getPlayerName());
		currentLog.appendText("Current Phase : " + gameplayphase.getGamePhase());
		currentLog.appendText("Armies Owned : " + gameplayphase.getPlayerList().get(playerIndex).getPlayerName());
	}

	/**
	 * This is a method for fortification process where player can move his/her army
	 * from one territory to it's adjacent owned territory.
	 * 
	 * @param command is a fortification command given from player
	 * @return message of result from fortification process
	 */
	private String fortification(String command) {

		String[] dataArray = command.split(" ");
		List<String> commandData = Arrays.asList(dataArray);
		String fortificationMessage = null;

		if (commandData.get(0).equalsIgnoreCase("fortify")) {

			if (commandData.size() == 2 && commandData.get(0).equals("fortify") && commandData.get(1).equals("-none")) {

				sb.append(playerName).append(" 's turn ended.!").append(NEWLINE);

				// System.out.println("FORTIFY ====> " + playerList);
				fortificationStarted = false;
				attackphaseEnded = false;
				fillTerritoryList();
				fillAdjacentTerritoryList();
				changeUserTurn();

			} else {
				if (commandData.size() != 4) {
					sb.append("Please Enter Valid command").append(NEWLINE);
				} else {
					if (validateInput(commandData.get(3), "[1-9][0-9]*")
							&& validateInput(commandData.get(1), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
							&& validateInput(commandData.get(2), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {

						String fromCountry = commandData.get(1);
						String toCountry = commandData.get(2);
						int armytoMove = Integer.parseInt(commandData.get(3));

						PlayerTerritory fromTerritory = playerList.get(playerIndex).getPlayerterritories().stream()
								.filter(x -> fromCountry.equals(x.getTerritoryName())).findAny().orElse(null);

						PlayerTerritory toTerritory = playerList.get(playerIndex).getPlayerterritories().stream()
								.filter(x -> toCountry.equals(x.getTerritoryName())).findAny().orElse(null);

						if (fromTerritory != null && toTerritory != null) {

							List<String> neighbourCountriesList = mapManagementImpl
									.getNeighbourCountriesListByCountryName(riskMap, fromCountry);

							// System.out.println("neighbourCountriesList ==> " + neighbourCountriesList);

							if (neighbourCountriesList.contains(toCountry)) {

								if (fromTerritory.getArmyOnterritory() > armytoMove) {

									int left = fromTerritory.getArmyOnterritory() - armytoMove;

									// System.out.println("left => " + left);

									fromTerritory.setArmyOnterritory(left);

									// System.out.println("from taratory army after => " +
									// fromTerritory.getArmyOnterritory());

									// System.out.println("To taratory before => " +
									// toTerritory.getArmyOnterritory());

									int armyBefore = toTerritory.getArmyOnterritory();

									// System.out.println("armyBefore=> " + armyBefore);

									toTerritory.setArmyOnterritory(armyBefore + armytoMove);

									int armyAfter = toTerritory.getArmyOnterritory();

									// System.out.println("armyAfter=> " + armyAfter);

									sb.append(playerName).append("'s fortification done").append(NEWLINE);
									sb.append(playerName).append(" 's turn ended.!").append(NEWLINE);

									// System.out.println("FORTIFY ====> " + playerList);

									fillTerritoryList();
									fillAdjacentTerritoryList();

									// Observer-Pattern Update Call
									fortificationMessage = armytoMove + " armies moved from " + fromTerritory + " to "
											+ toTerritory;

									fortificationStarted = false;
									attackphaseEnded = false;
									changeUserTurn();

								} else {
									sb.append("you should have at least one army in your territory after fortification")
											.append(fromCountry).append(NEWLINE);
									fortificationMessage = sb.toString();
								}
							} else {
								sb.append(toCountry).append(" is not neighbour country of ").append(fromCountry)
										.append(NEWLINE);
								fortificationMessage = sb.toString();
							}

						} else {
							sb.append("fromcountry or tocountry not found : Please Enter Valid country Name")
									.append(NEWLINE);
							fortificationMessage = sb.toString();
						}

					} else {
						sb.append("Please Enter Valid command").append(NEWLINE);
						fortificationMessage = sb.toString();
					}
				}
			}
		} else {
			sb.append("Please Enter Valid command").append(NEWLINE);
			fortificationMessage = sb.toString();
		}

		// observerSubject.setFortificationMessage(fortificationMessage);

		if (currentPhase.equals("FORTIFICATION")) {
			txtPhaseView.setText(phaseView.append(sb.toString()).toString());
		}

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.observerpattern.Observer#fortificationUpdate()
	 */
	@Override
	public void fortificationUpdate() {
		phaseviewLog.setText("");
		phaseviewLog.appendText(observerSubject.getFortificationMessage());
	}

	/**
	 * This method validate input given from user and return true if it's correct
	 * and false otherwise.
	 * 
	 * @param value   is string to be validated
	 * @param pattern is regex
	 * @return true if validation got succeed.
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
	 * This a reinforcement method in which you can put some number of extra army on
	 * your desired territory each time your turn comes.
	 * 
	 * @param command is a command fired from player
	 * @return message of result from reinforcement process
	 */
	private String placeReinforcement(String command) {
		String countryName = "";
		int armyToPlace;
		String[] dataArray = command.split(" ");
		List<String> commandData = Arrays.asList(dataArray);
		String reinforcementMessage;

		if (commandData.get(0).equals("reinforce") && !exchangeRequired) {

			if (commandData.size() == 3 && validateInput(commandData.get(1), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
					&& validateInput(commandData.get(2), "[1-9][0-9]*")) {

				countryName = commandData.get(1);
				armyToPlace = Integer.parseInt(commandData.get(2));

				final String cName = countryName;

				PlayerTerritory playerTerritory = playerList.get(playerIndex).getPlayerterritories().stream()
						.filter(x -> cName.equals(x.getTerritoryName())).findAny().orElse(null);

				if (playerTerritory != null) {

					if (armyToPlace <= currentPlayer.getPlayerReinforceArmy() && armyToPlace != 0 && currentPlayer.getPlayerReinforceArmy() != 0) {

						// System.out.println("playerTerritory before ==> " + playerTerritory);

						int army = playerTerritory.getArmyOnterritory() + armyToPlace;

						playerTerritory.setArmyOnterritory(army);

						// System.out.println("playerTerritory After ==> " + playerTerritory);

						//playerReinforceArmy = playerReinforceArmy - armyToPlace;
						//currentPlayer.setPlayerReinforceArmy(currentPlayer.getPlayerReinforceArmy()-armyToPlace);
						currentPlayer.placeReinforcement(armyToPlace);

						String message = armyToPlace + " Assigned to " + cName;

						sb.append(message).append(NEWLINE);
						sb.append("you have left ").append(currentPlayer.getPlayerReinforceArmy()).append(" to reinforcement")
								.append(NEWLINE);

						if (currentPlayer.getPlayerReinforceArmy() == 0) {

							sb.append(playerName).append(" 's Reinforcement Phase done please go to the Attack phase")
									.append(NEWLINE);

							//printPhaseView("ATTACK");
							subject.setPlayerPhaseViewMessage("ATTACK");
						}

						// System.out.println("REINFORCEMENT ====> " + playerList);
						fillTerritoryList();
						fillAdjacentTerritoryList();

					} else {
						sb.append("Please provide Valid Army details").append(NEWLINE).append("you have left ")
								.append(currentPlayer.getPlayerReinforceArmy()).append(" to reinforcement").append(NEWLINE);
					}
				} else {
					sb.append("Country not found : Please Enter Valid country Name").append(NEWLINE);
				}
			}
		} else if (commandData.get(0).equals("exchangecards")) {

			if (commandData.size() == 2 && commandData.get(1).equals("-none") && !exchangeRequired) {
				sb.append("Player Don't want to exchange cards.");

			} else {

				int numOfCardsPlayerOwned = currentPlayer.getCardListOwnedByPlayer().size();
				if (commandData.size() == 4 && validateInput(commandData.get(1), "[1-9][0-9]*")
						&& validateInput(commandData.get(2), "[1-9][0-9]*")
						&& validateInput(commandData.get(3), "[1-9][0-9]*") && numOfCardsPlayerOwned >= 3) {

					Integer x = Integer.parseInt(commandData.get(1));
					Integer y = Integer.parseInt(commandData.get(2));
					Integer z = Integer.parseInt(commandData.get(3));

					List<Integer> cardNumberLst = riskPlayImpl.getCardNumbersFromPlayer(currentPlayer);

					if (cardNumberLst.contains(x) && cardNumberLst.contains(y) && cardNumberLst.contains(z)) {

						RiskCardExchange cardExchange = new RiskCardExchange();

						cardExchange.setExchange1(riskPlayImpl.getCardBycardNumberofPlayer(currentPlayer, x));
						cardExchange.setExchange2(riskPlayImpl.getCardBycardNumberofPlayer(currentPlayer, y));
						cardExchange.setExchange3(riskPlayImpl.getCardBycardNumberofPlayer(currentPlayer, z));

						System.out.println("cardExchange==================>   " + cardExchange);

						boolean checkforExchange = riskPlayImpl.checkForExchange(cardExchange);

						System.out.println("checkforExchange==================>   " + checkforExchange);

						if (checkforExchange) {

							exchangeRequired = false;

							int count = currentPlayer.getExchangeCount();

							cardList.add(riskPlayImpl.getCardBycardNumberofPlayer(currentPlayer, x));
							cardList.add(riskPlayImpl.getCardBycardNumberofPlayer(currentPlayer, y));
							cardList.add(riskPlayImpl.getCardBycardNumberofPlayer(currentPlayer, z));
							gameplayphase.setRiskCardList(cardList);

							for (Player player : playerList) {

								if (player.getPlayerName().equals(currentPlayer.getPlayerName())) {

									player.setExchangeCount(count + 1);

									ListIterator<RiskCard> cards = player.getCardListOwnedByPlayer().listIterator();
									while (cards.hasNext()) {
										if (cards.next().getCardNumber() == x || cards.next().getCardNumber() == y
												|| cards.next().getCardNumber() == z) {
											cards.remove();
										}
									}

								}

							}

							sb.append("card Successfully exchanged").append(NEWLINE);

//							playerReinforceArmy = playerReinforceArmy
//									+ riskPlayImpl.updateArmyAfterCardExchange(currentPlayer);
							
							currentPlayer.setPlayerReinforceArmy(currentPlayer.getPlayerReinforceArmy() + riskPlayImpl.updateArmyAfterCardExchange(currentPlayer));

							leftArmyMsg = "You have " + currentPlayer.getPlayerReinforceArmy() + " armies to Reinforcement";
							sb.append(leftArmyMsg).append(NEWLINE);

							//printPhaseView("REINFORCEMENT");
							subject.setPlayerPhaseViewMessage("REINFORCEMENT");
							

						} else {
							sb.append("card Numbers are not valid for exchange").append(NEWLINE);
						}

					} else {
						sb.append("Invalid card Number.").append(NEWLINE);
					}

				} else {
					sb.append(" Please exchange card or Invalid command or Player don't have enough cards to trade")
							.append(NEWLINE);
				}
			}

		} else {

			sb.append("Please Enter Valid Command").append(NEWLINE);
		}
		// Observer Pattern Update Call
		reinforcementMessage = sb.toString();
		// observerSubject.setReinforcementMessage(reinforcementMessage);

		if (currentPhase.equals("REINFORCEMENT")) {
			txtPhaseView.setText(phaseView.append(sb.toString()).toString());
		}

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.observerpattern.Observer#reinforcemrentUpdate()
	 */
	@Override
	public void reinforcemrentUpdate() {

		phaseviewLog.setText("");
		phaseviewLog.appendText(observerSubject.getReinforcementMessage());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.observerpattern.Observer#attackUpdate()
	 */
	@Override
	public void attackUpdate() {

		phaseviewLog.setText("");
		phaseviewLog.appendText(observerSubject.getAttackMessage());
	}

	/**
	 * This method will exit the game terminates the window.
	 * 
	 * @param event will represents value sent from view
	 */
	@FXML
	void exitGame(ActionEvent event) {
		stageManager.switchScene(FxmlView.WELCOME, null);
	}

	/**
	 * This method will transfer phase of playing from one player to another player
	 * for the first time
	 * 
	 * @param object
	 */
	public void transferGamePlayPhase(Object object) {

		playerList.clear();
		// Display the message
		gameplayphase = (GamePlayPhase) object;
		riskMap = mapManagementImpl.readMap(gameplayphase.getFileName());
		// System.out.println("===> " + gameplayphase);
		// System.out.println(riskMap);

		playerList.addAll(gameplayphase.getPlayerList());

		totalCountries = getTotalCountries(riskMap);
		cardList = riskPlayImpl.makeCards(totalCountries);
		gameplayphase.setRiskCardList(cardList);

		System.out.println("totalCountries => " + totalCountries);
		System.out.println("cardList => " + cardList);
		System.out.println("cardList Size => " + cardList.size());

		txtCommandLine.clear();
		txtConsoleLog.clear();

		playerName = playerList.get(playerIndex).getPlayerName();
		currentPlayer = playerList.get(playerIndex);

//		playerReinforceArmy = riskPlayImpl.checkForReinforcement(
//				playerList.get(playerIndex).getPlayerterritories().size(), currentPlayer, gameplayphase.getFileName());
//		playerReinforceArmy = playerReinforceArmy + riskPlayImpl.updateArmyAfterCardExchange(currentPlayer);
		
		currentPlayer.setPlayerReinforceArmy(riskPlayImpl.checkForReinforcement(
				playerList.get(playerIndex).getPlayerterritories().size(), currentPlayer, gameplayphase.getFileName()));
		
		currentPlayer.setPlayerReinforceArmy(currentPlayer.getPlayerReinforceArmy()+riskPlayImpl.updateArmyAfterCardExchange(currentPlayer));

		turnStartedMsg = playerName + "'s turn is started";
		leftArmyMsg = "You have " + currentPlayer.getPlayerReinforceArmy() + " armies to Reinforcement";
		sb.append(turnStartedMsg).append(NEWLINE).append(leftArmyMsg).append(NEWLINE);

		txtConsoleLog.setText(sb.toString());

		fillTerritoryList();
		fillAdjacentTerritoryList();

		//printPhaseView("REINFORCEMENT");
		subject.setPlayerPhaseViewMessage("REINFORCEMENT");
		//printPlayerDominationView();
		subject.setPlayerDominationViewMessage(sb.toString());
	}

	/**
	 * This method will change user's turn everytime
	 */
	private void changeUserTurn() {

		if (currentPlayer.getPlayerterritories().size() != totalCountries) {

			if (playerIndex < playerList.size() - 1) {
				playerIndex++;
				if (playerList.get(playerIndex).getPlayerName().equalsIgnoreCase(NEUTRAL)) {
					playerIndex = 0;
				}
			} else {
				playerIndex = 0;
			}

			txtCommandLine.clear();
			txtConsoleLog.clear();

			playerName = playerList.get(playerIndex).getPlayerName();
			currentPlayer = playerList.get(playerIndex);

//			playerReinforceArmy = riskPlayImpl.checkForReinforcement(
//					playerList.get(playerIndex).getPlayerterritories().size(), currentPlayer,
//					gameplayphase.getFileName());
//			playerReinforceArmy = playerReinforceArmy + riskPlayImpl.updateArmyAfterCardExchange(currentPlayer);
			
			currentPlayer.setPlayerReinforceArmy(riskPlayImpl.checkForReinforcement(
					playerList.get(playerIndex).getPlayerterritories().size(), currentPlayer, gameplayphase.getFileName()));
			
			currentPlayer.setPlayerReinforceArmy(currentPlayer.getPlayerReinforceArmy()+riskPlayImpl.updateArmyAfterCardExchange(currentPlayer));

			turnStartedMsg = playerName + "'s turn is started";
			leftArmyMsg = "You have " + currentPlayer.getPlayerReinforceArmy() + " armies to Reinforcement";
			sb.append(turnStartedMsg).append(NEWLINE).append(leftArmyMsg).append(NEWLINE);

			if (currentPlayer.getCardListOwnedByPlayer().size() >= 5) {

				sb.append("Your Risk card is more than 5 please exchange it");
				exchangeRequired = true;

				//printPhaseView("EXCHANGE CARDS");
			}

			txtConsoleLog.setText(sb.toString());

			fillTerritoryList();
			fillAdjacentTerritoryList();

			//printPhaseView("REINFORCEMENT");
			subject.setPlayerPhaseViewMessage("REINFORCEMENT");

		} else {

			gamewin = true;
			sb.append(currentPlayer.getPlayerName()).append(" Won the Game !!").append(NEWLINE);

		}

	}

	/**
	 * This method will fill list of territories with it's army,that user owns to
	 * show it in GUI while he is having his turn
	 */
	private void fillTerritoryList() {

		territoryArea.clear();
		StringBuilder sbBuilder = new StringBuilder();
		Player player = playerList.get(playerIndex);
		if (player != null) {
			int count = 1;
			for (PlayerTerritory territory : player.getPlayerterritories()) {

				sbBuilder.append(count).append(") ").append(territory.getTerritoryName()).append(" : ")
						.append(territory.getArmyOnterritory()).append(NEWLINE);
				count++;
			}
			territoryArea.setText(sbBuilder.toString());
		}
	}

	/**
	 * This method will fill list of adjacent territories user owns to show it in
	 * GUI while he is having his turn
	 */
	private void fillAdjacentTerritoryList() {

		adjacentTerritoryArea.clear();
		StringBuilder sbBuilder = new StringBuilder();
		Player player = playerList.get(playerIndex);
		if (player != null) {

			List<PlayerTerritory> playerTerritories = player.getPlayerterritories();
			int count = 1;
			for (PlayerTerritory territory : playerList.get(playerIndex).getPlayerterritories()) {

				String country = territory.getTerritoryName();
				sbBuilder.append(count).append(") ").append(country).append("-").append(territory.getArmyOnterritory());
				List<String> neighbourCountriesList = mapManagementImpl.getNeighbourCountriesListByCountryName(riskMap,
						country);
				if(neighbourCountriesList != null && !neighbourCountriesList.isEmpty()) {
				for (String neighbour : neighbourCountriesList) {

					PlayerTerritory playerTerritory = playerList.get(playerIndex).getPlayerterritories().stream()
							.filter(x -> neighbour.equals(x.getTerritoryName())).findAny().orElse(null);

					if (playerTerritory != null) {
						sbBuilder.append(" ==> ").append(playerTerritory.getTerritoryName());
					}

				}
			}
				sbBuilder.append(NEWLINE);
				count++;
			}
			adjacentTerritoryArea.setText(sbBuilder.toString());
		}
	}

	private int getint(String str) {
		return Integer.parseInt(str);
	}

	/**
	 * This method will Print the phase View of Risk Game on every phase.
	 * 
	 * @param phase is the name of current Phase i.e. Reinforcement, Attack,
	 *              fortification etc.
	 */
	private void printPhaseView(String phase) {

		currentPhase = phase;

		txtPhaseView.clear();

		phaseView = new StringBuilder();

		phaseView.append("Current Player : ").append(currentPlayer.getPlayerName()).append(NEWLINE);
		phaseView.append("Current Phase : ").append(phase).append(NEWLINE).append(NEWLINE);

		txtPhaseView.setText(phaseView.toString());

		if (phase.equalsIgnoreCase("REINFORCEMENT")) {
			//printRiskCard();
			subject.setPlayerCardExchangeViewMessage(excahangeCardView.toString());
		} else {
			txtCardExchangeView.clear();
		}

	}

	/**
	 * This method will Print Player domination View including player name,
	 * percentage of world map he is covering etc.
	 */
	private void printPlayerDominationView() {

		txtPlayerDominationView.clear();

		playerView = new StringBuilder();

		String percentage = riskPlayImpl.getPlayerPercentageByCountry(currentPlayer, totalCountries);

		List<String> continentList = riskPlayImpl.getContinentControlledByPlayer(currentPlayer,
				gameplayphase.getFileName());

		int totalArmy = riskPlayImpl.getTotalArmyByPlayer(playerList, currentPlayer);

		playerView.append("Map Controlled by ").append(currentPlayer.getPlayerName()).append(" is ").append(percentage)
				.append(NEWLINE);
		playerView.append("Continent : ").append(continentList).append(NEWLINE);
		playerView.append("Total Numebr of Army : ").append(totalArmy).append(NEWLINE);

		txtPlayerDominationView.setText(playerView.toString());

	}

	/**
	 * This method will use to print riskcard in console of Card Exchnage View
	 */
	private void printRiskCard() {

		txtCardExchangeView.clear();

		excahangeCardView = new StringBuilder();

		excahangeCardView.append(currentPlayer.getPlayerName()).append(" cardexchange view :").append(NEWLINE);
		excahangeCardView.append("Risk Cards:").append(NEWLINE);

		for (RiskCard riskCard : currentPlayer.getCardListOwnedByPlayer()) {

			excahangeCardView.append("cardNumber: ").append(riskCard.getCardNumber()).append(" armyType: ")
					.append(riskCard.getArmyType()).append(NEWLINE);

		}
		txtCardExchangeView.setText(excahangeCardView.toString());

	}

	@Override
	public void playerDominationUpdate() {
		printPlayerDominationView();
		
	}

	@Override
	public void playerPhaseViewUpdate() {

		printPhaseView(subject.getPlayerPhaseViewMessage());
		
	}

	@Override
	public void playerCardExchangeViewUpdate() {
		printRiskCard();
		
	}

}
