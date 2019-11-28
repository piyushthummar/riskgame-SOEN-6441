/**
 * 
 */
package com.riskgame.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.PlayerHandlerImpl;
import com.riskgame.strategy.impl.AggresivePlayer;
import com.riskgame.strategy.impl.BenevolentPlayer;
import com.riskgame.strategy.impl.CheaterPlayer;
import com.riskgame.strategy.impl.HumanPlayer;
import com.riskgame.strategy.impl.RandomPlayer;

/**
 * This is the test cases file for PlayerHandlerImpl services.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 * @see com.riskgame.service.Impl.PlayerHandlerImpl
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerHandlerImplTest {

	public static final int NUMBER_OF_PLAYER = 6;
	public static final String VALID_MAP_NAME = "world.map";
	public static final String VALID_SMALL_MAP1 = "validSmall.map";
	public static final String VALID_CONQUEST_MAP_NAME = "worldconquest.map";
	public static final List<String> territoryList = new ArrayList<>();
	public static RiskMap riskMap;
	public static GamePlayPhase gamePlayPhase = new GamePlayPhase();
	List<Player> plist;

	@Autowired
	MapManagementImpl map;

	@Autowired
	PlayerHandlerImpl playerHandler;

	/**
	 * Setup methods to set objects
	 */
	@Before
	public void setup() {
		riskMap = map.readMap(VALID_SMALL_MAP1);
		territoryList.add("india");
		territoryList.add("china");
		territoryList.add("japan");
		territoryList.add("CA");
		territoryList.add("USA");
		territoryList.add("test");

		plist = new ArrayList<Player>();
		Player playerOne = new Player();
		Player playerTwo = new Player();

		plist.add(playerOne);
		plist.add(playerTwo);
		gamePlayPhase.setFileName(VALID_MAP_NAME);
		gamePlayPhase.setPlayerList(plist);

	}

	/**
	 * Test for validTestForFindTotalArmy.
	 * 
	 * @result
	 */
	@Test
	public void validTestForFindTotalArmy() {
		int totalArmy = playerHandler.findTotalArmy(NUMBER_OF_PLAYER);
		assertEquals(20, totalArmy);

	}

	/**
	 * Test for inValidTestForFindTotalArmy.
	 * 
	 * @result
	 */
	@Test
	public void inValidTestForFindTotalArmy() {
		int totalArmy = playerHandler.findTotalArmy(NUMBER_OF_PLAYER);
		assertNotEquals(10, totalArmy);

	}

	/**
	 * Test for notEmptyTerritoriesTest.
	 * 
	 * @result
	 */
	@Test
	public void notEmptyTerritoriesTest() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		List<PlayerTerritory> playerTerritories = playerHandler.getTerritories(riskMap);
		assertEquals(42, playerTerritories.size());
	}

	/**
	 * This method will Test GetTerritories method of Player Handler, which will
	 * return number of territories from provided map. This test method will return
	 * true if List generated is correct.
	 */
	@Test
	public void testGetTerritories() {
		List<PlayerTerritory> territories = playerHandler.getTerritories(riskMap);
		List<String> territoriesStringList = territories.stream().map(e -> e.getTerritoryName())
				.collect(Collectors.toList());
		boolean result = territoryList.containsAll(territoriesStringList);
		assertEquals(true, result);
	}

	/**
	 * This method will Test populateTerritoriesRandomly which will populate
	 * countries randomly to player return same Gameplayphase object true if
	 * Gameplayephase is equal.
	 */
	@Test
	public void populateTerritoriesRandomlyTest() {

		GamePlayPhase gamePlayPhaseReturn = playerHandler.populateTerritoriesRandomly(gamePlayPhase);
		assertEquals(gamePlayPhaseReturn, gamePlayPhase);

	}
	/**
	 * This method will Test populateTerritoriesRandomly For Conquest Map File which will populate
	 * countries randomly to player return same Gameplayphase object true if
	 * Gameplayephase is equal.
	 */
	@Test
	public void populateTerritoriesRandomlyTestForConquest() {
		gamePlayPhase.setFileName(VALID_CONQUEST_MAP_NAME);
		gamePlayPhase.setPlayerList(plist);
		GamePlayPhase gamePlayPhaseReturn = playerHandler.populateTerritoriesRandomly(gamePlayPhase);
		assertEquals(gamePlayPhaseReturn, gamePlayPhase);

	}
	/**
	 * This method will test placeAllArmyByRoundRobin which will place all
	 * armies in round robin manner to each player return same Gameplayphase object true if
	 * Gameplayephase is equal.
	 */
	@Test
	public void validPlaceAllArmyByRoundRobin() {
		gamePlayPhase.setFileName(VALID_MAP_NAME);
		gamePlayPhase.setPlayerList(plist);
		gamePlayPhase = playerHandler.populateTerritoriesRandomly(gamePlayPhase);
		GamePlayPhase gamePlayPhaseReturn = playerHandler.placeAllArmyByRoundRobin(gamePlayPhase);
		assertEquals(gamePlayPhaseReturn, gamePlayPhase);

	}
	/**
	 * This method will test getStrategyByNameTest which will return the object as defined by strategy
	 * if strategy is not present in strategy list than it returns null
	 * 
	 */
	@Test
	public void getStrategyByNameTest() {
		//Object human = new HumanPlayer();
		assertEquals(null, playerHandler.getStrategyByName("COMPUTER"));

	}
	/**
	 * This method will test getStrategyByNameTest which will return the object as defined by strategy
	 * if strategy is not present in strategy list than it returns null
	 * 
	 */
	@Test
	public void getStrategyByNameTestHuman() {
		Object object = new HumanPlayer();
		boolean expected = object instanceof HumanPlayer;
		Object output = playerHandler.getStrategyByName("HUMAN");
		boolean actual = output instanceof HumanPlayer;
		assertEquals(expected, actual);
		
		assertEquals(null, playerHandler.getStrategyByName("COMPUTER"));

	}
	/**
	 * This method will test getStrategyByNameTest which will return the object as defined by strategy
	 * if strategy is not present in strategy list than it returns null
	 * 
	 */
	@Test
	public void getStrategyByNameTestAggresive() {
		Object object = new AggresivePlayer();
		boolean expected = object instanceof AggresivePlayer;
		Object output = playerHandler.getStrategyByName("AGGRESIVE");
		boolean actual = output instanceof AggresivePlayer;
		assertEquals(expected, actual);

	}
	/**
	 * This method will test getStrategyByNameTest which will return the object as defined by strategy
	 * if strategy is not present in strategy list than it returns null
	 * 
	 */
	@Test
	public void getStrategyByNameTestBenevolent() {
		Object object = new BenevolentPlayer();
		boolean expected = object instanceof BenevolentPlayer;
		Object output = playerHandler.getStrategyByName("BENEVOLENT");
		boolean actual = output instanceof BenevolentPlayer;
		assertEquals(expected, actual);

	}
	/**
	 * This method will test getStrategyByNameTest which will return the object as defined by strategy
	 * if strategy is not present in strategy list than it returns null
	 * 
	 */
	@Test
	public void getStrategyByNameTestRandom() {
		Object object = new RandomPlayer();
		boolean expected = object instanceof RandomPlayer;
		Object output = playerHandler.getStrategyByName("RANDOM");
		boolean actual = output instanceof RandomPlayer;
		assertEquals(expected, actual);
	}
	/**
	 * This method will test getStrategyByNameTest which will return the object as defined by strategy
	 * if strategy is not present in strategy list than it returns null
	 * 
	 */
	@Test
	public void getStrategyByNameTestCheater() {
		Object object = new CheaterPlayer();
		boolean expected = object instanceof CheaterPlayer;
		Object output = playerHandler.getStrategyByName("CHEATER");
		boolean actual = output instanceof CheaterPlayer;
		assertEquals(expected, actual);

	}
}
