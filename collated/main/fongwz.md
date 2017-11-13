# fongwz
###### /java/seedu/address/Launcher.java
``` java
/**
 * Launches the splash screen before mainapp is started
 */
public class Launcher {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(MainApp.class, FirstPreloader.class, args);
    }
}
```
###### /java/seedu/address/FirstPreloader.java
``` java
/**
 * Preloader class
 */
public class FirstPreloader extends Preloader {

    private static final Double WIDTH = 506.0;
    private static final Double HEIGHT = 311.0;

    private Stage stage;

    /**
     * Method to create splash screen
     * @return Scene containing splashscreen
     */
    private Scene createPreloaderScene() {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.getRoot().setBackground(Background.EMPTY);
        Scene scene = new Scene(splashScreen.getRoot(), 460, 250);
        scene.setFill(Color.TRANSPARENT);
        return scene;
    }

    /**
     * Starts the splash screen
     */
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.initStyle(StageStyle.TRANSPARENT);

        centerStage(stage, WIDTH, HEIGHT);
        stage.show();

    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == Preloader.StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

    /**
     * Roughly centers the stage to your computer screen
     */
    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
}
```
###### /java/seedu/address/commons/events/ui/ChangeThemeEvent.java
``` java
/**
 * Indicates a request to jump to the list of browser panels
 */
public class ChangeThemeEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/JumpToBrowserListRequestEvent.java
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
###### /java/seedu/address/commons/events/ui/ShowBrowserEvent.java
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
###### /java/seedu/address/commons/events/ui/ShowMeetingEvent.java
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
###### /java/seedu/address/commons/events/ui/BrowserPanelSelectionChangedEvent.java
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
###### /java/seedu/address/ui/SplashScreen.java
``` java
/**
 * UI component to load splash screen and animate it
 */
public class SplashScreen extends UiPart<Region> {

    private static final String FXML = "SplashScreen.fxml";

    private Timeline timeline;

    @FXML
    private ImageView splashImage;

    @FXML
    private ImageView splashLoadingImage;


    public SplashScreen() {
        super(FXML);
        splashImage.setImage(new Image("/images/SplashScreen.png"));
        splashLoadingImage.setImage(new Image("/images/SplashScreenLoading.png"));
        setAnimation();
    }

