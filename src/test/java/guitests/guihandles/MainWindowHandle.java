package guitests.guihandles;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Password;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private static final String ADMIN_USERNAME = "loanShark97";
    private static final String ADMIN_PASSWORD = "hitMeUp123";
    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private InfoPanelHandle browserPanel;
    private ModelManager modelManager;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    public MainWindowHandle(Stage stage) {
        super(stage);

        modelManager = new ModelManager();
        try {
            login();
            Platform.runLater(() -> {
                personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
                resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
                commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
                mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
                browserPanel = new InfoPanelHandle(getChildNode(InfoPanelHandle.BROWSER_ID));
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
    public void login() throws IllegalValueException, CommandException {
        Username username = new Username(ADMIN_USERNAME);
        Password password = new Password(ADMIN_PASSWORD);
        LoginCommand loginCommand = new LoginCommand(username, password);
        loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());
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

    public InfoPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
