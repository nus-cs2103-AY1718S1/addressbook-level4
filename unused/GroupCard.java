//@@author hxy0229
package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;

/** checkstyle comment */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";


    private static String[] colors = { "red", "blue", "orange", "brown", "green", "pink",
        "grey", "purple", "gold", "crimson", "navy", "darkBlue", "mediumBlue", "darkGreen",
        "teal", "darkCyan", "deepSkyBlue", "lime", "springGreen", "midnightBlue", "forestGreen",
        "seaGreen", "royalBlue", "indigo", "darkOliveGreen", "maroon", "saddleBrown", "slateBlue" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private static int[] usedColors = new int[colors.length];


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Group group;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane persons;

    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        initPersons(group);
        bindListeners(group);
    }
    //@@author yanji1221
    /**
     * Color getter for a tag
     */
    private static String colorGetterForPerson(String personValue) {
        int colorCode;
        boolean isUsedUpAllColors = true;
        for (int i = 0; i < colors.length; i++) {
            if (usedColors[i] == 0) {
                isUsedUpAllColors = false;
                break;
            }
        }
        if (isUsedUpAllColors) {
            for (int j = 0; j < colors.length; j++) {
                usedColors[j] = 0;
            }
        }

        if (!tagColors.containsKey(personValue)) {
            do {
                colorCode = random.nextInt(colors.length);
            } while(usedColors[colorCode] == 1);
            usedColors[colorCode] = 1;
            tagColors.put(personValue, colors[colorCode]);
        }

        return tagColors.get(personValue);
    }
    //@@author
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Group group) {
        name.textProperty().bind(Bindings.convert(group.nameProperty()));
        group.personProperty().addListener((observable, oldValue, newValue) -> {
            persons.getChildren().clear();
            initPersons(group);
        });
    }

    //@@author yanji1221
    /**
     * Distribute colors for tags
     */
    private void initPersons(Group group) {
        group.getPersonList().forEach(person -> {
            Label personLabel = new Label(person.getName().fullName);
            personLabel.setStyle("-fx-background-color: " + colorGetterForPerson(person.getName().fullName));
            persons.getChildren().add(personLabel);
        });
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCard)) {
            return false;
        }

        // state check
        GroupCard card = (GroupCard) other;
        return id.getText().equals(card.id.getText())
                && group.equals(card.group);
    }
}

