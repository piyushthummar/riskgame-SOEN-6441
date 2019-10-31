/**
 * 
 */
package com.riskgame.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.PlayerHandlerInterface;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.PlayerHandlerImpl;

/**
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerHandlerImplTest {
	
	public static final int NUMBER_OF_PLAYER = 6;
	public static final String VALID_MAP_NAME = "world.map";
	
	@Autowired
	MapManagementImpl map;
	
	@Autowired
	PlayerHandlerImpl playerHandler;

	/**
	 * Test for validTestForFindTotalArmy.
	 * @result
	 */
	@Test
	public void validTestForFindTotalArmy() {
		int totalArmy = playerHandler.findTotalArmy(NUMBER_OF_PLAYER);
		assertEquals(20, totalArmy);

	}

	/**
	 * Test for inValidTestForFindTotalArmy.
	 * @result
	 */
	@Test
	public void inValidTestForFindTotalArmy() {
		int totalArmy = playerHandler.findTotalArmy(NUMBER_OF_PLAYER);
		assertNotEquals(10, totalArmy);

	}

	/**
	 * Test for notEmptyTerritoriesTest.
	 * @result
	 */
	@Test
	public void notEmptyTerritoriesTest() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		List<PlayerTerritory> playerTerritories = playerHandler.getTerritories(riskMap);
		assertEquals(42, playerTerritories.size());
	}
	
	
	
}
