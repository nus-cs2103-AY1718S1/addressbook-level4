package seedu.address.logic.commands;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Changes the remark of an existing person in the address book.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";

    public static final String COMMAND_ALIAS = "c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Outputs a selectable string of text "
            + "that contains email addresses of the selected person(s) in email-friendly format.\n"
            + "Parameters: INDEX (One or more, positive integers separated by comma) \n"
            + "Example: " + COMMAND_WORD + " 1 " + "\n"
            + "Output: johndoe@example.com";

    private ArrayList<String> outputList = new ArrayList<String> ();

    private final ArrayList<Index> targetIndices;

    public CopyCommand(ArrayList<Index> targetIndices) {
        Collections.sort(targetIndices);
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < targetIndices.size(); i++) {
            Index targetIndex = targetIndices.get(i);

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson targetPerson = lastShownList.get(targetIndex.getZeroBased());
            outputList.add((targetPerson.getEmail()).toString());
        }

        String MESSAGE_OUTPUT = outputList.toString().substring(1, outputList.toString().length() - 1);

        //return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        return new CommandResult(MESSAGE_OUTPUT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && this.targetIndices.equals(((CopyCommand) other).targetIndices)); // state check
    }
}