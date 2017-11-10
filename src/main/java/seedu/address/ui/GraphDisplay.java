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

//@@author joanneong
/**
 * Integrating GraphStream graph display into the application.
 */
public class GraphDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "GraphDisplay.fxml";

    /**
     * Displays the integrated graph with the desired style.
     *
     * This is a 'hack' since the current GraphStream API requires an absolute file path to the css file,
     * which is risky when the application is converted to a JAR file.
     * Alternatively, we can consider using "ClassLoader.getSystemClassLoader().getResource(".").getPath(‌​);"
     * but this is the most fail-safe method at the current moment.
     */
    private static final String GRAPH_DISPLAY_STYLESHEET =
            "graph { fill-color: white; }"
                    + "node {"
                    + "fill-color: black;"
                    + "shape: rounded-box;"
                    + "text-background-mode: rounded-box;"
                    + "text-padding: 5;"
                    + "text-background-color: black;"
                    + "text-color: white;"
                    + "text-size: 15;"
                    + "size-mode: fit;"
                    + "z-index: 3;}"
                    + "edge { "
                    + "size: 3;"
                    + "fill-color: black; "
                    + "arrow-size: 20, 10;"
                    + "text-alignment: along;"
                    + "text-background-color: white;"
                    + "text-background-mode: rounded-box;"
                    + "text-size: 10;"
                    + "text-padding: 5;"
                    + "z-index: 1;}";

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

    public static String getGraphDisplayStylesheet() {
        return GRAPH_DISPLAY_STYLESHEET;
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
