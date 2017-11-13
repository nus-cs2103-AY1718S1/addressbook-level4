# teclu
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request for theme change.
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeRequestEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }

}
```
###### \java\seedu\address\commons\util\AvatarUtil.java
``` java
/**
 * A class to draw the default placeholder avatar.
 */
public class AvatarUtil {
    private static final BufferedImage placeholder =
            new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_INDEXED);
    private static final Color gray = new Color(200, 200, 200);
    private static final Color darkGray = new Color(177, 177, 177);
    private static final Color lightGray = new Color(244, 244, 244);

    public AvatarUtil() {
        drawPlaceholderAvatar();
    }

    /**
     * This method draws the default placeholder avatar.
     */
    private void drawPlaceholderAvatar() {
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(lightGray); // Backdrop
        g2d.fillRect(1, 1, 198, 198);

        g2d.setColor(gray); // Body
        g2d.fillOval(25, 135, 155, 50);
        g2d.fillRect(25, 163, 155, 50);

        g2d.setColor(gray); // Head
        g2d.fillOval(55, 35, 95, 95);

        g2d.setColor(Color.white); // Border Frame
        g2d.drawRect(0, 0, 199, 199);
        g2d.dispose();
    }

    public BufferedImage getPlaceholderAvatar() {
        return placeholder;
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Changes the theme of the Address Book.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of the address book.\n"
            + "Parameters: THEME (must be either Light, Dark, Red, Blue or Green; case-insensitive)\n"
            + "Examples: theme Light, theme dark";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated to: %1$s";

    private final String theme;

    public ThemeCommand(String theme) {
        this.theme = formatThemeString(theme);
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!isValidTheme(this.theme)) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME);
        }
        if ((MainWindow.getCurrentTheme()).contains(this.theme)) {
            throw new CommandException("Theme is already set to " + this.theme + "!");
        }
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(this.theme));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, this.theme));
    }

    private boolean isValidTheme(String theme) {
        return theme.equals("Light") || theme.equals("Dark") || theme.equals("Red")
                || theme.equals("Blue") || theme.equals("Green");
    }

    private String formatThemeString(String theme) {
        theme = (theme.trim()).toLowerCase();
        return theme.substring(0, 1).toUpperCase() + theme.substring(1);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.theme.equals(((ThemeCommand) other).theme)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> avatar} into an {@code Optional<Avatar>} if {@code avatar} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Avatar> parseAvatar(Optional<String> avatar) throws IllegalValueException {
        requireNonNull(avatar);
        return avatar.isPresent() ? Optional.of(new Avatar(avatar.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ThemeCommand object.
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given (@code String) in the context of a ThemeCommand.
     * @return ThemeCommand Object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String userInput) throws ParseException {
        if (userInput.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        return new ThemeCommand(userInput);
    }
}
```
###### \java\seedu\address\model\person\avatar\Avatar.java
``` java
/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable.
 */
public class Avatar {
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar should be a valid online URL or local path.";
    public static final String DEFAULT_VALUE = "No Avatar";

    public final String value;
    private URL url;
    private BufferedImage image;

    /**
     * Sets a default placeholder avatar for new contacts being added.
     */
    public Avatar() throws IllegalValueException {
        AvatarUtil placeholder = new AvatarUtil();
        image = placeholder.getPlaceholderAvatar();
        value = DEFAULT_VALUE;
    }

    /**
     * Validates given avatar URL string and saves it in the data directory.
     *
     * @throws IllegalValueException if given URL string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        try {
            if (url.isEmpty() || DEFAULT_VALUE.equals(url)) {
                AvatarUtil placeholder = new AvatarUtil();
                image = placeholder.getPlaceholderAvatar();
                value = DEFAULT_VALUE;
            } else {
                File defaultAvatar = new File(url);

                if (isValidUrl(url)) {
                    this.url = new URL(url);
                } else {
                    this.url = defaultAvatar.toURI().toURL();
                }
                this.image = ImageIO.read(this.url);

                if (!isSavedInData(url)) {
                    String outputName = "/data/" + this.image.hashCode() + ".png";
                    File outputImage = new File(System.getProperty("user.dir") + outputName);

                    File parentDirectory = outputImage.getParentFile();
                    if (!parentDirectory.exists()) {
                        parentDirectory.mkdirs();
                    }

                    ImageIO.write(this.image, "png", outputImage);
                    this.url = outputImage.toURI().toURL();
                }
                this.value = this.url.toString();
            }
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidUrl(String url) {
        return (url.contains("https://") || url.contains("http://") || url.contains("file:/"));
    }

    /**
     * Returns true if a given valid url is already found in data folder.
     */
    public static boolean isSavedInData(String url) {
        return (url.contains("file:/") && url.contains("data/"));
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }

    @Override
    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    @Override
    public Avatar getAvatar() {
        return avatar.get();
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    /**
     * Assign a random colour to the tag.
     * @return A random colour hex code.
     */
    private String assignTagColour() {
        TagColours palette = new TagColours();
        String[] tagColours = palette.getTagColours();
        int randomIndex = colourSelect.nextInt(tagColours.length);

        return tagColours[randomIndex];
    }
```
###### \java\seedu\address\model\tag\TagColours.java
``` java
/**
 * A UI feature for each unique tag to have its own colour.
 */
public class TagColours {
    private static final String RED = "#CB4335";
    private static final String DARK_RED = "#911205";
    private static final String ORANGE = "#FF7700";
    private static final String LIGHT_ORANGE = "#FF9030";
    private static final String YELLOW = "#F4D03F";
    private static final String GREEN = "#27AE60";
    private static final String DARK_GREEN = "#006D36";
    private static final String BLUE = "#2980B9";
    private static final String NAVY_BLUE = "#095282";
    private static final String INDIGO = "#8E44AD";
    private static final String VIOLET = "#DB2B6F";
    private static final String PURPLE = "#6F3CB7";
    private static final String HOT_PINK = "#FF0062";
    private static final String CYAN = "#008C96";
    private static final String BROWN = "#684D03";

    /**
     * Returns a String array of available tag colours.
     */
    public String[] getTagColours() {
        return new String[] { RED, DARK_RED, ORANGE, LIGHT_ORANGE, YELLOW,
                                GREEN, DARK_GREEN, BLUE, NAVY_BLUE, INDIGO,
                                VIOLET, HOT_PINK, PURPLE, CYAN, BROWN };
    }
}
```
###### \java\seedu\address\ui\AvatarWindow.java
``` java
/**
 * Controller for avatar page
 */
public class AvatarWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(AvatarWindow.class);
    private static final String ICON = "/images/AvatarWindowIcon.png";
    private static final String FXML = "AvatarWindow.fxml";
    private static final String TITLE = "Avatar Options";

    @FXML
    private ImageView avatarW;
    private PersonCard selectedPersonCard;
    private String url;

    private final Logic logic;
    private final Stage dialogStage;

    public AvatarWindow(PersonCard selectedPersonCard, Logic logic) {
        super(FXML);

        this.logic = logic;
        this.selectedPersonCard = selectedPersonCard;

        Scene scene = new Scene(getRoot());

        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(false);
        dialogStage.setAlwaysOnTop(true);
        dialogStage.setHeight(325);
        dialogStage.setWidth(250);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        avatarW.setImage(SwingFXUtils.toFXImage(selectedPersonCard.person.getAvatar().getImage(), null));
        show();
    }

    /**
     * Shows the avatar window.
     */
    public void show() {
        logger.fine("Showing avatar window.");
        dialogStage.showAndWait();
    }

    /**
     * Opens the file dialog window upon clicking the load button.
     */
    @FXML
    private void loadPrompt() throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJpg, extFilterPng);

        File file = fileChooser.showOpenDialog(null);

        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        avatarW.setImage(image);
        url = file.getPath();
    }

    /**
     * Saves the avatar upon clicking the save button by executing an edit command.
     */
    @FXML
    private void savePrompt() {
        int displayedIndex = selectedPersonCard.getDisplayedIndex();

        try {
            CommandResult commandResult = logic.execute("edit " + displayedIndex + " v/" + url);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            dialogStage.close();
        } catch (CommandException | ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    public void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) throws CommandException, ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme(event.theme + "Theme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
    }

    @FXML
    private void setToLightTheme() {
        if (checkSameTheme("Light")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("LightTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Light"));
        raise(new NewResultAvailableEvent("Theme updated to: Light", false));
    }

    @FXML
    private void setToDarkTheme() {
        if (checkSameTheme("Dark")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("DarkTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Dark"));
        raise(new NewResultAvailableEvent("Theme updated to: Dark", false));
    }

    @FXML
    private void setToRedTheme() {
        if (checkSameTheme("Red")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("RedTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Red"));
        raise(new NewResultAvailableEvent("Theme updated to: Red", false));
    }

    @FXML
    private void setToBlueTheme() {
        if (checkSameTheme("Blue")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("BlueTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Blue"));
        raise(new NewResultAvailableEvent("Theme updated to: Blue", false));
    }

    @FXML
    private void setToGreenTheme() {
        if (checkSameTheme("Green")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("GreenTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Green"));
        raise(new NewResultAvailableEvent("Theme updated to: Green", false));
    }

    /**
     * Returns true if the theme to be set is already in place and raises an event to the user.
     */
    private boolean checkSameTheme(String theme) {
        if (currentTheme.contains(theme)) {
            raise(new NewResultAvailableEvent("Theme is already set to " + theme + "!", true));
            return true;
        }
        return false;
    }
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
/**
 * The Person Panel of the App, which displays the contact details of a selected person.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";

    private final Logic logic;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private PersonCard selectedPersonCard;
    private ReadOnlyPerson selectedPerson;
    private boolean isBlankPage = true;

    @FXML
    private VBox panel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView avatar;

    public PersonPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
        loadBlankPersonPage();
    }

    /**
     * Loads a blank page when no contact is selected.
     */
    private void loadBlankPersonPage() {
        name.setText("No contact selected");
        phone.setText("Phone: -");
        address.setText("Address: -");
        email.setText("Email: -");
        birthday.setText("Birthday: -");
        avatar.setImage(null);
        tags.getChildren().clear();
    }

    /**
     * Loads the selected person's contact details.
     */
    private void loadPersonPage() {
        name.setText(selectedPerson.getName().fullName);
        phone.setText("Phone: " + selectedPerson.getPhone().toString());
        address.setText("Address: " + selectedPerson.getAddress().toString());
        email.setText("Email: " + selectedPerson.getEmail().toString());
        birthday.setText("Birthday: " + selectedPerson.getBirthday().toString());
        avatar.setImage(SwingFXUtils.toFXImage(selectedPerson.getAvatar().getImage(), null));
        selectedPerson.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.tagColour);
            tags.getChildren().add(tagLabel);
        });
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        loadBlankPersonPage();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPersonCard = event.getNewSelection();
        selectedPerson = selectedPersonCard.person;
        isBlankPage = false;
        loadPersonPage();
    }

    @Subscribe
    private void handlePersonPanelNoSelectionEvent(PersonPanelNoSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        isBlankPage = true;
        loadBlankPersonPage();
    }

    /**
     * Clicking the Avatar will trigger a GUI option of uploading pictures.
     * Note: Implementation is not complete; may or may not use this.
     */
    @FXML
    private void avatarPrompt() {
        if (!isBlankPage) {
            new AvatarWindow(selectedPersonCard, logic);
        }
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (event.isError) {
            setCommandFailureStyle();
        } else {
            setDefaultStyle();
        }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets {@code ResultDisplay} style to the default style.
     */
    private void setDefaultStyle() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets {@code ResultDisplay} style to the fail-state style.
     */
    private void setCommandFailureStyle() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) { // Previous command was also invalid (failure)
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    /**
     * Sets the total number of contacts.
     */
    private void setNumTotalContacts(int numTotalContacts) {
        this.numTotalContacts.setText("Total: " + numTotalContacts + " contact(s)");
    }
```
###### \resources\view\AvatarWindow.fxml
``` fxml
<StackPane fx:id="avatarWindowRoot" prefHeight="286.0" prefWidth="250.0" style="-fx-background-color: #eeeeee;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <TitledPane animated="false" prefHeight="54.0" prefWidth="250.0" text="Options" StackPane.alignment="BOTTOM_CENTER">
        <content>
          <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="180.0" prefWidth="200.0" style="-fx-background-color: #dddddd;">
               <children>
                  <Button fx:id="avatarLoad" layoutY="2.0" mnemonicParsing="false" onAction="#loadPrompt" text="Load" />
                  <Button fx:id="avatarSave" layoutX="49.0" layoutY="2.0" mnemonicParsing="false" onAction="#savePrompt" text="Save" />
               </children>
            </AnchorPane>
        </content>
      </TitledPane>
       <ImageView fx:id="avatarW" fitHeight="200.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true" StackPane.alignment="TOP_CENTER">
         <effect>
            <DropShadow color="#333333" height="18.0" radius="8.5" width="18.0" />
         </effect>
         <StackPane.margin>
            <Insets top="15.0" />
         </StackPane.margin>
      </ImageView>
   </children>
</StackPane>
```
###### \resources\view\BlueTheme.css
``` css

/* Start: Default Settings */
.root {
    -fx-font-family: "Calibri Light";
    -fx-text-fill: #333333;
    -fx-font-size: 14pt;
}

.background {
    -fx-background-color: #f7f5f4;
    background-color: #ffffff; /* Used in the default.html file */
}

.scroll-bar {
    -fx-background-color: #f4f8ff;
}

.scroll-bar .thumb {
    -fx-background-color: #8698ba;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 1 1 1 1;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 0 8 0 8;
}

.split-pane-divider {
    -fx-background-color: transparent;
}

#tags {
    -fx-font-family: "Calibri";
    -fx-font-weight: bold;
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #ffffff;
    -fx-padding: 1 5 1 5;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
}

