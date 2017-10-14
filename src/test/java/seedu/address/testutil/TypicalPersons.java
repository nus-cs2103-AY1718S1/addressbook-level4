package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.*;
import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Website;
import seedu.address.model.person.exceptions.DuplicatePersonException;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withBirthday("15/02/1992")
            .withPhone("85355255")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .withTags("friends")
            .build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withBirthday("15/02/1993")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .withTags("owesMoney", "friends")
            .build();
    public static final ReadOnlyPerson CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withBirthday("15/02/1994")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withBirthday("15/02/1995")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withBirthday("15/02/1996")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();
    public static final ReadOnlyPerson IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withBirthday("15/02/2000")
            .withWebsite(Website.WEBSITE_EXAMPLE)
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY)
            .withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();
    public static final ReadOnlyPerson BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB)
            .withWebsite(VALID_WEBSITE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
