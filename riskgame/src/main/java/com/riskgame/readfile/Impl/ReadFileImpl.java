package com.riskgame.readfile.Impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.riskgame.model.Continent;
import com.riskgame.model.RiskMap;
import com.riskgame.model.Territory;
import com.riskgame.readfile.ReadFileInterface;

public class ReadFileImpl implements ReadFileInterface {

	private String files;

	@Override
	public void readMap(String fileName) {
		String mapline = "";
		System.out.println("files====> >" + files);
		boolean isFiles = false;
		boolean isContinents = false;
		boolean isCountries = false;
		boolean isBoarders = false;
		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader("src/main/resources/maps/" + fileName))) {

			RiskMap riskMap = new RiskMap();
			Continent continent = null;
			Territory territory = null;
			Map<Integer, Continent> continentMap = new HashMap<>();
			int continentCount = 1;

			while ((mapline = bufferedReader.readLine()) != null) {

				if (mapline != null && !mapline.isEmpty()) {

					if (mapline.startsWith(";")) {
						continue;
					}
					if (mapline.startsWith("name")) {
						String name = mapline.substring(5);
						riskMap.setMapName(name);
						System.out.println("filename====>> " + name);
					}
					if (mapline.equalsIgnoreCase("[files]")) {
						isFiles = true;
						continue;
					}
					if (isFiles) {
						System.out.println("files---> " + mapline);
					}

					if (mapline.equalsIgnoreCase("[continents]")) {
						isFiles = false;
						isContinents = true;
						continue;
					}
					if (isContinents && !mapline.equalsIgnoreCase("[countries]")) {
						System.out.println("Continents-------> " + mapline);
						continent = new Continent();
						String[] continentArray = mapline.split(" ");
						continent.setContinentName(continentArray[0]);
						continent.setContinentValue(Integer.parseInt(continentArray[1]));
						continentMap.put(continentCount, continent);
						continentCount++;
					}
					if (mapline.equalsIgnoreCase("[countries]")) {
						isContinents = false;
						isCountries = true;
						continue;
					}
					if (isCountries && !mapline.equalsIgnoreCase("[borders]")) {

						String[] countryArray = mapline.split(" ");

						int continentIndex = Integer.parseInt(countryArray[2]);
						Continent continent1 = continentMap.get(continentIndex);

						territory = new Territory();
						territory.setTerritoryName(countryArray[1]);
						territory.setTerritoryIndex(Integer.parseInt(countryArray[0]));

//						List<Territory> territories = continent1.getTerritoryList();
//						territories.add(territory);
//						continent1.setTerritoryList(territories);

						continent1.getTerritoryList().add(territory);

						continentMap.put(continentIndex, continent1);

						System.out.println("countries-------> " + mapline);

					}
					if (mapline.equalsIgnoreCase("[borders]")) {
						isCountries = false;
						isBoarders = true;
						continue;
					}
					if (isBoarders) {
						System.out.println("borders-------> " + mapline);

					}
					// System.out.println(mapline);

				}
			}
			riskMap.setContinents(continentMap);
			System.out.println("riskMap =====> " + riskMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
