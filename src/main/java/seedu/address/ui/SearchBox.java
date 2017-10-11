package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class SearchBox extends UiPart<Region> {

    private static final String ERROR_STYLE_CLASS = "error";

    // private static final Logger logger = LogsCenter.getLogger(SearchBox.class);
    private static final String FXML = "SearchBox.fxml";

    @FXML
    private TextField searchTextField;

    public SearchBox (){
        super(FXML);

        searchTextField.textProperty().addListener( (observable, oldValue, newValue) -> {

            System.out.println("textfield changed from " + oldValue + " to " + newValue);

        });
    }

    /**
     * Sets the search box style to use the default style.
     */
    private void setStyleToDefault() {
        searchTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the search box style to indicate a failed command.
     */

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = searchTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
