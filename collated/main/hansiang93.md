# hansiang93
###### /java/seedu/address/commons/events/ui/WebsiteSelectionRequestEvent.java
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
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " "
            + PREFIX_NAME + "{NAME} "
            + PREFIX_PHONE + "{PHONE} "
            + PREFIX_EMAIL + "{EMAIL} "
            + PREFIX_ADDRESS + "{ADDRESS} "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_WEB_LINK + "WEB LINK]...";
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {index}";
```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {tag}";
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {Index} "
            + PREFIX_PHONE + "{phone} "
            + PREFIX_EMAIL + "{email} "
            + PREFIX_TAG + "{tag} "
            + PREFIX_WEB_LINK + "{weblink}";
```
###### /java/seedu/address/logic/commands/FilterCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with any attributes that "
            + "matches all the keywords entered by user and displays them as a list with index numbers.\n"
            + "Parameters: [keyword]...\n"
            + "Example: " + COMMAND_WORD + " neighbours friends John";

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
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {keyword fragment}";
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " "
            + "{Index} "
            + PREFIX_REMARK + "{Remark}";

```
###### /java/seedu/address/logic/commands/SelectCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {index}";
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {[name/email/phone/address]}";

```
###### /java/seedu/address/logic/commands/UpdateUserCommand.java
``` java
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD
            + " " + PREFIX_NAME + "{NAME} "
            + PREFIX_PHONE + "{PHONE} "
            + PREFIX_EMAIL + "{EMAIL} "
            + PREFIX_ADDRESS + "{ADDRESS} "
            + PREFIX_WEB_LINK + "{WEBLINK} ";

```
###### /java/seedu/address/logic/commands/WebCommand.java
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

    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD
            + " {[facebook|instagram|maps|search|personal]}";

    public static final String MESSAGE_SUCCESS = "WebLink loading...";

    private final String targetWebsite;

    public WebCommand(String targetWebsite) {
        this.targetWebsite = targetWebsite;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!WEBSITES_MAP.containsValue(targetWebsite)) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEBLINK_TAG);
        }
        EventsCenter.getInstance().post(new WebsiteSelectionRequestEvent(targetWebsite));

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WebCommand // instanceof handles nulls
                && targetWebsite.equals(((WebCommand) other).targetWebsite));
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case FilterCommand.COMMAND_WORD:
        case FilterCommand.COMMAND_ALIAS:
            return new FilterCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case WebCommand.COMMAND_WORD:
        case WebCommand.COMMAND_ALIAS:
            return new WebCommandParser().parse(arguments);

```
###### /java/seedu/address/logic/parser/FilterCommandParser.java
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
###### /java/seedu/address/logic/parser/WebCommandParser.java
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
        WEBSITES_MAP.put("twitter", "twitter");
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
###### /java/seedu/address/ui/AutoCompleteSuggestions.java
``` java

/**
 * Handles all command suggestions and their usage examples.
 */
public class AutoCompleteSuggestions {