#mainWindow .split-pane {
    -fx-background-color: #f7f5f4;
    -fx-background-image: url("../images/LightThemeBackground.png") no-repeat center center;
}

#mainWindow .VBox {
    -fx-border-width: 5px;
}
/* End: Default Settings */

/* Start: Menu Bar */
.menu-bar {
    -fx-background-color: #3e6fc1;
    -fx-border-color: #375fa3;
    -fx-border-width: 0 0 1 0;
}

.menu-bar .menu {
    -fx-background-color: #3e6fc1;
}

.menu-bar .menu:hover {
    -fx-background-color: #375fa3;
}

.menu-bar .menu .label {
    -fx-text-fill: #eeeeee;
}

.menu-bar .menu-item {
    -fx-background-color: #ffffff;
}

.menu-bar .menu-item:hover {
    -fx-background-color: #f2f2f2;
}

.menu-bar .menu-item .label {
    -fx-text-fill: #333333;
}
/* End: Menu Bar */

/* Start: Person List Card */
#personList {
    -fx-background-color: #f7f5f4;
}

#personListView, #personListPanelPlaceholder {
    -fx-background-color: #f7f5f4;
}

#cardPane #name, #id {
    -fx-font-size: 18pt;
    -fx-text-fill: #333333;
}

#cardPane #tags .label {
    -fx-font-size: 13pt;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-background-color: #f7f5f4;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #f7f9ff;
}

