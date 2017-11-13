package seedu.address.model.person;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;

/**
 * A read-only immutable interface for a Person in ContactHub.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<PhoneList> phoneProperty();
    Set<Phone> getPhones();
    String getBrowserPhones();
    //@@author viviantan95
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
    //@@author
    String getBrowserEmails();
    ObjectProperty<EmailList> emailProperty();
    Set<Email> getEmails();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    String getBrowserModules();
    ObjectProperty<UniqueModuleList> moduleProperty();
    Set<Module> getModules();
    //@@author viviantan95
    ObjectProperty<Photo> photoProperty();
    Photo getPhoto();
    //@@author

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhones().equals(this.getPhones())
                //@@author viviantan95
                && other.getBirthday().equals(this.getBirthday())
                //@@author
                && other.getEmails().equals(this.getEmails())
                && other.getAddress().equals(this.getAddress()))
                //@@author viviantan95
                && other.getPhoto().equals(this.getPhoto());
        //@@author
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone Number(s): ")
                .append(getPhones().toString())
                //@@author viviantan95
                .append(" Birthday: ")
                .append(getBirthday())
                //@@author
                .append(" Email(s): ")
                .append(getEmails().toString())
                .append(" Address: ")
                .append(getAddress())
                //@@author viviantan95
                .append(" Photo URL: ")
                .append(getPhoto())
                //@@author
                .append(" Module(s): ");
        getModules().forEach(builder::append);
        return builder.toString();
    }

}
