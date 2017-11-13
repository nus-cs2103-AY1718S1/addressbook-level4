package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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

    public static final String VALID_TAG_ONE = "one";
    public static final String VALID_TAG_TWO = "two";
    public static final String INVALID_TAG = "$%5";
    public static final String VALID_TAG_FRIENDS = "friends";
    public static final String VALID_TAG_NEIGHBOURS = "neighbours";
    public static final String VALID_TAG_OWESMONEY = "owesMoney";
    public static final String VALID_TAG_COLLEAGUES = "colleagues";
    public static final String VALID_TAG_NICEPERSON = "nicePerson";
    public static final String VALID_TAG_BOSS = "boss";
    public static final String VALID_TAG_CLASSMATES = "classmates";

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withAvatar("default.png")
            .withTags(VALID_TAG_FRIENDS, VALID_TAG_NEIGHBOURS).build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withAvatar("default.png")
            .withNokName("Aleena Meier").withNokPhone("87654321")
            .withTags(VALID_TAG_OWESMONEY, VALID_TAG_FRIENDS, VALID_TAG_CLASSMATES).build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withAvatar("default.png")
            .withNokName("Carter Kurz").withNokPhone("81526415")
            .withTags(VALID_TAG_COLLEAGUES, VALID_TAG_NICEPERSON).build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withAvatar("default.png")
            .withNokName("Ethan Meier").withNokPhone("91564164")
            .withTags(VALID_TAG_BOSS, VALID_TAG_NICEPERSON).build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withAvatar("default.png")
            .withNokName("Adeleine Meyer").withNokPhone("84159455").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withAvatar("default.png")
            .withNokName("Cecilia Kunz").withNokPhone("91654865").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withAvatar("default.png")
            .withNokName("Gregory Best").withNokPhone("94518515")
            .withTags(VALID_TAG_FRIENDS, VALID_TAG_CLASSMATES, VALID_TAG_NEIGHBOURS).build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withAvatar("default.png")
            .withNokName("Hans Meier").withNokPhone("84185416").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withAvatar("default.png")
            .withNokName("Lena Mueller").withNokPhone("94152165").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withAvatar(VALID_AVATAR_AMY)
            .withNokName(VALID_NOK_NAME_AMY).withNokPhone(VALID_NOK_PHONE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withAvatar(VALID_AVATAR_BOB)
            .withNokName(VALID_NOK_NAME_BOB).withNokPhone(VALID_NOK_PHONE_BOB)
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
