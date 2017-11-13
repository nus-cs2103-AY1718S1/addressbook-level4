//@@author tshradheya
package seedu.address.ui;

import java.io.File;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.MainApp;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.util.TagColor;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";
    private static final Integer IMAGE_WIDTH = 100;
    private static final Integer IMAGE_HEIGHT = 100;
    private static final String DIRECTORY_SAVING_PATH = "pictures/";
    private static final String DEFAULT_IMAGE_PATH = "/images/defaulddp.png";
    private static final String EMPTY_STRING = "";
    private static final String IMAGE_EXTENSION = ".png";
    private static String[] colors = {"#ff0000", "#0000ff", "#008000", "#ff00ff", "#00ffff"};
    private static Random random = new Random();



    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;
    private TagColor tagColorObject;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox mainCardPane;
    @FXML
    private VBox secondaryCardPane;
    @FXML
    private Label detailsName;
    @FXML
    private Label detailsPhone;
    @FXML
    private Label detailsAddress;
    @FXML
    private Label detailsEmail;
    @FXML
    private Label detailsNickname;
    @FXML
    private Label detailsBirthday;
    @FXML
    private FlowPane detailsTag;
    @FXML
    private ImageView detailsDisplayPicture;


    public DetailsPanel(ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        tagColorObject = TagColor.getInstance();
        initTags(person);
        bindListeners(person);
    }

    /**
     * Assigns a random color to a tag if it does not exist in the HashMap
     * returns a String containing the color
     */
    private String getTagColor(String tag) {
        //Defensive coding
        if (tagColorObject == null) {
            assert false : "Impossible as it is an singleton class and one object already created by PersonCard";
        }
        if (!tagColorObject.containsTag(tag)) {
            tagColorObject.addColor(tag, colors[random.nextInt(colors.length)]);
        }
        return tagColorObject.getColor(tag);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        detailsName.textProperty().bind(Bindings.convert(person.nameProperty()));
        detailsPhone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        detailsAddress.textProperty().bind(Bindings.convert(person.addressProperty()));
        detailsEmail.textProperty().bind(Bindings.convert(person.emailProperty()));
        detailsNickname.textProperty().bind(Bindings.convert(person.nicknameProperty()));
        detailsBirthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            detailsTag.getChildren().clear();
            initTags(person);
        });
        assignImage(person);
    }

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals(EMPTY_STRING)) {

            Image image = new Image("file:" + DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            // To take care of image deleted manually
            File file = new File(DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION);
            if (!file.exists()) {
                image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                        IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
            }

            centerImage();
            detailsDisplayPicture.setImage(image);

        }
    }



    /**
     * Centre the image in ImageView
     */
    public void centerImage() {
        Image img = detailsDisplayPicture.getImage();
        if (img != null) {
            double w;
            double h;

            double ratioX = detailsDisplayPicture.getFitWidth() / img.getWidth();
            double ratioY = detailsDisplayPicture.getFitHeight() / img.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            detailsDisplayPicture.setX((detailsDisplayPicture.getFitWidth() - w) / 2);
            detailsDisplayPicture.setY((detailsDisplayPicture.getFitHeight() - h) / 2);

        }
    }

    /**
     * Initialize tags for the respective person
     * @param person whose tags have to be added and assigned color
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            detailsTag.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailsPanel)) {
            return false;
        }

        // state check
        DetailsPanel panel = (DetailsPanel) other;
        return person.equals(panel.person);
    }
}
