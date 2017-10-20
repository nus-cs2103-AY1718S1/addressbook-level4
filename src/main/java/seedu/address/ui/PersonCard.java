package seedu.address.ui;

import static seedu.address.model.font.FontSize.getassociatefxfontsizestring;

import java.io.File;

import java.util.HashMap;
import java.util.Random;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.ChangeTagColorEvent;
import seedu.address.model.font.FontSize;
import seedu.address.model.person.ReadOnlyPerson;

//import javax.swing.text.html.ImageView;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = {"red", "yellow", "blue", "orange", "brown", "green", "pink"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
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
    private Label date;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private ImageView image;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
        registerAsAnEventHandler(this);
        String currentFontSize = FontSize.getCurrentFontSizeLabel();
        setFontSize(currentFontSize);
        initTags(person, currentFontSize);

    }
    /**
     * Adds a photo to a persons contact
     */
    public void assignImage(String FilePath) {

        String url;

        if (FilePath.equals("")) {
            url = "/images/clock.png";
            Image Display = new Image(url);
            image.setImage(Display);
        }
        else {

            if (FilePath.endsWith("g")) {

                String home = System.getProperty("user.home");
                java.nio.file.Path path = java.nio.file.Paths.get(home, "Desktop", FilePath);
                url = path + "";
                File file = new File(url);

                Image Display = new Image(file.toURI().toString());
                image.setImage(Display);
            } else {

                url = "src/main/resources/images/" + person.getImage().getFilePath() + ".jpg";
                File stored = new File(url);
                Image Display = new Image(stored.toURI().toString(), 100, 100,
                        false, false);

                image.setImage(Display);

            }
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        date.textProperty().bind(Bindings.convert(person.dateOfBirthProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));

        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();

            initTags(person, FontSize.getCurrentFontSizeLabel());

        });
        assignImage(person.getImage().getFilePath());
    }

    @Subscribe
    private void handleChangeTagColorEvent(ChangeTagColorEvent event) {
        initTags(person, FontSize.getCurrentFontSizeLabel());
    }

    /**
     * Initialize tag color and font size for each tag
     *
     * @param person
     */
    private void initTags(ReadOnlyPerson person, String fontSizeLabel) {
        tags.getChildren().clear();

        String fxFormatFontSize = FontSize.getassociatefxfontsizestring(fontSizeLabel);

        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle(fxFormatFontSize + "-fx-background-color: " + tag.tagColor.tagColorName);
            tags.getChildren().add(tagLabel);
        });
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

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        initTags(person, event.getFontSize());
        setFontSize(event.getFontSize());
    }

    private void setFontSize(String newFontSize) {
        assert (FontSize.isValidFontSize(newFontSize));

        String fxFormatFontSize = getassociatefxfontsizestring(newFontSize);
        setFontSizeForAllAttributesExceptTag(fxFormatFontSize);
    }


    private void setFontSizeForAllAttributesExceptTag(String fontSize) {
        name.setStyle(fontSize);
        id.setStyle(fontSize);
        phone.setStyle(fontSize);
        address.setStyle(fontSize);
        email.setStyle(fontSize);
        date.setStyle(fontSize);
        remark.setStyle(fontSize);
    }

}
