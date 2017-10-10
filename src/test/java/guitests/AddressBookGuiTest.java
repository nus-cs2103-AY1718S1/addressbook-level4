package guitests;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Password;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

/**
 * A GUI Test class for AddressBook.
 */
public abstract class AddressBookGuiTest {

    private static final String ADMIN_USERNAME = "loanShark97";
    private static final String ADMIN_PASSWORD = "hitMeUp123";
    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    protected GuiRobot guiRobot = new GuiRobot();

    protected Stage stage;

    protected MainWindowHandle mainWindowHandle;
    private static ModelManager modelManager;
    private static Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    private static void login() {
        modelManager = new ModelManager();
        try {
            Username username = new Username(ADMIN_USERNAME);
            Password password = new Password(ADMIN_PASSWORD);
            LoginCommand loginCommand = new LoginCommand(username, password);
            loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());
            loginCommand.execute();
        } catch(IllegalValueException ive) {
            ive.printStackTrace();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @BeforeClass
    public static void setupOnce() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        login();
        FxToolkit.setupStage((stage) -> {
            this.stage = stage;
        });
        FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();

        mainWindowHandle = new MainWindowHandle(stage);
        mainWindowHandle.focus();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected AddressBook getInitialData() {
        return TypicalPersons.getTypicalAddressBook();
    }

    protected CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    protected PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    protected MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    protected BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    protected StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    protected ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Runs {@code command} in the application's {@code CommandBox}.
     * @return true if the command was executed successfully.
     */
    protected boolean runCommand(String command) {
        return mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws Exception {
        EventsCenter.clearSubscribers();
        FxToolkit.cleanupStages();
    }

}
