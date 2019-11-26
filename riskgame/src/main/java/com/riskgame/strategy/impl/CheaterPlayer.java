/**
 * 
 */
package com.riskgame.strategy.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
	//private static String NEWLINE = System.getProperty("line.separator");

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
					countriesList.add(territory.getContinentName());

				}
			}
		}
		String countries = StringUtils.collectionToCommaDelimitedString(countriesList);
		gamePlayPhase.setStatus("Cheater Doubled Armies on Territories: " + countries + "\n");
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
		List<String> countriesList = new ArrayList<String>();
		Player currentPlayer = null;
		for (Player player : gamePlayPhase.getPlayerList()) {
			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
				currentPlayer = player;
				break;
			} else {
				continue;
			}
		}
		if (currentPlayer != null) {
			List<String> playerOwnCountries = riskPlayImpl.getPlayersCountries(currentPlayer);

			for (PlayerTerritory territory : currentPlayer.getPlayerterritories()) {

				List<String> neighbourList = mapManagementImpl.getNeighbourCountriesListByCountryName(
						gamePlayPhase.getRiskMap(), territory.getTerritoryName());

				if (neighbourList.size() > 0) {

					for (String neighbourCountry : neighbourList) {

						if (!playerOwnCountries.contains(neighbourCountry)) {

							PlayerTerritory playerTerritory = riskPlayImpl.getPlayerTerritoryByCountry(neighbourCountry,
									gamePlayPhase.getPlayerList());

							if (playerTerritory != null) {

								// adding country
								playerTerritory.setArmyOnterritory(1);
								currentPlayer.getPlayerterritories().add(playerTerritory);

								countriesList.add(playerTerritory.getContinentName());

								// Fore removing country
								for (Player playertoLst : gamePlayPhase.getPlayerList()) {

									ListIterator<PlayerTerritory> playerTerritories = playertoLst.getPlayerterritories()
											.listIterator();

									while (playerTerritories.hasNext()) {
										if (playerTerritories.next().getTerritoryName()
												.equalsIgnoreCase(playerTerritory.getTerritoryName())) {
											playerTerritories.remove();
										}
									}
								}

							}

						}

					}

				}

			}

		}
		
		gamePlayPhase.setStatus(
				"Territories won by Cheater: " + StringUtils.collectionToCommaDelimitedString(countriesList) + "\n");
		riskPlayImpl.checkForWinner(gamePlayPhase);
		
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
		Player currentPlayer = null;
		for (Player player : gamePlayPhase.getPlayerList()) {
			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
				currentPlayer = player;
				break;
			} else {
				continue;
			}
		}
		if (currentPlayer != null) {

			List<String> playerOwnCountries = riskPlayImpl.getPlayersCountries(currentPlayer);

			for (PlayerTerritory territory : currentPlayer.getPlayerterritories()) {

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

		}

		gamePlayPhase.setStatus("Territories fortified by Cheater: "
				+ StringUtils.collectionToCommaDelimitedString(countriesList) + "\n");

		return gamePlayPhase;
	}

}
