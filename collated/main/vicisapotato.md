# vicisapotato
###### \java\seedu\address\commons\events\ui\JumpToTabRequestEvent.java
``` java
public class JumpToTabRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTabRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowParcelListEvent.java
``` java
public class ShowParcelListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
        EventsCenter.getInstance().post(new ShowParcelListEvent());
```
###### \java\seedu\address\logic\commands\TabCommand.java
``` java
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": changed the tab displayed in the parcel list panel "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SELECT_TAB_SUCCESS = "Selected Tab: %1$s";
    public static final String MESSAGE_INVALID_TAB_INDEX = "Invalid Tab Number";

    public static final int NUM_TAB = 2;

    private final Index targetIndex;

    /**
     * @param targetIndex of the tab in the TabPane to switch to
     */
    public TabCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (targetIndex.getZeroBased() >= NUM_TAB) {
            throw new CommandException(MESSAGE_INVALID_TAB_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToTabRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TAB_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TabCommand // instanceof handles nulls
                && this.targetIndex.equals(((TabCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\TabCommandParser.java
``` java
public class TabCommandParser implements Parser<TabCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TabCommand
     * and returns an TabCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TabCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TabCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadParcelLocationPage(ReadOnlyParcel parcel) {
        loadPage(GOOGLE_MAP_URL_PREFIX + getMapQueryStringFromPostalString(parcel.getAddress().postalCode.toString()));
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleParcelPanelSelectionChangedEvent(ParcelPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadParcelLocationPage(event.getNewSelection().parcel);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML @Subscribe
    private void handleMinimizeParcelListEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        splitPanePlaceholder.setDividerPositions(0.6);
    }

    @FXML @Subscribe
    private void handleParcelPanelSelectionChangedEvent(ParcelPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        splitPanePlaceholder.setDividerPositions(0.6);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML @Subscribe
    private void handleShowParcelListEvent(ShowParcelListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        splitPanePlaceholder.setDividerPositions(0.0);
    }
```
###### \java\seedu\address\ui\ParcelCard.java
``` java
    /**
     * Initializes tags and sets their style based on their tag label
     */
    private void initTags(ReadOnlyParcel parcel) {
        parcel.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.toString());
            tagLabel.setStyle("-fx-background-color: " + setColorForTag(tag.toString()));
            tags.getChildren().add(tagLabel);
        });
    }

```
###### \java\seedu\address\ui\ParcelListPanel.java
``` java
    @FXML @Subscribe
    private void handleJumpToTabEvent(JumpToTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        tabPanePlaceholder.getSelectionModel().select(event.targetIndex);
    }
```
###### \java\seedu\address\ui\PopupOverdueParcelsWindow.java
``` java
public class PopupOverdueParcelsWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PopupOverdueParcelsWindow.class);
    private static final String FXML = "PopupOverdueParcelsWindow.fxml";
    private static final String TITLE = "Important";
    private static final String CONTENT_TEXT = "You have parcels whose status are overdue\n"
                                                 + "Number of overdue parcels: ";

    private final Stage dialogStage;

    @FXML
    private Label contentPlaceholder;

    public PopupOverdueParcelsWindow (ObservableList<ReadOnlyParcel> uncompletedParcels) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(false);
        dialogStage.setAlwaysOnTop(true);
        fillDialogPane(getNumOverdueParcels(uncompletedParcels));
    }

    private void fillDialogPane (int numOverdueParcels) {
        contentPlaceholder.setText(CONTENT_TEXT + numOverdueParcels);
    }

    private int getNumOverdueParcels (ObservableList<ReadOnlyParcel> uncompletedParcels) {
        int numOverdueParcels = 0;

        for (int i = 0; i < uncompletedParcels.size(); i++) {
            if (uncompletedParcels.get(i).getStatus().equals(Status.OVERDUE)) {
                numOverdueParcels++;
            }
        }

        return numOverdueParcels;
    }

    /**
     * Shows the help window.
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
    public void show () {
        logger.fine("Showing popup window for overdue.");
        dialogStage.show();
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
            if (overDueParcels(logic.getUncompletedParcelList())) {
                PopupOverdueParcelsWindow popupOverdueParcelsWindow =
                        new PopupOverdueParcelsWindow(logic.getUncompletedParcelList());
                popupOverdueParcelsWindow.show();
            }
```
###### \java\seedu\address\ui\UiManager.java
``` java
    /**
     * Checks for presence of overdue parcels in parcel list
     */
    public boolean overDueParcels (ObservableList<ReadOnlyParcel> uncompletedParcelList) {

        // if there are overdue parcels
        for (int i = 0; i < uncompletedParcelList.size(); i++) {
            if (uncompletedParcelList.get(i).getStatus().equals(Status.OVERDUE)) {
                return true;
            }
        }

        return false;
    }
```
###### \resources\view\DarkTheme.css
``` css
.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane
{
    -fx-tab-min-width:90px;
}

.tab{
    -fx-background-insets: 0 1 0 1,0,0;
}
.tab-pane .tab
{
    -fx-background-color: #3c3c3c;

}

.tab-pane .tab:selected
{
    -fx-background-color: #3c3c3c;
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #d4d4d4;
    -fx-font-size: 12px;
    -fx-font-weight: bold;
}

.tab:selected .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #aab981;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
  <SplitPane fx:id="splitPanePlaceholder" dividerPositions="0.5" orientation="VERTICAL">
    <items>

      <StackPane fx:id="browserPlaceholder">
        <padding>
          <Insets bottom="10" left="10" right="10" top="10" />
        </padding>
      </StackPane>
     <StackPane fx:id="parcelListPanelPlaceholder" />
    </items>
  </SplitPane>
```
###### \resources\view\ParcelListCard.fxml
``` fxml
      <GridPane hgap="15.0" prefWidth="1367.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="111.66668701171875" minWidth="10.0" prefWidth="37.33333206176758" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.33331298828125" minWidth="10.0" prefWidth="128.66666793823242" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="213.0" minWidth="10.0" prefWidth="125.5" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="239.5" minWidth="10.0" prefWidth="129.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="458.0" minWidth="10.0" prefWidth="342.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="400.0" minWidth="10.0" prefWidth="334.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.0" minWidth="10.0" prefWidth="111.0" />
          <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="194.0" minWidth="10.0" prefWidth="109.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="386.0" minWidth="10.0" prefWidth="377.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
```
###### \resources\view\ParcelListPanel.fxml
``` fxml
    <TabPane fx:id="tabPanePlaceholder" styleClass="tab-pane" tabClosingPolicy="UNAVAILABLE">
     <tabs>
       <Tab fx:id="undeliveredParcelsTab" styleClass="tab-header-area" text="All Parcels">
            <content>
               <VBox>
                  <children>
                     <GridPane hgap="15.0">
                        <columnConstraints>
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="111.66668701171875" minWidth="10.0" prefWidth="37.33333206176758" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.33331298828125" minWidth="10.0" prefWidth="120.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="213.0" minWidth="10.0" prefWidth="114.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="239.5" minWidth="10.0" prefWidth="120.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="458.0" minWidth="10.0" prefWidth="325.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="400.0" minWidth="10.0" prefWidth="328.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.0" minWidth="10.0" prefWidth="131.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="194.0" minWidth="10.0" prefWidth="72.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="447.0" minWidth="10.0" prefWidth="447.0" />
                        </columnConstraints>
                        <rowConstraints>
                           <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        </rowConstraints>
                        <children>
                           <Label styleClass="cell_big_label" text="No." />
                           <Label styleClass="cell_big_label" text="Name" GridPane.columnIndex="1" />
                           <Label styleClass="cell_big_label" text="Tracking No." GridPane.columnIndex="2" />
                           <Label styleClass="cell_big_label" text="Phone No." GridPane.columnIndex="3" />
                           <Label styleClass="cell_big_label" text="Address" GridPane.columnIndex="4" />
                           <Label styleClass="cell_big_label" text="Email" GridPane.columnIndex="5" />
                           <Label styleClass="cell_big_label" text="Status" GridPane.columnIndex="7" />
                           <Label styleClass="cell_big_label" text="Tags" GridPane.columnIndex="8" />
                           <Label styleClass="cell_big_label" text="Due Date" GridPane.columnIndex="6" />
                        </children>
                        <padding>
                           <Insets bottom="5.0" left="5.0" top="5.0" />
                        </padding>
                     </GridPane>
                    <ListView fx:id="allUncompletedParcelListView" prefHeight="972.0" prefWidth="1806.0" style="-fx-background-color: #383838;" />
                  </children>
               </VBox>
            </content>
         </Tab>
       <Tab fx:id="deliveredParcelsTab" styleClass="tab-header-area" text="Completed Parcels">
            <content>
               <VBox>
                  <children>
                     <GridPane hgap="15.0">
                        <columnConstraints>
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="111.66668701171875" minWidth="10.0" prefWidth="37.33333206176758" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.33331298828125" minWidth="10.0" prefWidth="120.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="213.0" minWidth="10.0" prefWidth="114.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="239.5" minWidth="10.0" prefWidth="120.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="458.0" minWidth="10.0" prefWidth="325.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="400.0" minWidth="10.0" prefWidth="328.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.0" minWidth="10.0" prefWidth="123.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="194.0" minWidth="10.0" prefWidth="80.0" />
                           <ColumnConstraints hgrow="SOMETIMES" maxWidth="447.0" minWidth="10.0" prefWidth="447.0" />
                        </columnConstraints>
                        <rowConstraints>
                           <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        </rowConstraints>
                        <children>
                           <Label styleClass="cell_big_label" text="No." />
                           <Label styleClass="cell_big_label" text="Name" GridPane.columnIndex="1" />
                           <Label styleClass="cell_big_label" text="Tracking No." GridPane.columnIndex="2" />
                           <Label styleClass="cell_big_label" text="Phone No." GridPane.columnIndex="3" />
                           <Label styleClass="cell_big_label" text="Address" GridPane.columnIndex="4" />
                           <Label styleClass="cell_big_label" text="Email" GridPane.columnIndex="5" />
                           <Label styleClass="cell_big_label" text="Status" GridPane.columnIndex="7" />
                           <Label styleClass="cell_big_label" text="Tags" GridPane.columnIndex="8" />
                           <Label styleClass="cell_big_label" text="Due Date" GridPane.columnIndex="6" />
                        </children>
                        <padding>
                           <Insets bottom="5.0" left="5.0" top="5.0" />
                        </padding>
                     </GridPane>
                     <ListView fx:id="allCompletedParcelListView" prefHeight="1156.0" prefWidth="1806.0" style="-fx-background-color: #383838;" />
                  </children>
               </VBox>
            </content>
         </Tab>
     </tabs>
   </TabPane>
```
###### \resources\view\PopupOverdueParcelsWindow.fxml
``` fxml
<StackPane styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <VBox>
         <children>
            <Label alignment="CENTER" prefHeight="32.0" prefWidth="314.0" styleClass="cell_big_label" text="Overdue Parcels">
               <font>
                  <Font size="22.0" />
               </font>
               <padding>
                  <Insets bottom="10.0" />
               </padding>
            </Label>
            <Label fx:id="contentPlaceholder" alignment="TOP_LEFT" prefHeight="187.0" prefWidth="312.0" styleClass="cell_small_label" text="\$Content" wrapText="true" />
         </children>
      </VBox>
   </children>
</StackPane>
```
