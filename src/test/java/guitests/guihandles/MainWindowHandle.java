package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final SearchBoxHandle searchField;
    private final SortMenuHandle sortMenu;
    private final HelpOverlayHandle helpOverlay;

    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        searchField = new SearchBoxHandle(getChildNode(SearchBoxHandle.SEARCH_FIELD_ID));
        sortMenu = new SortMenuHandle(getChildNode(SortMenuHandle.SORT_MENU_ID));
        helpOverlay = new HelpOverlayHandle(getChildNode(HelpOverlayHandle.HELP_OVERLAY_ID));
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

    public SearchBoxHandle getSearchField() {
        return searchField;
    }

    public SortMenuHandle getSortMenu() {
        return sortMenu;
    }

    public HelpOverlayHandle getHelpOverlay() {
        return helpOverlay;
    }
}
