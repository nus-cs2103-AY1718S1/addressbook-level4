package seedu.address.ui;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import seedu.address.model.person.ReadOnlyPerson;

//@@author newalter
/**
 * This class enables auto-completion feature as a drop down menu from the command box.
 */
public class AutoCompleteTextField extends TextField {

    private static final int MAX_ENTRIES = 5;

    private final ContextMenu dropDownMenu = new ContextMenu();
    private final SuggestionHeuristic heuristic = new SuggestionHeuristic();
    private String prefixWords;
    private String lastWord;

    public AutoCompleteTextField() {
        super();
        // calls generateSuggestions() whenever there is a change to the text of the command box.
        textProperty().addListener((unused1, unused2, unused3) -> generateSuggestions());
        // hides the drop down menu when the focus changes
        focusedProperty().addListener((unused1, unused2, unused3) -> dropDownMenu.hide());
    }

    /**
     * Updates the drop down menu with the new set of matchedWords
     * Shows the menu if the set is non empty.
     * Hides the menu otherwise.
     */
    private void generateSuggestions() {
        splitWords();
        SortedSet<String> matchedWords = heuristic.getSuggestions(prefixWords, lastWord);
        if (matchedWords.size() <= 0) {
            dropDownMenu.hide();
            return;
        }

        fillDropDown(matchedWords);
        if (!dropDownMenu.isShowing()) {
            dropDownMenu.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
        }
    }

    /**
     * Initialises suggestion heuristic for Auto-Completion from
     * the different fields of persons from a list.
     * @param persons a list of person
     */
    public void initialiseHeuristic(List<ReadOnlyPerson> persons) {
        heuristic.initialise(persons);
    }

    /**
     * Fill the dropDownMenu with the matched words up to MAX_ENTRIES.
     * @param matchedWords The list of matched words.
     */
    private void fillDropDown(SortedSet<String> matchedWords) {
        List<MenuItem> menuItems = dropDownMenu.getItems();
        menuItems.clear();

        Iterator<String> iterator = matchedWords.iterator();
        int numEntries = Math.min(matchedWords.size(), MAX_ENTRIES);
        for (int i = 0; i < numEntries; i++) {
            final String suggestion = prefixWords + iterator.next();
            MenuItem item = new CustomMenuItem(new Label(suggestion), true);
            // Complete the word with the chosen suggestion when Enter is pressed.
            item.setOnAction((unused) -> complete(item));
            menuItems.add(item);
        }
    }

    /**
     * Sets the text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    public void replaceText(String text) {
        setText(text);
        positionCaret(getText().length());
    }

    /**
     * Auto-Complete with the first item in the dropDownMenu
     */
    public void completeFirst() {
        if (dropDownMenu.isShowing()) {
            MenuItem item = dropDownMenu.getItems().get(0);
            complete(item);
        }
    }

    /**
     * Auto-Complete with the given MenuItem
     * @param item the MenuItem used for Auto-Complete
     */
    private void complete(MenuItem item) {
        String suggestion = ((Label) ((CustomMenuItem) item).getContent()).getText();
        replaceText(suggestion);
    }

    /**
     * Splits the command in the command box into
     * two parts by the last occurrence of space or slash.
     * Store them into prefixWords and lastWord respectively.
     */
    private void splitWords() {
        String text = getText();
        int lastSpace = text.lastIndexOf(" ");
        int lastSlash = text.lastIndexOf("/");
        int splitingPosition = Integer.max(lastSlash, lastSpace);
        prefixWords = text.substring(0, splitingPosition + 1);
        lastWord = text.substring(splitingPosition + 1).toLowerCase();
    }

    public ContextMenu getDropDownMenu() {
        return dropDownMenu;
    }
}
