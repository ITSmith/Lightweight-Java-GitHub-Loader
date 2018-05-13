package application;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import application.model.Source;
import application.gui.DestinationEditDialogController;
import application.gui.SourceEditDialogController;
import application.gui.TabsController;
import application.model.DataWrapper;
import application.model.Destination;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private ObservableList<Source> sourceData = FXCollections.observableArrayList();
	private ObservableList<Destination> destinationData = FXCollections.observableArrayList();

	public Main() {
		// Sample data
		sourceData.add(new Source("https://github.com/beetbox/beets/blob/master/beetsplug/__init__.py"));
		sourceData.add(new Source("https://raw.githubusercontent.com/beetbox/beets/master/beetsplug/__init__.py"));
		sourceData.add(new Source("https://github.com/beetbox/beets/blob/master/.gitignore"));
		sourceData.add(new Source("https://raw.githubusercontent.com/beetbox/beets/master/.gitignore"));
		sourceData.add(new Source("https://github.com/beetbox/beets/blob/master/README.rst"));
		sourceData.add(new Source("https://raw.githubusercontent.com/beetbox/beets/master/README.rst"));
		destinationData.add(new Destination("D:/Music/Andre Rieu/Christmas Classics"));
		destinationData.add(new Destination("D:/Music/Andre Rieu/Christmas Classics/"));
		destinationData.add(new Destination("D:/"));
		destinationData.add(new Destination("D:"));
	}

	/**
	 * Returns the source data as an observable list of Sources.
	 * 
	 * @return
	 */
	public ObservableList<Source> getSourceData() {
		return sourceData;
	}

	/**
	 * Returns the destination data as an observable list of Destinations.
	 * 
	 * @return
	 */
	public ObservableList<Destination> getDestinationData() {
		return destinationData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("LightWeight Java GitHub Loader");
		setUserAgentStylesheet(STYLESHEET_CASPIAN);

		this.primaryStage.getIcons().add(new Image("file:resources/icons/icon.png"));

		initRootLayout();

		showTabs();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/Root.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			// scene.getStylesheets().add(getClass().getResource("css/DarkTheme.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the tabs inside the root layout.
	 */
	public void showTabs() {
		try {
			// Load tabs.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/Tabs.fxml"));
			TabPane tabs = (TabPane) loader.load();

			// Set tabs into the center of root layout.
			rootLayout.setCenter(tabs);

			// Give the controller access to the main application.
			TabsController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a dialog to edit details for the specified source. If the user clicks
	 * OK, the changes are saved into the provided source object and true is
	 * returned.
	 * 
	 * @param source
	 *            the source object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showSourceEditDialog(Source source) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/SourceEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Source");
			dialogStage.getIcons().add(new Image("file:resources/icons/icon.png"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the source into the controller.
			SourceEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setSource(source);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to edit details for the specified destination. If the user
	 * clicks OK, the changes are saved into the provided source object and true is
	 * returned.
	 * 
	 * @param destination
	 *            the destination object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showDestinationEditDialog(Destination destination) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/DestinationEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Destination");
			dialogStage.getIcons().add(new Image("file:resources/icons/icon.png"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the destination into the controller.
			DestinationEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setDestination(destination);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Returns the data file preference, i.e. the file that was last opened. The
	 * preference is read from the OS specific registry. If no such preference can
	 * be found, null is returned.
	 * 
	 * @return
	 */
	public File getDataFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in the
	 * OS specific registry.
	 * 
	 * @param file
	 *            the file or null to remove the path
	 */
	public void setDataFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("LightWeight Java GitHub Loader - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("LightWeight Java GitHub Loader");
		}
	}

	/**
	 * Loads data from the specified file. The current data will be replaced.
	 * 
	 * @param file
	 */
	public void loadDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(DataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			DataWrapper wrapper = (DataWrapper) um.unmarshal(file);

			sourceData.clear();
			sourceData.addAll(wrapper.getSources());
			destinationData.clear();
			destinationData.addAll(wrapper.getDestinations());

			// Save the file path to the registry.
			setDataFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	/**
	 * Saves the current data to the specified file.
	 * 
	 * @param file
	 */
	public void saveDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(DataWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			DataWrapper wrapper = new DataWrapper();
			wrapper.setSources(sourceData);
			wrapper.setSources(sourceData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setDataFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
