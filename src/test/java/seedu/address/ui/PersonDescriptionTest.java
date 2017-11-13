package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ZEPHYR;
import static seedu.address.ui.testutil.GuiTestAssert.assertDescriptionDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonDescriptionHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//author JunQuann
public class PersonDescriptionTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private PersonDescription personDescription;
    private PersonDescriptionHandle personDescriptionHandle;
    private Person testPerson;

    @Before
    public void setUp() throws Exception {
        testPerson = new PersonBuilder().build();
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(testPerson, 0));

        guiRobot.interact(() -> personDescription = new PersonDescription());
        uiPartRule.setUiPart(personDescription);

        personDescriptionHandle = new PersonDescriptionHandle(personDescription.getRoot());
    }

    @Test
    public void display() throws Exception {
        //selects a person
        testPerson.setName(ZEPHYR.getName());
        testPerson.setPhone(ZEPHYR.getPhone());
        testPerson.setAddress(ZEPHYR.getAddress());
        testPerson.setInstagramName(ZEPHYR.getInstagramName());
        testPerson.setTwitterName(ZEPHYR.getTwitterName());
        postNow(selectionChangedEventStub);

        assertDescriptionDisplaysPerson(testPerson, personDescriptionHandle);
    }

    @Test
    public void displayPicTest() {
        testPerson.setDisplayPic(ZEPHYR.getDisplayPic());
        postNow(selectionChangedEventStub);

        assertDescriptionDisplaysPerson(testPerson, personDescriptionHandle);
    }
}
