# karrui
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Homepage updatedHomepage = editPersonDescriptor.getHomepage().orElse(personToEdit.getHomepage());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
        Avatar updatedAvatar = personToEdit.getAvatar(); // edit command does not allow editing avatar

        if (updatedHomepage.value.equals(RESET_HOMEPAGE)) {
            return new Person(updatedName, updatedPhone, updatedEmail,
                    updatedAddress, updatedRemark, updatedAvatar, updatedTags);
        }

        if (personToEdit.isHomepageManuallySet() || !(originalHomepage.toString().equals(updatedHomepage.toString()))) {
            return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                    updatedRemark, updatedAvatar, updatedTags, updatedHomepage);
        } else {
            return new Person(updatedName, updatedPhone, updatedEmail,
                    updatedAddress, updatedRemark, updatedAvatar, updatedTags);
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setHomepage(Homepage homepage) {
            this.homepage = homepage;
        }

        public Optional<Homepage> getHomepage() {
            return Optional.ofNullable(homepage);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }
```
###### \java\seedu\address\logic\commands\RecentCommand.java
``` java
/**
 * Lists all the persons searched by user from the start of app launch.
 */
public class RecentCommand extends Command {
    public static final String COMMAND_WORD = "recent";
    public static final String COMMAND_ALIAS = "rc";
    public static final String MESSAGE_SUCCESS = "Searched persons listed";
    public static final String MESSAGE_NO_RECENT = "You have not yet searched for any persons.";



    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> previousFinds = findHistory.getHistory();

        if (previousFinds.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECENT);
        }

        model.updateFilteredPersonList(new PersonContainsRecentPredicate(previousFinds));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, FindHistory findHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(findHistory);
        requireNonNull(model);
        this.model = model;
        this.findHistory = findHistory;
    }
}
```
###### \java\seedu\address\logic\commands\SetAvatarCommand.java
``` java
/**
 * Changes the avatar of an existing person in the address book
 */
public class SetAvatarCommand extends Command {

