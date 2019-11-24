package com.riskgame.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.riskgame.adapter.DominationToConquestAdapter;
import com.riskgame.config.StageManager;
import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;
import com.riskgame.dto.NeighbourTerritoriesDto;
import com.riskgame.model.RiskMap;
import com.riskgame.service.ConquestMapInterface;
import com.riskgame.service.MapManagementInterface;
import com.riskgame.service.Impl.ConquestMapImpl;
import com.riskgame.service.Impl.MapManagementImpl;
import com.riskgame.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is entry point of Map management related operation like edit
 * map,create map, load map, validate map etc.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 */
@Slf4j
@Controller
public class MapController implements Initializable {

	/**
	 * It represents Column ContinentId in the table
	 */
	@FXML
	private TableColumn<ContinentDto, Integer> colContinentId;

	/**
	 * It represents Combobox of Continent
	 */
	@FXML
	private ComboBox<String> continentComboBox;

	/**
	 * It represents Column ContinentValue in the table
	 */
	@FXML
	private TableColumn<ContinentDto, Integer> colContinentValue;

	/**
	 * It represents Column Edit Country in the table
	 */
	@FXML
	private TableColumn<CountryDto, Boolean> colEditCountry;

	/**
	 * It represents deleteCountry menu
	 */
	@FXML
	private MenuItem deleteCountry;

	/**
	 * It represents Column CountryName in the table
	 */
	@FXML
	private TableColumn<CountryDto, String> colCountryName;

	/**
	 * It represents Column NeighbourId in the table
	 */
	@FXML
	private TableColumn<NeighbourTerritoriesDto, Integer> colNeighborId;

	/**
	 * It represents Column NeighbourId in the table
	 */
	@FXML
	private ComboBox<String> countryComboBox;

	/**
	 * It represents Column NeighbourId in the table
	 */
	@FXML
	private TableColumn<NeighbourTerritoriesDto, String> colCountryNeighbor;

	/**
	 * It represents menu deleteContinent
	 */
	@FXML
	private MenuItem deleteContinent;

	/**
	 * It represents TableView of neighborTable
	 */
	@FXML
	private TableView<NeighbourTerritoriesDto> neighborTable;

	/**
	 * It represents Column countryIdId in the table
	 */
	@FXML
	private TableColumn<CountryDto, Integer> colCountryId;

	/**
	 * It represents neighbor button to set neighbor
	 */
	@FXML
	private Button btnNeighbor;

	/**
	 * It represents Column ContinentName in the table
	 */
	@FXML
	private TableColumn<ContinentDto, String> colContinentName;

	/**
	 * It represents Column ContinentName in the table
	 */
	@FXML
	private TableColumn<NeighbourTerritoriesDto, Boolean> colEditNeighbor;

	/**
	 * It represents Column ContinentName in the table
	 */
	@FXML
	private TableColumn<CountryDto, String> colCountryContinent;

	/**
	 * It represents Column Edit Continent in the table
	 */
	@FXML
	private TableColumn<ContinentDto, Boolean> colEditContinent;

	/**
	 * It represents button country to add country
	 */
	@FXML
	private Button btnCountry;

	/**
	 * It represents the TextField of continentValue
	 */
	@FXML
	private TextField continentValue;

	/**
	 * It represents Column Edit Continent in the table
	 */
	@FXML
	private TableView<ContinentDto> continentTable;

	/**
	 * It represents button add Continent in the table
	 */
	@FXML
	private Button btnContinent;

	/**
	 * It represents Table view of CountryDto
	 */
	@FXML
	private TableView<CountryDto> countryTable;

	/**
	 * It represents TextField of countryName
	 */
	@FXML
	private TextField countryName;

	/**
	 * It represents menuItem for deleteNeighbor
	 */
	@FXML
	private MenuItem deleteNeighbor;

	/**
	 * It represents TextField of continentName
	 */
	@FXML
	private TextField continentName;

	/**
	 * It represents column of NeighborName
	 */
	@FXML
	private TableColumn<NeighbourTerritoriesDto, String> colNeighborName;

	/**
	 * It represents comboBox of neighbor
	 */
	@FXML
	private ComboBox<String> neighborComboBox;

	/**
	 * It represents label of continentId
	 */
	@FXML
	private Label lblContinentId;

	/**
	 * It represents label of countryId
	 */
	@FXML
	private Label lblCountryId;

	/**
	 * It represents label of neighbourId
	 */
	@FXML
	private Label lblNeighbourId;

	/**
	 * It represents button to edit map
	 */
	@FXML
	private Button btnEditMap;

	/**
	 * It represents TextField of command
	 */
	@FXML
	private TextField commandLine;

	/**
	 * It represents textArea of console
	 */
	@FXML
	private TextArea consoleArea;

	/**
	 * It represents TextField of fileName
	 */
	@FXML
	private TextField fileNameTextField;

	/**
	 * It represents button fire command
	 */
	@FXML
	private Button btnFireCommand;

	/**
	 * It represents button savemap
	 */
	@FXML
	private Button btnSaveMap;

	/**
	 * It represents button reset map
	 */
	@FXML
	private Button btnResetMap;

	/**
	 * It represents back button
	 */
	@FXML
	private Button btnback;

	/**
	 * It represents button of playgame
	 */
	@FXML
	private Button btnplaygame;

