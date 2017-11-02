# Lenaldnwj
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_AMY,
                new AddCommand(expectedPerson));

        // no email
        Person expectedPersonWithNoEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + POSTALCODE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoEmail));

        // no address
        Person expectedPersonWithNoAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + POSTALCODE_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoAddress));

        // no postal code
        Person expectedPersonWithNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoPostalCode));

        // no address, no postal code
        Person expectedPersonWithNoAddressNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoAddressNoPostalCode));

        // no email, no address, no postal code
        Person expectedPersonWithNoEmailNoAddressNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoEmailNoAddressNoPostalCode));

        // no email, no address, no postal code, no tag, meaning don't have all the optional inputs
        Person expectedPersonWithNoOptionalInputs = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY,
                new AddCommand(expectedPersonWithNoOptionalInputs));
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void optionalInput() {

        // When address, postal code, and email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 parent/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "e/ (Email not recorded) c/ (Postal code not recorded)");

        // When address and postal code not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 e/johnd@example.com f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 parent/97979797 e/johnd@example.com "
                + "f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "c/ (Postal code not recorded)");

        // When email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 "
                + "parent/97979797 a/311, Clementi Ave 2, #02-25 "
                + "f/12S23 g/123.0 t/friends t/owesMoney "
                + "e/ (Email not recorded) c/ (Postal code not recorded)");

        // When postal code not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 e/johnd@example.com a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 parent/97979797 e/johnd@example.com "
                + "a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney "
                + "c/ (Postal code not recorded)");

        // When student number not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/"
                + "parent/97979797 e/johnd@example.com a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 c/673349 "
                + "t/friends t/owesMoney"), "add n/John Doe p/parent/97979797 e/johnd@example.com "
                + "a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "c/673349 t/friends t/owesMoney");
    }
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("Student: 97272031 Parent: 9797979")); // parent do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("Student: 9727 Parent: 979")); // both do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("Student: 9727 Parent: 97979797")); // student do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("Student/ 97272031 Parent/ 97979797")); // / instead of :
        assertFalse(Phone.isValidPhone("studen: 97272031 paret: 97979797")); // wrong labelling
        assertFalse(Phone.isValidPhone("Student: 97272031   Parent: 97979797")); // consecutive whitespaces
        assertFalse(Phone.isValidPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(Phone.isValidPhone("Student: 972jb72031 Parent: 97jhb979797")); // alphanumeric numbers
        assertFalse(Phone.isValidPhone("Student: gfxgfx Parent: gfxgfxgfx")); // purely alphabets
        assertFalse(Phone.isValidPhone("Student: 97272031")); // must have at least parent number
        assertFalse(Phone.isValidPhone("Parent: 97979797 "
                + "Student: 97272031")); // parent number should be entered after student number
        // if both parent and student number are entered.
        assertFalse(Phone.isValidPhone("Parent: 972701")); // have at least parent number, but not exactly 8 digits.
        assertFalse(Phone.isValidPhone("StudenT: 97272031 "
                + "ParenT: 97979797")); // only first letter of student and parent can be in upper case

        // valid phone numbers
        assertTrue(Phone.isValidPhone("Student: 97272031 "
                + "Parent: 97979797")); // both parent and student phone number of exactly 8 numbers
        assertTrue(Phone.isValidPhone("Student: 87272111 "
                + "Parent: 87767988")); // another set of parent and student phone number of exactly 8 digit numbers
        assertTrue(Phone.isValidPhone("Parent: 87767988")); // parent number only of exactly 8 digit numbers
    }

    @Test
    public void changeToAppropriateUiFormat() {
        // Invalid Ui Format
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "Student:97272030 Parent:97979797"); // No spacing after Student: and Parent:
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student:97272030 parent:97979797"); // No capital letter S and P
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student:97272011 parent:97979700"); // Incorrect numbers
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student/ 97272011 parent/ 97979700"); // / does not change to :
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student: 97272011 Parent: 97979700"); // Letter s not capitalised
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "Student: 97272011 parent: 97979700"); // Letter p not capitalised
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                ""); // Returned empty string


        // Valid Ui Format
        assertEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "Student: 97272030 Parent: 97979797"); // valid Ui format
        assertEquals(Phone.changeToAppropriateUiFormat("parent/97979776"),
                "Parent: 97979776"); // Student number left out (student number is a optional field)
        assertEquals(Phone.changeToAppropriateUiFormat("student/82278977 parent/97979776"),
                "Student: 82278977 Parent: 97979776"); // valid Ui format with another set of numbers
    }
}
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
    @Test
    public void obtainTagColors() {
        Person personWithTags = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(personWithTags, 2);

        //Check if assigned tag color is added into the Arraylist of usedColors to ensure that
        //colors are unique after being assigned to a tag.
        assertTrue(personCard.getUsedColor().contains(personCard.getAssignedTagColor()));
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
```
