package application.gui;

import java.time.LocalDate;

import application.objects.Destination;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DestinationEditDialogController {
	@FXML
    private TextField destIdentifierField;
    @FXML
    private TextField destPathField;
    @FXML
    private TextArea destNotesArea;

    private Stage dialogStage;
    private Destination destination;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the destination to be edited in the dialog.
     * 
     * @param destination
     */
    public void setDestination(Destination destination) {
        this.destination = destination;

        destIdentifierField.setText(destination.getIdentifier());
        destPathField.setText(destination.getPath());
        destNotesArea.setText(destination.getNotes());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	destination.setIdentifier(destIdentifierField.getText());
        	destination.setPath(destPathField.getText());
        	destination.setNotes(destNotesArea.getText());
            // Update old values
        	destination.setDateAdded(LocalDate.now());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (destIdentifierField.getText() == null || destIdentifierField.getText().length() == 0) {
            errorMessage += "No valid Identifier!\n"; 
        }
        if (destPathField.getText() == null || destPathField.getText().length() == 0) {
            errorMessage += "No valid Path!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}