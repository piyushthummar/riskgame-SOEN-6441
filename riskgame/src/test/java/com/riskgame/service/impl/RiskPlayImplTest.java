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
	
	List<String> continentsControlledByUser;
	
	RiskMap riskMap;
	
	List<RiskCard> totalCards;
	int noOfCountries;
	RiskCard card;
	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "CAVALRY";
	public static final String ARTILLERY = "ARTILLERY";
	
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
		int expectedUpdateArmy=riskplay.updateArmyAfterCardExchange(player);
		assertEquals(25, expectedUpdateArmy);
		
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
		int expectedUpdateArmy=riskplay.updateArmyAfterCardExchange(player);
		assertNotEquals(25, expectedUpdateArmy);
		
	}
}
