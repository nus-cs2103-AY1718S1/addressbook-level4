# aaronyhsoh
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
/**
 * Favourites an existing contact
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Removes a person to/from your favourite contacts"
            + " by the index number used in the last person listing.\n"
            + "Parameters: [INDEX] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Added person to favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_UNFAVOURITE_PERSON_SUCCESS = "Removed person from favourites: %1$s";

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

        if (!favouritedPerson.getFavourite()) {
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
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the most recent N undo commands, where (N = to the INDEX entered)\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";
```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    private final Index index;

    public RedoCommand() {
        index = null;
    }

    public RedoCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        if (index == null) {
            undoRedoStack.popRedo().redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            int commandsToRedo = index.getOneBased();
            while (commandsToRedo != 0 && undoRedoStack.canRedo()) {
                undoRedoStack.popRedo().redo();
                commandsToRedo -= 1;
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.index.equals(((RedoCommand) other).index)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the most recent N commands, where (N = to the INDEX entered)\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    private final Index index;

    public UndoCommand() {
        index = null;
    }

    public UndoCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (index == null) {
            undoRedoStack.popUndo().undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            int commandsToUndo = index.getOneBased();
            while (commandsToUndo != 0 && undoRedoStack.canUndo()) {
                undoRedoStack.popUndo().undo();
                commandsToUndo -= 1;
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.index.equals(((UndoCommand) other).index)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Allows certain fields entered to be blank.
     * Shows a '-' for fields not entered.
     */
    private static String initialiseEmptyFields(String args) {
        if (!args.contains("a/")) {
            args = args + " a/ -";
        }
        if (!args.contains("e/")) {
            args = args + " e/ -";
        }
        if (!args.contains("p/")) {
            args = args + " p/ -";
        }
        return args;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommandParser().parse(arguments);

        case FavouriteCommand.COMMAND_WORD:
        case FavouriteCommand.COMMAND_ALIAS:
            return new FavouriteCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
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
###### \java\seedu\address\logic\parser\RedoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RedoCommand
     * and returns an RedoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new RedoCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
            }
        } else {
            return new RedoCommand();
        }
    }

}

```
###### \java\seedu\address\logic\parser\UndoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new UndoCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
            }
        } else {
            return new UndoCommand();
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
