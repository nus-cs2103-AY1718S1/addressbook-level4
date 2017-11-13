# liliwei25
###### \java\seedu\address\commons\events\ui\ChangeImageEvent.java
``` java
/**
 * Represents a image changing function call by user
 */
public class ChangeImageEvent extends BaseEvent {
    private final ReadOnlyPerson person;

    public ChangeImageEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\commons\events\ui\MapPersonEvent.java
``` java
/**
 * Represents a mapping function call by user
 */
public class MapPersonEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public MapPersonEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\BirthdayCommand.java
``` java
/**
 * Adds or Edits birthday field of selected person
 */
public class BirthdayCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "birthday";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[BIRTHDAY (dd-mm-yyyy)]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "12-05-2016";

    public static final String MESSAGE_BIRTHDAY_PERSON_SUCCESS = "Birthday Updated success: %1$s";

    private final Index index;
    private final Birthday birthday;

    public BirthdayCommand (Index index, Birthday birthday) {
        requireNonNull(index);
        requireNonNull(birthday);

        this.index = index;
        this.birthday = birthday;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ReadOnlyPerson editedPerson = getEditedPerson(personToEdit);

        updateModel(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_BIRTHDAY_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates a new {@code Person} with new person data
     *
     * @param personToEdit {@code Person} with old data
     * @return {@code Person} with new data
     */
    private Person getEditedPerson(ReadOnlyPerson personToEdit) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getRemark(), birthday,
                personToEdit.getTags(), personToEdit.getPicture(), personToEdit.getFavourite());
    }

    /**
     * Updates the model with the updated person
     *
     * @param personToEdit Old person data
     * @param editedPerson New person data
     * @throws CommandException when the new person already exists in the address book
     */
    private void updateModel(ReadOnlyPerson personToEdit, ReadOnlyPerson editedPerson) throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateListToShowAll();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayCommand // instanceof handles nulls
                && index.equals(((BirthdayCommand) other).index)
                && birthday.equals(((BirthdayCommand) other).birthday));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String deletedPersons = deleteAllSelectedPersonFromAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersons));
    }

    /**
     * Deletes all the selected person from address book and returns a StringJoiner containing their names
     *
     * @return A {@code StringJoiner} that includes all the names that were deleted
     * @throws CommandException when person selected is not found
     */
    private String deleteAllSelectedPersonFromAddressBook() throws CommandException {
        StringJoiner joiner = new StringJoiner(COMMA);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (int i = targetIndex.length - 1; i >= 0; i--) {
            if (targetIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex[i].getZeroBased());

            deletePersonFromAddressBook(joiner, personToDelete);
        }
        return joiner.toString();
    }

    /**
     * Delete selected person from address book
     *
     * @param joiner {@code StringJoiner} to join several names together if necessary
     * @param personToDelete Selected person
     */
    private void deletePersonFromAddressBook(StringJoiner joiner, ReadOnlyPerson personToDelete) {
        try {
            model.deletePerson(personToDelete);
            joiner.add(personToDelete.getName().toString());
        } catch (PersonNotFoundException pnfe) {
            assert false : MESSAGE_MISSING_PERSON;
        }
    }

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
        Birthday updatedBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        ProfilePicture picture = personToEdit.getPicture();
        Favourite favourite = personToEdit.getFavourite();
        NumTimesSearched numTimesSearched = personToEdit.getNumTimesSearched();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark, updatedBirthday,
                updatedTags, picture, favourite, numTimesSearched);
    }
```
###### \java\seedu\address\logic\commands\ImageCommand.java
``` java
/**
 * Command to add/edit/remove image of Person
 */
public class ImageCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "image";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the profile picture of the specified person in the address book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    static final String MESSAGE_IMAGE_SUCCESS = "Changed Profile Picture: %1$s";
    static final String DEFAULT = "default";

    public final Index index;
    public final boolean remove;

    public ImageCommand(Index index, boolean remove) {
        requireNonNull(index);

        this.index = index;
        this.remove = remove;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = updateAddressBook(lastShownList);
        return new CommandResult(String.format(MESSAGE_IMAGE_SUCCESS, personToEdit));
    }

    /**
     * Updates address book with new {@code Person} with new profile picture
     *
     * @param lastShownList List of all current {@code Person} in address book
     * @return Edited {@code Person}
     * @throws CommandException when a duplicate of the new person is found in the address book
     */
    private ReadOnlyPerson updateAddressBook(List<ReadOnlyPerson> lastShownList) throws CommandException {
        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ReadOnlyPerson editedPerson;
        try {
            editedPerson = updateDisplayPicture(lastShownList, personToEdit);
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : MESSAGE_MISSING_PERSON;
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateListToShowAll();
        return personToEdit;
    }

    /**
     * Updates profile picture of {@code Person} according to selected mode (remove/ edit)
     *
     * @param lastShownList List of all current {@code Person} in address book
     * @param personToEdit Selected {@code Person} to edit
     * @return {@code Person} with new profile picture
     * @throws PersonNotFoundException When selected person is not found in address book
     */
    private ReadOnlyPerson updateDisplayPicture(List<ReadOnlyPerson> lastShownList, ReadOnlyPerson personToEdit)
            throws PersonNotFoundException {
        ReadOnlyPerson editedPerson;
        if (remove) {
            editedPerson = removeDisplayPicture(personToEdit);
        } else {
            editedPerson = selectDisplayPicture(lastShownList, personToEdit);
        }
        return editedPerson;
    }

    /**
     * Opens file browser to choose new image
     *
     * @param lastShownList List of all current {@code Person} in address book
     * @param personToEdit Selected {@code Person} to edit
     * @return {@code Person} with new profile picture
     * @throws PersonNotFoundException When selected person is not found in address book
     */
    private ReadOnlyPerson selectDisplayPicture(List<ReadOnlyPerson> lastShownList, ReadOnlyPerson personToEdit)
            throws PersonNotFoundException {
        ReadOnlyPerson editedPerson;
        model.changeImage(personToEdit);
        editedPerson = lastShownList.get(index.getZeroBased());
        return editedPerson;
    }

    /**
     * Removes profile picture of selected person and set it to default
     *
     * @param personToEdit Selected {@code Person} to edit
     * @return {@code Person} with default profile picture
     */
    private Person removeDisplayPicture(ReadOnlyPerson personToEdit) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(),
                            personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getRemark(),
                            personToEdit.getBirthday(), personToEdit.getTags(), new ProfilePicture(DEFAULT),
                            personToEdit.getFavourite());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImageCommand // instanceof handles nulls
                && this.index.equals(((ImageCommand) other).index)
                && this.remove == (((ImageCommand) other).remove)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\MapCommand.java
``` java
/**
 *  Shows a person's address on Google Maps in browser
 */
