# zhoukai07
###### /java/seedu/address/logic/commands/ChangeThemeCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIntegers.INTEGER_FIRST_THEME;
import static seedu.address.testutil.TypicalIntegers.INTEGER_FOURTH_THEME;
import static seedu.address.testutil.TypicalIntegers.INTEGER_SECOND_THEME;
import static seedu.address.testutil.TypicalIntegers.INTEGER_THIRD_THEME;
import static seedu.address.testutil.TypicalIntegers.OUT_OF_BOUND_THEME;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ChangeThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validTheme_success() {
        execute_changeToTheme_success(INTEGER_FIRST_THEME);
        execute_changeToTheme_success(INTEGER_SECOND_THEME);
        execute_changeToTheme_success(INTEGER_THIRD_THEME);
        execute_changeToTheme_success(INTEGER_FOURTH_THEME);
    }
    @Test
    public void execute_invalidTheme_failure() {
        Integer outOfBoundsInteger = OUT_OF_BOUND_THEME;
        assertExecutionFailure(outOfBoundsInteger, Messages.MESSAGE_INVALID_THEME_INDEX);
    }
    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code integer}.
     */
    private void execute_changeToTheme_success(Integer integer) {
        ChangeThemeCommand changeThemeCommand = prepareCommand(integer);
        try {
            CommandResult result = changeThemeCommand.execute();
            assertEquals(String.format(ChangeThemeCommand.MESSAGE_SELECT_THEME_SUCCESS, integer),
                    result.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
    }
    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code integer}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Integer index, String expectedMessage) {
        ChangeThemeCommand changeThemeCommand = prepareCommand(index);

        try {
            changeThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ChangeThemeCommand} with parameters {@code integer}.
     */
    private ChangeThemeCommand prepareCommand(Integer integer) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(integer);
        return changeThemeCommand;
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // multiple emails - all email accepted
        Person expectedPersonMultipleEmails = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB, VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleEmails));
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        //missing phone -> accepted
        Person expectedPersonMissingPhone = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(null)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_EMPTY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new AddCommand(expectedPersonMissingPhone));

        //missing phone -> accepted
        Person expectedPersonMissingAddress = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(null).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_EMPTY,
                new AddCommand(expectedPersonMissingAddress));
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_ADD_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid add tag
        assertParseFailure(parser, "1" + INVALID_REM_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid remove tag
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
        // add tags
        userInput = targetIndex.getOneBased() + TAG_ADD_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withToAddTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remove tags
        userInput = targetIndex.getOneBased() + TAG_REM_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withToRemoveTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // clear tags
        userInput = targetIndex.getOneBased() + " " + PREFIX_CLEAR_TAG.getPrefix();
        descriptor = new EditPersonDescriptorBuilder().clearTags(true).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY, VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withToAddTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ArrayList<String> expectedEmail = new ArrayList<>();
        expectedEmail.add(INVALID_EMAIL);
        ParserUtil.parseEmail(expectedEmail);
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        ArrayList<Email> expectedEmail = new ArrayList<>();
        expectedEmail.add(new Email(VALID_EMAIL));
        expectedEmail.add(new Email(VALID_EMAIL_2));
        ArrayList<Email> actualEmail = ParserUtil.parseEmail(Arrays.asList(VALID_EMAIL, VALID_EMAIL_2));

        assertEquals(expectedEmail, actualEmail);
    }
```
