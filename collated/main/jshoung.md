# jshoung
###### \java\seedu\address\commons\events\ui\ClearPersonSelectionEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to clear the person selection.
 */
public class ClearPersonSelectionEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\GetModuleRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the module page.
 */
public class GetModuleRequestEvent extends BaseEvent {
    public final String module;

    public GetModuleRequestEvent(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowBusRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the bus page.
 */
public class ShowBusRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowMapRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the map page.

 */
public class ShowMapRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowSummaryRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the command summary.
 */
public class ShowSummaryRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\VenueRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the venue page.
 */
public class VenueRequestEvent extends BaseEvent {
    public final String venue;

    public VenueRequestEvent(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\BusCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBusRequestEvent;

/**
 * Opens bus route map for NUS
 */
public class BusCommand extends Command {

    public static final String COMMAND_WORD = "bus";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows bus routes for NUS.\n"
            + "Example: " + COMMAND_WORD;

    static final String SHOWING_BUS_MESSAGE = "Opened bus window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowBusRequestEvent());
        return new CommandResult(SHOWING_BUS_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\GetModuleCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearPersonSelectionEvent;
import seedu.address.commons.events.ui.GetModuleRequestEvent;

/**
 *
 * Opens NUSMods webpage to get module information.
 *
 */
public class GetModuleCommand extends Command {
    public static final String COMMAND_WORD = "getmodule";
    public static final String COMMAND_ALIAS = "gm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows module information.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CS2101" + "\n"
            + "Example: " + COMMAND_ALIAS + " CS2103T";

    public static final String SHOWING_MODULE_MESSAGE = "Loaded module information for: ";

    private final String module;

    public GetModuleCommand(String module) {
        this.module = module;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new GetModuleRequestEvent(module));
        EventsCenter.getInstance().post(new ClearPersonSelectionEvent());
        return new CommandResult(SHOWING_MODULE_MESSAGE + module);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetModuleCommand // instanceof handles nulls
                && this.module.equals(((GetModuleCommand) other).module)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\MapCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMapRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the NUS map.\n"
            + "Example: " + COMMAND_WORD;

    static final String SHOWING_MAP_MESSAGE = "Opened map window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMapRequestEvent());
        return new CommandResult(SHOWING_MAP_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\SummaryCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearPersonSelectionEvent;
import seedu.address.commons.events.ui.ShowSummaryRequestEvent;

/**
 * Opens ContactHub command summary
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String COMMAND_ALIAS = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows ContactHub command summary.\n"
            + "Example: " + COMMAND_WORD;

    static final String SHOWING_SUMMARY_MESSAGE = "Opened command summary.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ClearPersonSelectionEvent());
        EventsCenter.getInstance().post(new ShowSummaryRequestEvent());
        return new CommandResult(SHOWING_SUMMARY_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\VenueCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearPersonSelectionEvent;
import seedu.address.commons.events.ui.VenueRequestEvent;

/**
 *
 * Opens NUSMods webpage to get venue information.
 *
 */
public class VenueCommand extends Command {
    public static final String COMMAND_WORD = "venue";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows venue occupancy information.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " COM1-0114" + "\n"
            + "Example: " + COMMAND_ALIAS + " COM1-0114";

    public static final String SHOWING_VENUE_MESSAGE = "Loaded venue information for: ";

    private final String venue;

    public VenueCommand(String venue) {
        this.venue = venue;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new VenueRequestEvent(venue));
        EventsCenter.getInstance().post(new ClearPersonSelectionEvent());
        return new CommandResult(SHOWING_VENUE_MESSAGE + venue);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VenueCommand // instanceof handles nulls
                && this.venue.equals(((VenueCommand) other).venue)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        switch (commandWord.toLowerCase()) {
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case HelpCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case BusCommand.COMMAND_WORD:
        case BusCommand.COMMAND_ALIAS:
            return new BusCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case MapCommand.COMMAND_WORD:
        case MapCommand.COMMAND_ALIAS:
            return new MapCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case GetModuleCommand.COMMAND_WORD:
        case GetModuleCommand.COMMAND_ALIAS:
            return new GetModuleCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SummaryCommand.COMMAND_WORD:
        case SummaryCommand.COMMAND_ALIAS:
            return new SummaryCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case VenueCommand.COMMAND_WORD:
        case VenueCommand.COMMAND_ALIAS:
            return new VenueCommandParser().parse(arguments);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteModule(Module module) throws DuplicatePersonException, PersonNotFoundException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Person newPerson = new Person(oldPerson);
            Set<Module> newModules = newPerson.getModules();
            newModules.remove(module);
            newPerson.setModules(newModules);

            addressBook.updatePerson(oldPerson, newPerson);
        }
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    public String getBrowserAddress() {
        return value;
    }

}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
    public String getBrowserValue() {
        return browserValue;
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
    public String getBrowserName() {
        return browserName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns a String consisting of all the person's phone numbers separated by commas, for display in the browser.
     */
    @Override
    public String getBrowserPhones() {
        Set<Phone> phones = getPhones();
        String allPhones = "";

        for (Phone phone : phones) {
            allPhones = allPhones.concat(phone.toString());
            allPhones = allPhones.concat(", ");
        }
        if (allPhones.length() == 0) {
            return "";
        }
        allPhones = allPhones.substring(0, allPhones.length() - 2);

        return allPhones;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns a String consisting of all the person's emails separated by commas, for display in the browser.
     */
    @Override
    public String getBrowserEmails() {
        Set<Email> emails = getEmails();
        String allEmails = "";

        for (Email email : emails) {
            allEmails = allEmails.concat(email.toString());
            allEmails = allEmails.concat(", ");
        }
        if (allEmails.length() == 0) {
            return "";
        }
        allEmails = allEmails.substring(0, allEmails.length() - 2);

        return allEmails;
    }

    public ObjectProperty<EmailList> emailProperty() {
        return emails;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns a String consisting of all the person's modules separated by commas, for display in the browser.
     */
    @Override
    public String getBrowserModules() {
        Set<Module> modules = getModules();
        String allModules = "";

        for (Module module : modules) {
            allModules = allModules.concat(module.toString());
            allModules = allModules.concat(", ");
        }
        if (allModules.length() == 0) {
            return "";
        }
        allModules = allModules.substring(0, allModules.length() - 2);

        return allModules;
    }

    public ObjectProperty<UniqueModuleList> moduleProperty() {
        return modules;
    }

    /**
     * Replaces this person's modules with the modules in the argument module set.
     */
    public void setModules(Set<Module> replacement) {
        modules.set(new UniqueModuleList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phones, birthday, emails, address, modules);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### \java\seedu\address\model\person\Photo.java
``` java
    public String getBrowserPhoto() {
        return value;
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleShowSummaryRequestEvent (ShowSummaryRequestEvent event) {
        loadDefaultPage();
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
        String address = p.getAddress().getBrowserAddress();
        String birthday = p.getBirthday().getBrowserValue();
        String name = p.getName().getBrowserName();
        String photo = p.getPhoto().getBrowserPhoto();
        String emails = p.getBrowserEmails();
        String phones = p.getBrowserPhones();
        String modules = p.getBrowserModules();
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
                panel.executeScript("document.setBirthday(\"" + birthday + "\")");
                panel.executeScript("document.setName(\"" + name + "\")");
                panel.executeScript("document.setAddress(\"" + address + "\")");
                panel.executeScript("document.setPhoto(\"" + photo + "\")");
                panel.executeScript("document.setEmail(\"" + emails + "\")");
                panel.executeScript("document.setPhone(\"" + phones + "\")");
                panel.executeScript("document.setModule(\"" + modules + "\")");
            }
        });
```
###### \java\seedu\address\ui\BusWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for bus page
 */
public class BusWindow extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(BusWindow.class);
    private static final String ICON = "/images/bus_icon.png";
    private static final String FXML = "BusWindow.fxml";
    private static final String TITLE = "Bus Routes";

    private final Stage dialogStage;

    public BusWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false);
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the bus window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing bus routes for NUS.");
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the bus window.
     */
    @FXML
    public void handleBus() {
        BusWindow busWindow = new BusWindow();
        busWindow.show();
    }

    /**
     * Opens the map window.
     */
    @FXML
    public void handleMap() {
        MapWindow mapWindow = new MapWindow();
        mapWindow.show();
    }

    /**
     * Opens the module info in browser.
     */
    @FXML
    public void handleModule(String module) {
        browserPanel.loadPage("https://nusmods.com/modules/" + module);
    }

    /**
     * Opens the venue info in browser.
     */
    @FXML
    public void handleVenue(String venue) {
        browserPanel.loadPage("https://nusmods.com/venues/" + venue);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowBusEvent(ShowBusRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleBus();
    }

    @Subscribe
    private void handleShowMapEvent(ShowMapRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleMap();
    }

    @Subscribe
    private void handleShowModuleEvent(GetModuleRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleModule(event.module);
    }

    @Subscribe
    private void handleShowVenueEvent(VenueRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleVenue(event.venue);
    }

```
###### \java\seedu\address\ui\MapWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for map page
 */
public class MapWindow extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(MapWindow.class);
    private static final String ICON = "/images/map_icon.png";
    private static final String FXML = "MapWindow.fxml";
    private static final String TITLE = "NUS Map";

    private final Stage dialogStage;

    MapWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the map window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing map for NUS.");
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Scrolls to the top and clears person selection.
     */
    private void scrollToAndClear() {
        Platform.runLater(() -> {
            personListView.scrollTo(1);
            personListView.getSelectionModel().clearSelection();
        });
    }

    @Subscribe
    private void handleClearPersonListRequestEvent(ClearPersonSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToAndClear();
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));

        if (event.isError) {
            setStyleToIndicateCommandFailure();
        } else {
            setStyleToDefault();
        }
    }

    /**
     * Sets the (@code ResultDisplay) to use the default style.
     */
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the (@code ResultDisplay) to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) total");
    }
```
###### \resources\view\BusWindow.fxml
``` fxml

<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<ScrollPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.111">
   <content>
      <ImageView fitHeight="1000.0" fitWidth="1000.0" pickOnBounds="true" preserveRatio="true">
         <image>
            <Image url="@../docs/images/bus.jpg" />
         </image>
      </ImageView>
   </content>
</ScrollPane>
```
###### \resources\view\DarkThemeCommands.css
``` css
.background {
    -fx-background-color: derive(#1d1d1d, 20%);
    background-color: #383838; /* Used in the default.html file */
    font-family: "Segoe UI";
    color: white;
}

img {
    user-drag: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;ser-select: none;
    -moz-user-select: none;
    -webkit-user-drag: none;
    -webkit-user-select: none;
    -ms-user-select: none;
}

.command {
    font-weight: bold;
}

h1 {
    text-align: center;
    font-size: 32pt;
    font-family: "Segoe UI";
    color: white;
}

li {
    margin: 10px;
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
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
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
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
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
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#1d1d1d, 20%);
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
    -fx-background-color: #3c3e3f;
}

.list-cell:filled:odd {
    -fx-background-color: #515658;
}

.list-cell:filled:selected {
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: white;
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
    -fx-background-color: derive(#1d1d1d, 20%);
}

.pane-with-border {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: derive(#1d1d1d, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
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
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}

#mods {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#mods .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### \resources\view\MapWindow.fxml
``` fxml

<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<ScrollPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity"
            xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.111">
   <ImageView fitHeight="2600.0" fitWidth="2600.0" pickOnBounds="true" preserveRatio="true">
      <image>
         <Image url="@../docs/images/map.jpg"/>
      </image>
   </ImageView>
</ScrollPane>
```
###### \resources\view\PersonListPanel.fxml
``` fxml
  <ListView fx:id="personListView" style="-fx-background-color: #383838;" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
  <StatusBar styleClass="anchor-pane" fx:id="totalPersons" GridPane.columnIndex="1" />
```
