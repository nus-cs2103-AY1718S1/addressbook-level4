# siri99
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains any part of the {@code word}.
     *   Ignores case, and full word match is not required, word just has to be a substring.
     *   <br>examples:<pre>
     *       containsSubstringOfWord("ABc def", "abc") == true
     *       containsSubstringOfWord("ABc def", "DEF") == true
     *       containsSubstringOfWord("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word with minimum 2 characters
     */
    public static boolean containsSubstringOfWord(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        if (preppedWord.length() >= 2) {
            for (String wordInSentence : wordsInPreppedSentence) {
                if (wordInSentence.toLowerCase().contains(preppedWord.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Birthday updatedBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            if (!(birthday.toString().equals("No Birthday Listed"))) {
                this.birthday = birthday;
            }
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### \java\seedu\address\logic\commands\FavCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person identified using the index into the Favourite list.
 */
public class FavCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "fav";
    public static final String COMMAND_WORD_ALIAS = "fv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a person identified by the index number used to the Favourite list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAV_PERSON_SUCCESS = "Added person to Favourite list: %1$s";

    private final Index targetIndex;

    public FavCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFav = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.addFavouritePerson(personToFav);
        } catch (DuplicatePersonException e) {
            assert false : "The target person is already in the favourite list";
        }

        return new CommandResult(String.format(MESSAGE_FAV_PERSON_SUCCESS, personToFav));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteListCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FAVOURITE_PERSONS;

/**
 * Displays the Favourite list.
 */

public class FavouriteListCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favlist";
    public static final String COMMAND_WORD_ALIAS = "fl";

    public static final String MESSAGE_SUCCESS = "Listed all favourite contacts";

    @Override
    public CommandResult executeUndoableCommand() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredFavouritePersonList(PREDICATE_SHOW_ALL_FAVOURITE_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
* Sorts all persons in the address book by indicated format.
*/
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_WORD_ALIAS = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book in selected format.\n"
            + "Example: " + COMMAND_WORD + " : default sorts in alphabhetical order \n"
            + "Example: " + COMMAND_WORD + " name : sorts in alphabhetical order \n"
            + "Example: " + COMMAND_WORD + " birthday or " + COMMAND_WORD + " b : sorts in ascending order"
            + " of birthdays \n"
            + "Example: " + COMMAND_WORD + " score : sorts in descending order of scores";

    public static final String MESSAGE_SUCCESS_NAME = "Sorted successfully by Name, listing all persons below";

    public static final String MESSAGE_SUCCESS_BIRTHDAY = "Sorted successfully by Birthdays, listing all persons below";

```
###### \java\seedu\address\logic\commands\UnfavCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes a person identified using it's last displayed index from the favourite list.
 */
public class UnfavCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unfav";
    public static final String COMMAND_WORD_ALIAS = "unfv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove a person identified by the index number from the Favourite list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFAV_PERSON_SUCCESS = "Removed person from favourite list: %1$s";

    private final Index targetIndex;

    public UnfavCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredFavouritePersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnfav = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.removeFavouritePerson(personToUnfav);
        } catch (PersonNotFoundException e) {
            assert false : "The selected person is not in favourite list";
        }

        return new CommandResult(String.format(MESSAGE_UNFAV_PERSON_SUCCESS, personToUnfav));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnfavCommand) other).targetIndex)); // state check
    }

}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of favourited persons */
    ObservableList<ReadOnlyPerson> getFilteredFavoritePersonList();

    String getCurrentList();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredFavoritePersonList() {
        return model.getFilteredFavouritePersonList();
    }

    @Override
    public String getCurrentList() {
        return model.getCurrentList();
    }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_WORD_ALIAS:
            return new SortCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FavCommand.COMMAND_WORD:
        case FavCommand.COMMAND_WORD_ALIAS:
            return new FavCommandParser().parse(arguments);

        case UnfavCommand.COMMAND_WORD:
        case UnfavCommand.COMMAND_WORD_ALIAS:
            return new UnfavCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FavouriteListCommand.COMMAND_WORD:
        case FavouriteListCommand.COMMAND_WORD_ALIAS:
            return new FavouriteListCommand();
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).ifPresent
                (editPersonDescriptor::setBirthday);
```
###### \java\seedu\address\logic\parser\FavCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavCommand object
 */

public class FavCommandParser implements Parser<FavCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavCommand
     * and returns an FavCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */


    public FavCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        //return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.of
                (new Birthday("No Birthday Listed"));
    }
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, new Prefix(""));

        String sortFilter;
        sortFilter = argMultimap.getPreamble();

        if (sortFilter.equals("sort")) {
            //No arguments, set to default filter "name" which sorts in alphabhetical order
            sortFilter = "";
        }

        if (!(sortFilter.equals("name") || sortFilter.equals("birthday") || sortFilter.equals("b")
                || sortFilter.equals("") || sortFilter.equals("score") || sortFilter.equals("s"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(sortFilter);
    }
}
```
###### \java\seedu\address\logic\parser\UnfavCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnfavCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new UnfavCommand object
 */

public class UnfavCommandParser implements Parser<UnfavCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnfavCommand
     * and returns an UnfavCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public UnfavCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfavCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setFavouritePersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.favouritePersons.setPersons(persons);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
        try {
            setFavouritePersons(newData.getFavouritePersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /** Adds favourite person to addressBook
     * Also updates new tags found (if any)
     */
    public void addFavouritePerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        favouritePersons.add(newPerson);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the list of people in the address book.
     */
    public void sortPersonsByName() {
        persons.sortPersonsByName();
    }

    public void sortPersonsByBirthday() {
        persons.sortPersonsByBirthday();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeFavouritePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (favouritePersons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFavouritePersonList() {
        return favouritePersons.asObservableList();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given person from Favourite List. */
    void removeFavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given person into Favourite List */
    void addFavouritePerson(ReadOnlyPerson person) throws DuplicatePersonException;
```
###### \java\seedu\address\model\Model.java
``` java
    String getCurrentList();

    void setCurrentList(String currentList);
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered favourite person list */
    ObservableList<ReadOnlyPerson> getFilteredFavouritePersonList();

    /** Sorts the list in alphabhetical order of names */
    void sortFilteredPersonListName();

    /** Sorts the list in ascending order of birthdays : Jan to Dec */
    void sortFilteredPersonListBirthday();
```
###### \java\seedu\address\model\Model.java
``` java
    void updateFilteredFavouritePersonList(Predicate<ReadOnlyPerson> predicate);

    void changeListTo(String listName);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    private final FilteredList<ReadOnlyPerson> filteredFavouritePersons;

    private String currentList;
```
###### \java\seedu\address\model\ModelManager.java
``` java
        filteredFavouritePersons = new FilteredList<>(this.addressBook.getFavouritePersonList());
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void removeFavouritePerson(ReadOnlyPerson person) throws PersonNotFoundException {
        addressBook.removeFavouritePerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addFavouritePerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addFavouritePerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void changeListTo(String listName) {
        raise(new ChangeInternalListEvent(listName));
    }

    @Override
    public String getCurrentList() {
        return currentList;
    }

    @Override
    public void setCurrentList(String currentList) {
        this.currentList =  currentList;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredFavouritePersonList() {
        setCurrentList("favlist");
        return FXCollections.unmodifiableObservableList(filteredFavouritePersons);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateFilteredFavouritePersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredFavouritePersons.setPredicate(predicate);
    }

    @Override
    public void sortFilteredPersonListName() {
        addressBook.sortPersonsByName();
        indicateAddressBookChanged();
    }

    @Override
    public void sortFilteredPersonListBirthday() {
        addressBook.sortPersonsByBirthday();
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Day, Month and Year should be in DD/MM/YYYY format. Must be a valid date.";

    /*
     * Day, Month and Year should be valid. Should check if leap year, if 29th Feb is used.
     * DD, MM and YYYY should be separated by '/'. '.' and '-' separation not allowed.
     */
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2"
            + "\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579]"
            + "[26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|"
            + "[2-9]\\d)?\\d{2})$";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (trimmedBirthday.equals("No Birthday Listed")) {
            this.value = trimmedBirthday;
        } else {
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
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
}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
        Set<String> s1 = new HashSet<String>(keywords);
        String[] tagList = person.getOnlyTags().split(" ");
        Set<String> s2 = new HashSet<String>(Arrays.asList(tagList));

        return ((keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)))
                || (!Collections.disjoint(s2, s1))) || (keywords.stream().anyMatch(keyword ->
                StringUtil.containsSubstringOfWord(person.getName().fullName, keyword)));
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.birthday = new SimpleObjectProperty<>(birthday);
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday)  {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty()  {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Birthday: ")
                .append(getBirthday())
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     *Sorts the list alphabetically by name
     */
    public void sortPersonsByName() {

        Comparator<ReadOnlyPerson> personComparator = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson person1, ReadOnlyPerson person2) {

                String personName1 = person1.getName().toString().toLowerCase();
                String personName2 = person2.getName().toString().toLowerCase();

                return personName1.compareTo(personName2);
            }

        };

        FXCollections.sort(internalList, personComparator);
    }

    /**
     * Sorts the list of people in order of birthdays: Jan to Dec.
     */
    public void sortPersonsByBirthday() {

        Comparator<ReadOnlyPerson> personComparator = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson person1, ReadOnlyPerson person2) {

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
                String firstBirthday = person1.getBirthday().toString();
                String secondBirthday = person2.getBirthday().toString();

                /* if birthday is not listed, assigns them the last date of the year so that
                 *they are sorted to the bottom of the list */
                if (firstBirthday.equals("No Birthday Listed")) {
                    firstBirthday = "31/12/1900";
                }
                if (secondBirthday.equals("No Birthday Listed")) {
                    secondBirthday = "31/12/1900";
                }

                // Split into 3 parts - day, month and year
                String[] day1 = firstBirthday.split("/");
                String[] day2 = secondBirthday.split("/");

                String date1 = day1[1] + "/" + day1[0];
                String date2 = day2[1] + "/" + day2[0];

                Date birthday1 = null;
                Date birthday2 = null;
                sdf.setLenient(false);
                try {
                    birthday1 = sdf.parse(date1);
                    birthday2 = sdf.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return birthday1.compareTo(birthday2);

            }

        };

        FXCollections.sort(internalList, personComparator);
    }

```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    private String birthday;
    @XmlElement(required = true)
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().value;
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        favouritePersons.addAll(src.getFavouritePersonList().stream().map(XmlAdaptedPerson::new)
                .collect(Collectors.toList()));
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFavouritePersonList() {
        final ObservableList<ReadOnlyPerson> favouritePersons = this.favouritePersons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(favouritePersons);
    }
```
###### \java\seedu\address\ui\ChangeInternalListEvent.java
``` java
package seedu.address.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Creates an event to change the Internal List
 */
public class ChangeInternalListEvent extends BaseEvent {

    //private String listName;
    private final String listName;

    public ChangeInternalListEvent(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        CommandResult commandResult = new CommandResult("");
        if (logic.getCurrentList().contains("favlist")) {
            commandResult = new CommandResult("Add command does not work in favourite list");;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * handles button events given to it by the fxml document for which it is set as controller by
     * a constructor in UiPart. handleFavListButton event handles the event when the Favlist button
     * containing a 'star' icon to represent a fav list is clicked.
     * @param buttonEvent
     */
    @FXML
    private void handleFavlistButtonAction(ActionEvent buttonEvent) {
        try {
            CommandResult commandResult = logic.execute("favlist");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException | ParseException e) {
            // handling command failure
            logger.info("Delete call failed on index favlist");
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Changes displayed list accordingly
     */
    void fillInnerPartsWithIndicatedList(String listname) {
        switch(listname) {
        case "fl":
        case "favlist":
            personListPanel = new PersonListPanel(logic.getFilteredFavoritePersonList(), logic);
            break;

        default:
            personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic);
        }
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label birthday;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
```
###### \java\seedu\address\ui\PersonCard.java
``` java
            if (logic.getCurrentList().contains("favlist")) {
                commandResult = new CommandResult("Delete command does not work in favourite list");;
            } else {
                commandResult = logic.execute(delCommand);
            }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        CommandResult commandResult = new CommandResult("");
        if (logic.getCurrentList().contains("favlist")) {
            commandResult = new CommandResult("Edit command does not work in favourite list");;
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerPartsWithIndicatedList(event.getListName());
    }
```
###### \resources\view\CommandBox.fxml
``` fxml
  <Region prefHeight="10.0" prefWidth="1.0" HBox.hgrow="ALWAYS" />
  <Button onAction='#handleFavlistButtonAction' id="favlist" prefWidth="45.0" minWidth="45.0" prefHeight="31.0" minHeight="31.0">
    <graphic>
      <ImageView fitHeight="20.0" fitWidth="20.0">
        <image>
          <Image url="/images/favouritelist_icon.png" />
        </image>
      </ImageView>
    </graphic>
  </Button>
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday" />
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
      <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
```
