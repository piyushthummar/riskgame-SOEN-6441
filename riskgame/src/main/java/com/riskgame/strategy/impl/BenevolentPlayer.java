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

				player_occupied_territory = new ArrayList<>();
				List<Integer> armies_no_list = getListofArmiesValues(currentPlayer.getPlayerterritories());
				moveArmiesFromStrongToWeakTerritory(armies_no_list, currentPlayer.getPlayerterritories(),
						gamePlayPhase);

			}

		}

		return gamePlayPhase;
	}

	private void moveArmiesFromStrongToWeakTerritory(List<Integer> armies_no_list,
			List<PlayerTerritory> playerterritories, GamePlayPhase gamePlayPhase) {

		PlayerTerritory weak_territory = null;
		PlayerTerritory strong_territory = null;
		String message = "";
		setListofPlayerTerritories(playerterritories);

		List<PlayerTerritory> weak_territories_list = getWeakTerritoriesOfPlayer(armies_no_list, playerterritories);

		HashMap<String, List<PlayerTerritory>> territory_to_neighbouring = getNeighboursForFortifyPhase(
				weak_territories_list, gamePlayPhase);

		if (territory_to_neighbouring != null) {

			for (Entry<String, List<PlayerTerritory>> neighbor : territory_to_neighbouring.entrySet()) {

				weak_territory = getTerritoryObjectFromString(weak_territories_list, neighbor.getKey());
				int max = 0;
				List<PlayerTerritory> neigbours_list = neighbor.getValue();

				if (neigbours_list.size() > 0) {

					for (int neighbours_index = 0; neighbours_index < neigbours_list.size(); neighbours_index++) {
						if (neigbours_list.get(neighbours_index).getArmyOnterritory() > max) {
							max = neigbours_list.get(neighbours_index).getArmyOnterritory();
							strong_territory = neigbours_list.get(neighbours_index);
						}
					}
					int diff = (int) Math.floor(
							Math.abs(strong_territory.getArmyOnterritory() - weak_territory.getArmyOnterritory()) / 2);
					if (diff < 1) {
						message += "Fortification not possible because ";
						message += " Strong Territory Info : [ " + strong_territory.getTerritoryName() + ","
								+ strong_territory.getArmyOnterritory() + " ] and ";
						message += " Weak Territory Info : [ " + weak_territory.getTerritoryName() + ","
								+ weak_territory.getArmyOnterritory() + " ]\n";
						gamePlayPhase.setStatus(message);
						continue;
					} else {
						weak_territory.setArmyOnterritory(weak_territory.getArmyOnterritory() + diff);
						strong_territory.setArmyOnterritory(strong_territory.getArmyOnterritory() - diff);
						message += "Fortification Successful\n";
						message += "Strong Territory Info : [ " + strong_territory.getTerritoryName() + ","
								+ strong_territory.getArmyOnterritory() + " ] and ";
						message += "Weak Territory Info : [ " + weak_territory.getTerritoryName() + ","
								+ weak_territory.getArmyOnterritory() + " ]\n";
						message += diff + " armies moved from " + strong_territory.getTerritoryName() + " to "
								+ weak_territory.getTerritoryName();
						gamePlayPhase.setStatus(message);
						return;
					}
				} else {
					message += "No Strong Territory Neighbours Found For Territory " + weak_territory.getTerritoryName()
							+ "\n";
					gamePlayPhase.setStatus(message);
					continue;
				}
			}
		}

	}

	private PlayerTerritory getTerritoryObjectFromString(List<PlayerTerritory> weak_territories_list,
			String territoryName) {
		PlayerTerritory weak_territory_object = null;
		for (int i = 0; i < weak_territories_list.size(); i++) {
			if (weak_territories_list.get(i).getTerritoryName().equalsIgnoreCase(territoryName)) {
				weak_territory_object = weak_territories_list.get(i);
			}
		}
		return weak_territory_object;
	}

	private HashMap<String, List<PlayerTerritory>> getNeighboursForFortifyPhase(
			List<PlayerTerritory> current_player_territories, GamePlayPhase game_play) {

		HashMap<String, List<PlayerTerritory>> territory_to_neighbouring = new LinkedHashMap<>();

		for (PlayerTerritory player_territory : current_player_territories) {

			List<PlayerTerritory> player_own_occupied_neighbours_list = new ArrayList<>();

			List<String> neighbourCountriesList = mapManagementImpl.getNeighbourCountriesListByCountryName(
					game_play.getRiskMap(), player_territory.getTerritoryName());

			for (String country : neighbourCountriesList) {
				PlayerTerritory territory_object = isPlayerOwnTerritory(country);
				if (territory_object != null) {
					player_own_occupied_neighbours_list.add(territory_object);
				}
			}

			territory_to_neighbouring.put(player_territory.getTerritoryName(), player_own_occupied_neighbours_list);

		}

		return territory_to_neighbouring;
	}

	private PlayerTerritory isPlayerOwnTerritory(String territory_name) {
		for (int i = 0; i < player_occupied_territory.size(); i++) {
			if (player_occupied_territory.get(i).getTerritoryName().equalsIgnoreCase(territory_name)) {
				return player_occupied_territory.get(i);
			}
		}
		return null;
	}

	private List<Integer> getListofArmiesValues(List<PlayerTerritory> playerterritories) {

		List<Integer> armies = new ArrayList<>();
		for (PlayerTerritory territory : playerterritories) {
			armies.add(territory.getArmyOnterritory());
		}
		return armies;

	}

	public List<PlayerTerritory> getWeakTerritoriesOfPlayer(List<Integer> armies_no_list,
			List<PlayerTerritory> player_territories_list) {
		List<PlayerTerritory> weak_territories_list = new ArrayList<>();
		int min = Collections.min(armies_no_list);
		for (int i = 0; i < armies_no_list.size(); i++) {
			if (armies_no_list.get(i) <= min) {
				weak_territories_list.add(player_territories_list.get(i));
			}
		}
		return weak_territories_list;
	}

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
