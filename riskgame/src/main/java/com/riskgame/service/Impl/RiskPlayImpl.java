package com.riskgame.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.aspectj.apache.bcel.util.Play;
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
	public static int updatedArmy;

	@Autowired
	public MapManagementImpl mapManagementImpl;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#checkForReinforcement(int)
	 */
	@Override
	public int checkForReinforcement(int totalOwnedCountries, GamePlayPhase gamePlayPhase) {
		int total = Math.floorDiv(totalOwnedCountries, 3);
		int totalArmyforReinforce = Math.max(total, 3);
		totalArmyforReinforce += checkForContinentControlValue(gamePlayPhase);
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
		for (int i = 0; i < noOfCountries; i = i + 3) {
			RiskCard card = new RiskCard();
			card.setArmyType(INFANTRY);
			card.setCardNumber(i);
			totalCards.add(card);
		}
		for (int i = 1; i < noOfCountries; i = i + 3) {
			RiskCard card = new RiskCard();
			card.setArmyType(ARTILLERY);
			card.setCardNumber(i);
			totalCards.add(card);
		}
		for (int i = 2; i < noOfCountries; i = i + 3) {
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
	public int checkForContinentControlValue(GamePlayPhase gamePlayPhase) {
		int controlvalueTosend = 0;
		List<Player> playerList = gamePlayPhase.getPlayerList();
		Iterator<Player> itr = playerList.listIterator();
		while (itr.hasNext()) {
			Player p = itr.next();
			List<PlayerTerritory> territoryList = p.getPlayerterritories();
			List<String> territoryStringList = territoryList.stream().map(e -> e.getTerritoryName())
					.collect(Collectors.toList());
			Map<Integer, Continent> continentMap = mapManagementImpl.readMap(gamePlayPhase.getFileName())
					.getContinents();
			/*
			 * Iterator<Entry<Integer, Continent>> i = continentMap.entrySet().iterator();
			 * while (i.hasNext()) {
			 * 
			 * }
			 */
			for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {
				Continent continent = entry.getValue();
				List<String> continentTerritoryStringList = continent.getTerritoryList().stream()
						.map(e -> e.getTerritoryName()).collect(Collectors.toList());
				if (territoryStringList.containsAll(continentTerritoryStringList)) {
					controlvalueTosend = continent.getContinentValue();
				}
			}
		}
		return controlvalueTosend;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#updateArmyAfterCardExchange(com.riskgame.model.Player)
	 */
	@Override
	public int updateArmyAfterCardExchange(Player player) {
		if (player.getExchangeCount() == 0) {
			updatedArmy = 5;
			player.setExchangeCount(1);
		} else if (player.getExchangeCount() == 1) {
			updatedArmy = 10;
			player.setExchangeCount(2);
		} else if (player.getExchangeCount() == 2) {
			updatedArmy = 15;
			player.setExchangeCount(3);
		} else if (player.getExchangeCount() == 3) {
			updatedArmy = 20;
			player.setExchangeCount(4);
		} else if (player.getExchangeCount() == 4) {
			updatedArmy = 25;
			player.setExchangeCount(5);
		} else if (player.getExchangeCount() == 5) {
			updatedArmy = 30;
			player.setExchangeCount(6);
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

	public PlayerTerritory getPlayerTerritoryByCountryName(String country, Player player) {
		return player.getPlayerterritories().stream().filter(x -> country.equals(x.getTerritoryName())).findAny()
				.orElse(null);
	}

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
	 * @param dies
	 * @return sorted list of dice
	 */
	public List<Integer> getCountFromDies(int dies) {

		List<Integer> countList = new ArrayList<Integer>();
		for (int i = 0; i < dies; i++) {
			countList.add(generateRandomIntIntRange(1, 6));
		}
		/* Sorting in decreasing (descending) order */
		Collections.sort(countList, Collections.reverseOrder());
		return countList;

	}

	/**
	 * Generate random number for dice with random function
	 * 
	 * @param min
	 * @param max
	 * @return random number for dice
	 */
	public int generateRandomIntIntRange(int min, int max) {
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
	 * This method will return true if Player can excahnage card
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
}
