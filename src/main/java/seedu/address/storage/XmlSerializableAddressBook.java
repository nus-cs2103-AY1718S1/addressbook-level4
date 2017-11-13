package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {

    @XmlElement(name = "persons")
    private List<XmlAdaptedPerson> persons;
    @XmlElement(name = "tags")
    private List<XmlAdaptedTag> tags;
    //@@author OscarWang114
    @XmlElement(name = "lifeInsuranceMap")
    private Map<String, XmlAdaptedLifeInsurance> lifeInsuranceMap;
    //@@author

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        lifeInsuranceMap = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        lifeInsuranceMap = src.getLifeInsuranceMap().entrySet().stream()
            .collect(Collectors.<Map.Entry<UUID, ReadOnlyInsurance>, String, XmlAdaptedLifeInsurance>toMap(
                e -> e.getKey().toString(),
                e -> new XmlAdaptedLifeInsurance(e.getValue())
            ));
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

    //@@author OscarWang114
    @Override
    public Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap() {
        final Map<UUID, ReadOnlyInsurance> lifeInsurances = this.lifeInsuranceMap.entrySet().stream()
            .collect(Collectors.<Map.Entry<String, XmlAdaptedLifeInsurance>, UUID, ReadOnlyInsurance>toMap(
                i -> UUID.fromString(i.getKey()), i -> {
                    try {
                        return i.getValue().toModelType();
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            ));
        return lifeInsurances;
    }
    //@@author
}

