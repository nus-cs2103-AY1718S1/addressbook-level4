package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEAD_LINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEAD_LINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.DeadLine;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withPostalCode("600123").withDebt("123456789")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withPostalCode("123311").withDebt("12345")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withPostalCode("111111").withDebt("123456")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withAddress("wall street").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withDebt("1234567")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPostalCode("101010").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224").withPostalCode("673673").withDebt("100000")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPostalCode("616111").withPhone("9482427").withDebt("12").withDeadLine(DeadLine.NO_DEAD_LINE_SET)
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withPostalCode("040004").withEmail("anna@example.com").withDebt("45")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withAddress("4th street").build();
    public static final ReadOnlyPerson JELENA = new PersonBuilder().withName("Jelena Neo")
            .withAddress("123, Jurong West Ave 6, #08-111").withDebt("1234567").withEmail("alice@example.com")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPhone("85355255").withTags("friends").build();
    public static final ReadOnlyPerson WEIPING = new PersonBuilder().withName("Khoo Wei Ping")
            .withAddress("311, Clementi Ave 2, #02-25").withPostalCode("111111").withDebt("1234567")
            .withEmail("johnd@example.com").withPhone("98765432").withDeadLine(DeadLine.NO_DEAD_LINE_SET)
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson JAIVIGNESH = new PersonBuilder().withName("Jaivignesh Venugopal")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPhone("95352563").withEmail("heinz@example.com")
            .withAddress("wall street").withPostalCode("111111").withDebt("1234567").build();
    public static final ReadOnlyPerson LAWRENCE = new PersonBuilder().withName("Lawrence Koh")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withPostalCode("111111").withDebt("1234567").build();
    public static final ReadOnlyPerson ARCHANA = new PersonBuilder().withName("Archana Pradeep")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withPostalCode("111111").withDebt("1234567").build();
    public static final ReadOnlyPerson SIRISHA = new PersonBuilder().withName("Lakshmi Sirisha")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withPostalCode("111111").withDebt("1234567").build();
    public static final ReadOnlyPerson RUSHAN = new PersonBuilder().withName("Khor Ru Shan")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withPostalCode("111111").withDebt("1234567").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withDebt("560")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withPostalCode("217959").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withPostalCode("789789").withDebt("7890")
            .withDeadLine(DeadLine.NO_DEAD_LINE_SET).withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTAL_CODE_AMY)
            .withDebt(VALID_DEBT_AMY).withTags(VALID_TAG_FRIEND).withDeadLine(VALID_DEAD_LINE_AMY).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withDebt(VALID_DEBT_BOB).withDeadLine(VALID_DEAD_LINE_BOB).withPostalCode(VALID_POSTAL_CODE_BOB).build();

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

        for (ReadOnlyPerson person : getTypicalBlacklistedPersons()) {
            try {
                ab.addBlacklistedPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<ReadOnlyPerson> getTypicalBlacklistedPersons() {
        return new ArrayList<>(Arrays.asList(JELENA, WEIPING, JAIVIGNESH, LAWRENCE, ARCHANA, SIRISHA, RUSHAN));
    }
}
