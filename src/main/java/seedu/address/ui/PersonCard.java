//@@author ChenXiaoman
package seedu.address.ui;

import static seedu.address.model.font.FontSize.getAssociateFxFontSizeString;
import static seedu.address.model.font.FontSize.getAssociateFxFontSizeStringForName;

import java.io.File;

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
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.font.FontSize;
import seedu.address.model.person.ReadOnlyPerson;

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
    @FXML
    private ImageView imagePhone;
    @FXML
    private ImageView imageEmail;
    @FXML
    private ImageView imageAddress;
    @FXML
    private ImageView imageBirth;
    @FXML
    private ImageView imageRemark;



    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
        registerAsAnEventHandler(this);
        String currentFontSize = FontSize.getCurrentFontSizeLabel();
        setFontSize(currentFontSize);
        setSizeForAllImagesAccordingToFontSize(currentFontSize);
        setSizeForPhotosAccordingToFontSize(currentFontSize);
        initTags(person, currentFontSize);
    }

    /**
     * Get the label address
     */
    public Label getAddressLabel() {
        return address;
    }
    //@@author

    //@@author RonakLakhotia
    /**
     * Binds a photo to a persons PersonCard.
     */
    public void assignImageToPerson(String filePath) throws ParseException {

        String url;
        String Message_Image_Removed = "The image may have been removed from"
                + " the previous location!";

        if (filePath.equals("")) {
            url = "/images/user.png";
            Image Display = new Image(url);
            image.setImage(Display);

        } else {
            url = filePath + "";
            File file = new File(url);
            boolean isFileExists = file.exists();

            if (!isFileExists) {
                url = "/images/address_book_32.png";
                Image Display = new Image(url);
                image.setImage(Display);
                throw new ParseException(
                            String.format(Message_Image_Removed, PhotoCommand.MESSAGE_USAGE)
                    );
            }
            else {
                Image display = new Image(file.toURI().toString());
                image.setImage(display);
            }

        }
    }
    //@@author

    //@@author ChenXiaoman
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
        try {
            assignImageToPerson(person.getImage().getFilePath());
        }
        catch (ParseException pe) {
            new AssertionError("Invalid input");
        }

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

        String fxFormatFontSize = FontSize.getAssociateFxFontSizeString(fontSizeLabel);

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
        String newFontSize = event.getFontSize();
        initTags(person, newFontSize);
        setFontSize(newFontSize);
        setSizeForAllImagesAccordingToFontSize(newFontSize);
        setSizeForPhotosAccordingToFontSize(newFontSize);
    }

    private void setFontSize(String newFontSize) {
        assert (FontSize.isValidFontSize(newFontSize));

        String fxFormatFontSize = getAssociateFxFontSizeString(newFontSize);
        String fxFormatFontSizeForName = getAssociateFxFontSizeStringForName(newFontSize);

        setFontSizeForAllAttributesExceptTag(fxFormatFontSizeForName, fxFormatFontSize);
    }


    private void setFontSizeForAllAttributesExceptTag(String nameFontSize, String fontSize) {
        name.setStyle(nameFontSize);
        id.setStyle(nameFontSize);
        phone.setStyle(fontSize);
        address.setStyle(fontSize);
        email.setStyle(fontSize);
        date.setStyle(fontSize);
        remark.setStyle(fontSize);
    }

    private void setSizeForAllImagesAccordingToFontSize(String fontSize) {
        int newImageSize = FontSize.getAssociateImageSizeFromFontSize(fontSize);

        imagePhone.setFitHeight(newImageSize);
        imagePhone.setFitWidth(newImageSize);

        imageAddress.setFitHeight(newImageSize);
        imageAddress.setFitWidth(newImageSize);

        imageEmail.setFitHeight(newImageSize);
        imageEmail.setFitWidth(newImageSize);

        imageBirth.setFitHeight(newImageSize);
        imageBirth.setFitWidth(newImageSize);

        imageRemark.setFitHeight(newImageSize);
        imageRemark.setFitWidth(newImageSize);
    }

    private void setSizeForPhotosAccordingToFontSize(String fontSize) {
        int newImageSize = FontSize.getAssociatePhotoSizeFromFontSize(fontSize);
        image.setFitWidth(newImageSize);
        image.setFitHeight(newImageSize);
    }

    @FXML
    private void handleAddressClick() {
        raise(new ShowPersonAddressEvent(address.getText()));
    }

}
