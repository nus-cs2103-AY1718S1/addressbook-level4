package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCUPATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCUPATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;

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
            .withOccupation("Apple, CEO").withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withWebsite("https://twitter.com/AlicePauline").withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withOccupation("NUS, student").withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withWebsite("https://twitter.com/BensonMeier")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz")
            .withOccupation("SBS, Bus Driver").withPhone("95352563").withEmail("heinz@example.com")
            .withAddress("wall street").withWebsite("https://twitter.com/kurz").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withOccupation("Prudential, Manager").withPhone("87652533").withEmail("cornelia@example.com")
            .withAddress("10th street").withWebsite("https://twitter.com/DanielMeier").withTags("colleagues").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer")
            .withOccupation("NTU, Professor").withPhone("9482224").withTags("family").withEmail("werner@example.com")
            .withAddress("michegan ave").withWebsite("https://twitter.com/ElleMeyer").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withOccupation("Microsoft, Janitor").withPhone("9482427").withEmail("lydia@example.com")
            .withAddress("little tokyo").withWebsite("https://twitter.com/FionaKunz").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best")
            .withOccupation("NBA, Basketball player").withPhone("9482442").withEmail("anna@example.com")
            .withAddress("4th street").withWebsite("https://twitter.com/GeorgeBest").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier")
            .withOccupation("Apple, Manager").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withWebsite("https://twitter.com/HoonMeier")
            .build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller")
            .withOccupation("Samsung, Clerk").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withWebsite("https://twitter.com/IdaMueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withOccupation(VALID_OCCUPATION_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withWebsite(VALID_WEBSITE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withOccupation(VALID_OCCUPATION_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withWebsite(VALID_WEBSITE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_FRIENDS = "friends";
    public static final String KEYWORD_MATCHING_OWESMONEY = "owesMoney";
    public static final String KEYWORD_MATCHING_FAMILY = "family";
    public static final String KEYWORD_MATCHING_COLLEAGUES = "colleagues";

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
