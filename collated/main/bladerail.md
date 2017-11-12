# bladerail
###### /java/seedu/address/commons/events/model/UserPersonChangedEvent.java
``` java
/** Indicates the UserPerson in the model has changed*/
public class UserPersonChangedEvent extends BaseEvent {

    public final UserPerson userPerson;

    public UserPersonChangedEvent(UserPerson data) {
        this.userPerson = data;
    }

    @Override
    public String toString() {
        return "UserPerson changed: \n" + userPerson.getAsText();
    }

    public UserPerson getUserPerson() {
        return userPerson;
    }
}
```
###### /java/seedu/address/commons/events/ui/CopyToClipboardRequestEvent.java
``` java
/**
 * Indicates a request for copying a string to clipboard
 */
public class CopyToClipboardRequestEvent extends BaseEvent {

    public final ClipboardContent toCopy;

    public CopyToClipboardRequestEvent(String toCopy) {
        this.toCopy = new ClipboardContent();
        this.toCopy.putString(toCopy);
    }

    public ClipboardContent getToCopy() {
        return this.toCopy;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
/**
 * Changes the remark of an existing person in the address book.
 */

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified by INDEX.\n"
            + "Overwrites previous remark, if any.\n"
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "EXAMPLE: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "likes dogs.";

```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index  of the person in the filtered person list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public final CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags(), personToEdit.getWebLinks());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Returns command success message
     */

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceOf handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index) && remark.equals(e.remark);
    }
}
```
###### /java/seedu/address/logic/commands/ShareCommand.java
``` java
/**
 * Displays an add command for the user's contact
 */
public class ShareCommand extends Command {
    public static final String COMMAND_WORD = "share";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_SUCCESS = "Add Command generated. Copied to clipboard! \n%1$s";

    @Override
    public CommandResult execute() {
        UserPerson userPerson = model.getUserPerson();
        String result = addCommandBuilder(userPerson);

        EventsCenter.getInstance().post(new CopyToClipboardRequestEvent(result));

        return new CommandResult(String.format(MESSAGE_SUCCESS, result));
    }

    /**
     * Builds an addCommand for the model's userPerson
     * @return String
     */
    public static String addCommandBuilder(ReadOnlyPerson src) {
        final StringBuilder builder = new StringBuilder();
        builder.append(AddCommand.COMMAND_WORD)
                .append(" ")
                .append(PREFIX_NAME)
                .append(src.getName())
                .append(" ")
                .append(PREFIX_PHONE)
                .append(src.getPhone())
                .append(" ")
                .append(PREFIX_ADDRESS)
                .append(src.getAddress());
        for (Email email : src.getEmail()) {
            builder.append(" ")
                    .append(PREFIX_EMAIL)
                    .append(email);
        }

        for (WebLink webLink : src.getWebLinks()) {
            builder.append(" ")
                    .append(PREFIX_WEB_LINK)
                    .append(webLink.toStringWebLink());
        }

        return builder.toString();
    }
}

```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts all persons in the address book by indicated format.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the address book in ascending order by an indicated format."
            + "Sorts by default (Name) if no argument. "
            + "Currently 4 possible formats: Name, Email, Phone, Address.\n"
            + "Accepts aliases 'n', 'e', 'p', 'a' respectively."
            + "Parameters: "
            + COMMAND_WORD
            + " [name/email/phone/address]\n"
            + "Example: " + COMMAND_WORD + " name";

```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
    public static final String MESSAGE_SUCCESS = "Sorted successfully by %1$s, Listed all persons.";

    private String filterType;

    public SortCommand (String filterType) {
        // Filter type can be null to signify default listing
        // Todo: Allow sort to accept different parameters for filter types (eg. First Name, Last Name)
        this.filterType = filterType;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.sortFilteredPersonList(filterType);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filterType));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceOf handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return filterType.equals(e.filterType);
    }
}
```
###### /java/seedu/address/logic/commands/UpdateUserCommand.java
``` java
/**
 * Updates the model's UserPerson information.
 */
public class UpdateUserCommand extends Command {
    public static final String COMMAND_WORD = "update";
    public static final String COMMAND_ALIAS = "up";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits your user details in a similar "
            + "format to the ADD command. Cannot edit tags."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: update "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_WEB_LINK + "WEBLINK] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

