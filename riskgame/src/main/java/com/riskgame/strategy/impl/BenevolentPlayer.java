/**
 * 
 */
package com.riskgame.strategy.impl;

import java.util.ArrayList;
import java.util.List;

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

				playerWeakestTerritory = riskPlayImpl.getWeakestTerritory(currentPlayer);
				if (playerWeakestTerritory != null) {
					List<Integer> armies_no_list = getListofArmies(currentPlayer.getPlayerterritories());
					
					if(armies_no_list.size()>0) {
						
						
						List<PlayerTerritory> weak_territories_list = getWeakTerritories(armies_no_list,currentPlayer.getPlayerterritories());
						
						
						
					}
					
					
					
				}

			}

		}

		return gamePlayPhase;
	}

	private List<PlayerTerritory> getWeakTerritories(List<Integer> armies_no_list,
			List<PlayerTerritory> playerterritories) {
		// TODO Auto-generated method stub
		return null;
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
