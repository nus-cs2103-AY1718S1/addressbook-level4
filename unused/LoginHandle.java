package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import  javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

//@@author ReneeSeet
/**
 * This is LoginHandle for the login page.
 * It will fill up the email textfield and password textfield and press ENTER.
 * Reason why this was unused: I dedcided to remove the login page as it was not good
 * to store passsword and username and it caused coupling with the other features.
 */
/**
 * A handle to the {@code LoginPage} in the GUI.
 */

public class LoginHandle extends NodeHandle<Node> {

    public static final String LOGIN_DISPLAY_ID = "#LoginPanel";
    private static final String DEFAULT_EMAIL = "test@gmail.com";
    private static final String DEFAULT_PASSWORD = "password";

    public LoginHandle(Node loginPanelNode) {
        super(loginPanelNode);
        TextField s = getChildNode("#emailBox");
        s.setText(DEFAULT_EMAIL);
        PasswordField p = getChildNode("#passwordBox");
        p.setText(DEFAULT_PASSWORD);
        guiRobot.type(KeyCode.ENTER);
    }
}