public class MapCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "map";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the address on Google Maps of the person "
            + "identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_MAP_SHOWN_SUCCESS = "Map for Person: %1$s";

    public final Index index;

    public MapCommand (Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShow = lastShownList.get(index.getZeroBased());

        mapPerson(personToShow);

        return new CommandResult(String.format(MESSAGE_MAP_SHOWN_SUCCESS, personToShow));
    }

    /**
     * Shows the address of the selected {@code Person} on GoogleMaps in a new browser window
     *
     * @param personToShow Selected {@code Person} to show
     */
    private void mapPerson(ReadOnlyPerson personToShow) {
        try {
            model.mapPerson(personToShow);
        } catch (PersonNotFoundException pnfe) {
            assert false : MESSAGE_MISSING_PERSON;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.index.equals(((MapCommand) other).index)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
/**
 * Removes the specified tag from all persons in addressbook
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag from all persons in the addressbook.\n"
            + "Parameters: TAG (must be a valid tag)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_TAG_NOT_FOUND = "Specified tag is not found";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %s %s %3$s";
    public static final String ALL = "all";
    public static final String FROM = "from";
    private static final String MESSAGE_TAG_NOT_FOUND_IN_PERSON = "Tag not found in selected person";

    public final Tag target;
    public final String index;

    public RemoveTagCommand(String index, Tag target) {
        requireNonNull(index);
        requireNonNull(target);

        this.target = target;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String result;
        if (index.equals(ALL)) {
            removeTagFromAllPerson();
            removeTagFromModel();
            result = ALL;
        } else {
            result = removeTagFromPerson(Integer.parseInt(index) - 1);
        }
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, target, FROM, result));
    }

    /**
     * Removes selected {@code Tag} from selected {@code Person} in address book
     *
     * @throws CommandException When selected {@code Tag} is not found in address book
     */
    private String removeTagFromPerson(int index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson person = lastShownList.get(index);
        boolean result = removeAndUpdate(person);
        if (!result) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND_IN_PERSON);
        }
        return person.getName().fullName;
    }

    /**
     * Removes selected {@code Tag} from person and updates address book
     *
     * @param person person selected
     * @throws CommandException when selected Tag is not found
     * @return true when tag is found in person
     */
    private boolean removeAndUpdate(ReadOnlyPerson person) throws CommandException {
        if (person.getTags().contains(target)) {
            Set<Tag> updatedTags = new HashSet<>(person.getTags());

            updatedTags.remove(target);

            Person editedPerson = getEditedPerson(person, updatedTags);

            updateModel(person, editedPerson);
            return true;
        }
        return false;
    }

    /**
     * Removes selected {@code Tag} from all {@code Person} in address book
     *
     * @throws CommandException When selected {@code Tag} is not found in address book
     */
    private void removeTagFromAllPerson() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (ReadOnlyPerson person: lastShownList) {
            removeAndUpdate(person);
        }
    }

    /**
     * Removes selected {@code Tag} from address book
     *
     * @throws CommandException When selected {@code Tag} is not found in address book
     */
    private void removeTagFromModel() throws CommandException {
        try {
            model.removeTag(target);
        } catch (UniqueTagList.TagNotFoundException tnf) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
    }

    /**
     * Updates model with new {@code Person} after removing selected {@code Tag}
     *
     * @param person {@code Person} with old data
     * @param editedPerson {@code Person} with new data
     * @throws CommandException When there is duplicate of the new person found in the address book
     */
    private void updateModel(ReadOnlyPerson person, Person editedPerson) throws CommandException {
        try {
            model.updatePerson(person, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateListToShowAll();
    }

    /**
     * Creates a new person with the new list of {@code tag}
     *
     * @param person Selected {@code Person}
     * @param updatedTags New list of {@code Tag}
     * @return Updated {@code Person} with new list
     */
    private Person getEditedPerson(ReadOnlyPerson person, Set<Tag> updatedTags) {
        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getRemark(), person.getBirthday(), updatedTags,
                person.getPicture(), person.getFavourite(), person.getNumTimesSearched());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.target.equals(((RemoveTagCommand) other).target)
                && this.index.equals(((RemoveTagCommand) other).index)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\BirthdayCommandParser.java
``` java
/**
 * Parses arguments and returns BirthdayCommand
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {

    private static final String SPACE = " ";
    private static final int INDEX_POS = 0;
    private static final int BIRTHDAY_POS = 1;
    private static final int CORRECT_LENGTH = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splitArgs = args.trim().split(SPACE);

        Index index;
        Birthday birthday;
        try {
            index = ParserUtil.parseIndex(splitArgs[INDEX_POS]);
            if (splitArgs.length < CORRECT_LENGTH) {
                throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            birthday = new Birthday(splitArgs[BIRTHDAY_POS]);
        } catch (IllegalValueException ive) {
            if (ive.getMessage().equals(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE), ive);
            } else {
                throw new ParseException(ive.getMessage());
            }
        }

        return new BirthdayCommand(index, birthday);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index[] index = getIndices(args);
            return new DeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    private Index[] getIndices(String args) throws IllegalValueException {
        String[] arguments = args.trim().split(SPACE);
        Index[] index = new Index[arguments.length];
        int count = 0;
        for (String e: arguments) {
            index[count++] = ParserUtil.parseIndex(e);
        }
        return index;
    }
```
###### \java\seedu\address\logic\parser\ImageCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImageCommand object
 */
public class ImageCommandParser implements Parser<ImageCommand> {

    private static final String REMOVE = "remove";
    private static final boolean REMOVE_IMAGE = true;
    private static final String SPACE = " ";
    private static final int INDEX_POS = 0;
    private static final int SELECT_POS = 1;
    private static final String INVALID_POST_INDEX = "Wrong input after index";

    /**
     * Parses the given {@code String} of arguments in the context of the ImageCommand
     * and returns an ImageCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImageCommand parse(String args) throws ParseException {
        try {
            return getImageCommand(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }
    }

    private ImageCommand getImageCommand(String args) throws IllegalValueException {
        String[] splitArgs = args.trim().split(SPACE);
        Index index = ParserUtil.parseIndex(splitArgs[INDEX_POS]);
        if (splitArgs.length > 1 && splitArgs[SELECT_POS].toLowerCase().equals(REMOVE)) {
            return new ImageCommand(index, REMOVE_IMAGE);
        } else if (splitArgs.length <= 1) {
            return new ImageCommand(index, !REMOVE_IMAGE);
        } else {
            throw new IllegalValueException(INVALID_POST_INDEX);
        }
    }
}
```
###### \java\seedu\address\logic\parser\MapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    private static final String SPACE = " ";
    private static final int TAG_POS = 1;
    private static final int INDEX_POS = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String arg) throws ParseException {
        String[] splitArgs = arg.trim().split(SPACE);
        try {
            if (splitArgs.length < 2) {
                Tag t = ParserUtil.parseTag(splitArgs[INDEX_POS]);
                return new RemoveTagCommand(RemoveTagCommand.ALL, t);
            } else {
                Tag t = ParserUtil.parseTag(splitArgs[TAG_POS]);
                return new RemoveTagCommand(splitArgs[INDEX_POS], t);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Start a tray icon running in background
     */
    private void startTray() {
        Platform.setImplicitExit(false);
        if (!SystemTray.isSupported()) {
            logger.warning(MESSAGE_TRAY_UNSUPPORTED);
            return;
        }
        initTrayIcon();
        try {
            tray = SystemTray.getSystemTray();
            tray.add(trayIcon);
        } catch (AWTException e) {
            logger.warning(MESSAGE_ADD_TRAY_ICON_FAIL);
        }
    }

    /**
     * Initialize the tray icon for the app
     */
    private void initTrayIcon() {
        Image image = new Image(TRAY_ICON, ICON_SIZE,ICON_SIZE, true, true);
        PopupMenu popup = new PopupMenu();

        setupTrayIcon(image, popup);
        setupPopupMenu(popup);
        setupMouseListener();
    }

    /**
     * Sets up the tray icon
     */
    private void setupTrayIcon(Image image, PopupMenu popup) {
        trayIcon = new TrayIcon(SwingFXUtils.fromFXImage(image, null), APP_NAME, popup);
        trayIcon.setPopupMenu(popup);
    }

    /**
     * Sets up the pop-up menu of the tray icon
     */
    private void setupPopupMenu(PopupMenu popup) {
        MenuItem exitItem = new MenuItem(EXIT);
        exitItem.addActionListener(e -> Platform.runLater(this::stop));
        popup.add(exitItem);
    }

    /**
     * Sets up the mouse listener for the tray icon
     */
    private void setupMouseListener() {
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    if (ui.isShowing()) {
                        Platform.runLater(() -> ui.hide());
                    } else {
                        Platform.runLater(() -> ui.show());
                    }
                }
            }
        });
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void removeTag(Tag t) throws UniqueTagList.TagNotFoundException {
        tags.remove(t);
    }
