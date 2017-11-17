# LinusMelb
###### \seedu\address\commons\events\ui\BrowserJumpToHomePage.java
``` java
package seedu.address.commons.events.ui;

import java.net.URL;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents the view change in the browser panel
 */
public class BrowserJumpToHomePage extends BaseEvent {

    private URL url;

    public BrowserJumpToHomePage(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public URL getHomeUrl() {
        return url;
    }

}
```
###### \seedu\address\commons\events\ui\PersonPanelSelectionChangedEvent.java
``` java
    private final int backToHomePageValue = 0;
```
###### \seedu\address\commons\events\ui\PersonPanelSelectionChangedEvent.java
``` java
    public int getBackToHomePageValue() {
        return this.backToHomePageValue;
    }
```
###### \seedu\address\logic\commands\AddAvatarCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE_URL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Updates the avatar picture of an existing person in the address book.
 */

public class AddAvatarCommand extends Command {
    public static final String COMMAND_WORD = "avatar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the avatar picture of the person identified "
            + "by the index number. "
            + "Existing avatar picture will be replaced by the new picture.\n"
            + "Parameters: INDEX of person (positive integer) "
            + "[u/image URL]\n"
            + "Example of using online image: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE_URL
            + "http://139.59.227.237/public/avatar3.jpg\n"
            + "Example of using online image: " + COMMAND_WORD + " 2 "
            + PREFIX_IMAGE_URL + "http://139.59.227.237/public/avatar2.jpg\n"
            + "Example of using online image: " + COMMAND_WORD + " 3 "
            + PREFIX_IMAGE_URL + "http://139.59.227.237/public/avatar1.jpg\n";

    public static final String MESSAGE_UPDATE_AVATAR_PIC_SUCCESS = "Update avatar picture for Person: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "Please enter a valid image URL.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_IS_DEFAULT = "You must specify the Avatar URL\n"
            + "Format: avatar INDEX u/Image url";

    private final Index index;
    private final Avatar avatar;

    /**
     *
     * @param index of the person in the filtered person list to update avatar picture
     * @param avatar of the image to be used as the Avatar picture
     */
    public AddAvatarCommand(Index index, Avatar avatar) {
        requireNonNull(index);
        requireNonNull(avatar);

        this.index = index;
        this.avatar = avatar;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateAvatarPic = lastShownList.get(index.getZeroBased());
        Person updatedAvatarPicPerson = new Person(personToUpdateAvatarPic);
        Avatar newAvatar;

        if (avatar.toString().equalsIgnoreCase(Avatar.DEFAULT_URL)) {
            throw new CommandException(MESSAGE_IS_DEFAULT);
        }

        if (avatar.toString().compareTo(Avatar.DEFAULT_URL) == 0 && avatar.toString() != "") {

            newAvatar = avatar;

        } else {
            String newFile = Avatar.DEFAULT_URL;

            if (!Files.isDirectory(Paths.get("avatars"))) {
                try {
                    Files.createDirectory(Paths.get("avatars"));
                } catch (IOException ioe) {
                    throw new CommandException("avatars directory failed to be created");
                }
            }

            if (personToUpdateAvatarPic.getAvatarPic().toString() != "") {

                /*
                *  Validates avatar image
                * */
                String imgExtension = "";

                int i = avatar.toString().lastIndexOf('.');
                if (i > 0) {
                    imgExtension = avatar.toString().substring(i + 1);
                }

                List validExtension = Arrays.asList("jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF");
                if (validExtension.contains(imgExtension)) {
                    newFile = "avatars/" + new Date().getTime() + '.' + imgExtension;
                } else {
                    newFile = "avatars/" + new Date().getTime() + ".png";
                }

            } else {
                throw new CommandException(MESSAGE_NOT_UPDATED);
            }


            if (!Files.exists(Paths.get(newFile))) {
                try {
                    Files.createFile(Paths.get(newFile));
                } catch (IOException ioe) {
                    throw new CommandException("New file failed to be created");
                }
            }
            try {
                URL url = new URL(avatar.toString());

                /*
                * Updates avatar with url link
                * */
                newAvatar = new Avatar(url.toString());
            } catch (IOException ioe) {
                throw new CommandException("Image failed to download");
            } catch (IllegalValueException ive) {
                throw new CommandException(ive.getMessage());
            }
        }
        updatedAvatarPicPerson.setAvatarPic(newAvatar);

        /*
        *  Updates the avatar for the person based on its index
        * */
        EventsCenter.getInstance().post(new JumpToListRequestEvent(this.index));

        try {
            model.updatePerson(personToUpdateAvatarPic, updatedAvatarPicPerson);

        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person is missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String resultMessage = String.format(MESSAGE_UPDATE_AVATAR_PIC_SUCCESS, personToUpdateAvatarPic.getName());

        return new CommandResult(resultMessage);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAvatarCommand // instanceof handles nulls
                && this.index.equals(((AddAvatarCommand) other).index)
                && this.avatar.equals(((AddAvatarCommand) other).avatar)); // state check
    }

}
```
###### \seedu\address\logic\commands\HomeCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BrowserJumpToHomePage;

/**
 * Goes back to home page
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_SUCCESS = "Successfully went back to home page";

    @Override
    public CommandResult execute() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        URL url = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        EventsCenter.getInstance().post(new BrowserJumpToHomePage(url));

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \seedu\address\logic\parser\AddAvatarCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE_URL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAvatarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * Parses input arguments and creates a new AddAvatarCommand object
 */
public class AddAvatarCommandParser implements Parser<AddAvatarCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAvatarCommand
     * and returns an AddAvatarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public AddAvatarCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IMAGE_URL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAvatarCommand.MESSAGE_USAGE));
        }

        Person updatedPerson = new Person(SampleDataUtil.getSamplePersons()[0]);

        try {
            ParserUtil.parseImageUrl(argMultimap.getValue(PREFIX_IMAGE_URL)).ifPresent(updatedPerson::setAvatarPic);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddAvatarCommand(index, updatedPerson.getAvatarPic());
    }
}
```
###### \seedu\address\logic\parser\AddressBookParser.java
``` java
        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        case AddAvatarCommand.COMMAND_WORD:
            return new AddAvatarCommandParser().parse(arguments);

```
###### \seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> imageURL} into an {@code Optional<Avatar>} if {@code imageURL} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Avatar> parseImageUrl(Optional<String> imageUrl) throws IllegalValueException {
        requireNonNull(imageUrl);
        return imageUrl.isPresent() ? Optional.of(new Avatar(imageUrl.get())) : Optional.empty();
    }
