package com.riskgame.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;
import com.riskgame.dto.NeighbourTerritoriesDto;

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
import javafx.util.Callback;

/**
 * This class is the entry point of HTTP Client Library Implementation.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Controller
public class MapController implements Initializable {

	@FXML
	private TableColumn<ContinentDto, Integer> colContinentId;

	@FXML
	private ComboBox<String> continentComboBox;

	@FXML
	private TableColumn<ContinentDto, Integer> colContinentValue;

	@FXML
	private TableColumn<CountryDto, Boolean> colEditCountry;

	@FXML
	private MenuItem deleteCountry;

	@FXML
	private TableColumn<CountryDto, String> colCountryName;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, Integer> colNeighborId;

	@FXML
	private ComboBox<String> countryComboBox;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, String> colCountryNeighbor;

	@FXML
	private MenuItem deleteContinent;

	@FXML
	private TableView<NeighbourTerritoriesDto> neighborTable;

	@FXML
	private TableColumn<CountryDto, Integer> colCountryId;

	@FXML
	private Button btnNeighbor;

	@FXML
	private TableColumn<ContinentDto, String> colContinentName;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, Boolean> colEditNeighbor;

	@FXML
	private TableColumn<CountryDto, String> colCountryContinent;

	@FXML
	private TableColumn<ContinentDto, Boolean> colEditContinent;

	@FXML
	private Button btnCountry;

	@FXML
	private TextField continentValue;

	@FXML
	private TableView<ContinentDto> continentTable;

	@FXML
	private Button btnContinent;

	@FXML
	private TableView<CountryDto> countryTable;

	@FXML
	private TextField countryName;

	@FXML
	private MenuItem deleteNeighbor;

	@FXML
	private TextField continentName;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, String> colNeighborName;

	@FXML
	private ComboBox<String> neighborComboBox;

	@FXML
	private Label lblContinentId;

	@FXML
	private Label lblCountryId;

	@FXML
	private Label lblNeighbourId;

	@FXML
	private Button btnEditMap;

	@FXML
	private TextField commandLine;

	@FXML
	private TextArea consoleArea;

	@FXML
	private TextField fileNameTextField;

	@FXML
	private Button btnFireCommand;

	@FXML
	private Button btnSaveMap;

	private static int continentId = 1;
	private static int countryId = 1;
	private static int neighbourId = 1;

	private ObservableList<ContinentDto> continentList = FXCollections.observableArrayList();
	private ObservableList<CountryDto> countryList = FXCollections.observableArrayList();
	private ObservableList<NeighbourTerritoriesDto> neighbourList = FXCollections.observableArrayList();

	private ObservableList<String> continentComboValue = FXCollections.observableArrayList();

	private ObservableList<String> countryComboValue = FXCollections.observableArrayList();

	private ObservableList<String> neighbourComboValue = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		continentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		countryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		neighborTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		setContinentTableColumnProperties();
		setCountryTableColumnProperties();
		setNeighborTableColumnProperties();

		loadContinentDetails();
		loadCountryDetails();
		loadNeighbourDetails();
	}

	private void loadContinentDetails() {
		// TODO Auto-generated method stub

		// continentList.clear();
		continentTable.setItems(continentList);

	}

	private void loadCountryDetails() {

		List<String> continentInfo = continentList.stream().map(ContinentDto::getContinentName)
				.collect(Collectors.toList());
		continentComboValue.clear();
		continentComboValue.addAll(continentInfo);
		continentComboBox.setItems(continentComboValue);
		// countryList.clear();
		countryTable.setItems(countryList);

	}

	// private void loadContinentComboBox() {
	//
	//
	//
	// }

	private void loadNeighbourDetails() {

		List<String> countryInfo = countryList.stream().map(CountryDto::getCountryName).collect(Collectors.toList());

		countryComboValue.clear();
		countryComboValue.addAll(countryInfo);
		countryComboBox.setItems(countryComboValue);

		neighbourComboValue.clear();
		neighbourComboValue.addAll(countryInfo);
		neighborComboBox.setItems(neighbourComboValue);

		// neighbourList.clear();
		neighborTable.setItems(neighbourList);

	}

	private void setContinentTableColumnProperties() {
		// TODO Auto-generated method stub
		colContinentId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colContinentName.setCellValueFactory(new PropertyValueFactory<>("continentName"));
		colContinentValue.setCellValueFactory(new PropertyValueFactory<>("continentValue"));
		colEditContinent.setCellFactory(continentCellFactory);
	}

	private void setCountryTableColumnProperties() {
		// TODO Auto-generated method stub
		colCountryId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		colCountryContinent.setCellValueFactory(new PropertyValueFactory<>("continentName"));
		colEditCountry.setCellFactory(countryCellFactory);

	}

	private void setNeighborTableColumnProperties() {
		// TODO Auto-generated method stub
		colNeighborId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountryNeighbor.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		colNeighborName.setCellValueFactory(new PropertyValueFactory<>("countryNeighbourName"));
		colEditNeighbor.setCellFactory(neighbourCellFactory);

	}

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

	private boolean deleteCommonContinent(String continentName) {
		boolean result = false;

		for (int i = 0; i < continentList.size(); i++) {

			if (continentList.get(i).getContinentName().equalsIgnoreCase(continentName)) {
				continentList.remove(i);
				result = true;
				break;
			}

		}

		if (result) {
			// continentTable.setItems(continentList);
			// loadContinentComboBox();
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

	private boolean deleteCommonCountry(String countryName) {
		boolean result = false;

		for (int i = 0; i < countryList.size(); i++) {

			if (countryList.get(i).getCountryName().equalsIgnoreCase(countryName)) {
				countryList.remove(i);
				result = true;
				break;
			}

		}

		if (result) {

			// countryTable.setItems(countryList);
			loadCountryDetails();

			// for (int i = 0; i < neighbourList.size(); i++) {
			// NeighbourTerritoriesDto neighbourDto = neighbourList.get(i);
			// if
			// (neighbourDto.getCountryName().equalsIgnoreCase(countryDto.getCountryName()))
			// {
			// neighbourList.remove(i);
			// } else if
			// (neighbourDto.getCountryNeighbourName().equalsIgnoreCase(countryDto.getCountryName()))
			// {
			// neighbourList.remove(i);
			// }
			//
			// }

			neighbourList.removeIf(neighbour -> neighbour.getCountryName().equalsIgnoreCase(countryName));
			neighbourList.removeIf(neighbour -> neighbour.getCountryNeighbourName().equalsIgnoreCase(countryName));
			loadNeighbourDetails();
		}

		return result;

	}

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

	private boolean deleteCommonNeighbour(String countryName, String neighbourName) {
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
			// neighborTable.setItems(neighbourList);
			loadNeighbourDetails();
		}

		return result;
	}

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

	private void saveCommonContinent(String name, String value) {

		ContinentDto continentDto = new ContinentDto();
		continentDto.setId(continentId);
		continentId++;
		continentDto.setContinentName(name);
		continentDto.setContinentValue(Integer.parseInt(value));

		continentList.add(continentDto);

		// continentTable.setItems(continentList);
		// loadContinentComboBox();
		loadContinentDetails();
		loadCountryDetails();
	}

	private boolean isValidContinentName(String continentName, String continentId) {
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

	private boolean isValidCountryName(String countryName, String countryId) {
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

	private boolean isValidNeighbour(String countryName, String neighbourCountryName) {
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

	/*
	 * Validations
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

	private void alertMesage(String alertMessage) {

		Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("Saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(alertMessage);
		alert.showAndWait();
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (empty)
			alert.setContentText("Please Select or Enter " + field);
		else
			alert.setContentText("Please Select or Enter Valid " + field);

		alert.showAndWait();
	}

	private void clearContinentFields() {
		lblContinentId.setText(null);
		continentName.clear();
		continentValue.clear();
	}

	private void clearCountryFields() {
		lblCountryId.setText(null);
		countryName.clear();
		continentComboBox.getSelectionModel().clearSelection();
	}

	private void clearNeighbourFields() {
		lblNeighbourId.setText(null);
		countryComboBox.getSelectionModel().clearSelection();
		neighborComboBox.getSelectionModel().clearSelection();
	}

	@FXML
	void fireCommand(ActionEvent event) {
		consoleArea.clear();
		String message = "";
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

				} else if (command.startsWith("savemap")) {

				} else if (command.startsWith("editmap")) {

				} else if (command.startsWith("validatemap")) {

				} else {
					consoleArea.setText("Please enter valid command");
				}

			} else {
				consoleArea.setText("Please enter valid command");
			}
		} catch (Exception e) {
			e.printStackTrace();
			consoleArea.setText("Please enter valid command");
		}

	}

}
