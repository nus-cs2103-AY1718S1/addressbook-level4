package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withTags("friends", "enemy")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withTags(VALID_TAG_FRIEND, "owesMoney")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withRemark("Likes to swim").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    //@@author Xenonym
    // These persons have relationships for the purpose of testing storage mechanisms.
    // they are separate so as not to affect other tests that assume persons are independent eg. system tests
    public static final ReadOnlyPerson JAMES = new PersonBuilder().withName("James King").withPhone("65637492")
            .withEmail("jamesk@example.com").withAddress("123 Griffin Rd").build();
    public static final ReadOnlyPerson KELVIN = new PersonBuilder().withName("Kelvin Klein").withPhone("98762456")
            .withEmail("kklein@example.com").withAddress("23 Mobius Strip").build();
    public static final ReadOnlyPerson LISA = new PersonBuilder().withName("Lisa McAvoy").withPhone("65243679")
            .withEmail("lisamc@example.com").withAddress("43 Trance Ave").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    /**
     * Static initializer is for adding relationships to persons after they are all instantiated.
     */
    static {
        try {
            Relationship undirectedFrom = new Relationship(JAMES, KELVIN, RelationshipDirection.UNDIRECTED,
                    new Name("undirected"), new ConfidenceEstimate(29));
            Relationship undirectedTo = new Relationship(KELVIN, JAMES, RelationshipDirection.UNDIRECTED,
                    new Name("undirected"), new ConfidenceEstimate(29));
            Relationship directed = new Relationship(KELVIN, LISA, RelationshipDirection.DIRECTED,
                    new Name("directed"), new ConfidenceEstimate(30));

            Person james = (Person) JAMES;
            Person kelvin = (Person) KELVIN;
            Person lisa = (Person) LISA;

            james.addRelationship(undirectedFrom);
            kelvin.addRelationship(undirectedTo);
            kelvin.addRelationship(directed);
            lisa.addRelationship(directed);
        } catch (DuplicateRelationshipException | IllegalValueException e) {
            assert false : "impossible to have invalid data in test data";
        }
    }
    //@@author

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

    /**
     * Returns an {@code AddressBook} with all the typical persons in unsorted order.
     */
    public static AddressBook getUnsortedAddressBook() {
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

    //@@author Xenonym
    /**
     * Returns an {@code AddressBook} with all the typical persons and with some relationships
     */
    public static AddressBook getTypicalAddressBookWithRelationships() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersonsWithRelationships()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }
    //@@author

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<ReadOnlyPerson> getUnsortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, CARL, ALICE, ELLE, FIONA, GEORGE));
    }

    //@@author Xenonym
    public static List<ReadOnlyPerson> getTypicalPersonsWithRelationships() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JAMES, KELVIN, LISA));
    }
    //@@author
}
