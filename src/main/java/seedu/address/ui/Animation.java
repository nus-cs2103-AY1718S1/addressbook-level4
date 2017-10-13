package seedu.address.ui;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Adds animation to the UI
 */
public class Animation {

    /**
     * Fades in a given list of nodes.
     * @param nodes a list of nodes to fade into the UI.
     */
    public static void fadeIn(ObservableList<Node> nodes) {
        for (Node node : nodes) {
            node.setVisible(true);
            FadeTransition tagFadeTransition = new FadeTransition(Duration.millis(3000), node);
            tagFadeTransition.setFromValue(0);
            tagFadeTransition.setToValue(1.0);

            tagFadeTransition.play();
        }
    }
}
