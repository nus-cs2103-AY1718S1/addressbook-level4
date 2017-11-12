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
###### \java\seedu\address\commons\util\StringUtil.java
``` java

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Birthday updatedBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Score updatedScore = editPersonDescriptor.getScore().orElse(personToEdit.getScore());
        return new Person(updatedName, updatedPhone, updatedBirthday, updatedEmail, updatedAddress,
                updatedScore, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     * Also, travis is bugged, so am adding code to force another commit.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Birthday birthday;
        private Email email;
        private Address address;
        private Score score;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.birthday = toCopy.birthday;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.score = toCopy.score;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.birthday, this.email,
                    this.address, this.score, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

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
###### \java\seedu\address\logic\commands\EditCommand.java
``` java

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

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public void setScore(Score score) {
            if (score.toString().equals("")) {
            } else {
                this.score = score;
            }
        }

        public Optional<Score> getScore() {
            return Optional.ofNullable(score);
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getBirthday().equals(e.getBirthday())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getScore().equals(e.getScore())
                    && getTags().equals(e.getTags());
        }
    }
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
###### \java\seedu\address\logic\commands\FavCommand.java
``` java

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
###### \java\seedu\address\logic\commands\FavouriteListCommand.java
``` java

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
###### \java\seedu\address\logic\commands\SortCommand.java
``` java

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
###### \java\seedu\address\logic\commands\UnfavCommand.java
``` java

```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of favourited persons */
    ObservableList<ReadOnlyPerson> getFilteredFavoritePersonList();

    String getCurrentList();
```
###### \java\seedu\address\logic\Logic.java
``` java

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
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
###### \java\seedu\address\logic\LogicManager.java
``` java

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Score score = ParserUtil.parseScore(argMultimap.getValue(PREFIX_SCORE)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, birthday, email, address, score, tagList);

            return new AddCommand(person);
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_WORD_ALIAS:
            return new SortCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD_ALIAS:
            return new ClearCommand();

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

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_ALIAS:
            return new FindCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FavouriteListCommand.COMMAND_WORD:
        case FavouriteListCommand.COMMAND_WORD_ALIAS:
            return new FavouriteListCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java


```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).ifPresent
                (editPersonDescriptor::setBirthday);
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseScore(argMultimap.getValue(PREFIX_SCORE)).ifPresent(editPersonDescriptor::setScore);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
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
###### \java\seedu\address\logic\parser\FavCommandParser.java
``` java

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
                (new Birthday("valueNotFound"));
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> score} into an {@code Optional<Score>} if {@code score} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
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
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java

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
###### \java\seedu\address\logic\parser\UnfavCommandParser.java
``` java

```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setFavouritePersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.favouritePersons.setPersons(persons);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
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

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
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
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * Also replaces the given person {@code target} in the Favourite list with {@code editedReadOnlyPerson}
     * if same person is listed on Favourite list too.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
        /* Line makes sure that Favourite List is synced with main list, if a person is
         * edited from the main list and is also present on the Favourite List,
         * the person will be automatically edited from fav list too
         */
        if (favouritePersons.contains(target)) {
            favouritePersons.setPerson(target, editedPerson);
        }
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
```
###### \java\seedu\address\model\AddressBook.java
``` java

    public void sortPersonsByBirthday() {
        persons.sortPersonsByBirthday();
    }

    public void sortPersonsByScore() {
        persons.sortPersonsByScore();
    }
    /**
     * Ensures that every tag in this person:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * Also removes {@code key} from the Favourite List if same person is listed on Fav list too.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {

        if (persons.remove(key)) {
            /* Line makes sure that Favourite List is synced with main list, if a person is
             * deleted from the main list and is also present on the Favourite List,
             * the person will be automatically deleted from fav list too
             */
            if (favouritePersons.contains(key)) {
                favouritePersons.remove(key);
            }
            return true; // returns true if Person is in the main list irrespective of Fav list
        } else {
            throw new PersonNotFoundException();
        }
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

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + favouritePersons.asObservableList().size()
                + " favourite persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFavouritePersonList() {
        return favouritePersons.asObservableList();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.favouritePersons.equals(((AddressBook) other).favouritePersons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        //return Objects.hash(persons, tags);
        return Objects.hash(persons, favouritePersons, tags);
    }
}

