package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class CommandHistoryTest {
    private CommandHistory history;

    @Before
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Bob";

        history.add(validCommand);
        history.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), history.getHistory());
    }

    //@@author Xenonym
    @Test
    public void clear() {
        history.add("list");
        history.add("clear");
        history.clear();
        assertEquals(Collections.EMPTY_LIST, history.getHistory());
    }
    //@@author
}
