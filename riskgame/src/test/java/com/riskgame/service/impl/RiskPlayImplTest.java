/**
 * 
 */
package com.riskgame.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.riskgame.constant.ComputerStrategy;
import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.PlayerTerritory;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskMap;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.RiskPlayInterface;
import com.riskgame.service.Impl.PlayerHandlerImpl;
import com.riskgame.service.Impl.RiskPlayImpl;
import com.riskgame.strategy.StrategyInterface;

/**
 * This Test class will check all RiskplayImpl methods, which is business logic
 * related to game play.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.service.Impl.RiskPlayImpl
 * @see com.riskgame.service.RiskPlayInterface
 * @version 1.0.0
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
	
	@Autowired
	PlayerHandlerImpl playerHandlerImpl;

	Player player;
	PlayerTerritory playerTerritory;
	int countriesOwned;
	List<PlayerTerritory> ptList;
	String FILE_NAME = "risk.map";
	String CONQUEST_FILE_NAME = "worldconquest.map";
	List<Player> playerList = new ArrayList<>();

	List<String> continentsControlledByUser;

	RiskMap riskMap;

	List<RiskCard> totalCards;
	int noOfCountries;
	RiskCard card;
	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "CAVALRY";
	public static final String ARTILLERY = "ARTILLERY";

	/**
	 * Thus is setup method to setup objects before running the test file.
	 */
	@Before
	public void beforeTest() {
		riskMap = mapManagement.readMap(FILE_NAME);

		player = new Player();
		playerTerritory = new PlayerTerritory();
		ptList = new ArrayList<PlayerTerritory>();
		playerList.add(player);
		playerTerritory.setArmyOnterritory(1);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Venezuela");
		ptList.add(playerTerritory);

		playerTerritory.setArmyOnterritory(2);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Peru");
		ptList.add(playerTerritory);

		playerTerritory.setArmyOnterritory(3);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Brazil");
		ptList.add(playerTerritory);

		playerTerritory.setArmyOnterritory(4);
		playerTerritory.setContinentName("South-America");
		playerTerritory.setTerritoryName("Argentina");
		ptList.add(playerTerritory);

		player.setPlayerId(1);
		player.setPlayerName("Tester");
		player.setPlayerterritories(ptList);

		continentsControlledByUser = new ArrayList<>();

		noOfCountries = 3;
		totalCards = new ArrayList<>();
		card = new RiskCard();
		card.setArmyType(INFANTRY);
		card.setCardNumber(1);
		totalCards.add(card);
		card = new RiskCard();
		card.setArmyType(ARTILLERY);
		card.setCardNumber(2);
		totalCards.add(card);
		card = new RiskCard();
		card.setArmyType(CAVALRY);
		card.setCardNumber(3);
		totalCards.add(card);

	}

	/**
	 * Test for validTestforCalculationofNumberOfReinforcementArmies.
	 * 
	 * @result based on countries count method will return number of reinforcement
	 *         armies
	 */
	@Test
	public void validTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(countriesOwned, player, FILE_NAME);
		assertEquals(3, count);
	}

	/**
	 * Test for InvalidTestforCalculationofNumberOfReinforcementArmies.
	 * 
	 * @result based on countries count method will return number of reinforcement
	 *         armies
	 */
	@Test
	public void InvalidTestforCalculationofNumberOfReinforcementArmies() {
		int count = riskplay.checkForReinforcement(countriesOwned, player, FILE_NAME);
		assertNotEquals(4, count);
	}

	/**
	 * Test for validTestOfCheckForContinentControlValue.
	 * 
	 * @result based on countries count method will return a number of territories
	 *         of continent
	 */
	@Test
	public void validTestOfCheckForContinentControlValue() {
		int controlValue = riskplay.checkForContinentControlValue(player, FILE_NAME);
		assertEquals(0, controlValue);
	}

	/**
	 * Test for validTestOfCheckForContinentControlValue.
	 * 
	 * @result based on countries count method will return a number of territories
	 *         of continent
	 */
	@Test
	public void validTestOfCheckForContinentControlValueOfConquest() {
		int controlValue = riskplay.checkForContinentControlValue(player, CONQUEST_FILE_NAME);
		assertEquals(0, controlValue);
	}

	/**
	 * Test for validTestOfCheckForContinentControlValue.
	 * 
	 * @result based on countries count method will return a number of territories
	 *         of continent
	 */
	@Test
	public void invalidTestOfCheckForContinentControlValue() {
		int controlValue = riskplay.checkForContinentControlValue(player, FILE_NAME);
		assertNotEquals(4, controlValue);
	}

	/**
	 * Test for validTestForValidateFromCountry.
	 * 
	 * @result based on attack phase that from country is from player is valid or
	 *         not
	 * 
	 */
	@Test
	public void validTestForValidateFromCountry() {
		assertEquals(true, riskplay.validateFromCountry("Argentina", player));
	}

	/**
	 * Test for invalidTestForValidateFromCountry.
	 * 
	 * @result based on attack phase that from country is from player is valid or
	 *         not
	 * 
	 */
	@Test
	public void invalidTestForValidateFromCountry() {
		assertEquals(false, riskplay.validateFromCountry("India", player));
	}

	/**
	 * Test for validTestForValidateFromCountry.
	 * 
	 * @result based on attack phase that toCountry is from player is valid or not
	 * 
	 */
	@Test
	public void validTestForValidateToCountry() {
		assertEquals(false, riskplay.validateToCountry("Argentina", "India", riskMap, player));
	}

	/**
	 * Test for invalidTestForValidateToCountry.
	 * 
	 * @result based on attack phase that toCountry is from player is valid or not
	 * 
	 */
	@Test
	public void invalidTestForValidateToCountry() {
		assertEquals(true, riskplay.validateToCountry("Argentina", "Brazil", riskMap, player));
	}

	/**
	 * Test for validTestForMakeCards.
	 * 
	 * @result based on number of countries makeCards method will return List of
	 *         cards
	 * 
	 */
	@Test
	public void validTestForMakeCards() {

		List<RiskCard> resultTotalCards = new ArrayList<>();
		resultTotalCards = riskplay.makeCards(noOfCountries);
		assertNotEquals(totalCards, resultTotalCards);
	}

	/**
	 * Test for invalidTestForMakeCards.
	 * 
	 * @result based on number of countries makeCards method will return List of
	 *         cards
	 * 
	 */
	@Test
	public void invalidTestForMakeCards() {
		noOfCountries = 5;
		List<RiskCard> resultTotalCards = new ArrayList<>();
		resultTotalCards = riskplay.makeCards(noOfCountries);
		assertNotEquals(totalCards, resultTotalCards);
	}

	/**
	 * Test for validUpdateArmyAfterCardExchange.
	 * 
	 * @result based on count of it will update Army and returns updateArmy
	 * 
	 */
