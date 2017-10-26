package guitests.guihandles;

import javafx.stage.Stage;
import seedu.address.ui.InsuranceListPanel;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final InsuranceListPanelHandle insuranceListPanelHandle;

    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        insuranceListPanelHandle = new InsuranceListPanelHandle(
                getChildNode(InsuranceListPanelHandle.INSURANCE_LIST_VIEW_ID));
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

    public ProfilePanelHandle getProfilePanelHandle() {
        // ProfilePanel shares holder ID with insurance profile and doesn't exist at initial state
        return null;
    }

    public InsuranceListPanelHandle getInsuranceListPanelHandle() {
        return insuranceListPanelHandle;
    }
}
