package com.riskgame.service;

import com.riskgame.model.GamePlayPhase;

/**
 * This is a interface of RiskPlayGame buiness logic. Where reinforcement,attack
 * and fortification happens.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.Territory com.riskgame.model.PlayerTerritory
 */
public interface RiskPlayInterface {

	/**
	 * This method will check in new turn how many number of army player can get for reinforcement.
	 * @param totalOwnedCountries is a number of country owned by player
	 * @return army to reinforce
	 */
	int checkForReinforcement(int totalOwnedCountries);
	
	/**
	 * This method will make number of cards same as number of countries to use it at the phase of reinforcement
	 * @param noOfCountries
	 */
	void makeCards(int noOfCountries);
	
	int checkForContinentControlValue(GamePlayPhase gamePlayPhase);
}
