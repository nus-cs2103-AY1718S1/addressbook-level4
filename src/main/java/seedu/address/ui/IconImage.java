//@@author heiseish
package seedu.address.ui;

import javafx.scene.image.Image;

/**
 * Contains the image used in group and person card
 * All image are stored in the memory for this class to present java heap exhausted
 */
public class IconImage {
    private static final String ICON = "/images/heart.png";
    private static final String ICON_OUTLINE = "/images/heartOutline.png";
    private static final String DEFAULT = "/images/default.png";
    private static final String DEFAULT_GROUP = "/images/group.png";
    private static final String FACEBOOK = "/images/facebook.png";

    private final Image heart;
    private final Image heartOutline;
    private final Image fbicon;
    private final Image circlePerson;
    private final Image circleGroup;

    public IconImage() {
        circlePerson = new Image(getClass().getResourceAsStream(DEFAULT));
        circleGroup = new Image(getClass().getResourceAsStream(DEFAULT_GROUP));
        heart = new Image(getClass().getResourceAsStream(ICON), 25, 25, false, false);
        heartOutline = new Image(getClass().getResourceAsStream(ICON_OUTLINE), 20, 20, false, false);
        fbicon = new Image(getClass().getResourceAsStream(FACEBOOK), 25, 25, false, false);
    }

    /**
     * Return a heart shape image
     * @return heart
     */
    public Image getHeart() {
        return heart;
    }

    /**
     * Return a facebook icon
     * @return fbicon
     */
    public Image getFbicon() {
        return fbicon;
    }

    /**
     * @return a heart outline shape
     */
    public Image getHeartOutline() {
        return heartOutline;
    }

    /**
     * @return group circle
     */
    public Image getCircleGroup() {
        return circleGroup;
    }

    /**
     * @return person circle
     */
    public Image getCirclePerson() {
        return circlePerson;
    }
}
