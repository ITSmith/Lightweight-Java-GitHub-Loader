package application.utils;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
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
		} catch(IllegalArgumentException e) {
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
}