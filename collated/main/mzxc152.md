# mzxc152
###### \java\seedu\address\logic\commands\SortCommand.java
``` java

/**
 * Sorts all persons in Contags to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "List has been sorted.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list by name, phone, email, address or tag.\n"
            + "Parameters: KEYWORD\n"
            + "Example for email sort: " + COMMAND_WORD + " email";

    public final String toSort;

    public SortCommand(String toSort) {
        this.toSort = toSort;
    }

    @Override
    public CommandResult execute() {
        model.sortList(toSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java

/**
 * Parses input arguments to create a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of SortCommand
     * and returns a SortCommand object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty() || !trimmedArgs.equals("name") && !trimmedArgs.equals("phone")
                && !trimmedArgs.equals("email") && !trimmedArgs.equals("address") && !trimmedArgs.equals("tag")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the list in alphabetical order.
     */
    public void sortList(String toSort) {
        persons.sort(toSort);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Sorts Contags in alphabetical order. */
    void sortList(String toSort);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Sorts the list in alphabetical order.
     * @param toSort
     */
    @Override
    public void sortList(String toSort) {
        addressBook.sortList(toSort);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the list in alphabetical order.
     */
    public void sort(String toSort) {
        switch (toSort) {
        case "name":
            internalList.sort((p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString()));
            break;
        case "phone":
            internalList.sort((p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));
            break;
        case "email":
            internalList.sort((p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString()));
            break;
        case "address":
            internalList.sort((p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString()));
            break;
        case "tag":
            internalList.sort((p1, p2) -> p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString()));
            break;
        default:
            break;
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private TextField findField;

    @FXML
    private ComboBox comboBox;

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic)
            throws CommandException, ParseException {
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Initializes the find field.
     *
     * @param logic
     */
    private void initFindField(Logic logic) {
        findField.setOnKeyReleased(e -> {
            try {
                if (findField.getText().isEmpty()) {
                    logic.execute("list");
                }
                logic.execute("find " + findField.getText());
            } catch (CommandException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Initializes the sort box.
     */
    private void initSortBox() throws CommandException, ParseException {
        comboBox.getItems().addAll("Name", "Phone", "Email", "Address", "Tag");
        comboBox.getSelectionModel().select(0);
        logic.execute("sort name");
        comboBox.setOnAction(e -> {
            try {
                logic.execute("sort " + comboBox.getValue().toString());
            } catch (CommandException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static ArrayList<String> colors = new
            ArrayList<String>(Arrays.asList("salmon", "mediumspringgreen", "tan", "royalblue",
            "maroon", "seagreen", "rosybrown", "pink", "black", "red", "beige"));
    private static HashMap<String, String> tagColors = new HashMap<String, String>();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

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
    private Label birthday;
    @FXML
    private Label remark;
    @FXML
    private Label social;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    private static String getColorForTag(String tagName) {
        if (!tagColors.containsKey(tagName) && !colors.isEmpty()) {
            String color = colors.get(0);
            tagColors.put(tagName, color);
            colors.add(color);
            colors.remove(0);
        }
        return tagColors.get(tagName);
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
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        social.textProperty().bind(Bindings.convert(person.socialProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Initialize tag colour for each tag
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### \resources\view\DarkTheme.css
``` css
#comboBox .list-cell {
    -fx-text-fill: white;
}

#comboBox .list-view .list-cell {
    -fx-text-fill: white;
}

#comboBox .cell {
    -fx-background-color: derive(#383838, 45%);
}
```
###### \resources\view\MainWindow.fxml
``` fxml
  <StackPane prefHeight="42.0" prefWidth="690.0" styleClass="pane-with-border">
    <children>
      <Label style="-fx-font-size: 14; -fx-text-fill: white;" text="Sort By:" StackPane.alignment="CENTER_RIGHT">
        <StackPane.margin>
          <Insets right="140.0" />
        </StackPane.margin>
      </Label>
      <TextField fx:id="findField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" opacity="0.7" prefHeight="31.0" prefWidth="295.0" promptText="Find ..." style="-fx-background-color: derive(#383838, 45%);" styleClass="pane-with-border" StackPane.alignment="CENTER_LEFT">
        <StackPane.margin>
          <Insets left="15.0" />
        </StackPane.margin>
      </TextField>
      <ComboBox fx:id="comboBox" opacity="0.7" prefHeight="27.0" prefWidth="120.0" StackPane.alignment="CENTER_RIGHT">
        <StackPane.margin>
          <Insets right="10.0" />
        </StackPane.margin>
      </ComboBox>
    </children>
  </StackPane>

```
