package seedu.address.testutil;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserPerson;

//@@author bladerail
/**
 * A utility class containing a list of {@code UserPerson} objects to be used in tests.
 */
public class TypicalUserPerson {

    public static final ReadOnlyPerson BOB = new PersonBuilder().withName("Bob the Builder")
            .withAddress("456 Rochor Ave 3").withEmail("bob@builder.com")
            .withPhone("84712836")
            .withWebLinks("Bob@facebook.com").build();

    private TypicalUserPerson() {} // prevents instantiation

    public static UserPerson getTypicalUserPerson() {
        return new UserPerson(BOB);
    }

}
