package seedu.address.ui;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

public class Animation {
    
    public Animation() {
        
    }
    
    public static void fadeIn(ObservableList<Node> tags) {
        for (Node tag : tags) {
            tag.setVisible(true);
            FadeTransition tagFadeTransition = new FadeTransition(Duration.millis(3000), tag);
            tagFadeTransition.setFromValue(0);
            tagFadeTransition.setToValue(1.0);

            tagFadeTransition.play();
        }
    }
}
