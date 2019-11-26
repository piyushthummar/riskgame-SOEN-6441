/**
 * 
 */
package com.riskgame.strategy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.RiskPlayImpl;
import com.riskgame.strategy.StrategyInterface;

/**
 * Concrete implementation of Strategy interface of design pattern for
 * Benevolent strategy of player
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 * @see com.riskgame.strategy
 */
public class BenevolentPlayer implements StrategyInterface {

	public static StringBuilder sb;
	private static String NEWLINE = System.getProperty("line.separator");

	private MapManagementInterface mapManagementImpl;
	private RiskPlayInterface riskPlayImpl;

	/**
	 * Constructor which will initialize object of services
	 */
	public BenevolentPlayer() {
		mapManagementImpl = new MapManagementImpl();
		riskPlayImpl = new RiskPlayImpl();
	}

	private List<PlayerTerritory> player_occupied_territory = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#reinforce(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase reinforce(GamePlayPhase gamePlayPhase) {
		sb = new StringBuilder();
		Player currentPlayer = null;
		PlayerTerritory playerWeakestTerritory = null;
		if (gamePlayPhase != null) {
			for (Player player : gamePlayPhase.getPlayerList()) {
				if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
					currentPlayer = player;
					break;
				} else {
					continue;
				}
			}
			if (currentPlayer != null) {

				playerWeakestTerritory = riskPlayImpl.getWeakestTerritory(currentPlayer);

				if (playerWeakestTerritory != null) {

					playerWeakestTerritory.setArmyOnterritory(
							playerWeakestTerritory.getArmyOnterritory() + currentPlayer.getPlayerReinforceArmy());
					currentPlayer.setPlayerReinforceArmy(0);

					sb.append("weakest Territory :").append(playerWeakestTerritory.getTerritoryName())
							.append("Reinforced New Army count on weakest territory:")
							.append(playerWeakestTerritory.getArmyOnterritory()).append(gamePlayPhase.getStatus())
							.append(NEWLINE);

				}
			}
		}
		gamePlayPhase.setStatus(sb.toString());
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#attack(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase attack(GamePlayPhase gamePlayPhase) {
		gamePlayPhase.setStatus("Benevolent Player will never attack \n");
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#fortify(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase fortify(GamePlayPhase gamePlayPhase) {

		sb = new StringBuilder();
		Player currentPlayer = null;
		//PlayerTerritory playerWeakestTerritory = null;
		if (gamePlayPhase != null) {

			for (Player player : gamePlayPhase.getPlayerList()) {
				if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
					currentPlayer = player;
					break;
				} else {
					continue;
				}
			}
			if (currentPlayer != null) {

				player_occupied_territory = new ArrayList<>();
				List<Integer> noOfArmyList = getListofArmiesValues(currentPlayer.getPlayerterritories());
				moveArmiesFromStrongToWeakTerritory(noOfArmyList, currentPlayer.getPlayerterritories(),
						gamePlayPhase);

			}
		}
		return gamePlayPhase;
	}

	/**
	 * This method will move armies from strong country to weak country
	 * 
	 * @param noOfArmyList
	 * @param playerterritories
	 * @param gamePlayPhase
	 */
	private void moveArmiesFromStrongToWeakTerritory(List<Integer> noOfArmyList,
			List<PlayerTerritory> playerterritories, GamePlayPhase gamePlayPhase) {

		PlayerTerritory weakTerritory = null;
		PlayerTerritory strongTerritory = null;
		String message = "";
		setListofPlayerTerritories(playerterritories);

		List<PlayerTerritory> weakTerritoriesList = getWeakTerritoriesOfPlayer(noOfArmyList, playerterritories);

		HashMap<String, List<PlayerTerritory>> territoryToNeighbouringMap = getNeighboursForFortifyPhase(
				weakTerritoriesList, gamePlayPhase);

		if (territoryToNeighbouringMap != null) {

			for (Entry<String, List<PlayerTerritory>> neighbor : territoryToNeighbouringMap.entrySet()) {

				weakTerritory = getTerritoryObjectFromString(weakTerritoriesList, neighbor.getKey());
				int max = 0;
				List<PlayerTerritory> neigboursList = neighbor.getValue();

				if (neigboursList.size() > 0) {

					for (int i = 0; i < neigboursList.size(); i++) {
						if (neigboursList.get(i).getArmyOnterritory() > max) {
							max = neigboursList.get(i).getArmyOnterritory();
							strongTerritory = neigboursList.get(i);
						}
					}
					int diff = (int) Math.floor(
							Math.abs(strongTerritory.getArmyOnterritory() - weakTerritory.getArmyOnterritory()) / 2);
					if (diff < 1) {
						message += "Fortification not possible because ";
						message += " Strong Territory Info : [ " + strongTerritory.getTerritoryName() + ","
								+ strongTerritory.getArmyOnterritory() + " ] and ";
						message += " Weak Territory Info : [ " + weakTerritory.getTerritoryName() + ","
								+ weakTerritory.getArmyOnterritory() + " ]\n";
						gamePlayPhase.setStatus(message);
						continue;
					} else {
						weakTerritory.setArmyOnterritory(weakTerritory.getArmyOnterritory() + diff);
						strongTerritory.setArmyOnterritory(strongTerritory.getArmyOnterritory() - diff);
						message += "Fortification Successful\n";
						message += "Strong Territory Info : [ " + strongTerritory.getTerritoryName() + ","
								+ strongTerritory.getArmyOnterritory() + " ] and ";
						message += "Weak Territory Info : [ " + weakTerritory.getTerritoryName() + ","
								+ weakTerritory.getArmyOnterritory() + " ]\n";
						message += diff + " armies moved from " + strongTerritory.getTerritoryName() + " to "
								+ weakTerritory.getTerritoryName();
						gamePlayPhase.setStatus(message);
						return;
					}
				} else {
					message += "No Strong Territory Neighbours Found For Territory " + weakTerritory.getTerritoryName()
							+ "\n";
					gamePlayPhase.setStatus(message);
					continue;
				}
			}
		}

	}

	/**
	 * This method will return PlayerTerritoryObject from given TerritoryName and
	 * list of playerTerritory
	 * 
	 * @param weakTerritoryList
	 * @param territoryName
	 * @return playerTerritory
	 */
	private PlayerTerritory getTerritoryObjectFromString(List<PlayerTerritory> weakTerritoryList,
			String territoryName) {
		PlayerTerritory weakTerritoryObject = null;
		for (int i = 0; i < weakTerritoryList.size(); i++) {
			if (weakTerritoryList.get(i).getTerritoryName().equalsIgnoreCase(territoryName)) {
				weakTerritoryObject = weakTerritoryList.get(i);
			}
		}
		return weakTerritoryObject;
	}

	/**
	 * This method will return map for fortify benevolent strategy
	 * 
	 * @param currentPlayerTerritory
	 * @param gamePlay
	 * @return hashMap of territoryName and List of playerTerritory
	 */
	private HashMap<String, List<PlayerTerritory>> getNeighboursForFortifyPhase(
			List<PlayerTerritory> currentPlayerTerritory, GamePlayPhase gamePlay) {

		HashMap<String, List<PlayerTerritory>> territoryToNeighbouringMap = new LinkedHashMap<>();

		for (PlayerTerritory playerTerritory : currentPlayerTerritory) {

			List<PlayerTerritory> playerOwnedneighbourList = new ArrayList<>();

			List<String> neighbourCountriesList = mapManagementImpl
					.getNeighbourCountriesListByCountryName(gamePlay.getRiskMap(), playerTerritory.getTerritoryName());

			for (String country : neighbourCountriesList) {
				PlayerTerritory territoryObj = isPlayerOwnTerritory(country);
				if (territoryObj != null) {
					playerOwnedneighbourList.add(territoryObj);
				}
			}

			territoryToNeighbouringMap.put(playerTerritory.getTerritoryName(), playerOwnedneighbourList);

		}

		return territoryToNeighbouringMap;
	}

	/**
	 * This method will return playerTerritory model by given territoryName
	 * 
	 * @param territory_name
	 * @return model of PlayerTerritory
	 */
	private PlayerTerritory isPlayerOwnTerritory(String territoryName) {
		for (int i = 0; i < player_occupied_territory.size(); i++) {
			if (player_occupied_territory.get(i).getTerritoryName().equalsIgnoreCase(territoryName)) {
				return player_occupied_territory.get(i);
			}
		}
		return null;
	}

	/**
	 * This method will return list of army values of given list of playerTerritory
	 * 
	 * @param playerterritories
	 * @return list of army values
	 */
	private List<Integer> getListofArmiesValues(List<PlayerTerritory> playerterritories) {

		List<Integer> armies = new ArrayList<>();
		for (PlayerTerritory territory : playerterritories) {
			armies.add(territory.getArmyOnterritory());
		}
		return armies;

	}

	/**
	 * This method will return list of playerTerritory which is weak by army
	 * 
	 * @param noOfArmyList
	 * @param playerTerritoryList
	 * @return List of PlayerTerritory
	 */
	public List<PlayerTerritory> getWeakTerritoriesOfPlayer(List<Integer> noOfArmyList,
			List<PlayerTerritory> playerTerritoryList) {
		List<PlayerTerritory> weakTerritoryList = new ArrayList<>();
		int min = Collections.min(noOfArmyList);
		for (int i = 0; i < noOfArmyList.size(); i++) {
			if (noOfArmyList.get(i) <= min) {
				weakTerritoryList.add(playerTerritoryList.get(i));
			}
		}
		return weakTerritoryList;
	}

	/**
	 * This method will set List of player territories in playerTerritory model.
	 * 
	 * @param playerterritories
	 */
	private void setListofPlayerTerritories(List<PlayerTerritory> playerterritories) {

		for (PlayerTerritory territory : playerterritories) {
			player_occupied_territory.add(territory);
		}

	}

	/**
	 * This method will get list of armies values on each territory of current
	 * player
	 * 
	 * @param playerTerritoriesList List of Current Player Territories
	 * @return List of army values on each territory
	 */
	public List<Integer> getListofArmies(List<PlayerTerritory> playerTerritoriesList) {
		List<Integer> armies = new ArrayList<>();
		for (PlayerTerritory territory : playerTerritoriesList) {
			armies.add(territory.getArmyOnterritory());
		}
		return armies;
	}

}
