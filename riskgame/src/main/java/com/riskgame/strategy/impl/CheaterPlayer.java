/**
 * 
 */
package com.riskgame.strategy.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.RiskPlayImpl;
import com.riskgame.strategy.StrategyInterface;

/**
 * Concrete implementation of Strategy interface of design pattern for Cheater
 * strategy of player.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.strategy
 * @version 1.0.0
 */
public class CheaterPlayer implements StrategyInterface {

	public static StringBuilder sb;
	// private static String NEWLINE = System.getProperty("line.separator");

	private MapManagementInterface mapManagementImpl;
	private RiskPlayInterface riskPlayImpl;

	/**
	 * Constructor which will initialize object of services
	 */
	public CheaterPlayer() {
		mapManagementImpl = new MapManagementImpl();
		riskPlayImpl = new RiskPlayImpl();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#reinforce(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase reinforce(GamePlayPhase gamePlayPhase) {
		List<String> countriesList = new ArrayList<String>();
		for (Player player : gamePlayPhase.getPlayerList()) {

			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {

				player.setPlayerReinforceArmy(0);

				for (PlayerTerritory territory : player.getPlayerterritories()) {

					territory.setArmyOnterritory(territory.getArmyOnterritory() * 2);
					countriesList.add(territory.getTerritoryName());

				}

				break;
			}
		}
		String countries = StringUtils.collectionToCommaDelimitedString(countriesList);
		gamePlayPhase.setStatus("Cheater Doubled Armies on Territories: " + countries + "\n");
		System.out.println("CheaterPlayer reinforce - " + gamePlayPhase.getStatus());
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#attack(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase attack(GamePlayPhase gamePlayPhase) {
		sb = new StringBuilder();
		Set<String> countriesList = new HashSet<String>();
		Player currentPlayer = null;
		List<String> playerOwnCountries = new ArrayList<String>();
		List<PlayerTerritory> playert = new ArrayList<PlayerTerritory>();
		for (Player player : gamePlayPhase.getPlayerList()) {

			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
				currentPlayer = player;

				playerOwnCountries = riskPlayImpl.getPlayersCountries(player);

				break;
			} else {
				continue;
			}
		}

		for (PlayerTerritory territory : currentPlayer.getPlayerterritories()) {

			List<String> neighbourList = mapManagementImpl
					.getNeighbourCountriesListByCountryName(gamePlayPhase.getRiskMap(), territory.getTerritoryName());

			if (neighbourList.size() > 0) {

				for (String neighbourCountry : neighbourList) {

					if (!playerOwnCountries.contains(neighbourCountry)) {

						countriesList.add(neighbourCountry);

					}

				}

			}

		}

		if (countriesList.size() > 0) {
			for (String country : countriesList) {
				PlayerTerritory playerTerritory = riskPlayImpl.getPlayerTerritoryByCountry(country,
						gamePlayPhase.getPlayerList());

				if (playerTerritory != null) {

					// adding country
					playerTerritory.setArmyOnterritory(1);

					playert.add(playerTerritory);

				}
			}
		}

		if (playert.size() > 0) {
			currentPlayer.getPlayerterritories().addAll(playert);
		}

		if (countriesList.size() > 0) {
			// Fore removing country
			for (String country : countriesList) {
				for (Player playertoLst : gamePlayPhase.getPlayerList()) {

					ListIterator<PlayerTerritory> playerTerritories = playertoLst.getPlayerterritories().listIterator();

					while (playerTerritories.hasNext()) {
						if (playerTerritories.next().getTerritoryName().equalsIgnoreCase(country)

								&& playertoLst.getPlayerId() != gamePlayPhase.getCurrentPlayerId()) {
							playerTerritories.remove();
						}
					}
				}

			}
		}

		gamePlayPhase.setStatus(
				"Territories won by Cheater: " + StringUtils.collectionToCommaDelimitedString(countriesList) + "\n");
		riskPlayImpl.checkForWinner(gamePlayPhase);
		System.out.println("CheaterPlayer attack - " + gamePlayPhase.getStatus());
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
		List<String> countriesList = new ArrayList<String>();
		// Player currentPlayer = null;
		for (Player player : gamePlayPhase.getPlayerList()) {
			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
				// currentPlayer = player;

				List<String> playerOwnCountries = riskPlayImpl.getPlayersCountries(player);

				for (PlayerTerritory territory : player.getPlayerterritories()) {

					List<String> neighbourList = mapManagementImpl.getNeighbourCountriesListByCountryName(
							gamePlayPhase.getRiskMap(), territory.getTerritoryName());

					if (neighbourList.size() > 0) {

						for (String neighbourCountry : neighbourList) {

							if (!playerOwnCountries.contains(neighbourCountry)) {

								territory.setArmyOnterritory(territory.getArmyOnterritory() * 2);
								countriesList.add(territory.getTerritoryName());

								break;
							}

						}

					}

				}

				break;
			} else {
				continue;
			}
		}

		gamePlayPhase.setStatus("Territories fortified by Cheater: "
				+ StringUtils.collectionToCommaDelimitedString(countriesList) + "\n");
		System.out.println("CheaterPlayer fortify - " + gamePlayPhase.getStatus());
		return gamePlayPhase;
	}

}
