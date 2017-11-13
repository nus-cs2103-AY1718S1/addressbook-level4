//@@author qihao27
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a todolist count in the person card.
 */
public class FavouriteStarHandle extends NodeHandle<Node> {
    private static final String FAVOURITE_FIELD_ID = "#favourite";

    private final Label favouriteLabel;

    public FavouriteStarHandle(Node favouriteNode) {
        super(favouriteNode);

        this.favouriteLabel = getChildNode(FAVOURITE_FIELD_ID);
    }

    public String getFavouriteStar() {
        return favouriteLabel.getText();
    }
}
