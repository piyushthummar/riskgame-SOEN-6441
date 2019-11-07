package com.riskgame.service.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;
import com.riskgame.dto.NeighbourTerritoriesDto;
import com.riskgame.model.Continent;
import com.riskgame.model.RiskMap;
import com.riskgame.model.Territory;
import com.riskgame.service.MapManagementInterface;

/**
 * This is the implementation class of MapManagementInterface having business
 * logic of map management including create map, update map,edit map, validate
 * map etc.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 * 
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Continent
 * @see com.riskgame.model.Territory
 * @see com.riskgame.service.MapManagementInterface
 */

@Service
public class MapManagementImpl implements MapManagementInterface {

	public static final String NEW_LINE = "line.separator";
	public static final String MAP_DIR_PATH = "src/main/resources/maps/";

	public static final String NAME = "name";
	public static final String FILES = "[files]";
	public static final String CONTINENTS = "[continents]";
	public static final String COUNTRIES = "[countries]";
	public static final String BORDERS = "[borders]";

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#convertToRiskMap(java.util.List,
	 *      java.util.List, java.util.List)
	 */
	public RiskMap convertToRiskMap(List<ContinentDto> continentList, List<CountryDto> countryList,
			List<NeighbourTerritoriesDto> neighbourList) {
		RiskMap riskMap = new RiskMap();

		int continentIndex = 1;
		int territoryIndex = 1;
		Map<Integer, Continent> continentMap = new HashMap<Integer, Continent>();
		Continent continent = null;
		Territory territory = null;

		for (ContinentDto continentDto : continentList) {
			continent = new Continent();
			continent.setContinentName(continentDto.getContinentName());
			continent.setContinentValue(continentDto.getContinentValue());

			for (CountryDto countryDto : countryList) {

				if (countryDto.getContinentName().equalsIgnoreCase(continentDto.getContinentName())) {

					territory = new Territory();
					territory.setTerritoryIndex(territoryIndex);
					territoryIndex++;
					territory.setContinentIndex(continentIndex);
					territory.setTerritoryName(countryDto.getCountryName());
					territory.setXAxis(0);
					territory.setYAxis(0);
					List<String> neighbourTerritory = new ArrayList<String>();

					for (NeighbourTerritoriesDto neighbourTerritoriesDto : neighbourList) {
						if (countryDto.getCountryName().equalsIgnoreCase(neighbourTerritoriesDto.getCountryName())) {
							neighbourTerritory.add(neighbourTerritoriesDto.getCountryNeighbourName());
						}
					}

					territory.setNeighbourTerritories(neighbourTerritory);

					continent.getTerritoryList().add(territory);

				}

			}

			continent.setContinentIndex(continentIndex);
			continentMap.put(continentIndex, continent);
			continentIndex++;

		}
		riskMap.setContinents(continentMap);

		return riskMap;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#convertRiskMapToDtos(com.riskgame.model.RiskMap)
	 */
	@Override
	public Map<String, Object> convertRiskMapToDtos(RiskMap riskMap) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<ContinentDto> continentDtoList = new ArrayList<ContinentDto>();
		List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
		List<NeighbourTerritoriesDto> neighbourTerritoriesDtoList = new ArrayList<NeighbourTerritoriesDto>();
		ContinentDto continentDto = null;
		CountryDto countryDto = null;
		NeighbourTerritoriesDto territoriesDto = null;
		Map<Integer, Continent> continentMap = riskMap.getContinents();
		int neighboutIndex = 1;
		for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

			continentDto = new ContinentDto();
			Continent continent = entry.getValue();
			continentDto.setId(continent.getContinentIndex());
			continentDto.setContinentName(continent.getContinentName());
			continentDto.setContinentValue(continent.getContinentValue());
			continentDtoList.add(continentDto);

			List<Territory> territoriList = continent.getTerritoryList();
			for (Territory territory : territoriList) {

				countryDto = new CountryDto();
				countryDto.setId(territory.getTerritoryIndex());
				countryDto.setCountryName(territory.getTerritoryName());
				countryDto.setContinentName(continent.getContinentName());
				countryDtoList.add(countryDto);

				List<String> neighbourList = territory.getNeighbourTerritories();
				if (neighbourList != null && !neighbourList.isEmpty()) {

					for (String neighbourName : neighbourList) {
						territoriesDto = new NeighbourTerritoriesDto();
						territoriesDto.setCountryName(territory.getTerritoryName());
						territoriesDto.setCountryNeighbourName(neighbourName);
						territoriesDto.setId(neighboutIndex);
						neighboutIndex++;
						neighbourTerritoriesDtoList.add(territoriesDto);
					}

				}

			}

		}

		map.put("ContinentList", continentDtoList);
		map.put("CountryList", countryDtoList);
		map.put("NeighbourList", neighbourTerritoriesDtoList);

		return map;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#getAvailableMap()
	 */
	@Override
	public List<String> getAvailableMap() {
		List<String> mapList = new ArrayList<String>();

		try (Stream<Path> path = Files.walk(Paths.get(MAP_DIR_PATH))) {

			mapList = path.map(filePath -> filePath.toFile().getName()).filter(fileName -> fileName.endsWith(".map"))
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mapList;
	}

	/**
	 * {@inheritDoc}
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @see com.riskgame.service.MapManagementInterface#saveMapToFile(com.riskgame.model.RiskMap)
	 */
	@Override
	public boolean saveMapToFile(RiskMap riskMap)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		boolean result = false;

		try {

			StringBuilder sbContinent = new StringBuilder(CONTINENTS).append(System.getProperty(NEW_LINE));
			StringBuilder sbCountry = new StringBuilder(COUNTRIES).append(System.getProperty(NEW_LINE));
			StringBuilder sbNeighbour = new StringBuilder(BORDERS).append(System.getProperty(NEW_LINE));

			try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(MAP_DIR_PATH + riskMap.getMapName() + ".map"), "utf-8")))) {

				Map<Integer, Continent> continentMap = riskMap.getContinents();

				for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

					Continent continent = entry.getValue();

					sbContinent
							.append(continent.getContinentName() + " " + continent.getContinentValue() + " " + "none")
							.append(System.getProperty(NEW_LINE));

					List<Territory> territoriList = continent.getTerritoryList();
					for (Territory territory : territoriList) {

						if (territory != null) {

							sbCountry
									.append(territory.getTerritoryIndex() + " " + territory.getTerritoryName() + " "
											+ territory.getContinentIndex() + " " + "0" + " " + "0")
									.append(System.getProperty(NEW_LINE));

							List<String> neighbourList = territory.getNeighbourTerritories();
							if (neighbourList != null && !neighbourList.isEmpty()) {

								sbNeighbour.append(territory.getTerritoryIndex());
								for (String neighbourName : neighbourList) {
									int neighbourIndex = getNeighbourIndexFromMap(riskMap, neighbourName);
									sbNeighbour.append(" " + neighbourIndex);
								}
								sbNeighbour.append(System.getProperty(NEW_LINE));

							}

						}

					}

				}

				writer.println("; Yura Mamyrin");
				writer.println("; Risk Map");
				writer.println();
				writer.println("; Risk " + riskMap.getMapName() + " Game Map");
				writer.println("; Dimensions: 677 x 425 Pixels");
				writer.println();
				writer.println("; Made by Zankhanaben Patel");
				writer.println(";         Koshaben Patel");
				writer.println(";         Piyush Thummar");
				writer.println(";         Raj Mistry");
				writer.println(";         Jaswanth Banavathu");
				writer.println();
				writer.println("name " + riskMap.getMapName() + " map");
				writer.println();
				writer.println(FILES);
				writer.println("pic world_pic.jpg");
				writer.println("map world_map.gif");
				writer.println("crd risk.cards");
				writer.println("prv world.jpg");
				writer.println();

				writer.println(sbContinent.toString());
				writer.println(sbCountry.toString());
				writer.println(sbNeighbour.toString());

				result = true;

			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	/**
	 * This method will return index of neighbor country from given RiskMap
	 * 
	 * @param riskMap
	 * @param neighbourName
	 * @return index of neighbor
	 */
	private int getNeighbourIndexFromMap(RiskMap riskMap, String neighbourName) {
		int index = 0;

		Map<Integer, Continent> continentMap = riskMap.getContinents();

		for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

			Continent continent = entry.getValue();

			List<Territory> territoriList = continent.getTerritoryList();
			for (Territory territory : territoriList) {

				if (territory != null) {

					if (neighbourName.equalsIgnoreCase(territory.getTerritoryName())) {
						index = territory.getTerritoryIndex();
						break;
					}
				}
			}
		}
		return index;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#readMap(java.lang.String)
	 */
	@Override
	public RiskMap readMap(String fileName) {

		String mapline = "";
		System.out.println("files====> >" + fileName);
		boolean isFiles = false;
		boolean isContinents = false;
		boolean isCountries = false;
		boolean isBoarders = false;
		RiskMap riskMap = new RiskMap();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(MAP_DIR_PATH + fileName))) {

			Continent continent = null;
			Territory territory = null;
			Map<Integer, Continent> continentMap = new HashMap<>();
			int continentCount = 1;

			while ((mapline = bufferedReader.readLine()) != null) {

				if (mapline != null && !mapline.isEmpty()) {

					if (mapline.startsWith(";")) {
						continue;
					}
					if (mapline.startsWith(NAME)) {
						String name = mapline.substring(5);
						riskMap.setMapName(name);
						System.out.println("filename====>> " + name);
					}
					if (mapline.equalsIgnoreCase(FILES)) {
						isFiles = true;
						continue;
					}
					if (isFiles) {
						System.out.println("files---> " + mapline);
					}
					if (mapline.equalsIgnoreCase(CONTINENTS)) {
						isFiles = false;
						isContinents = true;
						continue;
					}
					if (isContinents && !mapline.equalsIgnoreCase(COUNTRIES)) {
						System.out.println("Continents-------> " + mapline);
						continent = new Continent();
						String[] continentArray = mapline.split(" ");
						continent.setContinentName(continentArray[0]);
						continent.setContinentValue(Integer.parseInt(continentArray[1]));
						continent.setContinentIndex(continentCount);
						continentMap.put(continentCount, continent);
						continentCount++;
					}
					if (mapline.equalsIgnoreCase(COUNTRIES)) {
						isContinents = false;
						isCountries = true;
						continue;
					}
					if (isCountries && !mapline.equalsIgnoreCase(BORDERS)) {

						String[] countryArray = mapline.split(" ");

						int continentIndex = Integer.parseInt(countryArray[2]);
						Continent continent1 = continentMap.get(continentIndex);

						territory = new Territory();
						territory.setTerritoryName(countryArray[1]);
						territory.setTerritoryIndex(Integer.parseInt(countryArray[0]));
						territory.setContinentIndex(continentIndex);

						continent1.getTerritoryList().add(territory);

						continentMap.put(continentIndex, continent1);

						System.out.println("countries-------> " + mapline);

					}
					if (mapline.equalsIgnoreCase(BORDERS)) {
						isCountries = false;
						isBoarders = true;
						continue;
					}
					if (isBoarders) {
						System.out.println("borders-------> " + mapline);

						String[] neighbourArray = mapline.split(" ");
						Continent continent2 = getContinentDetailsbyCountryId(continentMap,
								Integer.parseInt(neighbourArray[0]));

						List<String> neighbourName = new ArrayList<String>();
						for (int i = 1; i < neighbourArray.length; i++) {
							neighbourName
									.add(getNeighbourNamebyIndex(continentMap, Integer.parseInt(neighbourArray[i])));
						}

						for (int i = 0; i < continent2.getTerritoryList().size(); i++) {
							Territory territory2 = continent2.getTerritoryList().get(i);
							if (territory2.getTerritoryIndex() == Integer.parseInt(neighbourArray[0])) {
								territory2.setNeighbourTerritories(neighbourName);
								continent2.getTerritoryList().set(i, territory2);
							}
						}

						continentMap.put(continent2.getContinentIndex(), continent2);

					}
					// System.out.println(mapline);

				}
			}
			riskMap.setContinents(continentMap);
			System.out.println("riskMap =====> " + riskMap);
		} catch (Exception e) {
			riskMap.setStatus("INVALID");
			e.printStackTrace();
		}

		return riskMap;
	}

	/**
	 * This method will return whole Continent model from given map and given
	 * ContinentId or index
	 * 
	 * @param continentMap
	 *            is map of continentIndex and Continent model
	 * @param countryIndex
	 *            is index of country for which you want the details
	 * @return
	 */
	private Continent getContinentDetailsbyCountryId(Map<Integer, Continent> continentMap, int countryIndex) {
		Continent continent = null;
		for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

			continent = entry.getValue();

			List<Territory> territoriList = continent.getTerritoryList();
			for (Territory territory : territoriList) {

				if (territory != null) {

					if (territory.getTerritoryIndex() == countryIndex) {

						return continent;
					}
				}
			}
		}
		return continent;
	}

	/**
	 * This method will return name of neighbor by given index from continent map
	 * @param continentMap is map of continentIndex and Continent model
	 * @param parseInt is an index of neighbor 
	 * @return name of neighbor
	 */
	private String getNeighbourNamebyIndex(Map<Integer, Continent> continentMap, int parseInt) {

		String neighbourName = "";

		for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

			Continent continent = entry.getValue();

			List<Territory> territoriList = continent.getTerritoryList();
			for (Territory territory : territoriList) {

				if (territory != null) {

					if (parseInt == territory.getTerritoryIndex()) {
						neighbourName = territory.getTerritoryName();
						break;
					}
				}
			}
		}
		return neighbourName;

	}

	/**
	 * {@inheritDoc}
	 * @see com.riskgame.service.MapManagementInterface#getNeighbourCountriesListByCountryName(com.riskgame.model.RiskMap, java.lang.String)
	 */
	@Override
	public List<String> getNeighbourCountriesListByCountryName(RiskMap riskMap, String countryName) {

		List<String> neightbourCountryList = new ArrayList<String>();

		for (Map.Entry<Integer, Continent> entry : riskMap.getContinents().entrySet()) {

			Continent continent = entry.getValue();

			List<Territory> territoriList = continent.getTerritoryList();
			for (Territory territory : territoriList) {

				if (territory != null) {

					if (countryName.equalsIgnoreCase(territory.getTerritoryName())) {
						neightbourCountryList = territory.getNeighbourTerritories();
						break;
					}
				}
			}
		}
		return neightbourCountryList;
	}

	/**
	 * {@inheritDoc}
	 * @see com.riskgame.service.MapManagementInterface#validateMap(com.riskgame.model.RiskMap)
	 */
	@Override
	public boolean validateMap(RiskMap riskMap) {
		boolean result = true;
		try {
			if (riskMap != null && riskMap.getContinents().size() > 0) {

				for (Map.Entry<Integer, Continent> entry : riskMap.getContinents().entrySet()) {

					Continent continent = entry.getValue();

					if (continent.getContinentIndex() != 0 && continent.getContinentValue() != 0
							&& continent.getContinentName() != null && !continent.getContinentName().isEmpty()
							&& continent.getTerritoryList() != null && !continent.getTerritoryList().isEmpty()) {

						List<Territory> territoriList = continent.getTerritoryList();

						for (Territory territory : territoriList) {

							if (territory.getContinentIndex() != 0 && territory.getTerritoryIndex() != 0
									&& territory.getTerritoryName() != null && !territory.getTerritoryName().isEmpty()
									&& territory.getNeighbourTerritories() != null
									&& !territory.getNeighbourTerritories().isEmpty()
									&& !territory.getNeighbourTerritories().contains(territory.getTerritoryName())) {

								result = true;

							} else {
								result = false;
								break;
							}
						}
					} else {
						result = false;
						break;
					}
				}
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}