```
###### \java\seedu\address\model\BirthdayNotifier.java
``` java
/**
 * Checks current date against birthday of all persons
 */
public class BirthdayNotifier {
    public BirthdayNotifier(List<ReadOnlyPerson> list) {
        String[] people = getBirthdaysToday(list);
        if (people.length > 0) {
            createPopup(people);
        }
    }

    private String[] getBirthdaysToday(List<ReadOnlyPerson> list) {
        LocalDate now = LocalDate.now();
        int date = now.getDayOfMonth();
        int month = now.getMonthValue();

        ArrayList<String> people = new ArrayList<>();

        for (ReadOnlyPerson e: list) {
            if (e.getDay() == date && e.getMonth() == month) {
                people.add(e.getName().toString());
            }
        }
        return people.toArray(new String[people.size()]);
    }

    private void createPopup(String[] person) {
        new BirthdayPopup(person);
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Deletes the given tag from all persons in addressbook
     */
    void removeTag(Tag target) throws UniqueTagList.TagNotFoundException;

    /**
     * Shows the map for selected person in browser
     */
    void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Edits the profile picture for selected person
     */
    void changeImage(ReadOnlyPerson target) throws PersonNotFoundException;
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag target) throws UniqueTagList.TagNotFoundException {
        addressBook.removeTag(target);
        indicateAddressBookChanged();
    }

