package guitests.guihandles;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Password;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final String admin_username;
    private final String admin_password;
    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;
    private ModelManager modelManager;

    public MainWindowHandle(Stage stage) {
        super(stage);

        modelManager = new ModelManager();
        admin_username = modelManager.getUsernameFromUserPref();
        admin_password = modelManager.getPasswordFromUserPref();
        try {
            simulateLogin();
            Platform.runLater(() -> {
                personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
                resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
                commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
                mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
                browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
                statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            });
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    //@@author jelneo
    /**
     * Logs into admin user account so that other GUI tests can test the main GUIs in the address book
     */
    public void simulateLogin() throws IllegalValueException, CommandException {
        Username username = new Username(admin_username);
        Password password = new Password(admin_password);
        LoginCommand loginCommand = new LoginCommand(username, password);
        loginCommand.setData(modelManager, new CommandHistory(), new UndoRedoStack());
        loginCommand.execute();
    }
    //@@author

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
