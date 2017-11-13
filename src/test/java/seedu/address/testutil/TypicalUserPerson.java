package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserPerson;

//@@author bladerail
/**
 * A utility class containing a list of {@code UserPerson} objects to be used in tests.
 */
public class TypicalUserPerson {

    public static final EditCommand.EditPersonDescriptor DESC_JAMES;
    public static final EditCommand.EditPersonDescriptor DESC_WILLIAM;

    public static final ReadOnlyPerson JAMES = new PersonBuilder().withName("James Wong")
            .withAddress("456 Rochor Ave 3").withEmail("james@gmail.com")
            .withPhone("84712836")
            .withWebLinks("https://www.facebook.com/jameswong").build();

    public static final ReadOnlyPerson WILLIAM = new PersonBuilder().withName("William Sim")
            .withAddress("112 Clementi Ave 4").withEmail("william@hotmail.com")
            .withPhone("91332588")
            .withWebLinks("https://www.facebook.com/william").build();

    private TypicalUserPerson() {} // prevents instantiation

    public static UserPerson getTypicalUserPerson() {
        return new UserPerson(JAMES);
    }

    static {
        DESC_JAMES = new EditPersonDescriptorBuilder(JAMES).build();
        DESC_WILLIAM = new EditPersonDescriptorBuilder(WILLIAM).build();
    }
}
