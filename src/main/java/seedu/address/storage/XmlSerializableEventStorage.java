package seedu.address.storage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.ReadOnlyEventStorage;
import seedu.address.model.event.ReadOnlyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An Immutable EventStorage that is serializable to XML format
 */
@XmlRootElement(name = "eventstorage")
public class XmlSerializableEventStorage implements ReadOnlyEventStorage {

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableEventStorage.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventStorage() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventStorage(ReadOnlyEventStorage src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(ev -> {
            try {
                return ev.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }

}
