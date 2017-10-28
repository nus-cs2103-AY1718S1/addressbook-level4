package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CARRIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_CARRIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CARRIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_GER1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CARRIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CARRIE;

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
            .withAddress("123, Jurong West Ave 6, #08-111").withEmails("alice@example.com")
            .withPhones("85355255").withBirthday("25/09/1990")
            .withTags("friends", "colleagues").build();

    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmails("johnd@example.com").withPhones("98765432").withBirthday("12/02/1985")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhones("95352563")
            .withEmails("heinz@example.com").withBirthday("03/12/1973")
            .withAddress("wall street")
            .withTags("colleagues", "owesMoney").build();

    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhones("87652533").withBirthday("21/09/1988").withEmails("cornelia@example.com")
            .withAddress("10th street").build();

    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhones("9482224")
            .withBirthday("04/08/1991").withEmails("werner@example.com").withAddress("michegan ave")
            .build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhones("9482427")
            .withEmails("lydia@example.com").withAddress("little tokyo")
            .withBirthday("01/10/1989").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhones("9482442")
            .withBirthday("15/05/1993").withEmails("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhones("8482424")
            .withEmails("stefan@example.com").withAddress("little india")
            .withBirthday("03/03/1993").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhones("8482131")
            .withEmails("hans@example.com").withAddress("chicago ave")
            .withBirthday("01/01/1991").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhones(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
            .withEmails(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_MOD_CS2101).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhones(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
            .withEmails(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_MOD_GER1000, VALID_MOD_CS2101).build();
    public static final ReadOnlyPerson BOB_EDITED = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhones(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
            .withEmails(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_MOD_CS2103, VALID_MOD_CS2101).build();
    public static final ReadOnlyPerson CARRIE = new PersonBuilder().withName(VALID_NAME_CARRIE)
            .withPhones(VALID_PHONE_CARRIE).withBirthday(VALID_BIRTHDAY_CARRIE)
            .withEmails(VALID_EMAIL_CARRIE).withAddress(VALID_ADDRESS_CARRIE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final String KEYWORD_MATCHING_OWESMONEY = "owesMoney"; // A keyword that matches friends

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