    @Override
    public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        raise(new MapPersonEvent(target));
    }

    @Override
    public void changeImage(ReadOnlyPerson target) throws PersonNotFoundException {
        raise(new ChangeImageEvent(target));
    }

```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String[], LocalDate)}
 */
public class Birthday implements Comparable {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthdays can only contain numbers, and should be in the format dd-mm-yyyy";
    public static final String MESSAGE_WRONG_DATE = "Date entered is wrong";
    public static final String MESSAGE_LATE_DATE = "Date given should be before today %1$s";
    private static final String DASH = "-";
    private static final int DEFAULT_VALUE = 0;
    private static final String NOT_SET = "Not Set";
    private static final String EMPTY = "";
    private static final String REMOVE = "remove";
    private static final int MIN_MONTHS = 1;
    private static final int MAX_MONTHS = 12;
    private static final int MIN_DAYS = 1;
    private static final int DAY_POS = 0;
    private static final int MONTH_POS = 1;
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public final String value;
    private final int day;
    private final int month;
    private final int year;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (birthday.equals(EMPTY) || birthday.equals(NOT_SET) || birthday.equals(REMOVE)) {
            this.value = NOT_SET;
            day = month = year = DEFAULT_VALUE;
        } else {
            LocalDate inputBirthday;
            try {
                inputBirthday = LocalDate.parse(birthday, formatter);
            } catch (DateTimeParseException dtpe) {
                throw new IllegalValueException(MESSAGE_WRONG_DATE);
            }
            if (!isValidBirthday(birthday.split(DASH), inputBirthday)) {
                throw new IllegalValueException(MESSAGE_WRONG_DATE);
            } else if (!isDateCorrect(inputBirthday)) {
                throw new IllegalValueException(String.format(MESSAGE_LATE_DATE, LocalDate.now().format(formatter)));
            } else {
                this.value = trimmedBirthday;
                this.day = inputBirthday.getDayOfMonth();
                this.month = inputBirthday.getMonthValue();
                this.year = inputBirthday.getYear();
            }
        }
    }

    /**
     * Ensures that the date entered is before current date
     *
     * @param birthday {@code LocalDate} containing input by user
     * @return True when birthday entered by user is before or on current date
     */
    public static boolean isDateCorrect(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now()) || birthday.equals(LocalDate.now());
    }

    /**
     * Returns true if a given string is a valid person birthday. Requires both the input string and the parsed date
     * since the parsed date will be resolved to the correct values by {@code DateTimeFormatter}
     *
     * @param test Input birthday String by user
     * @param testBirthday Parsed birthday from input by user (will be resolved to closest correct date)
     * @return True when date entered by user is valid
     */
    public static boolean isValidBirthday(String[] test, LocalDate testBirthday) {
        int day = Integer.parseInt(test[DAY_POS]);
        int month = Integer.parseInt(test[MONTH_POS]);

        return month >= MIN_MONTHS
                && month <= MAX_MONTHS
                && day >= MIN_DAYS
                && day <= testBirthday.lengthOfMonth();
    }

    /**
     * Calls {@code isValidBirthday} with parsed values when input is only one String
     *
     * @param test Single String input
     * @return True when {@code isValidBirthday} verifies date entered by user
     */
    public static boolean isValidBirthday(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String[] split = test.split(DASH);
        LocalDate testBirthday;
        try {
            testBirthday = LocalDate.parse(test, formatter);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return isValidBirthday(split, testBirthday);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Object o) {
        Birthday comparedBirthday = (Birthday) o;
        return this.value.compareTo(comparedBirthday.toString());
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

    @Override
    public int getDay() {
        return birthday.get().getDay();
    }

    @Override
    public int getMonth() {
        return birthday.get().getMonth();
    }

    @Override
    public Favourite getFavourite() {
        return favourite.get();
    }

    public void setFavourite(Favourite favourite) {
        this.favourite.set(requireNonNull(favourite));
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public ObjectProperty<ProfilePicture> imageProperty() {
        return image;
    }

    @Override
    public ProfilePicture getPicture() {
        return image.get();
    }

    @Override
    public void setImage(String img) {
        image.set(new ProfilePicture(img));
    }

```
###### \java\seedu\address\model\person\ProfilePicture.java
``` java
/**
 * Represents a profile picture for Person
 */
public class ProfilePicture {

    private final String imageLocation;

    /**
     * Validates given location of image.
     */
    public ProfilePicture(String location) {
        requireNonNull(location);
        imageLocation = location;
    }

    public String getLocation() {
        return imageLocation;
    }

    @Override
    public String toString() {
        return imageLocation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.imageLocation.equals(((ProfilePicture) other).imageLocation)); // state check
    }

    @Override
    public int hashCode() {
        return imageLocation.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlImageStorage.java
``` java
/**
 * Creates folder to store all images saved by user
 */
