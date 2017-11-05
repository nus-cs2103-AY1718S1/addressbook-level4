# caoliangnus
###### /java/seedu/address/logic/commands/ColorKeywordCommandTest.java
``` java
public class ColorKeywordCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_enableColorCommand_success() {
        CommandResult result = new ColorKeywordCommand("enable").execute();
        assertEquals(ENABLE_COLOR + MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ColorKeywordEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_disableColorCommand_success() {
        CommandResult result = new ColorKeywordCommand("disable").execute();
        assertEquals(DISABLE_COLOR + MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ColorKeywordEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

}
```
###### /java/seedu/address/logic/parser/ColorKeywordCommandParserTest.java
``` java
public class ColorKeywordCommandParserTest {
    private ColorKeywordCommandParser parser = new ColorKeywordCommandParser();

    @Test
    public void parse_validArgs_returnsColorCommand() {
        assertParseSuccess(parser, "enable", new ColorKeywordCommand("enable"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "enabled",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColorKeywordCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/lesson/ClassTypeTest.java
``` java
public class ClassTypeTest {

    @Test
    public void isValidClassType() {
        // invalid addresses
        assertFalse(ClassType.isValidClassType("")); // empty string
        assertFalse(ClassType.isValidClassType(" ")); // spaces only
        assertFalse(ClassType.isValidClassType("Lecture")); // spell out 'lecture'
        assertFalse(ClassType.isValidClassType("Tutorial")); // spell out 'Tutorial'


        // valid addresses
        assertTrue(ClassType.isValidClassType("lec"));
        assertTrue(ClassType.isValidClassType("Lec")); // One capital character
        assertTrue(ClassType.isValidClassType("LEc")); // Two capital characters
        assertTrue(ClassType.isValidClassType("LEC")); // Three capital characters

        assertTrue(ClassType.isValidClassType("tut"));
        assertTrue(ClassType.isValidClassType("Tut")); // One capital character
        assertTrue(ClassType.isValidClassType("TUt")); // Two capital characters
        assertTrue(ClassType.isValidClassType("TUT")); // Three capital characters
    }
}
```
###### /java/seedu/address/model/lesson/CodeTest.java
``` java
public class CodeTest {
    @Test
    public void isValidCode() {
        // invalid name
        assertFalse(Code.isValidCode("")); // empty string
        assertFalse(Code.isValidCode(" ")); // spaces only
        assertFalse(Code.isValidCode("C2101")); // contains only 1 letter
        assertFalse(Code.isValidCode("CS")); // no digit
        assertFalse(Code.isValidCode("CS*")); // contains non-alphanumeric characters
        assertFalse(Code.isValidCode("CS221")); // contains only 3 digits
        assertFalse(Code.isValidCode("2101")); // contains only digit
        assertFalse(Code.isValidCode("2101")); // contains only digit
        assertFalse(Code.isValidCode("GEQR221")); // contains 4 letters

        // valid name
        assertTrue(Code.isValidCode("CS2101")); // 2 letters and 4 digits
        assertTrue(Code.isValidCode("cs2101")); // all small letter for

        assertTrue(Code.isValidCode("MA1101R")); // 2 letters, 4 digits and 1 letter
        assertTrue(Code.isValidCode("ma1101R")); // last letter is capital
        assertTrue(Code.isValidCode("ma1101r")); // all small letter
        assertTrue(Code.isValidCode("Ma1101r")); // first letter is capital

        assertTrue(Code.isValidCode("GEQ1000")); // 3 letters and 4 digits
        assertTrue(Code.isValidCode("geq1000")); // all small letter for
    }
}
```
###### /java/seedu/address/model/lesson/GroupTest.java
``` java
public class GroupTest {

    @Test
    public void isValidGroup() {
        // invalid phone numbers
        assertFalse(Group.isValidGroup("")); // empty string
        assertFalse(Group.isValidGroup(" ")); // spaces only
        assertFalse(Group.isValidGroup("phone")); // non-numeric
        assertFalse(Group.isValidGroup("9011p041")); // alphabets within digits
        assertFalse(Group.isValidGroup("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Group.isValidGroup("9")); // exactly 1 numbers
        assertTrue(Group.isValidGroup("93121534")); //more than 1 number
        assertTrue(Group.isValidGroup("124293842033123")); // long phone numbers
    }
}
```
###### /java/seedu/address/model/lesson/LocationTest.java
``` java
public class LocationTest {
    @Test
    public void isValidLocation() {
        // invalid addresses
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid addresses
        assertTrue(Location.isValidLocation("COM2 02-03"));
        assertTrue(Location.isValidLocation("LT29")); // No space
        assertTrue(Location.isValidLocation("NUS SOC COM2 02-05")); // long address
    }
}
```
###### /java/seedu/address/model/lesson/TimeSlotTest.java
``` java
public class TimeSlotTest {
    @Test
    public void isValidTimeSlot() {
        // invalid phone numbers
        assertFalse(TimeSlot.isValidTimeSLot("")); // empty string
        assertFalse(TimeSlot.isValidTimeSLot(" ")); // spaces only
        assertFalse(TimeSlot.isValidTimeSLot("FRI")); // no '['
        assertFalse(TimeSlot.isValidTimeSLot("FRI[]")); // no start time and end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1000]")); // no end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[10001200]")); // no '-'
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1200-1000]")); // start time less than end time

        // valid phone numbers
        assertTrue(TimeSlot.isValidTimeSLot("FRI[1000-1200]")); // Must follow this format
    }
}
```
###### /java/systemtests/ColorEnableSystemTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.address.logic.commands.ColorKeywordCommand;
import seedu.address.model.Model;

public class ColorEnableSystemTest extends AddressBookSystemTest {
    @Test
    public void colorEnable() {
        final Model defaultModel = getModel();

        /* Case: enable highlighting feature with leading spaces and trailing space
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " enable   ");

        /* Case: disable highlighting feature with leading spaces and trailing space
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " disable   ");


        /* Case: mixed case command word -> rejected */
        assertCommandFailure("EnaBle", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Wrong command word -> rejected */
        assertCommandFailure("enabledisable", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ColorKeywordCommand#MESSAGE_SUCCESS} and the model related components equal to an
     * empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        String text;
        if (command.contains("enable")) {
            text = ColorKeywordCommand.ENABLE_COLOR + ColorKeywordCommand.MESSAGE_SUCCESS;
        } else {
            text = ColorKeywordCommand.DISABLE_COLOR + ColorKeywordCommand.MESSAGE_SUCCESS;
        }

        assertCommandSuccess(command, text, getModel());
        assertStatusBarUnchanged();


    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ColorEnableSystemTest # assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
