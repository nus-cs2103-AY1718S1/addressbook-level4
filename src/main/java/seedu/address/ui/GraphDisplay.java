package seedu.address.ui;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

/**
 * Integrating GraphStream graph display into the application.
 */
public class GraphDisplay extends UiPart<Region>{

    private static final String FXML = "GraphDisplay.fxml";

    private final Logic logic;

    @FXML
    private SwingNode graphDisplay;
    @FXML
    private StackPane graphDisplayHolder;

    public GraphDisplay(Logic logic) {
        super(FXML);
        this.logic = logic;

        createAndSetSwingContent(graphDisplay);
    }

    /**
     * Sets the graph stream display in the SwingNode.
     */
    private void createAndSetSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() ->
            swingNode.setContent((JComponent) logic.getGraphWrapper().getView()));
    }

}
