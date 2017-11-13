//@@author keloysiusmak
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class ViewAliasCard extends UiPart<Region> {

    private static final String FXML = "ViewAliasListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final String command;

    @FXML
    private HBox cardPane;
    @FXML
    private Label defaultAlias;
    @FXML
    private Label alias;
    @FXML
    private Label id;

    public ViewAliasCard(String command, String commandWord, Logic logic) {
        super(FXML);
        this.command = command;
        id.setText(command);

        defaultAlias.setText("Default Alias : " + commandWord);
        alias.setText("Set Alias : " + logic.getAliasForCommand(commandWord));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        ViewAliasCard card = (ViewAliasCard) other;
        return id.getText().equals(card.id.getText())
                && command.equals(card.command);
    }
}
