# fongwz
###### \java\seedu\address\commons\events\ui\BrowserPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Browser List Panel
 */
public class BrowserPanelSelectionChangedEvent extends BaseEvent {

    private final String browserSelection;

    public BrowserPanelSelectionChangedEvent(String browserSelection) {
        this.browserSelection = browserSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getBrowserSelection() {
        return this.browserSelection;
    }
}
```
###### \java\seedu\address\commons\events\ui\JumpToBrowserListRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of browser panels
 */
public class JumpToBrowserListRequestEvent extends BaseEvent {

    public final String browserItem;

    public JumpToBrowserListRequestEvent(String item) {
        this.browserItem = item;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowBrowserEvent.java
``` java
/**
 * Indicates a request show the browser panel
 */
public class ShowBrowserEvent extends BaseEvent {

    public ShowBrowserEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowMeetingEvent.java
``` java
/**
 * Indicates a request show the meeting panel
 */
public class ShowMeetingEvent extends BaseEvent {

    public ShowMeetingEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\ChooseCommand.java
``` java
/**
 * Chooses the display screen mode
 */
public class ChooseCommand extends Command {

    public static final String COMMAND_WORD = "choose";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : selects the type of display in the main browser window.\n"
            + "Parameters: <TYPE>\n"
            + "Example: choose linkedin";


    public static final String MESSAGE_SUCCESS = "Selected type ";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TYPE";

    private final String targetDisplay;

    public ChooseCommand(String targetDisplay) {
        this.targetDisplay = targetDisplay;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (targetDisplay.equals("meeting")) {
            EventsCenter.getInstance().post(new ShowMeetingEvent());
            EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));
        } else {
            EventsCenter.getInstance().post(new ShowBrowserEvent());
            EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));
        }
        return new CommandResult(MESSAGE_SUCCESS + targetDisplay);
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the list of commands */
    ObservableList<String> getCommandList();

    /** Returns the list of command templates */
    List<String> getCommandTemplateList();

    /** Returns the list of meetings */
    ObservableList<Meeting> getMeetingList();

    /** Returns the address book */
    ArrayList<String> getMeetingNames(Meeting meeting);
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<String> getCommandList() {
        List<String> commandList = Arrays.asList(
                AddCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                DeleteTagCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                ListByMostSearchedCommand.COMMAND_WORD,
                RedoCommand.COMMAND_WORD,
                SelectCommand.COMMAND_WORD,
                UndoCommand.COMMAND_WORD,
                PrefCommand.COMMAND_WORD,
                ChooseCommand.COMMAND_WORD,
                NextMeetingCommand.COMMAND_WORD,
                SearchCommand.COMMAND_WORD,
                MapCommand.COMMAND_WORD
        );
        return FXCollections.observableList(commandList);
    }

    @Override
    public List<String> getCommandTemplateList() {
        List<String> templateList = Arrays.asList(
                AddCommand.MESSAGE_TEMPLATE,
                ClearCommand.MESSAGE_TEMPLATE,
                DeleteCommand.MESSAGE_TEMPLATE,
                DeleteTagCommand.MESSAGE_TEMPLATE,
                EditCommand.MESSAGE_TEMPLATE,
                ExitCommand.MESSAGE_TEMPLATE,
                FindCommand.MESSAGE_TEMPLATE,
                HelpCommand.MESSAGE_TEMPLATE,
                HistoryCommand.MESSAGE_TEMPLATE,
                ListCommand.MESSAGE_TEMPLATE,
                ListByMostSearchedCommand.MESSAGE_TEMPLATE,
                RedoCommand.MESSAGE_TEMPLATE,
                SelectCommand.MESSAGE_TEMPLATE,
                UndoCommand.MESSAGE_TEMPLATE,
                PrefCommand.MESSAGE_TEMPLATE,
                ChooseCommand.MESSAGE_TEMPLATE,
                NextMeetingCommand.MESSAGE_TEMPLATE,
                SearchCommand.MESSAGE_TEMPLATE,
                MapCommand.MESSAGE_TEMPLATE
        );
        return templateList;
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        return model.getMeetingList().getMeetingList();
    }

    @Override
    public ArrayList<String> getMeetingNames(Meeting meeting) {
        ArrayList<String> nameList = new ArrayList<>();
        try {
            for (InternalId id : meeting.getListOfPersonsId()) {
                nameList.add(model.getAddressBook().getPersonByInternalIndex(id.getId()).getName().fullName);
            }
            return nameList;
        } catch (PersonNotFoundException e) {
            logger.info(e.getMessage());
            return nameList;
        }
    }
