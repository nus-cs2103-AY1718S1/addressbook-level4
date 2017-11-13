# viviantan95
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            + PREFIX_BIRTHDAY + "BIRTHDAY"
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            + PREFIX_BIRTHDAY + "12/02/1985 "
```
###### \java\seedu\address\logic\commands\AddPhotoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a photo to a person in the address book.
 */
public class AddPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addphoto";
    public static final String COMMAND_ALIAS = "ap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a photo to a person in the address book using "
            + "the index of the person in the latest listing.\n"
            + "Parameters: INDEX (must be a positive integer) u/[PHOTO_URL]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PHOTO
            + "https://www.facebook.com/AlexYeoh/photo1.jpg\n";

    public static final String MESSAGE_ADDPHOTO_SUCCESS = "Added photo of Person: %1$s";
    public static final String MESSAGE_ADDPHOTO_UNSUCCESS = "Please enter a valid photo URL.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Index index;
    private final Photo photo;

    public AddPhotoCommand(Index index, Photo photo) {
        requireNonNull(index);
        requireNonNull(photo);

        this.index = index;
        this.photo = photo;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> latestList = model.getFilteredPersonList();
        if (index.getZeroBased() >= latestList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddPhoto = latestList.get(index.getZeroBased());
        Person addedPhotoPerson = new Person(personToAddPhoto);
        addedPhotoPerson.setPhoto(photo);

        try {
            model.updatePerson(personToAddPhoto, addedPhotoPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing.");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADDPHOTO_SUCCESS, personToAddPhoto));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPhotoCommand // instanceof handles nulls
                && this.index.equals(((AddPhotoCommand) other).index)
                && this.photo.equals(((AddPhotoCommand) other).photo));
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY] "
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Birthday updatedBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            this.birthday = toCopy.birthday;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
                    && getBirthday().equals(e.getBirthday())
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Photo photo = new Photo();
```
###### \java\seedu\address\logic\parser\AddPhotoCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;

import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * Parses input arguments and creates a new AddPhotoCommand object
 */
