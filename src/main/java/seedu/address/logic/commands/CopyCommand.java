package seedu.address.logic.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.email.Email;

/**
 * Changes the remark of an existing person in the address book.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";

    public static final String COMMAND_ALIAS = "y";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Outputs a selectable string of text "
            + "that contains email addresses of the selected person(s) in email-friendly format.\n"
            + "Parameters: INDEX (One or more, positive integers separated by comma) \n"
            + "Example: " + COMMAND_WORD + " 1 " + "\n"
            + "Output: johndoe@example.com";

    public static final String MESSAGE_COPY_PERSON_SUCCESS = "Copied to clipboard: %1$s";
    private ArrayList<String> outputList = new ArrayList<>();

    private final ArrayList<Index> targetIndices;

    public CopyCommand(ArrayList<Index> targetIndices) {
        Collections.sort(targetIndices);
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson targetPerson = lastShownList.get(targetIndex.getZeroBased());
            for (Email email: targetPerson.getEmails()) {
                outputList.add(email.toString());
            }
        }

        // outputList without square brackets
        String messageOutput = outputList.toString().substring(1, outputList.toString().length() - 1);
        // outputList use semi-colon separator
        messageOutput = messageOutput.replace(",", ";");

        // copy string to clipboard
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection messageOutputSelection = new StringSelection(messageOutput);
        clipboard.setContents(messageOutputSelection, null);

        return new CommandResult(String.format(MESSAGE_COPY_PERSON_SUCCESS, outputList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && this.targetIndices.equals(((CopyCommand) other).targetIndices)); // state check
    }
}
