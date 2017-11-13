//@@author heiseish
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.image.Image;

public class IconImageTest {
    private static final String ICON = "/images/heart.png";
    private static final String ICON_OUTLINE = "/images/heartOutline.png";
    private static final String DEFAULT = "/images/default.png";
    private static final String DEFAULT_GROUP = "/images/group.png";
    private static final String FACEBOOK = "/images/facebook.png";

    private Image heart;
    private Image heartOutline;
    private Image fbicon;
    private Image circlePerson;
    private Image circleGroup;
    private IconImage image = new IconImage();

    @Before
    public void setUp() {
        circlePerson = new Image(getClass().getResourceAsStream(DEFAULT));
        circleGroup = new Image(getClass().getResourceAsStream(DEFAULT_GROUP));
        heart = new Image(getClass().getResourceAsStream(ICON), 25, 25, false, false);
        heartOutline = new Image(getClass().getResourceAsStream(ICON_OUTLINE), 20, 20, false, false);
        fbicon = new Image(getClass().getResourceAsStream(FACEBOOK), 25, 25, false, false);

    }

    @Test
    public void testImage() {
        assertImageEqual(heart, image.getHeart());
        assertImageEqual(heartOutline, image.getHeartOutline());
        assertImageEqual(fbicon, image.getFbicon());
        assertImageEqual(circlePerson, image.getCirclePerson());
        assertImageEqual(circleGroup, image.getCircleGroup());
    }

    /**
     * Test the equality of 2 images
     * @param image1 first image
     * @param image2 second image
     */
    private void assertImageEqual(Image image1, Image image2) {
        double epsilon = 0.001;
        assertEquals(image1.getHeight(), image2.getHeight(), epsilon);
        assertEquals(image1.getWidth(), image2.getWidth(), epsilon);
        for (int i = 0; i < image1.getWidth(); i++) {
            for (int j = 0; j < image1.getHeight(); j++) {
                assertTrue(image1.getPixelReader().getColor(i, j).equals(image2.getPixelReader().getColor(i, j)));
            }
        }
    }
}
