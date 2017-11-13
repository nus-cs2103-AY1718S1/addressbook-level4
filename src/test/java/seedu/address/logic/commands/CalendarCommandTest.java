package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author yilun-zhu
public class CalendarCommandTest {
    @Test
    public void executeCommandSuccessful() {

        CommandResult commandResult = new CalendarCommand().execute();

        assertEquals(String.format(CalendarCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }
}
