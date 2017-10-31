package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class ReplyCommand extends Command {
    public static final String COMMAND_WORDVAR_YES = "yes";
    public static final String COMMAND_WORDVAR_NO = "no";
    public static final String MESSAGE_COMMAND_ROLLBACK = "Command not executed.";
    public static final String MESSAGE_COMMAND_INVALID = "No command to confirm execution.";
    private static final String MESSAGE_COMMAND_MISHANDLED = "Command handled inappropriately!";


    private String toReply;
    private static ReadOnlyPerson personToEdit;
    private static Person storedPerson;

    public ReplyCommand(String reply) {
        toReply = reply;
    }

    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        if (UndoableCommand.isWaitingforReply) {
            if (AddCommand.requiresHandling) {
                return HandleAddCommand();
            } else if (EditCommand.requiresHandling) {
                return HandleEditCommand();
            } else {
                return new CommandResult(MESSAGE_COMMAND_MISHANDLED);
            }
        } else {
            return new CommandResult(MESSAGE_COMMAND_INVALID);
        }
    }

    private CommandResult HandleEditCommand() throws CommandException {
        if (toReply.equalsIgnoreCase(COMMAND_WORDVAR_YES)) {

            UndoableCommand.isWaitingforReply = false;
            EditCommand.requiresHandling = false;
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
            UndoableCommand.isWaitingforReply = false;
            EditCommand.requiresHandling = false;
            return new CommandResult(MESSAGE_COMMAND_ROLLBACK);
        }
    }

    private CommandResult HandleAddCommand() throws CommandException {
        if (toReply.equalsIgnoreCase(COMMAND_WORDVAR_YES)) {

            UndoableCommand.isWaitingforReply = false;
            AddCommand.requiresHandling = false;
            try {
                model.addPerson(storedPerson);
                return new CommandResult(String.format(MESSAGE_SUCCESS, storedPerson));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

        } else {
            UndoableCommand.isWaitingforReply = false;
            AddCommand.requiresHandling = false;
            return new CommandResult(MESSAGE_COMMAND_ROLLBACK);
        }
    }

    public static void storeAddCommandParameter(Person person) {
        storedPerson = person;
    }

    public static void storeEditCommandParameter(ReadOnlyPerson original, Person editedPerson) {
        personToEdit = original;
        storedPerson = editedPerson;
    }
}
