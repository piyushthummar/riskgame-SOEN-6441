/**
 * 
 */
package com.riskgame.observerpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is Subject class to implement the observer pattern.
 * @author PIYUSH
 *
 */
public class Subject {
	private List<Observer> observers = new ArrayList<>();
	private String message;
	private String state;
	private String phase;
	private String reinforcementMessage;
	private String attackMessage;
	private String fortificationMessage;
	
	public void notifyAllObservers()
	{
		for(Observer observer : observers)
		{
			observer.update();
		}
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getReinforcementMessage() {
		return reinforcementMessage;
	}
	public void setReinforcementMessage(String reinforcementMessage) {
		this.reinforcementMessage = reinforcementMessage;
		
		//notifyForReinforcement
		for(Observer observer : observers)
		{
			observer.reinforcemrentUpdate();
		}
	}
	public String getAttackMessage() {
		return attackMessage;
	}
	public void setAttackMessage(String attackMessage) {
		this.attackMessage = attackMessage;
		
		//notifyForAttack
		for(Observer observer : observers)
		{
			observer.attackUpdate();
		}
	}
	public String getFortificationMessage() {
		return fortificationMessage;
	}
	public void setFortificationMessage(String fortificationMessage) {
		this.fortificationMessage = fortificationMessage;
		
		//notifyForFortification
		for(Observer observer : observers)
		{
			observer.fortificationUpdate();
		}
	}
	
	public void attach(Observer observer)
	{
		observers.add(observer);
	}
	
	public void detach(Observer observer)
	{
		boolean detachSuccess = observers.remove(observer);
		if(detachSuccess == true)
		{
			System.out.println(observer + " is detached successfully.");
		}
		else
		{
			System.out.println(observer + " is not found in observer list to be detached.");
		}
	}
	
	
}
