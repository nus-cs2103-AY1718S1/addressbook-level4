# zhoukai07
###### /java/seedu/address/commons/events/ui/ChangeThemeRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for theme change
 */
public class ChangeThemeRequestEvent extends BaseEvent {
    public final String themeUrl;
    public ChangeThemeRequestEvent (String themeUrl) {
        this.themeUrl = themeUrl;
    }
    public String getThemeUrl() {
        return this.themeUrl;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "changeToTheme";
    public static final String COMMAND_ALIAS = "ct";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change theme by the index number of the 4 inbuilt themes.\n"
            + "Parameters: INDEX (1, 2, 3 or 4)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {index}";

    public static final String MESSAGE_SELECT_THEME_SUCCESS = "Selected Theme: %1$s";

    private final Integer targetIndex;
    public ChangeThemeCommand(Integer targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        String[] themeList = new String[]{"/view/DarkTheme.css", "/view/DarkTheme2.css", "/view/LightTheme.css",
            "/view/LightTheme2.css"};
        if (targetIndex > themeList.length) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }
        String themeUrl = getClass().getResource(themeList[targetIndex - 1]).toExternalForm();
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(themeUrl));
        return new CommandResult(String.format(MESSAGE_SELECT_THEME_SUCCESS, targetIndex));
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
            + "[" + PREFIX_ADD_TAG + "TAG]..."
            + "[" + PREFIX_REM_TAG + "TAG]..."
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        private boolean clearTags = false;
        private Set<Tag> toAdd;
        private Set<Tag> toRemove;
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setToAdd(Set<Tag> toAdd) {
            this.toAdd = toAdd;
        }
        public void setToRemove(Set<Tag> toRemove) {
            this.toRemove = toRemove;
        }
        public void setClearTags(boolean clearTags) {
            this.clearTags = clearTags;
        }
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case ChangeThemeCommand.COMMAND_WORD:
        case ChangeThemeCommand.COMMAND_ALIAS:
            return new ChangeThemeCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/ArgumentMultimap.java
``` java
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }
```
###### /java/seedu/address/logic/parser/ChangeThemeCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTheme(args);
            return new ChangeThemeCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ADD_TAG = new Prefix("+t/");
    public static final Prefix PREFIX_CLEAR_TAG = new Prefix("ClearTag");
    public static final Prefix PREFIX_REM_TAG = new Prefix("-t/");
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> emails} into a {@code ArrayList<Emails>} if {@code emails} is non-empty.
     * If {@code emails} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Email>} containing zero emails.
     */
    private Optional<ArrayList<Email>> parseEmailsForEdit(Collection<String> emails) throws IllegalValueException {
        assert emails != null;

        if (emails.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> emailSet = emails.size() == 1 && emails.contains("") ? Collections.emptySet() :  emails;
        Collection<String> emailSetToParse = new ArrayList<>();
        for (String email : emailSet) {
            if (!emailSetToParse.contains(email)) {
                emailSetToParse.add(email);
            }
        }
        return Optional.of(ParserUtil.parseEmail(emailSetToParse));
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    private ObjectProperty<ArrayList<Email>> emails;
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setEmail(ArrayList<Email> email) {
        this.emails.set(requireNonNull(email));
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        Scene scene = new Scene(getRoot());
        String original = getClass().getResource("/view/DarkTheme.css").toExternalForm();
        scene.getStylesheets().add(original);
        this.scene2 = scene;
        primaryStage.setScene(scene);
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Allows for theme changes
     */
    @FXML
    private void handleDarkTheme() {
        String themeUrl = getClass().getResource("/view/DarkTheme.css").toExternalForm();
        setTheme(themeUrl);
    }
    @FXML
    private void handleDarkTheme2() {
        String themeUrl = getClass().getResource("/view/DarkTheme2.css").toExternalForm();
        setTheme(themeUrl);
    }
    @FXML
    private void handleLightTheme() {
        String themeUrl = getClass().getResource("/view/LightTheme.css").toExternalForm();
        setTheme(themeUrl);
    }
    @FXML
    private void handleLightTheme2() {
        String themeUrl = getClass().getResource("/view/LightTheme2.css").toExternalForm();
        setTheme(themeUrl);
    }
    public void setTheme(String themeUrl) {
        scene2.getStylesheets().clear();
        scene2.getStylesheets().add(themeUrl);
        primaryStage.setScene(scene2);
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setTheme(event.getThemeUrl());
    }

```
###### /resources/view/LightTheme.css
``` css
* {
    -fx-base-background-color-0: #EFF1F3;
    -fx-base-background-color-1: #DBD3D8;
    -fx-button-pressed-color: #7a7a7a;
    -fx-button-hovor-color: #999999;
    -fx-base-text-fill-color: #14141f;
    -fx-base-text-fill-color-alt: white;
    -fx-base-text-fill-color-labels: white;
    -fx-base-text-fill-color-weblink-labels: black;
    -fx-label-text-fill-color: #b3b3b3;
    -fx-list-cell-even: #D8B4A0;
    -fx-list-cell-odd: #DBD3D8;
    -fx-list-cell-selected: #C4C6C7;
    -fx-list-cell-selected-border: #C4C6C7;
    -fx-list-cell-empty: #f2f2f2;
}

.background {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
    background-color: -fx-base-background-color-1; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: -fx-base-text-fill-color;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: -fx-base-text-fill-color;
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
    -fx-base: -fx-base-background-color-0;
    -fx-control-inner-background: -fx-base-background-color-0;
    -fx-background-color: -fx-base-background-color-0;
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
    -fx-border-color: transparent transparent derive(-fx-base, 80%) transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: -fx-base-text-fill-color;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: -fx-list-cell-even;
}

.list-cell:filled:odd {
    -fx-background-color: -fx-list-cell-odd;
}

.list-cell:filled:selected {
    -fx-background-color: -fx-list-cell-selected;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: -fx-list-cell-selected-border;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: -fx-base-text-fill-color;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: -fx-label-text-fill-color;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: -fx-label-text-fill-color;
}

.anchor-pane {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
}

.pane-with-border {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
    -fx-border-color: derive(-fx-base-background-color-0, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
    -fx-text-fill: -fx-base-text-fill-color-alt;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: -fx-base-text-fill-color;
}

.result-display .label {
    -fx-text-fill: -fx-base-text-fill-color-alt !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: -fx-base-text-fill-color;
}

.status-bar-with-border {
    -fx-background-color: derive(-fx-base-background-color-0, 30%);
    -fx-border-color: derive(-fx-base-background-color-0, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: -fx-base-text-fill-color;
}

.grid-pane {
    -fx-background-color: derive(-fx-base-background-color-0, 30%);
    -fx-border-color: derive(-fx-base-background-color-0, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(-fx-base-background-color-0, 30%);
}

.context-menu {
    -fx-background-color: derive(-fx-base-background-color-0, 50%);
}

.context-menu .label {
    -fx-text-fill: -fx-base-text-fill-color;
}

.menu-bar {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: -fx-base-text-fill-color;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: -fx-base-text-fill-color-alt;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 3 22 3 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 1;
    -fx-background-radius: 0;
    -fx-background-color: -fx-base-background-color-1;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 8pt;
    -fx-text-fill: -fx-base-text-fill-color;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: -fx-button-hovor-color;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: -fx-button-pressed-color;
    -fx-text-fill: -fx-base-background-color-0;
}

.button:focused {
    -fx-border-color: -fx-base-text-fill-color, -fx-base-text-fill-color;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: -fx-base-background-color-0;
    -fx-text-fill: -fx-base-text-fill-color;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: -fx-base-text-fill-color-labels;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: -fx-base-background-color-0;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: -fx-base-background-color-0;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: -fx-base-text-fill-color;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(-fx-base-background-color-0, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: -fx-base-text-fill-color;
}

.scroll-bar {
    -fx-background-color: derive(-fx-base-background-color-0, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(-fx-base-background-color-0, 50%);
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

.auto-complete-popup {
    -fx-translate-y: -100;
    -fx-background-color: transparent;
    -fx-background-insets: 0.0px;
    -fx-background-radius: 0.0px;
    -fx-border-width: 0.0px;
    -fx-border-color: transparent;
    -fx-border-radius: 0.0px;
}

.auto-complete-popup > .list-view {
    -fx-background-color: transparent;
    -fx-background-insets: 0.0px;
    -fx-background-radius: 0.0px;
    -fx-border-color: transparent;
    -fx-border-width: 0.0px;
    -fx-border-radius: 0.0px;
    -fx-padding: 0.0px;
}

.auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell {
    -fx-background-color: transparent;
    -fx-background-radius: 0.0px;
    -fx-alignment: CENTER-LEFT;
    -fx-text-fill: #65728B;
    -fx-font-size: 1.0em;
    -fx-font-weight: normal;
    -fx-padding: 8.0px;
    -fx-cursor: hand;
}

.auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell:filled {
}

.auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell:filled:hover,
.auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell:filled:focused {
    -fx-background-color: #0078ce;
    -fx-text-fill: black;
}

.auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell:filled:selected {
    -fx-background-color: #0078ce;
    -fx-text-fill: black;
    -fx-cursor: default;
}

.auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell:filled:selected:hover {
    -fx-background-color: #0078ce;
    -fx-cursor: default;
}

.auto-complete-popup > .list-view > .placeholder > .label {
    -fx-text-fill: #65728B;
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
    -fx-background-color: transparent -fx-base-background-color-1 transparent -fx-base-background-color-1;
    -fx-background-insets: 0;
    -fx-border-color: -fx-base-background-color-1 -fx-base-background-color-1 #ffffff -fx-base-background-color-1;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: -fx-base-text-fill-color;
}

#detailedCardPane .label {
    -fx-text-fill: -fx-base-text-fill-color;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#phoneicon {
    -fx-image: url("../images/ic_phone_black_24dp.png");
}

#addressicon {
    -fx-image: url("../images/ic_home_black_24dp.png");
}

#emailicon {
    -fx-image: url("../images/ic_email_black_24dp.png");
}

#resultDisplay .content {
    -fx-background-color: transparent, -fx-base-background-color-1, transparent, -fx-base-background-color-1;
    -fx-background-radius: 0;
}

#rightViewSplitPane .split-pane-divider {
    -fx-padding: 0 1 0 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: -fx-base-text-fill-color-labels;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#webLinks {
    -fx-hgap: 7;
    -fx-vgap: 8;
}

#webLinks .label {
    -fx-text-fill: -fx-base-text-fill-color-weblink-labels;
    -fx-background-color: transparent;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 4;
    -fx-border-width: 3;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### /resources/view/MainWindow.fxml
``` fxml
        <Menu mnemonicParsing="false" text="Theme">
            <MenuItem mnemonicParsing="false" onAction="#handleDarkTheme" text="Dark Theme"/>
            <MenuItem mnemonicParsing="false" onAction="#handleDarkTheme2" text="Dark Theme 2"/>
            <MenuItem mnemonicParsing="false" onAction="#handleLightTheme" text="Light Theme"/>
            <MenuItem mnemonicParsing="false" onAction="#handleLightTheme2" text="Light Theme 2"/>
        </Menu>
```
