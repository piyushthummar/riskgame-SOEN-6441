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
	 * This method will check in new turn how many number of army player can get for
	 * reinforcement.
	 * 
	 * @param totalOwnedCountries
	 *            is a number of country owned by player
	 * @return army to reinforce
	 */
	int checkForReinforcement(int totalOwnedCountries, Player player, String fileName);

	/**
	 * This method will make number of cards same as number of countries to use it
	 * at the phase of reinforcement
	 * 
	 * @param noOfCountries
	 * @return
	 */
	List<RiskCard> makeCards(int noOfCountries);

	/**
	 * This method will be called to check if player control all the territories of
	 * particular continent
	 * 
	 * @param gamePlayPhase
	 * @return value of continent control to update army
	 */
	int checkForContinentControlValue(Player player, String fileName);

	/**
	 * This method will update armyCount after card exchange
	 * 
	 * @param player
	 */
	int updateArmyAfterCardExchange(Player player);

	/**
	 * This method will be called after card exchange has been done and card need to
	 * add again to total card list
	 * 
	 * @param player
	 * @param cards
	 *            total cards in game
	 * @param exchangedCards
	 */
	void updateCardListAfterExchange(Player player, List<RiskCard> cards, RiskCardExchange exchangedCards);

	/**
	 * This method will validate if given attacker country is owned by given player
	 * 
	 * @param fromCountry
	 * @param player
	 * @return true if country belongs to player
	 */
	boolean validateFromCountry(String fromCountry, Player player);

	/**
	 * This method will validate if given defender country is owned by given player
	 * 
	 * @param fromCountry
	 * @param toCountry
	 * @param riskMap
	 * @param player
	 * @return true if country belongs to that player
	 */
	boolean validateToCountry(String fromCountry, String toCountry, RiskMap riskMap, Player player);

	/**
	 * This method wil validate the number of dice given by attacker
	 * 
	 * @param dies
	 * @param fromCountry
	 * @param player
	 * @return true if number of dice is valid according to army on that territory
	 */
	boolean validateAttackerDice(int dies, String fromCountry, Player player);

	/**
	 * This method will return the player object, who owns given country
	 * 
	 * @param country
	 * @param playerList
	 * @return player object
	 */
	Player getPlayerByCountry(String country, List<Player> playerList);

	/**
	 * This method will validate defender dice given by defender based on army on
	 * given country
	 * 
	 * @param dies
	 * @param toCountry
	 * @param player
	 * @return true of number of dice given by defender is true
	 */
	boolean validateDefenderDice(int dies, String toCountry, Player player);

	/**
	 * This method will assign 1 risk card randomly from original list of cards to
	 * player
	 * 
	 * @param playerName
	 *            is the name of the player who want to add list of cards
	 * @param gamePlayPhase
	 */
	void addRiskCardToPlayer(String playerName, GamePlayPhase gamePlayPhase);

	/**
	 * This method will check if player can exchange card to get more army based on
	 * risk rules.
	 * 
	 * @param cardExchange
	 *            has 3 card, which player want to exchnage
	 * @return true if player can exchnage card
	 */
	boolean checkForExchange(RiskCardExchange cardExchange);

	/**
	 * This method will return list of card number current player is having.
	 * 
	 * @param player
	 *            is current player in game
	 * @return
	 */
	List<Integer> getCardNumbersFromPlayer(Player player);

	/**
	 * This method will return particular card whose number is provided from current
	 * player risk card list
	 * 
	 * @param player
	 *            is current player
	 * @param cardNumber
	 *            is which card this method need to return
	 * @return RiskCard object whose number is provided
	 */
	RiskCard getCardBycardNumberofPlayer(Player player, int cardNumber);

	String getPlayerPercentageByCountry(Player player, int totalCountry);
}
