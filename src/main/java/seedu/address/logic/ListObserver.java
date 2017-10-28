package seedu.address.logic;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

public class ListObserver {

    private Model model;

    public ListObserver(Model model) {
        this.model = model;
    }

    public List<ReadOnlyPerson> getCurrentFilteredList() {
        String currentList = model.getCurrentList();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            return model.getFilteredBlacklistedPersonList();

        case WhitelistCommand.COMMAND_WORD:
            return model.getFilteredWhitelistedPersonList();

        default:
            return model.getFilteredPersonList();
        }
    }

    public void updateCurrentFilteredList(Predicate<ReadOnlyPerson> predicate) {
        String currentList = model.getCurrentList();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            model.changeListTo(BlacklistCommand.COMMAND_WORD);
            model.updateFilteredBlacklistedPersonList(predicate);
            break;

        case WhitelistCommand.COMMAND_WORD:
            model.changeListTo(WhitelistCommand.COMMAND_WORD);
            model.updateFilteredWhitelistedPersonList(predicate);
            break;

        default:
            model.updateFilteredPersonList(predicate);
        }
    }

    public String getCurrentListName() {
        String currentList = model.getCurrentList();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            return "BLACKLIST:\n";

        case WhitelistCommand.COMMAND_WORD:
            return "WHITELIST:\n";

        default:
            return "MASTERLIST:\n";
        }
    }
}
