# ritchielq-reuse
###### \java\seedu\address\commons\events\ui\BrowserPanelChangeActiveTabEvent.java
``` java
/**
 * Indicates a request to change active tab for browser panel
 */
public class BrowserPanelChangeActiveTabEvent extends BaseEvent {

    public final int targetTab;

    public BrowserPanelChangeActiveTabEvent(int targetTab) {
        this.targetTab = targetTab;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\DeselectEvent.java
``` java
/**
 * Indicates a request to reselect person
 */
public class DeselectEvent extends BaseEvent {


    public DeselectEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\PersonPanelDeselectionEvent.java
``` java
/**
 * Represents a Deselection in the Person List Panel
 */
public class PersonPanelDeselectionEvent extends BaseEvent {

    public PersonPanelDeselectionEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadNoTimetablePage() {
        URL noTimetablePage = MainApp.class.getResource(FXML_FILE_FOLDER + NO_TIMETABLE);
        loadPage(noTimetablePage.toExternalForm());
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelDeselectionEvent(PersonPanelDeselectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
    }
}
```