```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given person from Favourite List. */
    void removeFavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given person into Favourite List */
    void addFavouritePerson(ReadOnlyPerson person) throws DuplicatePersonException;
```
###### \java\seedu\address\model\Model.java
``` java

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

```
###### \java\seedu\address\model\Model.java
``` java
    String getCurrentList();

    void setCurrentList(String currentList);
```
###### \java\seedu\address\model\Model.java
``` java

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

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

```
###### \java\seedu\address\model\Model.java
``` java
    void updateFilteredFavouritePersonList(Predicate<ReadOnlyPerson> predicate);

    void changeListTo(String listName);
```
###### \java\seedu\address\model\Model.java
``` java
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    private final FilteredList<ReadOnlyPerson> filteredFavouritePersons;

    private String currentList;
```
###### \java\seedu\address\model\ModelManager.java
``` java

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
```
###### \java\seedu\address\model\ModelManager.java
``` java
        filteredFavouritePersons = new FilteredList<>(this.addressBook.getFavouritePersonList());
```
###### \java\seedu\address\model\ModelManager.java
``` java
        this.currentList = "list";
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

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
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
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
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
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

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        setCurrentList("list");
        return FXCollections.unmodifiableObservableList(filteredPersons);
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
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
###### \java\seedu\address\model\ModelManager.java
``` java

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

    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2"
            + "\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579]"
            + "[26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|"
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
        if (trimmedBirthday.equals("valueNotFound")) {
            this.value = "No Birthday Listed";
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
###### \java\seedu\address\model\person\Birthday.java
``` java

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
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.birthday = new SimpleObjectProperty<>(birthday);
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.score = new SimpleObjectProperty<>(score);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.avatarPic = new SimpleObjectProperty<>(avatar);

    }

    /**
     * Creates a copy of the given ReadOnlyPerson, which is nice.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getBirthday(), source.getEmail(),
                source.getAddress(), source.getScore(), source.getTags(), source.getAvatarPic());
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
###### \java\seedu\address\model\person\Person.java
``` java

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
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
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Score> scoreProperty();
    Score getScore();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    Avatar getAvatarPic();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getBirthday().equals(this.getBirthday())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("avatar: ")
                .append(getAvatarPic())
                .append(" Phone: ")
                .append(getPhone())
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Birthday: ")
                .append(getBirthday())
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     *Sorts the list alphabetically by name
     */
    public void sortPersonsByName() {

        Comparator<ReadOnlyPerson> personComparator = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson person1, ReadOnlyPerson person2) {

                String personName1 = person1.getName().toString();
                String personName2 = person2.getName().toString();

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

                String[] day1 = person1.getBirthday().toString().split("/");
                String[] day2 = person2.getBirthday().toString().split("/");

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
###### \java\seedu\address\model\person\UniquePersonList.java
``` java

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

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
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Birthday("16/08/1968"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Score("8"),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"),  new Birthday("21/04/1973"),
                        new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Score("5"),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),  new Birthday("11/01/1999"),
                        new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Score("9"),
                    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"),  new Birthday("17/07/2006"),
                        new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Score("2"),
                    getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),  new Birthday("31/05/1936"),
                        new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Score("1"),
                    getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),  new Birthday("7/09/1995"),
                        new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Score("3"),
                    getTagSet("colleagues"))
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    private String birthday;
    @XmlElement(required = true)
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private String score;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().value;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        email = source.getEmail().value;
        address = source.getAddress().value;
        score = source.getScore().value;
        avatar = source.getAvatarPic().source;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Birthday birthday = new Birthday(this.birthday);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Score score = new Score(this.score);
        final Set<Tag> tags = new HashSet<>(personTags);

```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        favouritePersons.addAll(src.getFavouritePersonList().stream().map(XmlAdaptedPerson::new)
                .collect(Collectors.toList()));
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        final ObservableList<ReadOnlyPerson> persons = this.persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(persons);
    }

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
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tags);
    }

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
###### \java\seedu\address\ui\ChangeInternalListEvent.java
``` java

```
###### \java\seedu\address\ui\CommandBox.java
``` java
        CommandResult commandResult = new CommandResult("");
        if (logic.getCurrentList().contains("favlist")) {
            commandResult = new CommandResult("Add command does not work in favourite list");;
```
###### \java\seedu\address\ui\CommandBox.java
``` java

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
###### \java\seedu\address\ui\CommandBox.java
``` java
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
###### \java\seedu\address\ui\MainWindow.java
``` java

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label birthday;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label score;
    @FXML
    private FlowPane tags;

```
###### \java\seedu\address\ui\PersonCard.java
``` java
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        score.textProperty().bind(Bindings.convert(person.scoreProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Initializes color tags
     * initializes tags for the person
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + mapTagToColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
        //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * checks equality to the person
     * @param other
     * @return if the person equals the param
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
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

```
###### \java\seedu\address\ui\PersonCard.java
``` java
        CommandResult commandResult = new CommandResult("");
        if (logic.getCurrentList().contains("favlist")) {
            commandResult = new CommandResult("Edit command does not work in favourite list");;
```
###### \java\seedu\address\ui\PersonCard.java
``` java

```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerPartsWithIndicatedList(event.getListName());
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
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
###### \resources\view\CommandBox.fxml
``` fxml

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
