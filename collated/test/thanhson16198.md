# thanhson16198
###### \java\guitests\GuiRobot.java
``` java
    private static final int DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS = 500000;    //#Default: 5000
```
###### \java\seedu\address\logic\commands\GotoCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPlaceOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class GotoCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_goto_success() throws CommandException {
        showFirstPlaceOnly(model);

        assertExecutionFailure(INDEX_SECOND_PLACE, "The place index provided is invalid");
    }

    /**
     * Executes a {@code GotoCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        GotoCommand gotoCommand = prepareCommand(index);

        try {
            CommandResult commandResult = gotoCommand.execute();
            assertEquals(String.format(GotoCommand.MESSAGE_GOTO_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code GotoCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        GotoCommand gotoCommand = prepareCommand(index);

        try {
            gotoCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    private GotoCommand prepareCommand(Index index) {
        GotoCommand gotoCommand = new GotoCommand(index);
        gotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return gotoCommand;
    }
}
```
###### \java\seedu\address\logic\commands\WeatherCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.WeatherCommand.SHOWING_WEATHER_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowWeatherRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class WeatherCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_weather_success() {
        CommandResult result = new WeatherCommand().execute();
        assertEquals(SHOWING_WEATHER_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowWeatherRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
        URL expectedPlaceUrl;
        if (ALICE.getWebsite().toString().contains("www.-.com")) {
            expectedPlaceUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                    + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);
        } else {
            expectedPlaceUrl = new URL(ALICE.getWebsite().toString().replaceAll(" ", "+"));
        }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
        try {
            /*expectedUrl = new URL(GOOGLE_SEARCH_URL_PREFIX + selectedCardName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX); */
            if (selectedCardWebsite.contains("www.-.com")) {
                expectedUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                        + getPlaceListPanel().getHandleToSelectedCard().getName().replaceAll(" ", "+")
                        + GOOGLE_SEARCH_URL_SUFFIX);
            } else {
                expectedUrl = new URL(selectedCardWebsite.replaceAll(" ", "+"));
            }
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
```
