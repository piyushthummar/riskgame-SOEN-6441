package com.riskgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskMap;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.PlayerHandlerInterface;
import com.riskgame.service.RiskPlayInterface;

@SpringBootTest
public class RiskgameApplicationTests {

	public static final String VALID_MAP_NAME = "world.map";
	public static final String INVALID_MAP_NAME = "invalidone.map";

	public static final int COUNTRIES_COUNT = 20;

	public static final int NUMBER_OF_PLAYER = 6;
	
	public static final String COUNTRY_NAME = "India";

	@Autowired
	MapManagementInterface map;

	@Autowired
	RiskPlayInterface riskplay;

	@Autowired
	PlayerHandlerInterface playerHandler;

	@Test
	public void contextLoads() {
	}

	/**
	 * Test for valid map file.
	 * @result This test cases will read file from resources folder with map file name and if it is valid then return would be boolean.
	 */
	@Test
	void testForValidateMapFileName() {

		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		boolean result = map.validateMap(riskMap);

		assertEquals(true, result);

	}

	/**
	 * Test for invalid map file.
	 * @result This test cases will read file from resources folder with map file name and if it is valid then return would be boolean.
	 */
	@Test
	void testForInvalidateMapFileName() {

		RiskMap riskMap = map.readMap(INVALID_MAP_NAME);
		boolean result = map.validateMap(riskMap);

		assertEquals(false, result);

	}

	/**
	 * Test for validTestforCalculationofNumberOfReinforcementArmies.
	 * @result based on countries count method will return number of reinforcement armies
	 */
	@Test
	void validTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(COUNTRIES_COUNT);
		assertEquals(6, count);

	}

	/**
	 * Test for InvalidTestforCalculationofNumberOfReinforcementArmies.
	 * @result based on countries count method will return number of reinforcement armies
	 */
	@Test
	void InvalidTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(COUNTRIES_COUNT);
		assertNotEquals(10, count);

	}

	/**
	 * Test for validTestForFindTotalArmy.
	 * @result
	 */
	@Test
	void validTestForFindTotalArmy() {
		int totalArmy = playerHandler.findTotalArmy(NUMBER_OF_PLAYER);
		assertEquals(20, totalArmy);

	}

	/**
	 * Test for inValidTestForFindTotalArmy.
	 * @result
	 */
	@Test
	void inValidTestForFindTotalArmy() {
		int totalArmy = playerHandler.findTotalArmy(NUMBER_OF_PLAYER);
		assertNotEquals(10, totalArmy);

	}

	/**
	 * Test for notEmptyTerritoriesTest.
	 * @result
	 */
	@Test
	void notEmptyTerritoriesTest() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		List<PlayerTerritory> playerTerritories = playerHandler.getTerritories(riskMap);
		assertEquals(42, playerTerritories.size());
	}

	/**
	 * Test for notEmptyAvailableMap.
	 * @result
	 */
	@Test
	void notEmptyAvailableMap() {
		List<String> mapList = map.getAvailableMap();
		assertNotEquals(0, mapList.size());

	}
	
	/**
	 * Test for testForConvertRiskMapToDtos.
	 * @result
	 */
	@Test
	void testForConvertRiskMapToDtos() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		Map<String, Object> resultmap = map.convertRiskMapToDtos(riskMap);
		assertNotEquals(0, resultmap.size());
	}
	
	/**
	 * Test for testForgetNeighbourCountriesListByCountryName.
	 * @result
	 */
	@Test
	void testForgetNeighbourCountriesListByCountryName() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		List<String> neighbourCountriesList = map.getNeighbourCountriesListByCountryName(riskMap,COUNTRY_NAME);
		assertNotEquals(0, neighbourCountriesList.size());
		
	}

}
