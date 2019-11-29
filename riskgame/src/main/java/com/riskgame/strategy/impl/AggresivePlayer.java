/**
 * 
 */
package com.riskgame.strategy.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.service.Impl.RiskPlayImpl;
import com.riskgame.strategy.StrategyInterface;

/**
 * Concrete implementation of Strategy interface of design pattern for Aggresive
 * strategy of player
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 * @see com.riskgame.strategy
 */
public class AggresivePlayer implements StrategyInterface {

	private MapManagementInterface mapManagementImpl;
	private RiskPlayInterface riskPlayImpl;

	public static StringBuilder sb;
	private static String NEWLINE = System.getProperty("line.separator");

	/**
	 * Constructor which will initialize object of services
	 */
	public AggresivePlayer() {
		mapManagementImpl = new MapManagementImpl();
		riskPlayImpl = new RiskPlayImpl();
	}
	
	public boolean attackAgain = false;
	public String fromC = null;
	public String toC = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#reinforce(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase reinforce(GamePlayPhase gamePlayPhase) {
		sb = new StringBuilder();
		Player currentPlayer = null;
		PlayerTerritory playerStrongestTerritory = null;
		if (gamePlayPhase != null) {
			for (Player player : gamePlayPhase.getPlayerList()) {
				if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
					currentPlayer = player;
					break;
				} else {
					continue;
				}
			}
			if (currentPlayer != null) {

				if (currentPlayer.getCardListOwnedByPlayer().size() >= 5) {

					gamePlayPhase.setRiskCardExchange(riskPlayImpl.prepareCard(currentPlayer));

					if (gamePlayPhase.getRiskCardExchange() != null) {
						sb.append("Army Stock before Card Trade: \"\n"
								+ "								+ currentPlayer.getPlayerReinforceArmy() + \"\\n3 Cards Traded.\\n");
						// gamePlayPhase.setStatus("Army Stock before Card Trade: "+
						// currentPlayer.getPlayerReinforceArmy() + "\n3 Cards Traded.\n");

						riskPlayImpl.exchangeCards(gamePlayPhase);

						sb.append("\n" + "New Army Stock after Card Trade: ")
								.append(currentPlayer.getPlayerReinforceArmy()).append(gamePlayPhase.getStatus())
								.append(NEWLINE);
						// gamePlayPhase.setStatus("\n" + "New Army Stock after Card Trade: " +
						// currentPlayer.getPlayerReinforceArmy() + gamePlayPhase.getStatus());

					} else {
						sb.append("No Card for exchange.\n");
					}
				}

				playerStrongestTerritory = riskPlayImpl.getStrongestTerritory(currentPlayer);
				
				if (playerStrongestTerritory != null) {
					playerStrongestTerritory.setArmyOnterritory(
							playerStrongestTerritory.getArmyOnterritory() + currentPlayer.getPlayerReinforceArmy());
					currentPlayer.setPlayerReinforceArmy(0);
					
					
					sb.append("Strongest Territory: ").append(playerStrongestTerritory.getTerritoryName())
					.append(" Reinforced New Army count on strongest territory:")
					.append(playerStrongestTerritory.getArmyOnterritory())
					.append(NEWLINE);
				}
			}		
		}
		gamePlayPhase.setStatus(sb.toString());
		System.out.println("AggresivePlayer Reinforce - "+gamePlayPhase.getStatus());
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#attack(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase attack(GamePlayPhase gamePlayPhase) {
		Player currentPlayer = null;
		sb = new StringBuilder();
		PlayerTerritory playerStrongestTerritory = null;
		List<String> playerCountry = new ArrayList<>();
		String fromCountry = null;
		String toCountry = null;
		// For finding Strongest Territory
		for (Player player : gamePlayPhase.getPlayerList()) {
			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
				currentPlayer = player;
				int max = 0;

				for (PlayerTerritory territory : player.getPlayerterritories()) {
					playerCountry.add(territory.getTerritoryName());
					if (territory.getArmyOnterritory() > max) {
						max = territory.getArmyOnterritory();
						playerStrongestTerritory = territory;
						fromCountry = playerStrongestTerritory.getTerritoryName();
					}
				}
				break;
			}
		}
		
		if(attackAgain) {
			Player fromP = riskPlayImpl.getPlayerByCountry(fromC, gamePlayPhase.getPlayerList());
			playerStrongestTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(fromC,fromP);
			
		}

		if (fromCountry!= null && playerStrongestTerritory!=null && playerStrongestTerritory.getArmyOnterritory() > 1) {

			List<String> neighbourCountriesList = mapManagementImpl
					.getNeighbourCountriesListByCountryName(gamePlayPhase.getRiskMap(), fromCountry);
			for (String neighbourCountry : neighbourCountriesList) {
				if (!playerCountry.contains(neighbourCountry)) {
					toCountry = neighbourCountry;
					break;
				}
			}
			
			if(attackAgain) {
				toCountry = toC;
			}
			
			if (toCountry != null) {

				int armyOnFromC = playerStrongestTerritory.getArmyOnterritory();
				int armyOnToC = riskPlayImpl.getCurrentAramyByCountryName(toCountry, gamePlayPhase.getPlayerList());

				int attackerDice = riskPlayImpl.getAttackerDiesCount(armyOnFromC);
				int defenderDice = riskPlayImpl.getDefenderDiceCount(armyOnToC);

				if (attackerDice > 0 && defenderDice > 0) {

					List<Integer> attackerList = riskPlayImpl.getCountFromDies(attackerDice);
					List<Integer> defenderList = riskPlayImpl.getCountFromDies(defenderDice);
					int min = Math.min(attackerList.size(), defenderList.size());

					for (int i = 0; i < min; i++) {

						if (defenderList.get(i) == attackerList.get(i)) {
							updateArmyAfterBattle(fromCountry, "attacker", gamePlayPhase);
						} else if (defenderList.get(i) > attackerList.get(i)) {
							updateArmyAfterBattle(fromCountry, "attacker", gamePlayPhase);
						} else if (defenderList.get(i) < attackerList.get(i)) {
							updateArmyAfterBattle(toCountry, "defender", gamePlayPhase);
						}
					}

					Player defenderPlayer = riskPlayImpl.getPlayerByCountry(toCountry, gamePlayPhase.getPlayerList());
					PlayerTerritory toTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(toCountry,
							defenderPlayer);

					if (toTerritory.getArmyOnterritory() == 0) {

						// allOutTerritoryConqured = true;
						// attackMove = true;
						// alloutFinish = true;

						moveCountryToWinPlayer(fromCountry, toCountry, gamePlayPhase);

						Player attackerPlayer = riskPlayImpl.getPlayerByCountry(fromCountry,
								gamePlayPhase.getPlayerList());
						PlayerTerritory fromTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(fromCountry,
								attackerPlayer);

						if (fromTerritory != null && fromTerritory.getArmyOnterritory() > 1) {
							int armyToMove = fromTerritory.getArmyOnterritory() - 1;
							moveArmy(fromCountry, toCountry, armyToMove, gamePlayPhase);
						}
						
						if(gamePlayPhase.getRiskCardList().size()>0) {
							
							RiskCard riskCard = gamePlayPhase.getRiskCardList().get(0);
							currentPlayer.getCardListOwnedByPlayer().add(riskCard);
							sb.append(riskCard).append(" Assigned to ").append(currentPlayer.getPlayerName()).append(NEWLINE);
							
							gamePlayPhase.getRiskCardList().remove(0);
							
						}
					}else {
						
						
						armyOnFromC = riskPlayImpl.getCurrentAramyByCountryName(fromCountry, gamePlayPhase.getPlayerList());
						armyOnToC = riskPlayImpl.getCurrentAramyByCountryName(toCountry, gamePlayPhase.getPlayerList());

						attackerDice = riskPlayImpl.getAttackerDiesCount(armyOnFromC);
						defenderDice = riskPlayImpl.getDefenderDiceCount(armyOnToC);
						
						if (attackerDice > 0 && defenderDice > 0) {
							
							attackAgain = true;
							fromC = fromCountry;
							toC = toCountry;
							attack(gamePlayPhase);
							
						}else {
							sb.append("Allout Attack not possible - not an valid army on country").append(NEWLINE);
						}
						
					}

				} else {
					// alloutFinish = true;
					sb.append("Allout Attack not possible - not an valid army on country").append(NEWLINE);
				}

			} else {
				sb.append(
						"Attack is not possible because player's strongest countries all neighbour countries are part of player's country!");
			}

		} else {

			sb.append("Can't attack because strongest selected territory has not enough Army: ");
//					.append(playerStrongestTerritory.getTerritoryName()).append(" has only ")
//					.append(playerStrongestTerritory.getArmyOnterritory()).append(" army ").append(NEWLINE);
		}

		gamePlayPhase.setGamePhase("ATTACK");
		riskPlayImpl.checkForWinner(gamePlayPhase);
		gamePlayPhase.setStatus(sb.toString());
		System.out.println("AggresivePlayer Attack - "+gamePlayPhase.getStatus());
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#fortify(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase fortify(GamePlayPhase gamePlayPhase) {

		Player currentPlayer = null;
		Boolean isneighbour = false;

		String aggressiveMessageForFortify = "";
		String messgeOld = "";

		for (Player player : gamePlayPhase.getPlayerList()) {
			if (player.getPlayerId() == gamePlayPhase.getCurrentPlayerId()) {
				currentPlayer = player;
				break;
			} else {
				continue;
			}
		}

		if (currentPlayer != null) {

			for (PlayerTerritory territoryA : currentPlayer.getPlayerterritories()) {

				if(isneighbour == true) {
					break;
				}
				
				if (territoryA.getArmyOnterritory() == 1) {
					continue;
				}
				for (PlayerTerritory territoryB : currentPlayer.getPlayerterritories()) {

					//isneighbour = false;

					if (territoryA.getTerritoryName().equalsIgnoreCase(territoryB.getTerritoryName())
							|| territoryB.getArmyOnterritory() == 1) {
						continue;
					} else {

						List<String> neighbourCountriesList = mapManagementImpl.getNeighbourCountriesListByCountryName(
								gamePlayPhase.getRiskMap(), territoryA.getTerritoryName());
						if (neighbourCountriesList.contains(territoryB.getTerritoryName())) {
							isneighbour = true;
						}
					}

					if (isneighbour == true) {

						messgeOld = (territoryA.getArmyOnterritory() - 1) + " army moved from "
								+ territoryA.getTerritoryName() + " to " + territoryB.getTerritoryName() + "\n";
						aggressiveMessageForFortify = messgeOld + aggressiveMessageForFortify;
						
						territoryB.setArmyOnterritory(territoryB.getArmyOnterritory()+territoryA.getArmyOnterritory()-1);
						territoryA.setArmyOnterritory(1);
						
						break;
					}
					
					
					
				}
			}
		}

		gamePlayPhase.setStatus(aggressiveMessageForFortify);
		System.out.println("AggresivePlayer fortify - "+gamePlayPhase.getStatus());
		return gamePlayPhase;
	}

	/**
	 * This method will update number of army from attcker or defender territory
	 * after battle completed everytime
	 * 
	 * @param country
	 * @param name    attcker or defender
	 */
	private GamePlayPhase updateArmyAfterBattle(String country, String name, GamePlayPhase gamePlayPhase) {
		// sb = new StringBuilder();
		for (Player player : gamePlayPhase.getPlayerList()) {

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

				if (country.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					playerTerritory.setArmyOnterritory(playerTerritory.getArmyOnterritory() - 1);
					player.setArmyOwns(player.getArmyOwns() - 1);

					//sb.append(name).append(" Country loses 1 army ").append(country).append(" has left with ").append(playerTerritory.getArmyOnterritory()).append(NEWLINE);

					break;

				}
			}
		}
		gamePlayPhase.setStatus(gamePlayPhase.getStatus() + sb.toString());
		return gamePlayPhase;
	}

	/**
	 * This method will move conquered country to attacker
	 * 
	 * @param fromCountryAttack
	 * @param toCountryAttack
	 */
	private GamePlayPhase moveCountryToWinPlayer(String fromCountryAttack, String toCountryAttack,
			GamePlayPhase gamePlayPhase) {
		// sb = new StringBuilder();
		Player player = riskPlayImpl.getPlayerByCountry(toCountryAttack, gamePlayPhase.getPlayerList());
		PlayerTerritory playerTerritory = riskPlayImpl.getPlayerTerritoryByCountryName(toCountryAttack, player);

		// for Adding country
		for (Player playerFromLst : gamePlayPhase.getPlayerList()) {

			ListIterator<PlayerTerritory> playerTerritories = playerFromLst.getPlayerterritories().listIterator();

			while (playerTerritories.hasNext()) {
				if (playerTerritories.next().getTerritoryName().equalsIgnoreCase(fromCountryAttack)) {
					playerTerritories.add(playerTerritory);
				}
			}
		}
		

		// Fore removing country
		for (Player playertoLst : gamePlayPhase.getPlayerList()) {

			ListIterator<PlayerTerritory> playerTerritories = playertoLst.getPlayerterritories().listIterator();

			while (playerTerritories.hasNext()) {
				if (playerTerritories.next().getTerritoryName().equalsIgnoreCase(playerTerritory.getTerritoryName()) && player.getPlayerId()==playertoLst.getPlayerId()) {
					playerTerritories.remove();
				}
			}
		}
		
		sb.append(toCountryAttack).append(" country has been conquered ").append(NEWLINE);
		return gamePlayPhase;
	}

	/**
	 * This method will move army from attcker country to conqured country
	 * 
	 * @param fromCountryAttack
	 * @param toCountryAttack
	 * @param armyToMove
	 */
	private GamePlayPhase moveArmy(String fromCountryAttack, String toCountryAttack, int armyToMove,
			GamePlayPhase gamePlayPhase) {
		// sb = new StringBuilder();
		for (Player player : gamePlayPhase.getPlayerList()) {

			for (PlayerTerritory playerTerritory : player.getPlayerterritories()) {

				if (toCountryAttack.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					playerTerritory.setArmyOnterritory(playerTerritory.getArmyOnterritory() + armyToMove);

					sb.append(armyToMove).append(" Army Moved Successfully on ").append(toCountryAttack)
							.append(" country").append(NEWLINE);

				} else if (fromCountryAttack.equalsIgnoreCase(playerTerritory.getTerritoryName())) {

					playerTerritory.setArmyOnterritory(playerTerritory.getArmyOnterritory() - armyToMove);

				}
			}
		}

		gamePlayPhase.setStatus(gamePlayPhase.getStatus() + sb.toString());
		return gamePlayPhase;
	}

}
