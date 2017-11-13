package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * A handler for the {@code ResultDisplay} of the UI
 */
public class CommandPredictionPanelHandle extends NodeHandle<ListView> {

    public static final String COMMAND_PREDICTION_PANEL_ID = "#commandPredictionListView";

    public CommandPredictionPanelHandle(ListView commandPredictionPanelNode) {
        super(commandPredictionPanelNode);
    }

    /**
     * Returns the results of the {@code CommandPredictionPanel} as a list
     */
    public ObservableList<String> getPredictionResults() {
        return getRootNode().getItems();
    }
}