.list-cell:filled:odd {
    -fx-background-color: #eff4ff;
}

.list-cell:filled:selected {
    -fx-background-color: #d3e3ff;
    -fx-border-color: #c1d8ff;
    -fx-border-width: 1px;
}

/* End: Person List Card */

/* Start: Person Panel */
#personPanelPlaceholder {
    -fx-background-color: transparent;
}

#primaryDetails {
    -fx-background-color: #3e6fc1;
}

#secondaryDetails {
    -fx-font-family: "Calibri Light";
    -fx-background-color: #f2f2f2;
}

#personPanel #name {
    -fx-text-fill: #eeeeee;
    -fx-font-size: 42pt;
}

#personPanel #tags .label {
    -fx-font-family: "Calibri";
    -fx-font-size: 20pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
    -fx-label-padding: 0 0 0 42;
    -fx-background-position: 10 5;
    -fx-background-repeat: no-repeat;
    -fx-background-size: 32px;
}

#personPanel #address {
    -fx-background-image: url("../images/AddressIcon.png");
}

#personPanel #email {
    -fx-background-image: url("../images/EmailIcon.png");
}

#personPanel #phone {
    -fx-background-image: url("../images/PhoneIcon.png");
}

#personPanel #birthday {
    -fx-background-image: url("../images/BirthdayIcon.png");
}
/* End: Person Panel */

