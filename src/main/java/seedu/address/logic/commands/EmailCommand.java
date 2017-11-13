//@@author conantteo
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.EmailRequestEvent;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Email one or more persons identified using it's last displayed index from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Email one or more persons identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX [INDEX]... (must be a positive and no repeated integers)\n"
            + "Example: " + COMMAND_WORD + " 1" + " [2]" + " [3]";

    public static final String MESSAGE_EMAIL_PERSON_SUCCESS = "Email Person: %1$s";

    private final Index[] targetIndices;

    public EmailCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        StringBuilder addresses = new StringBuilder();
        StringBuilder persons = new StringBuilder();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                        + targetIndex.getOneBased());
            }
            ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
            if (personToEmail.getEmail().toString().isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_TO_EMAIL
                        + targetIndex.getOneBased());
            }
            // Concatenate the names and email addresses of each person
            persons.append(", " + personToEmail.getName().toString());
            addresses.append(" " + personToEmail.getEmail().toString());
        }

        String allPersons = StringUtil.getSubstringFromIndexTwo(persons.toString());
        String allEmailAddresses = StringUtil.replaceWhiteSpaceWithComma(addresses.toString());

        EventsCenter.getInstance().post(new EmailRequestEvent(allEmailAddresses));
        return new CommandResult(String.format(MESSAGE_EMAIL_PERSON_SUCCESS, allPersons));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && IndexArrayUtil.compareIndexArrays(this.targetIndices, (
                        (EmailCommand) other).targetIndices)); // state check
    }
}
