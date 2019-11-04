/**
 * 
 */
package com.riskgame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Model for card Exchange
 * 
 * Two annotations (Getter, Setter) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter in the code.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskCard
 * @version 1.0.0
 */
@Getter
@Setter
public class RiskCardExchange {
	private RiskCard exchange1;
	private RiskCard exchange2;
	private RiskCard exchange3;
	@Override
	public String toString() {
		return "RiskCardExchange [exchange1=" + exchange1 + ", exchange2=" + exchange2 + ", exchange3=" + exchange3
				+ "]";
	}
	
}