```
###### /java/seedu/address/logic/commands/UpdateUserCommand.java
``` java
    public static final String MESSAGE_UPDATE_USER_SUCCESS = "Successfully edited User Profile: %1s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_TAGS_NOT_ALLOWED = "Unable to edit your own tags";


    private final EditPersonDescriptor editPersonDescriptor;

    public UpdateUserCommand (EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);
        this.editPersonDescriptor = editPersonDescriptor;
    }

    public EditPersonDescriptor getEditPersonDescriptor() {
        return editPersonDescriptor;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_UPDATED);
        }

        ReadOnlyPerson personToEdit = model.getUserPerson();
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);

        model.updateUserPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_UPDATE_USER_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateUserCommand)) {
            return false;
        }

        // state check
        UpdateUserCommand updateUserCommand = (UpdateUserCommand) other;
        return editPersonDescriptor.equals(updateUserCommand.getEditPersonDescriptor());
    }
}
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns the userPerson */
    UserPerson getUserPerson();

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public UserPerson getUserPerson() {
        return model.getUserPerson();
    }
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommandParser().parse(arguments);

```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);

        case UpdateUserCommand.COMMAND_WORD:
        case UpdateUserCommand.COMMAND_ALIAS:
            return new UpdateUserCommandParser().parse(arguments);

        case ShareCommand.COMMAND_WORD:
        case ShareCommand.COMMAND_ALIAS:
            return new ShareCommand();

```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final String ARG_DEFAULT = "default";
    public static final String ARG_NAME = "name";
    public static final String ARG_NAME_ALIAS = "n";
    public static final String ARG_PHONE = "phone";
    public static final String ARG_PHONE_ALIAS = "p";
    public static final String ARG_EMAIL = "email";
    public static final String ARG_EMAIL_ALIAS = "e";
    public static final String ARG_ADDRESS = "address";
    public static final String ARG_ADDRESS_ALIAS = "a";
    public static final String ARG_REMARK = "remark";
    public static final String ARG_TAG = "tag";
    public static final String ARG_WEB_LINK = "weblink";
```

###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
}
```
###### /java/seedu/address/logic/parser/RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }

}
```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private HashSet<String> acceptedFilterTypes = new HashSet<>(Arrays.asList(ARG_DEFAULT,
            ARG_NAME, ARG_NAME_ALIAS, ARG_EMAIL, ARG_EMAIL_ALIAS,
            ARG_PHONE, ARG_PHONE_ALIAS, ARG_ADDRESS, ARG_ADDRESS_ALIAS));

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, new Prefix(""));

        String filterType;
        filterType = argMultimap.getPreamble().toLowerCase();

        if ("".equals(filterType)) {
            // No arguments, set to default
            filterType = ARG_DEFAULT;
        }

        if (!isValidFilterType(filterType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        } else {
            switch (filterType) {
            case ARG_NAME_ALIAS:
            case ARG_NAME:
                filterType = ARG_NAME;
                break;
            case ARG_EMAIL:
            case ARG_EMAIL_ALIAS:
                filterType = ARG_EMAIL;
                break;
            case ARG_ADDRESS:
            case ARG_ADDRESS_ALIAS:
                filterType = ARG_ADDRESS;
                break;
            case ARG_PHONE:
            case ARG_PHONE_ALIAS:
                filterType = ARG_PHONE;
                break;
            case ARG_DEFAULT:
            default:
                // TODO: Make default sort by date added
                filterType = ARG_DEFAULT;
                break;
            }
            return new SortCommand(filterType);
        }
    }

    /**
     * Returns true if the input filterType is a valid filter for the list. Only name, phone,
     * email and address are allowed.
     * @param filterType
     * @return
     */
    public boolean isValidFilterType(String filterType) {
        return acceptedFilterTypes.contains(filterType);
    }

    public HashSet<String> getAcceptedFilterTypes() {
        return acceptedFilterTypes;
    }
}
```
###### /java/seedu/address/logic/parser/UpdateUserCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UpdateUserCommand object
 */
