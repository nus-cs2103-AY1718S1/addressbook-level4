package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonDetailPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonDetailPanelTest extends GuiUnitTest {

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private PersonDetailPanel personDetailPanel;
    private PersonDetailPanelHandle personDetailPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> personDetailPanel = new PersonDetailPanel());
        uiPartRule.setUiPart(personDetailPanel);

        personDetailPanelHandle = new PersonDetailPanelHandle(getChildNode(personDetailPanel.getRoot(),
                PersonDetailPanelHandle.PERSON_DETAIL_ID));
    }

    @Test
    public void display() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        postNow(selectionChangedEventStub);
        assertPanelDisplaysPerson(ALICE, personDetailPanelHandle);

        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1));
        postNow(selectionChangedEventStub);
        assertPanelDisplaysPerson(BOB, personDetailPanelHandle);
    }

    /**
     * Asserts that {@code actualPerson} displays the details of {@code expectedPerson}.
     */
    private static void assertPanelDisplaysPerson(ReadOnlyPerson expectedPerson, PersonDetailPanelHandle actualPerson) {
        assertEquals(expectedPerson.getName().toString(), actualPerson.getName());
        assertEquals(expectedPerson.getPhone().toString(), actualPerson.getPhone());
        assertEquals(expectedPerson.getAddress().toString(), actualPerson.getAddress());
        assertEquals(expectedPerson.getEmail().toString(), actualPerson.getEmail());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualPerson.getTags());
    }
}
