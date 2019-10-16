/**
 * 
 */
package com.riskgame.service;

import java.util.List;
import java.util.Map;

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
	int findTotalArmy(int noOfPlayer);
	GamePlayPhase populateTerritoriesByRoundRobbin(GamePlayPhase playPhase);
	List<PlayerTerritory> getTerritories(RiskMap riskMap);
	GamePlayPhase placeAll(GamePlayPhase gamePlayPhase);
	
}
