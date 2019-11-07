package com.riskgame.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riskgame.model.Continent;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.model.RiskMap;
import com.riskgame.service.RiskPlayInterface;

/**
 * This is a implementation class of RiskPlayInterface where all buisness logic.
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

	@Autowired
	public MapManagementImpl mapManagementImpl;

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

		Map<Integer, Continent> continentMap = mapManagementImpl.readMap(fileName).getContinents();

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

	public List<String> getContinentControlledByPlayer(Player player, String fileName) {
		List<String> continentLst = new ArrayList<String>();

		List<PlayerTerritory> territoryList = player.getPlayerterritories();

		List<String> territoryStringList = territoryList.stream().map(e -> e.getTerritoryName())
				.collect(Collectors.toList());

		Map<Integer, Continent> continentMap = mapManagementImpl.readMap(fileName).getContinents();

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
	public int updateArmyAfterCardExchange(Player player) {
		int updatedArmy = 0;
		if (player.getExchangeCount() == 1) {
			updatedArmy = 5;
		} else if (player.getExchangeCount() == 2) {
			updatedArmy = 10;
		} else if (player.getExchangeCount() == 3) {
			updatedArmy = 15;
		} else if (player.getExchangeCount() == 4) {
			updatedArmy = 20;
		} else if (player.getExchangeCount() == 5) {
			updatedArmy = 25;
		} else if (player.getExchangeCount() == 6) {
			updatedArmy = 30;
		} else if (player.getExchangeCount() == 0) {
			updatedArmy = 0;
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

		Player playerFromCountry = null;

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
	public PlayerTerritory getPlayerTerritoryByCountryName(String country, Player player) {
		return player.getPlayerterritories().stream().filter(x -> country.equals(x.getTerritoryName())).findAny()
				.orElse(null);
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

	@Override
	public String getPlayerPercentageByCountry(Player player, int totalCountry) {

		int size = player.getPlayerterritories().size();
		float percent = size * 100f / totalCountry;

		return Float.toString(percent)+"%";

	}

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

}
