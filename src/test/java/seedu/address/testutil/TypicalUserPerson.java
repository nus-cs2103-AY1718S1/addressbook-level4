package seedu.address.testutil;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalUserPerson {

    public static final ReadOnlyPerson BOB = new PersonBuilder().withName("Bob the Builder")
            .withAddress("456 Rochor Ave 3").withEmail("bob@builder.com")
            .withPhone("84712836")
            .withTags("").build();

    private TypicalUserPerson() {} // prevents instantiation

    public static ReadOnlyPerson getTypicalUserPerson() {
        return BOB;
    }

}
