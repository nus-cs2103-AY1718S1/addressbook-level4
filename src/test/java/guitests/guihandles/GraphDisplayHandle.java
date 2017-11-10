package guitests.guihandles;

import javax.swing.JComponent;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;

//@@author joanneong
/**
 * A handler for the {@code GraphDisplay} of the UI.
 */
public class GraphDisplayHandle extends NodeHandle<Node> {

    public static final String DISPLAY_ID = "#graphDisplay";
    private SwingNode graphDisplayNode;

    public GraphDisplayHandle(SwingNode graphDisplayNode) {
        super(graphDisplayNode);

        this.graphDisplayNode = graphDisplayNode;
    }

    /**
     * Returns the JComponent (i.e. the viewer) embedded in the SwingNode.
     */
    public JComponent getContent() {
        return graphDisplayNode.getContent();
    }
}
