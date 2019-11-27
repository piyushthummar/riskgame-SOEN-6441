package com.riskgame.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.service.Impl.ConquestMapImpl;

/**
 * This test method will test business logic of MConquestMapImpl. This class
 * will check map validity and other functionalities related to Conquest map file
 * 
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * @see com.riskgame.service.Impl.ConquestMapImpl
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConquestMapImplTest {

	@Autowired
	ConquestMapImpl conquestImpl;

	/**
	 * The context load method to initialize springboot context
	 * 
	 * @throws Exception
	 */
	@Test
	public void contextLoads() throws Exception {
	}
}
