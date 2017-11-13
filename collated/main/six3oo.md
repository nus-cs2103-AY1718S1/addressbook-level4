# six3oo
###### /java/seedu/address/logic/commands/FavCommand.java
``` java
/**
 * Adds a person identified using it's last displayed index from the address book to the favourites list.
 */

public class FavCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "fave";
    public static final String COMMAND_ALIAS = "fv";
    public static final String COMMAND_HELP = "fave INDEX BOOLEAN";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or removes the person identified by the index number used in the last person listing to a "
            + "favourites list.\n"
            + "Parameters: INDEX (must be a positive integer), STATUS (must be a boolean variable, e.g. 'true' or "
            + "'false')\n"
            + "Example: " + COMMAND_WORD + " 1 true";

    public static final String MESSAGE_FAVE_PERSON_SUCCESS = "Added Person to Favourites: %1$s";
    public static final String MESSAGE_UNFAVE_PERSON_SUCCESS = "Removed Person from Favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the favourites list.";

    private final Index index;
    private final String faveString;

    /**
     * @param index of the person in the filtered person list to edit
     * @param faveState expression of the person's new favourites state
     */
    public FavCommand(Index index, String faveState) {
        requireNonNull(index);
        requireNonNull(faveState);
        this.index = index;
        this.faveString = faveState;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, faveString);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (faveString.equals("true")) {
            return new CommandResult(String.format(MESSAGE_FAVE_PERSON_SUCCESS, editedPerson));
        } else {
            return new CommandResult(String.format(MESSAGE_UNFAVE_PERSON_SUCCESS, editedPerson));
        }
    }

    /**
     * Creates and returns a {@code Person} with the updated faveState.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             String faveString) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        ChannelId updatedChannelId = personToEdit.getChannelId();
        Set<Tag> updatedTags = personToEdit.getTags();
        Favourite updatedFavs = new Favourite(faveString);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedChannelId, updatedTags,
                updatedFavs);
    }
}
```
###### /java/seedu/address/logic/commands/FindFavCommand.java
``` java
/**
 * Finds and lists all persons who are favourites.
 */
public class FindFavCommand extends Command {

    public static final String COMMAND_WORD = "findfav";
    public static final String COMMAND_ALIAS = "ffav";
    public static final String COMMAND_HELP = "findfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Find all persons who are in the favourites list.\n"
            + "Parameters: N/A\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed favourite persons";

    private final FavouritePredicate predicate;

    public FindFavCommand(FavouritePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
}
```
###### /java/seedu/address/model/person/Favourite.java
``` java
/**
 * Represents a Person's favourites status.
 * Guarantees: immutable
 */
public class Favourite {

    private boolean favourite;

    public Favourite(String faveState) {
        if (faveState.equals("true")) {
            this.favourite = true;
        } else {
            this.favourite = false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(favourite);
    }
}
```
###### /java/seedu/address/model/person/FavouritePredicate.java
``` java
/**
 * Tests if a {@code ReadOnlyPerson}'s {@code Favourite} is true.
 */
public class FavouritePredicate implements Predicate<ReadOnlyPerson> {

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        boolean result = false;
        if (readOnlyPerson.getFavourite().toString().equals("true")) {
            result = true;
        } else if (readOnlyPerson.getFavourite().toString().equals("false")) {
            result = false;
        }
        return result;
    }
}
```
