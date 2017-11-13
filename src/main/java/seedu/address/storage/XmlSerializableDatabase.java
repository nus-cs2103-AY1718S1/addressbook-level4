//@@author cqhchan
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyDatabase;
import seedu.address.model.account.ReadOnlyAccount;


/**
 * An Immutable Database that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableDatabase implements ReadOnlyDatabase {

    @XmlElement
    private List<XmlAdaptedAccount> accounts;
    @XmlElement
    private List<XmlAdaptedReminder> reminders;
    @XmlElement
    private List<XmlAdaptedTag> tags;


    /**
     * Creates an empty XmlSerializableDatabase.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDatabase() {
        accounts = new ArrayList<>();
        reminders = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDatabase(ReadOnlyDatabase src) {
        this();
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyAccount> getAccountList() {
        final ObservableList<ReadOnlyAccount> accounts = this.accounts.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(accounts);
    }



}
