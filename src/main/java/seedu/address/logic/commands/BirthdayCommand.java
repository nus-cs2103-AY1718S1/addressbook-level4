package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Birthday;

/**
 + * Changes the birthday of an existing person in the address book.
 + */

public class BirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "birthday";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BIRTHDAY + "[BIRTHDAY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BIRTHDAY + "01011995";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Birthday: %2$s";

    private final Index index;
    private final Birthday birthday;

    /**
     * @param index    of the person in the filtered person list to edit birthday
     * @param birthday of the person
     */
    public BirthdayCommand(Index index, Birthday birthday) {
        requireNonNull(index);
        requireNonNull(birthday);

        this.index = index;
        this.birthday = birthday;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), birthday));
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }


        //instance of handle nulls
        if (!(other instanceof BirthdayCommand)) {
            return false;
        }

        //state check
        BirthdayCommand e = (BirthdayCommand) other;
        return index.equals(e.index)
                && birthday.equals(e.birthday);
    }
}
