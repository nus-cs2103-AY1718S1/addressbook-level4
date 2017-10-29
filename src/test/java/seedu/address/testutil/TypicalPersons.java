package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Interest;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withHandphone("85355255").withHomePhone("60101010").withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET)
            .withPostalCode("600123").withDebt("123456789").withInterest(Interest.NO_INTEREST_SET)
            .withDeadline(Deadline.NO_DEADLINE_SET)
            .withTags("friendly").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withHandphone("98765432")
            .withPostalCode("123311").withDebt("12345").withInterest(Interest.NO_INTEREST_SET)
            .withHomePhone("62020022").withOfficePhone("60002000").withDeadline(Deadline.NO_DEADLINE_SET)
            .withTags("tricky", "friendly").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withHandphone("95352563")
            .withEmail("heinz@example.com").withPostalCode("111111").withDebt("123456").withOfficePhone("60030003")
            .withInterest(Interest.NO_INTEREST_SET).withDeadline(Deadline.NO_DEADLINE_SET)
            .withHomePhone("62333233").withAddress("wall street").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withHandphone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withDebt("1234567").withHomePhone("64044444")
            .withDeadline(Deadline.NO_DEADLINE_SET).withInterest(Interest.NO_INTEREST_SET).withOfficePhone("60044004")
            .withPostalCode("101010").withTags("tricky", "violent").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withHandphone("94872224")
            .withPostalCode("673673").withDebt("100000").withEmail("werner@example.com").withHomePhone("65055005")
            .withAddress("michegan ave").withInterest(Interest.NO_INTEREST_SET).withOfficePhone("60050505")
            .withTags("violent").withDeadline(Deadline.NO_DEADLINE_SET).build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withHomePhone("66066606")
            .withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).withPostalCode("616111").withHandphone("94824227")
            .withDebt("12").withDeadline(Deadline.NO_DEADLINE_SET).withEmail("lydia@example.com")
            .withInterest(Interest.NO_INTEREST_SET).withAddress("little tokyo")
            .withTags("tricky", "friendly").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withHandphone("94821442")
            .withPostalCode("040004").withEmail("anna@example.com").withDebt("45").withHomePhone("67273787")
            .withInterest(Interest.NO_INTEREST_SET).withDeadline(Deadline.NO_DEADLINE_SET)
            .withAddress("4th street").withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).build();

    // Blacklisted persons.
    public static final ReadOnlyPerson JELENA = new PersonBuilder().withName("Jelena Neo")
            .withAddress("123, Jurong West Ave 6, #08-111").withDebt("1234567").withEmail("alice@example.com")
            .withDeadline(Deadline.NO_DEADLINE_SET).withHandphone("85355255").withInterest(Interest.NO_INTEREST_SET)
            .withHomePhone("61234123").withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET)
            .withTags("friends").withPostalCode("623123").build();
    public static final ReadOnlyPerson WEIPING = new PersonBuilder().withName("Khoo Wei Ping")
            .withAddress("311, Clementi Ave 2, #02-25").withPostalCode("111111").withDebt("1234567")
            .withEmail("johnd@example.com").withInterest(Interest.NO_INTEREST_SET).withHomePhone("65600222")
            .withHandphone("98765432").withTags("friends")
            .withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).build();
    public static final ReadOnlyPerson JAIVIGNESH = new PersonBuilder().withName("Jaivignesh Venugopal")
            .withDeadline(Deadline.NO_DEADLINE_SET).withHandphone("95352563").withEmail("heinz@example.com")
            .withAddress("wall street").withPostalCode("111111").withInterest(Interest.NO_INTEREST_SET)
            .withHomePhone("63433233").withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).withDebt("1234567").build();
    public static final ReadOnlyPerson LAWRENCE = new PersonBuilder().withName("Lawrence Koh")
            .withDeadline(Deadline.NO_DEADLINE_SET).withHandphone("87652533").withInterest(Interest.NO_INTEREST_SET)
            .withEmail("cornelia@example.com").withAddress("10th street").withHomePhone("64748494")
            .withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).withPostalCode("111111").withDebt("1234567").build();

    // Whitelisted persons. Debt has to be zero!
    public static final ReadOnlyPerson ARCHANA = new PersonBuilder().withName("Archana Pradeep")
            .withDeadline(Deadline.NO_DEADLINE_SET).withHandphone("94812224").withInterest(Interest.NO_INTEREST_SET)
            .withEmail("werner@example.com").withAddress("michegan ave").withOfficePhone("60000010")
            .withHomePhone("61111101").withPostalCode("111111").withDebt("0").build();
    public static final ReadOnlyPerson SIRISHA = new PersonBuilder().withName("Lakshmi Sirisha")
            .withDeadline(Deadline.NO_DEADLINE_SET).withHandphone("94822427").withInterest(Interest.NO_INTEREST_SET)
            .withEmail("lydia@example.com").withAddress("little tokyo").withHomePhone("62222202")
            .withOfficePhone("60000020").withPostalCode("111111").withDebt("0").build();
    public static final ReadOnlyPerson RUSHAN = new PersonBuilder().withName("Khor Ru Shan")
            .withDeadline(Deadline.NO_DEADLINE_SET).withHandphone("94823442").withInterest(Interest.NO_INTEREST_SET)
            .withEmail("anna@example.com").withAddress("4th street").withHomePhone("63333303")
            .withPostalCode("111111").withDebt("0").withOfficePhone("60000030").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withHandphone("84842424")
            .withEmail("stefan@example.com").withAddress("little india").withDebt("560")
            .withInterest(Interest.NO_INTEREST_SET).withDeadline(Deadline.NO_DEADLINE_SET)
            .withPostalCode("217959").withHomePhone("61110111").withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET)
            .build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller")
            .withHandphone("84852131").withEmail("hans@example.com").withPostalCode("789789")
            .withDebt("7890").withHomePhone("62220222").withInterest(Interest.NO_INTEREST_SET)
            .withDeadline(Deadline.NO_DEADLINE_SET).withAddress("chicago ave")
            .withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withHandphone(VALID_HANDPHONE_AMY).withHomePhone(VALID_HOME_PHONE_AMY)
            .withOfficePhone(VALID_OFFICE_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withPostalCode(VALID_POSTAL_CODE_AMY).withDebt(VALID_DEBT_AMY).withTags(VALID_TAG_FRIEND)
            .withInterest(VALID_INTEREST_AMY).withDeadline(VALID_DEADLINE_AMY).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withHandphone(VALID_HANDPHONE_BOB).withHomePhone(VALID_HOME_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withDebt(VALID_DEBT_BOB).withDeadline(VALID_DEADLINE_BOB).withPostalCode(VALID_POSTAL_CODE_BOB)
            .withInterest(VALID_INTEREST_BOB).withOfficePhone(VALID_OFFICE_PHONE_BOB).build();

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
                assert false : "It is not possible to have a duplicate person added into the addressbook";
            }
        }

        for (ReadOnlyPerson person : getTypicalBlacklistedPersons()) {
            ab.addBlacklistedPerson(person);
        }

        for (ReadOnlyPerson person : getTypicalWhitelistedPersons()) {
            ab.addWhitelistedPerson(person);
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays
                .asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                        JELENA, WEIPING, JAIVIGNESH, LAWRENCE, ARCHANA, SIRISHA, RUSHAN));
    }

    public static List<ReadOnlyPerson> getTypicalBlacklistedPersons() {
        return new ArrayList<>(Arrays.asList(JELENA, WEIPING, JAIVIGNESH, LAWRENCE));
    }

    public static List<ReadOnlyPerson> getTypicalWhitelistedPersons() {
        return new ArrayList<>(Arrays.asList(ARCHANA, SIRISHA, RUSHAN));
    }
}
