package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.place.Place;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.DuplicatePlaceException;

/**
 * Adds a place to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a place to the Tourist Book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_WEBSITE + "WEBSITE "
            + "[" + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_POSTAL_CODE + "POSTALCODE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Marina Bay Sands "
            + PREFIX_PHONE + "66888868 "
            + PREFIX_WEBSITE + "http://www.marinabaysands.com/ "
            + PREFIX_ADDRESS + "10 Bayfront Avenue "
            + PREFIX_POSTAL_CODE + "018956 "
            + PREFIX_TAG + "attractions";

    public static final String MESSAGE_SUCCESS = "New place added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLACE = "This place already exists in the Tourist Book";

    private final Place toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPlace}
     */
    public AddCommand(ReadOnlyPlace place) {
        toAdd = new Place(place);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPlace(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePlaceException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
