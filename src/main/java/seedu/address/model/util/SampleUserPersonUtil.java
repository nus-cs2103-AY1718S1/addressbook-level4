package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

public class SampleUserPersonUtil {
    public static Person getSamplePerson() {
        try {
            return new Person (new Name("Josh Yeh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                            new Address("Blk 312 Geylang Street 29, #06-40"), new Remark(""),
                            SampleDataUtil.getTagSet("friends"));
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
}
