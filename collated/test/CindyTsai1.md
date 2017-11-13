# CindyTsai1
###### \java\seedu\address\logic\commands\SuggestCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SuggestCommand}.
 */
public class SuggestCommandTest {
    @Test
    public void execute_suggest_success() {
        String command = "edit";
        String expectedMessage = String.format(MESSAGE_SUCCESS, command);

        // assert that suggest command is executed
        try {
            new SuggestCommand(command).execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + GENDER_DESC_BOB
                + MATRIC_NO_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB + TIMETABLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing birthday prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + GENDER_DESC_BOB
                + MATRIC_NO_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_BIRTHDAY_BOB + TIMETABLE_DESC_BOB, expectedMessage);

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + GENDER_DESC_BOB
                        + MATRIC_NO_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INVALID_BIRTHDAY_DESC + TIMETABLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_BIRTHDAY_DESC,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS); // invalid birthday

```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

```
###### \java\seedu\address\logic\parser\SuggestCommandParserTest.java
``` java
public class SuggestCommandParserTest {

    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    public void parse_validCommand_returnsSuggestCommand() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, "edit");

        //extra character at the back of the commandWord
        assertParseSuccess(parser, "editt", expectedMessage);

        //extra character at the front of the commandWord
        assertParseSuccess(parser, "eedit", expectedMessage);

        //extra character in the middle of the commandWord
        assertParseSuccess(parser, "ediit", expectedMessage);

        //missing character at the back of the commandWord
        assertParseSuccess(parser, "edi", expectedMessage);

        //missing character at the front of the commandWord
        assertParseSuccess(parser, "dit", expectedMessage);

        //missing character in the middle of the commandWord
        assertParseSuccess(parser, "edt", expectedMessage);

        //swapping character in the middle of the commandWord
        assertParseSuccess(parser, "eidt", expectedMessage);

        //swapping character at the back of the commandWord
        assertParseSuccess(parser, "edti", expectedMessage);

        //swapping character at the front of the commandWord
        assertParseSuccess(parser, "deit", expectedMessage);

        //wrong character in the middle of the commandWord
        assertParseSuccess(parser, "edet", expectedMessage);

        //wrong character at the back of the commandWord
        assertParseSuccess(parser, "edip", expectedMessage);

        //wrong character at the front of the commandWord
        assertParseSuccess(parser, "adit", expectedMessage);
    }

    @Test
    public void parse_invalidCommand_returnsfailure() {
        //suggested command not found
        assertParseSuccess(parser, "amend", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Asserts that the parsing of {@code command} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String command, String expectedMessage) {
        try {
            parser.parse(command).execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }
    }
}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("1324")); // less than 8 digits
        assertFalse(Birthday.isValidBirthday("dgd")); // non-numeric
        assertFalse(Birthday.isValidBirthday("23/05/1997")); // invalid slash
        assertFalse(Birthday.isValidBirthday("23 05 1997")); // invalid space
        assertFalse(Birthday.isValidBirthday("99999999")); //invalid dates
        assertFalse(Birthday.isValidBirthday("00000000")); //invalid dates

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("23051997"));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_AMY)
                .withMatricNo(VALID_MATRIC_NO_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB).withTimetable(VALID_TIMETABLE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + MATRIC_NO_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_BOB + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_BIRTHDAY_DESC,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

```