    private static SuggestionProvider<String> suggestionList = new SuggestionProvider<String>() {
        @Override
        protected Comparator<String> getComparator() {
            return null;
        }

        @Override
        protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
            return (suggestion.startsWith(request.getUserText()) && !suggestion.equals(request.getUserText()));
        }
    };

    static {
        suggestionList.addPossibleSuggestions(AddCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(AddCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(EditCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(EditCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(SelectCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(SelectCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(DeleteCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(DeleteCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(ClearCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(FindCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(FindCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(FilterCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(FilterCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(ListCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(SortCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(SelectCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(SortCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(HistoryCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(ExitCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(HelpCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(UndoCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(RedoCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(DeleteTagCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(DeleteTagCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(RemarkCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(RemarkCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(UpdateUserCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(ShareCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(UpdateUserCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(WebCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(WebCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(ChangeThemeCommand.COMMAND_WORD);
    }

    public static SuggestionProvider<String> getSuggestionList() {
        return suggestionList;
    }
}
```
###### /java/seedu/address/ui/BrowserPanel.java
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

        browser.getEngine().setJavaScriptEnabled(true);
        loadDefaultPage();
        registerAsAnEventHandler(this);
        browser.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            logger.info("Loaded this page: " + browser.getEngine().getLocation());
                        }

                    }
                });
    }


    /**
     * Loads the selected Person's Google search by name page.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
        logger.info("Loading Google search of " + person.getName());
    }

    /**
     * Loads the selected Person's address search via Google Maps search.
     */
    private void loadPersonAddress(ReadOnlyPerson person) {
        loadPage(MAPS_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
        logger.info("Loading Address search of " + person.getName());
    }

    /**
     * Loads the selected Person's others page.
     */
    private void loadPersonPersonal(ReadOnlyPerson selectedPerson) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (webLink.toStringWebLinkTag().equals("others")) {
                loadPage(webLink.toStringWebLink());
                logger.info("Loading Personal Page of " + selectedPerson.getName());
                return;
            }
        });
    }

    /**
     * Loads the selected Person's social page.
     */
    private void loadPersonSocial(ReadOnlyPerson selectedPerson, String websiteRequested) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (websiteRequested.toLowerCase() == webLink.toStringWebLinkTag().trim().toLowerCase()) {
                loadPage(webLink.toStringWebLink());
                logger.info("Loading " + websiteRequested + " page of " + selectedPerson.getName());
                return;
            }
        });
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
        logger.info("Loading Landing Page...");
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
###### /java/seedu/address/ui/CommandBox.java
``` java
    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        TextFields.bindAutoCompletion(commandTextField,
                AutoCompleteSuggestions.getSuggestionList())
                .setMinWidth(450);
    }
```
###### /java/seedu/address/ui/DetailedPersonCard.java
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
    @FXML
    private ImageView phoneicon;
    @FXML
    private ImageView addressicon;
    @FXML
    private ImageView emailicon;

    public DetailedPersonCard(Optional<HashMap<String, String>> tagColors) {
        super(FXML);
        registerAsAnEventHandler(this);
        if (tagColors.isPresent()) {
            this.tagColors = tagColors.get();
        }
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
        if (person.phoneProperty().isNotNull().get()) {
            phoneicon.setVisible(true);
        } else {
            phoneicon.setVisible(false);
        }
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        if (person.addressProperty().isNotNull().get()) {
            addressicon.setVisible(true);
        } else {
            addressicon.setVisible(false);
        }
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        if (person.emailProperty().isNotNull().get()) {
            emailicon.setVisible(true);
        } else {
            emailicon.setVisible(false);
        }
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
            Label webLinkLabel = new Label(webLink.toStringWebLink());
            webLinkLabel.setStyle("-fx-background-color: " + getColorForWeblinks(webLink.toStringWebLinkTag()) + ";"
                                  + "-fx-text-fill: white;");
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
###### /java/seedu/address/ui/PersonCard.java
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
###### /java/seedu/address/ui/WebsiteButtonBar.java
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
        registerAsAnEventHandler(this);
        setEventHandlerForButtonClick();
        buttonBar.getButtons().setAll();
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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson selectedPerson = event.getNewSelection().person;
        ArrayList<Button> buttonList = new ArrayList<>();
        selectedPerson.getWebLinks().forEach(webLink -> {
            Button newbutton = new Button(webLink.toStringWebLinkTag());
            newbutton.setOnMouseClicked(e -> {
                logger.info(webLink.toStringWebLinkTag() + " button clicked");
                raise(new WebsiteSelectionRequestEvent(webLink.toStringWebLinkTag()));
            });
            buttonList.add(newbutton);
        });
        buttonList.add(searchButton);
        if (!selectedPerson.addressProperty().get().toString().equals("-")) {
            buttonList.add(mapsButton);
        }
        buttonBar.getButtons().setAll(buttonList);
    }
}
```
###### /resources/view/DarkTheme.css
``` css
* {
    -fx-base-background-color-0: #181b1d;
    -fx-base-background-color-1: #2e3138;
    -fx-button-pressed-color: white;
    -fx-button-hovor-color: #3a3a3a;
    -fx-base-text-fill-color: white;
    -fx-base-text-fill-color-alt: black;
    -fx-base-text-fill-color-labels: white;
    -fx-base-text-fill-color-weblink-labels: white;
    -fx-label-text-fill-color: #010505;
    -fx-list-cell-even: #3c3e3f;
    -fx-list-cell-odd: #4a4f58;
    -fx-list-cell-selected: #36435f;
    -fx-list-cell-selected-border: #3e7b91;
    -fx-list-cell-empty: #383838;
}

```
###### /resources/view/DarkTheme.css
``` css
/*adapted from https://codepen.io/joshcummingsdesign/pen/qOKaWd*/
body {
    background-color: #272727;
    padding: 30px;
}

.fakeButtons {
    height: 10px;
    width: 10px;
    border-radius: 50%;
    border: 1px 1px 1px 1px;
    position: relative;
    top: 6px;
    left: 6px;
    background-color: #ff3b47;
    border-color: #9d252b;
    display: inline-block;
}

.fakeMinimize {
    left: 11px;
    background-color: #ffc100;
    border-color: #9d802c;
}

.fakeZoom {
    left: 16px;
    background-color: #00d742;
    border-color: #049931;
}

.fakeMenu {
    width: 1000px;
    box-sizing: border-box;
    height: 25px;
    background-color: #bbb;
    margin: 0 0 0 0;
    border-top-right-radius: 5px;
    border-top-left-radius: 5px;
}

.fakeScreen {
    background-color: #151515;
    box-sizing: border-box;
    width: 1000px;
    margin: 0 0 0 0;
    padding: 45px;
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
}

p {
    position: relative;
    left: 20%;
    margin-left: -8.5em;
    text-align: left;
    font-size: 1.25em;
    font-family: monospace;
    white-space: nowrap;
    overflow: hidden;
    width: 0;
}

span {
    color: #fff;
    font-weight: bold;
}

.line1 {
    color: #CDEE69;
    -webkit-animation: type .5s 1s steps(40, end) forwards;
    -moz-animation: type .5s 1s steps(40, end) forwards;
    -o-animation: type .5s 1s steps(40, end) forwards;
    animation: type .5s 1s steps(40, end) forwards;
}

.cursor1 {
    -webkit-animation: blink 1s 2s 2 forwards;
    -moz-animation: blink 1s 2s 2 forwards;
    -o-animation: blink 1s 2s 2 forwards;
    animation: blink 1s 2s 2 forwards;
}

.line2 {
    color: #CDEE69;
    -webkit-animation: type .5s 4.25s steps(20, end) forwards;
    -moz-animation: type .5s 4.25s steps(20, end) forwards;
    -o-animation: type .5s 4.25s steps(20, end) forwards;
    animation: type .5s 4.25s steps(20, end) forwards;
}

.cursor2 {
    -webkit-animation: blink 1s 5.25s 2 forwards;
    -moz-animation: blink 1s 5.25s 2 forwards;
    -o-animation: blink 1s 5.25s 2 forwards;
    animation: blink 1s 5.25s 2 forwards;
}

.line3 {
    color: #CDEE69;
    -webkit-animation: type .5s 7.5s steps(20, end) forwards;
    -moz-animation: type .5s 7.5s steps(20, end) forwards;
    -o-animation: type .5s 7.5s steps(20, end) forwards;
    animation: type .5s 7.5s steps(20, end) forwards;
}

.cursor3 {
    -webkit-animation: blink 1s 8.5s 2 forwards;
    -moz-animation: blink 1s 8.5s 2 forwards;
    -o-animation: blink 1s 8.5s 2 forwards;
    animation: blink 1s 8.5s 2 forwards;
}

.line4 {
    color: #CDEE69;
    -webkit-animation: type .5s 10.75s steps(20, end) forwards;
    -moz-animation: type .5s 10.75s steps(20, end) forwards;
    -o-animation: type .5s 10.75s steps(20, end) forwards;
    animation: type .5s 10.75s steps(20, end) forwards;
}

.cursor4 {
    -webkit-animation: blink 1s 11.75s forwards;
    -moz-animation: blink 1s 11.75s forwards;
    -o-animation: blink 1s 11.75s forwards;
    animation: blink 1s 11.75s forwards;
}

.line5 {
    color: #fff;
    -webkit-animation: type .5s 15s steps(20, end) forwards;
    -moz-animation: type .5s 15s steps(20, end) forwards;
    -o-animation: type .5s 15s steps(20, end) forwards;
    animation: type .5s 15s steps(20, end) forwards;
}

.cursor5 {
    -webkit-animation: blink 1s 16s infinite;
    -moz-animation: blink 1s 16s infinite;
    -o-animation: blink 1s 16s infinite;
    animation: blink 1s 16s infinite;
}

@-webkit-keyframes blink {
    0% {
        opacity: 0;
    }
    40% {
        opacity: 0;
    }
    50% {
        opacity: 1;
    }
    90% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
}

@-moz-keyframes blink {
    0% {
        opacity: 0;
    }
    40% {
        opacity: 0;
    }
    50% {
        opacity: 1;
    }
    90% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
}

@-o-keyframes blink {
    0% {
        opacity: 0;
    }
    40% {
        opacity: 0;
    }
    50% {
        opacity: 1;
    }
    90% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
}

@keyframes blink {
    0% {
        opacity: 0;
    }
    40% {
        opacity: 0;
    }
    50% {
        opacity: 1;
    }
    90% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
}

@-webkit-keyframes type {
    to {
        width: 50em;
    }
}

@-moz-keyframes type {
    to {
        width: 50em;
    }
}

@-o-keyframes type {
    to {
        width: 50em;
    }
}

@keyframes type {
    to {
        width: 50em;
    }
}
/*adapted from https://codepen.io/joshcummingsdesign/pen/qOKaWd*/
```
###### /resources/view/DarkTheme2.css
``` css
* {
    -fx-base-background-color-0: #0b0c0d;
    -fx-base-background-color-1: #45484B;
    -fx-button-pressed-color: white;
    -fx-button-hovor-color: #3a3a3a;
    -fx-base-text-fill-color: white;
    -fx-base-text-fill-color-alt: black;
    -fx-base-text-fill-color-labels: white;
    -fx-base-text-fill-color-weblink-labels: white;
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
    -fx-image: url("../images/ic_phone_white_24dp.png");
}

#addressicon {
    -fx-image: url("../images/ic_home_white_24dp.png");
}

#emailicon {
    -fx-image: url("../images/ic_email_white_24dp.png");
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
###### /resources/view/DetailedPersonListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
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
         <FlowPane>
            <children>
               <ImageView id="phoneicon" fx:id="phoneicon" fitHeight="17.0" fitWidth="17.0" pickOnBounds="true" preserveRatio="true" visible="false">
                  <FlowPane.margin>
                     <Insets bottom="1.0" left="5.0" right="5.0" top="1.0" />
                  </FlowPane.margin>
               </ImageView>
            <Label fx:id="phone" styleClass="cell_small_label" />
            </children>
         </FlowPane>
         <FlowPane>
            <children>
               <ImageView id="addressicon" fx:id="addressicon" fitHeight="17.0" fitWidth="17.0" pickOnBounds="true" preserveRatio="true" visible="false">
                  <FlowPane.margin>
                     <Insets bottom="1.0" left="5.0" right="5.0" top="1.0" />
                  </FlowPane.margin></ImageView>
            <Label fx:id="address" styleClass="cell_small_label" />
            </children>
         </FlowPane>
         <FlowPane>
            <children>
               <ImageView id="emailicon" fx:id="emailicon" fitHeight="17.0" fitWidth="17.0" pickOnBounds="true" preserveRatio="true" visible="false">
                  <FlowPane.margin>
                     <Insets bottom="1.0" left="5.0" right="5.0" top="1.0" />
                  </FlowPane.margin></ImageView>
            <Label fx:id="email" styleClass="cell_small_label" />
            </children>
         </FlowPane>
         <FlowPane>
            <children>
            <Label fx:id="remark" styleClass="cell_small_label" />
            </children>
         </FlowPane>
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
###### /resources/view/LightTheme2.css
``` css
* {
    -fx-base-background-color-0: #69D2E7;
    -fx-base-background-color-1: #A7DBD8;
    -fx-button-pressed-color: #7a7a7a;
    -fx-button-hovor-color: #3a3a3a;
    -fx-base-text-fill-color: black;
    -fx-base-text-fill-color-alt: white;
    -fx-base-text-fill-color-labels: white;
    -fx-base-text-fill-color-weblink-labels: black;
    -fx-label-text-fill-color: #010505;
    -fx-list-cell-even: #E0E4CC;
    -fx-list-cell-odd: #A7DBD8;
    -fx-list-cell-selected: #81a9a7;
    -fx-list-cell-selected-border: #3e7b91;
    -fx-list-cell-empty: #E0E4CC;
}

```
###### /resources/view/MainWindow.fxml
``` fxml
    <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
        <VBox fx:id="personList" maxWidth="400.0" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
            <padding>
                <Insets bottom="10" left="10" right="10" top="10"/>
            </padding>
            <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
        </VBox>
        <SplitPane fx:id="rightViewSplitPane" dividerPositions="0.5" orientation="VERTICAL" prefHeight="200.0"
                   prefWidth="160.0">
            <items>
                <AnchorPane maxHeight="150.0" minHeight="150.0" minWidth="0.0" prefHeight="150.0" prefWidth="160.0">
                    <SplitPane dividerPositions="0.5" layoutX="5.0" layoutY="5.0" AnchorPane.bottomAnchor="5.0"
                               AnchorPane.leftAnchor="5.0" AnchorPane.rightAnchor="5.0" AnchorPane.topAnchor="5.0">
                        <items>
                            <StackPane fx:id="detailedPersonCardPlaceholder">
                                <padding>
                                    <Insets bottom="5.0" left="5.0" right="5.0" top="5.0"/>
                                </padding>
                            </StackPane>
                            <StackPane fx:id="websiteButtonbarPlaceholder">
                                <padding>
                                    <Insets bottom="10" left="10" right="10" top="10"/>
                                </padding>
                            </StackPane>
                        </items>
                    </SplitPane>
                </AnchorPane>
                <SplitPane dividerPositions="0.5" orientation="VERTICAL" prefHeight="200.0" prefWidth="160.0">
                    <items>
                        <AnchorPane minHeight="0.0" minWidth="0.0" prefWidth="160.0">
                            <children>

                                <StackPane fx:id="browserPlaceholder" prefHeight="163.0" prefWidth="199.0"
                                           AnchorPane.bottomAnchor="5.0" AnchorPane.leftAnchor="5.0"
                                           AnchorPane.rightAnchor="5.0" AnchorPane.topAnchor="5.0">
                                    <padding>
                                        <Insets bottom="10" left="10" right="10" top="10"/>
                                    </padding>
                                </StackPane>
                            </children>
                        </AnchorPane>
                        <SplitPane dividerPositions="0.5" maxHeight="170.0" orientation="VERTICAL">
                            <items>
                                <AnchorPane maxHeight="100.0">
                                    <children>

                                        <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100"
                                                   prefHeight="100" styleClass="pane-with-border"
                                                   AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0"
                                                   AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                                            <padding>
                                                <Insets bottom="5" left="10" right="10" top="5"/>
                                            </padding>
                                        </StackPane>
                                    </children>
                                </AnchorPane>
                                <AnchorPane maxHeight="70.0">
                                    <children>

                                        <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border"
                                                   AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0"
                                                   AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                                            <padding>
                                                <Insets bottom="5" left="10" right="10" top="5"/>
                                            </padding>
                                        </StackPane>
                                    </children>
                                </AnchorPane>
                            </items>
                        </SplitPane>
                    </items>
                </SplitPane>
            </items>
        </SplitPane>
    </SplitPane>

    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER"/>
</VBox>
```
###### /resources/view/WebsiteButtonbar.fxml
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
