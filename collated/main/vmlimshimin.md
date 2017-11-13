# vmlimshimin
###### /java/seedu/address/commons/events/ui/ThemeRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the theme.
 */
public class ThemeRequestEvent extends BaseEvent {

    public final String theme;

    public ThemeRequestEvent(String theme) {
        this.theme = theme;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
            queue.offer(personToDelete);
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue, String theme) {
        this.model = model;
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteMultipleCommand.java
``` java
                queue.offer(personToDelete);
```
###### /java/seedu/address/logic/commands/DeleteMultipleCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue, String theme) {
        this.model = model;
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/commands/RecentlyDeletedCommand.java
``` java
package seedu.address.logic.commands;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Shows a list of recently deleted contacts and their details, up to the last 30 contacts deleted.
 */
public class RecentlyDeletedCommand extends Command {
    public static final String COMMAND_WORD = "recentlyDel";
    public static final String COMMAND_ALIAS = "recentD";
    public static final String MESSAGE_SUCCESS = "Listed all recently deleted:\n%1$s";
    public static final String MESSAGE_NO_RECENTLY_DELETED = "You have not yet deleted any contacts.";

    @Override
    public CommandResult execute() {
        LinkedList<ReadOnlyPerson> previouslyDeleted;
        previouslyDeleted = queue.getQueue();

        if (previouslyDeleted.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECENTLY_DELETED);
        }

        Collections.reverse(previouslyDeleted);
        LinkedList<String> deletedAsText = new LinkedList<>();
        Iterator list = previouslyDeleted.listIterator(0);
        //System.out.println(previouslyDeleted.size());
        while (list.hasNext()) {
            String personAsText = (list.next()).toString();
            deletedAsText.add(personAsText);
        }
        //System.out.println(deletedAsText.size());
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", deletedAsText)));
    }

    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack,
                        RecentlyDeletedQueue queue, String theme) {
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/commands/ThemeCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ThemeRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Selects a theme based on the index provided by the user, which can be referred from the themes list.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "ct";

    public static final String MESSAGE_SWITCH_THEME_SUCCESS = "Switched Theme";


    @Override
    public CommandResult execute() {

        String themeToChange = (theme == "DarkTheme.css") ? "LightTheme.css" : "DarkTheme.css";

        EventsCenter.getInstance().post(new ThemeRequestEvent(themeToChange));

        theme = themeToChange;

        return new CommandResult(String.format(MESSAGE_SWITCH_THEME_SUCCESS, themeToChange));

    }


    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack,
                        RecentlyDeletedQueue queue, String theme) {
        this.theme = theme;
    }
}
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousQueue = new RecentlyDeletedQueue(queue.getQueue());
    }

```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        queue.setQueue(previousQueue.getQueue());
    }

```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue, String theme) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    private final RecentlyDeletedQueue queue;
    private final String theme;

```
###### /java/seedu/address/logic/LogicManager.java
``` java
        this.queue = new RecentlyDeletedQueue();
        this.theme = "DarkTheme.css";
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
            command.setData(model, history, undoRedoStack, queue, theme);
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case RecentlyDeletedCommand.COMMAND_WORD:
        case RecentlyDeletedCommand.COMMAND_ALIAS:
            return new RecentlyDeletedCommand();

        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommand();
```
###### /java/seedu/address/logic/RecentlyDeletedQueue.java
``` java
package seedu.address.logic;

import java.util.LinkedList;
import java.util.Queue;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Maintains the Recently Deleted Queue (the list of contacts that were recently deleted,
 * for up to 30 contacts).
 */
public class RecentlyDeletedQueue {
    private Queue<ReadOnlyPerson> recentlyDeletedQueue;
    private int count;

    public RecentlyDeletedQueue() {
        recentlyDeletedQueue = new LinkedList<>();
        count = 0;
    }

    public RecentlyDeletedQueue(LinkedList<ReadOnlyPerson> newQueue) {
        recentlyDeletedQueue = newQueue;
        count = newQueue.size();
    }

    /**
     * Offers the @param person in the queue when the @param person is deleted by
     * {@code DeleteCommand} or {@code DeleterMultipleCommand}.
     */
    public void offer(ReadOnlyPerson person) {
        if (count < 30) {
            recentlyDeletedQueue.offer(person);
            count++;
        } else {
            recentlyDeletedQueue.poll();
            recentlyDeletedQueue.offer(person);
        }
    }

    /**
     * Returns the list of people in the queue.
     */
    public LinkedList<ReadOnlyPerson> getQueue() {
        return new LinkedList<>(recentlyDeletedQueue);
    }

    public void setQueue(LinkedList<ReadOnlyPerson> newQueue) {
        recentlyDeletedQueue = newQueue;
    }

}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Initialises the themes in this {@code AddressBook}.
     */

    private void initialiseThemes() {
        themeList.add("DarkTheme.css");
        themeList.add("LightTheme.css");
    }

    public ArrayList<String> getThemesList() {
        return themeList;
    }

```
###### /java/seedu/address/storage/AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }

```
###### /java/seedu/address/storage/XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }

}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Selects the theme given by user input
     */
    public void handleSelectTheme(String theme) {
        if (getRoot().getStylesheets().size() > 1) {
            getRoot().getStylesheets().remove(CURRENT_THEME);
        }
        getRoot().getStylesheets().add("/view/" + theme);
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleSelectThemeEvent(ThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSelectTheme(event.theme);
    }
}
```
###### /resources/view/LightTheme.css
``` css
.background {
    -fx-background-color: derive(#FFD88B, 20%);
    background-color: #383838;
    background-image: url(../images/Yellow_background.jpg);
    background-size: 1000px;
}

.container {
    padding-top: 30px;
    padding-right: 100px;
    padding-bottom: 60px;
    padding-left: 100px;
}

.header {
  font-family: "fantasy";
  font-size: 45px;
  color: white;
}

.text {
    font-family: "Verdana";
    color: white;
  }

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #B88B30;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #FFD88B;
    -fx-control-inner-background: #FFD88B;
    -fx-background-color: #FFD88B;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#FFD88B, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#FFD88B, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #FFC95E;
}

.list-cell:filled:odd {
    -fx-background-color: #FFBB32;
}

.list-cell:filled:selected {
    -fx-background-color: #FFEAC0;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#FFD88B, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#FFD88B, 20%);
     -fx-border-color: derive(#FFD88B, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#FFD88B, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#FFD88B, 30%);
    -fx-border-color: derive(#FFD88B, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#FFD88B, 30%);
    -fx-border-color: derive(#FFD88B, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#FFD88B, 30%);
}

.context-menu {
    -fx-background-color: derive(#FFD88B, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#FFD88B, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #000000;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #FFD88B;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #000000;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #FFEAC0;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
  -fx-text-fill: #FFD88B;
}

.button:focused {
    -fx-border-color: black, black;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #FFD88B;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #000000;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #FFD88B;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #FFD88B;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#FFD88B, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#FFD88B, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#FFD88B, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #FFBB32 transparent #FFBB32;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #FFBB32, transparent, #FFBB32;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #757575;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
