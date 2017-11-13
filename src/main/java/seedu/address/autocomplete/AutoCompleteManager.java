package seedu.address.autocomplete;

import seedu.address.autocomplete.parser.IdentityParser;
import seedu.address.logic.ListElementPointer;
import seedu.address.model.Model;

//@@author john19950730
/** Manages autocomplete logic. */
public class AutoCompleteManager implements AutoCompleteLogic {

    private static final int AUTOCOMPLETE_CACHE_SIZE = 10;

    private final Model model;
    private final AutoCompletePossibilitiesManager possibilitiesManager;
    private AutoCompletePossibilities autoCompletePossibilities;

    public AutoCompleteManager(Model model) {
        this.model = model;
        possibilitiesManager = new AutoCompletePossibilitiesManager(model, AUTOCOMPLETE_CACHE_SIZE);
        autoCompletePossibilities = new AutoCompletePossibilities("", new IdentityParser());
    }

    @Override
    public ListElementPointer getAutoCompleteSnapshot() {
        return new ListElementPointer(autoCompletePossibilities.getPossibilities());
    }

    @Override
    public void updateAutoCompletePossibilities(String stub) {
        autoCompletePossibilities = possibilitiesManager.search(stub);
    }



}
