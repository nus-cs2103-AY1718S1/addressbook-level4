package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAV_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAV_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
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
            .withPhone("85355255").withBirthday("05 nov 89").withEmail("alice@example.com")
            .withAddress("123, Jurong West Ave 6, #08-111").withPostalCode("555920")
            .withFavourite("yes").withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432").withBirthday("08 jul 67").withEmail("johnd@example.com")
            .withAddress("311, Clementi Ave 2, #02-25").withPostalCode("120390")
            .withFavourite("no").withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withBirthday("22 03 98").withEmail("heinz@example.com").withAddress("wall street")
            .withPostalCode("320195").withFavourite("no").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withBirthday("31-03-1988").withEmail("cornelia@example.com").withAddress("10th street")
            .withPostalCode("800000").withFavourite("no").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withBirthday("07.07.84").withEmail("werner@example.com").withAddress("michegan ave")
            .withPostalCode("459568").withFavourite("no").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withBirthday("17/08/89").withEmail("lydia@example.com").withAddress("little tokyo")
            .withPostalCode("250350").withFavourite("no").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withBirthday("13.12.1982").withEmail("anna@example.com").withAddress("4th street")
            .withPostalCode("780125").withFavourite("no").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withBirthday("22 dec 95").withEmail("stefan@example.com").withAddress("little india")
            .withPostalCode("650988").withFavourite("no").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withBirthday("30 09 99").withEmail("hans@example.com").withAddress("chicago ave")
            .withPostalCode("300290").withFavourite("no").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withPostalCode(VALID_POSTALCODE_BOB).withFavourite(VALID_FAV_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