/* Start: Command Box */
.result-display {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
    -fx-text-fill: #000000;
}

.text-field {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
}
/* End: Command Box */

/* Start: Status Bar */
.status-bar {
    -fx-background-color: #e0e0e0;
}

.status-bar .label {
    -fx-font-size: 12pt;
    -fx-text-fill: #333333;
}
/* End: Status Bar */
```
###### \resources\view\DarkTheme.css
``` css

/* Start: Default Settings */
.root {
    -fx-font-family: "Calibri Light";
    -fx-text-fill: #ffffff;
    -fx-font-size: 14pt;
}

.background {
    -fx-background-color: #333333;
    background-color: #333333; /* Used in the default.html file */
}

.scroll-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: #515151;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 1 1 1 1;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 0 8 0 8;
}

.split-pane-divider {
    -fx-background-color: transparent;
}

#tags {
    -fx-font-family: "Calibri";
    -fx-font-weight: bold;
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #ffffff;
    -fx-padding: 1 5 1 5;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
}

#mainWindow .split-pane {
    -fx-background-color: #333333;
    -fx-background-image: url("../images/DarkThemeBackground.png") no-repeat center center;
}

#mainWindow .VBox {
    -fx-border-width: 5px;
}

/* End: Default Settings */

/* Start: Menu Bar */
.menu-bar {
    -fx-background-color: #43484f;
    -fx-border-color: #35393f;
    -fx-border-width: 0 0 1 0;
}

