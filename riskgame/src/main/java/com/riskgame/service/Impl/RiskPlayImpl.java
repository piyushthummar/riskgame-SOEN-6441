package com.riskgame.service.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riskgame.adapter.DominationToConquestAdapter;
import com.riskgame.constant.ComputerStrategy;
import com.riskgame.model.Continent;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.model.RiskMap;
import com.riskgame.service.ConquestMapInterface;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.strategy.StrategyInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is a implementation class of RiskPlayInterface where all buisness logic
 * of game playing screen is writeen.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * 
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.Territory com.riskgame.model.PlayerTerritory
 */

@Service
public class RiskPlayImpl implements RiskPlayInterface {
	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "CAVALRY";
	public static final String ARTILLERY = "ARTILLERY";
	public static final String GAME_DIR_PATH = "src/main/resources/savedgames/";
	public static StringBuilder sb;
	private static String NEWLINE = System.getProperty("line.separator");

	@Autowired
	public MapManagementImpl mapManagementImpl;
	
	@Autowired
	private PlayerHandlerImpl playerHandlerImpl;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#checkForReinforcement(int)
	 */
	@Override
	public int checkForReinforcement(int totalOwnedCountries, Player player, String fileName) {
		int total = Math.floorDiv(totalOwnedCountries, 3);
		int totalArmyforReinforce = Math.max(total, 3);
		totalArmyforReinforce += checkForContinentControlValue(player, fileName);
		return totalArmyforReinforce;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#makeCards(int)
	 */
	@Override
	public List<RiskCard> makeCards(int noOfCountries) {
		List<RiskCard> totalCards = new ArrayList<>();
		for (int i = 1; i <= noOfCountries; i = i + 3) {
			RiskCard card = new RiskCard();
			card.setArmyType(INFANTRY);
			card.setCardNumber(i);
			totalCards.add(card);
		}
		for (int i = 2; i <= noOfCountries; i = i + 3) {
			RiskCard card = new RiskCard();
			card.setArmyType(ARTILLERY);
			card.setCardNumber(i);
			totalCards.add(card);
		}
		for (int i = 3; i <= noOfCountries; i = i + 3) {
			RiskCard card = new RiskCard();
			card.setArmyType(CAVALRY);
			card.setCardNumber(i);
			totalCards.add(card);
		}

		Collections.shuffle(totalCards);
		return totalCards;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#checkForContinentControlValue(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public int checkForContinentControlValue(Player player, String fileName) {
		int controlvalueTosend = 0;

		List<PlayerTerritory> territoryList = player.getPlayerterritories();

		List<String> territoryStringList = territoryList.stream().map(e -> e.getTerritoryName())
				.collect(Collectors.toList());

		Map<Integer, Continent> continentMap;

		if (mapManagementImpl.isMapConquest(fileName)) {
			ConquestMapInterface conquestMapInterface = new ConquestMapImpl();
			MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestMapInterface);
			continentMap = mapInterface.readMap(fileName).getContinents();
		} else {
			continentMap = mapManagementImpl.readMap(fileName).getContinents();
		}

		for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {
			Continent continent = entry.getValue();
			List<String> continentTerritoryStringList = continent.getTerritoryList().stream()
					.map(e -> e.getTerritoryName()).collect(Collectors.toList());
			if (territoryStringList.containsAll(continentTerritoryStringList)) {
				controlvalueTosend = continent.getContinentValue();
			}
		}

		return controlvalueTosend;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getContinentControlledByPlayer(com.riskgame.model.Player,
	 *      java.lang.String)
	 */
	@Override
	public List<String> getContinentControlledByPlayer(Player player, String fileName) {
		List<String> continentLst = new ArrayList<String>();

		List<PlayerTerritory> territoryList = player.getPlayerterritories();

		List<String> territoryStringList = territoryList.stream().map(e -> e.getTerritoryName())
				.collect(Collectors.toList());

		Map<Integer, Continent> continentMap;

		if (mapManagementImpl.isMapConquest(fileName)) {
			ConquestMapInterface conquestMapInterface = new ConquestMapImpl();
			MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestMapInterface);
			continentMap = mapInterface.readMap(fileName).getContinents();
		} else {
			continentMap = mapManagementImpl.readMap(fileName).getContinents();
		}

		for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {
			Continent continent = entry.getValue();
			List<String> continentTerritoryStringList = continent.getTerritoryList().stream()
					.map(e -> e.getTerritoryName()).collect(Collectors.toList());
			if (territoryStringList.containsAll(continentTerritoryStringList)) {

				continentLst.add(continent.getContinentName());

			}
		}
		return continentLst;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#updateArmyAfterCardExchange(com.riskgame.model.Player)
	 */
	@Override
	public Player updateArmyAfterCardExchange(Player player) {

		if (player.getExchangeCount() == 0) {

			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 5));
			player.setExchangeCount(1);
		} else if (player.getExchangeCount() == 1) {

			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 10));
			player.setExchangeCount(2);
		} else if (player.getExchangeCount() == 2) {

			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 15));
			player.setExchangeCount(3);
		} else if (player.getExchangeCount() == 3) {

			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 20));
			player.setExchangeCount(4);
		} else if (player.getExchangeCount() == 4) {

			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 25));
			player.setExchangeCount(5);
		} else if (player.getExchangeCount() == 5) {

			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 30));
			player.setExchangeCount(6);
		} else if (player.getExchangeCount() > 5) {
			player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() + 35));
			player.setExchangeCount(player.getExchangeCount() + 1);
		}

		return player;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#newArmyAfterCardExchange(com.riskgame.model.Player)
	 */
	@Override
	public int newArmyAfterCardExchange(Player player) {
		int updatedArmy = 0;
		if (player.getExchangeCount() == 0) {

			updatedArmy = player.getPlayerReinforceArmy();

		} else if (player.getExchangeCount() == 1) {

			updatedArmy = player.getPlayerReinforceArmy() + 5;
		} else if (player.getExchangeCount() == 2) {

			updatedArmy = player.getPlayerReinforceArmy() + 10;
		} else if (player.getExchangeCount() == 3) {

			updatedArmy = player.getPlayerReinforceArmy() + 15;
		} else if (player.getExchangeCount() == 4) {

			updatedArmy = player.getPlayerReinforceArmy() + 20;
		} else if (player.getExchangeCount() == 5) {

			updatedArmy = player.getPlayerReinforceArmy() + 25;
		} else if (player.getExchangeCount() > 5) {
			updatedArmy = player.getPlayerReinforceArmy() + 30;
		}

		return updatedArmy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#updateCardListAfterExchange(com.riskgame.model.Player,
	 *      java.util.List, com.riskgame.model.RiskCardExchange)
	 */
	@Override
	public void updateCardListAfterExchange(Player player, List<RiskCard> cards, RiskCardExchange exchangedCards) {
		cards.add(exchangedCards.getExchange1());
		cards.add(exchangedCards.getExchange2());
		cards.add(exchangedCards.getExchange3());
		Iterator<RiskCard> itr = player.getCardListOwnedByPlayer().listIterator();
		while (itr.hasNext()) {
			RiskCard riskCard = itr.next();
			if ((riskCard.getCardNumber() == exchangedCards.getExchange1().getCardNumber()
					&& riskCard.getArmyType().equalsIgnoreCase(exchangedCards.getExchange1().getArmyType()))
					|| (riskCard.getCardNumber() == exchangedCards.getExchange2().getCardNumber()
							&& riskCard.getArmyType().equalsIgnoreCase(exchangedCards.getExchange2().getArmyType()))
					|| (riskCard.getCardNumber() == exchangedCards.getExchange3().getCardNumber()
							&& riskCard.getArmyType().equalsIgnoreCase(exchangedCards.getExchange3().getArmyType()))) {
				itr.remove();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#validateFromCountry(java.lang.String,
	 *      com.riskgame.model.Player)
	 */
	@Override
	public boolean validateFromCountry(String fromCountry, Player player) {

		for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

			if (fromCountry.equalsIgnoreCase(playerTerritory.getTerritoryName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#validateToCountry(java.lang.String,
	 *      java.lang.String, com.riskgame.model.RiskMap, com.riskgame.model.Player)
	 */
	@Override
	public boolean validateToCountry(String fromCountry, String toCountry, RiskMap riskMap, Player player) {

		List<String> playerCountry = new ArrayList<String>();
		for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

			playerCountry.add(playerTerritory.getTerritoryName());
		}

		if (!playerCountry.contains(toCountry)) {

			List<String> neighbourCountriesList = mapManagementImpl.getNeighbourCountriesListByCountryName(riskMap,
					fromCountry);
			if (neighbourCountriesList.contains(toCountry)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#validateAttackerDice(int,
	 *      java.lang.String, com.riskgame.model.Player)
	 */
	@Override
	public boolean validateAttackerDice(int dies, String fromCountry, Player player) {

		for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

			if (fromCountry.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

				int numDies = getAttackerDiesCount(playerTerritory.getArmyOnterritory());

				if (numDies == dies && dies != 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method will return dice count for attacker based on his army.
	 * 
	 * @param currentArmy
	 * @return count of dice attacker can play with
	 */
	@Override
	public int getAttackerDiesCount(int currentArmy) {
		int numDies = 0;
		if (currentArmy >= 4) {
			numDies = 3;
		} else if (currentArmy == 3) {
			numDies = 2;
		} else if (currentArmy == 2) {
			numDies = 1;
		}
		return numDies;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getPlayerByCountry(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public Player getPlayerByCountry(String country, List<Player> playerList) {

		Player playerFromCountry = new Player();

		for (Player player : playerList) {

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

				if (country.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					return player;
				}
			}
		}
		return playerFromCountry;

	}

	/**
	 * This method will return current army on particular country given
	 * 
	 * @param country
	 * @param playerList
	 * @return number of army on country
	 */
	@Override
	public int getCurrentAramyByCountryName(String country, List<Player> playerList) {
		int army = 0;

		for (Player player : playerList) {

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

				if (country.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					return playerTerritory.getArmyOnterritory();
				}
			}
		}
		return army;
	}

	/**
	 * This method will return whole object of PlayerTerritory of given player based
	 * upon given territory(country) name
	 * 
	 * @param country is name of country whose object is needed
	 * @param player  is the player object whose PlayerTerritory needed
	 * @return
	 */
	@Override
	public PlayerTerritory getPlayerTerritoryByCountryName(String country, Player player) {
		PlayerTerritory playerTerritory = null;
		if(country != null &&  player!= null && player.getPlayerterritories().size()>0) {
			return player.getPlayerterritories().stream().filter(x -> country.equals(x.getTerritoryName())).findAny()
					.orElse(null);
		}
		return playerTerritory;
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#validateDefenderDice(int,
	 *      java.lang.String, com.riskgame.model.Player)
	 */
	@Override
	public boolean validateDefenderDice(int dies, String toCountry, Player player) {

		for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

			if (toCountry.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

				int numDies = getDefenderDiceCount(playerTerritory.getArmyOnterritory());
				if (numDies == dies && dies != 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method will return number of dice defender can use based upon number of
	 * army he is having
	 * 
	 * @param currentArmy is number of army defender is having
	 * @return number of dice defender can have
	 */
	@Override
	public int getDefenderDiceCount(int currentArmy) {
		int numDies = 0;
		if (currentArmy >= 2) {
			numDies = 2;
		} else if (currentArmy == 1) {
			numDies = 1;
		}
		return numDies;
	}

	/**
	 * This method will return one list having count of generated dice
	 * 
	 * @param dice number of dice selected from user
	 * @return sorted list of dice
	 */
	@Override
	public List<Integer> getCountFromDies(int dice) {

		List<Integer> countList = new ArrayList<Integer>();
		for (int i = 0; i < dice; i++) {
			countList.add(generateRandomIntRange(1, 6));
		}
		/* Sorting in decreasing (descending) order */
		Collections.sort(countList, Collections.reverseOrder());
		return countList;

	}

	/**
	 * Generate random number for dice with random function
	 * 
	 * @param min number provided
	 * @param max number provided
	 * @return random number for dice
	 */
	@Override
	public int generateRandomIntRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#addRiskCardToPlayer(java.lang.String)
	 */
	@Override
	public void addRiskCardToPlayer(String playerName, GamePlayPhase gamePlayPhase) {
		if (gamePlayPhase != null) {
			List<Player> playerList = gamePlayPhase.getPlayerList();
			for (Player player : playerList) {
				if (player.getPlayerName().equals(playerName)) {
					Random random = new Random();
					int index = random.nextInt(gamePlayPhase.getRiskCardList().size());
					player.getCardListOwnedByPlayer().add(gamePlayPhase.getRiskCardList().get(index));
					gamePlayPhase.getRiskCardList().remove(index);
					break;
				}
			}
		}
	}

	/**
	 * {@inheritDoc} This method will return true if Player can excahnage card
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#checkForExchange(com.riskgame.model.RiskCardExchange)
	 */
	@Override
	public boolean checkForExchange(RiskCardExchange cardExchange) {
		String a = cardExchange.getExchange1().getArmyType();
		String b = cardExchange.getExchange2().getArmyType();
		String c = cardExchange.getExchange3().getArmyType();
		if ((a.equals(b) && b.equals(c)) || (!a.equals(b) && !b.equals(c) && !c.equals(a))) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getCardNumbersFromPlayer(com.riskgame.model.Player)
	 */
	@Override
	public List<Integer> getCardNumbersFromPlayer(Player player) {
		List<Integer> cardNumber = new ArrayList<Integer>();
		if (player.getCardListOwnedByPlayer() != null && !player.getCardListOwnedByPlayer().isEmpty()) {
			for (RiskCard riskCard : player.getCardListOwnedByPlayer()) {

				cardNumber.add(riskCard.getCardNumber());

			}
		}
		return cardNumber;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getCardBycardNumberofPlayer(com.riskgame.model.Player,
	 *      int)
	 */
	@Override
	public RiskCard getCardBycardNumberofPlayer(Player player, int cardNumber) {
		RiskCard riskCardReturn = new RiskCard();
		if (player.getCardListOwnedByPlayer() != null && !player.getCardListOwnedByPlayer().isEmpty()) {
			for (RiskCard riskCard : player.getCardListOwnedByPlayer()) {

				if (riskCard.getCardNumber() == cardNumber) {
					System.out.println("riskCard ==> " + riskCard);
					return riskCard;
				}

			}
		}
		System.out.println("riskCardReturn ==> " + riskCardReturn);
		return riskCardReturn;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getPlayerPercentageByCountry(com.riskgame.model.Player,
	 *      int)
	 */
	@Override
	public String getPlayerPercentageByCountry(Player player, int totalCountry) {

		int size = player.getPlayerterritories().size();
		float percent = size * 100f / totalCountry;

		return Float.toString(percent) + "%";

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getTotalArmyByPlayer(java.util.List,
	 *      com.riskgame.model.Player)
	 */
	@Override
	public int getTotalArmyByPlayer(List<Player> players, Player currentPlayer) {

		int totalArmy = 0;

		for (Player player : players) {

			if (player.getPlayerName().equalsIgnoreCase(currentPlayer.getPlayerName())) {
				for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

					totalArmy = totalArmy + playerTerritory.getArmyOnterritory();
				}
			}

		}
		return totalArmy;
	}

	/**
	 * {@inheritDoc} This method prepares a CardTrade Object for any Computer Player
	 * 
	 * @param currentPlayer Current player who is about to exchange card
	 * @return RiskCardExchange has the set of three cards to be exchange
	 */
	@Override
	public RiskCardExchange prepareCard(Player currentPlayer) {

		Boolean validCard = false;

		RiskCardExchange riskCardExchange = null;
		RiskCard card1 = null;
		RiskCard card2 = null;
		RiskCard card3 = null;

		for (int i = 0; i < currentPlayer.getCardListOwnedByPlayer().size() - 2; i++) {
			card1 = currentPlayer.getCardListOwnedByPlayer().get(i);
			for (int j = i + 1; j < currentPlayer.getCardListOwnedByPlayer().size() - 1; j++) {
				card2 = currentPlayer.getCardListOwnedByPlayer().get(j);
				for (int k = j + 1; k < currentPlayer.getCardListOwnedByPlayer().size(); k++) {
					card3 = currentPlayer.getCardListOwnedByPlayer().get(k);
					if ((card1.getArmyType().equalsIgnoreCase(card2.getArmyType())
							&& card1.getArmyType().equalsIgnoreCase(card3.getArmyType()))
							|| (!card1.getArmyType().equalsIgnoreCase(card2.getArmyType())
									&& !card2.getArmyType().equalsIgnoreCase(card3.getArmyType())
									&& !card3.getArmyType().equalsIgnoreCase(card1.getArmyType()))) {
						validCard = true;
						break;
					}
					if (validCard) {
						break;
					}

				}
				if (validCard) {
					break;
				}
			}
		}
		if (validCard && card1 != null && card2 != null && card3 != null) {
			riskCardExchange = new RiskCardExchange();
			riskCardExchange.setExchange1(card1);
			riskCardExchange.setExchange2(card2);
			riskCardExchange.setExchange3(card3);
		}
		return riskCardExchange;
	}

	/**
	 * {@inheritDoc} This method exchange cards during reinforcement phase of the
	 * game
	 * 
	 * @param gamePlayPhase State of the game at point of time holding the entire
	 *                      info about game. Like the current phase and player.
	 * @return gamePlayPhase after updating info on exchange cards.
	 */
	@Override
	public GamePlayPhase exchangeCards(GamePlayPhase gamePlayPhase) {
		sb = new StringBuilder();
		RiskCardExchange riskCardExchange = gamePlayPhase.getRiskCardExchange();

		if (riskCardExchange != null) {
			if (riskCardExchange.getExchange1() == null || riskCardExchange.getExchange2() == null
					|| riskCardExchange.getExchange3() == null) {
				sb.append("Exchange Card requires a minimum of three cards to be selected").append(NEWLINE);
				// gamePlayPhase.setStatus("Trading requires a minimum of three cards to be
				// selected.\n");
			} else {

				if ((riskCardExchange.getExchange1().getArmyType()
						.equalsIgnoreCase(riskCardExchange.getExchange2().getArmyType())
						&& riskCardExchange.getExchange1().getArmyType()
								.equalsIgnoreCase(riskCardExchange.getExchange3().getArmyType()))
						|| (!riskCardExchange.getExchange1().getArmyType()
								.equalsIgnoreCase(riskCardExchange.getExchange2().getArmyType())
								&& !riskCardExchange.getExchange2().getArmyType()
										.equalsIgnoreCase(riskCardExchange.getExchange3().getArmyType())
								&& !riskCardExchange.getExchange3().getArmyType()
										.equalsIgnoreCase(riskCardExchange.getExchange1().getArmyType()))) {

					int currentPlayerId = gamePlayPhase.getCurrentPlayerId();

					for (Player player : gamePlayPhase.getPlayerList()) {

						if (player.getPlayerId() == currentPlayerId) {

							updateArmyAfterCardExchange(player);
							updateCardList(player, gamePlayPhase.getRiskCardList(), riskCardExchange);

							break;
						}
					}
				} else {
					sb.append("Either all three cards should have same army type or all three have different army type")
							.append(NEWLINE);
					// gamePlayPhase.setStatus("Either all three cards should have same image or all
					// three different.\n");
				}
			}
		} else {
			sb.append("Inavlid exchange cards").append(NEWLINE);
			// gamePlayPhase.setStatus("Inavlid exchange cards");
		}
		gamePlayPhase.setStatus(sb.toString());
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc} This method updates current player's card list also free card
	 * lists after player's exchange cards
	 * 
	 * @param player        current Player.
	 * @param freeRiskCards list of freeRiskCards
	 * @param exchangeCard  3 card of current Users which will be exchange
	 */
	@Override
	public void updateCardList(Player player, List<RiskCard> freeRiskCards, RiskCardExchange exchangeCard) {
		freeRiskCards.add(exchangeCard.getExchange1());
		freeRiskCards.add(exchangeCard.getExchange2());
		freeRiskCards.add(exchangeCard.getExchange3());
		Iterator<RiskCard> iterator = player.getCardListOwnedByPlayer().iterator();
		while (iterator.hasNext()) {
			RiskCard card = (RiskCard) iterator.next();
			if ((card.getArmyType().equalsIgnoreCase(exchangeCard.getExchange1().getArmyType())

					|| (card.getArmyType().equalsIgnoreCase(exchangeCard.getExchange2().getArmyType())

							|| (card.getArmyType().equalsIgnoreCase(exchangeCard.getExchange3().getArmyType()))))) {
				iterator.remove();
			}
		}
	}

	/**
	 * {@inheritDoc} This method finds and returns the strongest territory that the
	 * current player
	 * 
	 * @param player Currently player in game.
	 * @return PlayerTerritory Strongest Territory.
	 */
	@Override
	public PlayerTerritory getStrongestTerritory(Player player) {
		PlayerTerritory playerStrongestTerritory = null;
		int max = 0;
		for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {
			if (playerTerritory.getArmyOnterritory() > max) {
				max = playerTerritory.getArmyOnterritory();
				playerStrongestTerritory = playerTerritory;
			}
		}
		return playerStrongestTerritory;
	}

	/**
	 * {@inheritDoc} This method finds and returns the weakest territory that the
	 * current player
	 * 
	 * @param player Currently player in game.
	 * @return PlayerTerritory weakest Territory.
	 */
	@Override
	public PlayerTerritory getWeakestTerritory(Player player) {
		PlayerTerritory playerWeakestTerritory = null;

		if (player.getPlayerterritories().size() > 0) {

			int min = player.getPlayerterritories().get(0).getArmyOnterritory();

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {
				if (playerTerritory.getArmyOnterritory() <= min) {
					min = playerTerritory.getArmyOnterritory();
					playerWeakestTerritory = playerTerritory;
				}
			}
		}

		return playerWeakestTerritory;
	}

	/**
	 * {@inheritDoc} This method will give total countries from the map.
	 * 
	 * @param riskmap of current game
	 */
	@Override
	public int getTotalCountries(RiskMap riskMap) {
		Map<Integer, Continent> continentMap = riskMap.getContinents();
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
	 * {@inheritDoc} This method is used for current player is winner or not
	 * 
	 * @param gamePlayPhase Current gamePlayPhase
	 */
	@Override
	public GamePlayPhase checkForWinner(GamePlayPhase gamePlayPhase) {

		for (Player player : gamePlayPhase.getPlayerList()) {
			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {

				if (getTotalCountries(gamePlayPhase.getRiskMap()) == player.getPlayerterritories().size()) {
					gamePlayPhase.setGamePhase("GAME_FINISH");
					gamePlayPhase.setStatus(gamePlayPhase.getStatus() + " \n Congratulations ! "
							+ player.getPlayerName() + " You are the Winner of the Game");
					gamePlayPhase.setWinner("Player: " + player.getPlayerName() + " Behaviour : "
							+ player.getPlayerType() + "is Winner");
					
					return gamePlayPhase;
				}

				break;
			}
		}
		return gamePlayPhase;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@Override
	public void convertObjectToJsonFile(GamePlayPhase gamePlayPhase, String fileName)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		String jsonInString = "";
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(GAME_DIR_PATH + fileName + ".json"), "utf-8")))) {
			try {
				jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gamePlayPhase);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writer.println(jsonInString);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Override
	public GamePlayPhase convertJsonFileToObject(String fileName) throws FileNotFoundException, IOException {
		GamePlayPhase gamePlayPhase = new GamePlayPhase();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		String line = "";
		StringBuilder sb = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(GAME_DIR_PATH + fileName+".json"))) {
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			jsonInString = sb.toString();
			try {
				gamePlayPhase = mapper.readValue(jsonInString, GamePlayPhase.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getAvailableGameFiles()
	 */
	@Override
	public List<String> getAvailableGameFiles() {
		List<String> gameList = new ArrayList<String>();

		try (Stream<Path> path = Files.walk(Paths.get(GAME_DIR_PATH))) {

			gameList = path.map(filePath -> filePath.toFile().getName()).filter(fileName -> fileName.endsWith(".json"))
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return gameList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getPlayersCountries(com.riskgame.model.Player)
	 */
	@Override
	public List<String> getPlayersCountries(Player player) {

		List<String> countries = new ArrayList<String>();
		for (PlayerTerritory territory : player.getPlayerterritories()) {

			countries.add(territory.getTerritoryName());

		}
		return countries;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#getPlayerTerritoryByCountry(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public PlayerTerritory getPlayerTerritoryByCountry(String countryName, List<Player> playerList) {
		PlayerTerritory playerTerritory = null;
		for (Player player : playerList) {

			List<PlayerTerritory> playerTerritories = player.getPlayerterritories();
			for (PlayerTerritory playercountry : playerTerritories) {

				if (playercountry.getTerritoryName().equalsIgnoreCase(countryName)) {

					
					return playercountry;

				}

			}

		}
		return playerTerritory;
	}
	
	/**
	 * {@inheritDoc} This method is used checking map file
	 * 
	 * @param mapfiles list of map file from user
	 * @param availableMap available map file in system
	 */
	@Override
	public boolean tournamentValidMapCheck(List<String> mapfiles,List<String> availableMap) {
		boolean result = true;
		
		if(availableMap.size()>0) {
			for (String mapfile : mapfiles) {
				if(!availableMap.contains(mapfile)) {
					return false;
				}
			}
		}else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * {@inheritDoc} This method is used checking tournamentValidStrategy
	 * 
	 * @param strategyfromUserTournamnet list of strategy from User
	 * @param playerStrategiesListFromEnum available strategy in system
	 */
	@Override
	public boolean tournamentValidStrategy(List<String> strategyfromUserTournamnet,List<String> playerStrategiesListFromEnum) {
		boolean result = true;
		
		if(playerStrategiesListFromEnum.size()>0) {
			for (String strategy : strategyfromUserTournamnet) {
				if(!playerStrategiesListFromEnum.contains(strategy.toLowerCase())) {
					return false;
				}
			}
		}else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * {@inheritDoc} This method is generating playerList based on strategy
	 * 
	 * @param strategy List of strategy
	 */
	@Override
	public List<Player> generatePlayerListByStrategy(List<String> strategy){
		
		List<Player> playerList = new ArrayList<Player>();
		int playerId = 1;
		Player player;
		
		for (String strategyString : strategy) {
			
			player = new Player();
			player.setPlayerId(playerId);
			player.setPlayerName(strategyString);
			player.setArmyOwns(0);
			player.setPlayerType(ComputerStrategy.COMPUTER.toString());
			player.setStrategy((StrategyInterface) playerHandlerImpl.getStrategyByName(strategyString));
			player.setStrategyName(strategyString);
			playerList.add(player);
		}
		return playerList;
		
	}
	
	/**
	 * This method is generate GamePlayPhase for tournament 
	 * 
	 * @param gamePlayPhase gameplayphase for earch game
	 * @param strategyList player strategy
	 */
	
	@Override
	public GamePlayPhase GenerateGamePlayPhase(GamePlayPhase gamePlayPhase,List<String> strategyList,String mapFileNameEach) {
		
		gamePlayPhase = new GamePlayPhase();
		gamePlayPhase.setFileName(mapFileNameEach);
		
		ObservableList<Player> tourNamentPlayerList = FXCollections.observableArrayList();
		tourNamentPlayerList.addAll(generatePlayerListByStrategy(strategyList));
		gamePlayPhase.setPlayerList(tourNamentPlayerList);
		
		RiskMap riskmap;
		if (mapManagementImpl.isMapConquest(gamePlayPhase.getFileName())) {
			ConquestMapInterface conquestMapInterface = new ConquestMapImpl();
			MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestMapInterface);
			riskmap = mapInterface.readMap(gamePlayPhase.getFileName());
		} else {
			riskmap = mapManagementImpl.readMap(gamePlayPhase.getFileName());
		}
		gamePlayPhase.setRiskMap(riskmap);
		
		int totalCountries = getTotalCountries(riskmap);
		List<RiskCard> cardList = makeCards(totalCountries);
		gamePlayPhase.setRiskCardList(cardList);
		gamePlayPhase.setTotalCountries(totalCountries);
		
		gamePlayPhase = playerHandlerImpl.populateTerritoriesRandomly(gamePlayPhase);
		
		for (Player player : gamePlayPhase.getPlayerList()) {

			List<PlayerTerritory> ptList = player.getPlayerterritories();
			for (PlayerTerritory playerTerritory : ptList) {
				playerTerritory.setArmyOnterritory(0);

			}
		}
		
		gamePlayPhase = playerHandlerImpl.placeAllArmyByRoundRobin(gamePlayPhase);
		
		
		return gamePlayPhase;
		
	}
}
