package seedu.address.commons.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Contains utility methods for JavaFX views
 */
public class FxViewUtil {

    /**
     * Sets the given image as the icon for the given stage.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    public static void setStageIcon(Stage stage, String iconSource) {
        stage.getIcons().setAll(AppUtil.getImage(iconSource));
    }

    //@@author jshoung
    /**
     * Returns the currently loaded {@code image} in the {@code ImageView}. For future test implementation
     */
    public static Image getImage(ImageView imageview) {
        return imageview.getImage();
    }

}