.menu-bar .menu {
    -fx-background-color: #43484f;
}

.menu-bar .menu:hover {
    -fx-background-color: #35393f;
}

.menu-bar .menu .label {
    -fx-text-fill: #ffffff;
}

.menu-bar .menu-item {
    -fx-background-color: #ffffff;
}

.menu-bar .menu-item:hover {
    -fx-background-color: #f2f2f2;
}

.menu-bar .menu-item .label {
    -fx-text-fill: #000000;
}
/* End: Menu Bar */

/* Start: Person List Card */
#personList {
    -fx-background-color: #333333;
}

#personListView {
    -fx-background-color: transparent #383838 transparent #383838;
}

#cardPane #name, #id {
    -fx-font-size: 18pt;
    -fx-text-fill: #EEEEEE;
}

#cardPane #tags .label {
    -fx-font-size: 13pt;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-background-color: #333333;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #3c3e3f;
}

.list-cell:filled:odd {
    -fx-background-color: #515658;
}

.list-cell:filled:selected {
    -fx-background-color: #424d5f;
    -fx-border-color: #3e7b91;
    -fx-border-width: 1px;
}

/* End: Person List Card */

/* Start: Person Panel */
#personPanelPlaceholder {
    -fx-background-color: transparent;
}

#primaryDetails {
    -fx-background-color: #3c3e3f;
}

#secondaryDetails {
    -fx-font-family: "Calibri Light";
    -fx-background-color: #515658;
}

#personPanel #name {
    -fx-text-fill: #eeeeee;
    -fx-font-size: 42pt;
}

#personPanel #tags .label {
    -fx-font-family: "Calibri";
    -fx-font-size: 20pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-text-fill: #eeeeee;
    -fx-font-size: 18pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
    -fx-label-padding: 0 0 0 42;
    -fx-background-position: 10 5;
    -fx-background-repeat: no-repeat;
    -fx-background-size: 32px;
}

#personPanel #address {
    -fx-background-image: url("../images/AddressIcon.png");
}

#personPanel #email {
    -fx-background-image: url("../images/EmailIcon.png");
}

#personPanel #phone {
    -fx-background-image: url("../images/PhoneIcon.png");
}

#personPanel #birthday {
    -fx-background-image: url("../images/BirthdayIcon.png");
}
/* End: Person Panel */

/* Start: Command Box */
.result-display {
    -fx-font-family: "Courier New";
    -fx-background-color: transparent;
    -fx-font-size: 14pt;
    -fx-text-fill: #eeeeee;
}

.text-field {
    -fx-background-color: #444444;
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
}

.text-area {
    -fx-background-color: #444444;
}

.text-area .scroll-pane {
    -fx-background-color: transparent;
}

.text-area .scroll-pane .viewport{
    -fx-background-color: transparent;
}

.text-area .scroll-pane .content{
    -fx-background-color: transparent;
}

#commandTextField {
    -fx-text-fill: #eeeeee;
}

.pane-with-border {
    -fx-background-color: #333333;
    -fx-border-color: #333333;
    -fx-border-top-width: 1px;
}
/* End: Command Box */

/* Start: Status Bar */
.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.status-bar .label {
    -fx-font-size: 12pt;
    -fx-text-fill: #EEEEEE;
}
/* End: Status Bar */
```
###### \resources\view\GreenTheme.css
``` css

/* Start: Default Settings */
.root {
    -fx-font-family: "Calibri Light";
    -fx-text-fill: #333333;
    -fx-font-size: 14pt;
}

.background {
    -fx-background-color: #f7f5f4;
    background-color: #ffffff; /* Used in the default.html file */
}

.scroll-bar {
    -fx-background-color: #e2ffe3;
}

.scroll-bar .thumb {
    -fx-background-color: #08910d;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 1 1 1 1;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 0 8 0 8;
}

.split-pane-divider {
    -fx-background-color: transparent;
}

#tags {
    -fx-font-family: "Calibri";
    -fx-font-weight: bold;
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #ffffff;
    -fx-padding: 1 5 1 5;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
}

#mainWindow .split-pane {
    -fx-background-color: #f7f5f4;
    -fx-background-image: url("../images/LightThemeBackground.png") no-repeat center center;
}

#mainWindow .VBox {
    -fx-border-width: 5px;
}
/* End: Default Settings */

/* Start: Menu Bar */
.menu-bar {
    -fx-background-color: #009607;
    -fx-border-color: #007c06;
    -fx-border-width: 0 0 1 0;
}

.menu-bar .menu {
    -fx-background-color: #009607;
}

