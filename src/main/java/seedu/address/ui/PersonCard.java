package seedu.address.ui;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import seedu.address.model.person.ReadOnlyPerson;

import javax.imageio.ImageIO;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {


    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    //tagColor matches a specific tag with a color
    private static ArrayList<String> colors = new ArrayList<String>(
            Arrays.asList("red", "blue", "orange", "brown", "green", "pink", "black", "grey"));
    private static HashMap<String, String> tagColor = new HashMap<String, String>();
    private static Random random = new Random();

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    public ImageView picture;
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
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
        picture = new ImageView();
        initImage();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    private void initImage() {
        try {
            File picFile = new File(person.getPicture().getPictureUrl());
            FileInputStream fileStream = new FileInputStream(picFile);
            Image personPicture = new Image(fileStream);
            picture.setFitHeight(person.getPicture().PIC_HEIGHT);
            picture.setFitWidth(person.getPicture().PIC_WIDTH);
            picture.setImage(personPicture);
            cardPane.getChildren().add(picture);
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    /**
     * Handler for adding image to person
     */
    @FXML
    private void handleAddImage() {
        FileChooser picChooser = new FileChooser();
        File selectedPic = picChooser.showOpenDialog(null);
        if (selectedPic != null) {
            try {
                person.getPicture().setPictureUrl(person.getName().toString() + person.getPhone().toString() + ".jpg");
                ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getPictureUrl()));
                FileInputStream fileStream = new FileInputStream(person.getPicture().getPictureUrl());
                Image newPicture = new Image(fileStream);
                picture.setImage(newPicture);
            } catch (Exception e) {
                System.out.println(e + "Invalid File");
            }
        } else {
            System.out.println("Invalid File");
        }
    }

    //following method gets the color related to a specified tag
    private static String getColorForTag(String tag) {
        if (!tagColor.containsKey(tag)) { //if the hashmap does not have this tag
            String chosenColor = colors.get(random.nextInt(colors.size()));
            tagColor.put(tag, chosenColor); //put the tag and color in
            /*after this color is chosen, remove from the available list of colors to avoid
            repeating */
        }

        return tagColor.get(tag);
    }

    /**
     * initialise the tag with the colors and the tag name
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
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
}
