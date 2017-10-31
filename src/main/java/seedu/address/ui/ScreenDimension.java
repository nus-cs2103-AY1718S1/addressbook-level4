package seedu.address.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

//@@author newalter
/**
 * Provides the dimension of the current screen
 */
public class ScreenDimension {

    private static Dimension dimension;

    public static Dimension getDimension() {
        if (dimension != null) {
            return dimension;
        }
        // Handle exceptions in Travis-CI environment
        try {
            dimension = Toolkit.getDefaultToolkit().getScreenSize();
        } catch (Exception e) {
            dimension = new Dimension();
        }
        return dimension;
    }
}
