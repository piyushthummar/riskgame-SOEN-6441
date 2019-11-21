package com.riskgame.service.Impl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.riskgame.model.Continent;
import com.riskgame.model.RiskMap;
import com.riskgame.model.Territory;
import com.riskgame.service.ConquestMapInterface;

public class ConquestMapImpl implements ConquestMapInterface {

	public static final String MAP_DIR_PATH = "src/main/resources/maps/";

	@Override
	public boolean saveMapToFile(RiskMap riskMap)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		boolean result = false;

		try (PrintStream fileWriter = new PrintStream(
				new BufferedOutputStream(new FileOutputStream(MAP_DIR_PATH + riskMap.getMapName() + ".map")))) {
			fileWriter.println("[Map]");
			fileWriter.println("author=Sean O'Connor");
			fileWriter.println("image=" + riskMap.getMapName() + ".map");
			fileWriter.println("wrap=no");
			fileWriter.println("scroll=horizontal");
			fileWriter.println("warn=yes");
			fileWriter.println();

			fileWriter.println("[Continents]");

			Map<Integer, Continent> continentMap = riskMap.getContinents();

			for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

				Continent continent = entry.getValue();

				fileWriter.println(continent.getContinentName() + "=" + continent.getContinentValue());

			}
			fileWriter.println();
			fileWriter.println("[Territories]");
			for (Map.Entry<Integer, Continent> entry : continentMap.entrySet()) {

				Continent continent = entry.getValue();

				List<Territory> territoriList = continent.getTerritoryList();
				for (Territory territory : territoriList) {

					if (territory != null) {

						String territoryInfo = "";

						territoryInfo = StringUtils.arrayToDelimitedString(
								new String[] { territory.getTerritoryName(), String.valueOf(territory.getXAxis()),
										String.valueOf(territory.getYAxis()), continent.getContinentName() },
								",");

						List<String> adjTerritoryList = new ArrayList<>();

						List<String> neighbourList = territory.getNeighbourTerritories();
						if (neighbourList != null && !neighbourList.isEmpty()) {

							for (String neighbourName : neighbourList) {
								adjTerritoryList.add(neighbourName);
							}
							territoryInfo += "," + StringUtils.arrayToDelimitedString(adjTerritoryList.toArray(), ",");
							fileWriter.println(territoryInfo);

						}

					}

				}
				fileWriter.println();
			}

			if (fileWriter.checkError()) {
				result = false;
			} else {
				result = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public RiskMap readMap(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
