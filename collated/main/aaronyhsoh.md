# aaronyhsoh
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Favourites an exisiting contact
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to your favourite contacts "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Added person to favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_UNFAVOURITE_PERSON_SUCCESS = "Person removed from favourites: ";

    private final Index index;


    /**
     * @param index of the person in the filtered person list to edit
     */
    public FavouriteCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFavourite = lastShownList.get(index.getZeroBased());

        Person favouritedPerson = createFavouritedPerson(personToFavourite);

        try {
            model.favouritePerson(personToFavourite, favouritedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (personToFavourite.getFavourite()) {
            return new CommandResult(String.format(MESSAGE_UNFAVOURITE_PERSON_SUCCESS, favouritedPerson));
        } else {
            return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_SUCCESS, favouritedPerson));
        }

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToFavourite}
     */
    private static Person createFavouritedPerson(ReadOnlyPerson personToFavourite) {
        assert personToFavourite != null;

        Name originalName = personToFavourite.getName();
        Phone originalPhone = personToFavourite.getPhone();
        Email originalEmail = personToFavourite.getEmail();
        Address originalAddress = personToFavourite.getAddress();
        Set<Tag> originalTags = personToFavourite.getTags();
        List<TodoItem> originalTodoItems = personToFavourite.getTodoItems();
        boolean newFavourite = !personToFavourite.getFavourite();

        return new Person(originalName, originalPhone, originalEmail, originalAddress,
                originalTags, originalTodoItems, newFavourite);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        // state check
        FavouriteCommand e = (FavouriteCommand) other;
        return index.equals(e.index);
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
        /**
         * Allows certain fields entered to be blank.
         * Shows a '-' for fields not entered.
         */
        if (!args.contains("a/")) {
            args = args + " a/ -";
        }
        if (!args.contains("e/")) {
            args = args + " e/ -";
        }
        if (!args.contains("p/")) {
            args = args + " p/ -";
        }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FavouriteCommand.COMMAND_WORD:
        case FavouriteCommand.COMMAND_ALIAS:
            return new FavouriteCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavouriteCommand object
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns an FavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavouriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Replaces the given person {@code target} in the list with {@code favouritedReadOnlyPerson}.
     * Sorts the list to show favourite contacts first.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code favouritedReadOnlyPerson}.
     *
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(favouritedReadOnlyPerson);

        Person favouritedPerson = new Person(favouritedReadOnlyPerson);

        syncMasterTagListWith(favouritedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, favouritedPerson);

        UniquePersonList notFavouriteList = new UniquePersonList();
        UniquePersonList favouriteList = new UniquePersonList();
        for (ReadOnlyPerson person : persons) {
            if (person.getFavourite()) {
                favouriteList.add(person);
            } else {
                notFavouriteList.add(person);
            }
        }
        persons.setPersons(favouriteList);
        for (ReadOnlyPerson person : notFavouriteList) {
            persons.add(person);
        }
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Replaces the given person {@code target} with {@code favouritedPerson}.
     *
     * @throws DuplicatePersonException if favouriting the person causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, favouritedPerson);

        addressBook.favouritePerson(target, favouritedPerson);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

```
