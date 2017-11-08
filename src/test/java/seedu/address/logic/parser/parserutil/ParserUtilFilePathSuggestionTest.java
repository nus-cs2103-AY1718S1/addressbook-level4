package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParseableFilePath;
import static seedu.address.logic.parser.ParserUtil.parseFirstFilePath;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageExtension;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageFilepath;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserUtilFilePathSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void tryParseFirstFilePathAssertFalse() {
        assertFalse(isParseableFilePath("one two three")); // characters
        assertFalse(isParseableFilePath("~!@# $%^&*()_+")); // symbols
        assertFalse(isParseableFilePath("2147483649")); // numbers
        assertFalse(isParseableFilePath("////")); // empty directory
        assertFalse(isParseableFilePath("C:/")); // empty directory, with absolute prefix
        assertFalse(isParseableFilePath("C:////")); // empty directory, with absolute prefix
        assertFalse(isParseableFilePath("var////")); // empty directory, with relative prefix
        assertFalse(isParseableFilePath("var\\\\\\\\")); // empty directory, backslashes
    }

    @Test
    public void tryParseFirstFilePathAssertTrue() {
        assertTrue(isParseableFilePath("data/default.rldx"));
        assertTrue(isParseableFilePath("Prefix data/default"));
        assertTrue(isParseableFilePath("C:/Users/Downloads/my.rldx postfix"));
        assertTrue(isParseableFilePath("Some prefix C:/abc.rldx"));
        assertTrue(isParseableFilePath("pref  214/748/36/49 postfix"));
        assertTrue(isParseableFilePath("data////r"));
        assertTrue(isParseableFilePath("C:////g postfix"));
        assertTrue(isParseableFilePath("prefix var////f"));
        assertTrue(isParseableFilePath("prefix var\\a\\b\\c\\"));
    }

    @Test
    public void parseFirstFilePathWhitespacePrefixExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("data/default.rldx", " data/default.rldx");
    }

    @Test
    public void parseFirstFilePathNoExtensionReturnsFilePathWithExtension() throws IllegalArgumentException {
        assertFilePath("pref  214/748/36/49 postfix.rldx", "pref  214/748/36/49 postfix");
    }

    @Test
    public void parseFirstFilePathStringPrefixNoExtensionReturnsPrefixFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("Prefix data/default.rldx", "Prefix data/default");
    }

    @Test
    public void parseFirstFilePathMidFileExtensionReturnsFilePathWithReplaceEndedExtension()
            throws IllegalArgumentException {
        assertFilePath("C:/Users/Downloads/my rolodex.rldx", "C:/Users/Downloads/my.rldx rolodex");
    }

    @Test
    public void parseFirstFilePathStringPrefixExtensionReturnsInput() throws IllegalArgumentException {
        assertFilePath("Some prefix C:/abc.rldx", "Some prefix C:/abc.rldx");
    }

    @Test
    public void parseFirstFilePathRelativeEmptyFolderHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("data////r.rldx", "data////r");
    }

    @Test
    public void parseFirstFilePathAbsoluteEmptyFolderHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("C:////g.rldx", "C:////g");
    }

    @Test
    public void parseFirstFilePathAbsoluteEmptyFolderFinalHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("C:////g.rldx", "C:////g/");
    }

    @Test
    public void parseFirstFilePathRootRelativeEmptyFolderHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("var////f.rldx", "var////f");
    }

    @Test
    public void parseFirstFilePathRootRelativeEmptyFolderHierarchyBackslashNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("var/a/b/c.rldx", "var\\a\\b\\c\\");
    }

    /**
     * Attempts to parse a filepath String and assert the expected value and validity of the file.
     */
    public void assertFilePath(String expected, String input) {
        assertEquals(expected, parseFirstFilePath(input));
        String possibleFilepath = parseFirstFilePath(input);
        assertTrue(isValidRolodexStorageFilepath(possibleFilepath));
        assertTrue(isValidRolodexStorageExtension(possibleFilepath));
    }

    @Test
    public void parseFirstFilePathCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("one two three");
    }

    @Test
    public void parseFirstFilePathSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("~!@# $%^&*()_+");
    }

    @Test
    public void parseFirstFilePathNumbersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("2147483649");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithAbsolutePrefixThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("C:/");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithAbsolutePrefixLongThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("C:////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithRelativePrefixThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("var////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithRelativePrefixBackslashesThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("var\\\\\\\\");
    }
}
