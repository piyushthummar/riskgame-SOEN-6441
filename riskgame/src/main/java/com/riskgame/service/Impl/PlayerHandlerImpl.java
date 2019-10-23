/**
 * 
 */
package com.riskgame.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riskgame.model.Continent;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;
import com.riskgame.model.Territory;
import com.riskgame.service.PlayerHandlerInterface;

/**
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.PlayerTerritory
 * @see com.riskgame.service.PlayerHandlerInterface
 * @see com.riskgame.model.GamePlayPhase
 */

@Service
public class PlayerHandlerImpl implements PlayerHandlerInterface {

	@Autowired
	private MapManagementImpl mapManagementImpl;

	/**
	 * {@inheritDoc}
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

	/**
	 * {@inheritDoc}
	 * @see com.riskgame.service.PlayerHandlerInterface#populateTerritoriesRandomly(com.riskgame.model.RiskMap)
	 */
	@Override
	public GamePlayPhase populateTerritoriesRandomly(GamePlayPhase playPhase) {

		List<Player> playerInformation = playPhase.getPlayerList();
		RiskMap riskMap = mapManagementImpl.readMap(playPhase.getFileName());
		List<PlayerTerritory> territoriesOwnedByPlayer = getTerritories(riskMap);
		Collections.shuffle(territoriesOwnedByPlayer);
		int counter = -1;
		for (PlayerTerritory pt : territoriesOwnedByPlayer) {
			counter++;
			if (playerInformation.get(counter) != null) {
				try {
					playerInformation.get(counter).getPlayerterritories().add(pt);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			} else {
				continue;
			}
			if (counter == playerInformation.size() - 1) {
				counter = -1;
			}
		}
		return playPhase;
	}

	/**
	 * {@inheritDoc}
	 * @see com.riskgame.service.PlayerHandlerInterface#getTerritories(com.riskgame.model.RiskMap)
	 */
	@Override
	public List<PlayerTerritory> getTerritories(RiskMap riskMap) {
		Continent continent;
		Map<Integer, Continent> continentsMap = new HashMap<>();
		List<Territory> territoryList;
		continentsMap = riskMap.getContinents();
		List<PlayerTerritory> territoriesOwnedByPlayer = new ArrayList<>();
		for (Entry<Integer, Continent> entry : continentsMap.entrySet()) {
			continent = entry.getValue();
			territoryList = continent.getTerritoryList();
			for (Territory t : territoryList) {
				PlayerTerritory playerTerritory = new PlayerTerritory();
				playerTerritory.setTerritoryName(t.getTerritoryName());
				playerTerritory.setContinentName(continent.getContinentName());
				playerTerritory.setArmyOnterritory(0);
				territoriesOwnedByPlayer.add(playerTerritory);

			}
		}
		return territoriesOwnedByPlayer;
	}

	/**
	 * {@inheritDoc}
	 * @see com.riskgame.service.PlayerHandlerInterface#placeAllArmyByRoundRobin(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase placeAllArmyByRoundRobin(GamePlayPhase gamePlayPhase) {
		
		int armyPerPlayer = findTotalArmy(gamePlayPhase.getPlayerList().size());
		
		
		for (int index = 0; index < gamePlayPhase.getPlayerList().size(); index++) {
			gamePlayPhase.getPlayerList().get(index).setArmyOwns(armyPerPlayer);
			int track = 0;
			for (int territoryIndex = 0; territoryIndex < gamePlayPhase.getPlayerList().get(index).getPlayerterritories().size(); territoryIndex++) {
				if (armyPerPlayer >= gamePlayPhase.getPlayerList().get(index).getPlayerterritories().size()) {
					if (track < armyPerPlayer) {
						int armies = gamePlayPhase.getPlayerList().get(index).getPlayerterritories().get(territoryIndex).getArmyOnterritory() + 1;
						gamePlayPhase.getPlayerList().get(index).getPlayerterritories().get(territoryIndex).setArmyOnterritory(armies);
						if (territoryIndex + 1 == gamePlayPhase.getPlayerList().get(index).getPlayerterritories().size()) {
							territoryIndex = -1;
						}
						track++;
					} else {
						break;
					}
				} else {
					int armies = gamePlayPhase.getPlayerList().get(index).getPlayerterritories().get(territoryIndex).getArmyOnterritory() + 1;
					gamePlayPhase.getPlayerList().get(index).getPlayerterritories().get(territoryIndex).setArmyOnterritory(armies);
				}
			}
		}
		return gamePlayPhase;
	}

}
