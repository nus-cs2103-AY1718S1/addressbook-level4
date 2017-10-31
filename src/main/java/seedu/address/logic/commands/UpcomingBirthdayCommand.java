package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

public class UpcomingBirthdayCommand extends Command {
    public static final String COMMAND_WORD = "UpcomingBirthday";

    public static final String MESSAGE_SUCCESS = "Upcoming birthdays are shown.";
    private static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public UpcomingBirthdayCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        Boolean ifListEmpty = model.ifListIsEmpty(contactList);
        if (!ifListEmpty) {
            model.sortListByUpcomingBirthday(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
