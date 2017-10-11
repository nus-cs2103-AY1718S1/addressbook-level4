package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.text.Text;

//@@author jelneo
/**
 * A handler for the {@code StartUpPanel} of the UI
 */
public class StartUpPanelHandle extends NodeHandle<Node> {

    public static final String WELCOME_MESSAGE_ID = "#welcomeMessage";
    public static final String LOGIN_INSTRUCTION_ID = "#loginInstruction";
    public static final String LOGIN_FORMAT_ID = "#loginFormat";

    private Text welcomeMessage;
    private Text loginInstruction;
    private Text loginFormat;

    public StartUpPanelHandle(Node startUpPanelNode) {
        super(startUpPanelNode);

        welcomeMessage = getChildNode(WELCOME_MESSAGE_ID);
        loginInstruction = getChildNode(LOGIN_INSTRUCTION_ID);
        loginFormat = getChildNode(LOGIN_FORMAT_ID);
    }

    /**
     * Returns the text in the start up panel.
     */
    public String getText() {
        String text = "" + welcomeMessage.getText() + loginInstruction.getText() + loginFormat.getText();
        return text;
    }

}
