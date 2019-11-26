package com.riskgame.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;
import com.riskgame.dto.NeighbourTerritoriesDto;
import com.riskgame.model.RiskMap;

/**
 * This is the interface file of MapManagement which can handle all map related
 * operation include read/write to/from files.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Continent
 * @see com.riskgame.model.Territory
 */
public interface MapManagementInterface {

	/**
	 * This method will convert data added by user to map and make .map file
	 * 
	 * @param continentList is list of continentDto added by user
	 * @param countryList   is list of country DTO
	 * @param neighbourList is list of NeighbourTerritoriesDto
	 * @return
	 */
	RiskMap convertToRiskMap(List<ContinentDto> continentList, List<CountryDto> countryList,
			List<NeighbourTerritoriesDto> neighbourList);

	/**
	 * This is a reverse from above method. It will read data from .map file and
	 * convert it to DTo and send it to view side.
	 * 
	 * @param riskMap is model sent by service side
	 * @return map of DTO
	 */
	Map<String, Object> convertRiskMapToDtos(RiskMap riskMap);

	/**
	 * This method will return list of map available in project
	 * 
	 * @return list of map
	 */
	List<String> getAvailableMap();

	/**
	 * This method will make returns true if riskmap file successfully created from
	 * data given from user side.
	 * 
	 * @param riskMap is model sent by service side
	 * @return true if file got saved successfully
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	boolean saveMapToFile(RiskMap riskMap) throws UnsupportedEncodingException, FileNotFoundException, IOException;

	/**
	 * This method will read file and store data in riskMap Model into particular
	 * field
	 * 
	 * @param fileName
	 * @return RiskMap model
	 */
	RiskMap readMap(String fileName);

	/**
	 * This method will validate given riskmap object. return true if map is correct
	 * and false otherwise
	 * 
	 * @param riskMap
	 * @return
	 */
	boolean validateMap(RiskMap riskMap);

	/**
	 * This method will return List of all adjacent territories of the given
	 * country.
	 * 
	 * @param riskMap     model
	 * @param countryName name of the country for which you want the
	 *                    adjacentTerritories
	 * @return
	 */
	List<String> getNeighbourCountriesListByCountryName(RiskMap riskMap, String countryName);

	/**
	 * This method will check if given file is conquest or not and then caller
	 * method will call respected adapter for further process
	 * 
	 * @param fileName
	 * @return true if given map file is of type conquest
	 */
	boolean isMapConquest(String fileName);

}