public class AddPhotoCommandParser implements Parser<AddPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPhotoCommand
     * and returns an AddPhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddPhotoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHOTO);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }
        Person toAddPhotoPerson = new Person(SampleDataUtil.getSamplePersons()[0]);

        try {
            ParserUtil.parsePhoto(argMultimap.getValue(PREFIX_PHOTO)).ifPresent(toAddPhotoPerson::setPhoto);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        }

        if (toAddPhotoPerson.getPhoto().toString().equals("file://"
                + Paths.get("src/main/resources/images/defaultPhoto.png").toAbsolutePath().toUri().getPath())) {
            throw new ParseException(AddPhotoCommand.MESSAGE_ADDPHOTO_UNSUCCESS);
        }

        return new AddPhotoCommand(index, toAddPhotoPerson.getPhoto());
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddPhotoCommand.COMMAND_WORD:
        case AddPhotoCommand.COMMAND_ALIAS:
            return new AddPhotoCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_PHOTO = new Prefix("u/");
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY))
                    .ifPresent(editPersonDescriptor::setBirthday);
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Optional<String> photo} into a {@code Optional<Photo>}.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhoto(Optional<String> photo) throws IllegalValueException {
        requireNonNull(photo);
        return photo.isPresent() ? Optional.of(new Photo(photo.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's Birthday in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidBirthday(String)}
 */

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday must be in the format of DD/MM/YYYY";

    public static final String BIRTHDAY_VALIDATION_REGEX =
            "\\d{2}/\\d{2}/\\d{4}";

    public final String value;
    private String browserValue;

    //Validates given Birthday.
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
        this.browserValue = trimmedBirthday;
    }

    /**
     * Returns true if a given String is a valid Birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

```
###### \java\seedu\address\model\person\Birthday.java
``` java

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Birthday
                && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Person with default photo of images/defaultPhoto/png
     */
    public Person(Name name, Set<Phone> phones, Birthday birthday, Set<Email> emails, Address address,
                  Set<Module> modules) {
        this(name, phones, birthday, emails, address, new Photo(), modules);
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.birthday = new SimpleObjectProperty<>(birthday);
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.photo = new SimpleObjectProperty<>(photo);
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setPhoto(Photo photo) {
        this.photo.set(requireNonNull(photo));
    }

    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }

    @Override
    public Photo getPhoto() {
        return photo.get();
    }
```
###### \java\seedu\address\model\person\Photo.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a photo of a Person in the addressbook.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhoto(String)}
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Person's photo must have a valid image URL.";

    public static final String PHOTO_VALIDATION_REGEX = "[^\\s].*";

    private static final String DEFAULT_PHOTO = "images/defaultPhoto.png";

    public final String value;
    private Image image;

    /**
     * Validates given photo
     *
     * @throws IllegalValueException if given photo url is invalid.
     */
    public Photo() {
        value = DEFAULT_PHOTO;
    }

    public Photo(String photo) throws IllegalValueException {
        requireNonNull(photo);
        if (!isValidPhoto(photo)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        }
        this.value = photo;
    }

```
###### \java\seedu\address\model\person\Photo.java
``` java
    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidPhoto(String test) {
        if (test.equals("images/defaultPhoto.png")) {
            return true;
        }

        if (test.matches(PHOTO_VALIDATION_REGEX)) {
            try {
                Image image = ImageIO.read(new URL(test));
                if (image == null) {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
            return true;

        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.value.equals(((Photo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Photo> photoProperty();
    Photo getPhoto();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                && other.getBirthday().equals(this.getBirthday())
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                && other.getPhoto().equals(this.getPhoto());
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Birthday: ")
                .append(getBirthday())
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Photo URL: ")
                .append(getPhoto())
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String birthday;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String photo;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().value;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        photo = source.getPhoto().value;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Birthday birthday = new Birthday(this.birthday);
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Photo photo = new Photo(this.photo);
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case ESCAPE:
            commandTextField.setText("");
            break;
        case ALT:
            keyEvent.consume();
            commandTextField.positionCaret(commandTextField.getText().length());
            break;
        case DELETE:
            keyEvent.consume();
            deleteChunk();
            break;
        case INSERT:
            commandTextField.setText("add " + PREFIX_NAME + " " + PREFIX_PHONE + " " + PREFIX_BIRTHDAY + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_MODULE);
            commandTextField.positionCaret(6);
            break;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Updates the text field by the position of the caret
     * 1. If the caret is in the extreme left position, break.
     * 2. If the caret is at the extreme right position, it will delete a chunk or white spaces or words accordingly
     * 3. If the chunk on the left of the caret are white spaces, it will delete
     * 4. If the chunk on the left of the caret are words, it will delete
     */
    private void deleteChunk() {
        int caretPos = commandTextField.getCaretPosition();
        int newCaretPos;

        if (caretPos == 0) {
            return;
        }

        if (caretPos == commandTextField.getText().length()) {   //caret at the extreme right of command box text
            if (noCharBefore(caretPos)) {
                //remove chunk of space
                newCaretPos = caretPos;

                for (int i = newCaretPos; i > 0; i--) {
                    if (!noCharBefore(newCaretPos)) {
                        break;
                    }
                    newCaretPos -= 1;
                }
            } else {
                //remove chunk of words
                newCaretPos = caretPos;

                for (int i = newCaretPos; i > 0; i--) {
                    if (noCharBefore(newCaretPos)) {
                        break;
                    }
                    newCaretPos -= 1;
                }
            }
        } else if (noCharBefore(caretPos) && caretPos != commandTextField.getText().length()) {
            //remove chunk of space
            int tempCaretPos = caretPos;

            for (int i = tempCaretPos; i > 0; i--) {
                if (!noCharBefore(tempCaretPos)) {
                    break;
                }
                tempCaretPos -= 1;
            }
            newCaretPos = tempCaretPos;

        } else {
            //remove chunk of words
            int tempCaretPos = caretPos;

            for (int i = tempCaretPos; i > 0; i--) {
                if (noCharBefore(tempCaretPos)) {
                    break;
                }
                tempCaretPos -= 1;
            }
            newCaretPos = tempCaretPos;
        }
        newCommandBoxText(newCaretPos, caretPos);
        commandTextField.positionCaret(newCaretPos);
    }

    /**
     * Replaces the command box text with reference of position of new text cursor and position of old text cursor
     * @param newCaretPos
     * @param oldCaretPos
     */
    private void newCommandBoxText(int newCaretPos, int oldCaretPos) {
        String oldCommandBoxText = commandTextField.getText().substring(0, newCaretPos);
        String newCommandBoxText;
        if (oldCaretPos == 0 || oldCaretPos == commandTextField.getText().length()) {
            newCommandBoxText = oldCommandBoxText;
        } else {
            newCommandBoxText = oldCommandBoxText + commandTextField.getText().substring(oldCaretPos);
        }
        commandTextField.setText(newCommandBoxText);
    }

    //Checks if there are any characters before the text cursor
    private boolean noCharBefore(int caretPos) {
        Character charBeforeCaret = commandTextField.getText().charAt(caretPos - 1);
        String toString = Character.toString(charBeforeCaret);
        return (toString.equals(" "));
    }

    //Checks if there are any characters before the text cursor
    //NullPointerException would occur if this function is called when text cursor is at extreme right
    private boolean noCharAfter(int caretPos) {
        Character charAfterCaret = commandTextField.getText().charAt(caretPos);
        String toString = Character.toString(charAfterCaret);
        return (toString.equals(" "));
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Gets text field for test
     */
    public TextField getCommandTextField() {
        return commandTextField;
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label birthday;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        initPhoto(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        person.photoProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(new Image(person.getPhoto().toString(), 120, 120,
                    true, false));
        });
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     *
     * Initialise the photo for each person
     */
    private void initPhoto(ReadOnlyPerson person) {
        imageView.setImage(new Image(person.getPhoto().toString(), 126, 126, true,
                false));
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
    <HBox spacing="5" alignment="CENTER_RIGHT">
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday" />
```
###### \resources\view\PersonListCard.fxml
``` fxml
    <ImageView fx:id="imageView">
      <image>
        <Image url="@../images/defaultPhoto.png"/>
      </image>
    </ImageView>
```