.menu-bar .menu:hover {
    -fx-background-color: #007c06;
}

.menu-bar .menu .label {
    -fx-text-fill: #eeeeee;
}

.menu-bar .menu-item {
    -fx-background-color: #ffffff;
}

.menu-bar .menu-item:hover {
    -fx-background-color: #f2f2f2;
}

.menu-bar .menu-item .label {
    -fx-text-fill: #333333;
}
/* End: Menu Bar */

/* Start: Person List Card */
#personList {
    -fx-background-color: #f7f5f4;
}

#personListView, #personListPanelPlaceholder {
    -fx-background-color: #f7f5f4;
}

#cardPane #name, #id {
    -fx-font-size: 18pt;
    -fx-text-fill: #333333;
}

#cardPane #tags .label {
    -fx-font-size: 13pt;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-background-color: #f7f5f4;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #c6ffc8;
}

.list-cell:filled:odd {
    -fx-background-color: #dbffdc;
}

.list-cell:filled:selected {
    -fx-background-color: #76bc79;
    -fx-border-color: #5b9e5e;
    -fx-border-width: 1px;
}

/* End: Person List Card */

/* Start: Person Panel */
#personPanelPlaceholder {
    -fx-background-color: transparent;
}

#primaryDetails {
    -fx-background-color: #297c2c;
}

#secondaryDetails {
    -fx-font-family: "Calibri Light";
    -fx-background-color: #f2f2f2;
}

#personPanel #name {
    -fx-text-fill: #eeeeee;
    -fx-font-size: 42pt;
}

#personPanel #tags .label {
    -fx-font-family: "Calibri";
    -fx-font-size: 20pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
    -fx-label-padding: 0 0 0 42;
    -fx-background-position: 10 5;
    -fx-background-repeat: no-repeat;
    -fx-background-size: 32px;
}

#personPanel #address {
    -fx-background-image: url("../images/AddressIcon.png");
}

#personPanel #email {
    -fx-background-image: url("../images/EmailIcon.png");
}

#personPanel #phone {
    -fx-background-image: url("../images/PhoneIcon.png");
}

#personPanel #birthday {
    -fx-background-image: url("../images/BirthdayIcon.png");
}
/* End: Person Panel */

/* Start: Command Box */
.result-display {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
    -fx-text-fill: #000000;
}

.text-field {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
}
/* End: Command Box */

/* Start: Status Bar */
.status-bar {
    -fx-background-color: #e0e0e0;
}

.status-bar .label {
    -fx-font-size: 12pt;
    -fx-text-fill: #333333;
}
/* End: Status Bar */
```
###### \resources\view\LightTheme.css
``` css

/* Start: Default Settings */
.root {
    -fx-font-family: "Calibri Light";
    -fx-text-fill: #333333;
    -fx-font-size: 14pt;
}

.background {
    -fx-background-color: #f7f5f4;
    background-color: #ffffff; /* Used in the default.html file */
}

.scroll-bar .thumb {
    -fx-background-color: #a5a5a5;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 1 1 1 1;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 0 8 0 8;
}

.split-pane-divider {
    -fx-background-color: transparent;
}

#tags {
    -fx-font-family: "Calibri";
    -fx-font-weight: bold;
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #ffffff;
    -fx-padding: 1 5 1 5;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
}

#mainWindow .split-pane {
    -fx-background-color: #f7f5f4;
    -fx-background-image: url("../images/LightThemeBackground.png") no-repeat center center;
}

#mainWindow .VBox {
    -fx-border-width: 5px;
}
/* End: Default Settings */

/* Start: Menu Bar */
.menu-bar {
    -fx-background-color: #e0e0e0;
    -fx-border-color: #d0d0d0;
    -fx-border-width: 0 0 1 0;
}

.menu-bar .menu {
    -fx-background-color: #e0e0e0;
}

.menu-bar .menu:hover {
    -fx-background-color: #d0d0d0;
}

.menu-bar .menu .label {
    -fx-text-fill: #333333;
}

.menu-bar .menu-item {
    -fx-background-color: #ffffff;
}

.menu-bar .menu-item:hover {
    -fx-background-color: #f2f2f2;
}

.menu-bar .menu-item .label {
    -fx-text-fill: #333333;
}
/* End: Menu Bar */

/* Start: Person List Card */
#personList {
    -fx-background-color: #f7f5f4;
}

#personListView, #personListPanelPlaceholder {
    -fx-background-color: #f7f5f4;
}

#cardPane #name, #id {
    -fx-font-size: 18pt;
    -fx-text-fill: #333333;
}

