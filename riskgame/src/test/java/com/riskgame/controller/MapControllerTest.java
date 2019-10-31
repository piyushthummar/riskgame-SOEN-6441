/**
 * 
 */
package com.riskgame.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.runner.RunWith;

/**
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapControllerTest {

	String str1 = new String("abc");
	String str2 = new String("abc");
	
	@Test
	public void TestAssertion()
	{
		assertEquals(str1, str2);
	}
	
}
