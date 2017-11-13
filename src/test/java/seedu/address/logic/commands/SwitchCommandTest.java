//@@author Hailinx
package seedu.address.logic.commands;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class SwitchCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_equals() throws Exception {
        SwitchCommand firstSwitchCommand = new SwitchCommand("1");
        SwitchCommand secondSwitchCommand = new SwitchCommand("2");

        Assert.assertTrue(firstSwitchCommand.equals(firstSwitchCommand));

        Assert.assertFalse(firstSwitchCommand.equals(secondSwitchCommand));

        SwitchCommand firstSwitchCommandCopy = new SwitchCommand("1");

        Assert.assertTrue(firstSwitchCommand.equals(firstSwitchCommandCopy));
    }

    @Test
    public void test_emptyParams_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        new SwitchCommand("");
    }

    @Test
    public void test_notNumberParams_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        new SwitchCommand("a");
    }

    @Test
    public void execute_switch_returnSuccess() throws ParseException, CommandException {
        SwitchCommand switchCommand = new SwitchCommand(String.valueOf(SwitchCommand.SWITCH_TO_TODOLIST));
        String expectedCommandResult =
                String.format(SwitchCommand.MESSAGE_SUCCESS, "Todo list");

        CommandResult result = switchCommand.execute();
        Assert.assertTrue(result.feedbackToUser.equals(expectedCommandResult));

        switchCommand = new SwitchCommand(String.valueOf(SwitchCommand.SWITCH_TO_BROWSER));
        expectedCommandResult =
                String.format(SwitchCommand.MESSAGE_SUCCESS, "browser");

        result = switchCommand.execute();
        Assert.assertTrue(result.feedbackToUser.equals(expectedCommandResult));
    }

    @Test
    public void execute_notDefinedMode_throwsException() throws Exception {
        try {
            new SwitchCommand("0").execute();
            Assert.fail("CommandException is not thrown");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), SwitchCommand.PARSE_EXCEPTION_MESSAGE);
        }

        try {
            new SwitchCommand("3").execute();
            Assert.fail("CommandException is not thrown");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), SwitchCommand.PARSE_EXCEPTION_MESSAGE);
        }

        try {
            new SwitchCommand("-1").execute();
            Assert.fail("CommandException is not thrown");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), SwitchCommand.PARSE_EXCEPTION_MESSAGE);
        }
    }

}