#cardPane #tags .label {
    -fx-font-size: 13pt;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-background-color: #f7f5f4;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #e2e2e2;
}

.list-cell:filled:odd {
    -fx-background-color: #f2f2f2;
}

.list-cell:filled:selected {
    -fx-background-color: #c4d4ed;
    -fx-border-color: #a9c1e8;
    -fx-border-width: 1px;
}

/* End: Person List Card */

/* Start: Person Panel */
#personPanelPlaceholder {
    -fx-background-color: transparent;
}

#primaryDetails {
    -fx-background-color: #e2e2e2;
}

#secondaryDetails {
    -fx-font-family: "Calibri Light";
    -fx-background-color: #f2f2f2;
}

#personPanel #name {
    -fx-font-size: 42pt;
}

#personPanel #tags .label {
    -fx-font-family: "Calibri";
    -fx-font-size: 20pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
    -fx-label-padding: 0 0 0 42;
    -fx-background-position: 10 5;
    -fx-background-repeat: no-repeat;
    -fx-background-size: 32px;
}

#personPanel #address {
    -fx-background-image: url("../images/AddressIcon.png");
}

#personPanel #email {
    -fx-background-image: url("../images/EmailIcon.png");
}

#personPanel #phone {
    -fx-background-image: url("../images/PhoneIcon.png");
}

#personPanel #birthday {
    -fx-background-image: url("../images/BirthdayIcon.png");
}
/* End: Person Panel */

/* Start: Command Box */
.result-display {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
    -fx-text-fill: #000000;
}

.text-field {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
}
/* End: Command Box */

/* Start: Status Bar */
.status-bar {
    -fx-background-color: #e0e0e0;
}

.status-bar .label {
    -fx-font-size: 12pt;
    -fx-text-fill: #333333;
}
/* End: Status Bar */
```
###### \resources\view\PersonListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="80.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="index">
                    <minWidth>
                    <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$first" />
            </HBox>
            <FlowPane fx:id="tags" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\PersonPanel.fxml
``` fxml
<AnchorPane fx:id="personPanel" prefHeight="296.0" prefWidth="975.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <Pane prefHeight="200.0" prefWidth="200.0">
          <children>
              <AnchorPane fx:id="primaryDetails" prefHeight="200.0" prefWidth="975.0">
                  <children>
                      <HBox prefHeight="200.0" prefWidth="975.0">
                          <children>
                              <ImageView fx:id="avatar" fitHeight="150.0" fitWidth="170.0" onMouseClicked="#avatarPrompt" pickOnBounds="true" preserveRatio="true">
                                  <cursor>
                                      <Cursor fx:constant="OPEN_HAND" />
                                  </cursor>
                                  <HBox.margin>
                                      <Insets bottom="0.0" left="10.0" right="10.0" top="10.0" />
                                  </HBox.margin>
                              </ImageView>
                              <Region prefHeight="150.0" prefWidth="20.0" />
                              <VBox prefHeight="195.0" prefWidth="975.0">
                                  <children>
                                      <Label fx:id="name" prefHeight="28.0" prefWidth="975.0" wrapText="true">
                                          <padding>
                                            <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                          </padding>
                                      </Label>
                                      <FlowPane fx:id="tags" alignment="CENTER_LEFT">
                                          <padding>
                                              <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                          </padding>
                                      </FlowPane>
                                  </children>
                                  <padding>
                                      <Insets bottom="0.0" left="10.0" right="10.0" top="10.0" />
                                  </padding>
                              </VBox>
                          </children>
                      </HBox>
                  </children>
              </AnchorPane>
          </children>
    </Pane>
    <AnchorPane fx:id="secondaryDetails" layoutY="170.0" prefHeight="220.0" prefWidth="975.0">
        <children>
            <HBox layoutY="-1.0" prefHeight="220.0" prefWidth="975.0">
                <children>
                    <VBox prefHeight="150.0" prefWidth="960.0">
                        <children>
                            <Region prefHeight="27.0" prefWidth="960.0" />
                            <Label fx:id="address" prefHeight="50.0" prefWidth="975.0" wrapText="true">
                                <padding>
                                    <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                </padding>
                            </Label>
                            <Region prefHeight="20.0" prefWidth="49.0" />
                            <Label fx:id="email" prefHeight="50.0" prefWidth="975.0" wrapText="true">
                                <padding>
                                    <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                </padding>
                            </Label>
                            <Region prefHeight="20.0" prefWidth="49.0" />
                            <Label fx:id="phone" prefHeight="50.0" prefWidth="975.0" wrapText="true">
                                <padding>
                                    <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                </padding>
                            </Label>
                            <Region prefHeight="20.0" prefWidth="49.0" />
                            <Label fx:id="birthday" prefHeight="50.0" prefWidth="975.0" wrapText="true">
                                <padding>
                                    <Insets bottom="20.0" left="10.0" right="5.0" top="5.0" />
                                </padding>
                            </Label>
                        </children>
                    </VBox>
                </children>
            </HBox>
        </children>
    </AnchorPane>
   <effect>
      <DropShadow color="#333333" height="18.0" radius="8.5" width="18.0" />
   </effect>
