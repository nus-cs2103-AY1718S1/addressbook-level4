package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IMAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IMAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withPhone("85355255")
            .withTags("friends", "family").build();
    public static final ReadOnlyPerson ALEX = new PersonBuilder().withName("Alex Pauline")
            .withAddress("PGP")
            .withEmail("alice@example.com")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withPhone("85355255")
            .withTags("friends", "family").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withAddress("wall street").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withAddress("10th street").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442").withEmail("anna@example.com").withAddress("4th street")
            .withDateOfBirth("13.10.1997")
            .withImage("")
            .withTags("family").build();

    public static final ReadOnlyPerson RONAK = new PersonBuilder().withName("Ronak Lakhotia")
            .withPhone("9391111").withEmail("dumm@gmail.com").withAddress("3rd street")
            .withDateOfBirth("13.10.1997").withImage("").withTags("friends").build();

    public static final ReadOnlyPerson SHARMA = new PersonBuilder().withName("Ronak Sharma")
            .withPhone("9391111").withEmail("dumm@gmail.com").withAddress("3rd street")
            .withDateOfBirth("13.10.1997").withImage("").withTags("friends").build();


    public static final ReadOnlyReminder ASSIGNMENT = new ReminderBuilder().withDetails("CS2103T")
            .withPriority("Priority Level: High").withDueDate("12.11.2017").build();

    public static final ReadOnlyReminder MEETING = new ReminderBuilder().withDetails("Group meeting")
            .withPriority("Priority Level: High").withDueDate("12.11.2017").build();

    public static final ReadOnlyReminder TUTORIAL = new ReminderBuilder().withDetails("Tutorial meeting")
            .withPriority("Priority Level: Medium").withDueDate("12.10.2017").build();

    public static final ReadOnlyReminder PRACTICE = new ReminderBuilder().withDetails("Practice meeting")
            .withPriority("Priority Level: Medium").withDueDate("11.10.2017").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com")
            .withDateOfBirth("13.10.1997")
            .withAddress("little india")
            .withUsername("meier")
            .withImage("").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withDateOfBirth("13.10.1997")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withImage("").build();

    public static final ReadOnlyPerson DUMMY = new PersonBuilder().withName("Ronak").withPhone("8482131")
            .withDateOfBirth("19.12.1998")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withImage("").build();

    // Manually added - Person's details found in {@code CommandTestUtil}



    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withImage(VALID_IMAGE_AMY)
            .withRemark(VALID_REMARK_AMY).withDateOfBirth(VALID_DOB_AMY)
            .withUsername(VALID_USERNAME_AMY)
            .withTags(VALID_TAG_FRIEND).build();

    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withImage(VALID_IMAGE_BOB)
            .withRemark(VALID_REMARK_BOB).withDateOfBirth(VALID_DOB_BOB)
            .withUsername(VALID_USERNAME_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_RONAK = "Ronak";
    public static final String KEYWORD_MATCHING_LAKHOTIA = "Ronak";

    public static final ReadOnlyPerson LAKHOTIA = new PersonBuilder().withName("Ronak Lakhotia").withPhone("93911558")
            .withEmail("email@gmail.com").withAddress("Prince Georges Park").withDateOfBirth("13.10.1997")
            .build();

    public static final ReadOnlyPerson RANDOM = new PersonBuilder().withName("Ronak Lakhotia").withPhone("12345678")
            .withEmail("ronak@gmail.com").withAddress("Pgp").withDateOfBirth("13.10.1997")
            .withImage("")
            .build();
    public static final ReadOnlyPerson RANDOMNEW = new PersonBuilder().withName("Ronak Lakhotia").withPhone("12345678")
            .withEmail("ronak@gmail.com").withAddress("Pgp").withDateOfBirth("13.10.1997")
            .withImage("")
            .build();

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            }
            catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        for (ReadOnlyReminder reminder : getTypicalReminders()) {
            try {
                ab.addReminder(reminder);
            }
            catch (DuplicateReminderException de) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                RONAK, SHARMA, DUMMY, LAKHOTIA, RANDOM));
    }

    public static List<ReadOnlyReminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT, MEETING));
    }
}
