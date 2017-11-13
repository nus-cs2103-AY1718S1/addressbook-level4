package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EmailMessageDisplayHandle;
import guitests.guihandles.EmailRecipientsDisplayHandle;
import guitests.guihandles.EmailSubjectDisplayHandle;
import seedu.address.commons.events.ui.EmailDraftChangedEvent;
import seedu.address.email.message.MessageDraft;

//@@author awarenessxz
/**
 * UI Test for Message Display
 */
public class MessageDisplayTest extends GuiUnitTest {
    private static final EmailDraftChangedEvent NEW_RESULT_EVENT_STUB =
            new EmailDraftChangedEvent(new MessageDraft("message", "subject"));

    private EmailMessageDisplayHandle messageDisplayHandle;
    private EmailSubjectDisplayHandle subjectDisplayHandle;
    private EmailRecipientsDisplayHandle recipientsDisplayHandle;

    @Before
    public void setUp() {
        MessageDisplay messageDisplay = new MessageDisplay();
        uiPartRule.setUiPart(messageDisplay);

        messageDisplayHandle = new EmailMessageDisplayHandle(getChildNode(messageDisplay.getRoot(),
                EmailMessageDisplayHandle.MESSAGE_DISPLAY_ID));
        subjectDisplayHandle = new EmailSubjectDisplayHandle(getChildNode(messageDisplay.getRoot(),
                EmailSubjectDisplayHandle.SUBJECT_DISPLAY_ID));
        recipientsDisplayHandle = new EmailRecipientsDisplayHandle(getChildNode(messageDisplay.getRoot(),
                EmailRecipientsDisplayHandle.RECIPIENTS_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", messageDisplayHandle.getText());
        assertEquals("", subjectDisplayHandle.getText());
        assertEquals("", recipientsDisplayHandle.getText());

        // new result received
        postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(NEW_RESULT_EVENT_STUB.message.getMessage(), messageDisplayHandle.getText());
        assertEquals(NEW_RESULT_EVENT_STUB.message.getSubject(), subjectDisplayHandle.getText());
        assertEquals(NEW_RESULT_EVENT_STUB.message.getRecipientsEmailtoString(), recipientsDisplayHandle.getText());
    }

}
