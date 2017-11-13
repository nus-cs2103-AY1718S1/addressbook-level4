# sofarsophie
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name, phone, email, "
            + "address, tag or group contain at least one of the keywords specified by parameter "
            + "and displays them as a list with index numbers. "
            + "Each parameter can be specified any number of times.\n"
            + "Parameters: [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [g/GROUP] [c/CUSTOMFIELD] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " n/alice p/910 a/bukit e/pp@pp.com c/meeting";

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts and displays the most recent persons listing based on a field identified by the prefix given.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts and lists the Persons in the last person listing based on the single given field. "
            + "Sorts Persons by name by default if no parameter is given.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_PHONE + "] "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_GROUP + "] "
            + "[" + PREFIX_ADDRESS + "]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE;

    public static final String MESSAGE_SORT_SUCCESS = "Sorted list!";

    private Prefix prefixToSortBy;

    public SortCommand() {
    }

    public SortCommand(Prefix prefixToSortBy) {
        this.prefixToSortBy = prefixToSortBy;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        ObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_PERSON_LIST);
        }
        model.sortFilteredPersonList(lastShownList, prefixToSortBy);

        return new CommandResult(MESSAGE_SORT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOM_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate.FindFields;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_CUSTOM_FIELD,
                        PREFIX_ADDRESS, PREFIX_GROUP, PREFIX_TAG);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME) || arePrefixesPresent(argMultimap, PREFIX_PHONE)
            || (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) || arePrefixesPresent(argMultimap, PREFIX_ADDRESS)
            || (arePrefixesPresent(argMultimap, PREFIX_TAG)) || arePrefixesPresent(argMultimap, PREFIX_GROUP)
            || arePrefixesPresent(argMultimap, PREFIX_CUSTOM_FIELD))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindFields fieldsToFind = new FindFields();
        try {
            parseNamesForSearch(argMultimap.getAllValues(PREFIX_NAME)).ifPresent(fieldsToFind::setNameKeywords);
            parsePhonesForSearch(argMultimap.getAllValues(PREFIX_PHONE)).ifPresent(fieldsToFind::setPhoneKeywords);
            parseEmailsForSearch(argMultimap.getAllValues(PREFIX_EMAIL)).ifPresent(fieldsToFind::setEmailKeywords);
            parseAddressesForSearch(argMultimap.getAllValues(PREFIX_ADDRESS))
                    .ifPresent(fieldsToFind::setAddressKeywords);
            parseGroupForSearch(argMultimap.getAllValues(PREFIX_GROUP)).ifPresent(fieldsToFind::setGroupKeywords);
            parseTagForSearch(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(fieldsToFind::setTagKeywords);
            parseCustomFieldsForSearch(argMultimap.getAllValues(PREFIX_CUSTOM_FIELD))
                    .ifPresent(fieldsToFind::setFieldsKeywords);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(fieldsToFind));
    }

    /**
     * Parses {@code List<String> names} into a {@code List<Name>} if {@code names} is non-empty.
     * If {@code names} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Name>} containing zero names.
     */
    private Optional<List<Name>> parseNamesForSearch(List<String> names) throws IllegalValueException {
        assert names != null;

        if (names.isEmpty()) {
            return Optional.empty();
        }
        List<String> nameList = names.size() == 1 && names.contains("") ? Collections.emptyList() : names;
        return Optional.of(ParserUtil.parseNames(nameList));
    }

    /**
     * Parses {@code List<String> phones} into a {@code List<Phone>} if {@code phones} is non-empty.
     * If {@code phones} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Phone>} containing zero phones.
     */
    private Optional<List<Phone>> parsePhonesForSearch(List<String> phones) throws IllegalValueException {
        assert phones != null;

        if (phones.isEmpty()) {
            return Optional.empty();
        }
        List<String> phoneList = phones.size() == 1 && phones.contains("") ? Collections.emptyList() : phones;
        return Optional.of(ParserUtil.parsePhones(phoneList));
    }

    /**
     * Parses {@code List<String> emails} into a {@code List<Email>} if {@code emails} is non-empty.
     * If {@code emails} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Email>} containing zero emails.
     */
    private Optional<List<Email>> parseEmailsForSearch(List<String> emails) throws IllegalValueException {
        assert emails != null;

        if (emails.isEmpty()) {
            return Optional.empty();
        }
        List<String> emailList = emails.size() == 1 && emails.contains("") ? Collections.emptyList() : emails;
        return Optional.of(ParserUtil.parseEmails(emailList));
    }

    /**
     * Parses {@code List<String> addresses} into a {@code List<Address>} if {@code addresses} is non-empty.
     * If {@code addresses} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Address>} containing zero addresses.
     */
    private Optional<List<Address>> parseAddressesForSearch(List<String> addresses) throws IllegalValueException {
        assert addresses != null;

        if (addresses.isEmpty()) {
            return Optional.empty();
        }
        List<String> addressList = addresses.size() == 1 && addresses.contains("")
                ? Collections.emptyList() : addresses;
        return Optional.of(ParserUtil.parseAddresses(addressList));
    }
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Collection<String> names} into a {@code List<Name>}
     */
    public static List<Name> parseNames(Collection<String> names) throws IllegalValueException {
        requireNonNull(names);
        final List<Name> nameList = new ArrayList<>();
        for (String name : names) {
            nameList.add(new Name(name));
        }
        return nameList;
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Collection<String> phones} into a {@code List<Phone>}
     */
    public static List<Phone> parsePhones(Collection<String> phones) throws IllegalValueException {
        requireNonNull(phones);
        final List<Phone> phoneList = new ArrayList<>();
        for (String phone : phones) {
            phoneList.add(new Phone(phone));
        }
        return phoneList;
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Collection<String> addresses} into a {@code List<Address>}
     */
    public static List<Address> parseAddresses(Collection<String> addresses) throws IllegalValueException {
        requireNonNull(addresses);
        final List<Address> addressList = new ArrayList<>();
        for (String address : addresses) {
            addressList.add(new Address(address));
        }
        return addressList;
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     *
     * Parses a {@code Collection<String> emails} into a {@code List<Email>}
     */
    public static List<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final List<Email> emailList = new ArrayList<>();
        for (String email : emails) {
            emailList.add(new Email(email));
        }
        return emailList;
    }
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses arguments and returns a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * If there are no arguments, returns a SortCommand object with the name prefix by default.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.equalsIgnoreCase(PREFIX_NAME.toString())) {
            return new SortCommand(PREFIX_NAME);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_ADDRESS.toString())) {
            return new SortCommand(PREFIX_ADDRESS);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_EMAIL.toString())) {
            return new SortCommand(PREFIX_EMAIL);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_PHONE.toString())) {
            return new SortCommand(PREFIX_PHONE);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_GROUP.toString())) {
            return new SortCommand(PREFIX_GROUP);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts addressbook according to field identified by the given prefix.
     */
    public void sortPersonsList(Prefix prefix) {
        persons.sortPersons(prefix);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    ObservableList<ReadOnlyPerson> sortFilteredPersonList(ObservableList<ReadOnlyPerson> unsortedList, Prefix prefix);

    /** Removes the specified tag from everyone in the AddressBook. */
    void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson currReadOnlyPerson = personList.get(i);

            Person newPerson = new Person(currReadOnlyPerson);
            Set<Tag> tagList = newPerson.getTags();
            tagList.remove(tag);
            newPerson.setTags(tagList);

            addressBook.updatePerson(currReadOnlyPerson, newPerson);
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> sortFilteredPersonList(ObservableList<ReadOnlyPerson> personsList,
                                                                 Prefix prefix) {
        addressBook.sortPersonsList(prefix);
        indicateAddressBookChanged();
        return personsList;
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    public int compareTo(Address a) {
        return this.value.compareTo(a.value);
    }

    public int compare(Address a, Address b) {
        return a.value.compareTo(b.value);
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    public int compareTo(Email e) {
        return this.value.compareTo(e.value);
    }

    public int compare(Email a, Email b) {
        return a.value.compareTo(b.value);
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
    public int compareTo(Name n) {
        return this.fullName.compareTo(n.fullName);
    }

    public int compare(Name a, Name b) {
        return a.fullName.compareTo(b.fullName);
    }
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
/**
 * Tests that any of {@code ReadOnlyPerson}'s fields
 * partially matches any of the keywords identified by the prefixes.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final FindFields findFields;

    public PersonContainsKeywordsPredicate(FindFields findFields) {
        this.findFields = new FindFields(findFields);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return ((findFields.getNameKeywordsStream().anyMatch(keyword
            -> StringUtil.containsPartialTextIgnoreCase(person.getName().fullName, keyword.fullName)))
                || (findFields.getPhoneKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getPhone().value, keyword.value)))
                || (findFields.getEmailKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getEmail().value, keyword.value)))
                || (findFields.getAddressKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getAddress().value, keyword.value)))
                || (findFields.getFieldsKeywordsStream().anyMatch(keyword
                    -> customFieldContainsWordIgnoreCase(person.getFields(), keyword)))
                || (findFields.getGroupsKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getGroup().groupName, keyword.groupName)))
                || (findFields.getTagsKeywordsStream().anyMatch(keyword
                    -> tagContainsWordIgnoreCase(person.getTags(), keyword))));
    }

    private boolean customFieldContainsWordIgnoreCase(Set<CustomField> fields, String keyword) {
        return fields.stream().anyMatch(field -> StringUtil.containsPartialTextIgnoreCase(field.toString(), keyword));
    }

    private boolean tagContainsWordIgnoreCase(Set<Tag> tags, String keyword) {
        return tags.stream().anyMatch(tag -> StringUtil.containsPartialTextIgnoreCase(tag.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.findFields.getNameKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getNameKeywords())
                && this.findFields.getPhoneKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getPhoneKeywords())
                && this.findFields.getEmailKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getEmailKeywords())
                && this.findFields.getAddressKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getAddressKeywords())
                && this.findFields.getGroupsKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getGroupsKeywords())); // state check
    }

    /**
     * Stores the list of fields to search with.
     * Supports operations to return a {@code Stream} or an {@code Optional} of the keywords.
     */
    public static class FindFields {
        private List<Name> nameKeywords;
        private List<Phone> phoneKeywords;
        private List<Email> emailKeywords;
        private List<Address> addressKeywords;
        private List<String> customFieldKeywords;
        private List<Group> groupKeywords;
        private List<String> tagKeywords;

        public FindFields() {}

        public FindFields(FindFields findFields) {
            this.nameKeywords = findFields.nameKeywords;
            this.phoneKeywords = findFields.phoneKeywords;
            this.emailKeywords = findFields.emailKeywords;
            this.addressKeywords = findFields.addressKeywords;
            this.customFieldKeywords = findFields.customFieldKeywords;
            this.groupKeywords = findFields.groupKeywords;
            this.tagKeywords = findFields.tagKeywords;
        }

        public void setNameKeywords(List<Name> names) {
            this.nameKeywords = names;
        }

        private Optional<List<Name>> getNameKeywords() {
            return Optional.ofNullable(nameKeywords);
        }

        private Stream<Name> getNameKeywordsStream() {
            return this.getNameKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setPhoneKeywords(List<Phone> phones) {
            this.phoneKeywords = phones;
        }

        private Optional<List<Phone>> getPhoneKeywords() {
            return Optional.ofNullable(phoneKeywords);
        }

        private Stream<Phone> getPhoneKeywordsStream() {
            return this.getPhoneKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setEmailKeywords(List<Email> emails) {
            this.emailKeywords = emails;
        }

        private Optional<List<Email>> getEmailKeywords() {
            return Optional.ofNullable(emailKeywords);
        }

        private Stream<Email> getEmailKeywordsStream() {
            return this.getEmailKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setGroupKeywords(List<Group> group) {
            this.groupKeywords = group;
        }

        private Optional<List<Group>> getGroupsKeywords() {
            return Optional.ofNullable(groupKeywords);
        }

        private Stream<Group> getGroupsKeywordsStream() {
            return this.getGroupsKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setTagKeywords(List<String> tags) {
            this.tagKeywords = tags;
        }

        private Optional<List<String>> getTagKeywords() {
            return Optional.ofNullable(tagKeywords);
        }

        private Stream<String> getTagsKeywordsStream() {
            return this.getTagKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setAddressKeywords(List<Address> addresses) {
            this.addressKeywords = addresses;
        }

        private Optional<List<Address>> getAddressKeywords() {
            return Optional.ofNullable(addressKeywords);
        }

        private Stream<Address> getAddressKeywordsStream() {
            return this.getAddressKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setFieldsKeywords(List<String> fields) {
            this.customFieldKeywords = fields;
        }

        private Optional<List<String>> getFieldKeywords() {
            return Optional.ofNullable(customFieldKeywords);
        }

        private Stream<String> getFieldsKeywordsStream() {
            return this.getFieldKeywords().map(List::stream).orElseGet(Stream::empty);
        }
    }
}
```
###### \java\seedu\address\model\person\Phone.java
``` java
    public int compareTo(Phone p) {
        return this.value.compareTo(p.value);
    }

    public int compare(Phone a, Phone b) {
        return a.value.compareTo(b.value);
    }
```
###### \java\seedu\address\model\person\ReadOnlyPersonComparator.java
``` java
/**
 * A comparator for comparing two {@code ReadOnlyPerson} objects by a given field identified the given {@code Prefix}.
 */
public class ReadOnlyPersonComparator implements Comparator<ReadOnlyPerson> {

    private static Prefix compareByPrefix;

    public ReadOnlyPersonComparator(Prefix compareByPrefix) {
        this.compareByPrefix = compareByPrefix;
    }

    /**
     * Returns a {@code ReadOnlyPersonComparator} with the given {@code Prefix}.
     */
    public ReadOnlyPersonComparator compareByPrefix(Prefix compareByPrefix) {
        this.compareByPrefix = compareByPrefix;
        return this;
    }

    @Override
    public int compare(ReadOnlyPerson a, ReadOnlyPerson b) {
        if (compareByPrefix.equals(PREFIX_ADDRESS)) {
            return a.getAddress().compareTo(b.getAddress());
        } else if (compareByPrefix.equals(PREFIX_EMAIL)) {
            return a.getEmail().compareTo(b.getEmail());
        } else if (compareByPrefix.equals(PREFIX_PHONE)) {
            return a.getPhone().compareTo(b.getPhone());
        } else if (compareByPrefix.equals(PREFIX_GROUP)) {
            return a.getGroup().compareTo(b.getGroup());
        } else if (compareByPrefix.equals(PREFIX_NAME)) {
            return a.getName().compareTo(b.getName());
        } else {
            return 0;
        }
    }

}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the internal list by the field identified by the given prefix.
     */
    public void sortPersons(Prefix prefixToSortBy) {

        Collections.sort(internalList, new ReadOnlyPersonComparator(prefixToSortBy));
        UniquePersonList replacement = new UniquePersonList();
        try {
            replacement.setPersons(internalList);
        } catch (DuplicatePersonException dpe) {
            dpe.getMessage();
        }
        setPersons(replacement);
    }
    //@author

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        sortControls = new SortControls(logic);
        sortControlsPlaceholder.getChildren().add(sortControls.getRoot());
```
###### \java\seedu\address\ui\SortControls.java
``` java
/**
 * The UI component that is responsible for processing a GUI-interaction triggered sort..
 */
public class SortControls extends UiPart<Region> {

    private static final String FXML = "SortControls.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    @FXML
    private ComboBox sortByDropdown;

    @FXML
    private Label dropdownLabel;

    public SortControls(Logic logic) {
        super(FXML);
        this.logic = logic;
        setDropDown();
        handleItemSelection();
    }

    /**
     * Sets the items in the dropdown list.
     */
    private void setDropDown() {
        sortByDropdown.getItems().addAll(
                "Name",
                "Address",
                "Phone",
                "Email",
                "Group"
        );
        sortByDropdown.getSelectionModel().selectFirst();
    }

    /**
     * Handles the event when a user selects an item from the dropdown list by
     * setting the matching prefix and triggering logic to execute a new sort command with the prefix.
     */

    private void handleItemSelection() {
        sortByDropdown.setOnAction((event) -> {
            String selectedField = sortByDropdown.getSelectionModel().getSelectedItem().toString();
            Prefix prefix;
            switch (selectedField) {
            case "Name": prefix = PREFIX_NAME;
                break;
            case "Address": prefix = PREFIX_ADDRESS;
                break;
            case "Phone": prefix = PREFIX_PHONE;
                break;
            case "Email": prefix = PREFIX_EMAIL;
                break;
            case "Group": prefix = PREFIX_GROUP;
                break;
            default: prefix = PREFIX_NAME;
            }
            try {
                CommandResult commandResult = logic.execute("sort " + prefix);
                initHistory();
                historySnapshot.next();
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

            } catch (CommandException | ParseException e) {
                initHistory();
                logger.info("Invalid sort");
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        });
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }
}
```
###### \resources\view\DarkTheme.css
``` css
.combo-box {
    -fx-background: white;
    -fx-background-color: transparent;
}

.combo-box .list-cell {
    -fx-background: white;
    -fx-background-color: transparent;
}

.combo-box-base {
    -fx-background-color: #eee;
}

.combo-box-base:hover {
    -fx-background-color: #dcdcdc;
}

.combo-box-popup .list-view .list-cell:hover {
    -fx-background-color: #dadada;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
      <StackPane fx:id="sortControlsPlaceholder">
        <padding>
          <Insets top="10" right="10" bottom="10" left="10" />
        </padding>
      </StackPane>
```
###### \resources\view\SortControls.fxml
``` fxml

<VBox prefHeight="45.0" prefWidth="318.0" styleClass="anchor-pane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <SplitPane dividerPositions="0.40822784810126583" prefHeight="45.0" prefWidth="314.0">
      <items>
          <Label fx:id="dropdownLabel" prefHeight="17.0" prefWidth="150.0" styleClass="label-bright" text="Sort by..." textFill="WHITE" />
          <ComboBox fx:id="sortByDropdown" prefHeight="27.0" prefWidth="136.0" />
      </items>
   </SplitPane>
</VBox>
```
