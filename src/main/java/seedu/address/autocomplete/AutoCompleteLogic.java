package seedu.address.autocomplete;

import seedu.address.logic.ListElementPointer;

//@@author john19950730
/**
 * API of the AutoCompleteLogic component
 */
public interface AutoCompleteLogic {

    /** Returns the list of possible options that autocomplete function should complete for the user */
    ListElementPointer getAutoCompleteSnapshot();

    /** Updates the current options based on new incomplete user input stub */
    void updateAutoCompletePossibilities(String stub);

}
