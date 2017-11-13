package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANISATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANISATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.RecycleBin;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.AddressBookData;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withBirthday("01-01-1991").withOrganisation("Microsoft Corporation")
            .withRemark("Meet for dinner 10/11 6pm Nex").withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withBirthday("02-02-1992")
            .withOrganisation("Hello Kitty Ltd.").withRemark("Owe me $20").withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withBirthday("03-03-1993").withEmail("heinz@example.com").withAddress("wall street")
            .withOrganisation("JP Morgan").withRemark("Owe $10").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withBirthday("04-04-1994").withEmail("cornelia@example.com").withAddress("10th street")
            .withOrganisation("SPH Singapore").withRemark("Need to discuss project").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withBirthday("05-05-1995").withEmail("werner@example.com").withAddress("michegan ave")
            .withOrganisation("DBS Ltd").withRemark("Need to discuss terms of contract").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withBirthday("06-06-1996").withEmail("lydia@example.com").withAddress("little tokyo")
            .withOrganisation("Pixar Animations").withRemark("Cycling at punggol?").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withBirthday("07-07-1997").withEmail("anna@example.com").withAddress("4th street")
            .withOrganisation("Pan Pacific Ltd.").withRemark("Return borrowed pen").build();
    public static final ReadOnlyPerson JEAN = new PersonBuilder().withName("Jean Meier").withPhone("8481313")
            .withBirthday("10-10-2000").withEmail("jumpy@example.com").withAddress("little japan")
            .withOrganisation("Unilever").withRemark("Movie at 6pm").build();
    public static final ReadOnlyPerson KEN = new PersonBuilder().withName("Ken Mueller").withPhone("8483737")
            .withBirthday("11-11-2001").withEmail("kitkat@example.com").withAddress("beansprout ave")
            .withOrganisation("P&G").withRemark("Need to plan holiday").build();
    public static final ReadOnlyPerson LINDA = new PersonBuilder().withName("Linda Meyer").withPhone("8443377")
            .withBirthday("12-12-2002").withEmail("lineup@example.com").withAddress("carrot road")
            .withOrganisation("ASDF").withRemark("Nothing to put here").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withBirthday("08-08-1998").withEmail("stefan@example.com").withAddress("little india")
            .withOrganisation("Unilever").withRemark("Meet for dinner?").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withBirthday("09-09-1999").withEmail("hans@example.com").withAddress("chicago ave")
            .withOrganisation("P&G").withRemark("Owe $5").build();
    public static final ReadOnlyPerson PERSON_WITH_MISSING_PHONE = new PersonBuilder().withName("Jake")
            .withBirthday("07-01-1995").withEmail("personmissingphone@example.com").withAddress("Chinatown")
            .withOrganisation("Ernst & Young").withRemark("Owe me $5").build();
    public static final ReadOnlyPerson PERSON_WITH_MISSING_BIRTHDAY = new PersonBuilder().withName("Jim")
            .withPhone("98591122").withEmail("person1@example.com").withAddress("Jurong Island")
            .withOrganisation("KMPG Singapore").withRemark("Meet for movie tmr").build();
    public static final ReadOnlyPerson PERSON_WITH_MISSING_EMAIL = new PersonBuilder().withName("Tommy")
            .withPhone("84291010").withBirthday("14-01-1995").withAddress("Kent Ridge Hill")
            .withOrganisation("PWC").withRemark("Lent shirt to him").build();
    public static final ReadOnlyPerson PERSON_WITH_MISSING_ADDRESS = new PersonBuilder().withName("Joel")
            .withPhone("81010101").withBirthday("25-01-1995").withEmail("person1@example.com")
            .withOrganisation("Deloitte").withRemark("Need to discuss work").build();
    public static final ReadOnlyPerson PERSON_WITH_MISSING_ORGANISATION = new PersonBuilder().withName("Andy")
            .withPhone("909912255").withEmail("personmissingorganisation@p.com").withAddress("Flower Road")
            .withRemark("Borrowed shirt from him").build();
    public static final ReadOnlyPerson PERSON_WITH_MISSING_REMARK = new PersonBuilder().withName("Benjamin")
            .withPhone("93153009").withEmail("personmissingremark@p.com").withAddress("Bishan Street 22").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withOrganisation(VALID_ORGANISATION_BOB).withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final String KEYWORD_MATCHING_MUELLER = "Mueller"; // A keyword that matches MUELLER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with four of the typical persons.
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

    //@@author frozventus
    /**
     * Returns a {@code RecycleBin} with three of the typical persons.
     */
    public static RecycleBin getTypicalRecycleBin() {
        RecycleBin rb = new RecycleBin();
        for (ReadOnlyPerson person : getTypicalPersonsForBin()) {
            try {
                rb.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return rb;
    }

    public static List<ReadOnlyPerson> getTypicalPersonsForBin() {
        return new ArrayList<>(Arrays.asList(JEAN, KEN, LINDA));
    }

    public static AddressBookData getTypicalData() {
        return new AddressBookData(getTypicalAddressBook(), getTypicalRecycleBin());
    }

}
