package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWITTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWITTER_BOB;

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

    public static final String TEST_IMG = TestUtil.getTestImgPath();

    public static final ReadOnlyPerson ZEPHYR = new PersonBuilder().withName("Zephyr Zelda")
            .withAddress("Pallet Town").withEmail("Zephyr@zelda.com").withPhone("92840142")
            .withBirthday("02/03/1990").withDisplayPic(TEST_IMG).withTags("Fictional").build();

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withBirthday("01/03/1990").withDisplayPic("/images/defaultperson.png")
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withBirthday("03/07/1886")
            .withTwitter("meier").withInstagram("meier")
            .withDisplayPic("/images/defaultperson.png").withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("").withAddress("wall street").withBirthday("05/05/1997")
            .withTwitter("kurz").withInstagram("kurz")
            .withDisplayPic("/images/defaultperson.png").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withBirthday("03/03/1993")
            .withTwitter("meier_dan").withInstagram("meier_dan")
            .withDisplayPic("/images/defaultperson.png").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withBirthday("06/12/1993")
            .withTwitter("meyer_elle").withInstagram("meyer_elle")
            .withDisplayPic("/images/defaultperson.png").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withBirthday("04/11/1955")
            .withTwitter("kunz").withInstagram("kunz")
            .withDisplayPic("/images/defaultperson.png").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withBirthday("08/09/1969")
            .withTwitter("iamthebest").withInstagram("iamthebest")
            .withDisplayPic("/images/defaultperson.png").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withBirthday("27/10/1899")
            .withTwitter("meier_hoon").withInstagram("meier_hoon")
            .withDisplayPic("/images/defaultperson.png").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withBirthday("16/12/1990")
            .withTwitter("ida_mueller").withInstagram("ida_mueller")
            .withDisplayPic("/images/defaultperson.png").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY)
            .withTwitter(VALID_TWITTER_AMY).withInstagram(VALID_INSTAGRAM_AMY)
            .withDisplayPic(VALID_DISPLAYPIC).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
            .withTwitter(VALID_TWITTER_BOB).withInstagram(VALID_INSTAGRAM_BOB)
            .withDisplayPic(VALID_DISPLAYPIC).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
