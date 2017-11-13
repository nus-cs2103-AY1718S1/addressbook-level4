package seedu.address.logic.parser;

import java.util.ArrayList;

/**
 * Contains helper methods for testing AddressBookParser
 */
public class AllCommandWordsAndAliases {
    private static ArrayList<String> listOfAllCommandWordsAndAliases;

    public static void initializeListOfAllCommandWordsAndAliases() {
        listOfAllCommandWordsAndAliases = new ArrayList<String>();
    }

    public static ArrayList<String> getListOfAllCommandWordsAndAliases() {
        return listOfAllCommandWordsAndAliases;
    }
}