//	@Test
//	public void validUpdateArmyAfterCardExchange() {
//		player.setExchangeCount(5);
//		int expectedUpdateArmy=riskplay.updateArmyAfterCardExchange(player);
//		assertEquals(25, expectedUpdateArmy);
//		
//	}

	/**
	 * Test for invalidUpdateArmyAfterCardExchange.
	 * 
	 * @result based on count of it will update Army and returns updateArmy
	 * 
	 */
	@Test
	public void invalidUpdateArmyAfterCardExchange() {
		player.setExchangeCount(6);
		Player playerReturn = riskplay.updateArmyAfterCardExchange(player);
		assertNotEquals(25, playerReturn.getPlayerReinforceArmy());

	}

	/**
	 * Test for validGetAttackerDiesCount.
	 * 
	 * @result based on current Army return dice count for attacker.
	 * 
	 */
	@Test
	public void validGetAttackerDiesCount() {
		int CurrentArmy = 5;
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int numDices = riskplayimpl.getAttackerDiesCount(CurrentArmy);
		assertEquals(3, numDices);

	}

	/**
	 * Test for invalidGetAttackerDiesCount.
	 * 
	 * @result based on current Army return dice count for attacker.
	 * 
	 */
	@Test
	public void invalidGetAttackerDiesCount() {
		int CurrentArmy = 5;
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int numDices = riskplayimpl.getAttackerDiesCount(CurrentArmy);
		assertNotEquals(2, numDices);

	}

	/**
	 * Test for validGetDefenderDiceCount.
	 * 
	 * @result returns number of dice defender can use based upon number of army he
	 *         is having.
	 * 
	 */
	@Test
	public void validGetDefenderDiceCount() {
		int CurrentArmy = 3;
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int numDices = riskplayimpl.getDefenderDiceCount(CurrentArmy);
		assertEquals(2, numDices);

	}

	/**
	 * Test for invalidGetDefenderDiceCount.
	 * 
	 * @result returns number of dice defender can use based upon number of army he
	 *         is having.
	 * 
	 */
	@Test
	public void invalidGetDefenderDiceCount() {
		int CurrentArmy = 3;
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int numDices = riskplayimpl.getDefenderDiceCount(CurrentArmy);
		assertNotEquals(1, numDices);

	}

	/**
	 * Test for validGetCurrentArmyByCountryName.
	 * 
	 * @result returns number of armies for given territory
	 * 
	 */
	@Test
	public void validGetCurrentArmyByCountryName() {
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int army = riskplayimpl.getCurrentAramyByCountryName("Brazil", playerList);
		assertEquals(0, army);

	}

	/**
	 * Test for invalidGetCurrentArmyByCountryName.
	 * 
	 * @result returns number of armies for given territory
	 * 
	 */
	@Test
	public void invalidGetCurrentArmyByCountryName() {
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		int army = riskplayimpl.getCurrentAramyByCountryName("Brazil", playerList);
		assertNotEquals(4, army);
	}

	/**
	 * Test for validGetPlayerTerritoryByCountryName.
	 * 
	 * @result returns player territory for given territory name
	 * 
	 */
	@Test
	public void validGetPlayerTerritoryByCountryName() {
		PlayerTerritory expectedPlayerTerritory = new PlayerTerritory();
		expectedPlayerTerritory.setArmyOnterritory(4);
		expectedPlayerTerritory.setContinentName("South-America");
		expectedPlayerTerritory.setTerritoryName("Argentina");
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		assertNotEquals(expectedPlayerTerritory, riskplayimpl.getPlayerTerritoryByCountryName("Argentina1", player));

	}

	/**
	 * Test for invalidGetPlayerTerritoryByCountryName.
	 * 
	 * @result returns player territory for given territory name
	 * 
	 */
	@Test
	public void invalidGetPlayerTerritoryByCountryName() {
		PlayerTerritory expectedPlayerTerritory = new PlayerTerritory();
		expectedPlayerTerritory.setArmyOnterritory(4);
		expectedPlayerTerritory.setContinentName("South-America");
		expectedPlayerTerritory.setTerritoryName("Argentina");
		RiskPlayImpl riskplayimpl = new RiskPlayImpl();
		assertNotEquals(expectedPlayerTerritory, riskplayimpl.getPlayerTerritoryByCountryName("Brazil", player));
	}

	/**
	 * Test for generatePlayerListByStrategy.
	 *
	 * @result returns list of players that are generated by list of strategies.
	 *
	 */
	@Test
	public void validGeneratePlayerListByStrategy() {

		ArrayList<String> strategies = new ArrayList<String>();
		strategies.add("AGGRESIVE");
		strategies.add("BENEVOLENT");

		List<Player> expectedPlayerList = new ArrayList<Player>();
		Player playerOne, playerTwo;
		playerOne = new Player();
		playerTwo = new Player();

		playerOne.setPlayerId(1);
		playerOne.setPlayerName("AGGRESIVE");
		playerOne.setArmyOwns(0);
		playerOne.setPlayerType(ComputerStrategy.COMPUTER.toString());
		playerOne.setStrategy((StrategyInterface) playerHandlerImpl.getStrategyByName("AGGRESIVE"));
		playerOne.setStrategyName("AGGRESIVE");
		expectedPlayerList.add(playerOne);

		playerTwo.setPlayerId(1);
		playerTwo.setPlayerName("BENEVOLENT");
		playerTwo.setArmyOwns(0);
		playerTwo.setPlayerType(ComputerStrategy.COMPUTER.toString());
		playerTwo.setStrategy((StrategyInterface) playerHandlerImpl.getStrategyByName("BENEVOLENT"));
		playerTwo.setStrategyName("BENEVOLENT");
		expectedPlayerList.add(playerTwo);

		assertNotEquals(expectedPlayerList, riskplay.generatePlayerListByStrategy(strategies));
	}

	/**
	 * Test for tournamentValidStrategy.
	 *
	 * @result returns true if strategy belongs to pre-defined strategy list.
	 *
	 */
	@Test
	public void validTournamentValidStrategyTest() {
		List<String> inputStrategies, predefinedStrategies;
		inputStrategies = new ArrayList<String>();
		inputStrategies.add("AGGRESIVE");
		inputStrategies.add("BENEVOLENT");
		inputStrategies.add("RANDOM");

		predefinedStrategies = new ArrayList<String>();
		predefinedStrategies.add("AGGRESIVE");
		predefinedStrategies.add("CHEATER");
		predefinedStrategies.add("BENEVOLENT");
		predefinedStrategies.add("RANDOM");
		assertEquals(false, riskplay.tournamentValidStrategy(inputStrategies, predefinedStrategies));
	}

	/**
	 * Test for tournamentValidStrategy.
	 *
	 * @result returns true if strategy belongs to pre-defined strategy list.
	 *
	 */
	@Test
	public void invalidTournamentValidStrategyTest() {
		List<String> inputStrategies, predefinedStrategies;
		inputStrategies = new ArrayList<String>();
		inputStrategies.add("AGGRESIVE");
		inputStrategies.add("HUMAN");
		inputStrategies.add("RANDOM");

		predefinedStrategies = new ArrayList<String>();
		predefinedStrategies.add("AGGRESIVE");
		predefinedStrategies.add("CHEATER");
		predefinedStrategies.add("BENEVOLENT");
		predefinedStrategies.add("RANDOM");
		assertEquals(false, riskplay.tournamentValidStrategy(inputStrategies, predefinedStrategies));
	}

	/**
	 * Test for tournamentValidMapCheck.
	 *
	 * @result returns true if map belongs to pre-existing available map list.
	 *
	 */
	@Test
	public void validtournamentValidMapCheck() {
		List<String> mapsToPlay, availableMaps;
		mapsToPlay = new ArrayList<String>();
		mapsToPlay.add("world.map");
		mapsToPlay.add("risk.map");

		availableMaps = mapManagement.getAvailableMap();
		assertEquals(true, riskplay.tournamentValidMapCheck(mapsToPlay, availableMaps));
	}

	/**
	 * Test for tournamentValidMapCheck.
	 *
	 * @result returns false if map does not belongs to pre-existing available map
	 *         list.
	 *
	 */
	@Test
	public void invalidtournamentValidMapCheck() {
		List<String> mapsToPlay, availableMaps;
		mapsToPlay = new ArrayList<String>();
		mapsToPlay.add("nonexisting.map");
		mapsToPlay.add("risk.map");

		availableMaps = mapManagement.getAvailableMap();
		assertEquals(false, riskplay.tournamentValidMapCheck(mapsToPlay, availableMaps));
	}
}
