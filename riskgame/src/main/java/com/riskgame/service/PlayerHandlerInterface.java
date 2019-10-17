/**
 * 
 */
package com.riskgame.service;

import java.util.List;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;

/**
 * This a interface where Playerhandler business logic implemented. This
 * interface has methods which can do add player, populatecountries and place
 * army etc. things.
 * 
 * @author <a href="mailto:r_istry@encs.concordia.ca">Raj Mistry</a>
 * @see com.riskgame.model.GamePlayPhase
 * 
 */
public interface PlayerHandlerInterface {
	/**
	 * This player will return how much army each player can have when number of player vary.
	 * @param noOfPlayer
	 * @return numberArmy each player can have according to the no of player
	 *         entered.
	 */
	int findTotalArmy(int noOfPlayer);

	/**
	 * This method will populate territories randomly
	 * @param playPhase is a GameplayPhase model 
	 * @return GamePlayPhase after territories assign
	 */
	GamePlayPhase populateTerritoriesRandomly(GamePlayPhase playPhase);

	/**
	 * This method will return list of territories player is having from given Riskmap model
	 * @param riskMap
	 * @return
	 */
	List<PlayerTerritory> getTerritories(RiskMap riskMap);

	/**
	 * This method will placeArmy to all player by round robin method
	 * @param gamePlayPhase
	 * @return
	 */
	GamePlayPhase placeAllArmyByRoundRobin(GamePlayPhase gamePlayPhase);

}
