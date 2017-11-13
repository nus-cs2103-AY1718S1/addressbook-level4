//@@author tshradheya
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Provides a handle to a person card in the Popular Contact list panel.
 */
public class PopularContactCardHandle extends NodeHandle<Node> {

    private static final String RANK_FEILD_ID = "#rank";
    private static final String NAME_FIELD_ID = "#popularContactName";
    private static final String DISPLAY_PICTURE_FIELD_ID = "#popularContactDisplayPicture";

    private final Label idLabel;
    private final Label nameLabel;
    private final Circle displayPictureImageView;

    public PopularContactCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(RANK_FEILD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.displayPictureImageView = getChildNode(DISPLAY_PICTURE_FIELD_ID);

    }

    public String getRank() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public Paint getDisplayPictureImageView() {
        return displayPictureImageView.getFill();
    }
}
