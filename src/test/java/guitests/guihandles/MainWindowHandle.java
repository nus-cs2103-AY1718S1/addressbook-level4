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

    private final String adminUsername;
    private final String adminPassword;
    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private InfoPanelHandle infoPanel;
    private ModelManager modelManager;

    public MainWindowHandle(Stage stage) {
        super(stage);

        modelManager = new ModelManager();
        adminUsername = modelManager.getUsernameFromUserPref();
        adminPassword = modelManager.getPasswordFromUserPref();
        try {
            simulateLogin();
            Platform.runLater(() -> {
                personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
                resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
                commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
                mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
                infoPanel = new InfoPanelHandle(getChildNode(InfoPanelHandle.INFO_PANEL_ID));
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
        Username username = new Username(adminUsername);
        Password password = new Password(adminPassword);
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

    public InfoPanelHandle getInfoPanel() {
        return infoPanel;
    }
}
