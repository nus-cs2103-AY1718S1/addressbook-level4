package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
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

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
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

    /**
     * @return {@code ObservableList} of blacklisted persons.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getBlacklistedPersonList() {
        ObservableList<ReadOnlyPerson> persons = getPersonList();
        ObservableList<ReadOnlyPerson> blacklistedPersons = persons.stream()
                .filter(person -> person.isBlacklisted())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(blacklistedPersons);
    }

    /**
     * @return {@code ObservableList} of whitelisted persons.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getWhitelistedPersonList() {
        ObservableList<ReadOnlyPerson> persons = getPersonList();
        ObservableList<ReadOnlyPerson> whitelistedPersons = persons.stream()
                .filter(person -> person.isWhitelisted())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(whitelistedPersons);
    }

    /**
     * @return {@code ObservableList} of persons with overdue debt.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getOverduePersonList() {
        ObservableList<ReadOnlyPerson> persons = getPersonList();
        ObservableList<ReadOnlyPerson> overduePersons = persons.stream()
                .filter(person -> person.hasOverdueDebt())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(overduePersons);
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
}
