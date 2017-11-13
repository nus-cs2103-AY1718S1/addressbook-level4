package seedu.room.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.ReadOnlyEventBook;
import seedu.room.model.event.ReadOnlyEvent;

//@@author sushinoya
/**
 * An Immutable EventBook that is serializable to XML format
 */
@XmlRootElement(name = "eventbook")
public class XmlSerializableEventBook implements ReadOnlyEventBook {

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableEventBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventBook() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventBook(ReadOnlyEventBook src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }
}
