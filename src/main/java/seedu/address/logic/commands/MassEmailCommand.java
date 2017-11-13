package seedu.address.logic.commands;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.MassEmailRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.TagMatchingPredicate;

//@@author ReneeSeet

/**
 * Finds and lists all persons in address book whose tags keywords for group emailing
 * Keyword matching is case-sensitive.
 */

public class MassEmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_SUCCESS = "Listed all required emails";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To email all persons with tag\n"
             + COMMAND_WORD + " all : To email everyone in CYNC\n"
             + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
             + "Example: " + COMMAND_WORD + " Sec 2 Sec 3\n";

    private final TagMatchingPredicate predicate;

    public MassEmailCommand(TagMatchingPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> allPerson = model.getFilteredPersonList();
        ArrayList<String> emails = new ArrayList<String>();
        for (ReadOnlyPerson person : allPerson) {
            emails.add(person.getEmail().toString());
        }
        EventsCenter.getInstance().post(new MassEmailRequestEvent(emails));
        return new CommandResult(getMessageForMassEmail(allPerson.size(), emails));
    }

    public TagMatchingPredicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MassEmailCommand // instanceof handles nulls
                && this.predicate.equals(((MassEmailCommand) other).predicate)); // state check
    }
}
