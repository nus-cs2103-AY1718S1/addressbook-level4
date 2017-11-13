package seedu.address.logic.commands;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author thehelpfulbees

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords,
 * or are included in a specified tag.
 * Keyword matching is case sensitive.
 * At present, only one tag can be searched for at a time
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Also allows for finding all persons in a specified tag, using the identifier \\t.\n"
            + "Parameters: [KEYWORD [MORE_KEYWORDS]]|[t\\TAG_KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie \n"
            + "Example: " + COMMAND_WORD + " t\\friend";

    private static final String MESSAGE_DUPLICATE_PERSON_WHEN_UPDATING =
            "Duplicate person error when updating num times searched.";
    private static final String MESSAGE_PERSON_NOT_FOUND_WHEN_UPDATING =
            "Person not found when updating num times searched.";


    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }



    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> filteredPersonList = model.getFilteredPersonList();

        for (ReadOnlyPerson personToEdit : filteredPersonList) {
            ReadOnlyPerson editedPerson = personToEdit;
            editedPerson.incrementNumTimesSearched();
            try {
                model.updatePerson(editedPerson, personToEdit);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON_WHEN_UPDATING);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError(MESSAGE_PERSON_NOT_FOUND_WHEN_UPDATING);
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(filteredPersonList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand) // other is FindCommand AND...
                && (((this.predicate instanceof NameContainsKeywordsPredicate) //it has a matching Name...Predicate OR
                && (((FindCommand) other).predicate) instanceof NameContainsKeywordsPredicate)
                && (this.predicate.equals(((FindCommand) other).predicate)))
                || (((this.predicate instanceof TagsContainKeywordPredicate) //it has a matching Tag...Predicate
                && (((FindCommand) other).predicate) instanceof TagsContainKeywordPredicate)
                && (this.predicate.equals(((FindCommand) other).predicate))); // state check
    }
}
