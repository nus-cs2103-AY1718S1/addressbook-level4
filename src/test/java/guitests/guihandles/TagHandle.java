package guitests.guihandles;

import javafx.scene.control.Label;

public class TagHandle extends NodeHandle<Label> {

    Label label;

    public TagHandle(Label label) {
        super(label);
        this.label = label;
    }

    public String getText() {
        return label.getText();
    }

}
