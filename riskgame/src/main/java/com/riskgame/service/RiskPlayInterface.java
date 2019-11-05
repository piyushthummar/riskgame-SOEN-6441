package com.riskgame.service;

import java.util.List;
import java.util.Map;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.model.RiskMap;

/**
 * This is a interface of RiskPlayGame buiness logic. Where reinforcement,attack
 * and fortification happens.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.RiskCardExchange
 * @see com.riskgame.model.RiskCard
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
	 * @return 
	 */
	Map<Integer, String> makeCards(int noOfCountries);
	
	/**
	 * This method will be called to check if player control all the territories of particular continent
	 * 
	 * @param gamePlayPhase
	 * @return value of continent control to update army
	 */
	int checkForContinentControlValue(GamePlayPhase gamePlayPhase);
	
	/**
	 * This method will update armyCount after card exchange
	 * 
	 * @param player
	 */
	int updateArmyAfterCardExchange(Player player);
	
	/**
	 * This method will be called after card exchange has been done and card need to add again to total card list
	 * @param player
	 * @param cards total cards in game
	 * @param exchangedCards
	 */
	void updateCardListAfterExchange(Player player,List<RiskCard> cards,RiskCardExchange exchangedCards);

	boolean validateFromCountry(String fromCountry, Player player);

	boolean validateToCountry(String fromCountry, String toCountry, RiskMap riskMap, Player player);

	boolean validateAttackerDice(int dies, String fromCountry, Player player);

	Player getPlayerByCountry(String country, List<Player> playerList);

	boolean validateDefenderDice(int dies, String toCountry, Player player);
}
