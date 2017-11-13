# ZhangH795
###### /java/seedu/address/logic/commands/SwitchThemeCommand.java
``` java
/**
 * Shows the location of a person on Google map identified using it's last displayed index from the address book.
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String DARK_THEME_WORD1 = "dark";
    public static final String DARK_THEME_WORD2 = "Twilight";
    public static final String BRIGHT_THEME_WORD1 = "bright";
    public static final String BRIGHT_THEME_WORD2 = "Sunburst";
    public static final String DEFAULT_THEME_WORD1 = "default";
    public static final String DEFAULT_THEME_WORD2 = "Minimalism";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change into the theme of choice of iConnect.\n"
            + "Available themes: 1.Twilight 2.Sunburst 3.Minimalism\n"
            + "Parameters: THEME\n"
            + "Example: " + COMMAND_WORD + " Twilight";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme changed to: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The index %1$s is invalid.\n";
    public static final String MESSAGE_UNKNOWN_THEME = "The theme %1$s is unknown.\n";

    private final String userThemeInput;
    private String themeChoice;

    public SwitchThemeCommand(String themeChoice) {
        this.userThemeInput = themeChoice;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (userThemeInput.matches("\\d+")) {
            if ("1".equals(userThemeInput)) {
                themeChoice = DARK_THEME_WORD2;
            } else if ("2".equals(userThemeInput)) {
                themeChoice = BRIGHT_THEME_WORD2;
            } else if ("3".equals(userThemeInput)) {
                themeChoice = DEFAULT_THEME_WORD2;
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_INDEX, userThemeInput));
            }
        } else {
            if (userThemeInput.toLowerCase().contains(DARK_THEME_WORD1)
                    || DARK_THEME_WORD1.contains(userThemeInput.toLowerCase())
                    || userThemeInput.toLowerCase().contains(DARK_THEME_WORD2.toLowerCase())
                    || DARK_THEME_WORD2.toLowerCase().contains(userThemeInput.toLowerCase())) {
                themeChoice = DARK_THEME_WORD2;
            } else if (userThemeInput.toLowerCase().contains(BRIGHT_THEME_WORD1)
                    || BRIGHT_THEME_WORD1.contains(userThemeInput.toLowerCase())
                    || userThemeInput.toLowerCase().contains(BRIGHT_THEME_WORD2.toLowerCase())
                    || BRIGHT_THEME_WORD2.toLowerCase().contains(userThemeInput.toLowerCase())) {
                themeChoice = BRIGHT_THEME_WORD2;
            } else if (userThemeInput.toLowerCase().contains(DEFAULT_THEME_WORD1)
                    || DEFAULT_THEME_WORD1.contains(userThemeInput.toLowerCase())
                    || userThemeInput.toLowerCase().contains(DEFAULT_THEME_WORD2.toLowerCase())
                    || DEFAULT_THEME_WORD2.toLowerCase().contains(userThemeInput.toLowerCase())) {
                themeChoice = DEFAULT_THEME_WORD2;
            } else {
                throw new CommandException(String.format(MESSAGE_UNKNOWN_THEME, userThemeInput));
            }
        }
        if (themeChoice.equals(DARK_THEME_WORD2)) {
            EventsCenter.getInstance().post(new ChangeDarkThemeEvent());
        } else if (themeChoice.equals(BRIGHT_THEME_WORD2)) {
            EventsCenter.getInstance().post(new ChangeBrightThemeEvent());
        } else {
            EventsCenter.getInstance().post(new ChangeDefaultThemeEvent());
        }
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS, userThemeInput));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchThemeCommand // instanceof handles nulls
                && this.userThemeInput.equals(((SwitchThemeCommand) other).userThemeInput)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/TagAddCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class TagAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "t-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tag to the person(s) identified "
            + "by the index number used in the last person listing. "
            + "Input tag will append to the existing tags.\n"
            + "Parameters: INDEX1 INDEX2... (must be a positive integer) "
            + "TAG (TAG Should not start with a number).\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 "
            + "friends";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "The %1$s tag entered already exists "
            + "in some person selected.";
    private final ArrayList<Index> index;
    private final TagAddDescriptor tagAddDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tagAddDescriptor details to edit the person with
     */
    public TagAddCommand(ArrayList<Index> index, TagAddDescriptor tagAddDescriptor) {
        requireNonNull(index);
        requireNonNull(tagAddDescriptor);

        this.index = index;
        this.tagAddDescriptor = new TagAddDescriptor(tagAddDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean looseFind = false;
        Tag tagToAdd = (Tag) tagAddDescriptor.getTags().toArray()[0];
        String tagInString = tagToAdd.toString();
        String tagInString1 = tagInString.substring(1, tagInString.lastIndexOf("]"));
        StringBuilder editedPersonDisplay = new StringBuilder();
        checkIndexInRange(lastShownList);
        TagMatchingKeywordPredicate tagPredicate = new TagMatchingKeywordPredicate(tagInString1, looseFind);
        ObservableList<ReadOnlyPerson> selectedPersonList = createSelectedPersonList(lastShownList);
        FilteredList<ReadOnlyPerson> tagFilteredPersonList = new FilteredList<>(selectedPersonList);
        tagFilteredPersonList.setPredicate(tagPredicate);
        if (tagFilteredPersonList.size() > 0) {
            throw new CommandException(String.format(MESSAGE_TAG_ALREADY_EXISTS, tagInString));
        }
        for (int i = 0; i < index.size(); i++) {
            ReadOnlyPerson personToEdit = lastShownList.get(index.get(i).getZeroBased());
            Set<Tag> originalTagList = personToEdit.getTags();
            Set<Tag> modifiableTagList = createModifiableTagSet(originalTagList, tagToAdd);
            TagAddDescriptor tempTagAddDescriptor = new TagAddDescriptor();
            tempTagAddDescriptor.setTags(modifiableTagList);

            Person editedPerson = createEditedPerson(personToEdit, tempTagAddDescriptor);
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            int tagListStringStartIndex = 1;
            int tagListStringEndIndex;
            String tagChangedDisplayRaw = editedPerson.getTags().toString();
            tagListStringEndIndex = tagChangedDisplayRaw.length() - 1;
            String tagChangedDisplay = editedPerson.getName() + " Tag List: "
                    + tagChangedDisplayRaw.substring(tagListStringStartIndex, tagListStringEndIndex);

            editedPersonDisplay.append(String.format(MESSAGE_ADD_TAG_SUCCESS, tagChangedDisplay));
            EventsCenter.getInstance().post(new JumpToListRequestEvent(index.get(0)));

            if (i != index.size() - 1) {
                editedPersonDisplay.append("\n");
            }
        }
        return new CommandResult(editedPersonDisplay.toString());
    }

    /**
     * @param lastShownList current tag List
     */
    public void checkIndexInRange(ObservableList<ReadOnlyPerson> lastShownList) throws CommandException {
        for (int i = 0; i < index.size(); i++) {
            if (index.get(i).getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * @param unmodifiable tag List
     * @param tagToAdd tag to be added
     */
    public Set<Tag> createModifiableTagSet(Set<Tag> unmodifiable, Tag tagToAdd) {
        Set<Tag> modifiable = new HashSet<>();
        for (Tag t : unmodifiable) {
            modifiable.add(t);
        }
        modifiable.add(tagToAdd);
        return modifiable;
    }

    /**
     * @param fullPersonList person list
     */
    public ObservableList<ReadOnlyPerson> createSelectedPersonList(ObservableList<ReadOnlyPerson> fullPersonList) {
        ArrayList<ReadOnlyPerson> selectedPersonList = new ArrayList<>();
        for (Index i : index) {
            ReadOnlyPerson personToEdit = fullPersonList.get(i.getZeroBased());
            selectedPersonList.add(personToEdit);
        }
        return FXCollections.observableArrayList(selectedPersonList);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             TagAddDescriptor tagAddDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Birthday updatedBirthday = personToEdit.getBirthday();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = tagAddDescriptor.getTags();
        Set<Event> updatedEvents = personToEdit.getEvents();
        DateAdded updateDateAdded = personToEdit.getDateAdded();

        return new Person(updatedName, updatedBirthday, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedEvents, updateDateAdded);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagAddCommand)) {
            return false;
        }

        // state check
        TagAddCommand e = (TagAddCommand) other;
        return index.equals(e.index)
                && tagAddDescriptor.equals(e.tagAddDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class TagAddDescriptor {
        private Name name;
        private Birthday birthday;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;

        public TagAddDescriptor() {}

        public TagAddDescriptor(TagAddDescriptor toCopy) {
            this.name = toCopy.name;
            this.birthday = toCopy.birthday;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            this.events = toCopy.events;
            this.dateAdded = toCopy.dateAdded;
        }
        public TagAddDescriptor(ReadOnlyPerson toCopy) {
            this.name = toCopy.getName();
            this.birthday = toCopy.getBirthday();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.events = toCopy.getEvents();

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setEvent(Set<Event> events) {
            this.events = events;
        }

        public Optional<Set<Event>> getEvents() {
            return Optional.ofNullable(events);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof TagAddDescriptor)) {
                return false;
            }

            // state check
            TagAddDescriptor e = (TagAddDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
```
###### /java/seedu/address/logic/commands/TagFindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains a certain tag.
 * Keyword matching is case insensitive.
 */