	private static int continentId = 1;
	private static int countryId = 1;
	private static int neighbourId = 1;
	private static boolean isConquestRead=false;

	private ObservableList<ContinentDto> continentList = FXCollections.observableArrayList();
	private ObservableList<CountryDto> countryList = FXCollections.observableArrayList();
	private ObservableList<NeighbourTerritoriesDto> neighbourList = FXCollections.observableArrayList();

	private ObservableList<String> continentComboValue = FXCollections.observableArrayList();

	private ObservableList<String> countryComboValue = FXCollections.observableArrayList();

	private ObservableList<String> neighbourComboValue = FXCollections.observableArrayList();

	@Autowired
	private MapManagementImpl mapManagementImpl;

	@Lazy
	@Autowired
	private StageManager stageManager;

	/**
	 * To initialize controller
	 * 
	 * @param location
	 *            of the FXML file
	 * @param resources
	 *            is properties information
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initMapView();

	}

	/**
	 * This method will initialize the MapView by loading all details
	 */
	private void initMapView() {

		continentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		countryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		neighborTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		setContinentTableColumnProperties();
		setCountryTableColumnProperties();
		setNeighborTableColumnProperties();

		loadContinentDetails();
		loadCountryDetails();
		loadNeighbourDetails();

		continentId = 1;
		countryId = 1;
		neighbourId = 1;

		continentList.clear();
		lblContinentId.setText(null);
		continentName.clear();
		continentValue.clear();

		countryList.clear();
		lblCountryId.setText(null);
		countryName.clear();
		continentComboValue.clear();

		neighbourList.clear();
		lblNeighbourId.setText(null);
		countryComboValue.clear();
		neighbourComboValue.clear();

		fileNameTextField.setText(null);
		fileNameTextField.setDisable(false);

		commandLine.setText(null);
		consoleArea.setText(null);

	}

	/**
	 * This method will load the ContinentDetails from the map
	 */
	private void loadContinentDetails() {

		continentTable.setItems(continentList);
	}

	/**
	 * This method will load country details from map
	 */
	private void loadCountryDetails() {

		List<String> continentInfo = continentList.stream().map(ContinentDto::getContinentName)
				.collect(Collectors.toList());
		continentComboValue.clear();
		continentComboValue.addAll(continentInfo);
		continentComboBox.setItems(continentComboValue);
		countryTable.setItems(countryList);
	}

	/**
	 * This method will load neighbor details from map
	 */
	private void loadNeighbourDetails() {

		List<String> countryInfo = countryList.stream().map(CountryDto::getCountryName).collect(Collectors.toList());

		countryComboValue.clear();
		countryComboValue.addAll(countryInfo);
		countryComboBox.setItems(countryComboValue);

		neighbourComboValue.clear();
		neighbourComboValue.addAll(countryInfo);
		neighborComboBox.setItems(neighbourComboValue);
		neighborTable.setItems(neighbourList);

	}

	/**
	 * This method will set column name of ContinentTable from ContinentDTO
	 */
	private void setContinentTableColumnProperties() {
		colContinentId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colContinentName.setCellValueFactory(new PropertyValueFactory<>("continentName"));
		colContinentValue.setCellValueFactory(new PropertyValueFactory<>("continentValue"));
		colEditContinent.setCellFactory(continentCellFactory);
	}

	/**
	 * This method will set column name of CountryTable from CountryDTO
	 */
	private void setCountryTableColumnProperties() {
		colCountryId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		colCountryContinent.setCellValueFactory(new PropertyValueFactory<>("continentName"));
		colEditCountry.setCellFactory(countryCellFactory);

	}

	/**
	 * This method will set column name of NeighborTable from NeighborDTO
	 */
	private void setNeighborTableColumnProperties() {
		colNeighborId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountryNeighbor.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		colNeighborName.setCellValueFactory(new PropertyValueFactory<>("countryNeighbourName"));
		colEditNeighbor.setCellFactory(neighbourCellFactory);

	}

