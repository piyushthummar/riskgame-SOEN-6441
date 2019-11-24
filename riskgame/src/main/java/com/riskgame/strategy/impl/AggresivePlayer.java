/**
 * 
 */
package com.riskgame.strategy.impl;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.RiskPlayImpl;
import com.riskgame.strategy.StrategyInterface;

/**
 * Concrete implementation of Strategy interface of design pattern for Aggresive
 * strategy of player
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 * @see com.riskgame.strategy
 */
public class AggresivePlayer implements StrategyInterface {

	private MapManagementInterface mapManagementImpl;
	private RiskPlayInterface riskPlayImpl;

	public static StringBuilder sb;
	private static String NEWLINE = System.getProperty("line.separator");

	public AggresivePlayer() {
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
		PlayerTerritory playerStrongestTerritory = null;
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

				if (currentPlayer.getCardListOwnedByPlayer().size() >= 5) {

					gamePlayPhase.setRiskCardExchange(riskPlayImpl.prepareCard(currentPlayer));

					if (gamePlayPhase.getRiskCardExchange() != null) {
						sb.append("Army Stock before Card Trade: \"\n"
								+ "								+ currentPlayer.getPlayerReinforceArmy() + \"\\n3 Cards Traded.\\n");
						// gamePlayPhase.setStatus("Army Stock before Card Trade: "+
						// currentPlayer.getPlayerReinforceArmy() + "\n3 Cards Traded.\n");

						riskPlayImpl.exchangeCards(gamePlayPhase);

						sb.append("\n" + "New Army Stock after Card Trade: ")
								.append(currentPlayer.getPlayerReinforceArmy()).append(gamePlayPhase.getStatus())
								.append(NEWLINE);
						// gamePlayPhase.setStatus("\n" + "New Army Stock after Card Trade: " +
						// currentPlayer.getPlayerReinforceArmy() + gamePlayPhase.getStatus());

					} else {
						sb.append("No Card for exchange.\n");
					}
				}

				//playerStrongestTerritory = riskPlayImpl.findStrongestTerritory(currentPlayer);
			}
		}
		
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#attack(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase attack(GamePlayPhase gamePlayPhase) {
		// TODO Auto-generated method stub
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#fortify(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase fortify(GamePlayPhase gamePlayPhase) {
		// TODO Auto-generated method stub
		return gamePlayPhase;
	}

}
