/**
 * 
 */
package com.riskgame.service;

import com.riskgame.model.GamePlayPhase;
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
	
}
