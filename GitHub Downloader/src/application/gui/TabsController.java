package application.gui;

import application.Main;
import application.objects.Destination;
import application.objects.Source;
import application.utils.DateUtil;
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
	
	// Source tab IDs
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
	
	// Destination tab IDs
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
	
	/**
	 * Called when the user clicks the new destination button. Opens a dialog to edit
	 * details for a new destination.
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
	 * Called when the user clicks the edit destination button. Opens a dialog to edit
	 * details for the selected destination.
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
}