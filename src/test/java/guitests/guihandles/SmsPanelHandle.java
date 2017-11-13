package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

//@@author zameschua
/**
 * A handler for the {@link seedu.address.ui.SmsPanel} of the UI
 */
public class SmsPanelHandle extends NodeHandle<Node>  {

    public static final String SMS_PANEL_ID = "#smsPanel";
    public static final String TO_TEXTBOX_ID = "recipientsBox";
    public static final String MESSAGE_TEXTBOX_ID = "smsMessage";

    private Node recipientBox;
    private Node smsMessage;

    public SmsPanelHandle(Node smsPanelNode) {
        super(smsPanelNode);
        Pane pane = getChildNode(SMS_PANEL_ID);
        ObservableList<Node> listNode = pane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getId() == null) {
                continue;
            }
            switch (listNode.get(i).getId()) {
            case TO_TEXTBOX_ID:
                recipientBox = listNode.get(i);
                break;
            case MESSAGE_TEXTBOX_ID:
                smsMessage = listNode.get(i);
                break;
            default:
                break;
            }
        }
    }

    public String getRecipientsText() {
        TextField f = (TextField) recipientBox;
        return f.getText();
    }

    public String getMessageText() {
        TextArea f = (TextArea) smsMessage;
        return f.getText();
    }
}
