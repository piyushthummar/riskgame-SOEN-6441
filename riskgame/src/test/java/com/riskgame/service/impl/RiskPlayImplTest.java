/**
 * 
 */
package com.riskgame.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.model.RiskMap;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.service.Impl.RiskPlayImpl;

/**
 * This Test class will check all RiskplayImpl methods, which is business logic
 * related to game play.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.service.Impl.RiskPlayImpl
 * @see com.riskgame.service.RiskPlayInterface
 * @version 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RiskPlayImplTest {
	GamePlayPhase phase = new GamePlayPhase();
	public static final int COUNTRIES_COUNT = 20;
	
	@Autowired
	RiskPlayInterface riskplay;

	@Autowired
	MapManagementInterface mapManagement;
	
	Player player;
	PlayerTerritory playerTerritory;
	int countriesOwned;
	List<PlayerTerritory> ptList;
	String FILE_NAME = "risk.map";
	String CONQUEST_FILE_NAME = "worldconquest.map";
	List<Player> playerList = new ArrayList<>();
	
	
	List<String> continentsControlledByUser;
	
	RiskMap riskMap;
	
	List<RiskCard> totalCards;
	int noOfCountries;
	RiskCard card;
	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "CAVALRY";
	public static final String ARTILLERY = "ARTILLERY";
	
	/**
	 * Thus is setup method to setup objects before running the test file.
	 */
	@Before
	public void beforeTest()
	{
		riskMap = mapManagement.readMap(FILE_NAME);
		
		player = new Player();
		playerTerritory = new PlayerTerritory();
		ptList = new ArrayList<PlayerTerritory>();

		playerTerritory.setArmyOnterritory(1);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Venezuela");
		ptList.add(playerTerritory);
		
		playerTerritory.setArmyOnterritory(2);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Peru");
		ptList.add(playerTerritory);
		
		playerTerritory.setArmyOnterritory(3);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Brazil");
		ptList.add(playerTerritory);
		
		playerTerritory.setArmyOnterritory(4);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Argentina");
		ptList.add(playerTerritory);
		
		player.setPlayerId(1);
		player.setPlayerName("Tester");
		player.setPlayerterritories(ptList);
		playerList.add(player);
		
		continentsControlledByUser = new ArrayList<>();
		
		noOfCountries = 3;
		totalCards = new ArrayList<>();
		card = new RiskCard();
		card.setArmyType(INFANTRY);
		card.setCardNumber(1);
		totalCards.add(card);
		card = new RiskCard();
		card.setArmyType(ARTILLERY);
		card.setCardNumber(2);
		totalCards.add(card);
		card = new RiskCard();
		card.setArmyType(CAVALRY);
		card.setCardNumber(3);
		totalCards.add(card);
		
		
	}
	
	/**
	 * Test for validTestforCalculationofNumberOfReinforcementArmies.
	 * 
	 * @result based on countries count method will return number of reinforcement
	 *         armies
	 */
	@Test
	public void validTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(countriesOwned, player, FILE_NAME);
		assertEquals(3, count);
	}

	/**
	 * Test for InvalidTestforCalculationofNumberOfReinforcementArmies.
	 * 
	 * @result based on countries count method will return number of reinforcement
	 *         armies
	 */
	@Test
	public void InvalidTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(countriesOwned, player, FILE_NAME);
		assertNotEquals(4, count);
	}
	
	
	/**
	 * Test for validTestOfCheckForContinentControlValue.
	 * 
	 * @result based on countries count method will return a number of territories
	 *         of continent
	 */
	@Test
	public void validTestOfCheckForContinentControlValue() {
		int controlValue = riskplay.checkForContinentControlValue(player, FILE_NAME);
		assertEquals(0, controlValue);
	}
	/**
	 * Test for validTestOfCheckForContinentControlValue.
	 * 
	 * @result based on countries count method will return a number of territories
	 *         of continent
	 */
	@Test
	public void validTestOfCheckForContinentControlValueOfConquest() {
		int controlValue = riskplay.checkForContinentControlValue(player, CONQUEST_FILE_NAME);
		assertEquals(0, controlValue);
	}
	/**
	 * Test for validTestOfCheckForContinentControlValue.
	 * 
	 * @result based on countries count method will return a number of territories
	 *         of continent
	 */
	@Test
	public void invalidTestOfCheckForContinentControlValue() {
		int controlValue = riskplay.checkForContinentControlValue(player, FILE_NAME);
		assertNotEquals(4, controlValue);
	}
	
	/**
	 * Test for validTestForValidateFromCountry.
	 * 
	 * @result based on attack phase that from country is from player is valid or not
	 *        
	 */
	@Test
	public void validTestForValidateFromCountry() {
		assertEquals(true, riskplay.validateFromCountry("Argentina", player));
	}
	
	/**
	 * Test for invalidTestForValidateFromCountry.
	 * 
	 * @result based on attack phase that from country is from player is valid or not
	 *         
	 */
	@Test
	public void invalidTestForValidateFromCountry() {
		assertEquals(false, riskplay.validateFromCountry("India", player));
	}

	/**
	 * Test for validTestForValidateFromCountry.
	 * 
	 * @result based on attack phase that toCountry is from player is valid or not
	 *        
	 */
	@Test
	public void validTestForValidateToCountry() {
		assertEquals(false, riskplay.validateToCountry("Argentina", "India", riskMap, player));
	}
	
	/**
	 * Test for invalidTestForValidateToCountry.
	 * 
	 * @result based on attack phase that toCountry is from player is valid or not
	 *         
	 */
	@Test
	public void invalidTestForValidateToCountry() {
		assertEquals(true, riskplay.validateToCountry("Argentina", "Brazil", riskMap, player));
	}
	
	/**
	 * Test for validTestForMakeCards.
	 * 
	 * @result based on number of countries makeCards method will return List of cards
	 * 
	 */
	@Test
	public void validTestForMakeCards() {
		
		List<RiskCard> resultTotalCards = new ArrayList<>();
		resultTotalCards = riskplay.makeCards(noOfCountries);
		assertNotEquals(totalCards, resultTotalCards);	
	}
	
	/**
	 * Test for invalidTestForMakeCards.
	 * 
	 * @result based on number of countries makeCards method will return List of cards
	 * 
	 */
	@Test
	public void invalidTestForMakeCards() {
		noOfCountries = 5;
		List<RiskCard> resultTotalCards = new ArrayList<>();
		resultTotalCards = riskplay.makeCards(noOfCountries);
		assertNotEquals(totalCards, resultTotalCards);	
	}
	
	/**
	 * Test for validUpdateArmyAfterCardExchange.
	 * 
	 * @result based on count of it will update Army and returns updateArmy
	 * 
	 */
	@Test
	public void validUpdateArmyAfterCardExchange() {
		player.setExchangeCount(5);
		Player expectedUpdateArmy=riskplay.updateArmyAfterCardExchange(player);
		player.setExchangeCount(6);
		player.setPlayerReinforceArmy(30);
		assertEquals(player, expectedUpdateArmy);
		
	}
	
	/**
	 * Test for invalidUpdateArmyAfterCardExchange.
	 * 
	 * @result based on count of it will update Army and returns updateArmy
	 * 
	 */
	@Test
	public void invalidUpdateArmyAfterCardExchange() {
		player.setExchangeCount(6);
		Player playerReturn = riskplay.updateArmyAfterCardExchange(player);
		assertNotEquals(25, playerReturn.getPlayerReinforceArmy());
		
	}
	/**
	 * Test for validGetAttackerDiesCount.
	 * 
	 * @result based on current Army return dice count for attacker.
	 * 
	 */
	@Test
	public void validGetAttackerDiesCount() {
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		assertEquals(3, riskplayimpl.getAttackerDiesCount(5));
		assertEquals(2, riskplayimpl.getAttackerDiesCount(3));
		assertEquals(1, riskplayimpl.getAttackerDiesCount(2));
	}
	
	/**
	 * Test for invalidGetAttackerDiesCount.
	 * 
	 * @result based on current Army return dice count for attacker.
	 * 
	 */
	@Test
	public void invalidGetAttackerDiesCount() {
		int CurrentArmy=5;
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int numDices = riskplayimpl.getAttackerDiesCount(CurrentArmy);
		assertNotEquals(2, numDices);
		
	}
	/**
	 * Test for validGetDefenderDiceCount.
	 * 
	 * @result returns number of dice defender can use based upon number of
	 *         army he is having.
	 * 
	 */
	@Test
	public void validGetDefenderDiceCount() {
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		assertEquals(2, riskplayimpl.getDefenderDiceCount(3));
		assertEquals(1, riskplayimpl.getDefenderDiceCount(1));
	}
	
	/**
	 * Test for invalidGetDefenderDiceCount.
	 * 
	 * @result returns number of dice defender can use based upon number of
	 *         army he is having.
	 * 
	 */
	@Test
	public void invalidGetDefenderDiceCount() {
		int CurrentArmy=3;
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int numDices=riskplayimpl.getDefenderDiceCount(CurrentArmy);
		assertNotEquals(1, numDices);
		
	}
	/**
	 * Test for validGetCurrentArmyByCountryName.
	 * 
	 * @result returns number of armies for given territory
	 * 
	 */
	@Test
	public void validGetCurrentArmyByCountryName() {
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int army = riskplayimpl.getCurrentAramyByCountryName("Brazil", playerList);
		assertEquals(0, army);
		
	}
	
	/**
	 * Test for invalidGetCurrentArmyByCountryName.
	 * 
	 * @result returns number of armies for given territory
	 * 
	 */
	@Test
	public void invalidGetCurrentArmyByCountryName() {
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int army = riskplayimpl.getCurrentAramyByCountryName("Brazil", playerList);
		assertNotEquals(4, army);	
	}
	
	/**
	 * Test for validGetPlayerTerritoryByCountryName.
	 * 
	 * @result returns player territory for given territory name
	 * 
	 */
	@Test
	public void validGetPlayerTerritoryByCountryName() {
		PlayerTerritory expectedPlayerTerritory = new PlayerTerritory();
		expectedPlayerTerritory.setArmyOnterritory(4);
		expectedPlayerTerritory.setContinentName("South-America");
		expectedPlayerTerritory.setTerritoryName("Argentina");
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		assertNotEquals(expectedPlayerTerritory, riskplayimpl.getPlayerTerritoryByCountryName("Argentina1", player));
		
	}
	
	/**
	 * Test for invalidGetPlayerTerritoryByCountryName.
	 * 
	 * @result returns player territory for given territory name
	 * 
	 */
	@Test
	public void invalidGetPlayerTerritoryByCountryName() {
		PlayerTerritory expectedPlayerTerritory = new PlayerTerritory();
		expectedPlayerTerritory.setArmyOnterritory(4);
		expectedPlayerTerritory.setContinentName("South-America");
		expectedPlayerTerritory.setTerritoryName("Argentina");
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		assertNotEquals(expectedPlayerTerritory, riskplayimpl.getPlayerTerritoryByCountryName("Brazil", player));	
	}
	/**
	 * Test for GetContinentControlledByPlayer.
	 * 
	 * @result returns number of continent list that is controlled by player.
	 * 
	 */
	@Test
	public void validGetContinentControlledByPlayer() {
		Player testPlayer = new Player();
		PlayerTerritory testPlayerTerritory = new PlayerTerritory();
		testPlayerTerritory.setContinentName("Asia");
		testPlayerTerritory.setTerritoryName("india");
		ArrayList<PlayerTerritory> testPtList = new ArrayList<PlayerTerritory>();
		testPtList.add(testPlayerTerritory);
		continentsControlledByUser = riskplay.getContinentControlledByPlayer(testPlayer, "validsmalltwo.map");
		ArrayList<String> expectedList = new ArrayList<String>();
		assertEquals(expectedList, continentsControlledByUser);
	}
	
	/**
	 * Test for GetContinentControlledByPlayer For Conquest Map.
	 * 
	 * @result returns number of continent list that is controlled by player.
	 * 
	 */
	@Test
	public void validGetContinentControlledByPlayerForConquest() {
		Player testPlayer = new Player();
		PlayerTerritory testPlayerTerritory = new PlayerTerritory();
		testPlayerTerritory.setContinentName("Asia");
		testPlayerTerritory.setTerritoryName("india");
		ArrayList<PlayerTerritory> testPtList = new ArrayList<PlayerTerritory>();
		testPtList.add(testPlayerTerritory);
		continentsControlledByUser = riskplay.getContinentControlledByPlayer(testPlayer, "worldconquest.map");
		ArrayList<String> expectedList = new ArrayList<String>();
		assertEquals(expectedList, continentsControlledByUser);
	}
	/**
	 * Test for validNewArmyAfterCardExchange.
	 * 
	 * @result based on count of it will return updatedArmy after exchange
	 * 
	 */
	@Test
	public void validNewArmyAfterCardExchange() {
		player.setExchangeCount(0);
		assertEquals(0, riskplay.newArmyAfterCardExchange(player));
		
		player.setExchangeCount(1);
		assertEquals(5, riskplay.newArmyAfterCardExchange(player));
		
		player.setExchangeCount(2);
		assertEquals(10, riskplay.newArmyAfterCardExchange(player));		
		
		player.setExchangeCount(3);
		assertEquals(15, riskplay.newArmyAfterCardExchange(player));	
		
		player.setExchangeCount(4);
		assertEquals(20, riskplay.newArmyAfterCardExchange(player));
		
		player.setExchangeCount(5);
		assertEquals(25, riskplay.newArmyAfterCardExchange(player));
		
		player.setExchangeCount(6);
		assertEquals(30, riskplay.newArmyAfterCardExchange(player));
	}
	
	/**
	 * Test for GetPlayerByCountry.
	 * 
	 * @result returns player object that owns the given country
	 * 
	 */
	@Test
	public void validGetPlayerByCountry() {
		assertEquals(player, riskplay.getPlayerByCountry("Argentina", playerList));	
	}
	/**
	 * Test for validCheckForExchange.
	 * 
	 * @result returns true if cards are eligible for exchange
	 * 
	 */
	@Test
	public void validCheckForExchange() {

		RiskCardExchange cardExchange = new RiskCardExchange();
		RiskCard cardOne, cardTwo, cardThree;
		cardOne = new RiskCard();
		cardOne.setArmyType(ARTILLERY);
		cardTwo = new RiskCard();
		cardTwo.setArmyType(CAVALRY);
		cardThree = new RiskCard();
		cardThree.setArmyType(INFANTRY);
		cardExchange.setExchange1(cardOne);
		cardExchange.setExchange2(cardTwo);
		cardExchange.setExchange3(cardThree);
		
		assertEquals(true, riskplay.checkForExchange(cardExchange));	
	}
	
	/**
	 * Test for validGetCardBycardNumberofPlayer.
	 * 
	 * @result returns risk card that is having same number as given
	 * 
	 */
	@Test
	public void validGetCardBycardNumberofPlayer() {

		
		RiskCard cardOne;
		cardOne = new RiskCard();
		cardOne.setArmyType(ARTILLERY);
		cardOne.setCardNumber(5);
		ArrayList<RiskCard> cardList = new ArrayList<RiskCard>();
		cardList.add(cardOne);
		player.setCardListOwnedByPlayer(cardList);
		
		assertEquals(cardOne, riskplay.getCardBycardNumberofPlayer(player, 5));	
	}
	/**
	 * Test for validGetCardNumbersFromPlayer.
	 * 
	 * @result returns card number list that layer is having
	 * 
	 */
	@Test
	public void validGetCardNumbersFromPlayer() {

		
		RiskCard cardOne, cardTwo, cardThree;
		cardOne = new RiskCard();
		cardOne.setArmyType(ARTILLERY);
		cardOne.setCardNumber(5);
		cardTwo = new RiskCard();
		cardTwo.setArmyType(CAVALRY);
		cardTwo.setCardNumber(6);
		cardThree = new RiskCard();
		cardThree.setArmyType(INFANTRY);
		cardThree.setCardNumber(3);
		ArrayList<RiskCard> cardList = new ArrayList<RiskCard>();
		cardList.add(cardOne);
		cardList.add(cardTwo);
		cardList.add(cardThree);
		player.setCardListOwnedByPlayer(cardList);
		
		ArrayList<Integer> cardNumbers = new ArrayList<Integer>();
		cardNumbers.add(5);
		cardNumbers.add(6);
		cardNumbers.add(3);
		assertEquals(cardNumbers, riskplay.getCardNumbersFromPlayer(player));	
	}
	/**
	 * Test for validGetTotalArmyByPlayer.
	 * 
	 * @result returns total armies of all players.
	 * 
	 */
	@Test
	public void validGetTotalArmyByPlayer() {

		assertEquals(16, riskplay.getTotalArmyByPlayer(playerList, player));	
	}
	/**
	 * Test for validGetTotalArmyByPlayer.
	 * 
	 * @result returns total armies of all players.
	 * 
	 */
	@Test
	public void validPrepareCard() {
		RiskCardExchange cardExchange = new RiskCardExchange();
		
		RiskCard cardOne, cardTwo, cardThree;
		cardOne = new RiskCard();
		cardOne.setArmyType(ARTILLERY);
		cardOne.setCardNumber(5);
		cardTwo = new RiskCard();
		cardTwo.setArmyType(CAVALRY);
		cardTwo.setCardNumber(3);
		cardThree = new RiskCard();
		cardThree.setArmyType(INFANTRY);
		cardThree.setCardNumber(6);
		
		cardExchange.setExchange1(cardOne);
		cardExchange.setExchange2(cardTwo);
		cardExchange.setExchange3(cardThree);
		
		ArrayList<RiskCard> cardList = new ArrayList<RiskCard>();
		cardList.add(cardOne);
		cardList.add(cardTwo);
		cardList.add(cardThree);
		player.setCardListOwnedByPlayer(cardList);
		
		assertNotEquals(cardExchange, riskplay.prepareCard(player));	
	}
}
