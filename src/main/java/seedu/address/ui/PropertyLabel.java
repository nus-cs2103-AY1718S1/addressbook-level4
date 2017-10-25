package seedu.address.ui;

import javafx.scene.control.Label;

/**
 * A customize JavaFX {@link Label} class used to display the key-value pairs of all properties.
 */
public class PropertyLabel extends Label {
    public PropertyLabel(String text, String style) {
        super(text);
        this.getStyleClass().add(style);
    }
}
