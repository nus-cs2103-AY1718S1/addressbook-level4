# seankwekjk
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setSocial(String url) {
            this.url = url;
        }

        public Optional<String> getSocial() {
            return Optional.ofNullable(url);
        }

```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Adds, removes and edits remarks of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person in the address book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_REMARK
            + "Likes to drink coffee.";

    public static final String REMARK_EDIT_SUCCESS = "Remark added.";
    public static final String REMARK_CLEAR_SUCCESS = "Remark cleared.";

    private Remark remark;
    private Index index;

    /**
     * @param remark text of the remark
     * @param index of the person whose remark is being modified
     */
    public RemarkCommand(Remark remark, Index index) {
        this.remark = remark;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                                personToEdit.getAddress(), personToEdit.getBirthday(), remark, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(successMessageType(editedPerson));
    }

    /**
     * Creates and returns a {@code String} based on the edited Remark of {@code personToEdit}
     */
    private String successMessageType(ReadOnlyPerson personToEdit) {
        if (!remark.getRemarkText().isEmpty()) {
            return String.format(REMARK_EDIT_SUCCESS, personToEdit);
        } else {
            return String.format(REMARK_CLEAR_SUCCESS, personToEdit);
        }
    }

    public Remark getRemark() {
        return remark;
    }

    public Index getIndex() {
        return index;
    }
}
```
###### \java\seedu\address\logic\commands\ToggleCommand.java
``` java
/**
 * Toggles Browser function of the Select Command.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String TOGGLE_SUCCESS = "Browser Panel Toggled to ";

    @Override
    public CommandResult execute() throws CommandException {
        BrowserPanel.setBrowserMode();
        return new CommandResult(composeCommandResult());
    }

    /**
    * Composes the Command Result @String based on the status of the toggled boolean variable
    */
    private String composeCommandResult() {
        if (BrowserPanel.getBrowserMode()) {
            return TOGGLE_SUCCESS + "Display Address";
        }   else {
            return TOGGLE_SUCCESS + "Display Social Media";
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommand();

```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    @Override
    public RemarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);
        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    RemarkCommand.MESSAGE_USAGE));
        }

        try {
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();

            return new RemarkCommand(remark, index);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Only url is allowed to be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Birthday birthday,
                  Remark remark, String url, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, birthday, url, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.remark = new SimpleObjectProperty<>(remark);
        this.social = new SimpleObjectProperty<>(url);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public Remark getRemark() {
        return remark.get();
    }

    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    public String getSocialMedia() {
        return social.get();
    }

    public ObjectProperty<String> socialProperty() {
        return social;
    }

    public void setSocialMedia(String social) {
        this.social.set(requireNonNull(social));
    }

```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 *Represents a Remark in the AddressBook.
 */

public class Remark {
    private String remarkText;

    /**
     * Validates given Remark.
     */
    public Remark(String text) {
        try {
            this.remarkText = text.trim();
        } catch (NullPointerException npe) {
            remarkText = ("");
        }
    }

    /**
     * Returns remarkText for checking operations
     */
    public String getRemarkText() {
        return remarkText;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + remarkText + ']';
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadPersonPage(ReadOnlyPerson pers) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX
                + pers.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));
    }

    private void loadSocialPage(ReadOnlyPerson pers) {
        loadPage(SOCIAL_MEDIA_URL_PREFIX + pers.getSocialMedia());
    }

    public static Boolean getBrowserMode() {
        return browserMode;
    }

    public static void setBrowserMode() {
        browserMode = !browserMode;
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (browserMode) {
            loadPersonPage(event.getNewSelection().person);
        }   else {
            loadSocialPage(event.getNewSelection().person);
        }
    }
}
```
