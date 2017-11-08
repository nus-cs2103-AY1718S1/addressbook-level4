package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CORNIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CORNIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOKADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOKADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CORNIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CORNIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNIQUETAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNIQUETAG2;

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
            .withPhone("85355255").withBirthday("06091971")
            .withFacebookAddress("https://www.facebook.com/alice/").withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withBirthday("02121983")
            .withFacebookAddress("https://www.facebook.com/benson/")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withBirthday("21041993")
            .withFacebookAddress("https://www.facebook.com/karl/").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withBirthday("19041999")
            .withFacebookAddress("https://www.facebook.com/daniel/").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withBirthday("23071997")
            .withFacebookAddress("https://www.facebook.com/el/").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withBirthday("02011990")
            .withFacebookAddress("https://www.facebook.com/fiona/").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withBirthday("29021992")
            .withFacebookAddress("https://www.facebook.com/george/").build();
    public static final ReadOnlyPerson BERNICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withBirthday("")
            .withFacebookAddress("").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withBirthday("09021994")
            .withFacebookAddress("https://www.facebook.com/hoon/").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withBirthday("19021990")
            .withFacebookAddress("https://www.facebook.com/ida/").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY).withTags(VALID_TAG_FRIEND)
            .withFacebookAddress(VALID_FACEBOOKADDRESS_AMY).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withFacebookAddress(VALID_FACEBOOKADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson CORNIE = new PersonBuilder().withName(VALID_NAME_CORNIE)
            .withPhone(VALID_PHONE_CORNIE).withEmail(VALID_EMAIL_CORNIE).withAddress(VALID_ADDRESS_CORNIE)
            .withTags(VALID_TAG_UNIQUETAG).build();
    public static final ReadOnlyPerson CORNIE_NEW_UNIQUE_TAG = new PersonBuilder().withName(VALID_NAME_CORNIE)
            .withPhone(VALID_PHONE_CORNIE).withEmail(VALID_EMAIL_CORNIE).withAddress(VALID_ADDRESS_CORNIE)
            .withTags(VALID_TAG_UNIQUETAG2).build();
    public static final ReadOnlyPerson CORNIE_NEW_NON_UNIQUE_TAG = new PersonBuilder().withName(VALID_NAME_CORNIE)
            .withPhone(VALID_PHONE_CORNIE).withEmail(VALID_EMAIL_CORNIE).withAddress(VALID_ADDRESS_CORNIE)
            .withTags("owesMoney").build();

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
