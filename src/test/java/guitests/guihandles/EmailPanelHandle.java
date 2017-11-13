package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

//@@author ReneeSeet
/**
 * A handler for the {@code MassEmailPanel} of the UI.
 */

public class EmailPanelHandle extends NodeHandle<Node>  {

    public static final String EMAIL_ID = "#emailpanel";
    public static final String SUBJECT_TEXTBOX_ID = "emailSubjectBox";
    public static final String TO_TEXTBOX_ID = "recipientsBox";
    public static final String SEND_BUTTON_ID = "sendButton";
    public static final String MESSAGE_TEXTBOX_ID = "emailMessage";

    private Node recipientBox;
    private Node subjectBox;
    private Node sendButton;
    private Node emailMessage;

    public EmailPanelHandle(Node emailPanelNode) {
        super(emailPanelNode);
        Pane pane = getChildNode(EMAIL_ID);
        ObservableList<Node> listNode = pane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getId() != null) {
                if (listNode.get(i).getId().equals(TO_TEXTBOX_ID)) {
                    recipientBox = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SEND_BUTTON_ID)) {
                    sendButton = listNode.get(i);
                } else if (listNode.get(i).getId().equals(MESSAGE_TEXTBOX_ID)) {
                    emailMessage = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SUBJECT_TEXTBOX_ID)) {
                    subjectBox = listNode.get(i);
                }
            }
        }
    }

    public String getRecipientsText() {
        TextField f = (TextField) recipientBox;
        return f.getText();
    }

    public String getSubjectText() {
        TextField f = (TextField) subjectBox;
        return f.getText();
    }

    public String getMessageText() {
        TextArea f = (TextArea) emailMessage;
        return f.getText();
    }

    public TextField getSubjectTextBox() {
        TextField f = (TextField) subjectBox;
        return f;
    }

    public TextArea getMessageTextBox() {
        TextArea f = (TextArea) emailMessage;
        return f;
    }

    public Button getSendButton() {
        Button f = (Button) sendButton;
        return f;
    }

}
