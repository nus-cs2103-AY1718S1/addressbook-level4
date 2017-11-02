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
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {
    private static final Logger logger = LogsCenter.getLogger(XmlSerializableAddressBook.class);

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedAliasToken> aliasTokens;
    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        aliasTokens = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        aliasTokens.addAll(src.getAliasTokenList().stream().map(XmlAdaptedAliasToken::new)
                .collect(Collectors.toList()));
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        final ObservableList<ReadOnlyPerson> persons = this.persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedPerson to Person or add it to PersonList: ");
                e.printStackTrace();
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
                logger.warning("Failed to convert XmlAdaptedTag to Tag or add it to TagList: ");
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tags);
    }

    //@@author deep4k
    @Override
    public ObservableList<ReadOnlyAliasToken> getAliasTokenList() {
        final ObservableList<ReadOnlyAliasToken> aliasTokens = this.aliasTokens.stream().map(a -> {
            try {
                return a.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedAliasToken to AliasToken or "
                        + "add it to AliasTokenList: ");
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(aliasTokens);
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<ReadOnlyTask> tasks = this.tasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedTask to Task or"
                        + "add it to TaskList: ");
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }
    //@@author 
}