    private void setAnimation() {
        KeyValue moveRight = new KeyValue(splashLoadingImage.translateXProperty(), 460);

        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashLoadingImage.setTranslateX(-92);
            }
        };

        KeyFrame kf = new KeyFrame(Duration.millis(2000), onFinished, moveRight);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().addAll(kf);
        timeline.play();
    }
}
```
###### /java/seedu/address/ui/ThemeSelectorCard.java
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

    public String getThemeName() {
        return themeLabel.textProperty().getValue();
    }
}
```
###### /java/seedu/address/ui/SettingsSelector.java
``` java
/**
 * Panel containing the list of settings
 */
public class SettingsSelector extends UiPart<Region> {

    private static final String FXML = "SettingsSelector.fxml";
    private final Logger logger = LogsCenter.getLogger(SettingsSelector.class);

    private ObservableList<BrowserSelectorCard> browserItems;
    private ObservableList<ThemeSelectorCard> themeItems;

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
        ObservableList<String> browserStringItems = FXCollections.observableArrayList(
                "linkedin", "google", "meeting", "maps"
        );
        browserItems = EasyBind.map(
                browserStringItems, (item) -> new BrowserSelectorCard(item));
        browserSelectorList.setItems(browserItems);
        browserSelectorList.setCellFactory(listView -> new BrowserListViewCell());

        //Setting connections for theme list
        ObservableList<String> themeStringItems = FXCollections.observableArrayList(
                "blue", "dark", "light"
        );
        themeItems = EasyBind.map(
                themeStringItems, (item) -> new ThemeSelectorCard(item));
        themeSelectorList.setItems(themeItems);
        themeSelectorList.setCellFactory(listView -> new SettingsSelector.ThemeListViewCell());

        setEventHandlerSelectionChange();
    }

    private void setEventHandlerSelectionChange() {
        browserSelectorList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in browser list panel changed to : '" + newValue + "'");
                        if (newValue.getImageString().equals("meeting")) {
                            raise(new ShowMeetingEvent());
                        } else {
                            raise(new ShowBrowserEvent());
                            raise(new BrowserPanelSelectionChangedEvent(newValue.getImageString()));
                        }
                    }
                });
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

    /**
     * Selects the theme on the theme ListView
     * @param theme
     */
    public void selectTheme(String theme) {
        for (int i = 0; i < themeSelectorList.getItems().size(); i++) {
            if (themeSelectorList.getItems().get(i).getThemeName().equals(theme)) {
                themeSelectorList.getSelectionModel().clearAndSelect(i);
            }
        }
    }

    @Subscribe
    private void handleJumpToBrowserListRequestEvent(JumpToBrowserListRequestEvent event) {
        selectBrowser(event.browserItem);
    }

    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        selectTheme(event.theme);
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

    /**
     * Returns the browser List View for test cases
     */
    public ListView<BrowserSelectorCard> getBrowserSelectorList() {
        return browserSelectorList;
    }

    /**
     * Returns the observable list of browser items for test cases
     */
    public ObservableList<BrowserSelectorCard> getBrowserItems() {
        return browserItems;
    }
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    public CommandBox(Logic logic, StackPane commandBoxHelp, SplitPane settingsPane) {
        super(FXML);
        this.logic = logic;
        this.commandBoxHelper = new CommandBoxHelper(logic);
        this.helperContainer = commandBoxHelp;
        this.settingsPane = settingsPane;
        this.style = getRoot().getStyle();
        registerAsAnEventHandler(this);
        setAnimation();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            commandTextField.setStyle(style);

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
        historySnapshot = logic.getHistorySnapshot();
    }
```
###### /java/seedu/address/ui/CommandBox.java
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
        timelineLeft = new Timeline();
        timelineRight = new Timeline();

        timelineLeft.setCycleCount(1);
        timelineLeft.setAutoReverse(true);
        KeyValue kvLeft1 = new KeyValue(settingsPane.translateXProperty(), -10);
        KeyFrame kfLeft = new KeyFrame(Duration.millis(200), kvLeft1);
        timelineLeft.getKeyFrames().add(kfLeft);

        timelineRight.setCycleCount(1);
        timelineRight.setAutoReverse(true);
        KeyValue kvRight1 = new KeyValue(settingsPane.translateXProperty(), 300);
        KeyFrame kfRight = new KeyFrame(Duration.millis(200), kvRight1);
        timelineRight.getKeyFrames().add(kfRight);
    }

    @Subscribe
    private void handleShowMeetingEvent(ShowMeetingEvent event) {
        timelineRight.play();
    }

    @Subscribe
    private void handleShowBrowserEvent(ShowBrowserEvent event) {
        timelineRight.play();
    }
```
###### /java/seedu/address/ui/CommandBoxHelper.java
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
            //logger.info("Invalid String or String is empty");
            //logger.info("Hiding command helper");
            //comment out if command box helper working as intended, fills log with unnecessary spam.
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
            //logger.info(e.getMessage() + " no items in the list!");
            // comment out if command helper is working as intended, fills log with unnecessary spam.
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
###### /java/seedu/address/ui/MeetingPanelCard.java
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

    public MeetingPanelCard(ReadOnlyMeeting meeting, ArrayList<String> names) {
        super(FXML);
        meetingDateLabel.textProperty().setValue(meeting.getDate());
        meetingPersonLabel.textProperty().setValue(names.toString());
        meetingLocationLabel.textProperty().setValue(meeting.getLocation().toString());
        meetingTimeLabel.textProperty().setValue(meeting.getTime());
        meetingNotesLabel.textProperty().setValue(meeting.getNotes());
    }
}
```
###### /java/seedu/address/ui/MeetingPanel.java
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
###### /java/seedu/address/ui/BrowserSelectorCard.java
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
        } else if (imageName.equals("google")) {
            browserCardImage.setImage(new Image("/images/google.png"));
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
###### /java/seedu/address/ui/HelperCard.java
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
###### /java/seedu/address/ui/MainWindow.java
``` java
        SettingsSelector settingsSelector = new SettingsSelector();
        settingsSelector.selectTheme(style);
        settingsSelectorPlaceholder.getChildren().add(settingsSelector.getRoot());
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        //Setting initial position of settings panel
        settingsPane.setTranslateX(160);

        CommandBox commandBox = new CommandBox(logic, commandBoxHelperPlaceholder, settingsPane);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
```
###### /java/seedu/address/ui/MainWindow.java
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

    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        scene.getStylesheets().remove(cssPath);
        cssPath = "";
        cssPath = "view/";

        switch (event.theme) {
        case "Light":
            cssPath += "LightTheme.css";
            break;
        case "Blue":
            cssPath += "BlueTheme.css";
            break;
        default:
            cssPath += "DarkTheme.css";
            break;
        }
        scene.getStylesheets().add(cssPath);
    }

