# JasmineSee
###### /resources/view/MainWindow.fxml
``` fxml
  <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="File">
         <MenuItem mnemonicParsing="false" onAction="#handleBlackTheme" text="Black Theme" />
         <MenuItem mnemonicParsing="false" onAction="#handleWhiteTheme" text="White Theme" />
         <MenuItem mnemonicParsing="false" onAction="#handleGreenTheme" text="Green Theme" />
      <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
```
###### /resources/view/GreenTheme.css
``` css
.background {
    -fx-background-color: derive(#527623, 20%);
    background-color: #7bb135; /* Used in the default.html file */
}

body {
     -fx-background-color: derive(#527623, 20%);
    background-color: #7bb135; /* Used in the default.html file */
}

h1 {
    font-size: 32pt;
    font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    text-fill: white;
    color: white;
}

h2 {
    font-size: 20pt;
    font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    text-fill: white;
    color: white;
}

.welcomeImage {
    width: 100%;
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #95ca4e;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
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
    -fx-base: #527623;
    -fx-control-inner-background: #527623;
    -fx-background-color: #527623;
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
    -fx-background-color: derive(#527623, 20%);
    -fx-border-color: transparent transparent transparent #89c43b;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#527623, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#527623, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #339900;
}

.list-cell:filled:odd {
    -fx-background-color: #226600;
}

.list-cell:filled:selected {
    -fx-background-color: #59b300;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #00cc66;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #0e1406;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #0e1406;
}

.anchor-pane {
     -fx-background-color: derive(#527623, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#527623, 20%);
     -fx-border-color: derive(#527623, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#527623, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#527623, 30%);
    -fx-border-color: derive(#527623, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#527623, 30%);
    -fx-border-color: derive(#527623, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#527623, 30%);
}

.context-menu {
    -fx-background-color: derive(#527623, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#527623, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e7f3d8;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #527623;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #dcedc4;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #608929;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #527623;
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
    -fx-background-color: #527623;
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
    -fx-background-color: #527623;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #527623;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#527623, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#527623, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#527623, 50%);
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
    -fx-background-color: transparent #7bb135 transparent #7bb135;
    -fx-background-insets: 0;
    -fx-border-color: #7bb135 #7bb135 #ffffff #7bb135;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #7bb135, transparent, #7bb135;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #00cc00;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### /resources/view/PersonListCard.fxml
``` fxml
   <ImageView fx:id="photo" fitHeight="150.0" fitWidth="157.0" pickOnBounds="true" preserveRatio="true" />