```
###### \java\seedu\address\logic\parser\ChooseCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ChooseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a ChooseCommand Object
 */
public class ChooseCommandParser implements Parser<ChooseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChooseCommand
     * and returns a ChooseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChooseCommand parse(String args) throws ParseException {
        String browsertype = args.trim();

        if (browsertype.equals("linkedin") || browsertype.equals("facebook") || browsertype.equals("meeting")) {
            return new ChooseCommand(args.trim());
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChooseCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleBrowserPanelSelectionChangedEvent(BrowserPanelSelectionChangedEvent event) {
        loadOtherPages(event.getBrowserSelection());
    }

    //@author martyn-wong
    @Subscribe
    private void handleMapPanelEvent(MapPersonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonMap(event.getPerson());
    }
}
```
###### \java\seedu\address\ui\BrowserSelectorCard.java
``` java
/**
 * A UI component that displays information on the browser display type
 */
public class BrowserSelectorCard extends UiPart<Region> {

    private static final String FXML = "BrowserSelectorCard.fxml";

    private final String imageString;

    @FXML
    private ImageView browserCardImage;

    @FXML
    private Label browserCardText;

    public BrowserSelectorCard(String imageName) {
        super(FXML);
        this.imageString = imageName;
        browserCardText.textProperty().setValue(imageName);
        fillImage(imageName);
    }

    /**
     * Fills the image on the browser card
     */
    private void fillImage(String imageName) {
        if (imageName.equals("linkedin")) {
            browserCardImage.setImage(new Image("/images/linkedin.png"));
        } else if (imageName.equals("facebook")) {
            browserCardImage.setImage(new Image("/images/facebook.png"));
        } else if (imageName.equals("meeting")) {
            browserCardImage.setImage(new Image("/images/meeting.png"));
        } else if (imageName.equals("maps")) {
            browserCardImage.setImage(new Image("/images/maps.png"));
        }
    }

    public String getImageString() {
        return imageString;
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public CommandBox(Logic logic, StackPane commandBoxHelp, SplitPane settingsPane) {
        super(FXML);
        this.logic = logic;
        this.commandBoxHelper = new CommandBoxHelper(logic);
        this.helperContainer = commandBoxHelp;
        this.settingsPane = settingsPane;
        setAnimation();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();

            /** Shows helper if there is text in the command field that corresponds to the command list*/
            if (commandBoxHelper.listHelp(commandTextField) && !helpEnabled) {
                showHelper();
            } else if (!commandBoxHelper.listHelp(commandTextField)) {
                hideHelper();
            }

            /** Shows settings screen if there is text in the command field that corresponds to settings*/
            if (checkForSettingsPanelPopup(commandTextField)) {
                timelineLeft.play();
            } else {
                timelineRight.play();
            }
        });
        commandTextField.setStyle("-fx-font-style: italic;" + " -fx-text-fill: lime");
        historySnapshot = logic.getHistorySnapshot();
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Shows the command helper
     */
    private void showHelper() {
        helperContainer.getChildren().add(commandBoxHelper.getRoot());
        helpEnabled = true;
    }

    /**
     * Hides the command helper
     */
    private void hideHelper() {
        helperContainer.getChildren().remove(commandBoxHelper.getRoot());
        helpEnabled = false;
    }

    /**
     * Check whether to display the settings panel
     */
    private boolean checkForSettingsPanelPopup(TextField commandTextField) {
        if (commandTextField.getText().contains("choose") || commandTextField.getText().contains("pref")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the animation sequence for entering left and right on the settings panel
     */
    private void setAnimation() {
        final Timeline timelineBounce = new Timeline();
        timelineBounce.setCycleCount(1);
        timelineBounce.setAutoReverse(true);
        KeyValue kv1 = new KeyValue(settingsPane.translateXProperty(), 0);
        KeyValue kv2 = new KeyValue(settingsPane.translateXProperty(), -10);
        KeyValue kv3 = new KeyValue(settingsPane.translateXProperty(), 0);
        KeyFrame kf1 = new KeyFrame(Duration.millis(200), kv1, kv2, kv3);
        timelineBounce.getKeyFrames().add(kf1);

        /* Event handler to call bouncing effect after the scroll to left is finished. */
        javafx.event.EventHandler<ActionEvent> onFinished = new javafx.event.EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timelineBounce.play();
            }
        };

        timelineLeft = new Timeline();
        timelineRight = new Timeline();

        timelineLeft.setCycleCount(1);
        timelineLeft.setAutoReverse(true);
        KeyValue kvLeft1 = new KeyValue(settingsPane.translateXProperty(), -10);
        KeyFrame kfLeft = new KeyFrame(Duration.millis(200), onFinished, kvLeft1);
        timelineLeft.getKeyFrames().add(kfLeft);

        timelineRight.setCycleCount(1);
        timelineRight.setAutoReverse(true);
        KeyValue kvRight1 = new KeyValue(settingsPane.translateXProperty(), 300);
        KeyFrame kfRight = new KeyFrame(Duration.millis(200), kvRight1);
        timelineRight.getKeyFrames().add(kfRight);
    }
```
###### \java\seedu\address\ui\CommandBoxHelper.java
``` java
/**
 * The UI component that is responsible for listing out possible commands based on user input in CLI
 */
public class CommandBoxHelper extends UiPart<Region> {

    private static final String FXML = "CommandBoxHelper.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBoxHelper.class);
    private final Logic logic;

    private Character firstChar;
    private String commandString;
    private ArrayList<String> helpList;

    @FXML
    private ListView<HelperCard> commandBoxHelperList;


    public CommandBoxHelper(Logic logic) {
        super(FXML);
        this.logic = logic;
        this.helpList = new ArrayList<String>();
    }

    /**
     * Command to display text within the CommandBoxHelper given user input in the CLI.
     */
    public boolean listHelp(TextField commandText) {
        clearListView();
        try {
            commandString = commandText.getText();
            firstChar = commandText.getText().charAt(0);
        } catch (Exception e) {
            logger.info("Invalid String or String is empty");
            logger.info("Hiding command helper");
            return false;
        }

        for (int i = 0; i < logic.getCommandList().size(); i++) {
            if (checkSubset(logic.getCommandList().get(i), commandString, firstChar)) {
                helpList.add(logic.getCommandList().get(i));
            }
        }
        if (helpList.isEmpty()) {
            return false;
        } else {
            setConnections(FXCollections.observableList(helpList));
            return true;
        }
    }

    /**
     * Checks if commandBoxHelper is empty
     * @return true if empty, false otherwise
     */
    public boolean checkEmpty() {
        return helpList.isEmpty();
    }

    /**
     * Called when user presses down key on command helper
     */
    public void selectDownHelperBox() {
        if (!commandBoxHelperList.getSelectionModel().isSelected(0)) {
            commandBoxHelperList.getSelectionModel().selectFirst();
        } else {
            commandBoxHelperList.getSelectionModel().select(
                    commandBoxHelperList.getSelectionModel().getSelectedIndex() + 1);
        }
    }

    /**
     * Called when user presses up key on command helper
     */
    public void selectUpHelperBox() {
        if (!commandBoxHelperList.getSelectionModel().isSelected(0)) {
            commandBoxHelperList.getSelectionModel().selectFirst();
        } else {
            commandBoxHelperList.getSelectionModel().select(
                    commandBoxHelperList.getSelectionModel().getSelectedIndex() - 1);
        }
    }

    /**
     * Checks whether the command helper has been selected
     * @return true if selected, false otherwise
     */
    public boolean isMainSelected() {
        for (int i = 0; i <= helpList.size(); i++) {
            if (commandBoxHelperList.getSelectionModel().isSelected(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter method for each HelperCard in the commandBoxHelperList
     * @return String of each HelperCard
     */
    public String getHelperText() {
        for (int i = 0; i <= logic.getCommandTemplateList().size(); i++) {
            if (logic.getCommandTemplateList().get(i).contains(
                    commandBoxHelperList.getSelectionModel().getSelectedItem().getText())) {
                return logic.getCommandTemplateList().get(i);
            }
        }
        return null;
    }

    /**
     * Checks if commandFieldString is a subset of commandWord and if their first letter matches
     */
    private boolean checkSubset(String commandWord , String commandFieldString, Character firstChar) {
        try {
            if (commandWord.contains(commandFieldString) && firstChar.equals(commandWord.charAt(0))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Empties the list view
     */
    private void clearListView() {
        try {
            helpList.clear();
            ArrayList<HelperCard> selectedItemsCopy = new ArrayList<>(
                    commandBoxHelperList.getSelectionModel().getSelectedItems());
            commandBoxHelperList.getItems().removeAll(selectedItemsCopy);
        } catch (Exception e) {
            logger.info(e.getMessage() + " no items in the list!");
        }
    }

    private void setConnections(ObservableList<String> commandList) {
        ObservableList<HelperCard> mappedList = EasyBind.map(
                commandList, (commandString) -> new HelperCard(commandString));
        commandBoxHelperList.setItems(mappedList);
        commandBoxHelperList.setCellFactory(listView -> new CommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class CommandListViewCell extends ListCell<HelperCard> {

        @Override
        protected void updateItem(HelperCard command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(command.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\HelperCard.java
``` java
/**
 * A ui component that displays commands in the command helper box
 */
