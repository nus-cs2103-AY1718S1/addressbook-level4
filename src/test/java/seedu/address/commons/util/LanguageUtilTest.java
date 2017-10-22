//@@author A0143832J
package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LanguageUtilTest {
    @Test
    public void getClosestCommand() throws Exception {
        //for find
        assertEquals("find", getCommand("fnid"));
        assertEquals("find", getCommand("fin"));

        //for add
        assertEquals("add", getCommand("ad"));
        assertEquals("add", getCommand("dad"));

        //for delete
        assertEquals("delete", getCommand("dele"));
        assertEquals("delete", getCommand("detele"));

    }

    @Test
    public void levenshteinDistance() throws Exception {
        //addition
        assertEquals((Integer) 1, getLevenshteinDistance("ad", "add"));
        assertEquals((Integer) 2, getLevenshteinDistance("dele", "delete"));
        //substitution
        assertEquals((Integer) 2, getLevenshteinDistance("lsit", "list"));
        assertEquals((Integer) 3, getLevenshteinDistance("letede", "delete"));
        //subtraction
        assertEquals((Integer) 1, getLevenshteinDistance("liste", "list"));
        assertEquals((Integer) 2, getLevenshteinDistance("deletete", "delete"));
        //mixed: 1 sub + 1 subtraction
        assertEquals((Integer) 3, getLevenshteinDistance("lsite", "list"));
    }

    /**
     * Return result from getClosestCommand in languageUtil
     * @param cmd commandWord
     * @return nearest commandword
     */
    private String getCommand(String cmd) {
        return LanguageUtil.getClosestCommand(cmd);
    }

    /**
     * Return Levenshtein distance between 2 strings
     * @param s1 first string
     * @param s2 second string
     * @return levenshtein distance
     */
    private Integer getLevenshteinDistance(String s1, String s2) {
        return LanguageUtil.levenshteinDistance(s1, s1.length(), s2, s2.length());
    }

}