```
###### \seedu\address\model\person\Avatar.java
``` java
import static java.util.Objects.requireNonNull;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's avatar picture in the address book.
 */
public class Avatar {
    public static final String MESSAGE_AVATAR_PIC_CONSTRAINTS =
            "Person's avatar must be a valid image URL";
    public static final String MESSAGE_AVATAR_PIC_EXPIRED =
            "This avatar is invalid, select another image URL";
    public static final String DEFAULT_URL = "http://139.59.227.237/public/default.jpg";

    /*
     * The first character of the address must not be a whitespace
     */
    public static final String AVATAR_PIC_VALIDATION_REGEX = "[^\\s].*";

    public final String source;

    public Avatar() {
        source = DEFAULT_URL;
    }

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given AvatarPic string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        requireNonNull(url);

        int isValidUrl = isValidUrl(url);

        /*
        *  Expired Image URL
        * */
        if (isValidUrl == -2) {

            throw new IllegalValueException(MESSAGE_AVATAR_PIC_EXPIRED);
        }

        /*
        * Invalid Image URL
        * */
        if (isValidUrl == -1) {

            throw new IllegalValueException(MESSAGE_AVATAR_PIC_CONSTRAINTS);
        }

        source = url;
    }

    /**
     * Returns true if a given string is a valid image URL.
     */
    public static int isValidUrl(String test) {

        if (test.matches(AVATAR_PIC_VALIDATION_REGEX)) {
            try {
                Image img = ImageIO.read(new URL(test));
                if (img == null) {
                    return -1;
                }
            } catch (IOException e) {
                if (test.compareTo(DEFAULT_URL) == 0) {
                    return 0;
                } else {
                    return -2;
                }
            }
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return source;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.source.equals(((Avatar) other).source)); // state check
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }
}
```
###### \seedu\address\model\util\SampleDataUtil.java
``` java
    private static String[] firstNames = {"Roy", "David", "Alex", "Linus", "Siri", "Hector", "Achilles", "Odysseus",
        "Jack", "Donald"};
    private static String[] lastNames = {"Herason", "Trump", "Thompson", "Curry", "Westbrook", "Harden", "O'niel",
        "Clinton", "Obama", "Washington"};
    private static String[] emails = {"gmail.com", "yahoo", "outlook", "hotmail", "qq.com", "sina.com"};
    private static String[] tags = {"colleagues", "friends", "president", "students", "customers", "lawyers",
        "farmers"};
    private static String[] phones = {"90909099", "87878888", "33222111", "84456723", "90111123", "12345678",
        "98363363"};
    private static String[] birthdays = {"01/09/1988", "03/08/1975", "02/02/1989", "31/12/1966", "28/05/1956",
        "03/02/1999", "13/12/2000"};

    private static int sampleDataSize = 6;

    public static Person[] getSamplePersons() {
        /*
         *  Sample data
         *  It generates 100 test data
         * */
        Random rand = new Random();
        String firstName = null;
        ArrayList<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < sampleDataSize; i++) {

            firstName = firstNames[i % (sampleDataSize)];

            for (int j = 0; j < sampleDataSize - 1; j++) {

                try {

                    persons.add(generatePerson(firstName, j, rand));

                } catch (IllegalValueException e) {

                    throw new AssertionError("sample data cannot be invalid", e);

                }
            }
        }
        return persons.toArray(new Person[persons.size()]);
    }

    /**
     * Returns a tag set containing the list of strings given baesd on input.
     */
    public static Set<Tag> generateTags(int i) throws IllegalValueException {

        HashSet<Tag> addTags = new HashSet<>();
        for (int j = 0; j < i; j++) {
            addTags.add(new Tag(tags[j]));
        }
        return addTags;
    }

    /**
     * Returns a person object that generated randomly.
     */
    public static Person generatePerson(String firstName, int j, Random rand) throws IllegalValueException {

        /*
        *  Those numbers are totally arbitrary for generating sample data
        * */
        j = j % 7;
        String lastName = lastNames[j % (sampleDataSize - 2)];
        String phone = phones[j];
        String email = emails[j % (sampleDataSize - 2)];
        String units = Integer.toString(j);
        String score = Integer.toString(j);
        Set<Tag> tag = generateTags(j);

        if (j / 3 == 1) {
            return new Person(new Name(firstName + " " + lastName), new Phone(phone),
                    new Birthday("No Birthday Listed"),
                    new Email(firstName + "@" + email),
                    new Address("Blk 45 Aljunied Street 85, #" + units),
                    new Score(score), tag);
        }
        return new Person(new Name(firstName + " " + lastName), new Phone(phone),
                new Birthday(generateBirthday(j)),
                new Email(firstName + "@" + email),
                new Address("Victoria 34 Arran Street 45, #" + units),
                new Score(score), tag);

    }

    /**
     * Returns a birthday string based on input.
     */
    public static String generateBirthday(int i) {
        int n = i % 7;
        return birthdays[n];
    }

