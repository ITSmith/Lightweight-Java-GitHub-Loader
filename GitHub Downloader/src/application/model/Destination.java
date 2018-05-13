package application.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import application.utils.LocalDateAdapter;
import application.utils.StringUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Destination {

	private final StringProperty identifier;
	private final StringProperty path;
	private final StringProperty notes;
	private final ObjectProperty<LocalDate> dateAdded;

	public Destination() {
		this(null);
	}

	public Destination(String path) {
		this.path = new SimpleStringProperty(path);
		this.dateAdded = new SimpleObjectProperty<LocalDate>(LocalDate.now());

		// Default
		identifier = new SimpleStringProperty(StringUtil.getFolderFromPath(path));
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

	public String getPath() {
		return path.get();
	}

	public void setPath(String path) {
		this.path.set(path);
	}

	public StringProperty pathProperty() {
		return path;
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

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDateAdded() {
		return dateAdded.get();
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded.set(dateAdded);
	}

	public ObjectProperty<LocalDate> dateAddedProperty() {
		return dateAdded;
	}
}