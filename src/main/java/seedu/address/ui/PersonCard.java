package seedu.address.ui;

import static seedu.address.ui.MainWindow.DEFAULT_DP;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String INSTA_ICON = "/images/insta_Icon.png";
    private static final String TWITTER_ICON = "/images/twitter_icon.png";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.sax
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
    private FlowPane tags;
    @FXML
    private Circle displayPic;
    @FXML
    private Circle instaIcon;
    @FXML
    private Circle twitterIcon;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initDisplayPic(person);
        initInstaIcon(person);
        initTwitterIcon(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        //@@author JunQuann
        person.displayPicProperty().addListener((observable, oldValue, newValue) -> {
            initDisplayPic(person);
        });
        person.instagramNameProperty().addListener((observable, oldValue, newValue) -> {
            initInstaIcon(person);
        });
        person.twitterNameProperty().addListener((observable, oldValue, newValue) -> {
            initTwitterIcon(person);
        });
    }

    /**
     * Initialise the image in PersonCard display
     * @param person
     */
    private void initDisplayPic(ReadOnlyPerson person) {
        Image displayPicture;
        if (person.getDisplayPic().toString().equals(DEFAULT_DP)) {
            displayPicture = AppUtil.getImage(DEFAULT_DP);
        } else {
            File personImg = new File(person.getDisplayPic().toString());
            String imgUrl = personImg.toURI().toString();
            displayPicture = new Image(imgUrl);
        }
        displayPic.setFill(new ImagePattern(displayPicture));
    }

    /**
     * Initialise the twitter icon is the person has a twitter account
     */
    private void initTwitterIcon(ReadOnlyPerson person) {
        String twitterName = person.getTwitterName().value;
        if (!twitterName.isEmpty()) {
            Image twitterIconPic = AppUtil.getImage(TWITTER_ICON);
            twitterIcon.setFill(new ImagePattern(twitterIconPic));
        }
    }

    /**
     * Initialise the instagram icon is the person has a instagram account
     */
    private void initInstaIcon(ReadOnlyPerson person) {
        String instaName = person.getInstagramName().value;
        if (!instaName.isEmpty()) {
            Image instaIconPic = AppUtil.getImage(INSTA_ICON);
            instaIcon.setFill(new ImagePattern(instaIconPic));
        }
    }

    /**
     * Create new labels and bind a colour to it
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label newTag = new Label(tag.tagName);
            newTag.setStyle(":#C1D3DD");
            tags.getChildren().add(newTag);
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
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
