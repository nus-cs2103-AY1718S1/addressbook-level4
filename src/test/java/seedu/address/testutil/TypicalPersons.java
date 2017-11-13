package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_NUM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_NUM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCH_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCH_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Website;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withSchEmail("alicepauline@u.nus.edu")
            .withWebsite("https://www.twitter.com/alice")
            .withBirthday("12/10/1998")
            .withPhone("85355255").withHomeNumber("65822291")
            .withFavourite("false")
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withHomeNumber("65432112")
            .withSchEmail("bensonmeier@u.nus.edu")
            .withWebsite("https://www.facebook.com/benson")
            .withBirthday("12/10/1998").withFavourite("false")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withHomeNumber("68912301").withEmail("heinz@example.com").withSchEmail("e0014559@u.nus.edu")
            .withWebsite("https://www.facebook.com/carl")
            .withBirthday("12/10/1998").withFavourite("false")
            .withAddress("wall street").withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withHomeNumber("63123112").withEmail("cornelia@example.com").withSchEmail("daniel.meier@u.nus.edu")
            .withWebsite("https://www.facebook.com/daniel")
            .withBirthday("12/10/1998").withFavourite("false")
            .withAddress("10th street").withTags("owesMoney", "colleagues").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withHomeNumber("65123411").withEmail("werner@example.com").withSchEmail("ellemeyer@u.ntu.edu")
            .withWebsite("https://www.facebook.com/elle")
            .withBirthday("12/10/1998").withFavourite("false")
            .withAddress("michegan ave").withTags("friends", "neighbours").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withHomeNumber("62134411").withEmail("lydia@example.com").withSchEmail("fionaaa@u.nus.edu")
            .withWebsite("https://www.facebook.com/fiona")
            .withBirthday("12/10/1998").withFavourite("false")
            .withAddress("little tokyo").withTags("neighbours", "owesMoney").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withHomeNumber("68901234").withEmail("anna@example.com").withSchEmail("bestgeorge@u.nus.edu")
            .withWebsite("https://www.facebook.com/george")
            .withBirthday("12/10/1998").withFavourite("false")
            .withAddress("4th street").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withHomeNumber("67512082").withEmail("stefan@example.com").withSchEmail("hoonguy@u.nus.edu")
            .withWebsite("https://www.facebook.com/hoon")
            .withAddress("little india").withBirthday("12/10/1998").withFavourite("false")
            .build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withHomeNumber("67521341").withEmail("hans@example.com").withSchEmail("idamueller@u.nus.edu")
            .withWebsite("https://www.facebook.com/ida")
            .withAddress("chicago ave").withBirthday("12/10/1998").withFavourite("false")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withHomeNumber(VALID_HOME_NUM_AMY).withEmail(VALID_EMAIL_AMY).withSchEmail(VALID_SCH_EMAIL_AMY)
            .withWebsite(VALID_WEBSITE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withFavourite("false")
            .withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
            .withWebsite(VALID_WEBSITE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB).withFavourite("false")
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // Person with no homeNumber
    public static final ReadOnlyPerson ERNEST = new PersonBuilder().withName("Ernest")
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withSchEmail(VALID_SCH_EMAIL_BOB).withWebsite(VALID_WEBSITE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB).withFavourite("false")
            .withTags(VALID_TAG_FRIEND).build();
    // Person with no birthday
    public static final ReadOnlyPerson EVELYN = new PersonBuilder().withName("Evelyn").withPhone(VALID_PHONE_BOB)
            .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
            .withSchEmail(VALID_SCH_EMAIL_BOB).withWebsite(VALID_WEBSITE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withFavourite("false")
            .withTags(VALID_TAG_FRIEND).build();
    // Person with no School Email
    public static final ReadOnlyPerson EDITH = new PersonBuilder().withName("Edith").withPhone(VALID_PHONE_BOB)
            .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
            .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withFavourite("false")
            .withTags(VALID_TAG_FRIEND).build();
    // Person with no Website
    public static final ReadOnlyPerson ERIC = new PersonBuilder().withName("Edith").withPhone(VALID_PHONE_BOB)
            .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
            .withWebsite(Website.WEBSITE_TEMPORARY)
            .withSchEmail(VALID_SCH_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withFavourite("false")
            .withTags(VALID_TAG_FRIEND).build();
    // Person with no Address
    public static final ReadOnlyPerson ETHAN = new PersonBuilder().withName("Edith").withPhone(VALID_PHONE_BOB)
            .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(Address.ADDRESS_TEMPORARY)
            .withSchEmail(VALID_SCH_EMAIL_BOB).withWebsite(VALID_WEBSITE_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withFavourite("false")
            .withTags(VALID_TAG_FRIEND).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String TAG_MATCHING_DANIEL = "colleagues"; // A tag that matches DANIEL

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