public class HelperCard extends UiPart<Region> {

    private static final String FXML = "HelperCard.fxml";

    public final String commandString;

    @FXML
    private HBox commandCardPane;

    @FXML
    private Label command;

    public HelperCard(String commandString) {
        super(FXML);
        this.commandString = commandString;
        command.textProperty().setValue(commandString);
    }

    public String getText() {
        return this.commandString;
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        SettingsSelector settingsSelector = new SettingsSelector();
        settingsSelectorPlaceholder.getChildren().add(settingsSelector.getRoot());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        //Setting initial position of settings panel
        settingsPane.setTranslateX(160);

        CommandBox commandBox = new CommandBox(logic, commandBoxHelperPlaceholder, settingsPane);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowBrowserEvent(ShowBrowserEvent event) {
        browserPlaceholder.getChildren().remove(meetingPanel.getRoot());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
    }

    @Subscribe
    private void handleShowMeetingEvent(ShowMeetingEvent event) {
        try {
            browserPlaceholder.getChildren().remove(browserPanel.getRoot());
        } catch (IllegalArgumentException e) {
            logger.info("Error removing browser panel : " + e.getMessage());
        }

        try {
            browserPlaceholder.getChildren().add(meetingPanel.getRoot());
        } catch (IllegalArgumentException e) {
            logger.info("Meeting panel is already displayed!");
        }
    }
```
###### \java\seedu\address\ui\MeetingPanel.java
``` java
/**
 *  UI component containing a listview to show list of meetings
 */
public class MeetingPanel extends UiPart<Region> {

    private static final String FXML = "MeetingPanel.fxml";

    private Logic logic;

    @FXML
    private ListView<MeetingPanelCard> meetingList;

    public MeetingPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        setConnections();
    }

    private void setConnections() {
        ObservableList<MeetingPanelCard> mappedMeetingList = EasyBind.map(
                logic.getMeetingList(), (item) -> new MeetingPanelCard(item, logic.getMeetingNames(item)));
        meetingList.setItems(mappedMeetingList);
        meetingList.setCellFactory(listView -> new MeetingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MeetingPanelCard}.
     */
    class MeetingListViewCell extends ListCell<MeetingPanelCard> {

        @Override
        protected void updateItem(MeetingPanelCard item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(item.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\MeetingPanelCard.java
``` java
/**
 *  UI component that displays information about upcoming meetings
 */
public class MeetingPanelCard extends UiPart<Region> {

    private static final String FXML = "MeetingPanelCard.fxml";

    @FXML
    private Label meetingDateLabel;

    @FXML
    private Label meetingPersonLabel;

    @FXML
    private Label meetingLocationLabel;

    @FXML
    private Label meetingTimeLabel;

    @FXML
    private Label meetingNotesLabel;

    public MeetingPanelCard(Meeting meeting, ArrayList<String> names) {
        super(FXML);
        meetingDateLabel.textProperty().setValue(meeting.getDate());
        meetingPersonLabel.textProperty().setValue(names.toString());
        meetingLocationLabel.textProperty().setValue(meeting.getLocation());
        meetingTimeLabel.textProperty().setValue(meeting.getTime());
        meetingNotesLabel.textProperty().setValue(meeting.getNotes());
    }
}
```
###### \java\seedu\address\ui\SettingsSelector.java
``` java
/**
 * Panel containing the list of settings
 */
public class SettingsSelector extends UiPart<Region> {

    private static final String FXML = "SettingsSelector.fxml";
    private final Logger logger = LogsCenter.getLogger(SettingsSelector.class);

    @FXML
    private ListView<BrowserSelectorCard> browserSelectorList;

    @FXML
    private ListView<ThemeSelectorCard> themeSelectorList;

    @FXML
    private Label browserSelectorTitle;

    @FXML
    private Label themeSelectorTitle;

    public SettingsSelector() {
        super(FXML);
        setConnections();
        registerAsAnEventHandler(this);
        browserSelectorTitle.textProperty().setValue("Display Mode :");
        browserSelectorTitle.getStyleClass().add("label-bright-underline");
        themeSelectorTitle.textProperty().setValue("Theme :");
        themeSelectorTitle.getStyleClass().add("label-bright-underline");
    }

    private void setConnections() {
        //Setting connections for browser list
        ObservableList<String> browserItems = FXCollections.observableArrayList(
                "linkedin", "facebook", "meeting", "maps"
        );
        ObservableList<BrowserSelectorCard> mappedBrowserList = EasyBind.map(
                browserItems, (item) -> new BrowserSelectorCard(item));
        browserSelectorList.setItems(mappedBrowserList);
        browserSelectorList.setCellFactory(listView -> new BrowserListViewCell());

        //Setting connections for theme list
        ObservableList<String> themeItems = FXCollections.observableArrayList(
                "blue", "dark", "light"
        );
        ObservableList<ThemeSelectorCard> mappedThemeList = EasyBind.map(
                themeItems, (item) -> new ThemeSelectorCard(item));
        themeSelectorList.setItems(mappedThemeList);
        themeSelectorList.setCellFactory(listView -> new SettingsSelector.ThemeListViewCell());
    }

    /**
     * Raise an event to select the browser list items
     */
    private void selectBrowser(String browserSelection) {
        for (int i = 0; i <= browserSelectorList.getItems().size(); i++) {
            if (browserSelectorList.getItems().get(i).getImageString().equals(browserSelection)) {
                browserSelectorList.getSelectionModel().clearAndSelect(i);
                raise(new BrowserPanelSelectionChangedEvent(browserSelection));
                return;
            }
        }
    }

    @Subscribe
    private void handleJumpToBrowserListRequestEvent(JumpToBrowserListRequestEvent event) {
        selectBrowser(event.browserItem);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code BrowserSelectorCard}.
     */
    class BrowserListViewCell extends ListCell<BrowserSelectorCard> {

        @Override
        protected void updateItem(BrowserSelectorCard item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(item.getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ThemeSelectorCard}.
     */
    class ThemeListViewCell extends ListCell<ThemeSelectorCard> {

        @Override
        protected void updateItem(ThemeSelectorCard item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(item.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\ThemeSelectorCard.java
``` java
/**
 * A UI component that displays information on which theme is currently selected
 */
public class ThemeSelectorCard extends UiPart<Region> {

    private static final String FXML = "ThemeSelectorCard.fxml";

    @FXML
    private Circle themeCircle;

    @FXML
    private Label themeLabel;

    public ThemeSelectorCard(String themeName) {
        super(FXML);
        if (themeName.equals("blue")) {
            themeLabel.textProperty().setValue("Blue");
            themeCircle.setFill(Paint.valueOf("#616fd4"));
        } else if (themeName.equals("dark")) {
            themeLabel.textProperty().setValue("Dark");
            themeCircle.setFill(Paint.valueOf("#494b5c"));
        } else if (themeName.equals("light")) {
            themeLabel.textProperty().setValue("Light");
            themeCircle.setFill(Paint.valueOf("#dddff0"));
        }
    }
}
```
###### \resources\view\BrowserSelectorCard.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>

<HBox fx:id="browserCardPane" alignment="CENTER" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane prefWidth="130.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="38.0" minWidth="38.0" prefWidth="38.0" />
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <ImageView fx:id="browserCardImage" nodeOrientation="INHERIT" pickOnBounds="true" preserveRatio="true" />
            <Label fx:id="browserCardText" text="Label" GridPane.columnIndex="1" />
         </children>
      </GridPane>
   </children>
</HBox>
```
###### \resources\view\CommandBoxHelper.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ListView fx:id="commandBoxHelperList" />
   </children>
</VBox>
```
###### \resources\view\HelperCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="commandCardPane" fx:id="commandCardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="15" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <Label fx:id="command" styleClass="cell_small_label" text="\$command" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\MainWindow.fxml
``` fxml
    <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
        <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
        </padding>
    </StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
          <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
        <VBox fx:id="personList" minWidth="340.0" prefWidth="340.0" SplitPane.resizableWithParent="false">
            <padding>
                <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
            <StackPane fx:id="personListPanelPlaceholder" alignment="TOP_LEFT" VBox.vgrow="ALWAYS" />
        </VBox>
      <StackPane prefWidth="340.0">
         <children>
                          <StackPane fx:id="browserPlaceholder" prefWidth="340.0" StackPane.alignment="CENTER">
                  <padding>
                      <Insets bottom="10" left="10" right="10" top="10" />
                  </padding>
              </StackPane>
            <SplitPane fx:id="settingsPane" maxWidth="150.0" minWidth="150.0" orientation="VERTICAL" prefWidth="150.0" StackPane.alignment="CENTER_RIGHT">
               <items>
                  <VBox maxWidth="150.0" minWidth="150.0" prefWidth="150.0" SplitPane.resizableWithParent="false">
                     <children>
                        <StackPane fx:id="settingsSelectorPlaceholder" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" />
                     </children>
                  </VBox>
               </items>
            </SplitPane>
         </children>
         <padding>
            <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
         </padding>
      </StackPane>
    </SplitPane>
```
###### \resources\view\MeetingPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>


<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <VBox>
         <children>
            <GridPane>
              <columnConstraints>
                <ColumnConstraints halignment="LEFT" hgrow="ALWAYS" maxWidth="120.0" minWidth="10.0" prefWidth="120.0" />
                <ColumnConstraints halignment="LEFT" hgrow="SOMETIMES" maxWidth="230.0" minWidth="10.0" prefWidth="230.0" />
                  <ColumnConstraints halignment="LEFT" hgrow="SOMETIMES" maxWidth="230.0" minWidth="10.0" prefWidth="230.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="80.0" minWidth="10.0" prefWidth="80.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <ImageView fitHeight="32.0" fitWidth="32.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../images/meeting_date.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <Label alignment="CENTER_RIGHT" text="Date" GridPane.halignment="CENTER" />
                  <ImageView fitHeight="32.0" fitWidth="32.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="1">
                     <image>
                        <Image url="@../images/meeting_person.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <Label text="People" GridPane.columnIndex="1" GridPane.halignment="LEFT">
                     <GridPane.margin>
                        <Insets left="40.0" />
                     </GridPane.margin>
                  </Label>
                  <ImageView fitHeight="32.0" fitWidth="32.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="2">
                     <image>
                        <Image url="@../images/meeting_location.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <Label text="Location" GridPane.columnIndex="2" GridPane.halignment="LEFT">
                     <GridPane.margin>
                        <Insets left="40.0" />
                     </GridPane.margin>
                  </Label>
                  <Label text="Time" GridPane.columnIndex="3" GridPane.halignment="CENTER">
                     <GridPane.margin>
                        <Insets left="28.0" />
                     </GridPane.margin>
                  </Label>
                  <ImageView fitHeight="32.0" fitWidth="32.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="3">
                     <image>
                        <Image url="@../images/meeting_time.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fitHeight="32.0" fitWidth="32.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="4">
                     <GridPane.margin>
                        <Insets left="5.0" right="5.0" />
                     </GridPane.margin>
                     <image>
                        <Image url="@../images/meeting_notes.png" />
                     </image>
                  </ImageView>
                  <Label text="Notes" GridPane.columnIndex="4">
                     <GridPane.margin>
                        <Insets left="40.0" />
                     </GridPane.margin>
                  </Label>
               </children>
               <opaqueInsets>
                  <Insets bottom="10.0" />
               </opaqueInsets>
               <VBox.margin>
                  <Insets bottom="4.0" />
               </VBox.margin>
            </GridPane>
            <ListView fx:id="meetingList" />
         </children>
      </VBox>
   </children>
</StackPane>
```
###### \resources\view\MeetingPanelCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>

<HBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane gridLinesVisible="true">
        <columnConstraints>
          <ColumnConstraints halignment="LEFT" hgrow="ALWAYS" maxWidth="120.0" minWidth="10.0" prefWidth="120.0" />
          <ColumnConstraints halignment="LEFT" hgrow="SOMETIMES" minWidth="10.0" prefWidth="230.0" />
            <ColumnConstraints halignment="LEFT" hgrow="SOMETIMES" minWidth="10.0" prefWidth="230.0" />
            <ColumnConstraints halignment="LEFT" hgrow="NEVER" maxWidth="80.0" minWidth="10.0" prefWidth="80.0" />
            <ColumnConstraints halignment="LEFT" hgrow="ALWAYS" minWidth="10.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label fx:id="meetingDateLabel" text="Label" GridPane.halignment="LEFT">
               <padding>
                  <Insets left="12.0" right="12.0" />
               </padding></Label>
            <Label fx:id="meetingPersonLabel" text="Label" GridPane.columnIndex="1" GridPane.halignment="LEFT">
               <padding>
                  <Insets left="12.0" right="12.0" />
               </padding></Label>
            <Label fx:id="meetingLocationLabel" text="Label" GridPane.columnIndex="2" GridPane.halignment="LEFT">
               <padding>
                  <Insets left="12.0" right="12.0" />
               </padding></Label>
            <Label fx:id="meetingTimeLabel" text="Label" GridPane.columnIndex="3" GridPane.halignment="LEFT">
               <padding>
                  <Insets left="12.0" right="12.0" />
               </padding></Label>
            <Label fx:id="meetingNotesLabel" text="Label" GridPane.columnIndex="4" GridPane.halignment="LEFT">
               <padding>
                  <Insets left="12.0" right="12.0" />
               </padding></Label>
         </children>
      </GridPane>
   </children>
</HBox>
```
###### \resources\view\SettingsSelector.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="150.0" minHeight="-Infinity" minWidth="-Infinity" prefWidth="150.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane prefWidth="150.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="200.0" minHeight="10.0" prefHeight="200.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="100.0" minHeight="10.0" prefHeight="100.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="150.0" minHeight="10.0" prefHeight="150.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label fx:id="browserSelectorTitle" prefWidth="150.0" text="Label" />
            <ListView fx:id="browserSelectorList" GridPane.rowIndex="1" />
            <ListView fx:id="themeSelectorList" prefHeight="200.0" prefWidth="200.0" GridPane.rowIndex="4" />
            <Label fx:id="themeSelectorTitle" text="Label" GridPane.halignment="CENTER" GridPane.rowIndex="3" />
         </children>
      </GridPane>
   </children>
</VBox>
```
###### \resources\view\ThemeSelectorCard.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.shape.Circle?>

<HBox fx:id="themeCardPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefWidth="130.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane>
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="60.0" />
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Circle fx:id="themeCircle" fill="DODGERBLUE" radius="11.0" stroke="BLACK" strokeType="INSIDE" GridPane.halignment="CENTER" />
            <Label fx:id="themeLabel" text="Label" GridPane.columnIndex="1" />
         </children>
      </GridPane>
   </children>
</HBox>
```
