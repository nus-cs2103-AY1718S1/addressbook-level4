package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FORMCLASS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FORMCLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENTPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENTPHONE_BOB;
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
            .withPhone("91119222").withParentPhone("96753511").withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com").withFormClass("12S23").withGrades("125.0").withPostalCode("279392")
            .withTags("basketball", "friends")
            .build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("97979797").withParentPhone("91113321").withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withFormClass("12S23").withGrades("125.0").withPostalCode("279392")
            .withTags("studentcouncil", "scholarship", "Track", "friends", "owesMoney")
            .build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("97978989").withParentPhone("96753031").withEmail("heinz@example.com").withAddress("wall street")
            .withFormClass("12S23").withGrades("165.0").withPostalCode("279392").withTags("dance")
            .build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("97972020").withParentPhone("97978181").withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withFormClass("12S23").withGrades("100.0").withPostalCode("279392")
            .withTags("studentcouncil", "scholarship", "Track")
            .build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("97971231").withParentPhone("96722718").withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withFormClass("12S23").withGrades("150.0").withPostalCode("279392").withTags("owesMoney")
            .build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("97202010").withParentPhone("96712345").withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withFormClass("12S23").withGrades("75.0").withPostalCode("279392").withTags("owesMoney")
            .build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("91122001").withParentPhone("96750000").withEmail("anna@example.com").withAddress("4th street")
            .withFormClass("12S23").withGrades("50.0").withPostalCode("279392").withRemark("Likes to swim")
            .withTags("owesMoney")
            .build();


    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("97972222").withParentPhone("96751010").withEmail("stefan@example.com")
            .withAddress("little india")
            .withFormClass("12S23").withGrades("125.0").withPostalCode("987527").withTags("owesMoney")
            .build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("97908012").withParentPhone("96751010").withEmail("hans@example.com").withAddress("chicago ave")
            .withFormClass("12S23").withGrades("125.0").withPostalCode("123456").withTags("owesMoney")
            .build();
    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withParentPhone(VALID_PARENTPHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withFormClass(VALID_FORMCLASS_AMY)
            .withGrades(VALID_GRADES_AMY).withPostalCode(VALID_POSTALCODE_AMY).withTags(VALID_TAG_FRIEND)
            .build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withParentPhone(VALID_PARENTPHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withFormClass(VALID_FORMCLASS_BOB)
            .withGrades(VALID_GRADES_BOB).withPostalCode(VALID_POSTALCODE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_TRACK = "Track"; // A keyword that matches Track

    private TypicalPersons() {
    } // prevents instantiation

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
