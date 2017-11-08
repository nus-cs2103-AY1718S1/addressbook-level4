package seedu.address.autocomplete;

//@@author john19950730

import java.util.List;
import java.util.stream.Collectors;

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
    public static int findFirstPrefixPosition(String argsString, String prefix) {
        int prefixIndex = argsString.indexOf(" " + prefix);
        return prefixIndex == -1 ? -1
                : prefixIndex + 3; // +3 as offset for whitespace and prefix
    }

    /**
     * Returns the index of the last occurrence of {@code prefix} in
     * {@code argsString}. An occurrence
     * is valid if there is a whitespace before {@code prefix}. Returns -1 if no
     * such occurrence can be found.
     */
    public static int findLastPrefixPosition(String argsString, String prefix) {
        int prefixIndex = argsString.lastIndexOf(" " + prefix);
        return prefixIndex == -1 ? -1
                : prefixIndex + 3; // +3 as offset for whitespace and prefix
    }

    /**
     * Returns the command word in the stub.
     * @param stub incomplete user input
     * @return command word of the stub
     */
    public static String getCommandWordInStub(String stub) {
        return stub.split(" ")[0];
    }

    /**
     * Returns the section of stub that is not to be modified by autocomplete.
     * @param stub incomplete user input
     * @return Section of the stub that will not be modified
     */
    public static String getStaticSection(String stub) {
        String[] splitStub = stub.split(" ");
        String staticSection = "";
        for (int index = 0; index < splitStub.length - 1; ++index) {
            staticSection = staticSection + splitStub[index] + " ";
        }
        return staticSection;
    }

    /**
     * Returns the section of stub that is to be completed by autocomplete
     * @param stub incomplete user input
     * @return Section of the stub that will be modified
     */
    public static String getAutoCompleteSection(String stub) {
        String[] splitStub = stub.split(" ");
        return splitStub[splitStub.length - 1];
    }

    /**
     * Generates list of matches based on list of all possible options,
     * static section (not to be considered in matching)
     * and autocomplete section (to be matched with all possible matches)
     * @param allPossibleMatches list of all possible autocomplete options to match against
     * @param staticSection section of the stub to be left untouched
     * @param autoCompleteSection section of the stub to match for autocomplete
     * @return list of possible matches
     */
    public static List<String> generateListOfMatches(List<String> allPossibleMatches,
                                                     String staticSection, String autoCompleteSection) {
        return allPossibleMatches.stream()
                .filter(possibleMatch -> AutoCompleteUtils.startWithSameLetters(autoCompleteSection, possibleMatch))
                .map(filteredMatch -> staticSection + filteredMatch)
                .collect(Collectors.toList());
    }

}
