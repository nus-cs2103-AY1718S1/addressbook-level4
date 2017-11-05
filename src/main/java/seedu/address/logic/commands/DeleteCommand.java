package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


//@@author Pengyuz
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */

public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_2 = "remove";
    public static final String COMMAND_WORD_3 = "-";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the recycle bin";
    private boolean allvalid = true;
    private boolean exist = false;
    private boolean duplicate = false;

    private ArrayList<Index> targetIndexs = new ArrayList<>();
    private String target = "";

    public DeleteCommand(ArrayList<Index> targetIndex) {
        this.targetIndexs = targetIndex;
    }
    public DeleteCommand(String target) {
        this.target = target;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList =  model.getFilteredPersonList();
        ArrayList<ReadOnlyPerson> personstodelete =  new ArrayList<ReadOnlyPerson>();
        if (target != "") {
            for (ReadOnlyPerson p: lastShownList) {
                if (p.getName().fullName.equals(target) && exist == true) {
                    personstodelete.add(p);
                    duplicate = true;
                }
                if (p.getName().fullName.equals(target)) {
                    personstodelete.add(p);
                    exist = true;
                }

            }
        } else {
            for (Index s: targetIndexs) {
                if (s.getZeroBased() >= lastShownList.size()) {
                    allvalid = false;
                } else {
                    personstodelete.add(lastShownList.get(s.getZeroBased()));
                    exist = true;
                }
            }
        }

        if (exist && duplicate) {
            List<String> duplicatePerson = Arrays.asList(target);
            NameContainsKeywordsPredicate updatedpredicate = new NameContainsKeywordsPredicate(duplicatePerson);
            model.updateFilteredPersonList(updatedpredicate);
            return new CommandResult("Duplicate persons exist, please choose one to delete.");
        }

        if (allvalid && exist) {
            try {
                for (ReadOnlyPerson o: personstodelete) {
                    if (model.getRecycleBin().getPersonList().contains(o)) {
                       model.deletePerson(o);
                       personstodelete.remove(o);
                    }
                }
                model.deletePerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException d) {
                assert false: "The duplicate person cannot exist";
            }
            return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndexs.equals(((DeleteCommand) other).targetIndexs))
                && (other instanceof DeleteCommand
                && this.target.equals(((DeleteCommand) other).target)); // state check
    }
}

