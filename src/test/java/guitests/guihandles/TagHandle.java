package guitests.guihandles;

import javafx.scene.control.Label;

/**
 * Provides a handle to Tag
 */
public class TagHandle extends NodeHandle<Label> {

    private Label label;

    public TagHandle(Label label) {
        super(label);
        this.label = label;
    }

    public String getText() {
        return label.getText();
    }

}
