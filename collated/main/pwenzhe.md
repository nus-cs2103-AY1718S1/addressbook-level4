# pwenzhe
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request for theme change.
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setFavourite(Favourite favourite) {
            this.favourite = favourite;
        }

        public Optional<Favourite> getFavourite() {
            return Optional.ofNullable(favourite);
        }
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Changes theme of the app.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "changetheme";
    public static final String COMMAND_ALIAS = "ct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme of UI between dark and bright themes.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Theme changed!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) {
        ui.changeTheme();
    }
```
###### \java\seedu\address\model\person\Favourite.java
``` java
/**
 * Represents a Person's favourite status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFav(String)}
 */
public class Favourite {

    public static final String MESSAGE_FAVOURITE_CONSTRAINTS =
            "Favourite is case insensitive and can only be 'yes', 'y', 'no', 'n' or empty";
    public static final String FAV_VALIDATION_REGEX = "yes|y|no|n";
    public final boolean isFavourite;
    public final String value;

    /**
     * Validates given favourite status.
     *
     * @throws IllegalValueException if given favourite status is invalid.
     */
    public Favourite(String favouriteArg) throws IllegalValueException {
        String formattedFav = favouriteArg.trim().toLowerCase();
        if (formattedFav.isEmpty()) {
            isFavourite = false;
            value = "no";
        } else {
            if (!isValidFav(formattedFav)) {
                throw new IllegalValueException(MESSAGE_FAVOURITE_CONSTRAINTS);
            }

            isFavourite = parseFav(formattedFav);
            value = favValue(formattedFav);
        }
    }

    /**
     * Returns true if a given string is a valid favourite status.
     */
    public static boolean isValidFav(String test) {
        return test.matches(FAV_VALIDATION_REGEX);
    }

    /**
     * Parses and returns the appropriate value for favourite status.
     */
    private boolean parseFav(String str) {
        if (str.matches("yes|y")) {
            return true;
        } else if (str.matches("no|n")) {
            return false;
        }

        return true;
    }

    /**
     * Formats favourite status into a string.
     */
    private String favValue(String str) {
        if (str.matches("yes|y")) {
            return "yes";
        } else if (str.matches("no|n")) {
            return "no";
        }

        return "";
    }

