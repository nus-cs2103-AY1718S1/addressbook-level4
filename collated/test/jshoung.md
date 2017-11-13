# jshoung
###### /java/guitests/guihandles/BusWindowHandle.java
``` java

```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void deleteModule(Module module) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/BusCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.BusCommand.SHOWING_BUS_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowBusRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class BusCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_bus_success() {
        CommandResult result = new BusCommand().execute();
        assertEquals(SHOWING_BUS_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowBusRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/logic/commands/MapCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.MapCommand.SHOWING_MAP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowMapRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class MapCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_map_success() {
        CommandResult result = new MapCommand().execute();
        assertEquals(SHOWING_MAP_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowMapRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/logic/commands/SummaryCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SummaryCommand.SHOWING_SUMMARY_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowSummaryRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SummaryCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_summary_success() {
        CommandResult result = new SummaryCommand().execute();
        assertEquals(SHOWING_SUMMARY_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowSummaryRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
    }
}
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = MainApp.class.getResource(FXML_FILE_FOLDER + ADDRESS_PAGE);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
```
###### /java/seedu/address/ui/BusWindowTest.java
``` java

```
