package com.riskgame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

import com.riskgame.controller.MapControllerTest;
import com.riskgame.controller.RiskPlayScreenControllerTest;
import com.riskgame.controller.StartupPhaseControllerTest;
import com.riskgame.service.impl.MapManagementImplTest;
import com.riskgame.service.impl.PlayerHandlerImplTest;
import com.riskgame.service.impl.RiskPlayImplTest;

@SpringBootTest
@RunWith(Suite.class)
@SuiteClasses({ MapControllerTest.class, RiskPlayScreenControllerTest.class, StartupPhaseControllerTest.class,
		MapManagementImplTest.class, RiskPlayImplTest.class, PlayerHandlerImplTest.class })
public class RiskgameApplicationTests {

	
	@Test
	public void contextLoads() {
	}

}
