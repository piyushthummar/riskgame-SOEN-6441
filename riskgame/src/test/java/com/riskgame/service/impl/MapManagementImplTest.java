package com.riskgame.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.model.RiskMap;
import com.riskgame.service.Impl.MapManagementImpl;

/**
 * This test method will test business logic of MapManagementImpl. This class
 * will check map validity and other functionalities related to map
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapManagementImplTest {

	public static final String VALID_MAP_NAME = "world.map";
	public static final String INVALID_MAP_NAME = "invalidone.map";
	public static final String COUNTRY_NAME = "India";
	public static final boolean TRUE = true;

	@Autowired
	MapManagementImpl map;

	@Test
	public void contextLoads() throws Exception {
	}

	/**
	 * Test for valid map file.
	 * 
	 * @result This test cases will read file from resources folder with map file
	 *         name and if it is valid then return would be boolean.
	 */
	@Test
	public void testForValidateMapFileName() {

		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		boolean result = map.validateMap(riskMap);

		assertEquals(true, result);

	}

	/**
	 * Test for invalid map file.
	 * 
	 * @result This test cases will read file from resources folder with map file
	 *         name and if it is valid then return would be boolean.
	 */
	@Test
	public void testForInvalidateMapFileName() {

		RiskMap riskMap = map.readMap(INVALID_MAP_NAME);
		boolean result = map.validateMap(riskMap);

		assertEquals(false, result);

	}

	/**
	 * Test for testForgetNeighbourCountriesListByCountryName.
	 * 
	 * @result
	 */
	@Test
	public void testForgetNeighbourCountriesListByCountryName() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		List<String> neighbourCountriesList = map.getNeighbourCountriesListByCountryName(riskMap, COUNTRY_NAME);
		assertNotEquals(0, neighbourCountriesList.size());

	}

	/**
	 * Test for testForConvertRiskMapToDtos.
	 * 
	 * @result
	 */
	@Test
	public void testForConvertRiskMapToDtos() {
		RiskMap riskMap = map.readMap(VALID_MAP_NAME);
		Map<String, Object> resultmap = map.convertRiskMapToDtos(riskMap);
		assertNotEquals(0, resultmap.size());
	}

	/**
	 * Test for notEmptyAvailableMap.
	 * 
	 * @result
	 */
	@Test
	public void notEmptyAvailableMap() {
		List<String> mapList = map.getAvailableMap();
		assertNotEquals(0, mapList.size());
	}
	
	@Test
	public boolean saveMapToFile(RiskMap map)
	{
		
		
		
		return true;
	}
}
