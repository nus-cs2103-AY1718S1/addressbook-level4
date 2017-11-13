package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

//@@author awarenessxz
/**
 * A handler for the {@code LeftDisplayPanel} of the UI
 */
public class LeftDisplayPanelHandle extends NodeHandle<TabPane> {
    public static final String LEFT_DISPLAY_ID = "#leftDisplayPanel";
    private final PersonListPanelHandle personListPanel;
    private final EmailRecipientsDisplayHandle emailRecipientsDisplay;
    private final EmailMessageDisplayHandle emailMessageDisplay;
    private final EmailSubjectDisplayHandle emailSubjectDisplay;

    public LeftDisplayPanelHandle(TabPane leftDisplayPanelNode) {
        super(leftDisplayPanelNode);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        emailMessageDisplay = new EmailMessageDisplayHandle(getChildNode(EmailMessageDisplayHandle.MESSAGE_DISPLAY_ID));
        emailRecipientsDisplay = new EmailRecipientsDisplayHandle(
                getChildNode(EmailRecipientsDisplayHandle.RECIPIENTS_DISPLAY_ID));
        emailSubjectDisplay = new EmailSubjectDisplayHandle(getChildNode(EmailSubjectDisplayHandle.SUBJECT_DISPLAY_ID));
    }

    /**
     * Toggle the Tabs
     */
    public void toggle(int index) {
        click();
        guiRobot.interact(() -> getRootNode().getSelectionModel().select(index));
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the text in the result display.
     */
    public int getSelectedTabIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    public ObservableList<Tab> getTabs() {
        return getRootNode().getTabs();
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public EmailMessageDisplayHandle getEmailMessageDisplay() {
        return emailMessageDisplay;
    }

    public EmailRecipientsDisplayHandle getEmailRecipientsDisplay() {
        return emailRecipientsDisplay;
    }

    public EmailSubjectDisplayHandle getEmailSubjectDisplay() {
        return emailSubjectDisplay;
    }
}
