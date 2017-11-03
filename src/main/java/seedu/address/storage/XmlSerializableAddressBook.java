package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.meeting.Meeting;
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
    @XmlElement
    private List<XmlAdaptedMeeting> meetings;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        meetings = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        meetings.addAll(src.getMeetingList().stream().map(XmlAdaptedMeeting::new).collect(Collectors.toList()));
    }

    //@@author newalter
    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return convertToModelType(this.persons);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return convertToModelType(this.tags);
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        return convertToModelType(this.meetings);
    }

    /**
     * Converts a list of XmlAdaptedType Objects into an ObservableList of ModelType Objects
     * @param xmlAdaptedObjectList the list of the XmlAdaptedType Objects e.g. this.tags
     * @param <ModelT> the Model Type e.g. Tag
     * @param <XmlAdaptedT> the XmlAdaptedType that implements XmlAdaptedClass e.g. XmlAdaptedTag
     * @return an ObservableList of ModelType Objects
     */
    private <ModelT, XmlAdaptedT extends XmlAdaptedClass<ModelT>> ObservableList<ModelT> convertToModelType(
            List<XmlAdaptedT> xmlAdaptedObjectList) {
        final List<ModelT> modelTypeList = new ArrayList<>();
        for (XmlAdaptedT element : xmlAdaptedObjectList) {
            try {
                modelTypeList.add((element.toModelType()));
            } catch (IllegalValueException e) {
                logger.warning("Illegal data found in storage.");
            }
        }
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(modelTypeList));
    }
    //@@author

}
