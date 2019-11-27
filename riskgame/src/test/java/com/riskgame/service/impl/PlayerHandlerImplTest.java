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
	public static final String VALID_SMALL_MAP1 = "validsmalltwo.map";
	public static final List<String> territoryList = new ArrayList<>();
	public static RiskMap riskMap;
	public static GamePlayPhase gamePlayPhase = new GamePlayPhase();

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

		List<Player> plist = new ArrayList<Player>();
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
		assertEquals(false, result);
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

}
