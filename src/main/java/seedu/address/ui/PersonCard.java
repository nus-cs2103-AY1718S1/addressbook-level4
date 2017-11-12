package seedu.address.ui;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

//@@author hansiang93

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = {"darkblue", "darkolivegreen", "slategray ", "teal", "maroon", "darkslateblue"};
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static HashMap<String, String> webLinkColors = new HashMap<>();

    static {
        webLinkColors.put("facebook", "#3b5998");
        webLinkColors.put("twitter", "#00aced");
        webLinkColors.put("linkedin", "#0077b5");
        webLinkColors.put("instagram", "#8a3ab9");
        webLinkColors.put("others", "grey");
    }
    //@@author

    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane webLinks;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView phoneicon;
    @FXML
    private ImageView addressicon;
    @FXML
    private ImageView emailicon;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initWebLinks(person);
        bindListeners(person);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    private static String getColorForWeblinks(String webLinkTag) {
        return webLinkColors.get(webLinkTag);
    }
    //@@author

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.webLinkProperty().addListener((observable, oldValue, newValue) -> {
            webLinks.getChildren().clear();
            initWebLinks(person);
        });
        if (person.phoneProperty().get().toString().equals("-")) {
            phoneicon.setStyle("-fx-opacity: " + 0.2);
        }
        if (person.addressProperty().get().toString().equals("-")) {
            addressicon.setStyle("-fx-opacity: " + 0.2);
        }
        if (person.emailProperty().get().toString().equals("-")) {
            emailicon.setStyle("-fx-opacity: " + 0.2);
        }
    }

    /**
     * Initialize person's tag and add appropriate background color to it
     *
     * @param person name
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initialize person's webLink and add appropriate background color to it
     *
     * @param person name
     */
    private void initWebLinks(ReadOnlyPerson person) {
        person.getWebLinks().forEach(webLink -> {
            Label webLinkLabel = new Label(webLink.toStringWebLinkTag());
            webLinkLabel.setStyle("-fx-background-color: transparent");
            webLinkLabel.setStyle("-fx-border-color: " + getColorForWeblinks(webLink.toStringWebLinkTag()));
            webLinks.getChildren().add(webLinkLabel);
        });
    }

    public static Optional<HashMap<String, String>> getTagColors() {
        return Optional.of(tagColors);
    }

    public static HashMap<String, String> getWebLinkColors() {
        return webLinkColors;
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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
