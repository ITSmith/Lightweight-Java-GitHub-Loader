package application.gui;

import java.io.File;

import application.Main;
import application.model.Destination;
import application.model.Source;
import application.utils.DateUtil;
import application.utils.FileUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TabsController {

	// Download tab IDs
	@FXML
	private TextField sourceField;
	@FXML
	private TextField destinationField;

	// Source Tab IDs
	@FXML
	private TableView<Source> sourceTable;
	@FXML
	private TableColumn<Source, String> sourceColumn;
	@FXML
	private Label srcIdentifierLbl;
	@FXML
	private Label srcFileLbl;
	@FXML
	private Label srcURLLbl;
	@FXML
	private Label srcDateAddedLbl;
	@FXML
	private Label srcLastDownloadedLbl;
	@FXML
	private TextArea srcNotesArea;

	// Destination Tab IDs
	@FXML
	private TableView<Destination> destinationTable;
	@FXML
	private TableColumn<Destination, String> destinationColumn;
	@FXML
	private Label destIdentifierLbl;
	@FXML
	private Label destPathLbl;
	@FXML
	private Label destDateAddedLbl;
	@FXML
	private TextArea destNotesArea;

	
	
	// Reference to the main application.
	private Main main;

	/**
	 * The constructor is called before the initialize() method.
	 */
	public TabsController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the tables with their columns.
		sourceColumn.setCellValueFactory(cellData -> cellData.getValue().identifierProperty());
		destinationColumn.setCellValueFactory(cellData -> cellData.getValue().identifierProperty());

		// Clear source details.
		showSourceDetails(null);
		// Clear destination details.
		showDestinationDetails(null);

		// Listen for selection changes and show the source details when changed.
		sourceTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showSourceDetails(newValue));
		// Listen for selection changes and show the destination details when changed.
		destinationTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDestinationDetails(newValue));
	}

	/**
	 * Called by the main application to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMainApp(Main main) {
		this.main = main;

		// Add observable list data to the tables
		sourceTable.setItems(main.getSourceData());
		destinationTable.setItems(main.getDestinationData());
	}

	// === Download Tab ===

	/**
	 * Called when the user clicks the save source button. Opens a dialog to edit
	 * details for a new source.
	 */
	@FXML
	private void handleSaveSource() {
		Source tempSource = new Source(sourceField.getText());
		boolean okClicked = main.showSourceEditDialog(tempSource);
		if (okClicked) {
			main.getSourceData().add(tempSource);
		}
	}

	/**
	 * Called when the user clicks the save destination button. Opens a dialog to
	 * edit details for a new destination.
	 */
	@FXML
	private void handleSaveDestination() {
		Destination tempDestination = new Destination(destinationField.getText());
		boolean okClicked = main.showDestinationEditDialog(tempDestination);
		if (okClicked) {
			main.getDestinationData().add(tempDestination);
		}
	}

	@FXML
	private void handleChooseDestination() {
		String path = destinationField.getText();
		File tempFile;
		if (path == null || path.length() == 0) {
			tempFile = FileUtil.chooseDirectory(main.getPrimaryStage());
		} else {
			tempFile = FileUtil.chooseDirectory(main.getPrimaryStage(), path);
		}
		if (tempFile != null)
			path = tempFile.getPath();
		destinationField.setText(path);
	}

	// === Source Tab ===

	private void showSourceDetails(Source source) {
		if (source != null) {
			// Fill the labels with info from the source object.
			srcIdentifierLbl.setText(source.getIdentifier());
			srcFileLbl.setText(source.getFile());
			srcURLLbl.setText(source.getURL());
			srcNotesArea.setText(source.getNotes());

			srcDateAddedLbl.setText(DateUtil.dateToString(source.getDateAdded()));
			srcLastDownloadedLbl.setText(DateUtil.dateToString(source.getLastDownloaded()));
		} else {
			// Source is null, remove all the text.
			srcIdentifierLbl.setText("");
			srcFileLbl.setText("");
			srcURLLbl.setText("");
			srcDateAddedLbl.setText("");
			srcLastDownloadedLbl.setText("");
			srcNotesArea.setText("");
		}
	}

	/**
	 * Called when the user clicks on the source load button.
	 */
	@FXML
	private void handleLoadSource() {
		int selectedIndex = sourceTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Source selectedSource = sourceTable.getSelectionModel().getSelectedItem();
			sourceField.setText(selectedSource.getURL());
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Source Selected");
			alert.setContentText("Please select a source in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the source delete button.
	 */
	@FXML
	private void handleDeleteSource() {
		int selectedIndex = sourceTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			sourceTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Source Selected");
			alert.setContentText("Please select a source in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new source button. Opens a dialog to edit
	 * details for a new source.
	 */
	@FXML
	private void handleNewSource() {
		Source tempSource = new Source();
		boolean okClicked = main.showSourceEditDialog(tempSource);
		if (okClicked) {
			main.getSourceData().add(tempSource);
		}
	}

	/**
	 * Called when the user clicks the edit source button. Opens a dialog to edit
	 * details for the selected source.
	 */
	@FXML
	private void handleEditSource() {
		Source selectedSource = sourceTable.getSelectionModel().getSelectedItem();
		if (selectedSource != null) {
			boolean okClicked = main.showSourceEditDialog(selectedSource);
			if (okClicked) {
				showSourceDetails(selectedSource);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Source Selected");
			alert.setContentText("Please select a source in the table.");

			alert.showAndWait();
		}
	}

	// === Destination Tab ===

	private void showDestinationDetails(Destination destination) {
		if (destination != null) {
			// Fill the labels with info from the destination object.
			destIdentifierLbl.setText(destination.getIdentifier());
			destPathLbl.setText(destination.getPath());
			destNotesArea.setText(destination.getNotes());

			destDateAddedLbl.setText(DateUtil.dateToString(destination.getDateAdded()));
		} else {
			// Destination is null, remove all the text.
			destIdentifierLbl.setText("");
			destPathLbl.setText("");
			destDateAddedLbl.setText("");
			destNotesArea.setText("");
		}
	}

	/**
	 * Called when the user clicks on the destination load button.
	 */
	@FXML
	private void handleLoadDestination() {
		int selectedIndex = destinationTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Destination selectedDestination = destinationTable.getSelectionModel().getSelectedItem();
			destinationField.setText(selectedDestination.getPath());
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Destination Selected");
			alert.setContentText("Please select a destination in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the destination delete button.
	 */
	@FXML
	private void handleDeleteDestination() {
		int selectedIndex = destinationTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			destinationTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Destination Selected");
			alert.setContentText("Please select a destination in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new destination button. Opens a dialog to
	 * edit details for a new destination.
	 */
	@FXML
	private void handleNewDestination() {
		Destination tempDestination = new Destination();
		boolean okClicked = main.showDestinationEditDialog(tempDestination);
		if (okClicked) {
			main.getDestinationData().add(tempDestination);
		}
	}

	/**
	 * Called when the user clicks the edit destination button. Opens a dialog to
	 * edit details for the selected destination.
	 */
	@FXML
	private void handleEditDestination() {
		Destination selectedDestination = destinationTable.getSelectionModel().getSelectedItem();
		if (selectedDestination != null) {
			boolean okClicked = main.showDestinationEditDialog(selectedDestination);
			if (okClicked) {
				showDestinationDetails(selectedDestination);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Destination Selected");
			alert.setContentText("Please select a destination in the table.");

			alert.showAndWait();
		}
	}

	// === Settings Tab ===

	/**
	 * Deletes all Data.
	 */
	@FXML
	private void handleNewData() {
		main.getSourceData().clear();
		main.getDestinationData().clear();
		main.setDataFilePath(null);
	}

	/**
	 * Opens a FileChooser to let the user select data to load.
	 */
	@FXML
	private void handleOpenData() {
		File file = FileUtil.openXML(main.getPrimaryStage());

		if (file != null) {
			main.loadDataFromFile(file);
		}
	}

	/**
	 * Saves the data to the file that is currently open. If there is no open file,
	 * the "save as" dialog is shown.
	 */
	@FXML
	private void handleSaveData() {
		File dataFile = main.getDataFilePath();
		if (dataFile != null) {
			main.saveDataToFile(dataFile);
		} else {
			handleSaveDataAs();
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveDataAs() {
		File file = FileUtil.saveXML(main.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			main.saveDataToFile(file);
		}
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("LightWeight Java GitHub Loader");
		alert.setHeaderText("About");
		alert.setContentText(
				"Author: Ian Smith\nGitHub Repository: https://github.com/ITSmith/Lightweight-Java-GitHub-Loader");

		alert.showAndWait();
	}
}