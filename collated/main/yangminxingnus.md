# yangminxingnus
###### /java/seedu/address/ui/LoginPage.java
``` java
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AccountsStorage;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * The login page. Users need to key in their username and password to login the MainWindow.
 */
public class LoginPage extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "LoginPage.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private Stage primaryStage;
    private MainWindow mainWindow;

    private Config config;
    private StorageManager storage;
    private UserPrefs prefs;
    private Logic logic;
    private Model model;
    private AccountsStorage accPrefs;
    private UiManager uiManager;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public LoginPage(Stage primaryStage, Config config, StorageManager storage, UserPrefs prefs,
                     Logic logic, AccountsStorage accPrefs, UiManager uiManager) {
        super(FXML);
        this.logic = logic;
        // Set dependencies
        this.primaryStage = primaryStage;
        this.config = config;
        this.prefs = prefs;
        this.accPrefs = accPrefs;
        this.storage = storage;
        this.uiManager = uiManager;
        uiManager.setLoginPage(this);

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        initTheme();
        registerAsAnEventHandler(this);
    }

    private void initTheme() {
        Theme.changeTheme(primaryStage, Theme.getCurrentTheme());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public MainWindow getMainWindow() { return mainWindow; }

    /**
     * Method for handle login event
     */
    @FXML
    private void handleLoginEvent() throws IOException {
        logger.info("Trying to login");
        String uname = username.getText();
        String pword = password.getText();
        if (checkValid(uname, pword)) {

            String path = "data/" + uname + "addressbook.xml";

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
            AddressBookStorage addressBookStorage = new XmlAddressBookStorage(path);

            //storage.setUserPrefsStorage(userPrefsStorage);
            prefs.setAddressBookFilePath(path);
            storage.setAddressBookStorage(addressBookStorage);

            model = initModelManager(storage, prefs);
            logic = new LogicManager(model);

            mainWindow = new MainWindow(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
            uiManager.setMainWindow(mainWindow);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Incorrect username or password.");
            alert.setContentText("Your username or password is not correct, please try again.");
            alert.showAndWait();
            logger.info("Wrong name or password!");
        }
    }

    /**
     * Handles the register event.
     */
    @FXML
    private void handleRegisterEvent() {
        logger.info("Trying to register");
        RegisterPage registerPage = new RegisterPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
        this.hide();
        registerPage.show();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleLoginEvent();
        }
    }

    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), FontSize.getCurrentFontSizeLabel(),
                Theme.getCurrentTheme());
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        FontSize.setCurrentFontSizeLabel(prefs.getGuiSettings().getFontSize());
        Theme.setCurrentTheme(prefs.getGuiSettings().getTheme());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    void hide() {
        primaryStage.hide();
    }

    /**
     * release the resources
     */
    void releaseResources() {
        if (mainWindow != null) {
            mainWindow.logout();
            mainWindow.getBrowserPanel().freeResources();
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     *
     * @param username
     * @param password
     * @return validity of account
     */
    private boolean checkValid(String username, String password) {
        if (accPrefs.getHm().get(username) != null) {
            return accPrefs.getHm().get(username).equals(password);
        } else {
            return false;
        }
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
        return new ModelManager(initialData, userPrefs);
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String newFontSize) {
        String fxFormatFontSize = FontSize.getAssociateFxFontSizeString(newFontSize);
        username.setStyle(fxFormatFontSize);
        password.setStyle(fxFormatFontSize);
    }
}
```
###### /resources/view/RegisterPage.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox minHeight="400.0" minWidth="800.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <StackPane styleClass="pane-with-border" VBox.vgrow="ALWAYS">
      <children>
         <TextField fx:id="username" alignment="CENTER" onKeyPressed="#handleKeyPress" promptText="username" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets bottom="140.0" left="300.0" right="300.0" />
            </StackPane.margin>
         </TextField>
         <PasswordField fx:id="password" alignment="CENTER" onKeyPressed="#handleKeyPress" prefHeight="33.0" prefWidth="300.0" promptText="password" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets bottom="20.0" left="300.0" right="300.0" />
            </StackPane.margin>
         </PasswordField>
         <PasswordField fx:id="password1" alignment="CENTER" layoutX="311.0" layoutY="127.0" onKeyPressed="#handleKeyPress" prefHeight="33.0" prefWidth="300.0" promptText="reenter password" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets bottom="-100.0" left="300.0" right="300.0" />
            </StackPane.margin>
         </PasswordField>
      </children>
   </StackPane>
   <StackPane styleClass="pane-with-border" VBox.vgrow="ALWAYS">
      <children>
         <Button fx:id="registerButton" alignment="CENTER" layoutX="344.0" layoutY="60.0" mnemonicParsing="false" onMouseClicked="#handleRegisterEvent" prefHeight="70.0" prefWidth="143.0" text="Register" textAlignment="CENTER" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets left="-200.0" />
            </StackPane.margin>
         </Button>
         <Button fx:id="backButton" alignment="CENTER" layoutX="390.0" layoutY="55.0" mnemonicParsing="false" onMouseClicked="#handleBackEvent" prefHeight="70.0" prefWidth="143.0" text="Back" textAlignment="CENTER" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets right="-200.0" />
            </StackPane.margin>
         </Button>
      </children>
   </StackPane>
   <stylesheets>
      <URL value="@Extensions.css" />
   </stylesheets>
</VBox>
```
