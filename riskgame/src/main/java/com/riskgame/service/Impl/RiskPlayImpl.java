package com.riskgame.service.Impl;

import org.springframework.stereotype.Service;

import com.riskgame.service.RiskPlayInterface;

/**
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.Territory 
 * com.riskgame.model.PlayerTerritory
 * */

@Service
public class RiskPlayImpl implements RiskPlayInterface {
	
	/**
	 * @param armyToPlace
	 * @param totalOwnedCountries
	 * @param addExtraArmy
	 * @return
	 */
	@Override
	public int checkForReinforcement(int totalOwnedCountries) {
		int total = Math.floorDiv(totalOwnedCountries, 3);
		int totalArmyforReinforce = Math.max(total, 3);
		return totalArmyforReinforce;

	}

}
