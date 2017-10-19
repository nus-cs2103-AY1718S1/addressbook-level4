package seedu.address.logic.autocomplete.parser;

/** Utility class that contains commonly used methods in AutoComplete feature */
public class AutoCompleteUtils {

    /**
     * Checks if the command word starts with the letters of the incomplete command stub provided
     * @param stub incomplete command supplied by the user
     * @param fullWord any possible full word that is checked against
     * @return true if fullWord contains stub as the first few letters
     */
    public static boolean startWithSameLetters(String stub, String fullWord) {
        if (stub.length() <= fullWord.length()) {
            return stub.equals(fullWord.substring(0, stub.length()));
        } else {
            return false;
        }
    }

}