    public static final String COMMAND_WORD = "setavatar";
    public static final String COMMAND_ALIAS = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the avatar of the person identified by the index number used in the last person listing."
            + "The image will be sourced from the path URL parameter provided.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AVATAR + "[URL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AVATAR + "http://www.examplepictures.com/image.jpg";

    public static final String MESSAGE_SET_AVATAR_SUCCESS = "Set Avatar for Person: %1$s";
    public static final String MESSAGE_REMOVE_AVATAR_SUCCESS = "Removed Avatar for Person: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_URL_INVALID = "The URL is invalid / is not an image."
            + " Avatar has been set to the default avatar";

    private final Index targetIndex;
    private final Avatar avatar;

    public SetAvatarCommand(Index index, Avatar avatar) {
        requireNonNull(index);
        requireNonNull(avatar);

        this.targetIndex = index;
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSetAvatar = lastShownList.get(targetIndex.getZeroBased());
        String personToSetAvatarPath = personToSetAvatar.getAvatar().path;
        Person editedPerson;

        if ("".equals(avatar.path) && !"".equals(personToSetAvatarPath)) { // delete image from storage
            ProcessImageUtil.removeImageFromStorage(personToSetAvatarPath);
        } else if (!"".equals(personToSetAvatarPath)) {   // has a previously set avatar, remove first before processing
            ProcessImageUtil.removeImageFromStorage(personToSetAvatarPath);
        }

        if (personToSetAvatar.isHomepageManuallySet()) {
            editedPerson = new Person(personToSetAvatar.getName(), personToSetAvatar.getPhone(),
                    personToSetAvatar.getEmail(), personToSetAvatar.getAddress(), personToSetAvatar.getRemark(),
                    avatar, personToSetAvatar.getTags(), personToSetAvatar.getHomepage());
        } else {
            editedPerson = new Person(personToSetAvatar.getName(), personToSetAvatar.getPhone(),
                    personToSetAvatar.getEmail(), personToSetAvatar.getAddress(), personToSetAvatar.getRemark(),
                    avatar, personToSetAvatar.getTags());
        }

        try {
            model.updatePerson(personToSetAvatar, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }


        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Success message if remark added or deleted
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!avatar.path.isEmpty()) {
            return String.format(MESSAGE_SET_AVATAR_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_REMOVE_AVATAR_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetAvatarCommand)) {
            return false;
        }

        // state check
        SetAvatarCommand e = (SetAvatarCommand) other;
        return targetIndex.equals(e.targetIndex)
                && avatar.equals(e.avatar);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all persons in the address book by alphabetical order of contact's name and lists to the user.
 * The order can be either ascending or descending.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all persons in the address book as a list"
            + " sorted by ascending/descending alphabetical order of name."
            + "Command without parameters will be defaulted to ascending order."
            + "Optional Parameters: "
            + "[a] ASCENDING / [d] DESCENDING";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final boolean isDescendingSort;

    public SortCommand(boolean isDescendingSort) {
        this.isDescendingSort = isDescendingSort;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        model.sortContactList(isDescendingSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && (isDescendingSort == (((SortCommand) other).isDescendingSort)));
    }
}
```
###### \java\seedu\address\logic\FindHistory.java
``` java
/**
 * Stores the persons returned by FindCommand.
 */
public class FindHistory {
    private LinkedList<ReadOnlyPerson> userFindHistory;

    public FindHistory() {
        userFindHistory = new LinkedList<>();
    }

    /**
     * Adds {@code person} to the list of persons user found with FindCommand.
     */
    public void add(ReadOnlyPerson person) {
        requireNonNull(person);
        userFindHistory.add(person);
    }

    /**
     * Changes {@code person} to {@code newPerson}
     */
    public void set(ReadOnlyPerson person, ReadOnlyPerson newPerson) {
        if (userFindHistory.contains(person)) {
            userFindHistory.set(userFindHistory.indexOf(person), newPerson);
        }
    }

    /**
     * Removes {@code person} from userFindHistory
     */
    public void deletePerson(ReadOnlyPerson person) {
        userFindHistory.remove(person);
    }

    public void resetData(LinkedList<ReadOnlyPerson> newData) {
        this.userFindHistory = newData;
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<ReadOnlyPerson> getHistory() {
        return new LinkedList<>(userFindHistory);
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_HOMEPAGE, PREFIX_REMARK);

        // only name and phone are mandatory fields
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(ParserUtil.parseValues(argMultimap.getValue(PREFIX_EMAIL))).get();
            Address address = ParserUtil.parseAddress(ParserUtil.parseValues(
                    argMultimap.getValue(PREFIX_ADDRESS))).get();
            Remark remark = ParserUtil.parseRemark(ParserUtil.parseValues(argMultimap.getValue(PREFIX_REMARK))).get();
            Avatar avatar = new Avatar(""); // add command does not allow adding avatar straight away
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            ReadOnlyPerson person;

            if (arePrefixesPresent(argMultimap, PREFIX_HOMEPAGE)) {
                Homepage homepage = ParserUtil.parseHomepage(argMultimap.getValue(PREFIX_HOMEPAGE)).get();
                person = new Person(name, phone, email, address, remark, avatar, tagList, homepage);
            } else {
                person = new Person(name, phone, email, address, remark, avatar, tagList);
            }


            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RecentCommand.COMMAND_WORD:
        case RecentCommand.COMMAND_ALIAS:
            return new RecentCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SetAvatarCommand.COMMAND_WORD:
        case SetAvatarCommand.COMMAND_ALIAS:
            return new SetAvatarCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.map(Remark::new);
    }

    /**
     * Parses a {@code Optional<String> homepage} into an {@code Optional<Homepage>} if {@code homepage} is present.
     * If {@code homepage} is "", returns default homepage constructor.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Homepage> parseHomepage(Optional<String> homepage) throws IllegalValueException {
        requireNonNull(homepage);
        if (homepage.isPresent()) {
            if (homepage.get().equals("")) {
                return Optional.of(new Homepage());
            } else {
                return Optional.of(new Homepage(homepage.get()));
            }
        } else {
            return Optional.empty();
        }
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /** Parses {@code sortOrder} into a {@code boolean} and returns it.
    * @throws IllegalValueException if the specified parameter is invalid (not "a", "d", or "").
    */
    public static boolean parseSort(String sortOrder) throws IllegalValueException {
        String trimmedSortParameter = sortOrder.trim();
        switch (trimmedSortParameter) {
        case "":
        case "a":
            return false;
        case "d":
            return true;
        default:
            throw new IllegalValueException(MESSAGE_INVALID_ARG);
        }
    }

    /**
     * Parse parameters provided, if exist, return value. If value does not exist, return empty string.
     */
    public static Optional<String> parseValues(Optional<String> value) {
        return Optional.of(value.orElse(STRING_IF_EMPTY));
    }

```
###### \java\seedu\address\logic\parser\SetAvatarCommandParser.java
``` java
/**
 * Parses the given {@code String} of arguments in the context of the SetAvatarCommand
 * and returns an SetAvatarCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class SetAvatarCommandParser implements Parser<SetAvatarCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetAvatarCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AVATAR);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAvatarCommand.MESSAGE_USAGE));
        }

        String avatar = argMultimap.getValue(PREFIX_AVATAR).orElse("");

        try {
            return new SetAvatarCommand(index, new Avatar(avatar));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            boolean isDescending = ParserUtil.parseSort(args);
            return new SortCommand(isDescending);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void sortAddressBook(boolean isDescendingSort) {
        persons.sort(isDescendingSort);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortContactList(boolean isDescendingSort) {
        addressBook.sortAddressBook(isDescendingSort);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\Avatar.java
``` java
/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class Avatar {


    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Image not found/ image extension not supported! Only supports \"BMP\", \"GIF\", \"JPEG\", and \"PNG\"\n"
            + "Image must also be 50KB or smaller";
    public static final String MESSAGE_IMAGESIZE_CONSTRAINTS = "Image is too big! Please keep size to 50KB or lower";
    public static final String DEFAULT_AVATAR_FILE_LOCATION = "./data/avatar/";
    public final String path;
    public final String initialUrl;

    /**
     * Validates given avatar.
     * Invokes ProcessImageUtil for processing of storage of avatar
     * @throws IllegalValueException if given path string is invalid.
     */
    public Avatar(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();
        if (!isValidPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        this.initialUrl = trimmedPath;
        this.path = ProcessImageUtil.writeImageToStorage(trimmedPath);
    }

    /**
     * Returns true if a given string is a valid image filepath
     */
    public static boolean isValidPath(String path) {
        if ("".equals(path)) {  // default
            return true;
        }
        return isImageValid(path) && isImageCorrectSize(path);
    }

    /**
     * Returns true if image can be loaded from path
     */
    public static boolean isImageValid(String path) {
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            return image != null;
        } catch (IOException ioe) {
            // invalid URL, check if is file path
            try {
                BufferedImage image = ImageIO.read(new File(path));
                return image != null;
            } catch (IOException ioe2) {
                return false;
            }
        }
    }

    /**
     * Returns true if image is smaller than 50KB.
     * (This is because if the image is too big, the application will start slowing down)
     */
    public static boolean isImageCorrectSize(String path) {
        if ("".equals(path)) {  // default
            return true;
        }
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            // invalid URL, or is file path, check file size instead
            return ((new File(path).length()) / 1024) < 50;
        }
        int fileSize = getFileSize(url) / 1024;     // file size in KBs
        return fileSize < 50 && fileSize > 0;
    }

    /**
     * Obtains file size of image using a HEAD http request
     */
    private static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            assert conn != null;
            conn.disconnect();
        }
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.initialUrl.equals(((Avatar) other).initialUrl)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Homepage.java
``` java
/**
 * Represents a Person's homepage in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHomepage(String)}
 */
public class Homepage {
    public static final String MESSAGE_HOMEPAGE_CONSTRAINTS = "Person homepage should be a valid URL";
    public static final String RESET_HOMEPAGE = "404";
    public final String value;

    /**
     * Validates given homepage.
     *
     * @throws IllegalValueException if given homepage string is invalid.
     */
    public Homepage(String homepage) throws IllegalValueException {
        requireNonNull(homepage);
        String trimmedHomepage = homepage.trim();
        if (!isValidHomepage(trimmedHomepage)) {
            throw new IllegalValueException(MESSAGE_HOMEPAGE_CONSTRAINTS);
        }
        this.value = trimmedHomepage;
    }

    /**
     * For sole use by {@code ParserUtil} to reset homepage
     */
    public Homepage() {
        this.value = RESET_HOMEPAGE;
    }

    /**
     * Returns if a given string is a valid person homepage.
     */
    public static boolean isValidHomepage(String test) {
        try {
            URL url = new URL(test);
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Homepage // instanceof handles nulls
                && this.value.equals(((Homepage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\PersonContainsRecentPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson} matches any of the persons in the backing list.
 */
public class PersonContainsRecentPredicate implements Predicate<ReadOnlyPerson> {
    private final List<ReadOnlyPerson> recentPersons;

    public PersonContainsRecentPredicate(List<ReadOnlyPerson> recentPersons) {
        this.recentPersons = recentPersons;
    }

    /**
     * Tests that a {@code ReadOnlyPerson} matches any of the persons in the backing list.
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        return recentPersons.stream().anyMatch(p -> p.equals(person));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsRecentPredicate// instanceof handles nulls
                && this.recentPersons.equals(((PersonContainsRecentPredicate) other).recentPersons)); // state check
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    /**
     * Formats the person as text, showing all available contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ").append(getPhone());
        if (!"".equals(getEmail().value)) {
            builder.append(" Email: ").append(getEmail());
        }
        if (!"".equals(getAddress().value)) {
            builder.append(" Address: ").append(getAddress());
        }
        builder.append(" Homepage: ").append(getHomepage());
        if (!getTags().isEmpty()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
```
###### \java\seedu\address\storage\AvatarStorage.java
``` java
/**
 * A class to access the avatar file directory path on the hard disk.
 */
public class AvatarStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String directoryPath;

    public AvatarStorage(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
        createDirectory(directoryPath);
    }

    /**
     * Creates a directory in specified path if directory does not exist
     */
    public void createDirectory(String directoryPath) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            logger.info("Failed to create directory at " + directoryPath);
        }
    }

    @Override
    public String toString() {
        return "Local avatar directory location : " + directoryPath;
    }
}
```
###### \java\seedu\address\storage\util\ProcessImageUtil.java
``` java
/**
 * Utility class to write an image from an URL to a file for the Avatar class
 */
public class ProcessImageUtil {
    public static final String MESSAGE_FILE_NOT_FOUND = "%s: no such" + " file or directory%n";
    private static final Logger logger = LogsCenter.getLogger(ProcessImageUtil.class);

    /**
     * Writes the image URL path provided into an image file
     */
    public static String writeImageToStorage(String path) throws IllegalValueException {
        String standardPath = path.replace('\\', '/');
        if ("".equals(standardPath) || standardPath.startsWith(DEFAULT_AVATAR_FILE_LOCATION)) {
            return standardPath;
        }
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            File file = getUniqueFile();
            ImageIO.write(image, "jpg", file);
            logger.info("Image from " + path + " written to to file: " + file.getName());
            return file.getPath().replace('\\', '/');
        } catch (IOException ioe) {
            logger.info("Failed to create image from path: " + path);
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns a File with an unique file path by using {@code Random} class.
     */
    private static File getUniqueFile() {
        Random random = new Random();
        // random.nextInt() + checking if file exists assures uniqueness of name of created file
        File file = new File(DEFAULT_AVATAR_FILE_LOCATION + random.nextInt() + ".jpg");
        while (file.exists()) {
            file = new File(DEFAULT_AVATAR_FILE_LOCATION + random.nextInt() + ".jpg");
        }
        return file;
    }

    /**
     * Removes the image file currently in storage
     */
    public static void removeImageFromStorage(String path) {
        File file = new File(path);
        if (file.delete()) {
            logger.info("File at path: " + path + " is deleted");
        } else {
            logger.severe("Failed to delete file at " + path);
        }
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the correct image to the person.
     * If url is "", default display picture will be assigned, else image from URL will be assigned
     */
    private void initImage(ReadOnlyPerson person) {
        String path = person.getAvatar().toString();
        Image image;
        if (!"".equals(path)) {   // not default image
            File file = new File(path);
            image = new Image(file.toURI().toString());
            avatar.setImage(image);
            avatar.setFitHeight(90);
            avatar.setPreserveRatio(true);
            avatar.setCache(true);
        }
    }
```
