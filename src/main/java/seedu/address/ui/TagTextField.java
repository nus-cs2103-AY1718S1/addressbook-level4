package seedu.address.ui;

import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.tag.Tag;

/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
 */
public class TagTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "TagTextField.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextArea tagTextArea;
    private Set<Tag> tagSet;

    public TagTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getTagTextArea() {
        return tagTextArea.getText();
    }

    public void setTagTextArea(String text) {
        tagTextArea.setText(text);
    }
    public void setTagSet(Set<Tag> t) {
        tagSet = t;
    }
    public Set<Tag> getTagSet() {
        return tagSet;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        StringBuilder currTags = new StringBuilder();
        tagSet = event.getNewSelection().person.getTags();
        setTagSet(event.getNewSelection().person.getTags());
        for (Tag t : tagSet) {
            if (currTags.length() != 0) {
                currTags.append(",");
            }
            currTags.append(t.tagName);
        }
        setTagTextArea(currTags.toString());
    }


}