```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the list of commands */
    ObservableList<String> getCommandList();

    /** Returns the list of command templates */
    List<String> getCommandTemplateList();

    /** Returns the list of meetings */
    ObservableList<ReadOnlyMeeting> getMeetingList();
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<String> getCommandList() {
        List<String> commandList = Arrays.asList(
                AddCommand.COMMAND_WORD,
                AddMeetingCommand.COMMAND_WORD,
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
                SetupAsanaCommand.COMMAND_WORD,
                SetUniqueKeyCommand.COMMAND_WORD,
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
                AddMeetingCommand.MESSAGE_TEMPLATE,
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
                SetupAsanaCommand.MESSAGE_TEMPLATE,
                SetUniqueKeyCommand.MESSAGE_TEMPLATE,
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
    public ObservableList<ReadOnlyMeeting> getMeetingList() {
        return model.getMeetingList().getMeetingList();
    }

    @Override
    public ArrayList<String> getMeetingNames(ReadOnlyMeeting meeting) {
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
###### /java/seedu/address/logic/parser/ChooseCommandParser.java
``` java
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
        try {
            String browserType = ParserUtil.parseArgument(args.trim());
            return new ChooseCommand(browserType);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ARGUMENT, ChooseCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code args} into a trimmed argument and returns it.
     * @throws IllegalValueException if the argument provided is invalid (contains special characters).
     */
    public static String parseArgument(String args) throws IllegalValueException {
        String parsedArgs = args.trim();
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(parsedArgs);
        boolean b = m.find();

        if (b) {
            throw new IllegalValueException(MESSAGE_INVALID_ARGUMENT);
        }
        return parsedArgs;
    }
