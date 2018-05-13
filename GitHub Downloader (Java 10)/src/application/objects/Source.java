package application.objects;

import java.time.LocalDate;

import application.utils.StringUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Source {

	private final StringProperty identifier;
	private final StringProperty file;
	private final StringProperty URL;
	private final StringProperty notes;
	private final ObjectProperty<LocalDate> dateAdded;
	private final ObjectProperty<LocalDate> lastDownloaded;

	public Source() {
		this(null);
	}

	public Source(String URL) {
		this.URL = new SimpleStringProperty(URL);
		this.file = new SimpleStringProperty(StringUtil.getFileFromURL(URL));
		this.dateAdded = new SimpleObjectProperty<LocalDate>(LocalDate.now());

		// Defaults
		identifier = new SimpleStringProperty(StringUtil.getFileFromURL(URL));
		lastDownloaded = new SimpleObjectProperty<LocalDate>();
		notes = new SimpleStringProperty("");
	}

	public String getIdentifier() {
		return identifier.get();
	}

	public void setIdentifier(String identifier) {
		this.identifier.set(identifier);
	}

	public StringProperty identifierProperty() {
		return identifier;
	}

	public String getFile() {
		return file.get();
	}

	public void setFile(String file) {
		this.file.set(file);
	}

	public StringProperty fileProperty() {
		return file;
	}

	public String getURL() {
		return URL.get();
	}

	public void setURL(String URL) {
		this.URL.set(URL);
	}

	public StringProperty URLProperty() {
		return URL;
	}

	public String getNotes() {
		return notes.get();
	}

	public void setNotes(String notes) {
		this.notes.set(notes);
	}

	public StringProperty notesProperty() {
		return notes;
	}

	public LocalDate getDateAdded() {
		return dateAdded.get();
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded.set(dateAdded);
	}

	public ObjectProperty<LocalDate> dateAddedProperty() {
		return dateAdded;
	}

	public LocalDate getLastDownloaded() {
		return lastDownloaded.get();
	}

	public void setLastDownloaded(LocalDate lastDownloaded) {
		this.lastDownloaded.set(lastDownloaded);
	}

	public ObjectProperty<LocalDate> lastDownloadedProperty() {
		return lastDownloaded;
	}
}