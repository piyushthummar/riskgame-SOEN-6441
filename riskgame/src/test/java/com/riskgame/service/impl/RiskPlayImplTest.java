/**
 * 
 */
package com.riskgame.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.service.RiskPlayInterface;

/**
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RiskPlayImplTest {

	public static final int COUNTRIES_COUNT = 20;
	@Autowired
	RiskPlayInterface riskplay;
	/**
	 * Test for validTestforCalculationofNumberOfReinforcementArmies.
	 * @result based on countries count method will return number of reinforcement armies
	 */
	@Test
	public void validTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(COUNTRIES_COUNT);
		assertEquals(6, count);

	}

	/**
	 * Test for InvalidTestforCalculationofNumberOfReinforcementArmies.
	 * @result based on countries count method will return number of reinforcement armies
	 */
	@Test
	public void InvalidTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(COUNTRIES_COUNT);
		assertNotEquals(10, count);

	}
	
}
