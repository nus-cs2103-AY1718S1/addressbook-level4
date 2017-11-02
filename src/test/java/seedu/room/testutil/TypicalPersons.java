package seedu.room.testutil;

import static seedu.room.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_ROOM_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.room.model.ResidentBook;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withRoom("08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withRoom("02-250")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withRoom("04-300F").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withRoom("06-120").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withRoom("09-165G").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withRoom("15-117").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withRoom("16-150K").build();
    public static final ReadOnlyPerson TEMPORARY_JOE = new PersonBuilder().withName("Temporary Joe")
            .withPhone("9482442").withEmail("anna@example.com").withRoom("17-210").withTemporary(0)
            .withTags("friend").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withRoom("05-140").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withRoom("13-119").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY).withTemporary(0).withTags(VALID_TAG_FRIEND)
            .build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB).withTemporary(0).withTags(VALID_TAG_HUSBAND,
                    VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB1 = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB).withTemporary(1).withTags(VALID_TAG_HUSBAND,
                    VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code ResidentBook} with all the typical persons.
     */
    public static ResidentBook getTypicalResidentBook() {
        ResidentBook ab = new ResidentBook();
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
