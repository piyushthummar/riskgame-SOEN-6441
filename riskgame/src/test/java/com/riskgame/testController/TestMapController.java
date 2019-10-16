/**
 * 
 */
package com.riskgame.testController;

/**
 * 
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 */
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.riskgame.controller.MapController;
import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class TestMapController {
	/**
	 * @throws java.lang.Exception
	 */
	
	MapController mapController;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		
	}
	
	@Before
	public void beforeTest() throws Exception {
		mapController = new MapController();
		
		// Continent Details
		ContinentDto continentOne, continentTwo, continentThree;
		continentOne = new ContinentDto(1, "North-America", 5);
		continentTwo = new ContinentDto(2, "Asia", 7);
		continentThree = new ContinentDto(3, "South-America", 6);
		
		ObservableList<ContinentDto> continentList = FXCollections.observableArrayList();
		continentList.add(continentOne);
		continentList.add(continentTwo);
		continentList.add(continentThree);
		
		// Country Details
		CountryDto countryOne, countryTwo, countryThree, countryFour, countryFive;
		countryOne = new CountryDto(1, "India", "Asia");
		countryTwo = new CountryDto(2, "Canada", "North-America");
		countryThree = new CountryDto(3, "Russia", "Asia");
		countryFour = new CountryDto(4, "United States of America", "South-America");
		countryFive = new CountryDto(5, "China", "Asia");
		
		ObservableList<CountryDto> countryList = FXCollections.observableArrayList();
		countryList.add(countryOne);
		countryList.add(countryTwo);
		countryList.add(countryThree);
		countryList.add(countryFour);
		countryList.add(countryFive);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void afterTest() throws Exception {
	}

	@Test
	public void testDeleteCommonContinent() {
		assertEquals(true, mapController.deleteCommonContinent("South-America"));
	}
	
	@Test
	public void testDeleteCommonCountry() {
		assertEquals(true, mapController.deleteCommonCountry("South-America"));
	}
	
	@Test
	public void testdeleteCommonNeighbour() {
		assertEquals(true, mapController.deleteCommonNeighbour("India", "China"));
	}
	
	@Test
	public void testIsValidContinentName() {
		// Testcase returns false if continentId is null
		assertEquals(false, mapController.isValidContinentName("Europe", null));
		
		// Testcase returns false if continentName already exist
		assertEquals(false, mapController.isValidContinentName("South-America", "4"));
		
		// Testcase returns true if continentId is not null and continent does not exist
		assertEquals(true, mapController.isValidContinentName("Africa", "4"));
	}
	
	@Test
	public void testIsValidCountryName() {
		// Testcase returns false if countryId is null
		assertEquals(false, mapController.isValidCountryName("Alberta", null));
		
		// Testcase returns false if countryName already exist
		assertEquals(false, mapController.isValidCountryName("China", "6"));
		
		// Testcase returns true if continentId is not null and continent does not exist
		assertEquals(true, mapController.isValidCountryName("Ontario", "6"));
	}
	
	@Test
	public void testIsValidNeighbour() {
		// Testcase returns false if country and neighbour country are same
		assertEquals(false, mapController.isValidNeighbour("India", "India"));
		
		// Testcase returns false if country and neighbour country combination already exist
		assertEquals(false, mapController.isValidNeighbour("India", "China"));
		
		// Testcase returns true if country has a valid neighbour
		assertEquals(true, mapController.isValidNeighbour("China", "Rusia"));
	}
	
	@Test
	public void testCommandEditContinent() {
		
		assertEquals("-add Oceania 3 :=> Oceania continent saved successfully", mapController.commandEditContinent("editcontinent -add Oceania 3"));
		
		assertEquals("-add Asia 6 :=> Oceania continent name already exists", mapController.commandEditContinent("editcontinent -add Asia 6"));
		
		// Invalid continentValue
		assertEquals("-add Asia a :=> Please enter valid continent name or value", mapController.commandEditContinent("editcontinent -add Asia a"));
		
		
		
		assertEquals("-remove Asia :=> Asia continent removed successfully", mapController.commandEditContinent("editcontinent -remove Asia"));
		
		assertEquals("-remove Asia :=> Asia continent not found", mapController.commandEditContinent("editcontinent -remove Asia"));
		
		// Invalid continentName
		assertEquals("-remove Asi@ :=> Asi@ Please enter valid continent Name", mapController.commandEditContinent("editcontinent -remove Asi@"));
		
	}
	
	@Test
	public void testCommandEditCountry() {
		
		assertEquals("-add Alaska North-America :=> Alaska country saved successfully", mapController.commandEditCountry("editcountry -add Alaska North-America"));
		
		assertEquals("-add London Europe :=> Europe continent not found", mapController.commandEditCountry("editcontinent -add London Europe"));
		
		assertEquals("-add India Asia :=> India country name already exists", mapController.commandEditCountry("editcontinent -add India Asia"));
		
		// Invalid countryName
		assertEquals("-add Rusi@ Asia :=> Please enter valid country name or continent name", mapController.commandEditCountry("editcontinent -add Rusi@ Asia"));
		
		
		
		assertEquals("-remove China :=> China country removed successfully", mapController.commandEditCountry("editcontinent -remove China"));
		
		assertEquals("-remove Japan :=> Japan country not found", mapController.commandEditCountry("editcontinent -remove Japan"));
		
		// Invalid countryName
		assertEquals("-remove Chin@ :=> Chin@ Please enter valid country Name", mapController.commandEditCountry("editcontinent -remove Chin@"));
		
	}
	
	@Test
	public void testCommandEditNeighbour() {
		
		assertEquals("-add Russia China :=> Russia China saved successfully", mapController.commandEditNeighbour("editneighbor -add Russia China"));
		
		assertEquals("-add Russia China :=>  country and neighbour country pair already exists", mapController.commandEditNeighbour("editneighbor -add Russia China"));
		
		assertEquals("-add Russia Alaska :=>  country or neighbour country not found in Country's information", mapController.commandEditNeighbour("editneighbor -add Russia Alaska"));
		
		assertEquals("-add Russia Rusia :=> Country name amd Neighbour country name should not be same", mapController.commandEditNeighbour("editneighbor -add Russia Rusia"));
		
		// Invalid countryName
		assertEquals("-add Russi@ India:=> Please enter valid country name or neighbour country name", mapController.commandEditNeighbour("editneighbor -add Russi@ India"));
		
		
		
		assertEquals("-remove Russia China :=>  country and neighbour country's pair removed successfully", mapController.commandEditNeighbour("editneighbor -remove Russia China"));
		
		assertEquals("-remove India Russia :=>  country or neighbour country's pair not found", mapController.commandEditNeighbour("editneighbor -remove India Russia"));
		
		// Invalid countryName
		assertEquals("-remove Chin@ Russia:=> Please enter valid country name or neighbour country name", mapController.commandEditNeighbour("editneighbor -remove Chin@ Russia"));
		
	}
}
