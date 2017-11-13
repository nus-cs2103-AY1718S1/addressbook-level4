package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EmailPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seedu.address.model.person.ReadOnlyPerson;

//@@author ReneeSeet

public class EmailPanelTest extends GuiUnitTest  {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private static final String DELIMITER  = ";";
    private static final String STUB_MESSAGE = "This is a test email";
    private static final String STUB_SUBJECT = "test";
    private EmailPanel emailPanel;
    private EmailPanelHandle emailPanelHandle;
    private String expectedEmail;
    private ArrayList<String> emails;

    @Before
    public void setUp() {
        StringBuilder expectedEmailbuilder = new StringBuilder();
        emails = new ArrayList<String>();
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            emails.add(TYPICAL_PERSONS.get(i).getEmail().toString());
            expectedEmailbuilder.append(TYPICAL_PERSONS.get(i).getEmail().toString()).append(DELIMITER);
        }
        expectedEmail = expectedEmailbuilder.toString();
        guiRobot.interact(() -> emailPanel = new EmailPanel(emails));
        uiPartRule.setUiPart(emailPanel);

        emailPanelHandle = new EmailPanelHandle(emailPanel.getRoot());
    }

    @Test
    public void display() {
        //check that the emails get added to "to:" textbox correctly
        assertEquals(expectedEmail, emailPanelHandle.getRecipientsText());
        //check that the subject box is empty
        assertEquals("", emailPanelHandle.getSubjectText());
        //check that the Message box is
        assertEquals("", emailPanelHandle.getMessageText());
    }

    /**
     * Note the verification of sending emails are done via exploratory testing
     * It is difficult for test to authenticate google account and send out an actual email via Gmail's API
     * In the testCase below sendButton is not clicked but if google account has been pre autheticated,
     * it can be use.
     */

    @Test
    public void send_email() {
        //fill up subject box
        TextField sub =  emailPanelHandle.getSubjectTextBox();
        sub.setText(STUB_SUBJECT);
        //fill up message box
        TextArea mess =  emailPanelHandle.getMessageTextBox();
        mess.setText(STUB_MESSAGE);
        //click send button
        Button sendbutton = emailPanelHandle.getSendButton();
        //sendbutton.fire();
    }
}
