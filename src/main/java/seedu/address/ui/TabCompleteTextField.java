package seedu.address.ui;


import java.util.Arrays;
import java.util.LinkedList;
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

public class TabCompleteTextField extends TextField {

    private final int MAX_ENTRIES = 5;
    private final SortedSet<String> options = new TreeSet<>();
    private final ContextMenu dropDownMenu = new ContextMenu();

    private String prefixWords;
    private String lastWord;


    public TabCompleteTextField() {
        super();
        textProperty().addListener((unused1, unused2, unused3) -> generateSuggestions());
        focusedProperty().addListener((unused1, unused2, unused3) -> dropDownMenu.hide());
    }

    private void generateSuggestions() {
        splitCommandWords();
        if (lastWord.length() == 0) {
            dropDownMenu.hide();
        } else {
            LinkedList<String> matchedWords = new LinkedList<>();
            matchedWords.addAll(options.subSet(lastWord, lastWord + Character.MAX_VALUE));
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

    public void generateOptions(List<ReadOnlyPerson> persons) {
        for (ReadOnlyPerson person : persons) {
            options.addAll(Arrays.asList(person.getName().fullName.split("\\s+")));
            person.getTags().stream().map(tag -> tag.tagName).forEachOrdered(options::add);
        }
    }


    private void splitCommandWords() {
        String text = getText();
        int lastSpace = text.lastIndexOf(" ");
        prefixWords = text.substring(0, lastSpace + 1);
        lastWord = text.substring(lastSpace + 1);
    }

    /**
     * Fill the dropDownMenu with the matched words up to MAX_ENTRIES.
     * @param matchedWords The list of matching words.
     */
    private void fillDropDown(List<String> matchedWords) {
        List menuItems = dropDownMenu.getItems();
        menuItems.clear();

        int numEntries = Math.min(matchedWords.size(), MAX_ENTRIES);
        for (int i = 0; i < numEntries; i++) {
            final String suggestion = prefixWords + matchedWords.get(i);
            MenuItem item = new CustomMenuItem(new Label(suggestion), true);
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
        dropDownMenu.hide();
    }
}
