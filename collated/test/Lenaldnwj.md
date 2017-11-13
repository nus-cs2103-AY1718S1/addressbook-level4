# Lenaldnwj
###### /java/seedu/address/logic/commands/EditPersonDescriptorTest.java
``` java
        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different parentPhone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withParentPhone(VALID_PARENTPHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_AMY,
                new AddCommand(expectedPerson));

        // no email
        Person expectedPersonWithNoEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + POSTALCODE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoEmail));

        // no student phone
        Person expectedPersonWithNoStudentPhone = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone("(Student phone not recorded)")
                .withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                        + PARENTPHONE_DESC_AMY + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + POSTALCODE_DESC_AMY + TAG_DESC_FRIEND + EMAIL_DESC_AMY,
                new AddCommand(expectedPersonWithNoStudentPhone));

        // no address
        Person expectedPersonWithNoAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withAddress("(Address not recorded)")
                .withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + POSTALCODE_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoAddress));

        // no postal code
        Person expectedPersonWithNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + EMAIL_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoPostalCode));

        // no address, no postal code
        Person expectedPersonWithNoAddressNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoAddressNoPostalCode));

        // no email, no address, no postal code
        Person expectedPersonWithNoEmailNoAddressNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoEmailNoAddressNoPostalCode));

        // no email, no address, no postal code, no tag.
        Person expectedPersonWithOptionalPhone = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + PARENTPHONE_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY,
                new AddCommand(expectedPersonWithOptionalPhone));

        // no student phone, no email, no address, no postal code, no tag. Have none of the optional inputs
        Person expectedPersonWithNoOptionalInputs = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone("(Student phone not recorded)").withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                        + PARENTPHONE_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY,
                new AddCommand(expectedPersonWithNoOptionalInputs));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PARENTPHONE_DESC_BOB
                + FORMCLASS_DESC_BOB + GRADES_DESC_BOB, expectedMessage);

        // missing parentPhone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PARENTPHONE_BOB
                + FORMCLASS_DESC_BOB + GRADES_DESC_BOB, expectedMessage);

        // missing formClass prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PARENTPHONE_DESC_BOB
                + VALID_FORMCLASS_BOB + GRADES_DESC_BOB, expectedMessage);

        // missing grades prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PARENTPHONE_DESC_BOB
                + FORMCLASS_DESC_BOB + VALID_GRADES_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PARENTPHONE_BOB
                + VALID_FORMCLASS_BOB + VALID_GRADES_BOB, expectedMessage);
    }
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
    @Test
    public void optionalInput() {

        // When student phone, address, postal code, and email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe "
                + "pp/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe pp/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "e/ (Email not recorded) c/ (Postal code not recorded) p/ (Student phone not recorded)");

        // When address, postal code, and email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/97272031 "
                + "pp/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/97272031 pp/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "e/ (Email not recorded) c/ (Postal code not recorded)");

        // When address and postal code not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/97272031 "
                + "pp/97979797 e/johnd@example.com f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/97272031 pp/97979797 e/johnd@example.com "
                + "f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "c/ (Postal code not recorded)");

        // When email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/97272031 "
                + "pp/97979797 a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/97272031 "
                + "pp/97979797 a/311, Clementi Ave 2, #02-25 "
                + "f/12S23 g/123.0 t/friends t/owesMoney "
                + "e/ (Email not recorded) c/ (Postal code not recorded)");

        // When postal code not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/97272031 "
                + "pp/97979797 e/johnd@example.com a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/97272031 pp/97979797 e/johnd@example.com "
                + "a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney "
                + "c/ (Postal code not recorded)");

        // When student number not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe "
                + "pp/97979797 e/johnd@example.com a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 c/673349 "
                + "t/friends t/owesMoney"), "add n/John Doe pp/97979797 e/johnd@example.com "
                + "a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "c/673349 t/friends t/owesMoney p/ (Student phone not recorded)");
    }
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // parentPhone
        userInput = targetIndex.getOneBased() + PARENTPHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withParentPhone(VALID_PARENTPHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseParentPhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseParentPhone(null);
    }

    @Test
    public void parseParentPhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseParentPhone(Optional.of(INVALID_PARENTPHONE));
    }

    @Test
    public void parseParentPhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseParentPhone(Optional.empty()).isPresent());
    }

    @Test
    public void parseParentPhone_validValue_returnsParentPhone() throws Exception {
        ParentPhone expectedParentPhone = new ParentPhone(VALID_PARENTPHONE);
        Optional<ParentPhone> actualGrades = ParserUtil.parseParentPhone(Optional.of(VALID_PARENTPHONE));

        assertEquals(expectedParentPhone, actualGrades.get());
    }

```
###### /java/seedu/address/model/person/ParentPhoneTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParentPhoneTest {

    @Test
    public void isValidPhone() {
        // invalid parent phone numbers
        assertFalse(ParentPhone.isValidParentPhone("")); // empty string
        assertFalse(ParentPhone.isValidParentPhone(" ")); // spaces only
        assertFalse(ParentPhone.isValidParentPhone("9797979")); // number do not have exactly 8 digit
        assertFalse(ParentPhone.isValidParentPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(ParentPhone.isValidParentPhone("97jhb971")); // alphanumeric numbers
        assertFalse(ParentPhone.isValidParentPhone("gfxgfxgf")); // purely alphabets

        // valid parent phone numbers
        assertTrue(ParentPhone.isValidParentPhone("97272031")); // parent phone number of exactly 8 numbers
        assertTrue(ParentPhone.isValidParentPhone("87767988")); // parent phone number of exactly 8 digit numbers
    }

}
```
###### /java/seedu/address/model/person/PhoneTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("9797979")); // number do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(Phone.isValidPhone("97jhb971")); // alphanumeric numbers
        assertFalse(Phone.isValidPhone("gfxgfxgf")); // purely alphabets

        // valid phone numbers
        assertTrue(Phone.isValidPhone("97272031")); // student phone number of exactly 8 numbers
        assertTrue(Phone.isValidPhone("87767988")); // student number only of exactly 8 digit numbers
    }

}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code ParentPhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentPhone(String parentPhone) {
        try {
            ParserUtil.parseParentPhone(Optional.of(parentPhone)).ifPresent(descriptor::setParentPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("parentPhone is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code ParentPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentPhone(String parentPhone) {
        try {
            this.person.setParentPhone(new ParentPhone(parentPhone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("parentPhone is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/ui/PersonCardTest.java
``` java
    @Test
    public void obtainTagColors() {

        //Check if same tags are assigned the same color.
        Person marcusPersonWithTags = new PersonBuilder().withName("Marcus").withTags("Basketballer").build();
        PersonCard marcusPersonCard = new PersonCard(marcusPersonWithTags, 3);

        Person jamiePersonWithTags = new PersonBuilder().withName("Jamie").withTags("Basketballer").build();
        PersonCard jamiePersonCard = new PersonCard(jamiePersonWithTags, 4);

        Person lennyPersonWithTags = new PersonBuilder().withName("Lenny").withTags("Basketballer").build();
        PersonCard lennyPersonCard = new PersonCard(lennyPersonWithTags, 5);

        assertEquals(marcusPersonCard.obtainTagColors(marcusPersonWithTags.getTags().toString()),
                jamiePersonCard.obtainTagColors(jamiePersonWithTags.getTags().toString()));

        assertEquals(marcusPersonCard.obtainTagColors(marcusPersonWithTags.getTags().toString()),
                lennyPersonCard.obtainTagColors(lennyPersonWithTags.getTags().toString()));

        assertEquals(lennyPersonCard.obtainTagColors(lennyPersonWithTags.getTags().toString()),
                jamiePersonCard.obtainTagColors(jamiePersonWithTags.getTags().toString()));


        //Check if different tags are assigned different color.
        Person lunaPersonWithTags = new PersonBuilder().withName("Luna").withTags("Quiet").build();
        PersonCard lunaPersonCard = new PersonCard(lunaPersonWithTags, 6);

        Person potterPersonWithTags = new PersonBuilder().withName("Potter").withTags("Noisy").build();
        PersonCard potterPersonCard = new PersonCard(potterPersonWithTags, 7);

        Person ronPersonWithTags = new PersonBuilder().withName("Ron").withTags("Happy").build();
        PersonCard ronPersonCard = new PersonCard(ronPersonWithTags, 8);

        assertNotEquals(lunaPersonCard.obtainTagColors(lunaPersonWithTags.getTags().toString()),
                potterPersonCard.obtainTagColors(potterPersonWithTags.getTags().toString()));

        assertNotEquals(ronPersonCard.obtainTagColors(ronPersonWithTags.getTags().toString()),
                lunaPersonCard.obtainTagColors(lunaPersonWithTags.getTags().toString()));

        assertNotEquals(ronPersonCard.obtainTagColors(ronPersonWithTags.getTags().toString()),
                potterPersonCard.obtainTagColors(potterPersonWithTags.getTags().toString()));

        //Check if assigned tag color is added into the Arraylist of usedColors.
        Person personWithTags = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(personWithTags, 2);

        assertTrue(personCard.getUsedColor().contains(personCard.getAssignedTagColor()));
        assertTrue(ronPersonCard.getUsedColor().contains(ronPersonCard.getAssignedTagColor()));
        assertTrue(lunaPersonCard.getUsedColor().contains(lunaPersonCard.getAssignedTagColor()));
        assertTrue(potterPersonCard.getUsedColor().contains(potterPersonCard.getAssignedTagColor()));
        assertTrue(jamiePersonCard.getUsedColor().contains(jamiePersonCard.getAssignedTagColor()));
        assertTrue(marcusPersonCard.getUsedColor().contains(marcusPersonCard.getAssignedTagColor()));
        assertTrue(lennyPersonCard.getUsedColor().contains(lennyPersonCard.getAssignedTagColor()));

    }
