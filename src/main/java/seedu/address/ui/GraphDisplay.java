package seedu.address.ui;

import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewGraphDisplayEvent;
import seedu.address.logic.Logic;
import seedu.address.model.graph.GraphWrapper;

/**
 * Integrating GraphStream graph display into the application.
 */
public class GraphDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "GraphDisplay.fxml";

    private final Logic logic;

    @FXML
    private SwingNode graphDisplay;
    @FXML
    private StackPane graphDisplayHolder;

    public GraphDisplay(Logic logic) {
        super(FXML);
        this.logic = logic;

        registerAsAnEventHandler(this);
    }

    /**
     * Sets the graph stream display in the SwingNode.
     */
    protected void createAndSetSwingContent() {
        SwingUtilities.invokeLater(() ->
            graphDisplay.setContent((JComponent) GraphWrapper.getInstance().getView()));
    }

    @Subscribe
    private void handleNewGraphIntialisedEvent(NewGraphDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() ->
            graphDisplay.setContent((event.getGraph().display().getDefaultView())));
    }

}
