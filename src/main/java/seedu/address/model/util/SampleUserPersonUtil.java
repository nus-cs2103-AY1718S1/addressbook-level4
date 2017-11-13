package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;

//@@author bladerail
/**
 * Contains utility methods for creating sample UserPerson data.
 */
public class SampleUserPersonUtil {
    public static ReadOnlyPerson getDefaultSamplePerson() {
        try {
            ArrayList<Email> emails = new ArrayList<Email>();
            emails.add(new Email("default@default.com"));
            HashSet<WebLink> webLinks = new HashSet<>();
            webLinks.add(new WebLink("https://www.facebook.com/default"));
            return new Person (new Name("Default"), new Phone("00000000"), emails,
                            new Address("Default"), new Remark(""), new HashSet<Tag>(), webLinks);
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
}
