package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GraphDisplayHandle;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.NewGraphDisplayEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.ReadOnlyPerson;

//@@author joanneong
public class GraphDisplayTest extends GuiUnitTest {

    private static final SingleGraph NEW_GRAPH_STUB = new SingleGraph("");
    private static final NewGraphDisplayEvent NEW_GRAPH_INITIALISED_STUB =
        new NewGraphDisplayEvent(NEW_GRAPH_STUB, "Stub");

    private GraphDisplayHandle graphDisplayHandle;

    @Before
    public void setUp() {
        GraphDisplay graphDisplay = new GraphDisplay(new LogicStub());
        uiPartRule.setUiPart(graphDisplay);

        graphDisplayHandle = new GraphDisplayHandle(getChildNode(graphDisplay.getRoot(),
            GraphDisplayHandle.DISPLAY_ID));
    }

    @Test
    public void display() {
        // by default, the SwingNode should be be empty
        guiRobot.pauseForHuman();
        assertEquals(null, graphDisplayHandle.getContent());

        // TODO: add tests to check SwingNode content
    }

    /**
     * A default logic stub that have all of the methods failing.
     */
    private class LogicStub implements Logic {
        @Override
        public CommandResult execute(String commandText) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ListElementPointer getHistorySnapshot() {
            fail("This method should not be called.");
            return null;
        }
    }
}
