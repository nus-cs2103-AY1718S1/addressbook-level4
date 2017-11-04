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
    private static String[] colors = { "#cc4f4f", "#57b233", "#2696b5", "#5045c6", "#7739ba", "#b534a1", "black" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
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

    /**
     * tags with value tagValue will be assigned a random color from a list colors field
     */
    private static String setColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
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
     <StackPane fx:id="parcelListPanelPlaceholder" prefHeight="134.0" prefWidth="688.0" />
    </items>
  </SplitPane>
```
###### \resources\view\ParcelListCard.fxml
``` fxml
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="111.66668701171875" minWidth="10.0" prefWidth="28.33331298828125" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="234.33331298828125" minWidth="10.0" prefWidth="147.33331298828125" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="227.0" minWidth="10.0" prefWidth="107.66668701171875" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="458.0" minWidth="10.0" prefWidth="290.6666259765625" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="347.3333740234375" minWidth="10.0" prefWidth="310.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="213.0" minWidth="10.0" prefWidth="140.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="171.66650390625" minWidth="10.0" prefWidth="95.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="133.0" minWidth="10.0" prefWidth="89.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="296.666748046875" minWidth="10.0" prefWidth="277.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
        <Label fx:id="id" styleClass="cell_big_label" text="\$id">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="name" styleClass="cell_big_label" text="\$first" GridPane.columnIndex="1" />
        <Label fx:id="status" styleClass="cell_small_label" text="\$status" GridPane.columnIndex="7" />
        <Label fx:id="phone" styleClass="cell_big_label" text="\$phone" GridPane.columnIndex="2" />
        <Label fx:id="address" styleClass="cell_big_label" text="\$address" GridPane.columnIndex="3" />
        <Label fx:id="email" styleClass="cell_big_label" text="\$email" GridPane.columnIndex="4" />
        <Label fx:id="trackingNumber" styleClass="cell_big_label" text="\$trackingNumber" GridPane.columnIndex="5" />
        <Label fx:id="deliveryDate" styleClass="cell_big_label" text="\$deliveryDate" GridPane.columnIndex="6" />
          <FlowPane fx:id="tags" alignment="CENTER_LEFT" GridPane.columnIndex="8" />
         </children>
      </GridPane>
```
###### \resources\view\ParcelListPanel.fxml
``` fxml
    <TabPane fx:id="tabPanePlaceholder" styleClass="tab-pane" tabClosingPolicy="UNAVAILABLE">
     <tabs>
       <Tab fx:id="undeliveredParcelsTab" text="All Parcels">
            <content>
              <ListView fx:id="allUncompletedParcelListView" style="-fx-background-color: #383838;" />
            </content>
         </Tab>
       <Tab fx:id="deliveredParcelsTab" styleClass="tab-header-area" text="Completed Parcels">
            <content>
               <ListView fx:id="allCompletedParcelListView" prefHeight="200.0" prefWidth="200.0" style="-fx-background-color: #383838;" />
            </content>
         </Tab>
     </tabs>
   </TabPane>
```
