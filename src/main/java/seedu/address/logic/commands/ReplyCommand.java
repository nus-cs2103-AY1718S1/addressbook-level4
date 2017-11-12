package seedu.address.logic.commands;

//@@author LeeYingZheng
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddCommand.MESSAGE_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Replies prompt of duplicate fields from AddCommand and EditCommand.
 */
public class ReplyCommand extends Command {

    public static final String COMMAND_WORDVAR_YES = "yes";
    public static final String COMMAND_WORDVAR_NO = "no";
    public static final String MESSAGE_COMMAND_ROLLBACK = "Command not executed.";
    public static final String MESSAGE_COMMAND_INVALID = "No command to confirm execution.";

    private static Person storedPerson;

    private String toReply;

    /**
     * Creates an ReplyCommand to to reply {@code String} to AddCommand/EditCommand Prompt
     */
    public ReplyCommand(String reply) {
        toReply = reply;
    }

    /**
     * Executes ReplyCommand.
     */
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        if (AddCommand.isWaitingforReply()) {
            return handleAddCommand();
        } else {
            return new CommandResult(MESSAGE_COMMAND_INVALID);
        }
    }

    /**
     * Handle replies to AddCommand prompts
     */
    private CommandResult handleAddCommand() throws CommandException {

        if (toReply.equalsIgnoreCase(COMMAND_WORDVAR_YES)) {

            AddCommand.reply();
            try {
                model.addPerson(storedPerson);
                return new CommandResult(String.format(MESSAGE_SUCCESS, storedPerson));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

        } else {
            AddCommand.reply();
            return new CommandResult(MESSAGE_COMMAND_ROLLBACK);
        }
    }

    /**
     * Stores person to add.
     */
    public static void storeAddCommandParameter(Person person) {
        storedPerson = person;
    }

}
