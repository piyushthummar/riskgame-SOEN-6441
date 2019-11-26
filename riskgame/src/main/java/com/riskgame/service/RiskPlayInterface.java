package com.riskgame.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.model.RiskMap;

/**
 * This is a interface of RiskPlayGame business logic. Where
 * reinforcement,attack and fortification happens.
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
	 * @param totalOwnedCountries is a number of country owned by player
	 * @return army to reinforce
	 */
	int checkForReinforcement(int totalOwnedCountries, Player player, String fileName);

	/**
	 * This method will make number of cards same as number of countries to use it
	 * at the phase of reinforcement
	 * 
	 * @param noOfCountries
	 * @return List of riskcard
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
	 * @return player model
	 */
	Player updateArmyAfterCardExchange(Player player);

	/**
	 * This method will be called after card exchange has been done and card need to
	 * add again to total card list
	 * 
	 * @param player
	 * @param cards          total cards in game
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
	 * This method will validate the number of dice given by attacker
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
	 * @param playerName    is the name of the player who want to add list of cards
	 * @param gamePlayPhase
	 */
	void addRiskCardToPlayer(String playerName, GamePlayPhase gamePlayPhase);

	/**
	 * This method will check if player can exchange card to get more army based on
	 * risk rules.
	 * 
	 * @param cardExchange has 3 card, which player want to exchnage
	 * @return true if player can exchnage card
	 */
	boolean checkForExchange(RiskCardExchange cardExchange);

	/**
	 * This method will return list of card number current player is having.
	 * 
	 * @param player is current player in game
	 * @return
	 */
	List<Integer> getCardNumbersFromPlayer(Player player);

	/**
	 * This method will return particular card whose number is provided from current
	 * player risk card list
	 * 
	 * @param player     is current player
	 * @param cardNumber is which card this method need to return
	 * @return RiskCard object whose number is provided
	 */
	RiskCard getCardBycardNumberofPlayer(Player player, int cardNumber);

	/**
	 * This method will return percentage string of percentage of map covered by
	 * given player
	 * 
	 * @param player
	 * @param totalCountry
	 * @return
	 */
	String getPlayerPercentageByCountry(Player player, int totalCountry);

	/**
	 * This method will return army count of given player
	 * 
	 * @param players
	 * @param currentPlayer
	 * @return army count of player
	 */
	int getTotalArmyByPlayer(List<Player> players, Player currentPlayer);

	/**
	 * This method prepares a CardTrade Object for any Computer Player
	 * 
	 * @param currentPlayer Current player who is about to exchange card
	 * @return RiskCardExchange has the set of three cards to be exchange
	 */
	RiskCardExchange prepareCard(Player currentPlayer);

	/**
	 * This method exchange cards during reinforcement phase of the game
	 * 
	 * @param gamePlayPhase State of the game at point of time holding the entire
	 *                      info about game. Like the current phase and player.
	 * @return gamePlayPhase after updating info on exchange cards.
	 */
	GamePlayPhase exchangeCards(GamePlayPhase gamePlayPhase);

	/**
	 * This method updates current player's card list also free card lists after
	 * player's exchange cards
	 * 
	 * @param player        current Player.
	 * @param freeRiskCards list of freeRiskCards
	 * @param exchangeCard  3 card of current Users which will be exchange
	 */
	void updateCardList(Player player, List<RiskCard> freeRiskCards, RiskCardExchange exchangeCard);

	/**
	 * This method finds and returns the strongest territory that the current player
	 * 
	 * @param player Currently player in game.
	 * @return PlayerTerritory Strongest Territory.
	 */
	PlayerTerritory getStrongestTerritory(Player player);

	/**
	 * This method will return list of continent whose all countries occupied by
	 * given player in given map
	 * 
	 * @param player
	 * @param fileName
	 * @return list of continent
	 */
	List<String> getContinentControlledByPlayer(Player player, String fileName);

	/**
	 * This method will return dice count for attacker based on his army.
	 * 
	 * @param currentArmy
	 * @return count of dice attacker can play with
	 */
	int getAttackerDiesCount(int currentArmy);

	/**
	 * This method will return current army on particular country given
	 * 
	 * @param country
	 * @param playerList
	 * @return number of army on country
	 */
	int getCurrentAramyByCountryName(String country, List<Player> playerList);

	/**
	 * This method will return whole object of PlayerTerritory of given player based
	 * upon given territory(country) name
	 * 
	 * @param country is name of country whose object is needed
	 * @param player  is the player object whose PlayerTerritory needed
	 * @return
	 */
	PlayerTerritory getPlayerTerritoryByCountryName(String country, Player player);

	/**
	 * This method will return number of dice defender can use based upon number of
	 * army he is having
	 * 
	 * @param currentArmy is number of army defender is having
	 * @return number of dice defender can have
	 */
	int getDefenderDiceCount(int currentArmy);

	/**
	 * This method will return one list having count of generated dice
	 * 
	 * @param dice number of dice selected from user
	 * @return sorted list of dice
	 */
	List<Integer> getCountFromDies(int dice);

	/**
	 * Generate random number for dice with random function
	 * 
	 * @param min number provided
	 * @param max number provided
	 * @return random number for dice
	 */
	int generateRandomIntRange(int min, int max);

	/**
	 * This method will give total countries from the map.
	 * 
	 * @param riskmap of current game
	 * @return 
	 */
	GamePlayPhase checkForWinner(GamePlayPhase gamePlayPhase);

	/**
	 * This method will give total countries from the map.
	 * 
	 * @param riskmap of current game
	 */
	int getTotalCountries(RiskMap riskMap);

	/**
	 * This method finds and returns the weakest territory that the current player
	 * 
	 * @param player Currently player in game.
	 * @return PlayerTerritory weakest Territory.
	 */
	PlayerTerritory getWeakestTerritory(Player player);

	/**
	 * This method will convert gameplayphase object to JSON file and save the game
	 * 
	 * @param gamePlayPhase current phase of game
	 * @throws FileNotFoundException,       IOException
	 * @throws UnsupportedEncodingException
	 */
	void convertObjectToJsonFile(GamePlayPhase gamePlayPhase, String fileName)
			throws UnsupportedEncodingException, FileNotFoundException, IOException;

	/**
	 * This method will convert JSON file to GamePlayPhase object while loading game
	 * 
	 * @param fileName is JSON file which you want to load
	 * @return GamePlayPhase object
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	GamePlayPhase convertJsonFileToObject(String fileName) throws FileNotFoundException, IOException;

	/**
	 * This method will return list of available saved game file's names
	 * 
	 * @return List of available saved games
	 */
	public List<String> getAvailableGameFiles();

	/**
	 * This method will return list of player names of given player
	 * 
	 * @param player
	 * @return list of player country's names
	 */
	List<String> getPlayersCountries(Player player);

	/**
	 * This method will given playerTerritory model of given country name and list
	 * of playerList
	 * 
	 * @param countryName
	 * @param playerList
	 * @return PlayerTerritory object (model)
	 */
	PlayerTerritory getPlayerTerritoryByCountry(String countryName, List<Player> playerList);

	/**
	 * This method will return new army count after every time player exchange cards
	 * 
	 * @param player
	 * @return no of increasing army count after card exchange
	 */
	int newArmyAfterCardExchange(Player player);
}
