package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SmsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

//@@author zameschua
/**
 * GUI Tests for {@link SmsPanel}
 */
public class SmsPanelTest extends GuiUnitTest  {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private SmsPanel smsPanel;
    private SmsPanelHandle smsPanelHandle;
    private String expectedPhoneNumbers;
    private ArrayList<String> phoneNumbers;

    @Before
    public void setUp() {
        StringBuilder expectedPhoneNumbersBuilder = new StringBuilder();
        phoneNumbers = new ArrayList<String>();
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            phoneNumbers.add(TYPICAL_PERSONS.get(i).getPhone().toString());
            expectedPhoneNumbersBuilder.append(TYPICAL_PERSONS.get(i).getPhone().toString()).append(";");
        }
        expectedPhoneNumbers = expectedPhoneNumbersBuilder.toString();
        guiRobot.interact(() -> smsPanel = new SmsPanel(phoneNumbers));
        uiPartRule.setUiPart(smsPanel);

        smsPanelHandle = new SmsPanelHandle(smsPanel.getRoot());
    }

    @Test
    public void execute_smsPanelGui_phoneNumbersAddedCorrectly() {
        //check that the phone numbers get added to "to:" textbox correctly
        assertEquals(expectedPhoneNumbers, smsPanelHandle.getRecipientsText());
        //check that the Message box is empty
        assertEquals("", smsPanelHandle.getMessageText());
    }
}