    /**
     * Returns if Person is a favourite.
     */
    public boolean getFavourite() {
        return isFavourite;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite // instanceof handles nulls
                && (this.isFavourite == ((Favourite) other).isFavourite)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Name.java
``` java
    public static final String NAME_VALIDATION_REGEX = "[[A-Z0-9]$\\p{Alnum}][[A-Z0-9]$\\p{Alnum} ]*";
```
###### \java\seedu\address\model\person\Name.java
``` java
        String[] nameArr = trimmedName.split(" ");
        StringBuilder capitalisedName = new StringBuilder();

        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        } else {
            for (int i = 0; i < nameArr.length; i++) {
                capitalisedName.append(Character.toUpperCase(nameArr[i].charAt(0)));
                capitalisedName.append(nameArr[i].substring(1));

                if (i < nameArr.length - 1) {
                    capitalisedName.append(' ');
                }
            }
        }

        this.fullName = capitalisedName.toString();
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
                || keywords.stream()
                        .filter(num -> num.matches("\\d+"))
                        .anyMatch(number -> person.getPhone().toString().contains(number))
                || keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                        .anyMatch(email -> person.getEmail().toString().contains(email))
                || keywords.stream()
                        .anyMatch(address -> StringUtil
                                .containsWordIgnoreCase(person.getAddress().toString(), address))
                || keywords.stream()
                        .filter(postal -> postal.matches("\\d+"))
                        .anyMatch(postalCode -> person.getPostalCode().toString().contains(postalCode))
                || keywords.stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(person.getTags().stream()
                                .map(i -> i.toString()).collect(Collectors.joining(" "))
                                .replaceAll("\\[|\\]|\\,", " "), tag))
                || keywords.stream()
                        .filter(fav -> fav.toLowerCase().matches("favourite|fav|favs|favorite|favourites|favorites"))
                        .anyMatch(favourite -> person.getFavourite().toString().matches("yes"));
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Compare Persons by favourite status and then name
     */
    public int comparePeople(Person p1, Person p2) {
        int compare;
        String p1Fav = "";
        String p2Fav = "";
        if (p1.getFavourite().getFavourite()) {
            p1Fav = "1";
        } else {
            p1Fav = "2";
        }
        if (p2.getFavourite().getFavourite()) {
            p2Fav = "1";
        } else {
            p2Fav = "2";
        }
        compare = p1Fav.compareTo(p2Fav);
        if (compare == 0) {
            compare = p1.getName().toString().compareTo(p2.getName().toString());
        }

        return compare;
    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private String addressBookTheme = "Bright";
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getAddressBookTheme() {
        return addressBookTheme;
    }

    public void setAddressBookTheme() {
        if (this.addressBookTheme.equals("Bright")) {
            this.addressBookTheme = "Dark";
        } else {
            this.addressBookTheme = "Bright";
        }
    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
        sb.append("\nAddressBook theme : " + addressBookTheme);
```
###### \java\seedu\address\model\UserPrefs.java
``` java
        return sb.toString();
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Initializes the theme on startup to the user preferred theme.
     * @param theme
     */
    public void initTheme(String theme) {
        String initThemePath = MainApp.class.getResource(FXML_FILE_FOLDER + theme + "Theme.css").toString();

        getRoot().getStylesheets().add(initThemePath);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise favourite image
     * @param person
     */
    private void initFavourite(ReadOnlyPerson person) {
        if (!person.getFavourite().isFavourite) {
            favourite.setVisible(false);
        }
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
            mainWindow.initTheme(prefs.getAddressBookTheme());
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Override
    public void changeTheme() {
        mainWindow.changeTheme();
        this.prefs.setAddressBookTheme();
    }
```
###### \resources\view\BrightTheme.css
``` css
.background {
    -fx-background-color: derive(#dddddd, 20%);
    background-color: #e1e1e1; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #eeeeee;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
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
    -fx-base: #dddddd;
    -fx-control-inner-background: #dddddd;
    -fx-background-color: #dddddd;
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
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#dddddd, 20%);
    -fx-border-color: transparent transparent transparent #a7a7a7;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#dddddd, 20%);
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

.list-cell:filled:even {
    -fx-background-color: #e1e1e2;
}

.list-cell:filled:odd {
    -fx-background-color: #BCBCBC;
}

.list-cell:filled:selected {
    -fx-background-color: #e2e4e7;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #9ebdc8;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.listPanel > ListView {
    -fx-background-color: #e7e7e7;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #d8d9d9;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #d8d9d9;
}

.anchor-pane {
     -fx-background-color: derive(#dddddd, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#dddddd, 20%);
     -fx-border-color: derive(#dddddd, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#dddddd, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#dddddd, 30%);
    -fx-border-color: derive(#dddddd, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#dddddd, 30%);
    -fx-border-color: derive(#dddddd, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#dddddd, 30%);
}

.personLabel {
    -fx-font-size: 20pt;
    -fx-text-fill: black;
}

.personGrid > Label {
    -fx-text-fill: black;
}

.context-menu {
    -fx-background-color: derive(#dddddd, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#dddddd, 20%);
}

.menu-bar .label {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #f6f6f6;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #dddddd;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #f3f3f3;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #c3c3c3;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
  -fx-text-fill: #dddddd;
}

.button:focused {
    -fx-border-color: black, black;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #dddddd;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #dddddd;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #dddddd;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#dddddd, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#dddddd, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#dddddd, 50%);
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
    -fx-background-color: transparent #e1e1e1 transparent #e1e1e1;
    -fx-background-insets: 0;
    -fx-border-color: #e1e1e1 #e1e1e1 #000000 #e1e1e1;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
   -fx-effect: innershadow(gaussian, white, 10, 0, 0, 0);
   -fx-background-color: #ffffff;
}

#resultDisplay .content {
    -fx-background-color: transparent, #e1e1e1, transparent, #e1e1e1;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #9ebdc8;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

```
###### \resources\view\PersonDetailsPanel.fxml
``` fxml
      <Label fx:id="name" layoutX="20.0" layoutY="14.0" styleClass="personLabel" text="Label">
```
###### \resources\view\PersonListCard.fxml
``` fxml
            <VBox />
        <Region HBox.hgrow="ALWAYS" />
        <ImageView fx:id="favourite" translateY="-5.0">
          <image>
            <Image url="@../images/favourite_star.png" />
          </image>
        </ImageView>
```
###### \resources\view\PersonListPanel.fxml
``` fxml
<VBox styleClass="listPanel" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
```
