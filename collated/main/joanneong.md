# joanneong
###### \java\seedu\address\commons\core\Commands.java
``` java
/**
 * Container for all command words, command aliases, and shortened command usage in the application.
 */
public class Commands {
    private static final String[] ALL_COMMAND_WORDS = {
        AddCommand.COMMAND_WORD,
        AddRelationshipCommand.COMMAND_WORD,
        BackupCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        ClearHistoryCommand.COMMAND_WORD,
        ColourTagCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        DeleteRelationshipCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        RelPathCommand.COMMAND_WORD,
        RemarkCommand.COMMAND_WORD,
        RemoveTagCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        SortCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD
    };

    private static final String[] ALL_COMMAND_ALIASES = {
        AddCommand.COMMAND_ALIAS,
        AddRelationshipCommand.COMMAND_ALIAS,
        BackupCommand.COMMAND_ALIAS,
        ClearCommand.COMMAND_ALIAS,
        ClearHistoryCommand.COMMAND_ALIAS,
        ColourTagCommand.COMMAND_ALIAS,
        DeleteCommand.COMMAND_ALIAS,
        DeleteRelationshipCommand.COMMAND_ALIAS,
        EditCommand.COMMAND_ALIAS,
        ExitCommand.COMMAND_ALIAS,
        FindCommand.COMMAND_ALIAS,
        HelpCommand.COMMAND_ALIAS,
        HistoryCommand.COMMAND_ALIAS,
        ListCommand.COMMAND_ALIAS,
        RedoCommand.COMMAND_ALIAS,
        RelPathCommand.COMMAND_ALIAS,
        RemarkCommand.COMMAND_ALIAS,
        SelectCommand.COMMAND_ALIAS,
        SortCommand.COMMAND_ALIAS,
        UndoCommand.COMMAND_ALIAS
    };

    private static final String[] ALL_SHORT_MESSAGE_USAGES = {
        AddCommand.SHORT_MESSAGE_USAGE,
        AddRelationshipCommand.SHORT_MESSAGE_USAGE,
        BackupCommand.SHORT_MESSAGE_USAGE,
        ClearCommand.SHORT_MESSAGE_USAGE,
        ClearHistoryCommand.SHORT_MESSAGE_USAGE,
        ColourTagCommand.SHORT_MESSAGE_USAGE,
        DeleteCommand.SHORT_MESSAGE_USAGE,
        DeleteRelationshipCommand.SHORT_MESSAGE_USAGE,
        EditCommand.SHORT_MESSAGE_USAGE,
        ExitCommand.SHORT_MESSAGE_USAGE,
        FindCommand.SHORT_MESSAGE_USAGE,
        HelpCommand.SHORT_MESSAGE_USAGE,
        HistoryCommand.SHORT_MESSAGE_USAGE,
        ListCommand.SHORT_MESSAGE_USAGE,
        RedoCommand.SHORT_MESSAGE_USAGE,
        RelPathCommand.SHORT_MESSAGE_USAGE,
        RemarkCommand.SHORT_MESSAGE_USAGE,
        RemoveTagCommand.SHORT_MESSAGE_USAGE,
        SelectCommand.SHORT_MESSAGE_USAGE,
        SortCommand.SHORT_MESSAGE_USAGE,
        UndoCommand.SHORT_MESSAGE_USAGE
    };

    private static final HashMap<String, String> ALL_COMMANDS_AND_SHORT_MESSAGES;
    static {
        ALL_COMMANDS_AND_SHORT_MESSAGES = new HashMap<>();

        for (int i = 0; i < ALL_COMMAND_WORDS.length; i++) {
            ALL_COMMANDS_AND_SHORT_MESSAGES.put(ALL_COMMAND_WORDS[i], ALL_SHORT_MESSAGE_USAGES[i]);
        }
    }

    public static String[] getAllCommandWords() {
        return ALL_COMMAND_WORDS;
    }

    public static String[] getAllCommandAliases() {
        return ALL_COMMAND_ALIASES;
    }

    public static HashMap<String, String> getAllCommandUsages() {
        return ALL_COMMANDS_AND_SHORT_MESSAGES;
    }
}
```
###### \java\seedu\address\commons\events\ui\NewGraphDisplayEvent.java
``` java
/**
 * Indicates that a new graph display is available.
 */
public class NewGraphDisplayEvent extends BaseEvent {

    public final String message;
    private final SingleGraph graph;

    public NewGraphDisplayEvent(SingleGraph graph, String message) {
        this.message = message;
        this.graph = graph;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public SingleGraph getGraph() {
        return this.graph;
    }

}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        Predicate<ReadOnlyPerson> predicate;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Prefix> getPrefixInArgs = getPrefixes(trimmedArgs);

        if (getPrefixInArgs.isEmpty()) {
            List<String> arguments = Arrays.asList(trimmedArgs.split(" "));
            List<String> trimmedArguments = prepareArguments(arguments);
            return new FindCommand(new AnyContainsKeywordsPredicate(trimmedArguments));
        }

        if (getPrefixInArgs.size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Prefix targetPrefix = getPrefixInArgs.get(0);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, targetPrefix);

        if (!isAPrefixWithValue(argMultimap, targetPrefix)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (targetPrefix.equals(PREFIX_NAME)) {
            List<String> name = argMultimap.getAllValues(PREFIX_NAME);
            List<String> trimmedName = prepareArguments(name);
            predicate = new NameContainsKeywordsPredicate(trimmedName);
        } else if (targetPrefix.equals(PREFIX_PHONE)) {
            List<String> phone = argMultimap.getAllValues(PREFIX_PHONE);
            List<String> trimmedPhone = prepareArguments(phone);
            predicate = new PhoneContainsKeywordsPredicate(trimmedPhone);
        } else if (targetPrefix.equals(PREFIX_EMAIL)) {
            List<String> email = argMultimap.getAllValues(PREFIX_EMAIL);
            List<String> trimmedEmail = prepareArguments(email);
            predicate = new EmailContainsKeywordsPredicate(trimmedEmail);
        } else if (targetPrefix.equals(PREFIX_ADDRESS)) {
            List<String> address = argMultimap.getAllValues(PREFIX_ADDRESS);
            List<String> trimmedAddress = prepareArguments(address);
            predicate = new AddressContainsKeywordsPredicate(trimmedAddress);
        } else {
            List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);
            List<String> trimmedTagList = prepareArguments(tagList);
            predicate = new TagContainsKeywordsPredicate(trimmedTagList);
        }
        assert(PREFIX_MAPPING.containsValue(targetPrefix));

        return new FindCommand(predicate);
    }

    /**
     * Returns a list of prefixes that can be found in the arguments list.
     * @param args
     */
    private static List<Prefix> getPrefixes(String args) {
        List<Prefix> prefixesInList = new ArrayList<>();
        for (Map.Entry<String, Prefix> entry : PREFIX_MAPPING.entrySet()) {
            if (args.contains(entry.getKey())) {
                prefixesInList.add(entry.getValue());
            }
        }
        return prefixesInList;
    }

    /**
     * Prepares the argument list to be searched by ensuring that each argument is a single word without
     * any leading or ending whitespaces.
     * @param potentialArgumentsList
     */
    private static List<String> prepareArguments(List<String> potentialArgumentsList) {
        List<String> preparedArgumentsList = new ArrayList<>();
        for (String arg : potentialArgumentsList) {
            String[] element = arg.split(" ");
            for (String subElement : element) {
                String trimmedSubElement = subElement.trim();
                if (!trimmedSubElement.isEmpty()) {
                    preparedArgumentsList.add(trimmedSubElement);
                }
            }
        }
        return preparedArgumentsList;
    }

    /**
     * Returns true if the target prefix contains at least one non-empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAPrefixWithValue(ArgumentMultimap argumentMultimap, Prefix prefix) {
        List<String> values = argumentMultimap.getAllValues(prefix);
        boolean hasValue = false;
        for (String v : values) {
            if (!v.isEmpty()) {
                hasValue = true;
            }
        }
        return hasValue;
    }

}
```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * Returns the view attached to the viewer for the graph.
     */
    public View getView() {
        return this.view;
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * Initialise advanced renderer for integrated graph display.
     */
    private void initialiseRenderer() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.addAttribute("ui.quality");
    }

    /**
     * Initialise custom viewer for integrated graph display.
     */
    private void initialiseViewer() {
        this.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        layoutAlgorithm = Layouts.newLayoutAlgorithm();
        viewer.enableAutoLayout(layoutAlgorithm);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        this.view = viewer.addDefaultView(false);
        rebuildNext = true;
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * Style each node in the integrated graph display
     */
    private void styleGraphNode(SingleNode node, String nodeLabel) {
        node.addAttribute(nodeAttributeNodeLabel, nodeLabel);
        node.addAttribute("layout.weight", 3);
        layoutAlgorithm.setStabilizationLimit(0.95);
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * Label edges in the integrated graph display
     */
    private void labelGraphEdge(Relationship relationship, Edge edge) {
        StringBuilder edgeLabel = new StringBuilder();

        String shortRelationshipName = relationship.getName().toString();
        if (relationship.getName().toString().length() > 10) {
            shortRelationshipName = relationship.getName().toString().substring(0, 6) + "...";
        }
        String confidenceEstimate = relationship.getConfidenceEstimate().toString();

        if (shortRelationshipName.length() != 0 || !confidenceEstimate.equals("0.0")) {
            if (!confidenceEstimate.equals("0.0")) {
                edgeLabel.append("(" + confidenceEstimate + ")");
            }

            if (shortRelationshipName.length() != 0) {
                if (!confidenceEstimate.equals("0.0")) {
                    edgeLabel.append(" ");
                }
                edgeLabel.append(shortRelationshipName);
            }

            edge.addAttribute(nodeAttributeNodeLabel, edgeLabel.toString());
        }
        edge.addAttribute("layout.weight", 5);
    }

```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\AnyContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s details matches any of the keywords given.
 */
public class AnyContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AnyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String stringifyTags = Joiner.on(" ").join(person.getTags());

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(stringifyTags, keyword)
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnyContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AnyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\EmailContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> (person.getPhone().value.contains(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autoCompletionBinding = TextFields.bindAutoCompletion(commandTextField, Commands.getAllCommandWords());
        autoCompletionBinding.setVisibleRowCount(3);
        autoCompletionBinding.setMinWidth(100);
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Returns the {@code TextField} in the command box (i.e. the user input).
     */
    protected TextField getCommandTextField() {
        return commandTextField;
    }

```
###### \java\seedu\address\ui\GraphDisplay.java
``` java
/**
 * Integrating GraphStream graph display into the application.
 */
public class GraphDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "GraphDisplay.fxml";

    /**
     * Displays the integrated graph with the desired style.
     *
     * This is a 'hack' since the current GraphStream API requires an absolute file path to the css file,
     * which is risky when the application is converted to a JAR file.
     * Alternatively, we can consider using "ClassLoader.getSystemClassLoader().getResource(".").getPath(‌​);"
     * but this is the most fail-safe method at the current moment.
     */
    private static final String GRAPH_DISPLAY_STYLESHEET =
            "graph { fill-color: white; }"
                    + "node {"
                    + "fill-color: black;"
                    + "shape: rounded-box;"
                    + "text-background-mode: rounded-box;"
                    + "text-padding: 5;"
                    + "text-background-color: black;"
                    + "text-color: white;"
                    + "text-size: 15;"
                    + "size-mode: fit;"
                    + "z-index: 3;}"
                    + "edge { "
                    + "size: 3;"
                    + "fill-color: black; "
                    + "arrow-size: 20, 10;"
                    + "text-alignment: along;"
                    + "text-background-color: white;"
                    + "text-background-mode: rounded-box;"
                    + "text-size: 10;"
                    + "text-padding: 5;"
                    + "z-index: 1;}";

    private final Logic logic;

    @FXML
    private SwingNode graphDisplay;
    @FXML
    private StackPane graphDisplayHolder;

    public GraphDisplay(Logic logic) {
        super(FXML);
        this.logic = logic;

        registerAsAnEventHandler(this);
    }

    public static String getGraphDisplayStylesheet() {
        return GRAPH_DISPLAY_STYLESHEET;
    }

    /**
     * Sets the graph stream display in the SwingNode.
     */
    protected void createAndSetSwingContent() {
        SwingUtilities.invokeLater(() ->
            graphDisplay.setContent((JComponent) GraphWrapper.getInstance().getView()));
    }

    @Subscribe
    private void handleNewGraphIntialisedEvent(NewGraphDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() ->
            graphDisplay.setContent((event.getGraph().display().getDefaultView())));
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        graphDisplay = new GraphDisplay(logic);
        graphDisplayPlaceholder.getChildren().add(graphDisplay.getRoot());
        graphDisplay.createAndSetSwingContent();

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), prefs.getGuiSettings().getTagColours());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay.setLinkedInput(commandBox);
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Sets the default size based on default values.
     */
    private void setWindowDefaultSize() {
        primaryStage.setHeight(DEFAULT_HEIGHT);
        primaryStage.setWidth(DEFAULT_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), prefs.getGuiSettings().getTagColours());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        resultDisplay.setWrapText(true);
        infoDisplay.setWrapText(true);
        registerAsAnEventHandler(this);
    }

    void setLinkedInput(CommandBox commandBox) {
        linkedCommandInput = commandBox.getCommandTextField();
        linkedCommandInput.textProperty().addListener((observable, oldValue, newValue) ->
            updateInfoDisplay(oldValue, newValue));
    }

    /**
     * Updates the information display according to the user input in the command box.
     */
    private void updateInfoDisplay(String oldInput, String newInput) {
        if (lastFoundCommand.isEmpty()
            || (newInput.length() < oldInput.length() && !newInput.contains(lastFoundCommand))
            || (newInput.length() > oldInput.length()) && allCommandUsages.containsKey(newInput)) {
            infoDisplay.setText("");
            lastFoundCommand = "";
            if (allCommandUsages.containsKey(newInput)) {
                infoDisplay.setText("How to use:\n" + allCommandUsages.get(newInput));
                lastFoundCommand = newInput;
            }
        }
    }

```
###### \resources\view\DarkTheme.css
``` css
#tags .friends {
    -fx-background-color: red;
}

#tags .colleagues {
    -fx-background-color: green;
}

#tags .family {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

#tags .neighbours {
    -fx-text-fill: black;
    -fx-background-color: #AED6F1;
}


```
###### \resources\view\GraphDisplay.fxml
``` fxml
<?import javafx.embed.swing.SwingNode?>
<?import javafx.scene.layout.StackPane?>

<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.111">
   <children>
      <SwingNode fx:id="graphDisplay" />
   </children>
</StackPane>
```
###### \resources\view\ResultDisplay.fxml
``` fxml
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.StackPane?>

<StackPane fx:id="placeHolder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <TextArea editable="false" styleClass="result-display" />
    <SplitPane fx:id="splitPane" dividerPositions="0.65" prefHeight="160.0" prefWidth="200.0">
      <items>
         <TextArea fx:id="resultDisplay" editable="false" prefWidth="346.0" styleClass="result-display" />
         <TextArea fx:id="infoDisplay" editable="false" styleClass="info-display" />
      </items>
   </SplitPane>
</StackPane>
```