public class XmlImageStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlImageStorage.class);
    private static final String PNG = ".png";

    /**
     * Save selected image to image folder
     *
     * @throws IOException when image copy fails
     */
    public void saveImage(File image, String name) throws IOException {
        requireNonNull(image);
        requireNonNull(name);

        File file = new File(name + PNG);
        Files.copy(image.toPath(), file.toPath(), REPLACE_EXISTING);
    }

    /**
     * Deletes the selected image from folder
     * @param image to delete
     * @throws IOException when image deletion fails
     */
    public void removeImage(File image) throws IOException {
        requireNonNull(image);

        File[] files = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
                .listFiles();

        for (File file : files) {
            if (file.getName().equals(image.getName())) {
                file.delete();
            }
        }
    }
}
```
###### \java\seedu\address\ui\BirthdayPopup.java
``` java
/**
 * Creates a birthday notification
 */
public class BirthdayPopup {
    private static final int WIDTH = 300;
    private static final int MIN_HEIGHT = 125;
    private static final int INCREMENT = 50;
    private static final String BIRTHDAY_ALERT = "Birthday Alert!";
    private static final String ICON_LOCATION = "src/main/resources/images/birthday_cake.png";
    private static final String ICON_DESCRIPTION = "birthday icon";
    private static final String CLOSE_BUTTON = "x";
    private static final String BIRTHDAY_MESSAGE = "There are birthdays today: \n";
    private static final int DELAY = 5000;

