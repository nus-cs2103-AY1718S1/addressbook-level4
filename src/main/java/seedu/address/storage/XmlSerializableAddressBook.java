package seedu.address.storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.timeslot.Date;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    //@@author reginleiff
    @XmlElement
    private List<XmlAdaptedEvent> events;
    //@@author
    //@@author huiyiiih
    @XmlElement
    private List<XmlAdaptedRelationship> relation;
    //@@author

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        //@@author reginleiff
        events = new ArrayList<>();
        //@@author huiyiiih
        relation = new ArrayList<>();
        //@@author
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        //@@author reginleiff
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
        //@@author
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        //@@author huiyiiih
        relation.addAll(src.getRelList().stream().map(XmlAdaptedRelationship::new).collect(Collectors.toList()));
        //@@author
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        final ObservableList<ReadOnlyPerson> persons = this.persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(persons);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tags);
    }

    //@@author reginleiff
    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(event -> {
            try {
                return event.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }

    @Override
    public ObservableList<ReadOnlyEvent> getTimetable(Date currentDate) {
        return null;
    }
    //@@author

    //@@author huiyiiih
    @Override
    public ObservableList<Relationship> getRelList() {
        final ObservableList<Relationship> rel = this.relation.stream().map(r -> {
            try {
                return r.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(rel);
    }
    //@@author

    @Override
    public ReadOnlyEvent getLastChangedEvent() {
        return null;
    }

    @Override
    public ReadOnlyEvent getNewlyAddedEvent() {
        return null;
    }

    /**
     *
     * Gets the current date and returns the local implementation of date.
     *
     * @return the current date
     */
    @Override
    public Date getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date currentDate = new java.util.Date();

        try {
            return new Date(dateFormat.format(currentDate));
        } catch (IllegalValueException e) {
            return null;
        }
    }
}
