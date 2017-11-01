package seedu.address.logic.commands;

//@@author LeeYingZheng
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Replies prompt of duplicate fields from AddCommand and EditCommand.
 */
public class ReplyCommand extends Command {

    public static final String COMMAND_WORDVAR_YES = "yes";
    public static final String COMMAND_WORDVAR_NO = "no";
    public static final String MESSAGE_COMMAND_ROLLBACK = "Command not executed.";
    public static final String MESSAGE_COMMAND_INVALID = "No command to confirm execution.";
    private static final String MESSAGE_COMMAND_MISHANDLED = "Command handled inappropriately!";

    private static ReadOnlyPerson personToEdit;
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

        if (UndoableCommand.isWaitingforReply) {
            if (AddCommand.requiresHandling()) {
                return handleAddCommand();
            } else if (EditCommand.requiresHandling()) {
                return handleEditCommand();
            } else {
                return new CommandResult(MESSAGE_COMMAND_MISHANDLED);
            }
        } else {
            return new CommandResult(MESSAGE_COMMAND_INVALID);
        }
    }

    /**
     * Handle replies to EditCommand prompts
     */
    private CommandResult handleEditCommand() throws CommandException {

        if (toReply.equalsIgnoreCase(COMMAND_WORDVAR_YES)) {

            UndoableCommand.reply();
            EditCommand.setHandlingFalse();
            try {
                model.updatePerson(personToEdit, storedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, storedPerson));

        } else {
            UndoableCommand.reply();
            EditCommand.setHandlingFalse();
            return new CommandResult(MESSAGE_COMMAND_ROLLBACK);
        }
    }

    /**
     * Handle replies to AddCommand prompts
     */
    private CommandResult handleAddCommand() throws CommandException {

        if (toReply.equalsIgnoreCase(COMMAND_WORDVAR_YES)) {

            UndoableCommand.reply();
            AddCommand.setHandlingFalse();
            try {
                model.addPerson(storedPerson);
                return new CommandResult(String.format(MESSAGE_SUCCESS, storedPerson));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

        } else {
            UndoableCommand.reply();
            AddCommand.setHandlingFalse();
            return new CommandResult(MESSAGE_COMMAND_ROLLBACK);
        }
    }

    /**
     * Stores person to add.
     */
    public static void storeAddCommandParameter(Person person) {
        storedPerson = person;
    }

    /**
     * Stores original person to be edited and the final editedPerson.
     */
    public static void storeEditCommandParameter(ReadOnlyPerson original, Person editedPerson) {
        personToEdit = original;
        storedPerson = editedPerson;
    }
}
