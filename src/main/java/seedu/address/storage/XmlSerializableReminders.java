package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;

//@@author justinpoh
/**
 * A List of reminders that is serializable to XML format.
 */
@XmlRootElement(name = "reminders")
public class XmlSerializableReminders implements ReadOnlyUniqueReminderList {

    @XmlElement
    private List<XmlAdaptedReminder> reminders;

    /**
     * Creates an empty XmlSerializableReminders.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableReminders() {
        reminders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableReminders(List<ReadOnlyReminder> source) {
        this();
        reminders.addAll(source.stream().map(XmlAdaptedReminder::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyReminder> asObservableList() {

        final ObservableList<ReadOnlyReminder> reminders = this.reminders.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                throw new AssertionError("Data file is corrupted!");
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(reminders);
    }
}
