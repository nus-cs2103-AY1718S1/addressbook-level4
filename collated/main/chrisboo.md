# chrisboo
###### \java\seedu\address\commons\events\ui\SwitchAddressBookRequestEvent.java
``` java
/**
 * Indicates a request to switch (open existing / create new) AddressBook
 */
public class SwitchAddressBookRequestEvent extends BaseEvent {

    private String fileName;
    private String filePath;
    private boolean isNewFile;

    public SwitchAddressBookRequestEvent(File file, boolean isNewFile) {
        fileName = file.getName();
        filePath = file.getPath();
        this.isNewFile = isNewFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isOpenNewAddressBook() { return isNewFile; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\util\FileUtil.java
``` java
    /**
     * Return a file from FileChooser.
     * If {@code isNewFile} is true, opens a FileChooser that creates/overwrite file.
     * Otherwise, opens a FileChooser that selects existing file.
     */
    public static File getFileFromChooser(boolean isNewFile) {
        FileChooser fileChooser = new FileChooser();

        // Set and add extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        return isNewFile ? fileChooser.showSaveDialog(new Stage())
                         : fileChooser.showOpenDialog(new Stage());
    }
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    private FindPersonDescriptor findPersonDescriptor;

    public FindCommand(FindPersonDescriptor findPersonDescriptor) {
        requireNonNull(findPersonDescriptor);

        this.findPersonDescriptor = new FindPersonDescriptor(findPersonDescriptor);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(person -> findPersonDescriptor.match(person));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && this.findPersonDescriptor.equals(((FindCommand) other).findPersonDescriptor)); // state check
    }

    /**
     * Stores the details to find of the person.
     */
    public static class FindPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Birthday birthday;
        private Website website;
        private Set<Tag> tags;

        public FindPersonDescriptor() {
        }

        public FindPersonDescriptor(FindPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.birthday = toCopy.birthday;
            this.website = toCopy.website;
            this.tags = toCopy.tags;
        }

        /**
         * @return false if no fields are provided
         */
        public boolean allNull() {
            return this.name == null
                && this.phone == null
                && this.email == null
                && this.address == null
                && this.birthday == null
                && this.website == null
                && this.tags == null;
        }

        /**
         * @param other to check
         * @return true if other matches all fields
         */
        public boolean match(Object other) {
            if (!(other instanceof Person)) {
                return false;
            }
            if (this.name != null && !match(this.name, ((Person) other).getName())) {
                return false;
            }
            if (this.phone != null && !match(this.phone, ((Person) other).getPhone())) {
                return false;
            }
            if (this.email != null && !this.email.equals(((Person) other).getEmail())) {
                return false;
            }
            if (this.address != null && !this.address.equals(((Person) other).getAddress())) {
                return false;
            }
            if (this.website != null && !this.website.equals(((Person) other).getWebsite())) {
                return false;
            }
            if (this.birthday != null && !this.birthday.equals(((Person) other).getBirthday())) {
                return false;
            }
            if (this.tags != null && !match(this.tags, ((Person) other).getTags())) {
                return false;
            }

            return true;
        }

        /**
         * @param predicate
         * @param test
         * @return true if test contains predicate
         */
        private boolean match(Name predicate, Name test) {
            if (predicate == test) {
                return true;
            }
            if (predicate == null || test == null) {
                return false;
            }

            String[] splitPredicate = predicate.toString().toUpperCase().split("\\s+");

            for (String keyword : splitPredicate) {
                if (keyword.equals("")) {
                    continue;
                }
                if (test.toString().toUpperCase().contains(keyword)) {
                    return true;
                }
            }

            return false;
        }

        /**
         *
         * @param predicate
         * @param test
         * @return true if predicate is a substring of test
         */
        private boolean match(Phone predicate, Phone test) {
            if (predicate == test) {
                return true;
            }
            if (predicate == null || test == null) {
                return false;
            }

            return test.toString().contains(predicate.toString());
        }

        /**
         * @param predicate
         * @param test
         * @return true if test contains a tag that is among the predicate
         */
        private boolean match(Set<Tag> predicate, Set<Tag> test) {
            for (Tag predicateTag : predicate) {
                for (Tag testTag : test) {
                    if (predicateTag.equals(testTag)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            if (phone.value != null) {
                this.phone = phone;
            }
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            if (email.value != null) {
                this.email = email;
            }
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            if (address.value != null) {
                this.address = address;
            }
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setBirthday(Birthday birthday) {
            if (birthday.value != null) {
                this.birthday = birthday;
            }
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setWebsite(Website website) {
            if (website.value != null) {
                this.website = website;
            }
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindPersonDescriptor)) {
                return false;
            }

            // state check
            FindPersonDescriptor e = (FindPersonDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getBirthday().equals(e.getBirthday())
                && getWebsite().equals(e.getWebsite())
                && getTags().equals(e.getTags());
        }
    }
```
###### \java\seedu\address\logic\commands\NewCommand.java
``` java
/**
 * Open Address Book
 */
public class NewCommand extends Command {

    public static final String COMMAND_SHORT = "n";
    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Open a new DeathNote. \n"
        + "Paremeters: FILEPATH \n"
        + "Example: " + COMMAND_WORD + " C:\\Users\\crispy\\Downloads\\NewDeathNote.xml";

    public static final String MESSAGE_OPEN_DEATHNOTE_SUCCESS = "Opened DeathNote: %1$s";

    private final File file;

    public NewCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isFileExists(file)) {
            throw new CommandException(Messages.MESSAGE_EXISTING_FILE);
        }

        EventsCenter.getInstance().post(new SwitchAddressBookRequestEvent(file, true));
        return new CommandResult(String.format(MESSAGE_OPEN_DEATHNOTE_SUCCESS, file.getPath()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NewCommand // instanceof handles nulls
            && this.file.equals(((NewCommand) other).file)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\OpenCommand.java
``` java
/**
 * Open Address Book
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";
    public static final String COMMAND_SHORT = "o";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Open a different DeathNote. \n"
        + "Paremeters: LOCATION \n"
        + "Example: " + COMMAND_WORD + " C:\\Users\\crispy\\Downloads\\DeathNote.xml";

    public static final String MESSAGE_OPEN_DEATHNOTE_SUCCESS = "Opened DeathNote: %1$s";

    private final File file;

    public OpenCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!isFileExists(file)) {
            throw new CommandException(Messages.MESSAGE_INVALID_FILE_PATH);
        }

        EventsCenter.getInstance().post(new SwitchAddressBookRequestEvent(file, false));
        return new CommandResult(String.format(MESSAGE_OPEN_DEATHNOTE_SUCCESS, file.getPath()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OpenCommand // instanceof handles nulls
            && this.file.equals(((OpenCommand) other).file)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case OpenCommand.COMMAND_SHORT:
        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case NewCommand.COMMAND_SHORT:
        case NewCommand.COMMAND_WORD:
            return new NewCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\NewCommandParser.java
``` java
/**
 * Parses input arguments and creates a new NewCommand object
 */
public class NewCommandParser implements Parser<NewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NewCommand
     * and returns an NewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NewCommand parse(String args) throws ParseException {
        try {
            File file = ParserUtil.parseFile(args);
            return new NewCommand(file);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\OpenCommandParser.java
``` java
/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns an OpenCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        try {
            File file = ParserUtil.parseFile(args);
            return new OpenCommand(file);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parse {@code path} into a {@code File} and returns it. Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the file does not exist
     */
    public static File parseFile(String path) throws IllegalValueException {
        String trimmedAddress = path.trim();
        File file = new File(trimmedAddress);
        if (!getExtension(file).equals(".xml")) {
            throw new IllegalValueException(MESSAGE_INVALID_FILE);
        }
        return file;
    }
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleSwitchAddressBookRequestEvent(SwitchAddressBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        try {
            updateConfig("config.json", event.getFileName());
            updateUserPrefs("preferences.json", event.getFilePath(), event.getFileName());

            restart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Address)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Address) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Address) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Birthday)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Birthday) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Birthday) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Email)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Email) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Email) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Phone.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Phone)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Phone) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Phone) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Website.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Website)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Website) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Website) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\storage\JsonUserPrefsStorage.java
``` java
    /**
     * Update the addressBookFilePath and addressBookName fields in preferences.json
     */
    public static void updateUserPrefs(String userPrefsFilePath, String addressBookFilePath, String addressBookName)
        throws DataConversionException, IOException {
        UserPrefs userPrefs = readUserPrefs(userPrefsFilePath).get();
        userPrefs.setAddressBookFilePath(addressBookFilePath);
        userPrefs.setAddressBookName(addressBookName);
        saveUserPrefs(userPrefs, userPrefsFilePath);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens a FileChooser to let the user select an address book to save.
     */
    @FXML
    private void handleNew() {
        File file = FileUtil.getFileFromChooser(true);

        raise(new SwitchAddressBookRequestEvent(file, true));
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        File file = FileUtil.getFileFromChooser(false);

        raise(new SwitchAddressBookRequestEvent(file, false));
    }
```