</AnchorPane>
```
###### \resources\view\RedTheme.css
``` css

/* Start: Default Settings */
.root {
    -fx-font-family: "Calibri Light";
    -fx-text-fill: #333333;
    -fx-font-size: 14pt;
}

.background {
    -fx-background-color: #f7f5f4;
    background-color: #ffffff; /* Used in the default.html file */
}

.scroll-bar {
    -fx-background-color: #fff4f4;
}

.scroll-bar .thumb {
    -fx-background-color: #ff8c8c;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 1 1 1 1;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 0 8 0 8;
}

.split-pane-divider {
    -fx-background-color: transparent;
}

#tags {
    -fx-font-family: "Calibri";
    -fx-font-weight: bold;
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #ffffff;
    -fx-padding: 1 5 1 5;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
}

#mainWindow .split-pane {
    -fx-background-color: #f7f5f4;
    -fx-background-image: url("../images/LightThemeBackground.png") no-repeat center center;
}

#mainWindow .VBox {
    -fx-border-width: 5px;
}
/* End: Default Settings */

/* Start: Menu Bar */
.menu-bar {
    -fx-background-color: #b52735;
    -fx-border-color: #aa0114;
    -fx-border-width: 0 0 1 0;
}

.menu-bar .menu {
    -fx-background-color: #b52735;
}

.menu-bar .menu:hover {
    -fx-background-color: #aa0114;
}

.menu-bar .menu .label {
    -fx-text-fill: #eeeeee;
}

.menu-bar .menu-item {
    -fx-background-color: #ffffff;
}

.menu-bar .menu-item:hover {
    -fx-background-color: #f2f2f2;
}

.menu-bar .menu-item .label {
    -fx-text-fill: #333333;
}
/* End: Menu Bar */

/* Start: Person List Card */
#personList {
    -fx-background-color: #f7f5f4;
}

#personListView, #personListPanelPlaceholder {
    -fx-background-color: #f7f5f4;
}

#cardPane #name, #id {
    -fx-font-size: 18pt;
    -fx-text-fill: #333333;
}

#cardPane #tags .label {
    -fx-font-size: 13pt;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-background-color: #f7f5f4;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #ffeaea;
}

.list-cell:filled:odd {
    -fx-background-color: #fff4f4;
}

.list-cell:filled:selected {
    -fx-background-color: #ffe2e2;
    -fx-border-color: #ffd3d3;
    -fx-border-width: 1px;
}

/* End: Person List Card */

/* Start: Person Panel */
#personPanelPlaceholder {
    -fx-background-color: transparent;
}

#primaryDetails {
    -fx-background-color: #c13434;
}

#secondaryDetails {
    -fx-font-family: "Calibri Light";
    -fx-background-color: #f2f2f2;
}

#personPanel #name {
    -fx-text-fill: #eeeeee;
    -fx-font-size: 42pt;
}

#personPanel #tags .label {
    -fx-font-family: "Calibri";
    -fx-font-size: 20pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone, #personPanel #birthday {
    -fx-font-size: 18pt;
    -fx-label-padding: 0 0 0 42;
    -fx-background-position: 10 5;
    -fx-background-repeat: no-repeat;
    -fx-background-size: 32px;
}

#personPanel #address {
    -fx-background-image: url("../images/AddressIcon.png");
}

#personPanel #email {
    -fx-background-image: url("../images/EmailIcon.png");
}

#personPanel #phone {
    -fx-background-image: url("../images/PhoneIcon.png");
}

#personPanel #birthday {
    -fx-background-image: url("../images/BirthdayIcon.png");
}
/* End: Person Panel */

/* Start: Command Box */
.result-display {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
    -fx-text-fill: #000000;
}

.text-field {
    -fx-font-family: "Courier New";
    -fx-font-size: 14pt;
}
/* End: Command Box */

/* Start: Status Bar */
.status-bar {
    -fx-background-color: #e0e0e0;
}

.status-bar .label {
    -fx-font-size: 12pt;
    -fx-text-fill: #333333;
}
/* End: Status Bar */
```
