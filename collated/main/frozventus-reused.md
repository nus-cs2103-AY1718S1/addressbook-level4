# frozventus-reused
###### \java\seedu\address\logic\commands\BinDeleteCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class BinDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bindelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index number used in the last bin listing.\n"
            + "Parameters: INDEX (must be positive integers in ascending order, separated by a comma)\n"
            + "Example: " + COMMAND_WORD + " 1, 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted from Bin: ";

    private final Index[] targetIndex;

    public BinDeleteCommand (Index... targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getRecycleBinList();
        for (Index targetIndex : targetIndex) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndex.length > 1) {
            for (int i = 1; i < targetIndex.length; i++) {
                if (targetIndex[i].getZeroBased() < targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX);
                } else if (targetIndex[i].getZeroBased() == targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_REPEATED_INDEXES);
                }
            }
        }

        ReadOnlyPerson personToDelete;
        String[] personDeletedMessage = new String[targetIndex.length];
        StringBuilder deleteMessage = new StringBuilder();

        for (int i = (targetIndex.length - 1); i >= 0; i--) {
            int target = targetIndex[i].getZeroBased();
            personToDelete = lastShownList.get(target);
            try {
                model.deleteFromBin(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            personDeletedMessage[i] = MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;
        }

        for (String message : personDeletedMessage) {
            deleteMessage.append(message);
            deleteMessage.append("\n");
        }
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(deleteMessage.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BinDeleteCommand // instanceof handles nulls
                && Arrays.equals(this.targetIndex, ((BinDeleteCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\RestoreCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class RestoreCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores the persons identified by the index number used in the last bin listing.\n"
            + "Parameters: INDEX (must be positive integers in ascending order, separated by a comma)\n"
            + "Example: " + COMMAND_WORD + " 1, 3";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS = "Restored Person: ";

    private final Index[] targetIndex;

    public RestoreCommand (Index... targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getRecycleBinList();
        for (Index targetIndex : targetIndex) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndex.length > 1) {
            for (int i = 1; i < targetIndex.length; i++) {
                if (targetIndex[i].getZeroBased() < targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX);
                } else if (targetIndex[i].getZeroBased() == targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_REPEATED_INDEXES);
                }
            }
        }

        ReadOnlyPerson personToRestore;
        String[] personRestoredMessage = new String[targetIndex.length];
        StringBuilder restoreMessage = new StringBuilder();

        for (int i = (targetIndex.length - 1); i >= 0; i--) {
            int target = targetIndex[i].getZeroBased();
            personToRestore = lastShownList.get(target);
            try {
                model.restorePerson(personToRestore);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
            }
            personRestoredMessage[i] = MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;
        }

        for (String message : personRestoredMessage) {
            restoreMessage.append(message);
            restoreMessage.append("\n");
        }
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(restoreMessage.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestoreCommand // instanceof handles nulls
                && Arrays.equals(this.targetIndex, ((RestoreCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\BinDeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BinDeleteCommand parse(String args) throws ParseException {
        try {
            Index[] index = ParserUtil.parseDeleteIndex(args);
            return new BinDeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinDeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\RestoreCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestoreCommand parse(String args) throws ParseException {
        try {
            Index[] index = ParserUtil.parseDeleteIndex(args);
            return new RestoreCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    void executeBinSort(String sortType) throws EmptyAddressBookException;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void executeBinSort(String sortType) throws EmptyAddressBookException {
        recycleBin.executeSort(sortType);
        indicateAddressBookChanged();
    }

```