```
###### \seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement
    private String avatar;
```
###### \seedu\address\storage\XmlAdaptedPerson.java
``` java
        Avatar tempAvatar;
        try {
            tempAvatar = new Avatar(this.avatar);
        } catch (IllegalValueException e) {
            tempAvatar = new Avatar();
        }
        return new Person(name, phone, birthday, email, address, score, tags, tempAvatar);
```
###### \seedu\address\ui\BrowserPanel.java
``` java
    public static final String BROWSER_PAGE = "BrowserPanel.html";
```
###### \seedu\address\ui\BrowserPanel.java
``` java
    private int backToHomePage = 0;
```
###### \seedu\address\ui\BrowserPanel.java
``` java
        URL addressPage = MainApp.class.getResource(FXML_FILE_FOLDER + BROWSER_PAGE);
```
###### \seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) throws IOException {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson p = event.getNewSelection().person;

        String address = p.getAddress().toString();
        String name = p.getName().toString();
        String emails = p.getEmail().toString();
        String phones = p.getPhone().toString();
        String tags = p.getOnlyTags().toString();
        String avatar = p.getAvatarPic().toString();

        backToHomePage = event.getBackToHomePageValue();

        browser.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED && backToHomePage == 0) {
                WebEngine panel = browser.getEngine();

                panel.executeScript("document.setName(\"" + name + "\")");
                panel.executeScript("document.setAddress(\"" + address + "\")");
                panel.executeScript("document.setEmail(\"" + emails + "\")");
                panel.executeScript("document.setPhone(\"" + phones + "\")");
                panel.executeScript("document.setTags(\"" + tags + "\")");
                panel.executeScript("document.setAvatar(\"" + avatar + "\")");
            }
        });

        loadBrowserPage(event.getNewSelection().person);

    }

    @Subscribe
    private void handleGoBackToHomePageEvent(BrowserJumpToHomePage event) throws IOException {

        browser.getEngine().load(event.getHomeUrl().toExternalForm());
        backToHomePage = 1;

    }
```
###### \seedu\address\ui\MainWindow.java
``` java
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(), filteredPersonList);
```
###### \seedu\address\ui\MainWindow.java
``` java
    /**
     * Changes the theme color to dark of the program
     */
    @FXML
    private void handleDarkTheme() {

        int size = scene.getRoot().getStylesheets().size();

        for (int i = 0; i < size; i++) {
            scene.getRoot().getStylesheets().remove(0);
        }

        scene.getRoot().getStylesheets().add("view/DarkTheme.css");
        scene.getRoot().getStylesheets().add("view/Extensions.css");

    }

    /**
     * Changes the theme color to light of the program
     */
    @FXML
    private void handleLightTheme() {

        int size = scene.getRoot().getStylesheets().size();
        for (int i = 0; i < size; i++) {

            scene.getRoot().getStylesheets().remove(0);

        }
        scene.getRoot().getStylesheets().add("view/LightTheme.css");

    }
```
###### \seedu\address\ui\StatusBarFooter.java
``` java
    public static final String SYNC_PERSONLIST_UPADTED_SIZE = "Total size: %d, ";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
```
###### \seedu\address\ui\StatusBarFooter.java
``` java
    private ObservableList<ReadOnlyPerson> filteredPersonList;
```
###### \seedu\address\ui\StatusBarFooter.java
``` java
    public StatusBarFooter(String saveLocation, ObservableList<ReadOnlyPerson> filteredPersonList) {
        super(FXML);
        this.filteredPersonList = filteredPersonList;
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }
```