public class TagFindCommand extends Command {

    public static final String COMMAND_WORD = "t-find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [TAG] \n"
            + "Example: " + COMMAND_WORD + " " + "friends";

    private final TagMatchingKeywordPredicate predicate;

    public TagFindCommand(TagMatchingKeywordPredicate keywordPredicate) {
        this.predicate = keywordPredicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().size() > 0) {
            Index defaultIndex = new Index(0);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));
        } else {
            EventsCenter.getInstance().post(new ClearPersonListEvent());
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagFindCommand // instanceof handles nulls
                && this.predicate.equals(((TagFindCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/TagRemoveCommand.java
``` java

/**
 * Removes a tag from existing person(s) in the address book.
 */
public class TagRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "t-remove";
    public static final String FAVOURITE_KEYWORD = "fav";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tag to the person(s) identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX1 INDEX2... (must be a positive integer) "
            + "TAG (TAG should not start with a number).\n"
            + "If no index is provided, remove the tag from all people. "
            + "Example: " + COMMAND_WORD + " 1 2 3 "
            + "friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = "The %1$s tag is not found.";
    public static final String MESSAGE_TAG_NOT_FOUND_FOR_SOME = "The %1$s tag entered is not found "
            + "for some person selected.";
    private ArrayList<Index> index;
    private final TagRemoveDescriptor tagRemoveDescriptor;

    /**
     * @param index               of the person in the filtered person list to edit
     * @param tagRemoveDescriptor details to edit the person with
     */
    public TagRemoveCommand(ArrayList<Index> index, TagRemoveDescriptor tagRemoveDescriptor) {
        requireNonNull(index);
        requireNonNull(tagRemoveDescriptor);

        this.index = index;
        this.tagRemoveDescriptor = new TagRemoveDescriptor(tagRemoveDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        Tag tagToRemove = (Tag) tagRemoveDescriptor.getTags().toArray()[0];
        String tagInString = tagToRemove.toString();
        String tagInString1 = tagInString.substring(1, tagInString.lastIndexOf("]"));
        boolean looseFind = tagInString1.toLowerCase().contains(FAVOURITE_KEYWORD);
        boolean removeAll = false;
        TagMatchingKeywordPredicate tagPredicate = new TagMatchingKeywordPredicate(tagInString1, looseFind);

        StringBuilder editedPersonDisplay = new StringBuilder();
        ArrayList<Index> indexList = index;
        checkIndexInRange(lastShownList);
        if (index.size() == 0) {
            removeAll = true;
            indexList = makeFullIndexList(lastShownList.size());
        }
        ObservableList<ReadOnlyPerson> selectedPersonList = createSelectedPersonList(indexList, lastShownList);
        FilteredList<ReadOnlyPerson> tagFilteredPersonList = new FilteredList<>(selectedPersonList);
        tagFilteredPersonList.setPredicate(tagPredicate);
        if (tagFilteredPersonList.size() == 0) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagInString));
        } else if (!removeAll && tagFilteredPersonList.size() < indexList.size()) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND_FOR_SOME, tagInString));
        }
        for (int i = 0; i < tagFilteredPersonList.size(); i++) {
            ReadOnlyPerson personToEdit = tagFilteredPersonList.get(i);
            Set<Tag> modifiableTagList = createModifiableTagSet(personToEdit.getTags(), tagToRemove);
            TagRemoveDescriptor tempTagRemoveDescriptor = new TagRemoveDescriptor();
            tempTagRemoveDescriptor.setTags(modifiableTagList);
            Person editedPerson = createEditedPerson(personToEdit, tempTagRemoveDescriptor);
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            int tagListStringStartIndex = 1;
            int tagListStringEndIndex;
            String tagChangedDisplayRaw = editedPerson.getTags().toString();
            tagListStringEndIndex = tagChangedDisplayRaw.length() - 1;
            String tagChangedDisplay = editedPerson.getName() + " Tag List: "
                    + tagChangedDisplayRaw.substring(tagListStringStartIndex, tagListStringEndIndex);

            editedPersonDisplay.append(String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagChangedDisplay));
            Index defaultIndex = new Index(0);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));

            if (i != indexList.size() - 1) {
                editedPersonDisplay.append("\n");
            }
        }
        return new CommandResult(editedPersonDisplay.toString());
    }

    /**
     * @param unmodifiable tag List
     * @param tagToRemove  tag to be removed
     */
    public Set<Tag> createModifiableTagSet(Set<Tag> unmodifiable, Tag tagToRemove) {
        Set<Tag> modifiable = new HashSet<>();
        String tagName = tagToRemove.tagName;
        boolean removeFavourite = tagName.toLowerCase().contains(FAVOURITE_KEYWORD);
        for (Tag t : unmodifiable) {
            if (!tagToRemove.equals(t) && !(removeFavourite && t.tagName.toLowerCase().contains(FAVOURITE_KEYWORD))) {
                modifiable.add(t);
            }
        }
        return modifiable;
    }

    /**
     * @param fullPersonList person list
     */
    public ObservableList<ReadOnlyPerson> createSelectedPersonList(ArrayList<Index> indexList,
                                                                   ObservableList<ReadOnlyPerson> fullPersonList) {
        ArrayList<ReadOnlyPerson> selectedPersonList = new ArrayList<>();
        for (Index i : indexList) {
            ReadOnlyPerson personToEdit = fullPersonList.get(i.getZeroBased());
            selectedPersonList.add(personToEdit);
        }
        return FXCollections.observableArrayList(selectedPersonList);
    }

    /**
     * @param tagList current tag List
     */
    public boolean containsTag(List<Tag> tagList) {
        Set<Tag> tagsToRemove = tagRemoveDescriptor.getTags();
        for (Tag tagToRemove : tagsToRemove) {
            for (Tag current : tagList) {
                if (tagToRemove.tagName.equalsIgnoreCase(current.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param personListSize current person list size
     */
    public ArrayList<Index> makeFullIndexList(int personListSize) {
        ArrayList<Index> indexList = new ArrayList<>();
        for (int i = 1; i <= personListSize; i++) {
            indexList.add(Index.fromOneBased(i));
        }
        return indexList;
    }

    /**
     * @param lastShownList current tag List
     */
    public void checkIndexInRange(ObservableList<ReadOnlyPerson> lastShownList) throws CommandException {
        for (int i = 0; i < index.size(); i++) {
            if (index.get(i).getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public Person createEditedPerson(ReadOnlyPerson personToEdit,
                                     TagRemoveDescriptor tagRemoveDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Birthday updatedBirthday = personToEdit.getBirthday();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = tagRemoveDescriptor.getTags();
        Set<Event> updatedEvents = personToEdit.getEvents();
        DateAdded updateDateAdded = personToEdit.getDateAdded();

        return new Person(updatedName, updatedBirthday, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedEvents, updateDateAdded);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagRemoveCommand)) {
            return false;
        }

        // state check
        TagRemoveCommand e = (TagRemoveCommand) other;
        return index.equals(e.index)
                && tagRemoveDescriptor.equals(e.tagRemoveDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class TagRemoveDescriptor {
        private Name name;
        private Birthday birthday;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;

        public TagRemoveDescriptor() {
        }

        public TagRemoveDescriptor(TagRemoveDescriptor toCopy) {
            this.name = toCopy.name;
            this.birthday = toCopy.birthday;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            this.events = toCopy.events;
            this.dateAdded = toCopy.dateAdded;
        }

        public TagRemoveDescriptor(ReadOnlyPerson toCopy) {
            this.name = toCopy.getName();
            this.birthday = toCopy.getBirthday();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.events = toCopy.getEvents();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setEvent(Set<Event> events) {
            this.events = events;
        }

        public Optional<Set<Event>> getEvents() {
            return Optional.ofNullable(events);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof TagRemoveDescriptor)) {
                return false;
            }

            // state check
            TagRemoveDescriptor e = (TagRemoveDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
```
###### /java/seedu/address/logic/parser/SwitchThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SwitchThemeCommand object
 */
public class SwitchThemeCommandParser implements Parser<SwitchThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchThemeCommand
     * and returns an SwitchThemeCommand object for execution.
     * @throws ParseException if the user input does not provide any input
     */
    public SwitchThemeCommand parse(String args) throws ParseException {
        String userInput = args.trim();
        if (userInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchThemeCommand.MESSAGE_USAGE));
        } else {
            return new SwitchThemeCommand(userInput);
        }
    }
}
```
###### /java/seedu/address/logic/parser/TagAddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TagAddCommand object
 */
public class TagAddCommandParser implements Parser<TagAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagAddCommand
     * and returns an TagAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String newTag = "";
        int lastIndex = -1;
        String[] argsArray;
        ArrayList<Index> index = new ArrayList<>();
        if (args.isEmpty() || (argsArray = args.trim().split(" ")).length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }
        try {
            for (int i = 0; i < argsArray.length; i++) {
                if (argsArray[i].matches("\\d+")) {
                    index.add(ParserUtil.parseIndex(argsArray[i]));
                    lastIndex = i;
                } else {
                    break;
                }
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }

        if (lastIndex == -1 || lastIndex == (argsArray.length - 1)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }
        HashSet<String> tagSet = new HashSet<>();
        for (int i = lastIndex + 1; i < argsArray.length; i++) {
            newTag = newTag.concat(argsArray[i] + " ");
        }
        newTag = newTag.trim();
        tagSet.add(newTag);
        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor();
        try {
            parseTagsForEdit(tagSet).ifPresent(tagAddDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!tagAddDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TagAddCommand.MESSAGE_NOT_EDITED);
        }

        return new TagAddCommand(index, tagAddDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
```
###### /java/seedu/address/logic/parser/TagFindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TagFindCommand object
 */
public class TagFindCommandParser implements Parser<TagFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagFindCommand
     * and returns an TagFindCommand object for execution.
     * @throws ParseException if the user does not provide any input
     */
    public TagFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        boolean looseFind = true;
        //Throw an error if there is no argument followed by the command word
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagFindCommand.MESSAGE_USAGE));
        }
        TagMatchingKeywordPredicate predicate = new TagMatchingKeywordPredicate(trimmedArgs, looseFind);
        return new TagFindCommand(predicate);
    }
}
```
###### /java/seedu/address/logic/parser/TagRemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TagRemoveCommand object
 */
public class TagRemoveCommandParser implements Parser<TagRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagRemoveCommand
     * and returns an TagRemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public TagRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String newTag = "";
        int lastIndex = -1;
        ArrayList<Index> index = new ArrayList<>();
        String[] argsArray;
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }
        argsArray = args.trim().split(" ");
        try {
            for (int i = 0; i < argsArray.length; i++) {
                if (argsArray[i].matches("\\d+")) {
                    index.add(ParserUtil.parseIndex(argsArray[i]));
                    lastIndex = i;
                } else {
                    break;
                }
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }
        if (lastIndex == argsArray.length - 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }
        for (int i = lastIndex + 1; i < argsArray.length; i++) {
            newTag = newTag.concat(" " + argsArray[i]);
        }
        HashSet<String> tagSet = new HashSet<>();
        newTag = newTag.trim();
        tagSet.add(newTag);
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        try {
            parseTagsForEdit(tagSet).ifPresent(tagRemoveDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!tagRemoveDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TagRemoveCommand.MESSAGE_NOT_EDITED);
        }
        return new TagRemoveCommand(index, tagRemoveDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
```
###### /resources/view/BrightTheme.css
``` css
.background {
    -fx-background-color: derive(#1d1d1d, 20%);
    background-color: red; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: tomato;
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
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane {
    -fx-tab-min-width:90px;
    -fx-padding: 0 0 0 1;
}

.tab{
    -fx-background-insets: 0 1 0 1,0,0
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
    -fx-background-color: #3c3c3c;
}

.tab-pane .tab:selected
{
    -fx-background-color: coral;
}

.tab-pane .tab
{
    -fx-background-color: derive(skyblue, 80%);
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: orange;
    -fx-font-size: 12px;
    -fx-font-weight: bold;
}

.tab:selected .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: white;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: red;
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
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(coral, 10%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
    -fx-background-color: orange;
}

.list-cell:filled:even {
    -fx-background-color:  derive(skyblue, 70%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(orange, 50%);
}

.list-cell:filled:selected {
    -fx-background-color: skyblue;
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
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#1d1d1d, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#1d1d1d, 20%);
     -fx-border-color: derive(#1d1d1d, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.context-menu .label {
    -fx-text-fill: orange;
}

.menu-bar {
    -fx-background-color: derive(skyblue, 50%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: orange;
    -fx-opacity: 1.9;
}

.menu:hover {
    -fx-background-color: skyblue;
}

.menu .left-container {
    -fx-background-color: derive(orange, 80%);
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
 /*
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: skyblue;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}
*/
.button:hover {
    -fx-background-color: derive(orange, 50%);
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
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: orange;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: orange;
}

.scroll-bar .thumb {
    -fx-background-color: derive(orange, 50%);
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
    -fx-border-color: orange;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
    -fx-text-fill: white;
}

#resultDisplay .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
    -fx-border-color: orange;
    -fx-border-insets: 0;
    -fx-border-width: 1;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#eventStatus {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#eventStatus  {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
