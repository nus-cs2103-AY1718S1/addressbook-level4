package guitests.guihandles;

import javafx.scene.control.TextArea;

// @@author HouDenghao
/**
 * A handler for the {@code InformationBoard} of the UI
 */
public class InformationBoardHandle extends NodeHandle<TextArea> {

    public static final String INFORMATION_BOARD_ID = "#informationBoard";

    public InformationBoardHandle(TextArea informationBoardNode) {
        super(informationBoardNode);
    }

    /**
     * Returns the text in the information board.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
