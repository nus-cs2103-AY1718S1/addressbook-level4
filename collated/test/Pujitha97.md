# Pujitha97
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private static final String DOB_FIELD_ID = "#dob";
    private static final String GENDER_FIELD_ID = "#gender";
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getDateOfBirth() {
        return dobLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }
```
###### \java\guitests\guihandles\ProfilePanelHandle.java
``` java
    public String getDateOfBirth() {
        return dobLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_DOB_AMY = "27 01 1997";
    public static final String VALID_DOB_BOB = "05 05 1996";
    public static final String VALID_GENDER_AMY = "FEMALE";
    public static final String VALID_GENDER_BOB = "MALE";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String DOB_DESC_AMY = " " + PREFIX_DOB + VALID_DOB_AMY;
    public static final String DOB_DESC_BOB = " " + PREFIX_DOB + VALID_DOB_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "Pujitha"; // unrecognised words for gender
    public static final String INVALID_DOB_DESC = " " + PREFIX_DOB + "19 boo 1"; // unrecognised words for dob
```
###### \java\seedu\address\logic\commands\EditPersonDescriptorTest.java
``` java
        // different dob -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withDateOfBirth(VALID_DOB_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple dobs - last DOB accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DOB_DESC_AMY
                + DOB_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GENDER_DESC_AMY
                + DOB_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing date of birth prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withGender(VALID_GENDER_AMY).withEmptyDateOfBirth().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + GENDER_DESC_AMY, new AddCommand(expectedPerson));

        // missing gender prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withEmptyGender().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY, new AddCommand(expectedPerson));
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid dob
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + GENDER_DESC_BOB + INVALID_DOB_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                DateParser.MESSAGE_INVALID_MONTH);

        // invalid gender
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DOB_DESC_BOB + INVALID_GENDER_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Gender.MESSAGE_GENDER_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_GENDER_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_DOB_DESC, DateParser.MESSAGE_INVALID_MONTH); // invalid dob
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // dob
        userInput = targetIndex.getOneBased() + DOB_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withDateOfBirth(VALID_DOB_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseDateOfBirth_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDateOfBirth(null);
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDateOfBirth(Optional.of(INVALID_DOB));
    }

    @Test
    public void parseDateOfBirth_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDateOfBirth(Optional.empty()).isPresent());
    }

    @Test
    public void parseDateOfBirth_validValue_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DateOfBirth);
        Optional<DateOfBirth> actualDateOfBirth = ParserUtil.parseDateOfBirth(Optional.of(VALID_DateOfBirth));

        assertEquals(expectedDateOfBirth, actualDateOfBirth.get());
    }
    @Test
    public void parseGender_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseGender(null);
    }

    @Test
    public void parseGender_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseGender(Optional.of(INVALID_GENDER));
    }

    @Test
    public void parseGender_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseGender(Optional.empty()).isPresent());
    }

    @Test
    public void parseGender_validValue_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        Optional<Gender> actualGender = ParserUtil.parseGender(Optional.of(VALID_GENDER));

        assertEquals(expectedGender, actualGender.get());
    }
```
###### \java\seedu\address\model\person\DateOfBirthTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DateOfBirthTest {
    @Test
    public void isValidDateOfBirth() {
        // invalid dob
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only

        // valid dob
        assertTrue(DateOfBirth.isValidDateOfBirth("27 01 1997")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27 01/1997")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27-01.97")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27 JaN 1997")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27 January 97")); // alphabets only
    }
}

```
###### \java\seedu\address\model\person\GenderTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class GenderTest {
    @Test
    public void isValidGender() {
        // invalid Gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("hello world"));
        assertFalse(Gender.isValidGender("1234"));
        assertFalse(Gender.isValidGender("MALEEEEE"));
        assertFalse(Gender.isValidGender("Male123(*)"));

        // valid gender
        assertTrue(Gender.isValidGender("FeMaLe")); // alphabets only
        assertTrue(Gender.isValidGender("MALE")); // alphabets only
        assertTrue(Gender.isValidGender("other")); // alphabets only
        assertTrue(Gender.isValidGender("Not_SpeCified")); // alphabets only
        assertTrue(Gender.isValidGender("OtHeR")); // alphabets only
    }
}

```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
        descriptor.setDateOfBirth(person.getDateOfBirth());
        descriptor.setGender(person.getGender());
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Date of Birth} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDateOfBirth(String dob) {
        try {
            ParserUtil.parseDateOfBirth(Optional.of(dob)).ifPresent(descriptor::setDateOfBirth);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date of birth is expected to be unique.");
        }
        return this;
    }
    /**
     * Sets the {@code Gender} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGender(String gender) {
        try {
            ParserUtil.parseGender(Optional.of(gender)).ifPresent(descriptor::setGender);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Gender is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dob) {
        try {
            this.person.setDateOfBirth(new DateOfBirth(dob));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date of Birth is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyDateOfBirth() {
        this.person.setDateOfBirth(new DateOfBirth());
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        try {
            this.person.setGender(new Gender(gender));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Gender is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyGender() {
        this.person.setGender(new Gender());
        return this;
    }
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
        sb.append(PREFIX_GENDER + person.getGender().toString() + " ");
        sb.append(PREFIX_DOB + person.getDateOfBirth().toString() + " ");
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson AMY_NO_DOB = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withEmptyDateOfBirth().withTags(VALID_TAG_FRIEND)
            .withGender(VALID_GENDER_AMY).build();
    public static final ReadOnlyPerson AMY_NO_GENDER = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DOB_AMY).withEmptyGender().withTags(VALID_TAG_FRIEND)
            .build();
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
            personWithTags.setDateOfBirth(ALICE.getDateOfBirth());
            personWithTags.setGender(ALICE.getGender());
```
###### \java\seedu\address\ui\ProfilePanelTest.java
``` java
        assertNull(profilePanelHandle.getDateOfBirth());
        assertNull(profilePanelHandle.getGender());
```
###### \java\seedu\address\ui\ProfilePanelTest.java
``` java
        assertEquals(expectedSelectedPerson.getDateOfBirth().toString(), profilePanelHandle.getDateOfBirth());
        assertEquals(expectedSelectedPerson.getGender().toString(), profilePanelHandle.getGender());
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except dob -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_BOB)
                .withGender(VALID_GENDER_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DOB_DESC_BOB + GENDER_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except gender -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withGender(VALID_GENDER_BOB).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DOB_DESC_AMY + GENDER_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing dob -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_DOB);
        /* Case: missing gender -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DOB_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_GENDER);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid dob -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + GENDER_DESC_AMY + ADDRESS_DESC_AMY + INVALID_DOB_DESC;
        assertCommandFailure(command, DateParser.MESSAGE_INVALID_MONTH);
        /* Case: invalid gender -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_GENDER_DESC + ADDRESS_DESC_AMY + DOB_DESC_AMY;
        assertCommandFailure(command, Gender.MESSAGE_GENDER_CONSTRAINTS);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid DOB -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_DOB_DESC,
                DateParser.MESSAGE_INVALID_MONTH);
        /* Case: invalid gender -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_GENDER_DESC,
                Gender.MESSAGE_GENDER_CONSTRAINTS);
```
