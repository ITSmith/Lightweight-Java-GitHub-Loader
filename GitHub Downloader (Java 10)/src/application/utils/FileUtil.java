package application.utils;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileUtil {

	public static File chooseDirectory(Stage stage) {
		return chooseDirectory(stage, new File(System.getProperty("user.dir")));
	}

	public static File chooseDirectory(Stage stage, String defaultPath) {
		return chooseDirectory(stage, new File(defaultPath));
	}

	public static File chooseDirectory(Stage stage, File defaultDirectory) {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Choose Destination");
			chooser.setInitialDirectory(defaultDirectory);
			return chooser.showDialog(stage);
		} catch (IllegalArgumentException e) {
			// Invalid default path.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Directory");
			alert.setHeaderText("Invalid Starting Path");
			alert.setContentText("\"" + defaultDirectory.getPath() + "\" is not a valid path.");

			alert.showAndWait();
			return null;
		}
	}

	public static File openXML(Stage stage) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show open file dialog
		return fileChooser.showOpenDialog(stage);
	}

	public static File saveXML(Stage stage) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show save file dialog
		return fileChooser.showSaveDialog(stage);
	}
}