    private JDialog frame = new JDialog();
    private GridBagConstraints constraints = new GridBagConstraints();

    public BirthdayPopup(String[] person) {
        createFrame(person.length);
        createIcon();
        createCloseButton();
        createMessage(person);
        createPopup();
    }

    /**
     * Creates the dialog for the popup
     *
     * @param size of popup
     */
    private void createFrame(int size) {
        frame.setSize(WIDTH, MIN_HEIGHT + size * INCREMENT);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());
        frame.setAlwaysOnTop(true);
    }

    /**
     * Creates the icon for the popup
     */
    private void createIcon() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;

        Icon headingIcon = new ImageIcon(ICON_LOCATION, ICON_DESCRIPTION);
        JLabel headingLabel = new JLabel(BIRTHDAY_ALERT);
        headingLabel.setIcon(headingIcon);
        headingLabel.setOpaque(false);
        frame.add(headingLabel, constraints);
    }

    /**
     * Creates the close button for the popup
     */
    private void createCloseButton() {
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton closeButton = new JButton(new AbstractAction(CLOSE_BUTTON) {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
            }
        });
        closeButton.setMargin(new Insets(1, 4, 1, 4));
        closeButton.setFocusable(false);
        frame.add(closeButton, constraints);
    }

    /**
     * create the message for all the person with birthday
     *
     * @param person names that have birthdays
     */
    private void createMessage(String[] person) {
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel(BIRTHDAY_MESSAGE);
        frame.add(messageLabel, constraints);
        for (String e: person) {
            constraints.gridy++;
            messageLabel = new JLabel(e);
            frame.add(messageLabel, constraints);
        }
        frame.setVisible(true);
    }

    /**
     * create a timer for the popup
     */
    private void createPopup() {
        new Thread(() -> {
            try {
                Thread.sleep(DELAY);
                frame.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadPersonMap(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAPS_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the map window.
     */
    private void handleMapEvent(ReadOnlyPerson person) {
        MapWindow mapWindow = new MapWindow(person);
        Platform.runLater(mapWindow::show);
    }

    /**
     * Opens file browser.
     */
    private void handleImageEvent(ReadOnlyPerson person) {
        Stage parent = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(BUTTON_DESCRIPTION, IMAGE_EXTENSIONS);
        fileChooser.getExtensionFilters().add(filter);
        File result = fileChooser.showOpenDialog(parent);
        if (result != null) {
            try {
                imageStorage.saveImage(result, person.getName().toString());
            } catch (IOException io) {
                logger.warning(MESSAGE_COPY_FAILURE);
            }
            person.setImage(person.getName().toString() + PNG);
        }
    }

    @Subscribe
    private void handleMapPanelEvent(MapPersonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleMapEvent(event.getPerson());
    }

    @Subscribe
    private void handleChangeImageEvent(ChangeImageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleImageEvent(event.getPerson());
    }
```
###### \java\seedu\address\ui\MapWindow.java
``` java
/**
 * Shows the map in a pop-up browser
 */
public class MapWindow extends UiPart<Region> {

    public static final String TITLE = "Map";
    public static final String SPACE = " ";
    public static final String PLUS = "+";

    private static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
    private static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    private static final Logger logger = LogsCenter.getLogger(MapWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "MapWindow.fxml";
    private static final String MESSAGE_SHOW_MAP = "Showing map window for selected person.";

    @FXML
    private WebView map;

    private final Stage dialogStage;

    public MapWindow(ReadOnlyPerson person) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = setupStage(scene);
        displayMap(person);
    }

    /**
     * Setup stage for browser pop-up
     */
    private Stage setupStage(Scene scene) {
        Stage dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        return dialogStage;
    }

    /**
     * Displays map of selected person on pop-up browser
     *
     * @param person Selected person to map
     */
    private void displayMap(ReadOnlyPerson person) {
        String mapUrl = GOOGLE_MAPS_URL_PREFIX + person.getAddress().getMapableAddress().replaceAll(SPACE, PLUS)
                + GOOGLE_SEARCH_URL_SUFFIX;
        map.getEngine().load(mapUrl);
    }

    /**
     * Shows the map window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine(MESSAGE_SHOW_MAP);
        dialogStage.show();
    }
}
```
###### \java\seedu\address\ui\PersonInfoPanel.java
``` java
/**
 * Shows the selected Person's info
 */
public class PersonInfoPanel extends UiPart<Region> {

    private static final String FXML = "PersonInfoPanel.fxml";
    private static final String EMPTY = "";
    private static final String DEFAULT = "profiles/default.png";
    private static final String DEFAULT_TEXT = "default";
    private static final int RADIUS = 100;
    private final Logger logger = LogsCenter.getLogger(PersonInfoPanel.class);
    private final ReadOnlyPerson person;
    private final Circle circle = new Circle(RADIUS, RADIUS, RADIUS);

    @FXML
    private ImageView profileImage;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    public PersonInfoPanel(ReadOnlyPerson person) {
        super(FXML);
        setConnections(person);
        this.person = person;
        registerAsAnEventHandler(this);
        setupProfileImage();
    }

    private void setupProfileImage() {
        profileImage.setClip(circle);
    }

    /**
     * Updates {@code PersonInfoPanel} with new details from {@code person}
     *
     * @param person New details for update
     */
    public void updateConnections(ReadOnlyPerson person) {
        setConnections(person);
    }

    private void setDefaultConnections() {
        setConnections(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null, DEFAULT_TEXT);
    }

    private void setConnections(ReadOnlyPerson person) {
        if (person == null) {
            setDefaultConnections();
        } else {
            setConnections(person.getName().fullName, person.getPhone().value, person.getAddress().value,
                    person.getEmail().value, person.getBirthday().value, person.getRemark().value, person.getTags(),
                    person.getPicture().getLocation());
        }
    }

    private void setConnections(String name, String phone, String address, String email, String birthday,
                                String remark, Set<Tag> tags, String loc) {
        this.name.setText(name);
        this.phone.setText(phone);
        this.address.setText(address);
        this.email.setText(email);
        this.birthday.setText(birthday);
        this.remark.setText(remark);
        this.tags.getChildren().clear();
        if (tags != null) {
            setTags(tags);
        }
        setImage(loc);
    }

    private void setTags(Set<Tag> tagList) {
        tagList.forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setImage(String loc) {
        try {
            Image image = getProfileImage(loc);
            profileImage.setImage(image);
        } catch (IllegalArgumentException iae) {
            profileImage.setImage(new Image(DEFAULT));
        }
    }

    private Image getProfileImage(String loc) {
        Image image;
        if (loc.equals(DEFAULT_TEXT)) {
            image = new Image(DEFAULT);
        } else {
            File img = new File(loc);
            image = new Image(img.toURI().toString());
        }
        return image;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(event.getNewSelection().person);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInfoPanel)) {
            return false;
        }

        // state check
        PersonInfoPanel card = (PersonInfoPanel) other;
        return name.getText().equals(card.name.getText())
                && phone.getText().equals(card.phone.getText())
                && address.getText().equals(card.address.getText())
                && email.getText().equals(card.email.getText())
                && birthday.getText().equals(card.birthday.getText())
                && remark.getText().equals(card.remark.getText())
                && person.equals(card.person);
    }
}
```
###### \resources\view\BrightTheme.css
``` css
.background {
    -fx-background-color: derive(#efefef, 20%);
    background-color: #ffffff; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #efefef;
    -fx-control-inner-background: #efefef;
    -fx-background-color: #efefef;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#efefef, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#efefef, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #f7f7f7;
}

.list-cell:filled:odd {
    -fx-background-color: #fefefe;
}

.list-cell:filled:selected {
    -fx-background-color: #f2f1f1;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 18px;
    -fx-font-style: oblique;
    -fx-text-fill: #555555;
    -fx-min-width: 70;
    -fx-min-height: 30;
}

#personInfoPanel #name {
    -fx-font-size: 28px;
}
.cell_small_label1 {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 18px;
    -fx-text-fill: black;
}
.anchor-pane {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-padding: 10;
    -fx-text-fill: black;
}

.pane-with-border {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-border-color: derive(#efefef, 10%);
    -fx-padding: 0;
    -fx-text-fill: black;
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
    -fx-wrap-text: true;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#efefef, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#efefef, 30%);
    -fx-border-width: 1px;
    -fx-text-fill: black;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#ffffff, 30%);
    -fx-padding: 10;
}

.context-menu {
    -fx-background-color: derive(#efefef, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#efefef, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #020202;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #fefefe;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: black;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #efefef;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #fefefe;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #fefefe;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#fefefe, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#efefef, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#efefef, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #383838 #383838;
    -fx-border-insets: 0;
    -fx-border-width: 2;
    -fx-border-radius: 20;
    -fx-prompt-text-fill: #0000fa;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #e3e3e3, transparent, #e3e3e3;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 4;
    -fx-background-radius: 2;
    -fx-font-size: 15;
}

```
###### \resources\view\MainWindow.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" >
  <stylesheets>
    <URL value="@BrightTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>
  <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="File">
      <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
    <Menu mnemonicParsing="false" text="Help">
      <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
    </Menu>
  </MenuBar>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="340" prefWidth="2000" SplitPane.resizableWithParent="false">
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
      <StackPane VBox.vgrow="NEVER" fx:id="commandBoxPlaceholder" styleClass="pane-with-border">
        <padding>
          <Insets top="5" right="10" bottom="5" left="10" />
        </padding>
      </StackPane>
      <StackPane VBox.vgrow="NEVER" fx:id="resultDisplayPlaceholder" styleClass="pane-with-border" minHeight="100" prefHeight="100" maxHeight="100">
        <padding>
          <Insets top="5" right="10" bottom="5" left="10" />
        </padding>
      </StackPane>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
    </VBox>
    <VBox minWidth="800" SplitPane.resizableWithParent="false">
      <StackPane fx:id="infoPlaceholder" prefWidth="340" >
        <padding>
          <Insets top="10" right="10" bottom="10" left="10" />
        </padding>
      </StackPane>
      <StackPane fx:id="browserPlaceholder" prefWidth="340" >
        <padding>
            <Insets top="10" right="10" bottom="10" left="10" />
        </padding>
      </StackPane>
    </VBox>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\MapWindow.fxml
``` fxml
<StackPane fx:id="mapWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <WebView fx:id="map" />
</StackPane>
```
###### \resources\view\PersonInfoPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane fx:id="personInfoPanel" styleClass="grid-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
        <ImageView fx:id="profileImage" fitHeight="200.0" fitWidth="200" GridPane.columnIndex="0" />
      <GridPane GridPane.columnIndex="1" GridPane.hgrow="ALWAYS" GridPane.rowSpan="2" >
         <columnConstraints>
           <ColumnConstraints hgrow="NEVER" minWidth="10.0" prefWidth="100.0" />
           <ColumnConstraints hgrow="ALWAYS" minWidth="10.0" prefWidth="500.0" maxWidth="Infinity"/>
         </columnConstraints>
         <Label fx:id="name" styleClass="cell_small_label1" text="\$name" GridPane.columnIndex="0" GridPane.columnSpan="2" />
         <Label styleClass="cell_small_label" text="Phone:" GridPane.columnIndex="0" GridPane.rowIndex="1" />
         <Label fx:id="phone" styleClass="cell_small_label1" text="\$phone" GridPane.columnIndex="1" GridPane.rowIndex="1" />
         <Label styleClass="cell_small_label" text="Address:" GridPane.columnIndex="0" GridPane.rowIndex="2" />
         <Label fx:id="address" styleClass="cell_small_label1" text="\$address" GridPane.columnIndex="1" GridPane.rowIndex="2" />
         <Label styleClass="cell_small_label" text="Email:" GridPane.columnIndex="0" GridPane.rowIndex="3" />
         <Label fx:id="email" styleClass="cell_small_label1" text="\$email" GridPane.columnIndex="1"  GridPane.rowIndex="3" />
         <Label styleClass="cell_small_label" text="Birthday:" GridPane.columnIndex="0" GridPane.rowIndex="4" />
         <Label fx:id="birthday" styleClass="cell_small_label1" text="\$birthday" GridPane.columnIndex="1" GridPane.rowIndex="4" />
         <Label styleClass="cell_small_label" text="Remarks:" GridPane.columnIndex="0" GridPane.rowIndex="5" />
         <Label fx:id="remark" styleClass="cell_small_label1" text="\$remark" GridPane.columnIndex="1" GridPane.rowIndex="5" />
         <FlowPane fx:id="tags" prefHeight="23.0" prefWidth="172.0" GridPane.columnIndex="1" GridPane.rowIndex="6" />
      </GridPane>
        <columnConstraints>
            <ColumnConstraints maxWidth="410.0" minWidth="-Infinity" prefWidth="220.0" />
        </columnConstraints>
        <rowConstraints>
            <RowConstraints maxHeight="200.0" minHeight="50.0" prefHeight="200.0" />
        </rowConstraints>
    </GridPane>
</VBox>
```
