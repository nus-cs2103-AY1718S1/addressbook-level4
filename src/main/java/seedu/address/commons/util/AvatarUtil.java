package seedu.address.commons.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//@@author teclu
/**
 * A class to draw the default placeholder avatar.
 */
public class AvatarUtil {
    private static final BufferedImage placeholder =
            new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_INDEXED);
    private static final Color gray = new Color(200, 200, 200);
    private static final Color darkGray = new Color(177, 177, 177);
    private static final Color lightGray = new Color(244, 244, 244);

    public AvatarUtil() {
        drawPlaceholderAvatar();
    }

    /**
     * This method draws the default placeholder avatar.
     */
    private void drawPlaceholderAvatar() {
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(lightGray); // Backdrop
        g2d.fillRect(1, 1, 198, 198);

        g2d.setColor(gray); // Body
        g2d.fillOval(25, 135, 155, 50);
        g2d.fillRect(25, 163, 155, 50);

        g2d.setColor(gray); // Head
        g2d.fillOval(55, 35, 95, 95);

        g2d.setColor(Color.white); // Border Frame
        g2d.drawRect(0, 0, 199, 199);
        g2d.dispose();
    }

    public BufferedImage getPlaceholderAvatar() {
        return placeholder;
    }
}
