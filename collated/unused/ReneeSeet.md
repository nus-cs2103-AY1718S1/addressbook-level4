# ReneeSeet
###### \AddAttendanceCommand.java
``` java
        JoinDate date = editAttendancePersonDescriptor.getJoinDate();
```
###### \AddAttendanceCommand.java
``` java
        private JoinDate date;
```
###### \AddAttendanceCommand.java
``` java
            this.date = toCopy.date;
```
###### \AddAttendanceCommand.java
``` java
        public void setJoinDate(JoinDate date) {
            this.date = date;
        }

        public JoinDate getJoinDate() {
            return date;
        }

```
###### \LoginHandle.java
``` java
/**
 * A handle to the {@code LoginPage} in the GUI.
 */

public class LoginHandle extends NodeHandle<Node> {

    public static final String LOGIN_DISPLAY_ID = "#LoginPanel";

    public LoginHandle(Node loginPanelNode) {
        super(loginPanelNode);
        TextField s = getChildNode("#emailBox");
        s.setText("test@gmail.com");
        PasswordField p = getChildNode("#passwordBox");
        p.setText("password");
        guiRobot.type(KeyCode.ENTER);
    }
}
```
###### \LoginPanel.fxml
``` fxml

<?import java.net.URL?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<Pane fx:id="LoginPanel" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="605.0" prefWidth="594.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@../src/main/resources/view/Styles.css" />
        <URL value="@../src/main/resources/view/Extensions.css" />
    </stylesheets>
    <children>
        <PasswordField fx:id="passwordBox" layoutX="246.0" layoutY="406.0" onAction="#onEnter" />
        <Text layoutX="158.0" layoutY="427.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Password" />
        <Text layoutX="172.0" layoutY="362.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Email" />
        <Text fx:id="loginText" layoutX="142.0" layoutY="536.0" strokeType="OUTSIDE" strokeWidth="0.0" text="" wrappingWidth="373.0" />
        <TextField fx:id="emailBox" layoutX="246.0" layoutY="341.0" onAction="#onEnter" />
        <Button fx:id="loginButton" layoutX="273.0" layoutY="469.0" mnemonicParsing="false" text="Login" />
        <Text layoutX="246.0" layoutY="260.0" strokeType="OUTSIDE" strokeWidth="0.0" text="CYNC">
            <font>
                <Font size="54.0" />
            </font>
        </Text>
        <Text layoutX="222.0" layoutY="306.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Customer Manager" textAlignment="CENTER" wrappingWidth="186.13476524021826" />
    </children>
</Pane>
```
###### \LoginPanel.java
``` java

/**
 * The Login Panel of the App.
 */
public class LoginPanel extends UiPart<Region> {

    private static final String FXML = "LoginPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginPanel.class);

    @FXML
    private Button loginButton;

    @FXML
    private Text loginText;

    @FXML
    private TextField emailBox;

    @FXML
    private PasswordField passwordBox;

    public LoginPanel() {
        super(FXML);
    }

    /**
    * Catch Enter button
    */

    @FXML
    public void onEnter(ActionEvent ae) {
        if (!emailBox.getText().equals("") && !passwordBox.getText().equals("")) {
            EventsCenter.getInstance().post(new LoginRequestEvent());
        } else {
            loginText.setText("Please enter email and password");
            logger.info("nothing entered");
        }
    }
}
```
###### \LoginRequestEvent.java
``` java

/**
 * Indicates a request for Logging
 */

public class LoginRequestEvent extends BaseEvent {

    public LoginRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window for login.
     */
    void fillLogin() {
        loginPanel = new LoginPanel();
        browserPanel = new BrowserPanel();
        calendarPanel = new CalendarPanel();
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(), logic.getFilteredPersonList().size());
        resultDisplay = new ResultDisplay();
        commandBox = new CommandBox(logic);
        browserPlaceholder.getChildren().add(loginPanel.getRoot());
    }

```
###### \MainWindow.java
``` java
    /**
     * Switch to the Email panel.
     */
    @FXML
    public void handleEmail(ArrayList<String> emails) {
        emailPanel = new EmailPanel(emails);
        browserPlaceholder.getChildren().add(emailPanel.getRoot());
        browserPlaceholder.getChildren().setAll(emailPanel.getRoot());
    }
```
###### \MainWindow.java
``` java
    @Subscribe
    private void handleMassEmailEvent(MassEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmail(event.getEmailList());
    }
```
###### \MainWindowWithLoginHandle.java
``` java
/**
 * Provides a handle to the main menu of the app.
 */

public class MainWindowWithLoginHandle extends StageHandle {

    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;
    private final LoginHandle loginPanel;

    public MainWindowWithLoginHandle(Stage stage) {
        super(stage);
        loginPanel = new LoginHandle(getChildNode(LoginHandle.LOGIN_DISPLAY_ID));
        EventsCenter.getInstance().post(new LoginRequestEvent()); //unlock for system test
        try {
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        } catch (NodeNotFoundException ne) {
            EventsCenter.getInstance().post(new LoginRequestEvent()); //unlock for system test
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        }
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
```
###### \UiManager.java
``` java
    @Subscribe
    private void handleLogin(LoginRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerParts();
    }
```