```
###### /java/seedu/address/logic/commands/ChooseCommand.java
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

        if (Selection.getSelectionStatus() == false) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_SELECTED);
        }

        if (targetDisplay.equals("meeting")) {
            EventsCenter.getInstance().post(new ShowMeetingEvent());
            EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));


        } else if (targetDisplay.equals("linkedin") || targetDisplay.equals("google") || targetDisplay.equals("maps")) {
            EventsCenter.getInstance().post(new ShowBrowserEvent());
            EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));

        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_BROWSER_INDEX);
        }

        return new CommandResult(MESSAGE_SUCCESS + targetDisplay);
    }

}
```
###### /resources/view/BlueTheme.css
``` css
.background {
    -fx-background-color: derive(#0D47A1, 20%);
    background-color: #283593; /* Used in the default.html file */
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

.label-bright-underline {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #ffe4e1;
    -fx-opacity: 1;
    -fx-underline: true;
    -fx-alignment: center;
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
    -fx-base: #0D47A1;
    -fx-control-inner-background: #0D47A1;
    -fx-background-color: #0D47A1;
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
    -fx-background-color: derive(#a4a5ab, 20%);
    -fx-border-color: transparent #53587a transparent #53587a;
    -fx-border-width: 1;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#4b86b4, 60%);
}

.split-pane:vertical .split-pane-divider {
    -fx-background-color: #a4a5ab;
    -fx-border-width: 2;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: #e8fffc
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #718ea8;
}

.list-cell:filled:odd {
    -fx-background-color: #2c537a;
}

.list-cell:filled:selected {
    -fx-background-color: #3949AB;
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

/*Command box inner*/
.anchor-pane {
     -fx-background-color: derive(#FFFFFF, 20%);
}

/*Result display outer and command box outer */
.pane-with-border {
     -fx-background-color: #5092a8;
     -fx-border-color: derive(#0D47A1, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#FFFFFF, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: #427873;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
    -fx-background-color: #427873;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#0D47A1, 30%);
    -fx-border-color: derive(#0D47A1, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#0D47A1, 30%);
    -fx-border-color: derive(#0D47A1, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#0D47A1, 30%);
}

.context-menu {
    -fx-background-color: derive(#0D47A1, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

/* Menu bar at top */
.menu-bar {
    /*-fx-background-color: derive(#1f3f5e, 20%);*/
    -fx-background-color: linear-gradient(to right, #2c537a ,#a2d9e1);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/

.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #0D47A1;
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
  -fx-text-fill: #0D47A1;
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
    -fx-background-color: #0D47A1;
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
    -fx-background-color: #0D47A1;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #0D47A1;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#0D47A1, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}
*/
.scroll-bar {
    -fx-background-color: #38647a;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#38647a, 50%);
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
    -fx-background-color: transparent #283593 transparent #283593;
    -fx-background-insets: 0;
    -fx-border-color: #283593 #283593 #ffffff #283593;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Arial";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}
/*
#resultDisplay .content {
    -fx-background-color: transparent, #283593, transparent, #283593;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
} */

#browserSelectorList .list-cell:even {
    -fx-padding: 8,0,0,8;
    -fx-background-color: #718ea8;
}

#browserSelectorList .list-cell:odd {
    -fx-padding: 8,0,0,8;
    -fx-background-color: #2c537a;
}

.list-cell:filled:selected #browserCardPane {
    -fx-border-color: palegreen;
    -fx-border-width: 2;
    -fx-border-radius: 7 7 7 7;
}

#themeSelectorList .list-cell:even {
    -fx-padding: 8,0,0,8;
    -fx-background-color: #718ea8;
}

#themeSelectorList .list-cell:odd {
    -fx-padding: 8,0,0,8;
    -fx-background-color: #2c537a;
}

.list-cell:filled:selected #themeCardPane {
    -fx-border-color: palegreen;
    -fx-border-width: 2;
    -fx-border-radius: 7 7 7 7;
}

#browserPlaceholder {
    -fx-background-color: derive(#718ea8,50%);
}

#browserStack {
    -fx-background-color: #a4a5ab;
}

#browser {
    -fx-background-color: derive(#718ea8,50%);
}
```
###### /resources/view/ThemeSelectorCard.fxml
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
###### /resources/view/BrowserSelectorCard.fxml
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
###### /resources/view/MeetingPanel.fxml
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
                <ColumnConstraints halignment="LEFT" maxWidth="230.0" minWidth="10.0" prefWidth="230.0" />
                  <ColumnConstraints halignment="LEFT" maxWidth="230.0" minWidth="10.0" prefWidth="230.0" />
                  <ColumnConstraints maxWidth="80.0" minWidth="10.0" prefWidth="80.0" />
                  <ColumnConstraints minWidth="10.0" prefWidth="100.0" />
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
###### /resources/view/LightTheme.css
``` css
.background {
    -fx-background-color: derive(#F57F17, 20%);
    background-color: #F57F17; /* Used in the default.html file */
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
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

.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: #f4f4f4;
}
```
###### /resources/view/MeetingPanelCard.fxml
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
###### /resources/view/MainWindow.fxml
``` fxml
    <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
        <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
        </padding>
    </StackPane>
```
###### /resources/view/MainWindow.fxml
``` fxml
          <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
        <VBox fx:id="personList" minWidth="340.0" prefWidth="340.0" SplitPane.resizableWithParent="false">
            <padding>
                <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
            <StackPane fx:id="personListPanelPlaceholder" alignment="TOP_LEFT" VBox.vgrow="ALWAYS" />
        </VBox>
      <StackPane fx:id="mainDisplayContainer" prefWidth="340.0">
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
###### /resources/view/CommandBoxHelper.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="commandhelperVbox" styleClass="vbox" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ListView fx:id="commandBoxHelperList" styleClass="list-vew" />
   </children>
</VBox>
```
###### /resources/view/SettingsSelector.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="150.0" minHeight="-Infinity" minWidth="-Infinity" prefWidth="150.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
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
            <Label fx:id="browserSelectorTitle" alignment="CENTER" contentDisplay="CENTER" prefWidth="150.0" text="Label" />
            <ListView fx:id="browserSelectorList" GridPane.rowIndex="1" />
            <ListView fx:id="themeSelectorList" prefHeight="200.0" prefWidth="200.0" GridPane.rowIndex="4" />
            <Label fx:id="themeSelectorTitle" text="Label" GridPane.halignment="CENTER" GridPane.rowIndex="3" />
         </children>
      </GridPane>
   </children>
</VBox>
```
###### /resources/view/HelperCard.fxml
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