```
###### /resources/view/WhiteTheme.css
``` css
.background {
    -fx-background-color: derive(#e6e6e6, 20%);
    background-color: #bfbfbf;
}

body {
    -fx-background-color: derive(#e6e6e6, 20%);
    background-color: #bfbfbf; /* Used in the default.html file */
}

h1 {
    font-size: 32pt;
    font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    text-fill: black;
    color: black;
}

h2 {
    font-size: 20pt;
    font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    text-fill: black;
    color: black;
}

.welcomeImage {
    width: 100%;
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #b3b3b3;
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
    -fx-base: #f2f2f2;
    -fx-control-inner-background: #f2f2f2;
    -fx-background-color: #f2f2f2;
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
    -fx-background-color: derive(#e6e6e6, 20%);
    -fx-border-color: transparent transparent transparent #b3b3b3;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#e6e6e6, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#e6e6e6, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #e6ffe6;
}

.list-cell:filled:odd {
    -fx-background-color: #ffffff;
}

.list-cell:filled:selected {
    -fx-background-color:   #bfbfbf;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color:  #b3b3ff;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
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

.anchor-pane {
      -fx-background-color: derive(#e6e6e6, 20%);
}

.pane-with-border {
    -fx-background-color: derive(#e6e6e6, 20%);
    -fx-border-color: derive(#e6e6e6, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
     -fx-background-color: derive(#e6e6e6, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: white !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#e6e6e6, 30%);
    -fx-border-color: derive(#e6e6e6, 25%);
    -fx-border-width: 1px;
}


.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#e6e6e6, 30%);
    -fx-border-color: derive(#e6e6e6, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#e6e6e6, 30%);
}

.context-menu {
    -fx-background-color: derive(#e6e6e6, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#e6e6e6, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
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
    -fx-border-color: #999999;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #e6e6e6;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #1a1a1a;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #cccccc;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
  -fx-text-fill: white;
}

.button:focused {
    -fx-border-color: #595959, #595959;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: white;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: black;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #e6e6e6;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #e6e6e6;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#e6e6e6, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: #f2f2f2;
}

.scroll-bar .thumb {
    -fx-background-color:  #d9d9d9;
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
    -fx-background-color: transparent #cccccc transparent #cccccc;
    -fx-background-insets: 0;
    -fx-border-color: #cccccc #cccccc #000000 #cccccc;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, white, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #e6e6e6, transparent, #e6e6e6;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: black;
    -fx-background-color: #edf5f7;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Changes to default dark theme.
     */
    @FXML
    private void handleBlackTheme() {
        if (vBox.getStylesheets().contains("view/DarkTheme.css")) {
            vBox.getStylesheets().remove("view/DarkTheme.css");
        }
        vBox.getStylesheets().remove("view/WhiteTheme.css");
        vBox.getStylesheets().remove("view/GreenTheme.css");
        vBox.getStylesheets().add("view/DarkTheme.css");
        // this.scene.setFill(Color.BLUE);
    }

    /**
     * Changes to white theme.
     */
    @FXML
    private void handleWhiteTheme() {
        if (vBox.getStylesheets().contains("view/WhiteTheme.css")) {
            vBox.getStylesheets().remove("view/WhiteTheme.css");
        }
        vBox.getStylesheets().remove("view/DarkTheme.css");
        vBox.getStylesheets().remove("view/GreenTheme.css");
        vBox.getStylesheets().add("view/WhiteTheme.css");
    }


    /**
     * Changes to green theme.
     */
    @FXML
    private void handleGreenTheme() {
        if (vBox.getStylesheets().contains("view/GreenTheme.css")) {
            vBox.getStylesheets().remove("view/GreenTheme.css");
        }
        vBox.getStylesheets().remove("view/WhiteTheme.css");
        vBox.getStylesheets().remove("view/DarkTheme.css");
        vBox.getStylesheets().add("view/GreenTheme.css");
    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
        registerAsAnEventHandler(this);
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

        Path path = Paths.get("src/main/photos/" + person.getEmail().toString() + ".png");
        if (Files.exists(path)) {
            File filePic = new File("src/main/photos/" + person.getEmail().toString() + ".png");
            Image image = new Image(filePic.toURI().toString(), 150, 150, false, false);
            photo.setImage(image);
        } else {
            File fileDefault = new File("src/main/photos/default.jpeg");
            Image image = new Image(fileDefault.toURI().toString(), 150, 150, false, false);
            photo.setImage(image);
        }

        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    /**
     * Handles photo change
     */
    @Subscribe
    private void handlePhotoChange(PhotoChangeEvent event) {
        File file = new File("src/main/photos/" + person.getEmail().toString() + ".png");
        //}
        Path path = Paths.get("src/main/photos/" + person.getEmail().toString() + ".png");
        if (Files.exists(path)) {
            Image image = new Image(file.toURI().toString(), 150, 150, false, false);
            photo.setImage(image);
            //   System.out.println("Photo changed " + person.getName().fullName);
        }

    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java

    /**
     * Init person tags with colour
     */
```
###### /java/seedu/address/logic/parser/TagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### /java/seedu/address/logic/parser/UploadPhotoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UploadImageCommand object
 */
public class UploadPhotoCommandParser implements Parser<UploadPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadImageCommand
     * and returns an UploadImageCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadPhotoCommand parse(String args) throws ParseException {
        try {
            String[] argsArr = args.trim().split(" ");

            String indexString = argsArr[0];
            String filePath = "";
            if (argsArr.length > 1) {
                filePath = argsArr[1];
            }
            Index index = ParserUtil.parseIndex(indexString);
            return new UploadPhotoCommand(index, filePath);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
        }
    }
}

```
###### /java/seedu/address/logic/parser/RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {
    private final ArrayList<Tag> tagToRemove = new ArrayList<>();

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        for (int i = 0; i < tagKeywords.length; i++) {
            try {
                tagToRemove.add(new Tag(tagKeywords[i]));
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
        }
        return new RemoveTagCommand(tagToRemove);
    }


}
```
###### /java/seedu/address/logic/commands/UploadPhotoCommand.java
``` java
/**
 * Uploads image file to specified person.
 */
public class UploadPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uploads image to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) or "
            + "INDEX (must be a positive integer) and image file path\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR: " + COMMAND_WORD + " 1 " + "C:\\Users\\Pictures\\photo.jpg";

    public static final String MESSAGE_UPLOAD_IMAGE_SUCCESS = "Uploaded image to Person: %1$s";
    public static final String MESSAGE_UPLOAD_IMAGE_FALURE = "Image file is not valid. Try again!";
    private final Index targetIndex;
    private final String filePath;
    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private ImageView imageView = new ImageView();
    private HashMap<Email, String> photoList;

    public UploadPhotoCommand(Index targetIndex, String filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToUploadImage = lastShownList.get(targetIndex.getZeroBased());
        File imageFile;
        //  Photo targetPhoto = lastShownList.get(targetIndex.getZeroBased()).getPhoto();

        if (filePath.equals("")) {
            imageFile = handleFileChooser();
        } else {
            imageFile = new File(filePath);
        }

        if (isValidImageFile(imageFile)) {
            imageFile = saveFile(imageFile, personToUploadImage.getEmail());
            EventsCenter.getInstance().post(new PhotoChangeEvent());
        } else {
            throw new CommandException(String.format(MESSAGE_UPLOAD_IMAGE_FALURE));
        }

        // ReadOnlyPerson editedPerson = lastShownList.get(targetIndex.getZeroBased());
        // editedPerson.getPhoto().setPath(imageFile.getPath());
        // photoList.put(personToUploadImage.getEmail(), imageFile.getPath());
        // EventsCenter.getInstance().registerHandler(handler);
        return new CommandResult(String.format(MESSAGE_UPLOAD_IMAGE_SUCCESS, personToUploadImage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadPhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((UploadPhotoCommand) other).targetIndex)); // state check
    }

    /**
     * Opens fileChooser to select image file
     */
    public File handleFileChooser() {
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    /**
     * Checks if file given is an valid image file.
     */
    private boolean isValidImageFile(File file) {
        boolean isValid = true;
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {  //file is not an image file
                isValid = false;
            }
        } catch (IOException ex) { //file could not be opened
            isValid = false;
        }
        return isValid;
    }

    /**
     * Reads and saves image file into project directory folder "photos".
     */
    private File saveFile(File file, Email email) {

        File path = new File("src/main/photos/" + email.toString() + ".png");

        try {
            path.mkdirs();
            path.createNewFile();
            //     System.out.println(path.getPath());
            //  BufferedImage image = new BufferedImage(100, 100, 1);
            BufferedImage image;
            image = ImageIO.read(file);
            ImageIO.write(image, "png", path);

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, e);
        }
        return path;
    }

}
```
###### /java/seedu/address/logic/commands/TagCommand.java
``` java
/**
 * Lists all persons in the address book to the user.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters and shows list of persons with the specified tag.\n"
            + "Parameters: Existing tag\n"
            + "Example: " + COMMAND_WORD + " friends ";


    private final TagContainsKeywordsPredicate predicate;

    public TagCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof TagCommand // instanceof handles nulls
                && this.predicate.equals(((TagCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/RemoveTagCommand.java
``` java
/**
 * Removes specified tag from all persons from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rtag";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes specified tag from all persons.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends classmates colleagues";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag(s)";
    public static final String MESSAGE_TAG_NOT_REMOVED = "Tag(s) not removed";

    private final ArrayList<Tag> tagsToRemove;

    public RemoveTagCommand(ArrayList<Tag> tagsToRemove) {

        this.tagsToRemove = tagsToRemove;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            for (Tag tag : tagsToRemove) {
                model.removeTag(tag);
                LoggingCommand loggingCommand = new LoggingCommand();
                loggingCommand.keepLog(tag.toString(), "Remove Tag");
            }
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        if (!isTagsExist(tagsToRemove)) {
            return new CommandResult(String.format(MESSAGE_TAG_NOT_REMOVED));
        }
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && tagsToRemove.equals(((RemoveTagCommand) other).tagsToRemove));
    }

    /**
     * @param tagKeywords
     * @return true if keywords exist in current tag list
     */
    public boolean isTagsExist(ArrayList<Tag> tagKeywords) {
        List<Tag> tagList = model.getAddressBook().getTagList();
        return tagKeywords.stream()
                .anyMatch(keyword -> tagList.contains(keyword));
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);
            addressBook.updatePerson(oldPerson, newPerson);
            indicateAddressBookChanged();
        }
    }
```
###### /java/seedu/address/model/tag/TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTags().toString().toLowerCase().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/Model.java
``` java
    void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException;
```
