package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.alias.ReadOnlyAliasToken;

//@@author Alim95
/**
 * An UI component that displays information of a {@code Alias}.
 */
public class AliasCard extends UiPart<Region> {

    private static final String FXML = "AliasListCard.fxml";

    public final ReadOnlyAliasToken alias;

    @FXML
    private HBox cardPane;
    @FXML
    private Label keyword;
    @FXML
    private Label id;
    @FXML
    private Label representation;

    public AliasCard(ReadOnlyAliasToken alias, int displayedIndex) {
        super(FXML);
        this.alias = alias;
        id.setText(displayedIndex + ". ");
        bindListeners(alias);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Alias} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyAliasToken alias) {
        keyword.textProperty().bind(Bindings.convert(alias.keywordProperty()));
        representation.textProperty().bind(Bindings.convert(alias.representationProperty()));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AliasCard)) {
            return false;
        }

        // state check
        AliasCard card = (AliasCard) other;
        return id.getText().equals(card.id.getText())
                && alias.equals(card.alias);
    }
}
