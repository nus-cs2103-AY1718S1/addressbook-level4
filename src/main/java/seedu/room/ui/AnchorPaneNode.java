package seedu.room.ui;

import java.time.LocalDate;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

//@@author Haozhe321-reused
/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private final Background focusBackground = new Background( new BackgroundFill(
            Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY ) );
    private final Background unfocusBackground = new Background( new BackgroundFill(
            Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY ) );

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked


        this.setOnMouseClicked( ( e ) ->
        {
            this.focusGrid();
        } );



    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    //this method focuses the Grid when the mouse clicks on it
    public void focusGrid() {
        this.requestFocus();
        this.backgroundProperty().bind( Bindings
                .when( this.focusedProperty() )
                .then( focusBackground )
                .otherwise(unfocusBackground)
        );

    }


}
