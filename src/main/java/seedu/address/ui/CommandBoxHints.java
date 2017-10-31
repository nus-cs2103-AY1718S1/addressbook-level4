package seedu.address.ui;

import static seedu.address.logic.parser.HintParser.generateHint;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.address.commons.events.ui.CommandInputChangedEvent;
import seedu.address.commons.util.TextUtil;

/**
 * The UI component that is responsible for displaying hints
 */

//@@author nicholaschuayunzhi
public class CommandBoxHints extends UiPart<TextField> {

    private static final String FXML = "CommandBoxHints.fxml";

    @FXML
    private TextField commandBoxHints;

    private TextField commandTextField;

    public CommandBoxHints(TextField commandTextField) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.commandTextField = commandTextField;

        commandBoxHints.textProperty().addListener((ob, o, n) -> {
            // expand the textfield
            double width = TextUtil.computeTextWidth(commandBoxHints.getFont(),
                    commandBoxHints.getText(), 0.0D) + 1;
            width = (width < 1) ? 1 : width;
            commandBoxHints.setPrefWidth(width);
        });
    }


    @Subscribe
    private void handleCommandInputChangedEvent(CommandInputChangedEvent event) {
        String userInput = event.currentInput;
        if (userInput.isEmpty()) {
            commandBoxHints.setText("Enter Command Here");
            return;
        }
        String hint = generateHint(userInput);
        commandBoxHints.setText(hint);

    }

    @FXML
    private void handleOnClick() {
        commandTextField.requestFocus();
        commandTextField.positionCaret(commandTextField.getText().length());
    }


}
