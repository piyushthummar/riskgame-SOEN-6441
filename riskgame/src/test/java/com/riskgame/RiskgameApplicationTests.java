package com.riskgame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

import com.riskgame.controller.MapControllerTest;
import com.riskgame.controller.RiskPlayScreenControllerTest;
import com.riskgame.controller.StartupPhaseControllerTest;
import com.riskgame.service.Impl.ConquestMapImpl;
import com.riskgame.service.impl.ConquestMapImplTest;
import com.riskgame.service.impl.MapManagementImplTest;
import com.riskgame.service.impl.PlayerHandlerImplTest;
import com.riskgame.service.impl.RiskPlayImplTest;

/**
 * This is the main suite class for textcases. All testcases files for different
 * services will be mentioned here. From here we can run the test case files.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 * @see com.riskgame.service
 * @see com.riskgame.service.Impl
 */
@SpringBootTest
@RunWith(Suite.class)
@SuiteClasses({ MapControllerTest.class, RiskPlayScreenControllerTest.class, StartupPhaseControllerTest.class,
		MapManagementImplTest.class, RiskPlayImplTest.class, PlayerHandlerImplTest.class, ConquestMapImplTest.class })
public class RiskgameApplicationTests {

	/**
	 * The context load method to initialize springboot context
	 */
	@Test
	public void contextLoads() {
	}

}
