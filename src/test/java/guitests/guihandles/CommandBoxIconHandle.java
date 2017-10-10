package guitests.guihandles;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * A handle to the {@code CommandBoxIcon} in the GUI.
 */
public class CommandBoxIconHandle extends NodeHandle<FontIcon> {

    public static final String COMMAND_ICON_FIELD_ID = "#icon";

    public CommandBoxIconHandle(FontIcon icon) {
        super(icon);
    }

    /**
     * Returns the current icon code.
     */
    public Ikon getIconCode() {
        return getRootNode().getIconCode();
    }
}
