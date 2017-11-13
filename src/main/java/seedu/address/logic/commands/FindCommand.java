package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.PopulateBirthdayEvent;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Finds and lists all persons in UniCity whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 * If the name is not in UniCity, a list of the closest matching persons are listed instead.
 * If the input returns the keyword itself, this means that the given keyword did not pass the tolerance value and
 * hence does not match with any name in UniCity, In that case, all contacts are shown instead.
 */
public class FindCommand extends Command {

    //@@author LeeYingZheng
    public static final String COMMAND_WORDVAR_1 = "find";
    public static final String COMMAND_WORDVAR_2 = "f";
    //@@author

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers."
            + " Command is case-insensitive. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " alice bob charlie \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " alice bob charlie \n";

    private final NameContainsKeywordsPredicate predicate;


    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author vivekscl
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        int numberOfPersonsShown = model.getFilteredPersonList().size();
        boolean isPersonsNotFoundForGivenKeyword = numberOfPersonsShown == 0 && !predicate.getKeywords().isEmpty();

        if (isPersonsNotFoundForGivenKeyword) {
            String targets = model.getClosestMatchingName(predicate);
            List<String> targetsAsList = Arrays.asList(targets.split("\\s+"));

            if (targetsAsList.equals(predicate.getKeywords())) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(Messages.MESSAGE_NO_MATCHING_NAME_FOUND, predicate));
            }

            model.updateFilteredPersonList(new NameContainsKeywordsPredicate(targetsAsList));
            return new CommandResult(String.format(Messages.MESSAGE_NO_PERSON_FOUND, predicate,
                    String.join(", ", targetsAsList)));
        }
        EventsCenter.getInstance().post(new PopulateBirthdayEvent(model.getFilteredPersonList()));
        return new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsShown));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
