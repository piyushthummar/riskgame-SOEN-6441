/**
 * 
 */
package com.riskgame.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.service.PlayerHandlerInterface;

/**
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.PlayerTerritory
 * @see com.riskgame.service.PlayerHandlerInterface
 * @see com.riskgame.model.GamePlayPhase
 */
public class PlayerHandlerImpl implements PlayerHandlerInterface{
	private List<Player> playerInformation;
	/** 
	 * @see com.riskgame.service.PlayerHandlerInterface#createPlayer(int)
	 */
	@Override
	public GamePlayPhase createPlayer(int noOfPlayer) {
		playerInformation = new ArrayList<Player>();
		int armyPerPlayer = findTotalArmy(noOfPlayer);
		for(int i=0;i<noOfPlayer;i++)
		{
			
			List<PlayerTerritory> playerTerritoryList = new ArrayList<>();
			String playerName = "player" + i;
			Player player = new Player();
			player.setPlayerId(i);
			player.setPlayerName(playerName);
			player.setPlayerterritories(playerTerritoryList);
			player.setArmyOwns(armyPerPlayer);
			playerInformation.add(player);
		}
		return null;
	}
	/** 
	 * @see com.riskgame.service.PlayerHandlerInterface#findTotalArmy(int)
	 */
	@Override
	public int findTotalArmy(int noOfPlayer) {
		int armyPerPlayer = 0;
		if (noOfPlayer == 2) {
			armyPerPlayer = 40;
		} else if (noOfPlayer == 3) {
			armyPerPlayer = 35;
		} else if (noOfPlayer == 4) {
			armyPerPlayer = 30;
		} else if (noOfPlayer == 5) {
			armyPerPlayer = 25;
		} else if (noOfPlayer == 6) {
			armyPerPlayer = 20;
		}
		return armyPerPlayer;
	}

}
