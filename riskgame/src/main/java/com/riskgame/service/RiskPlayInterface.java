package com.riskgame.service;


/**
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.Territory 
 * com.riskgame.model.PlayerTerritory
 * */
public interface RiskPlayInterface {
	
	int checkForReinforcement(int totalOwnedCountries);

}
