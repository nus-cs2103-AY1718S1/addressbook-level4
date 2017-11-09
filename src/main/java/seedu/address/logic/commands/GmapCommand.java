package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayGmapEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.NameConsistsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Display google map of person identified using it's last displayed index or name from the address book.
 */
//@@author Choony93
public class GmapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display google map data of person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR\n"
            + ": Display google map data of person identified by the name used in the person listing.\n"
            + "Parameters: NAME \n"
            + "Example: " + COMMAND_WORD + " Bernice";

    private static final String MESSAGE_GMAP_PERSON_SUCCESS = "Displayed Google map of Person: %1$s";

    private final boolean usingIndex;
    private Index targetIndex = Index.fromOneBased(Integer.parseInt("1"));
    private NameConsistsKeywordsPredicate predicate = new NameConsistsKeywordsPredicate(new ArrayList<>());

    public GmapCommand(Index targetIndex) {
        this.usingIndex = true;
        this.targetIndex = targetIndex;
    }

    public GmapCommand(NameConsistsKeywordsPredicate predicate) {
        this.usingIndex = false;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (!this.usingIndex) {
            lastShownList = model.getPersonListByPredicate(predicate);
            try {
                this.targetIndex = ParserUtil.parseIndex("1");
            } catch (IllegalValueException ive) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
            }
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new DisplayGmapEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_GMAP_PERSON_SUCCESS,
                lastShownList.get(targetIndex.getZeroBased()).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GmapCommand // instanceof handles nulls
                && this.targetIndex.equals(((GmapCommand) other).targetIndex)) // state check
                || (other instanceof GmapCommand // instanceof handles nulls
                && this.predicate.equals(((GmapCommand) other).predicate)); // state check
    }
}
