package seedu.address.logic;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Monitors current displayed list on person list panel.
 * Obtains or updates the various lists according to the changes made to the current displayed list.
 */
public class ListObserver {

    public static final String MASTERLIST_NAME_DISPLAY_FORMAT = "MASTERLIST:\n";
    public static final String BLACKLIST_NAME_DISPLAY_FORMAT = "BLACKLIST:\n";
    public static final String WHITELIST_NAME_DISPLAY_FORMAT = "WHITELIST:\n";

    private Model model;

    public ListObserver(Model model) {
        this.model = model;
    }

    /**
     * Monitors current displayed list on person list panel.
     * @return updated version of the current displayed list.
     */
    public List<ReadOnlyPerson> getCurrentFilteredList() {
        String currentList = model.getCurrentListName();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            return model.getFilteredBlacklistedPersonList();

        case WhitelistCommand.COMMAND_WORD:
            return model.getFilteredWhitelistedPersonList();

        default:
            return model.getFilteredPersonList();
        }
    }

    /**
     * Monitors current displayed list on person list panel.
     * Updates the current displayed list with {@param predicate}
     * @return updated version of the current displayed list.
     */
    public int updateCurrentFilteredList(Predicate<ReadOnlyPerson> predicate) {
        String currentList = model.getCurrentListName();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            model.changeListTo(BlacklistCommand.COMMAND_WORD);
            return model.updateFilteredBlacklistedPersonList(predicate);

        case WhitelistCommand.COMMAND_WORD:
            model.changeListTo(WhitelistCommand.COMMAND_WORD);
            return model.updateFilteredWhitelistedPersonList(predicate);

        default:
            return model.updateFilteredPersonList(predicate);
        }
    }

    /**
     * Monitors current displayed list on person list panel.
     * @return name of current displayed list.
     */
    public String getCurrentListName() {
        String currentList = model.getCurrentListName();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            return BLACKLIST_NAME_DISPLAY_FORMAT;

        case WhitelistCommand.COMMAND_WORD:
            return WHITELIST_NAME_DISPLAY_FORMAT;

        default:
            return MASTERLIST_NAME_DISPLAY_FORMAT;
        }
    }

    /**
     * Monitors current displayed list on person list panel.
     * @return {@code Index} of current selected person.
     */
    public Index getIndexofSelectedPersonInCurrentList() {
        Index index;
        switch (model.getCurrentListName()) {

        case "blacklist":
            index = Index.fromZeroBased(model.getFilteredBlacklistedPersonList().indexOf(model.getSelectedPerson()));
            break;
        case "whitelist":
            index = Index.fromZeroBased(model.getFilteredWhitelistedPersonList().indexOf(model.getSelectedPerson()));
            break;
        default:
            index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(model.getSelectedPerson()));
        }

        return index;
    }

    public Index getIndexofPersonInCurrentList(ReadOnlyPerson person) {
        Index index;

        switch (model.getCurrentListName()) {
        case "blacklist":
            if (model.getFilteredBlacklistedPersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredBlacklistedPersonList()
                        .indexOf(person));
            } else {
                index = null;
            }
            break;

        case "whitelist":
            if (model.getFilteredWhitelistedPersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredWhitelistedPersonList()
                        .indexOf(person));
            } else {
                index = null;
            }
            break;

        default:
            if (model.getFilteredPersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(person));
            } else {
                index = null;
            }
        }

        return index;
    }
}
