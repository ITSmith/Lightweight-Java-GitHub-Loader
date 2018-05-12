package application.gui;

import java.time.LocalDate;

import application.objects.Source;
import application.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SourceEditDialogController {
	@FXML
    private TextField srcIdentifierField;
    @FXML
    private TextField srcURLField;
    @FXML
    private TextArea srcNotesArea;

    private Stage dialogStage;
    private Source source;
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
     * Sets the source to be edited in the dialog.
     * 
     * @param source
     */
    public void setSource(Source source) {
        this.source = source;

        srcIdentifierField.setText(source.getIdentifier());
        srcURLField.setText(source.getURL());
        srcNotesArea.setText(source.getNotes());
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
            source.setIdentifier(srcIdentifierField.getText());
            source.setURL(srcURLField.getText());
            source.setNotes(srcNotesArea.getText());
            // Update old values
            source.setFile(StringUtil.getFileFromURL(source.getURL()));
            source.setDateAdded(LocalDate.now());

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

        if (srcIdentifierField.getText() == null || srcIdentifierField.getText().length() == 0) {
            errorMessage += "No valid Identifier!\n"; 
        }
        if (srcURLField.getText() == null || srcURLField.getText().length() == 0) {
            errorMessage += "No valid URL!\n";
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