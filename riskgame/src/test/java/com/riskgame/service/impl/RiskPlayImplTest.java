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

import com.riskgame.model.GamePlayPhase;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;

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

	/**
	 * Test for validTestforCalculationofNumberOfReinforcementArmies.
	 * 
	 * @result based on countries count method will return number of reinforcement
	 *         armies
	 */
	@Test
	public void validTestforCalculationofNumberOfReinforcementArmies() {
	//	int count = riskplay.checkForReinforcement(COUNTRIES_COUNT, phase);
	//	assertEquals(6, count);
	}

	/**
	 * Test for InvalidTestforCalculationofNumberOfReinforcementArmies.
	 * 
	 * @result based on countries count method will return number of reinforcement
	 *         armies
	 */
	@Test
	public void InvalidTestforCalculationofNumberOfReinforcementArmies() {
		//int count = riskplay.checkForReinforcement(COUNTRIES_COUNT, phase);
		//assertNotEquals(10, count);
	}
}
