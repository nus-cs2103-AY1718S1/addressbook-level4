package seedu.address.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.FileChooserEvent;
import seedu.address.commons.events.ui.CloseProgressEvent;
import seedu.address.commons.events.ui.EmailRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowProgressEvent;
import seedu.address.commons.events.ui.ShowUrlEvent;
import seedu.address.commons.events.ui.SocialRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.UserName;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    public static final String DEFAULT_DP = "/images/defaultperson.png";
    private static final String ICON = "/images/Icon.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;
    private static final String EMAIL_URI_PREFIX = "mailTo:";
    private static final String EXPORT_FILE_PATH = "./data/";


    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private GroupListPanel groupListPanel;
    private PersonDescription personDescriptionPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private Pane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane personDescriptionPlaceHolder;

    @FXML
    private Pane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane tagListPanelPlaceholder;

    private ProgressWindow pWindow;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personDescriptionPanel = new PersonDescription();
        personDescriptionPlaceHolder.getChildren().add(personDescriptionPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        groupListPanel = new GroupListPanel(logic.getAllTags());
        tagListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    //@@author JunQuann
    private String getDisplayPicPath() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter ("PICTURE files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return DEFAULT_DP;
        }
    }

    @Subscribe
    private void handleFileChooserEvent(FileChooserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Select your image from the file chooser"));
        String currentImgPath = getDisplayPicPath();
        event.setImgPath(currentImgPath);
    }
    //@@author

    /**
     * Opens the help window.
     */
    @FXML
    private void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }
    //@@author conantteo
    /**
     * This method will call the user's default mail application and set the recipients field with all the
     * email addresses specified by the user.
     * @param allEmailAddresses is a string of all valid email addresses user request to email to.
     * @throws IOException when java Desktop class is not supported in this platform.
     */
    private void handleEmail(String allEmailAddresses) {

        URI mailTo = null;
        try {
            mailTo = new URI(EMAIL_URI_PREFIX + allEmailAddresses);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Checks if Desktop class is supported in the current platform
        if (Desktop.isDesktopSupported()) {
            Desktop userDesktop = Desktop.getDesktop();
            try {
                logger.info("Showing user's default mail client");
                userDesktop.mail(mailTo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //@@author tbhbhbh
    /**
     * This method will use the built-in browser to open the selected index's social media profile (either Twitter
     * or Instagram).
     * @param userName is a UserName of the person
     */
    private void handleSocial(UserName userName, String socialMediaLink) {
        browserPanel.loadPage(socialMediaLink + userName);
    }


    //@@author danielbrzn

    /**
     * Opens the provided URL in the built-in browser
     * @param link is a URL to be opened in the BrowserPanel
     */
    private void handleUrl(String link) {
        browserPanel.loadPage(link);
    }

    /**
     * Opens the progress window.
     */
    @FXML
    private void handleProgress(ReadOnlyDoubleProperty progress) {
        Platform.runLater(() -> {
            pWindow = new ProgressWindow(progress);
            pWindow.show();
        });

    }

    /**
     * Closes the progress window.
     */
    @FXML
    private void handleCloseProgress() {
        pWindow.getDialogStage().close();
    }

    //@@author
    void show() {
        primaryStage.show();
    }
    //@@author conantteo
    /**
     * Opens a file directory which shows the folder where contacts.vcf file is located.
     * The file directory is is guaranteed to exist before showing.
     * @throws IOException when java Desktop class is not supported in this platform.
     */
    private void handleExport() {
        File file = new File(EXPORT_FILE_PATH);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop userDesktop = Desktop.getDesktop();
                logger.info("Showing user's folder for contacts.vcf");
                userDesktop.open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //@@author
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
    //@@author conantteo
    @Subscribe
    private void handleEmailRequestEvent(EmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmail(event.getAllEmailAddresses());
    }

    //@@author danielbrzn

    @Subscribe
    private void handleShowUrlEvent(ShowUrlEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleUrl(event.getUrl());
    }

    @Subscribe
    private void handleSocialEvent(SocialRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSocial(event.getUserName(), event.getSocialMediaLink());
    }

    @Subscribe
    private void handleShowProgressEvent(ShowProgressEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleProgress(event.getProgress());

    }

    @Subscribe
    private void handleCloseProgressEvent(CloseProgressEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleCloseProgress();

    }

    //@@author conantteo
    @Subscribe
    private void handleExportRequestEvent(ExportRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleExport();
    }
}
