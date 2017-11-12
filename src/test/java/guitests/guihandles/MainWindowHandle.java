package guitests.guihandles;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.ListObserver;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Password;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    public static final String TEST_USERNAME = "TESTloanShark97";
    public static final String TEST_PASSWORD = "TESThitMeUp123";
    private static ModelManager modelManager;
    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private InfoPanelHandle infoPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        UserPrefs testUserPrefs = new UserPrefs();
        testUserPrefs.setAdminUsername(TEST_USERNAME);
        testUserPrefs.setAdminPassword(TEST_PASSWORD);
        modelManager = new ModelManager(getTypicalAddressBook(), testUserPrefs);
        ListObserver.init(modelManager);
        simulateLogin();
        Platform.runLater(() -> {
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            infoPanel = new InfoPanelHandle(getChildNode(InfoPanelHandle.INFO_PANEL_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        });
    }

    //@@author jelneo
    /**
     * Logs into admin user account so that other GUI tests can test the main GUIs in the address book
     */
    public static void simulateLogin() {
        try {
            Username username = new Username(TEST_USERNAME);
            Password password = new Password(TEST_PASSWORD);
            LoginCommand loginCommand = new LoginCommand(username, password);
            loginCommand.setData(modelManager, new CommandHistory(), new UndoRedoStack());
            loginCommand.execute();
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }
    //@@author

    /**
     * Retrieves new infopanel and personlistpanel when list changes.
     */
    public void updateChangeInList() {
        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        infoPanel = new InfoPanelHandle(getChildNode(InfoPanelHandle.INFO_PANEL_ID));
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

    public InfoPanelHandle getInfoPanel() {
        return infoPanel;
    }
}
