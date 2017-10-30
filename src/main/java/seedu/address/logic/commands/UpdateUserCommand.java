package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Updates the model's UserPerson information.
 */
public class UpdateUserCommand extends Command {
    public static final String COMMAND_WORD = "update";
    public static final String COMMAND_ALIAS = "up";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits your user details in a similar "
            + "format to the ADD command. Cannot edit tags."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD
            + " " + PREFIX_NAME + "{NAME} "
            + PREFIX_PHONE + "{PHONE} "
            + PREFIX_EMAIL + "{EMAIL} "
            + PREFIX_ADDRESS + "{ADDRESS} ";

    public static final String MESSAGE_UPDATE_USER_SUCCESS = "Successfully edited User Profile: %1s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_TAGS_NOT_ALLOWED = "Unable to edit your own tags";


    private final EditPersonDescriptor editPersonDescriptor;

    public UpdateUserCommand (EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    public EditPersonDescriptor getEditPersonDescriptor() {
        return editPersonDescriptor;
    }

    @Override
    public CommandResult execute() throws CommandException {

        ReadOnlyPerson personToEdit = model.getUserPerson();
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);

        model.updateUserPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_UPDATE_USER_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateUserCommand)) {
            return false;
        }

        // state check
        UpdateUserCommand updateUserCommand = (UpdateUserCommand) other;
        return editPersonDescriptor.equals(updateUserCommand.getEditPersonDescriptor());
    }
}
