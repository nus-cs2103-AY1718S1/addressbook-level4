package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GraphPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class GraphPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private GraphPanel graphPanel;
    private GraphPanelHandle graphPanelHandle;

    @Before
    public void setUp() {
        try {
            guiRobot.interact(() -> graphPanel = new GraphPanel(TYPICAL_PERSONS));
            uiPartRule.setUiPart(graphPanel);
            graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() throws Exception {
        // select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));;
        // select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
    }
}
