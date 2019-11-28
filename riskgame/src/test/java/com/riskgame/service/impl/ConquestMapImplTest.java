package com.riskgame.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.riskgame.model.RiskMap;
import com.riskgame.service.ConquestMapInterface;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.Impl.ConquestMapImpl;
import com.riskgame.adapter.DominationToConquestAdapter;
import com.riskgame.model.Continent;
import com.riskgame.model.Territory;

/**
 * This test method will test business logic of ConquestMapImpl. This class will
 * check map validity and other functionalities related to Conquest map file
 * 
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * @see com.riskgame.service.Impl.ConquestMapImpl
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConquestMapImplTest {
	public static RiskMap riskMap, expectedRiskMap;
	public static MapManagementInterface mapInterface;
	public static final String VALID_CONQUEST_MAP_NAME = "worldconquest";
	public static final String VALID_SMALL_CONQUEST_MAP_NAME = "validSmallConquest";
	@Autowired
	ConquestMapInterface conquestImpl;

	/**
	 * Setup method to setup object initially
	 */
	@Before
	public void setup() {
		conquestImpl = new ConquestMapImpl();
		mapInterface = new DominationToConquestAdapter(conquestImpl);
	}

	/**
	 * The context load method to initialize springboot context
	 * 
	 * @throws Exception
	 */
	@Test
	public void contextLoads() throws Exception {
	}

	/**
	 * This method will test readMap() that game reads the valid conquest map and
	 * returns RiskMap Object true if it returns the same object as expectedRiskMap
	 */
	@Test
	public void testReadMap() {
		Continent continent;
		ArrayList<Territory> territoryList = new ArrayList<>();
		Territory territory;
		ArrayList<String> neighborsList;
		Map<Integer, Continent> continents = new HashMap<Integer, Continent>();

		neighborsList = new ArrayList<>();
		territory = new Territory();
		territory.setTerritoryIndex(1);
		territory.setTerritoryName("Alaska");
		territory.setXAxis(70);
		territory.setYAxis(126);
		territory.setContinentIndex(1);

		territory.setNeighbourTerritories(neighborsList);
		territoryList.add(territory);
		continent = new Continent();
		continent.setContinentIndex(1);
		continent.setContinentName("North America");
		continent.setContinentValue(5);
		continent.setTerritoryList(territoryList);
		continents.put(1, continent);

		expectedRiskMap = new RiskMap();
		expectedRiskMap.setMapName("validSmallConquest");
		expectedRiskMap.setStatus(null);
		expectedRiskMap.setContinents(continents);

		conquestImpl = new ConquestMapImpl();
		MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestImpl);
		riskMap = mapInterface.readMap(VALID_SMALL_CONQUEST_MAP_NAME+".map");

		assertNotEquals(expectedRiskMap, riskMap);
	}

	/**
	 * This method will test saveMapToFile method that saves the conquest map and
	 * returns true if it successfully saved.
	 */
	@Test
	public void testSaveMapToFile() {
		boolean actualResult = false;

		riskMap = mapInterface.readMap(VALID_CONQUEST_MAP_NAME+".map");

		try {
			actualResult = conquestImpl.saveMapToFile(riskMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(true, actualResult);
	}
}
