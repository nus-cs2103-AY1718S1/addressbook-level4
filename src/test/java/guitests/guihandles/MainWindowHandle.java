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
    private final ExtendedPersonCardHandle extendedPersonCard;
    private final GraphPanelHandle graphPanel;
    private final StatisticsPanelHandle statisticsPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        extendedPersonCard = new ExtendedPersonCardHandle(getChildNode(ExtendedPersonCardHandle
                .EXTENDED_PERSON_CARD_ID));
        graphPanel = new GraphPanelHandle(getChildNode(GraphPanelHandle.GRAPH_DISPLAY_ID));
        statisticsPanel = new StatisticsPanelHandle(getChildNode(StatisticsPanelHandle.STATISTICS_PANEL_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
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

    public ExtendedPersonCardHandle getExtendedPersonCard() {
        return extendedPersonCard;
    }

    public GraphPanelHandle getGraphPanel() {
        return graphPanel;
    }

    public StatisticsPanelHandle getStatisticsPanel() {
        return statisticsPanel;
    }
}
