# hansiang93
###### \java\seedu\address\commons\events\ui\WebsiteSelectionRequestEvent.java
``` java
/**
 * Represents a selection change in the Person List Panel
 */
public class WebsiteSelectionRequestEvent extends BaseEvent {


    private final String websiteRequested;

    public WebsiteSelectionRequestEvent(String websiteRequested) {
        this.websiteRequested = websiteRequested;
    }

    public String getWebsiteRequested() {
        return websiteRequested;
    }

    @Override
    public String toString() {
        return this.websiteRequested;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " "
            + PREFIX_NAME + "{NAME} "
            + PREFIX_PHONE + "{PHONE} "
            + PREFIX_EMAIL + "{EMAIL} "
            + PREFIX_ADDRESS + "{ADDRESS} "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_WEB_LINK + "WEB LINK]...";
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {index}";
```
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {tag}";
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {Index} "
            + PREFIX_PHONE + "{phone} "
            + PREFIX_EMAIL + "{email} "
            + PREFIX_TAG + "{tag} "
            + PREFIX_WEB_LINK + "{weblink}";
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are tagged with "
            + "the specified tag (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " neighbours friends";

    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {keyword}";

    private final FilterKeywordsPredicate predicate;

    public FilterCommand(FilterKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {keyword fragment}";
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " "
            + "{Index} "
            + PREFIX_REMARK + "{Remark}";

```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {index}";
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {[name/email/phone/address/tag]}";

```
###### \java\seedu\address\logic\commands\UpdateUserCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD
            + " " + PREFIX_NAME + "{NAME} "
            + PREFIX_PHONE + "{PHONE} "
            + PREFIX_EMAIL + "{EMAIL} "
            + PREFIX_ADDRESS + "{ADDRESS} "
            + PREFIX_WEB_LINK + "{WEBLINK} ";

```
###### \java\seedu\address\logic\commands\WebCommand.java
``` java
/**
 * Lists all persons in the address book to the user.
 */
public class WebCommand extends Command {

    public static final String COMMAND_WORD = "web";
    public static final String COMMAND_ALIAS = "w";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the social links of the selected person in the web view on the right.\n"
            + "Parameters: 'facebook' OR 'instagram' OR 'maps' OR 'search' OR 'linkedin' OR 'personal'\n"
            + "Example: " + COMMAND_WORD + " facebook";

```
###### \java\seedu\address\logic\commands\WebCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD
            + " {[facebook|instagram|linkedin|maps|search|personal]}";
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FilterCommand.COMMAND_WORD:
        case FilterCommand.COMMAND_ALIAS:
            return new FilterCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case WebCommand.COMMAND_WORD:
        case WebCommand.COMMAND_ALIAS:
            return new WebCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FilterCommand(new FilterKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\WebCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class WebCommandParser implements Parser<WebCommand> {
    public static final HashMap<String, String> WEBSITES_MAP;

    static {
        WEBSITES_MAP = new HashMap<String, String>();
        WEBSITES_MAP.put("facebook", "facebook");
        WEBSITES_MAP.put("maps", "mapsView");
        WEBSITES_MAP.put("search", "searchView");
        WEBSITES_MAP.put("instagram", "instagram");
        WEBSITES_MAP.put("linkedin", "linkedin");
        WEBSITES_MAP.put("personal", "others");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public WebCommand parse(String args) throws ParseException {

        try {
            String websiteName = ParserUtil.parseWebname(args).trim().toLowerCase();
            String websiteToShow = WEBSITES_MAP.get(websiteName);
            if (websiteToShow == null) {
                throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            return new WebCommand(websiteToShow);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WebCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\ui\AutoCompleteSuggestions.java
``` java
/**
 * Handles all command suggestions and their usage examples.
 */
public class AutoCompleteSuggestions {
    private static ArrayList<String> suggestionList = new ArrayList<>();

    static {
        suggestionList.add(AddCommand.COMMAND_WORD);
        suggestionList.add(AddCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(EditCommand.COMMAND_WORD);
        suggestionList.add(EditCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(SelectCommand.COMMAND_WORD);
        suggestionList.add(SelectCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(DeleteCommand.COMMAND_WORD);
        suggestionList.add(DeleteCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(ClearCommand.COMMAND_WORD);
        suggestionList.add(FindCommand.COMMAND_WORD);
        suggestionList.add(FindCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(FilterCommand.COMMAND_WORD);
        suggestionList.add(FilterCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(ListCommand.COMMAND_WORD);
        suggestionList.add(SortCommand.COMMAND_WORD);
        suggestionList.add(SortCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(HistoryCommand.COMMAND_WORD);
        suggestionList.add(ExitCommand.COMMAND_WORD);
        suggestionList.add(HelpCommand.COMMAND_WORD);
        suggestionList.add(UndoCommand.COMMAND_WORD);
        suggestionList.add(RedoCommand.COMMAND_WORD);
        suggestionList.add(DeleteTagCommand.COMMAND_WORD);
        suggestionList.add(DeleteTagCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(RemarkCommand.COMMAND_WORD);
        suggestionList.add(RemarkCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(UpdateUserCommand.COMMAND_WORD);
        suggestionList.add(UpdateUserCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(WebCommand.COMMAND_WORD);
        suggestionList.add(WebCommand.MESSAGE_USAGE_EXAMPLE);
    }

    public static ArrayList<String> getSuggestionList() {
        return suggestionList;
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String MAPS_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/search/";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson selectedPerson;

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }


    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
        logger.info("Loading Google search of " + person.getName());
    }

    private void loadPersonAddress(ReadOnlyPerson person) {
        loadPage(MAPS_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
    }

    /**
     * Loads the selected Person's others page.
     */
    private void loadPersonPersonal(ReadOnlyPerson selectedPerson) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (webLink.webLinkTag.equals("others")) {
                loadPage(webLink.webLinkInput);
                return;
            }
        });
        logger.info("Loading Personal Page of " + selectedPerson.getName());
    }

    /**
     * Loads the selected Person's social page.
     */
    private void loadPersonSocial(ReadOnlyPerson selectedPerson, String websiteRequested) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (websiteRequested.toLowerCase() == webLink.webLinkTag.trim().toLowerCase()) {
                loadPage(webLink.webLinkInput);
                return;
            }
        });
        logger.info("Loading " + websiteRequested + "Page of " + selectedPerson.getName());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPerson = event.getNewSelection().person;
        loadPersonPage(selectedPerson);
    }

    /**
     * Called when the user clicks on the button bar buttons or via the "web" command.
     */
    @Subscribe
    private void handleWebsiteSelectionEvent(WebsiteSelectionRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getWebsiteRequested()) {
        case "mapsView":
            loadPersonAddress(selectedPerson);
            break;
        case "searchView":
            loadPersonPage(selectedPerson);
            break;
        case "othersView":
            loadPersonPersonal(selectedPerson);
            break;
        default:
            loadPersonSocial(selectedPerson, event.getWebsiteRequested());
            break;
        }
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
        historySnapshot = logic.getHistorySnapshot();
        ArrayList<String> suggestions = AutoCompleteSuggestions.getSuggestionList();
        TextFields.bindAutoCompletion(commandTextField, suggestions);
    }
```
###### \java\seedu\address\ui\DetailedPersonCard.java
``` java
/**
 * An UI component that displays information of a {@code Person}.
 */
public class DetailedPersonCard extends UiPart<Region> {

    private static final String FXML = "DetailedPersonListCard.fxml";
    private static String[] colors = {"darkblue", "darkolivegreen", "slategray ", "teal", "maroon", "darkslateblue"};
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static HashMap<String, String> webLinkColors = new HashMap<>();

    static {
        webLinkColors = PersonCard.getWebLinkColors();
    }

    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson selectedPerson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane webLinks;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    public DetailedPersonCard(HashMap<String, String> tagColors) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.tagColors = tagColors;
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    private static String getColorForWeblinks(String webLinkTag) {
        return webLinkColors.get(webLinkTag);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.webLinkProperty().addListener((observable, oldValue, newValue) -> {
            webLinks.getChildren().clear();
            initWebLinks(person);
        });
    }

    /**
     * Initialize person's tag and add appropriate background color to it
     *
     * @param person name
     */
    private void initTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initialize person's webLink and add appropriate background color to it
     *
     * @param person name
     */
    private void initWebLinks(ReadOnlyPerson person) {
        webLinks.getChildren().clear();
        person.getWebLinks().forEach(webLink -> {
            Label webLinkLabel = new Label(webLink.webLinkInput);
            webLinkLabel.setStyle("-fx-background-color: " + getColorForWeblinks(webLink.toStringWebLinkTag()));
            webLinks.getChildren().add(webLinkLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailedPersonCard)) {
            return false;
        }

        // state check
        DetailedPersonCard card = (DetailedPersonCard) other;
        return id.getText().equals(card.id.getText())
                && selectedPerson.equals(card.selectedPerson);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPerson = event.getNewSelection().person;
        initTags(selectedPerson);
        initWebLinks(selectedPerson);
        bindListeners(selectedPerson);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = {"darkblue", "darkolivegreen", "slategray ", "teal", "maroon", "darkslateblue"};
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static HashMap<String, String> webLinkColors = new HashMap<>();

    static {
        webLinkColors.put("facebook", "#3b5998");
        webLinkColors.put("twitter", "#00aced");
        webLinkColors.put("linkedin", "#0077b5");
        webLinkColors.put("instagram", "#8a3ab9");
        webLinkColors.put("others", "grey");
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initWebLinks(person);
        bindListeners(person);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    private static String getColorForWeblinks(String webLinkTag) {
        return webLinkColors.get(webLinkTag);
    }
```
###### \java\seedu\address\ui\WebsiteButtonBar.java
``` java
/**
 * The Button Bar above the browser of the App.
 */
public class WebsiteButtonBar extends UiPart<Region> {

    private static final String FXML = "WebsiteButtonbar.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Button mapsButton;

    @FXML
    private Button searchButton;

    public WebsiteButtonBar() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        setEventHandlerForButtonClick();
    }

    private void setEventHandlerForButtonClick() {
        mapsButton.setOnMouseClicked(e -> {
            logger.info("Maps button clicked");
            raise(new WebsiteSelectionRequestEvent("mapsView"));
        });
        searchButton.setOnMouseClicked(e -> {
            logger.info("Search button clicked");
            raise(new WebsiteSelectionRequestEvent("searchView"));
        });
    }

    // To add dynamically added buttons in the future
}
```
###### \resources\view\DarkTheme.css
``` css
* {
    -fx-base-background-color-0: #181b1d;
    -fx-base-background-color-1: #2e3138;
    -fx-base-text-fill-color: white;
    -fx-base-text-fill-color-alt: black;
    -fx-base-text-fill-color-labels: white;
    -fx-label-text-fill-color: #010505;
    -fx-list-cell-even: #3c3e3f;
    -fx-list-cell-odd: #4a4f58;
    -fx-list-cell-selected: #36435f;
    -fx-list-cell-selected-border: #3e7b91;
    -fx-list-cell-empty: #383838;
}
```
###### \resources\view\DarkTheme2.css
``` css
* {
    -fx-base-background-color-0: #0b0c0d;
    -fx-base-background-color-1: #45484B;
    -fx-base-text-fill-color: white;
    -fx-base-text-fill-color-alt: black;
    -fx-base-text-fill-color-labels: white;
    -fx-label-text-fill-color: #010505;
    -fx-list-cell-even: #676556;
    -fx-list-cell-odd: #524e3b;
    -fx-list-cell-selected: #a09b73;
    -fx-list-cell-selected-border: #a29a8e;
    -fx-list-cell-empty: #36393b;
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
    -fx-background-color: -fx-base-background-color-0;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 8pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: -fx-base-background-color-0;
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
    -fx-background-color: -fx-base-background-color-0;
    -fx-text-fill: -fx-base-text-fill-color;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
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
    -fx-text-fill: -fx-base-text-fill-color-labels;
    -fx-background-color: transparent;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 4;
    -fx-border-width: 3;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### \resources\view\DetailedPersonListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="detailedCardPane" fx:id="detailedCardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <Label fx:id="name" styleClass="cell_big_label" text="Select a Person" />
      <FlowPane fx:id="tags" />
      <FlowPane fx:id="webLinks" />
      <Label fx:id="phone" styleClass="cell_small_label" />
      <Label fx:id="address" styleClass="cell_small_label" />
      <Label fx:id="email" styleClass="cell_small_label" />
      <Label fx:id="remark" styleClass="cell_small_label" />
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
###### \resources\view\LightTheme2.css
``` css
* {
    -fx-base-background-color-0: #69D2E7;
    -fx-base-background-color-1: #A7DBD8;
    -fx-base-text-fill-color: black;
    -fx-base-text-fill-color-alt: white;
    -fx-base-text-fill-color-labels: white;
    -fx-label-text-fill-color: #010505;
    -fx-list-cell-even: #E0E4CC;
    -fx-list-cell-odd: #A7DBD8;
    -fx-list-cell-selected: #81a9a7;
    -fx-list-cell-selected-border: #3e7b91;
    -fx-list-cell-empty: #E0E4CC;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
    <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
        <VBox fx:id="personList" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
            <padding>
                <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
            <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
        </VBox>
        <SplitPane fx:id="rightViewSplitPane" dividerPositions="0.5" orientation="VERTICAL" prefHeight="200.0" prefWidth="160.0">
            <items>
                <AnchorPane maxHeight="150.0" minHeight="150.0" minWidth="0.0" prefHeight="150.0" prefWidth="160.0">
               <SplitPane dividerPositions="0.5" layoutX="5.0" layoutY="5.0" AnchorPane.bottomAnchor="5.0" AnchorPane.leftAnchor="5.0" AnchorPane.rightAnchor="5.0" AnchorPane.topAnchor="5.0">
                  <items>
                     <StackPane fx:id="detailedPersonCardPlaceholder">
                        <padding>
                           <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                        </padding>
                     </StackPane>
                          <StackPane fx:id="websiteButtonbarPlaceholder">
                              <padding>
                                  <Insets bottom="10" left="10" right="10" top="10" />
                              </padding>
                          </StackPane>
                  </items>
               </SplitPane>
                </AnchorPane>
                <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="100.0" prefWidth="160.0">
                    <children>

                        <StackPane fx:id="browserPlaceholder" prefHeight="163.0" prefWidth="199.0" AnchorPane.bottomAnchor="5.0" AnchorPane.leftAnchor="5.0" AnchorPane.rightAnchor="5.0" AnchorPane.topAnchor="5.0">
                            <padding>
                                <Insets bottom="10" left="10" right="10" top="10" />
                            </padding>
                        </StackPane>
                    </children>
                </AnchorPane>
            </items>
        </SplitPane>
    </SplitPane>

    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\WebsiteButtonbar.fxml
``` fxml
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ButtonBar?>

<ButtonBar fx:id="buttonBar" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.111">
  <buttons>
    <Button fx:id="mapsButton" mnemonicParsing="false" text="Maps" />
    <Button fx:id="searchButton" mnemonicParsing="false" text="Search" />
  </buttons>
</ButtonBar>
```
