package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.user.ReadOnlyUser;

//@@author quanle1994

/**
 * An Immutable Account that is serializable to XML format
 */
@XmlRootElement(name = "account")
public class XmlSerializableAccount implements ReadOnlyAccount {

    @XmlElement
    private List<XmlAdaptedUser> users;

    public XmlSerializableAccount() {
        users = new ArrayList<>();
    }

    public XmlSerializableAccount(ReadOnlyAccount src) {
        this();
        users.addAll(src.getUserList().stream().map(XmlAdaptedUser::new).collect(Collectors.toList()));
    }

    public List<XmlAdaptedUser> getUsers() {
        return users;
    }

    @Override
    public ObservableList<ReadOnlyUser> getUserList() {
        final ObservableList<ReadOnlyUser> users = this.users.stream().map(u ->
                u.toModelType()).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(users);
    }
}
