package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";


    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s):\n%1$s";

    private ArrayList<Index> targetIndexArraylist = new ArrayList<Index>();

//    private final Index targetIndex;

//        public DeleteCommand(Index targetIndex) {
//        this.targetIndex = targetIndex;
//    }

    public DeleteCommand(ArrayList<Index> targetIndexArraylist) {
        this.targetIndexArraylist = targetIndexArraylist;
    }
//
    public DeleteCommand(Index targetIndex) {
//        ArrayList<Index> targetIndexArrayList = new ArrayList<Index>();
//        targetIndexArrayList.add(targetIndex);
//        this.targetIndexArraylist = targetIndexArraylist;
        this.targetIndexArraylist.add(targetIndex);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

//        if (targetIndex.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
//
//        try {
//            model.deletePerson(personToDelete);
//        } catch (PersonNotFoundException pnfe) {
//            assert false : "The target person cannot be missing";
//        }



        String result = "";
        String people = "";
        ReadOnlyPerson personToDelete = null;
        System.out.println("here");

        System.out.println("targetIndexArraylist size: " + this.targetIndexArraylist.size());
//
        for (Index i : this.targetIndexArraylist) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

//            ReadOnlyPerson personToDelete = lastShownList.get(i.getZeroBased());
            personToDelete = lastShownList.get(i.getZeroBased());

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

            people += personToDelete + "\n";
//            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }

        people = people.substring(0, people.length() - 1);
//
        result = String.format(MESSAGE_DELETE_PERSON_SUCCESS, people);

//        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        return new CommandResult(result);
    }

    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof DeleteCommand // instanceof handles nulls
//                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
//    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndexArraylist.equals(((DeleteCommand) other).targetIndexArraylist)); // state check
    }
}
