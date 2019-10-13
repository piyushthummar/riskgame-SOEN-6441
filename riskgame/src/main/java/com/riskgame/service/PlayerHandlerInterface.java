/**
 * 
 */
package com.riskgame.service;

import java.util.List;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;

/**
 * 
 * @author <a href="mailto:r_istry@encs.concordia.ca">Raj Mistry</a>
 * @see com.riskgame.model.GamePlayPhase
 * 
 */
public interface PlayerHandlerInterface {
	GamePlayPhase createPlayer(int noOfPlayer);
	int findTotalArmy(int noOfPlayer);
	void populateTerritoriesByRoundRobbin(RiskMap riskMap);
	List<PlayerTerritory> getTerritories(RiskMap riskMap);
	
}
