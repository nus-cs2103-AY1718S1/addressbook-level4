package seedu.address.logic.autocomplete;

/**
 * API of the AutoCompleteLogic component
 */
public interface AutoCompleteLogic {

    /**
     * Generates all possible complete input that the user potentially want,
     * based on incomplete user input supplied.
     * @param stub incomplete user input specified.
     * @return AutoCompletePossibilities object containing list of all possible complete input.
     */
    AutoCompletePossibilities generateAutoCompletePossibilities(String stub);

}
