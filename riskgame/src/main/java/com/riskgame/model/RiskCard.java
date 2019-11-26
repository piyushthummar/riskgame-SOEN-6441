/**
 * 
 */
package com.riskgame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Model for Risk Cards
 * 
 * Two annotations (Getter, Setter) you can see on the top of the class are
 * lombok dependencies to automatically generate getter, setter in the code.
 * 
 * @author <a href="mailto:r_istry@encs.concordia.ca">Raj Mistry</a>
 */
@Getter
@Setter
public class RiskCard {

	/**
	 * This data member indicate card number
	 */
	private int cardNumber;

	/**
	 * It indicate types of army i.e. Infantry, cavilary etc.
	 */
	private String armyType;

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RiskCard [cardNumber=" + cardNumber + ", armyType=" + armyType + "]";
	}
}
