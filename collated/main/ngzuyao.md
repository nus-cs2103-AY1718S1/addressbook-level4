# ngzuyao
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        /* Commented out to remove address, email and tags from Person Card
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });*/
        initPhoto(person);
    }
```
###### \java\seedu\address\ui\PersonInformationPanel.java
``` java
/**
 * The person information panel of the app.
 */
public class PersonInformationPanel extends UiPart<Region> {

    private static final String FXML = "PersonInformationPanel.fxml";
    private static String[] colors = {"red", "blue", "orange", "brown", "green", "black", "grey"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private static final int MIN_HEIGHT = 40;
    private static final int MIN_WIDTH = 160;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson person;

    @FXML
    private VBox informationPane;
    @FXML
    private VBox optionalPhoneList;
    @FXML
    private VBox customFieldNameList;
    @FXML
    private VBox customFieldValueList;
    @FXML
    private FlowPane tags;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private VBox optionalPhoneLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label address;
    @FXML
    private Label addressLabel;
    @FXML
    private Label email;
    @FXML
    private Label emailLabel;

    public PersonInformationPanel() {
        super(FXML);
        loadDefaultScreen();
        registerAsAnEventHandler(this);

        //Configure UI
        setLabelIndentation();
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    private void loadDefaultScreen() {
        this.person = null; }

    /**
     * binds the person's information to that of the person card.
     * @param person
     * @param personid
     */
    private void bindListeners(ReadOnlyPerson person, int personid) {
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags();
        });
    }

    /**
     * loads the selected person's information to be displayed.
     * @param person
     * @param personId
     */
    private void loadPersonInformation(ReadOnlyPerson person, int personId) {
        this.person = person;
        tags.getChildren().clear();
        initTags();
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        id.setText(Integer.toString(personId));
        optionalPhoneList.getChildren().clear();
        initOptionalPhones(person);
        initCustomField(person);
    }

    /**
     * Sets a background color for each tag.
     * @param
     */
    private void initTags() {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initialise optional phone display flowpane
     */
    public void initOptionalPhones(ReadOnlyPerson person) {
        optionalPhoneLabel.getChildren().clear();
        person.getPhoneList().forEach(optionalPhone -> {
            Label additionalLabel = new Label("Other Phones: ");
            setIndentation(additionalLabel);
            optionalPhoneLabel.getChildren().add(additionalLabel);
            Label otherPhone = new Label(optionalPhone.value);
            setIndentation(otherPhone);
            optionalPhoneList.getChildren().add(otherPhone);
        });
    }
```
###### \java\seedu\address\ui\PersonInformationPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInformation(event.getNewSelection().person, event.getNewSelection().stringid);
        bindListeners(event.getNewSelection().person, event.getNewSelection().stringid);
    }

    private void setLabelIndentation() {
        phoneLabel.setMinHeight(MIN_HEIGHT);
        phoneLabel.setMinWidth(MIN_WIDTH);
        phone.setMinHeight(MIN_HEIGHT);
        addressLabel.setMinHeight(MIN_HEIGHT);
        addressLabel.setMinWidth(MIN_WIDTH);
        address.setMinHeight(MIN_HEIGHT);
        emailLabel.setMinHeight(MIN_HEIGHT);
        emailLabel.setMinWidth(MIN_WIDTH);
        email.setMinHeight(MIN_HEIGHT);
        //customFieldsLabel.setMinWidth(MIN_WIDTH);
        //customFieldsLabel.setMinHeight(MIN_HEIGHT);
    }

    private void setIndentation(Label label) {
        label.setMinWidth(MIN_WIDTH);
        label.setMinHeight(MIN_HEIGHT);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInformationPanel)) {
            return false;
        }

        // state check
        PersonInformationPanel card = (PersonInformationPanel) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
