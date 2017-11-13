package seedu.address.ui;

import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

//@@author jacoblipech
/**
 * Create an anchor pane that stores additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    private LocalDate date;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate (LocalDate date) {
        this.date = date;
    }
}