```
###### /java/seedu/address/ui/PersonCardTest.java
``` java

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
###### /java/systemtests/AddCommandSystemTest.java
``` java

        /* Case: missing student phone -> added */
        toAdd = new PersonBuilder().withName("Amy Beenn").withPhone("(Student phone not recorded)")
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Amy Beenn" + PARENTPHONE_DESC_AMY + ADDRESS_DESC_AMY
                + EMAIL_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> added */
        toAdd = new PersonBuilder().withName("Amy Beenn").withPhone(VALID_PHONE_BOB)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail("(Email not recorded)")
                .withAddress(VALID_ADDRESS_AMY).withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Amy Beenn" + PHONE_DESC_BOB + PARENTPHONE_DESC_AMY + ADDRESS_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> added */
        toAdd = new PersonBuilder().withName("Johnny").withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress("(Address not recorded)").withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode(VALID_POSTALCODE_BOB).withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Johnny" + PHONE_DESC_AMY + PARENTPHONE_DESC_AMY + EMAIL_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing postalCode -> added */
        toAdd = new PersonBuilder().withName("June").withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode("(Postal code not recorded)").withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ June" + PHONE_DESC_AMY
                + PARENTPHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing postalCode and address -> added */
        toAdd = new PersonBuilder().withName("July").withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress("(Address not recorded)").withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode("(Postal code not recorded)").withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ July" + PHONE_DESC_AMY + PARENTPHONE_DESC_AMY + EMAIL_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email, postalCode and address -> added */
        toAdd = new PersonBuilder().withName("Midoriya").withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail("(Email not recorded)")
                .withAddress("(Address not recorded)").withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode("(Postal code not recorded)").withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Midoriya" + PHONE_DESC_AMY + PARENTPHONE_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email, student phone, postalCode and address -> added */
        toAdd = new PersonBuilder().withName("Emilia").withPhone("(Student phone not recorded)")
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail("(Email not recorded)")
                .withAddress("(Address not recorded)").withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode("(Postal code not recorded)").withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Emilia" + PARENTPHONE_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address and email -> added */
        toAdd = new PersonBuilder().withName("Sally").withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail("(Email not recorded)")
                .withAddress("(Address not recorded)").withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode(VALID_POSTALCODE_BOB).withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Sally" + PHONE_DESC_AMY + PARENTPHONE_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing postalCode and email -> added */
        toAdd = new PersonBuilder().withName("Ray").withPhone(VALID_PHONE_AMY)
                .withParentPhone(VALID_PARENTPHONE_AMY).withEmail("(Email not recorded)")
                .withAddress(VALID_ADDRESS_AMY).withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY)
                .withPostalCode("(Postal code not recorded)").withTags(VALID_TAG_FRIEND)
                .build();
        command = AddCommand.COMMAND_WORD + " n/ Ray" + PHONE_DESC_AMY + PARENTPHONE_DESC_AMY + ADDRESS_DESC_AMY
                + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### /java/systemtests/EditCommandSystemTest.java
``` java
        /* Case: invalid parentPhone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_PARENTPHONE_DESC,
                ParentPhone.MESSAGE_PARENTPHONE_CONSTRAINTS);
```
