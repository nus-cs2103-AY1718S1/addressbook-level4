package seedu.address.logic.autocomplete;

/** Utility class that contains commonly used methods in AutoComplete feature */
public class AutoCompleteUtils {

    /**
     * Checks if the command word starts with the letters of the incomplete command stub provided
     * This method is case-insensitive
     * @param stub incomplete command supplied by the user
     * @param fullWord any possible full word that is checked against
     * @return true if fullWord contains stub as the first few letters
     */
    public static boolean startWithSameLetters(String stub, String fullWord) {
        if (stub.length() <= fullWord.length()) {
            return stub.toLowerCase().equals(fullWord.toLowerCase().substring(0, stub.length()));
        } else {
            return false;
        }
    }

    /**
     * Returns the index of the first occurrence of {@code prefix} in
     * {@code argsString}. An occurrence
     * is valid if there is a whitespace before {@code prefix}. Returns -1 if no
     * such occurrence can be found.
     *
     * E.g if {@code argsString} = "e/hip/900", {@code prefix} = "p/",
     * this method returns -1 as there are no valid occurrences of "p/" with whitespace before it.
     * However, if {@code argsString} = "e/hi p/900",
     * {@code prefix} = "p/", this method returns 5.
     */
    public static int findPrefixPosition(String argsString, String prefix) {
        int prefixIndex = argsString.indexOf(" " + prefix);
        return prefixIndex == -1 ? -1
                : prefixIndex + 3; // +1 as offset for whitespace and prefix
    }

}
