package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_LINK_DEFAULT;

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
            .withPhone("85355255")
            .withTags("friend")
            .withWebLinks("https://www.facebook.com/alice", "https://www.instagram.com/alice/")
            .build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friend")
            .withWebLinks("https://www.facebook.com/benson", "https://www.instagram.com/benson/")
            .build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withWebLinks("https://www.facebook.com/carl", "https://www.instagram.com/carl/")
            .build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withTags("owesMoney", "friend")
            .withWebLinks("https://www.facebook.com/daniel", "https://www.instagram.com/daniel/")
            .build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withTags("owesMoney", "friend")
            .withWebLinks("https://www.facebook.com/elle", "https://www.instagram.com/elle/")
            .build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withWebLinks("https://www.facebook.com/fiona", "https://www.instagram.com/fiona/")
            .build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("94824423")
            .withEmail("anna@example.com").withAddress("4th street").withRemark("Likes to swim")
            .withWebLinks("https://www.facebook.com/george", "https://www.instagram.com/george/")
            .build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();
    public static final ReadOnlyPerson JULIAN_NO_PHONE = new PersonBuilder().withName("Julian Kaiser")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("julian@example.com")
            .withTags("friend").build();
    public static final ReadOnlyPerson KENDRICK_NO_ADDRESS = new PersonBuilder().withName("Kendrick Lopez")
            .withEmail("kendrick@example.com").withPhone("86324716")
            .withTags("friend").build();
    public static final ReadOnlyPerson LEMAR_NO_EMAIL = new PersonBuilder().withName("Lemar Kendrick")
            .withEmail("kendrick@example.com").withPhone("86324789")
            .withTags("friend").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withWebLinks(VALID_WEB_LINK_DEFAULT).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withWebLinks(VALID_WEB_LINK_DEFAULT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    //@@author bladerail
    /**
     * Returns an {@code AddressBook} with all the typical persons in sorted order.
     */
    public static AddressBook getSortedTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getSortedTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons in unsorted order.
     */
    public static AddressBook getUnsortedTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getUnsortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, DANIEL, CARL, GEORGE, FIONA, ELLE));
    }

    public static List<ReadOnlyPerson> getSortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
    //@@author
}