	/**
	 * It will return selected value from ContinentTable
	 */
	Callback<TableColumn<ContinentDto, Boolean>, TableCell<ContinentDto, Boolean>> continentCellFactory = new Callback<TableColumn<ContinentDto, Boolean>, TableCell<ContinentDto, Boolean>>() {
		@Override
		public TableCell<ContinentDto, Boolean> call(final TableColumn<ContinentDto, Boolean> param) {
			final TableCell<ContinentDto, Boolean> cell = new TableCell<ContinentDto, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							ContinentDto continentDto = getTableView().getItems().get(getIndex());
							updateContinent(continentDto);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateContinent(ContinentDto continentDto) {
					lblContinentId.setText(String.valueOf(continentDto.getId()));
					continentName.setText(continentDto.getContinentName());
					continentValue.setText(String.valueOf(continentDto.getContinentValue()));

				}
			};
			return cell;
		}
	};

	/**
	 * It will return selected value from CountryTable
	 */
	Callback<TableColumn<CountryDto, Boolean>, TableCell<CountryDto, Boolean>> countryCellFactory = new Callback<TableColumn<CountryDto, Boolean>, TableCell<CountryDto, Boolean>>() {
		@Override
		public TableCell<CountryDto, Boolean> call(final TableColumn<CountryDto, Boolean> param) {
			final TableCell<CountryDto, Boolean> cell = new TableCell<CountryDto, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							CountryDto countryDto = getTableView().getItems().get(getIndex());
							updateCountry(countryDto);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateCountry(CountryDto countryDto) {
					lblCountryId.setText(String.valueOf(countryDto.getId()));
					countryName.setText(countryDto.getCountryName());
					continentComboBox.getSelectionModel().select(countryDto.getContinentName());

				}
			};
			return cell;
		}
	};

	/**
	 * It will return selected value from NeighborTable
	 */
	Callback<TableColumn<NeighbourTerritoriesDto, Boolean>, TableCell<NeighbourTerritoriesDto, Boolean>> neighbourCellFactory = new Callback<TableColumn<NeighbourTerritoriesDto, Boolean>, TableCell<NeighbourTerritoriesDto, Boolean>>() {
		@Override
		public TableCell<NeighbourTerritoriesDto, Boolean> call(
				final TableColumn<NeighbourTerritoriesDto, Boolean> param) {
			final TableCell<NeighbourTerritoriesDto, Boolean> cell = new TableCell<NeighbourTerritoriesDto, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							NeighbourTerritoriesDto neighbourTerritoriesDto = getTableView().getItems().get(getIndex());
							updateNeighbour(neighbourTerritoriesDto);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateNeighbour(NeighbourTerritoriesDto neighbourTerritoriesDto) {
					lblNeighbourId.setText(String.valueOf(neighbourTerritoriesDto.getId()));
					countryComboBox.getSelectionModel().select(neighbourTerritoriesDto.getCountryName());
					neighborComboBox.getSelectionModel().select(neighbourTerritoriesDto.getCountryNeighbourName());

				}
			};
			return cell;
		}
	};

	/**
	 * This method will delete continent before saveMap got clicked
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void deleteContinent(ActionEvent event) {

		List<ContinentDto> continentDtoList = continentTable.getSelectionModel().getSelectedItems();
		ContinentDto continentDtoDel = continentDtoList.get(0);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {

			deleteCommonContinent(continentDtoDel.getContinentName());
			alertMesage("Continent deleted successfully");

		}

	}

	/**
	 * This method will return true and break if continent got removed and this
	 * method is common for both commandline and GUI
	 * 
	 * @param continentName
	 *            the name of the continent you want to check duplicate for
	 * @return false if duplicate not found
	 */
	public boolean deleteCommonContinent(String continentName) {
		boolean result = false;

		for (int i = 0; i < continentList.size(); i++) {

			if (continentList.get(i).getContinentName().equalsIgnoreCase(continentName)) {
				continentList.remove(i);
				result = true;
				break;
			}

		}

		if (result) {
			loadContinentDetails();
			// delete country
			List<String> countryToDel = new ArrayList<String>();
			for (CountryDto countryDto : countryList) {
				if (countryDto.getContinentName().equalsIgnoreCase(continentName)) {
					countryToDel.add(countryDto.getCountryName());
				}
			}
			countryList.removeIf(country -> country.getContinentName().equalsIgnoreCase(continentName));
			loadCountryDetails();

			// deleteNeighbour
			for (String country : countryToDel) {
				neighbourList.removeIf(neighbour -> neighbour.getCountryName().equalsIgnoreCase(country));
				neighbourList.removeIf(neighbour -> neighbour.getCountryNeighbourName().equalsIgnoreCase(country));
			}
			loadNeighbourDetails();
		}
		return result;
	}

	/**
	 * This method will delete country before saveMap got clicked
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void deleteCountry(ActionEvent event) {

		List<CountryDto> countryDtoList = countryTable.getSelectionModel().getSelectedItems();
		CountryDto countryDto = countryDtoList.get(0);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			deleteCommonCountry(countryDto.getCountryName());
			alertMesage("Country deleted successfully");

		}

	}

	/**
	 * This method will return true and break if country got removed and this
	 * method is common for both commandline and GUI
	 * 
	 * @param countryName
	 *            the name of the country you want to check duplicate for
	 * 
	 * @return false if duplicate not found
	 */
	public boolean deleteCommonCountry(String countryName) {
		boolean result = false;

		for (int i = 0; i < countryList.size(); i++) {

			if (countryList.get(i).getCountryName().equalsIgnoreCase(countryName)) {
				countryList.remove(i);
				result = true;
				break;
			}
		}

		if (result) {
			loadCountryDetails();
			neighbourList.removeIf(neighbour -> neighbour.getCountryName().equalsIgnoreCase(countryName));
			neighbourList.removeIf(neighbour -> neighbour.getCountryNeighbourName().equalsIgnoreCase(countryName));
			loadNeighbourDetails();
		}
		return result;
	}

	/**
	 * This method will delete Neighbor before saveMap got clicked
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void deleteNeighbor(ActionEvent event) {

		List<NeighbourTerritoriesDto> neighbourDtoList = neighborTable.getSelectionModel().getSelectedItems();
		NeighbourTerritoriesDto neighbourDto = neighbourDtoList.get(0);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {

			deleteCommonNeighbour(neighbourDto.getCountryName(), neighbourDto.getCountryNeighbourName());
			alertMesage("Neighbour deleted successfully");

		}

	}

	/**
	 * This method will return true and break if neighbor country got removed and this
	 * method is common for both commandline and GUI
	 * 
	 * @param countryName
	 *            the name of the country you want to check duplicate for
	 * @param neighbourName
	 *            the name of the neighbor you want to check duplicate for
	 * @return
	 */
	public boolean deleteCommonNeighbour(String countryName, String neighbourName) {
		boolean result = false;

		for (int i = 0; i < neighbourList.size(); i++) {
			NeighbourTerritoriesDto dto = neighbourList.get(i);
			if (dto.getCountryName().equalsIgnoreCase(countryName)
					&& dto.getCountryNeighbourName().equalsIgnoreCase(neighbourName)) {
				neighbourList.remove(i);
				result = true;
				break;
			}
		}

		if (result) {
			loadNeighbourDetails();
		}

		return result;
	}

	/**
	 * This method will save Continent when saveMap got clicked or command got fired
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void saveContinent(ActionEvent event) {

		if (validate("Continent Name", continentName.getText(), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
				&& validate("Continent Value", continentValue.getText(), "[1-9][0-9]*")
				&& isValidContinentName(continentName.getText(), lblContinentId.getText())) {

			if (lblContinentId.getText() == null || lblContinentId.getText() == "") {
				saveCommonContinent(continentName.getText(), continentValue.getText());
				alertMesage("Continent saved successfully");

			} else {

				String oldContinentName = "";

				for (int i = 0; i < continentList.size(); i++) {

					if (String.valueOf(continentList.get(i).getId()).equals(lblContinentId.getText())) {
						ContinentDto continentDto = continentList.get(i);
						oldContinentName = continentDto.getContinentName();
						continentDto.setContinentName(continentName.getText());
						continentDto.setContinentValue(Integer.parseInt(continentValue.getText()));
						continentList.set(i, continentDto);
						break;
					}

				}

				alertMesage("Continent updated successfully");
				// continentTable.setItems(continentList);
				// loadContinentComboBox();
				loadContinentDetails();

				for (int i = 0; i < countryList.size(); i++) {
					CountryDto countryDto = countryList.get(i);
					if (oldContinentName.equalsIgnoreCase(countryDto.getContinentName())) {
						countryDto.setContinentName(continentName.getText());
						countryList.set(i, countryDto);
					}
				}
				loadCountryDetails();
			}
			clearContinentFields();
		}
	}

	/**
	 * This method will save data for both commandLine and GUI
	 * 
	 * @param name
	 *            name of continent
	 * @param value
	 *            value of Continent
	 */
	private void saveCommonContinent(String name, String value) {

		ContinentDto continentDto = new ContinentDto();
		continentDto.setId(continentId);
		continentId++;
		continentDto.setContinentName(name);
		continentDto.setContinentValue(Integer.parseInt(value));
		continentList.add(continentDto);
		loadContinentDetails();
		loadCountryDetails();
	}

	/**
	 * @param continentName
	 *            name of continent to validate
	 * @param continentId
	 *            name of continentId to valid
	 * @return true if validation got succeed
	 */
	public boolean isValidContinentName(String continentName, String continentId) {
		boolean isValid = true;

		for (ContinentDto continentDto : continentList) {
			// for create
			if (continentName.equalsIgnoreCase(continentDto.getContinentName())
					&& (continentId == null || continentId.isEmpty())) {
				alertMesage("Continent name already exists");
				isValid = false;
				break;

				// for update
			} else if ((continentId != null && !continentId.isEmpty())
					&& (Integer.parseInt(continentId) != continentDto.getId())
					&& (continentName.equalsIgnoreCase(continentDto.getContinentName()))) {
				alertMesage("Continent name already exists");
				isValid = false;
				break;
			}

		}

		return isValid;
	}

	/**
	 * This method will save Country when saveMap got clicked or command got fired
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void saveCountry(ActionEvent event) {

		if (validate("Country Name", countryName.getText(), "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
				&& emptyValidation("Continent", continentComboBox.getSelectionModel().getSelectedItem() == null)
				&& isValidCountryName(countryName.getText(), lblCountryId.getText())) {

			if (lblCountryId.getText() == null || lblCountryId.getText() == "") {

				saveCommonCountry(countryName.getText(), continentComboBox.getSelectionModel().getSelectedItem());
				alertMesage("Country saved successfully");

			} else {

				String oldCountryName = "";

				for (int i = 0; i < countryList.size(); i++) {

					if (String.valueOf(countryList.get(i).getId()).equals(lblCountryId.getText())) {
						CountryDto countryDto = countryList.get(i);
						oldCountryName = countryDto.getCountryName();
						countryDto.setCountryName(countryName.getText());
						countryDto.setContinentName(continentComboBox.getSelectionModel().getSelectedItem());
						countryList.set(i, countryDto);
						break;
					}

				}

				alertMesage("Country updated successfully");
				// countryTable.setItems(countryList);
				loadCountryDetails();

				for (int i = 0; i < neighbourList.size(); i++) {
					NeighbourTerritoriesDto neighbourDto = neighbourList.get(i);

					if (neighbourDto.getCountryName().equalsIgnoreCase(oldCountryName)) {
						neighbourDto.setCountryName(countryName.getText());
					} else if (neighbourDto.getCountryNeighbourName().equalsIgnoreCase(oldCountryName)) {
						neighbourDto.setCountryNeighbourName(countryName.getText());
					}

					neighbourList.set(i, neighbourDto);
				}
				loadNeighbourDetails();
			}
			clearCountryFields();
		}
	}

	/**
	 * This method will save country given from both GUI and command line
	 * 
	 * @param countryName
	 *            name of the country
	 * @param continentName
	 *            name of continent
	 */
	private void saveCommonCountry(String countryName, String continentName) {
		CountryDto countryDto = new CountryDto();
		countryDto.setId(countryId);
		countryId++;
		countryDto.setCountryName(countryName);
		countryDto.setContinentName(continentName);

		countryList.add(countryDto);

		// countryTable.setItems(countryList);
		loadCountryDetails();
		loadNeighbourDetails();
	}

	/**
	 * This method will check if CountryName and Id is valid or not
	 * 
	 * @param countryName
	 * @param countryId
	 * @return true if countryName and countryId got validated
	 */
	public boolean isValidCountryName(String countryName, String countryId) {
		boolean isValid = true;

		for (CountryDto countryDto : countryList) {

			// for create
			if (countryName.equalsIgnoreCase(countryDto.getCountryName())
					&& (countryId == null || countryId.isEmpty())) {
				alertMesage("Country name already exists");
				isValid = false;
				break;

				// for update
			} else if ((countryId != null && !countryId.isEmpty())
					&& (Integer.parseInt(countryId) != countryDto.getId())
					&& (countryName.equalsIgnoreCase(countryDto.getCountryName()))) {
				alertMesage("Country name already exists");
				isValid = false;
				break;
			}

		}

		return isValid;
	}

	/**
	 * This method will check if CountryName and Neighborcountry name is valid or
	 * not
	 * 
	 * @param countryName
	 * @param neighbourCountryName
	 * @return true if countryName and Neighborcountry name got validated
	 */
	public boolean isValidNeighbour(String countryName, String neighbourCountryName) {
		boolean isValid = true;

		if (countryName.equalsIgnoreCase(neighbourCountryName)) {
			alertMesage("Please select valid options");
			isValid = false;
		} else {

			for (NeighbourTerritoriesDto neighbourDto : neighbourList) {

				if (countryName.equalsIgnoreCase(neighbourDto.getCountryName())
						&& neighbourCountryName.equalsIgnoreCase(neighbourDto.getCountryNeighbourName())) {
					alertMesage("Combination of value already exists");
					isValid = false;

				}

			}

		}

		return isValid;
	}

	/**
	 * This method will save neighbor when saveMap got clicked or command got fired
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void saveNeighbor(ActionEvent event) {

		if (emptyValidation("Country", countryComboBox.getSelectionModel().getSelectedItem() == null)

				&& emptyValidation("Neighbour Country", neighborComboBox.getSelectionModel().getSelectedItem() == null)

				&& isValidNeighbour(countryComboBox.getSelectionModel().getSelectedItem(),
						neighborComboBox.getSelectionModel().getSelectedItem())) {

			if (lblNeighbourId.getText() == null || lblNeighbourId.getText() == "") {

				saveCommonNeighbour(countryComboBox.getSelectionModel().getSelectedItem(),
						neighborComboBox.getSelectionModel().getSelectedItem());
				alertMesage("Neighbour saved successfully");

			} else {

				for (int i = 0; i < neighbourList.size(); i++) {

					if (String.valueOf(neighbourList.get(i).getId()).equals(lblNeighbourId.getText())) {
						NeighbourTerritoriesDto neighbourDto = neighbourList.get(i);
						neighbourDto.setCountryName(countryComboBox.getSelectionModel().getSelectedItem());
						neighbourDto.setCountryNeighbourName(neighborComboBox.getSelectionModel().getSelectedItem());
						neighbourList.set(i, neighbourDto);

					}

				}

				alertMesage("Neighbour updated successfully");
				// countryTable.setItems(countryList);
				loadNeighbourDetails();
			}

			clearNeighbourFields();

		}

	}

	/**
	 * This method will save neighbor country given from both GUI and command line
	 * 
	 * @param country
	 * @param neighbourCountry
	 */
	private void saveCommonNeighbour(String country, String neighbourCountry) {

		NeighbourTerritoriesDto neighbourDto = new NeighbourTerritoriesDto();
		neighbourDto.setId(neighbourId);
		neighbourId++;
		neighbourDto.setCountryName(country);
		neighbourDto.setCountryNeighbourName(neighbourCountry);

		neighbourList.add(neighbourDto);
		// neighborTable.setItems(value);
		loadNeighbourDetails();
	}

	/**
	 * This is common validate method used for all commands and inputs
	 * 
	 * @param field
	 *            the name of the field to be display in alert
	 * @param value
	 *            String to be validated
	 * @param pattern
	 *            regex for validation
	 * @return
	 */
	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	/**
	 * This method used to print alert message with given string
	 * 
	 * @param alertMessage
	 *            is the message to print
	 */
	private void alertMesage(String alertMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("Saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(alertMessage);
		alert.showAndWait();
	}

	/**
	 * This method will validate the comboBox if they are empty or not
	 * 
	 * @param field
	 *            the name of the field to be display in alert
	 * @param empty
	 * @return false if dropDown menu is empty
	 */
	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	/**
	 * This method is used to give alert message if validation is not true
	 * 
	 * @param field
	 *            the name of the field to be display in alert
	 * @param empty
	 */
	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (empty)
			alert.setContentText("Please Select or Enter valid " + field);
		else
			alert.setContentText("Please Select or Enter Valid " + field);

		alert.showAndWait();
	}

	/**
	 * This method is used to clear continent field in GUI after particular
	 * operation
	 */
	private void clearContinentFields() {
		lblContinentId.setText(null);
		continentName.clear();
		continentValue.clear();
	}

	/**
	 * This method is used to clear country field in GUI after particular operation
	 */
	private void clearCountryFields() {
		lblCountryId.setText(null);
		countryName.clear();
		continentComboBox.getSelectionModel().clearSelection();
	}

	/**
	 * This method is used to clear neighbor field in GUI after particular operation
	 */
	private void clearNeighbourFields() {
		lblNeighbourId.setText(null);
		countryComboBox.getSelectionModel().clearSelection();
		neighborComboBox.getSelectionModel().clearSelection();
	}

	/**
	 * This method represent fire command button onAction where all commands got
	 * separated and sent it to respective methods
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void fireCommand(ActionEvent event) {
		consoleArea.clear();
		try {
			String command = commandLine.getText();
			if (command != null && !command.isEmpty()) {

				if (command.startsWith("editcontinent")) {
					consoleArea.setText(commandEditContinent(command));
				} else if (command.startsWith("editcountry")) {
					consoleArea.setText(commandEditCountry(command));
				} else if (command.startsWith("editneighbor") || command.startsWith("editneighbour")) {
					consoleArea.setText(commandEditNeighbour(command));
				} else if (command.startsWith("showmap")) {
					RiskMap riskMap = mapManagementImpl.convertToRiskMap(continentList, countryList, neighbourList);
					consoleArea.setText(riskMap.toString());
				} else if (command.startsWith("savemap")) {
					String result = saveMapToFile(Arrays.asList(command.split(" ")).get(1));
					consoleArea.setText(result);
				} else if (command.startsWith("editmap")) {

					String editFileName = Arrays.asList(command.split(" ")).get(1);

					if (validateInput(editFileName, "[a-zA-Z]+")) {

						List<String> mapNameList = mapManagementImpl.getAvailableMap();
						

						if (mapNameList.contains(editFileName + ".map")) {
							consoleArea.setText(EditMap(editFileName + ".map"));
						} else {
							consoleArea
									.setText("Map not found in system Please create New map and after editing save it");
							fileNameTextField.setText(editFileName);
							fileNameTextField.setDisable(true);
						}

					} else {
						consoleArea.setText("Please enter valid file name for editMap");
					}

				} else if (command.startsWith("validatemap")) {

					RiskMap riskMap = mapManagementImpl.convertToRiskMap(continentList, countryList, neighbourList);
					boolean validMap = mapManagementImpl.validateMap(riskMap);

					if (validMap) {
						consoleArea.setText("Map is valid");
					} else {
						consoleArea.setText("MapValidationError : Invalid Map Please correct Map");
					}

				} else {
					consoleArea.setText("Please enter valid command");
				}

			} else {
				consoleArea.setText("Please enter valid command");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("Exception in fire command", e);
			consoleArea.setText("Please enter valid command");
		}

	}

	/**
	 * This method will analyze edit Continent command, validate and then call
	 * particular method for next operation
	 * 
	 * @param commandLine
	 *            is edit command sent from user
	 * @return proper message of result after edit Continent
	 */
	public String commandEditContinent(String commandLine) {
		StringBuilder result = new StringBuilder();
		String cName = "";
		String cValue = "";
		List<String> command = Arrays.asList(commandLine.split(" "));

		for (int i = 0; i < command.size(); i++) {

			if (command.get(i).equalsIgnoreCase("-add")) {
				cName = command.get(i + 1);
				cValue = command.get(i + 2);
				String message = "-add " + cName + " " + cValue + " :=> ";
				if (validateInput(cName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$") && validateInput(cValue, "[1-9][0-9]*")) {
					boolean isvalidName = true;
					for (ContinentDto continentDto : continentList) {
						if (continentDto != null && cName.equalsIgnoreCase(continentDto.getContinentName())) {
							result.append(message + cName + " continent name already exists")
									.append(System.getProperty("line.separator"));
							isvalidName = false;
							break;
						}
					}
					if (isvalidName) {
						saveCommonContinent(cName, cValue);
						result.append(message + cName + " continent saved successfully")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + "Please enter valid continent name or value")
							.append(System.getProperty("line.separator"));
				}

			} else if (command.get(i).equalsIgnoreCase("-remove")) {
				cName = command.get(i + 1);
				String message = "-remove " + cName + " :=> ";
				if (validateInput(cName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {

					if (deleteCommonContinent(cName)) {
						result.append(message + cName + " continent removed successfully")
								.append(System.getProperty("line.separator"));
					} else {
						result.append(message + cName + " continent not found")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + cName + " Please enter valid continent Name")
							.append(System.getProperty("line.separator"));
				}
			}

		}

		return result.toString();

	}

	/**
	 * This method will analyze edit Country command, validate and then call
	 * particular method for next operation
	 * 
	 * @param commandLine
	 *            is edit command sent from user
	 * @return proper message of result after edit Country
	 */
	public String commandEditCountry(String commandLine) {
		StringBuilder result = new StringBuilder();
		String countryName = "";
		String continentName = "";
		List<String> command = Arrays.asList(commandLine.split(" "));

		for (int i = 0; i < command.size(); i++) {

			if (command.get(i).equalsIgnoreCase("-add")) {
				countryName = command.get(i + 1);
				continentName = command.get(i + 2);
				String message = "-add " + countryName + " " + continentName + " :=> ";
				if (validateInput(countryName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
						&& validateInput(continentName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {
					final String cName = countryName;
					final String contiName = continentName;
					CountryDto dto = countryList.stream().filter(x -> cName.equalsIgnoreCase(x.getCountryName()))
							.findAny().orElse(null);
					if (dto == null) {

						ContinentDto continentDto = continentList.stream()
								.filter(x -> contiName.equals(x.getContinentName())).findAny().orElse(null);
						if (continentDto != null) {
							saveCommonCountry(countryName, continentName);
							result.append(message + countryName + " country saved successfully")
									.append(System.getProperty("line.separator"));
						} else {
							result.append(message + continentName + " continent not found")
									.append(System.getProperty("line.separator"));
						}

					} else {
						result.append(message + countryName + " country name already exists")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + "Please enter valid country name or continent name")
							.append(System.getProperty("line.separator"));
				}

			} else if (command.get(i).equalsIgnoreCase("-remove")) {
				countryName = command.get(i + 1);

				String message = "-remove " + countryName + " :=> ";

				if (validateInput(countryName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {

					if (deleteCommonCountry(countryName)) {
						result.append(message + countryName + " country removed successfully")
								.append(System.getProperty("line.separator"));
					} else {
						result.append(message + countryName + " country not found")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + countryName + " Please enter valid country Name")
							.append(System.getProperty("line.separator"));
				}
			}

		}
		return result.toString();
	}

	/**
	 * This method will analyze edit Neighbor command, validate and then call
	 * particular method for next operation
	 * 
	 * @param commandLine
	 *            is edit command sent from user
	 * @return proper message of result after edit neighbor country
	 */
	public String commandEditNeighbour(String commandLine) {
		StringBuilder result = new StringBuilder();
		String cName = "";
		String ncName = "";
		List<String> command = Arrays.asList(commandLine.split(" "));

		for (int i = 0; i < command.size(); i++) {

			if (command.get(i).equalsIgnoreCase("-add")) {
				cName = command.get(i + 1);
				ncName = command.get(i + 2);

				String message = "-add " + cName + " " + ncName + " :=> ";

				if (validateInput(cName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
						&& validateInput(ncName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {

					final String country = cName;
					final String neighbourCountry = ncName;

					if (!country.equalsIgnoreCase(neighbourCountry)) {

						CountryDto countryDto = countryList.stream().filter(x -> country.equals(x.getCountryName()))
								.findAny().orElse(null);
						CountryDto neighbourDto = countryList.stream()
								.filter(x -> neighbourCountry.equals(x.getCountryName())).findAny().orElse(null);

						if (countryDto != null && neighbourDto != null) {

							NeighbourTerritoriesDto countryNeighbourDto = neighbourList.stream()
									.filter((x) -> country.equalsIgnoreCase(x.getCountryName())
											&& neighbourCountry.equalsIgnoreCase(x.getCountryNeighbourName()))
									.findAny().orElse(null);
							if (countryNeighbourDto == null) {
								saveCommonNeighbour(country, neighbourCountry);
								result.append(message + country + " " + neighbourCountry + " saved successfully")
										.append(System.getProperty("line.separator"));

							} else {
								result.append(message + " country and neighbour country pair already exists")
										.append(System.getProperty("line.separator"));
							}

						} else {
							result.append(message + " country or neighbour country not found in Country's information")
									.append(System.getProperty("line.separator"));
						}

					} else {
						result.append(message + "Country name amd Neighbour country name should not be same")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + "Please enter valid country name or neighbour country name")
							.append(System.getProperty("line.separator"));
				}

			} else if (command.get(i).equalsIgnoreCase("-remove")) {
				cName = command.get(i + 1);
				ncName = command.get(i + 2);
				String message = "-remove " + cName + " " + ncName + " :=> ";

				if (validateInput(cName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")
						&& validateInput(ncName, "^([a-zA-Z]-+\\s)*[a-zA-Z-]+$")) {

					if (deleteCommonNeighbour(cName, ncName)) {
						result.append(message + " country and neighbour country's pair removed successfully")
								.append(System.getProperty("line.separator"));
					} else {
						result.append(message + " country or neighbour country's pair not found")
								.append(System.getProperty("line.separator"));
					}

				} else {
					result.append(message + "Please enter valid country name or neighbour country name")
							.append(System.getProperty("line.separator"));
				}
			}
		}
		return result.toString();
	}

	/**
	 * This is OnAction method of edit Map. When editmap got clicked or given
	 * command from GUI then particular method will be calld for next operation
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void EditMapOnAction(ActionEvent event) {

		try {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select map file");
			fileChooser.setInitialDirectory(new File("src/main/resources/maps/"));
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Please select .map File only", "*.map"));

			File selectedFile = fileChooser.showOpenDialog(null);
			String selectedFileName = selectedFile.getName();

			if (selectedFile != null) {

				System.out.println("File ==> " + selectedFileName);
				alertMesage(EditMap(selectedFileName));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will accept file name to edit and call next methods to edit the
	 * map and return result whether it's done or some error got occured.
	 * 
	 * @param fileName
	 * @return message of result then what happened after EditMap got fired or
	 *         clicked from GUI
	 */
	private String EditMap(String fileName) {
		String result = "";
		RiskMap riskMap = null;
		try {

			boolean isConquest = mapManagementImpl.isMapConquest(fileName);
			if(isConquest) {
				isConquestRead = true;
				ConquestMapInterface conquestMapInterface = new ConquestMapImpl();
				MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestMapInterface);
				riskMap = mapInterface.readMap(fileName);				
			}
			else{
			riskMap = mapManagementImpl.readMap(fileName);
			}

			boolean validMap = mapManagementImpl.validateMap(riskMap);

			Map<String, Object> mapDtos = mapManagementImpl.convertRiskMapToDtos(riskMap);

			List<ContinentDto> continentDtoList = (List<ContinentDto>) mapDtos.get("ContinentList");
			List<CountryDto> countryDtoList = (List<CountryDto>) mapDtos.get("CountryList");
			List<NeighbourTerritoriesDto> neighbourDtoList = (List<NeighbourTerritoriesDto>) mapDtos
					.get("NeighbourList");

			System.out.println(riskMap);
			System.out.println(continentDtoList);
			System.out.println(countryDtoList);
			System.out.println(neighbourDtoList);

			continentList.clear();
			countryList.clear();
			neighbourList.clear();

			continentList.addAll(continentDtoList);
			countryList.addAll(countryDtoList);
			neighbourList.addAll(neighbourDtoList);

			continentId = continentList.size() + 1;
			countryId = countryList.size() + 1;
			neighbourId = neighbourList.size() + 1;

			loadContinentDetails();
			loadCountryDetails();
			loadNeighbourDetails();

			String fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
			fileNameTextField.setText(fileNameWithoutExt);
			fileNameTextField.setDisable(true);

			if (validMap) {

				result = "Map loaded successfully ! Please save map after editing";

			} else {
				result = "MapValidationError : Invalid Map Please correct Map";
			}

		} catch (Exception e) {
			result = "Exception in EditMap : Invalid Map Please correct Map";
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * This is OnAction method of savemap button, when this button got clicked this
	 * method will be called.
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void saveMapOnAction(ActionEvent event) {

		String mapName = fileNameTextField.getText();
		String result = saveMapToFile(mapName);
		alertMesage(result);

	}

	/**
	 * This method will save edited map to file after savemap button got clicked
	 * 
	 * @param mapName
	 * @return message of result then what happened after saveMap got fired or
	 *         clicked from GUI
	 */
	private String saveMapToFile(String mapName) {
		String result = "";

		try {
			if (validateInput(mapName, "[a-zA-Z]+")) {

				List<String> mapNameList = mapManagementImpl.getAvailableMap();
				if (!mapNameList.contains(mapName + ".map") || fileNameTextField.isDisable()) {
					
					RiskMap riskMap = mapManagementImpl.convertToRiskMap(continentList, countryList, neighbourList);
					riskMap.setMapName(mapName);
					boolean validMap = mapManagementImpl.validateMap(riskMap);

					if (validMap) {
						
						Boolean bResult = false;
						
						if(isConquestRead)
						{
							ConquestMapInterface conquestMapInterface = new ConquestMapImpl();
							MapManagementInterface mapInterface = new DominationToConquestAdapter(conquestMapInterface);
							bResult = mapInterface.saveMapToFile(riskMap);
							
						}else {
							bResult = mapManagementImpl.saveMapToFile(riskMap);
						}
							
						if (bResult) {
							initMapView();
							result = "Map successfully saved to filesystem";

						} else {
							result = "SaveMapToFileError : Invalid Map Please correct Map";
						}
					} else {
						result = "MapValidationError : Invalid Map Please correct Map";
					}

				} else {
					result = "Map Name is already in System Please enter another one";
				}

			} else {
				result = "Please select or Enter valid map name";
			}

		} catch (Exception e) {
			result = "Exception in savemap : Invalid Map Please correct Map";
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * This method will validate the input given from GUI or console
	 * 
	 * @param value
	 *            is string for which you want to check validation
	 * @param pattern
	 *            regex for validation
	 * @return
	 */
	private boolean validateInput(String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * This method is OnAction method of resetMap button. When resetMap got clicked
	 * this method will be called and it will reset some feilds.
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void resetMap(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Conformation Dialog");
		alert.setHeaderText(null);
		alert.setContentText(
				"Are you sure you want to reset your Map? Once you reset your editable map will be removed");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {

			initMapView();

		} else {
			System.out.println("cancel pressed");
		}

	}

	/**
	 * This method will redirect user to welcome page
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void backToWelcome(ActionEvent event) {
		stageManager.switchScene(FxmlView.WELCOME, null);
	}

	/**
	 * This method will redirect user to play game screen
	 * 
	 * @param event
	 *            will represents value sent from view
	 */
	@FXML
	void playgame(ActionEvent event) {
		stageManager.switchScene(FxmlView.STARTUPPHASE, null);
	}

}