public class UpdateUserCommandParser implements Parser<UpdateUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateUserCommand
     * and returns an UpdateUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateUserCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_WEB_LINK);

        String preamble = argMultimap.getPreamble();
        if (!preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateUserCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            parseEmailsForEdit(argMultimap.getAllValues(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            parseWebLinkForEdit(argMultimap.getAllValues(PREFIX_WEB_LINK)).ifPresent(editPersonDescriptor::setWebLinks);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(UpdateUserCommand.MESSAGE_TAGS_NOT_ALLOWED);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateUserCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateUserCommand(editPersonDescriptor);
    }


    /**
     * Parses {@code Collection<String> emails} into a {@code ArrayList<Emails>} if {@code emails} is non-empty.
     * If {@code emails} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Email>} containing zero emails.
     */
    private Optional<ArrayList<Email>> parseEmailsForEdit(Collection<String> emails) throws IllegalValueException {
        assert emails != null;

        if (emails.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> emailSet = emails.size() == 1 && emails.contains("") ? Collections.emptySet() :  emails;
        Collection<String> emailSetToParse = new ArrayList<>();
        for (String email : emailSet) {
            if (!emailSetToParse.contains(email)) {
                emailSetToParse.add(email);
            }
        }
        return Optional.of(ParserUtil.parseEmail(emailSetToParse));
    }

    /**
     * Parses {@code Collection<String> webLinks} into a {@code Set<weblink>} if {@code webLinks} is non-empty.
     * If {@code webLinks} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<WebLinks>} containing zero tags.
     */
    private Optional<Set<WebLink>> parseWebLinkForEdit(Collection<String> webLinks) throws IllegalValueException {
        assert webLinks != null;

        if (webLinks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> webLinkSet = webLinks.size() == 1
                && webLinks.contains("") ? Collections.emptySet() : webLinks;
        return Optional.of(ParserUtil.parseWebLink(webLinkSet));
    }
}
```
###### /java/seedu/address/MainApp.java
``` java
    protected Clipboard clipboard;
```
###### /java/seedu/address/MainApp.java
``` java
        try {
            userPersonOptional = storage.readUserProfile();
            if (!userPersonOptional.isPresent()) {
                logger.info(" No userProfile found, will be starting with a new user");
            }
            initialUser = userPersonOptional.orElse(new UserPerson());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with a new User");
            initialUser = new UserPerson();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with a new User");
            initialUser = new UserPerson();
        }
```
###### /java/seedu/address/MainApp.java
``` java
    /**
     * Returns a {@code UserPerson} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPerson} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPerson initUserPerson(UserProfileStorage storage) {
        String userProfileFilePath = storage.getUserProfileFilePath();
        logger.info("Using UserProfile file : " + userProfileFilePath);

        UserPerson initializedPerson;
        try {
            Optional<UserPerson> userPersonOptional = storage.readUserProfile();
            initializedPerson = userPersonOptional.orElse(new UserPerson());
        } catch (DataConversionException e) {
            logger.warning("UserProfile file at " + userProfileFilePath + " is not in the correct format. "
                    + "Using default user profile");
            initializedPerson = new UserPerson();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with a new user Profile");
            initializedPerson = new UserPerson();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPerson(initializedPerson);
        } catch (IOException e) {
            logger.warning("Failed to save UserProfile file : " + StringUtil.getDetails(e));
        }

        return initializedPerson;
    }

```
###### /java/seedu/address/MainApp.java
``` java
        this.clipboard = Clipboard.getSystemClipboard();
```
###### /java/seedu/address/MainApp.java
``` java
            storage.saveUserPerson(model.getUserPerson());
```
###### /java/seedu/address/MainApp.java
``` java
    @Subscribe
    public void handleCopyToClipboardRequestEvent (CopyToClipboardRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        clipboard.setContent(event.getToCopy());
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Sorts the filteredPerson list by the filterType, one of [Name/Email/Address/Phone]
     * @param filterType
     */
    void sortFilteredPersonList(String filterType);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /** Returns the UserPerson */
    UserPerson getUserPerson();

    /**
     * Updates the UserPerson with an editedPerson
     * @param editedPerson
     */
    void updateUserPerson(ReadOnlyPerson editedPerson);
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public UserPerson getUserPerson() {
        return userPerson;
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sortFilteredPersonList(String filterType) {
        Predicate<? super ReadOnlyPerson> currPredicate = filteredPersons.getPredicate();
        if (currPredicate == null) {
            currPredicate = PREDICATE_SHOW_ALL_PERSONS;
        }
        addressBook.sortPersons(filterType);
        ObservableList<ReadOnlyPerson> sortedList = this.addressBook.getPersonList();
        this.filteredPersons = new FilteredList<>(sortedList);
        filteredPersons.setPredicate(currPredicate);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateUserPerson(ReadOnlyPerson editedPerson) {
        requireAllNonNull(editedPerson);
        userPerson.update(editedPerson);
        indicateUserPersonChanged();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserPersonChanged() {
        raise(new UserPersonChangedEvent(userPerson));
        logger.info("Updated User Person: " + userPerson);
    }

```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the internal list by order of a comparator, which by default is name.
     */
    public void sortPersons(String filterType) {

        Comparator<ReadOnlyPerson> personComparator = (ReadOnlyPerson person1, ReadOnlyPerson person2) -> {

            String arg1;
            String arg2;
            switch (filterType) {
            case ARG_NAME:
                arg1 = person1.getName().toString().toLowerCase();
                arg2 = person2.getName().toString().toLowerCase();
                break;
            case ARG_PHONE:
                arg1 = person1.getPhone().toString();
                arg2 = person2.getPhone().toString();
                break;
            case ARG_EMAIL:
                arg1 = person1.getEmail().toString();
                arg2 = person2.getEmail().toString();
                break;
            case ARG_ADDRESS:
                arg1 = person1.getAddress().toString();
                arg2 = person2.getAddress().toString();
                break;
            default:
                // TODO: Make default sort by date added
                arg1 = person1.getName().toString();
                arg2 = person2.getName().toString();
                break;
            }
            return arg1.compareTo(arg2);
        };

        FXCollections.sort(internalList, personComparator);
    }

```
###### /java/seedu/address/model/person/UserPerson.java
``` java
/**
 * Represents the user's Profile in the address book.
 *
 */
public class UserPerson implements ReadOnlyPerson {
    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<ArrayList<Email>> emails;
    private ObjectProperty<Address> address;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<UniqueWebLinkList> webLinks;

    public UserPerson() {
        this(SampleUserPersonUtil.getDefaultSamplePerson().getName(),
                SampleUserPersonUtil.getDefaultSamplePerson().getPhone(),
                SampleUserPersonUtil.getDefaultSamplePerson().getEmail(),
                SampleUserPersonUtil.getDefaultSamplePerson().getAddress(),
                SampleUserPersonUtil.getDefaultSamplePerson().getWebLinks());
    }


    /**
     * Every field must be present and not null.
     */
    public UserPerson(Name name, Phone phone, ArrayList<Email> email, Address address, Set<WebLink> webLinks) {
        requireAllNonNull(name, phone, email, address);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.emails = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.remark = new SimpleObjectProperty<>(new Remark(""));
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.webLinks = new SimpleObjectProperty<>(new UniqueWebLinkList(webLinks));
    }

    /**
     * Every field must be present and not null.
     */
    public UserPerson(ReadOnlyPerson src) {
        this.name = new SimpleObjectProperty<>(src.getName());
        this.phone = new SimpleObjectProperty<>(src.getPhone());
        this.emails = new SimpleObjectProperty<>(src.getEmail());
        this.address = new SimpleObjectProperty<>(src.getAddress());
        this.remark = new SimpleObjectProperty<>(new Remark(""));
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.webLinks = new SimpleObjectProperty<>(new UniqueWebLinkList(src.getWebLinks()));
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(ArrayList<Email> email) {
        this.emails.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<ArrayList<Email>> emailProperty() {
        return emails;
    }

    @Override
    public ArrayList<Email> getEmail() {
        return emails.get();
    }

    public String getEmailAsText() {
        StringBuilder builder = new StringBuilder();
        for (Email email : getEmail()) {
            builder.append(email.toString());
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        return builder.toString();
    }

    public String getWebLinksAsText() {
        StringBuilder builder = new StringBuilder();
        for (WebLink webLink : getWebLinks()) {
            builder.append(webLink.toStringWebLink());
            builder.append(", ");
        }
        if (builder.length() != 0) {
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return tags.get().toSet();
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

    /**
     * Replaces this person's web links with the web links in the argument tag set.
     */
    public void setWebLinks(Set<WebLink> replacement) {
        webLinks.set(new UniqueWebLinkList(replacement));
    }

    /**
     * Returns an immutable webLink set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<WebLink> getWebLinks() {
        return webLinks.get().toSet();
    }

    @Override
    public ObjectProperty<UniqueWebLinkList> webLinkProperty() {
        return webLinks;
    }


    /**
     * returns a ArrayList of string websites for UI usage.
     * @code WebLinkUtil for the list of webLinkTags can be used as category.
     */
    @Override
    public ArrayList<String> listOfWebLinkByCategory (String category) {
        ArrayList<String> outputWebLinkList = new ArrayList<String>();
        for (Iterator<WebLink> iterateWebLinkSet = getWebLinks().iterator(); iterateWebLinkSet.hasNext();) {
            WebLink checkWebLink = iterateWebLinkSet.next();
            String webLinkAddedToList = checkWebLink.toStringWebLink();
            String checkWebLinkTag = checkWebLink.toStringWebLinkTag();
            if (checkWebLinkTag.equals(category)) {
                outputWebLinkList.add(webLinkAddedToList);
            }
        }
        return outputWebLinkList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, emails, address, tags, webLinks);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Update the UserPerson details with that of the ReadOnlyPerson target
     * @param target
     */
    public void update(ReadOnlyPerson target) {
        setName(target.getName());
        setEmail(target.getEmail());
        setPhone(target.getPhone());
        setAddress(target.getAddress());
        setWebLinks(target.getWebLinks());
    }
}
```
###### /java/seedu/address/model/UserPrefs.java
``` java
    public String getUserProfileFilePath() {
        return userProfileFilePath;
    }

    public void setUserProfileFilePath(String newProfileFilePath) {
        this.userProfileFilePath = newProfileFilePath;
    }

```
###### /java/seedu/address/model/util/SampleUserPersonUtil.java
``` java
/**
 * Contains utility methods for creating sample UserPerson data.
 */
public class SampleUserPersonUtil {
    public static ReadOnlyPerson getDefaultSamplePerson() {
        try {
            ArrayList<Email> emails = new ArrayList<Email>();
            emails.add(new Email("default@default.com"));
            HashSet<WebLink> webLinks = new HashSet<>();
            webLinks.add(new WebLink("default@facebook.com"));
            return new Person (new Name("Default"), new Phone("00000000"), emails,
                            new Address("Default"), new Remark(""), new HashSet<Tag>(), webLinks);
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
}
```
###### /java/seedu/address/storage/Storage.java
``` java
    @Override
    String getUserProfileFilePath();

    @Override
    Optional<UserPerson> readUserProfile() throws DataConversionException, IOException;

    @Override
    void saveUserPerson(UserPerson userPerson, String filePath) throws IOException;

    void handleUserPersonChangedEvent(UserPersonChangedEvent upce);

```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public String getUserProfileFilePath() {
        return userProfileStorage.getUserProfileFilePath();
    }

    @Override
    public Optional<UserPerson> readUserProfile() throws DataConversionException, IOException {
        return userProfileStorage.readUserProfile();
    }

    @Override
    public void saveUserPerson(UserPerson newUserPerson) throws IOException {
        userProfileStorage.saveUserPerson(newUserPerson, userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public void saveUserPerson(UserPerson newUserPerson, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userProfileStorage.saveUserPerson(newUserPerson, filePath);
    }

    @Override
    @Subscribe
    public void handleUserPersonChangedEvent(UserPersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "\nLocal User Profile data changed, saving to file"));
        try {
            saveUserPerson(event.getUserPerson());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

```
###### /java/seedu/address/storage/UserProfileStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface UserProfileStorage {

    /**
     * Returns the file path of the UserProfile data file.
     */
    String getUserProfileFilePath();

    /**
     * Returns UserProfile data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserPerson> readUserProfile() throws DataConversionException, IOException;

    /**
     * Saves the given {@link UserPerson} to the storage.
     * @param userPerson cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPerson(UserPerson userPerson, String filePath) throws IOException;

    void saveUserPerson(UserPerson userPerson) throws IOException;

}
```
###### /java/seedu/address/storage/XmlUserPerson.java
``` java
/**
 * An Immutable UserPerson that is serializable to XML format
 */
@XmlRootElement(name = "userperson")
public class XmlUserPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private ArrayList<Email> emailList;
    @XmlElement(required = true)
    private String address;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedWebLink> webLinkList = new ArrayList<>();

    /**
     *
     * This empty constructor is required for marshalling.
     */
    public XmlUserPerson() {
    }

    public XmlUserPerson(UserPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        emailList = new ArrayList<Email>();
        for (Email email : source.getEmail()) {
            emailList.add(email);
        }
        address = source.getAddress().value;
        webLinkList = new ArrayList<>();
        for (WebLink webLink : source.getWebLinks()) {
            this.webLinkList.add(new XmlAdaptedWebLink(webLink));
        }
    }

    public UserPerson getUser() throws IllegalValueException {
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final ArrayList<Email> email = new ArrayList<>(this.emailList);
        final Address address = new Address(this.address);

        final List<WebLink> webLinkInputs = new ArrayList<>();
        for (XmlAdaptedWebLink webLink: webLinkList) {
            webLinkInputs.add(webLink.toModelType());
        }

        final Set<WebLink> webLinks = new HashSet<>(webLinkInputs);
        return new UserPerson(name, phone, email, address, webLinks);
    }
}
```
###### /java/seedu/address/storage/XmlUserProfileStorage.java
``` java
/**
 * A class to access UserProfile stored in the hard disk as an xml file
 */
public class XmlUserProfileStorage implements UserProfileStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserProfileStorage.class);

    private String filePath;

    public XmlUserProfileStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserProfileFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPerson> readUserProfile() throws DataConversionException, IOException {
        return readUserProfile(filePath);
    }

    /**
     * Similar to {@link #readUserProfile()}
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPerson> readUserProfile(String filePath) throws DataConversionException,
            FileNotFoundException {

        requireNonNull(filePath);

        File userProfileFile = new File(filePath);

        if (!userProfileFile.exists()) {
            logger.info("UserProfile file "  + userProfileFile + " not found");
            return Optional.empty();
        }

        UserPerson userPersonOptional;

        try {
            XmlUserPerson xml = XmlUtil.getDataFromFile(userProfileFile, XmlUserPerson.class);
            userPersonOptional = xml.getUser();
            return Optional.of(userPersonOptional);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        } catch (IllegalValueException e) {
            logger.warning("Illegal parameters");
            return null;
        }
    }

    @Override
    public void saveUserPerson(UserPerson userPerson) throws IOException {
        saveUserPerson(userPerson, filePath);
    }

    /**
     * Similar to {@link #saveUserPerson(UserPerson userPerson)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveUserPerson(UserPerson userPerson, String filePath) throws IOException {
        requireNonNull(userPerson);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        try {
            XmlUtil.saveDataToFile(file, new XmlUserPerson(userPerson));
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        setAccelerator(userProfileMenuItem, KeyCombination.valueOf("F2"));
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        setAccelerator(userProfileMenuItem, KeyCombination.valueOf("F2"));
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Displays the user profile to the user
     */
    @FXML
    private void handleUserProfile() {
        logger.info("Opening User Profile Window");
        UserProfileWindow userProfileWindow = new UserProfileWindow(logic.getUserPerson());
        userProfileWindow.show();
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    //Update the filteredPersonList when addressBook is changed, mainly for sort
    @Subscribe
    void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personListPanel.setConnections(logic.getFilteredPersonList());
    }
```
###### /java/seedu/address/ui/UserProfileWindow.java
``` java
/**
 * Controller for the User Profile Window
 */
public class UserProfileWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UserProfileWindow.class);
    private static final String FXML = "UserProfileWindow.fxml";
    private static final String TITLE = "User Profile";

    private final UserPerson userPerson;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField webLinkTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private final Stage stage;

    public UserProfileWindow(UserPerson userPerson) {
        super(FXML);
        registerAsAnEventHandler(this);

        Scene scene = new Scene(getRoot());
        stage = createDialogStage(TITLE, null, scene);
        statusLabel.setText("");
        this.userPerson = userPerson;
        nameTextField.setText(userPerson.getName().toString());
        emailTextField.setText(userPerson.getEmailAsText());
        phoneTextField.setText(userPerson.getPhone().toString());
        addressTextField.setText(userPerson.getAddress().toString());
        webLinkTextField.setText(userPerson.getWebLinksAsText());

        setAccelerators(scene);

    }

    /**
     * Sets accelerators for the UserProfileWindow
     * @param scene Current scene
     */
    private void setAccelerators(Scene scene) {
        scene.getAccelerators().put(KeyCombination.valueOf("ESC"), this::handleCancel);
        scene.getAccelerators().put(KeyCombination.valueOf("ENTER"), this::handleOk);

    }

    /**
     *
     */
    public void show() {
        logger.fine("Showing User Profile.");
        stage.showAndWait();
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    /**
     * Handles the OK button
     */
    @FXML
    private void handleOk() {
        try {
            updateUserPerson();
            raise(new UserPersonChangedEvent(userPerson));
            logger.fine("UserPerson updated via UserProfileWindow, saving");
            stage.close();
        } catch (Exception e) {
            logger.fine("Invalid UserPerson modification");
        }
    }

    @Subscribe
    private void handleUserPersonChangedEvent(UserPersonChangedEvent event) {
        logger.fine(LogsCenter.getEventHandlingLogMessage(event));
    }

    /**
     * Updates the user person
     */
    private void updateUserPerson() throws Exception {
        try {
            userPerson.setName(new Name(nameTextField.getText()));
        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_NAME_CONSTRAINTS);
            throw new Exception();
        }

        try {
            String[] emails = emailTextField.getText().split(", ");
            ArrayList<Email> emailList = new ArrayList<>();
            for (String curr : emails) {
                emailList.add(new Email(curr));
            }
            userPerson.setEmail(emailList);

        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_EMAIL_CONSTRAINTS);
            throw new Exception();
        }

        try {
            if (phoneTextField.getText().equals("")) {
                userPerson.setPhone(new Phone(null));
            } else {
                userPerson.setPhone(new Phone(phoneTextField.getText()));
            }
        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_PHONE_CONSTRAINTS);
            throw new Exception();
        }

        try {
            if (phoneTextField.getText().equals("")) {
                userPerson.setAddress(new Address(null));
            } else {
                userPerson.setAddress(new Address(addressTextField.getText()));
            }
        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_ADDRESS_CONSTRAINTS);
            throw new Exception();
        }

        try {
            String[] webLinks = webLinkTextField.getText().split(", ");

            Comparator<WebLink> comparator = Comparator.comparing(WebLink::toStringWebLink);

            Set<WebLink> webLinkSet = new TreeSet<WebLink>(comparator);

            for (String curr : webLinks) {
                webLinkSet.add(new WebLink(curr));
            }
            userPerson.setWebLinks(webLinkSet);
        } catch (IllegalValueException e) {
            statusLabel.setText("Please input a valid webLink");
            throw new Exception();
        } catch (ClassCastException e) {
            statusLabel.setText("Class cast exception");
            throw new Exception();
        }
    }
}
```
###### /resources/view/UserProfileWindow.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ButtonBar?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox alignment="CENTER" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox alignment="CENTER" fillHeight="false" prefHeight="35.0" prefWidth="200.0" VBox.vgrow="NEVER">
         <children>
            <Label contentDisplay="CENTER" text="User Profile">
               <font>
                  <Font size="20.0" />
               </font></Label>
         </children>
      </HBox>
      <GridPane VBox.vgrow="ALWAYS">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="296.0" minWidth="10.0" prefWidth="163.0" />
          <ColumnConstraints hgrow="ALWAYS" minWidth="10.0" />
        </columnConstraints>
        <rowConstraints>
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="60.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label contentDisplay="BOTTOM" text="Name">
               <GridPane.margin>
                  <Insets left="10.0" />
               </GridPane.margin>
            </Label>
            <Label text="Emails" GridPane.rowIndex="1">
               <GridPane.margin>
                  <Insets left="10.0" />
               </GridPane.margin>
            </Label>
            <Label text="Phone" GridPane.rowIndex="2">
               <GridPane.margin>
                  <Insets left="10.0" />
               </GridPane.margin>
            </Label>
            <Label text="Address" GridPane.rowIndex="3">
               <GridPane.margin>
                  <Insets left="10.0" />
               </GridPane.margin>
            </Label>
            <Label text="Weblink" GridPane.rowIndex="4">
               <GridPane.margin>
                  <Insets left="10.0" />
               </GridPane.margin>
            </Label>
            <TextField fx:id="nameTextField" GridPane.columnIndex="1" />
            <TextField fx:id="emailTextField" prefHeight="50.0" GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <TextField fx:id="phoneTextField" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <TextField fx:id="addressTextField" GridPane.columnIndex="1" GridPane.rowIndex="3" />
            <TextField fx:id="webLinkTextField" GridPane.columnIndex="1" GridPane.rowIndex="4" />
         </children>
      </GridPane>
      <Label fx:id="statusLabel" text="Label" />
      <ButtonBar prefHeight="40.0" prefWidth="200.0">
        <buttons>
          <Button fx:id="cancelButton" mnemonicParsing="false" onAction="#handleCancel" text="Cancel" />
            <Button fx:id="okButton" mnemonicParsing="false" onAction="#handleOk" text="OK" />
        </buttons>
      </ButtonBar>
   </children>
   <padding>
      <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
   </padding>
</VBox>
```
