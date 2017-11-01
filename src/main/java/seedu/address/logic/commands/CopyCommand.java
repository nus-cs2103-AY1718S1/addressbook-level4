package seedu.address.logic.commands;

import java.util.List;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;


//@@author rushan-khor
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";
    public static final String COMMAND_ALIAS = "c"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the email address of the person identified by the index number used in the last person listing.\n"
            + COMMAND_ALIAS + ": Shorthand equivalent for Copy. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example 1: " + COMMAND_ALIAS + " 1 \n"
            + "Example 2: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_PERSON_SUCCESS = "%1$s's email address has been copied to your clipboard.";

    private final Index targetIndex;

    public CopyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson targetPerson = lastShownList.get(targetIndex.getZeroBased());
        String resultantEmailAddress = targetPerson.getEmail().toString();

        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        ClipboardContent systemClipboardContent = new ClipboardContent();
        systemClipboardContent.putString(resultantEmailAddress);

        systemClipboard.setContent(systemClipboardContent);

        return new CommandResult(String.format(MESSAGE_COPY_PERSON_SUCCESS, targetPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && this.targetIndex.equals(((CopyCommand) other).targetIndex)); // state check
    }
}
