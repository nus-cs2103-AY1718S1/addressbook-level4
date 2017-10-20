package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withPhoto("default.jpeg").withTags("friends").withBirthdate("12/12/1995").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
            .withPhoto("default.jpeg").withTags("owesMoney", "friends").withBirthdate("12/11/1995").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withPhoto("default.jpeg").withEmail("heinz@example.com").withAddress("wall street").withBirthdate("12/10/1995").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withPhoto("default.jpeg").withEmail("cornelia@example.com").withAddress("10th street").withBirthdate("23/05/1993").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822242")
            .withPhoto("default.jpeg").withEmail("werner@example.com").withAddress("michegan ave").withBirthdate("12/02/1992").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94826427")
            .withPhoto("default.jpeg").withEmail("lydia@example.com").withAddress("little tokyo").withBirthdate("13/10/1995").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("94822442")
            .withPhoto("default.jpeg").withEmail("anna@example.com").withAddress("4th street").withBirthdate("10/12/1995").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84822424")
            .withEmail("stefan@example.com").withAddress("little india").withPhoto("default.jpeg").withBirthdate("12/12/1995").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84822131")
            .withPhoto("default.jpeg").withEmail("hans@example.com").withAddress("chicago ave").withBirthdate("12/12/1995").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto("default.jpeg")
            .withTags(VALID_TAG_FRIENDS).withBirthdate(VALID_BIRTHDATE_AMY).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPhoto("default.jpeg")
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIENDS).withBirthdate(VALID_BIRTHDATE_BOB).build();
    public static final ReadOnlyPerson WEN = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPhoto("default.jpeg")
            .withTags(VALID_TAG_HUSBAND).withBirthdate(VALID_BIRTHDATE_BOB).build();

    // Different situations of missing data.
    public static final ReadOnlyPerson MISSINGNAME = new PersonBuilder().withPhone("84822131")
            .withEmail("hans@example.com").withAddress("chicago ave").withPhoto("default.jpeg").build();
    public static final ReadOnlyPerson MISSINGPHONE = new PersonBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").withPhoto("default.jpeg").withAddress("chicago ave").build();
    public static final ReadOnlyPerson MISSINGADDRESS = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84822131").withEmail("hans@example.com").withPhoto("default.jpeg").build();

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
