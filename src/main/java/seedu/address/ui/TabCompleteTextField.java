package seedu.address.ui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class TabCompleteTextField extends TextField {

    private static final int MAX_ENTRIES = 5;

    private final SortedSet<String> options = new TreeSet<>();
    private final ContextMenu dropDownMenu = new ContextMenu();

    private String prefixWords;
    private String lastWord;


    public TabCompleteTextField() {
        super();
        // calls generateSuggestions() whenever there is a change to the text of the command box.
        textProperty().addListener((unused1, unused2, unused3) -> generateSuggestions());
        // hides the drop down menu whenever the focus in not on the command box
        focusedProperty().addListener((unused1, unused2, unused3) -> dropDownMenu.hide());
    }

    /**
     * Generates a list of suggestions according to the prefix of the lastWord.
     * Shows the drop down menu if the menu is not empty.
     * Hides the menu otherwise.
     */
    private void generateSuggestions() {
        splitCommandWords();
        if (lastWord.length() == 0 || prefixWords.equals("")) {
            dropDownMenu.hide();
        } else {
            SortedSet<String> matchedWords =
                    options.subSet(lastWord + Character.MIN_VALUE, lastWord + Character.MAX_VALUE);
            if (matchedWords.size() > 0) {
                fillDropDown(matchedWords);
                if (!dropDownMenu.isShowing()) {
                    dropDownMenu.show(TabCompleteTextField.this, Side.BOTTOM, 0, 0);
                }
            } else {
                dropDownMenu.hide();
            }
        }
    }

    /**
     * Generates options for Auto-Completion from
     * the names and tags of persons from a list.
     * @param persons a list of person
     */
    public void generateOptions(List<ReadOnlyPerson> persons) {
        for (ReadOnlyPerson person : persons) {
            options.addAll(Arrays.asList(person.getName().fullName.toLowerCase().split("\\s+")));
            person.getTags().stream().map(tag -> tag.tagName.toLowerCase()).forEachOrdered(options::add);
        }
    }

    /**
     * Updates options for Auto-Completion from
     * the arguments of an inputted command.
     * @param command an inputted command
     */
    public void updateOptions(String command) {
        String[] args = command.toLowerCase().split("(\\s+|[a-z]/)");
        for (int i = 1; i < args.length; i++) {
            if (args[i].matches("[a-z]+")) {
                options.add(args[i]);
            }
        }
    }

    /**
     * Splits the command in the command box into
     * two parts by the last occurrence of space.
     * Store them into prefixWords and lastWord respectively.
     */
    private void splitCommandWords() {
        String text = getText();
        int lastSpace = text.lastIndexOf(" ");
        int lastSlash = text.lastIndexOf("/");
        int splitingPosition = Integer.max(lastSlash, lastSpace);
        prefixWords = text.substring(0, splitingPosition + 1);
        lastWord = text.substring(splitingPosition + 1).toLowerCase();
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

    public ContextMenu getDropDownMenu() {
        return dropDownMenu;
    }
}
