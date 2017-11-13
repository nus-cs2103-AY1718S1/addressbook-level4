# JunQuann
###### \java\seedu\address\commons\events\model\FileChooserEvent.java
``` java
/**
 * Indicates that the file chooser needs to pop up
 */
public class FileChooserEvent extends BaseEvent {

    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Select your image from the file chooser";
    }

}
```
###### \java\seedu\address\commons\events\model\NewImageEvent.java
``` java
/**
 * Indicate that a new image is available to be copied to designated folder
 */
public class NewImageEvent extends BaseEvent {

    public final String imageName;
    public final String currentImagePath;

    private String imagePath;

    public NewImageEvent(String imageName, String currentImagePath) {
        this.imageName = imageName;
        this.currentImagePath = currentImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Image has been copied to the designated folder.";
    }
}
```
###### \java\seedu\address\logic\parser\ArgumentTokenizer.java
``` java
    public static ArgumentMultimap tokenize(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        ArgumentMultimap argsMultimap = extractArguments(argsString, positions);
        return inputDisplayPicPath(argsMultimap, prefixes);
    }
```
###### \java\seedu\address\logic\parser\ArgumentTokenizer.java
``` java
    /***
     * Input the path of the display pic that is copied into the preferred image file directory
     */
    private static ArgumentMultimap inputDisplayPicPath(ArgumentMultimap argMultimap, Prefix... prefixes) {
        Optional<String> displayPicValue = argMultimap.getValue(PREFIX_DP);
        if (displayPicValue.isPresent() && !displayPicValue.get().equals(DEFAULT_DP)) {
            String currentImgPath = getCurrentImgPath();
            String hashedDisplayPicName = createUniqueDisplayPicName(argMultimap, currentImgPath, prefixes);
            if (currentImgPath == null || currentImgPath.equals(DEFAULT_DP)) {
                argMultimap.put(PREFIX_DP, DEFAULT_DP);
            } else {
                String finalImgPath = getFinalImgPath(hashedDisplayPicName, currentImgPath);
                argMultimap.put(PREFIX_DP, finalImgPath);
            }
        }
        return argMultimap;
    }

    private static String getFinalImgPath(String hashedDisplayPicName, String imgPath) {
        NewImageEvent newImageEvent = new NewImageEvent(hashedDisplayPicName, imgPath);
        EventsCenter.getInstance().post(newImageEvent);
        return newImageEvent.getImagePath();
    }

    private static String getCurrentImgPath() {
        FileChooserEvent fileChooserEvent = new FileChooserEvent();
        EventsCenter.getInstance().post(fileChooserEvent);
        return fileChooserEvent.getImgPath();
    }

    /**
     * Create a unique display pic name by adding all fields of a person together and creating a hashcode
     */
    private static String createUniqueDisplayPicName(ArgumentMultimap argMultimap,
                                                     String currentImgPath, Prefix... prefixes) {
        String displayPicName = currentImgPath;
        for (Prefix prefix : prefixes) {
            displayPicName += argMultimap.getValue(prefix);
        }
        return String.valueOf(displayPicName.hashCode());
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> displayPic} into an {@code Optional<DisplayPic>}
     * if {@code displayPic} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DisplayPic> parseDisplayPic(Optional<String> displayPic) throws IllegalValueException {
        requireNonNull(displayPic);
        return displayPic.isPresent() ? Optional.of(new DisplayPic(displayPic.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\DisplayPic.java
``` java
/**
 * Represents a Person's display picture path in a addressbook
 */
public class DisplayPic {
    public static final String MESSAGE_DISPLAYPIC_CONSTRAINTS = "Please ensure that you choose a valid image file";

    private String newDisplayPicPath;

    public DisplayPic(String displayPicName) throws IllegalValueException {
        requireNonNull(displayPicName);
        String trimmedDisplayPicName = displayPicName.trim();
        if (!isValidPicPath(trimmedDisplayPicName) && !trimmedDisplayPicName.equals(DEFAULT_DP)) {
            throw new IllegalValueException(MESSAGE_DISPLAYPIC_CONSTRAINTS);
        }
        this.newDisplayPicPath = trimmedDisplayPicName;
    }

    public String getNewDisplayPicPath() {
        return newDisplayPicPath;
    }

    /**
     * Returns if a given string is a valid picture path
     */
    public static boolean isValidPicPath(String displayPicPath) {
        File displayPicFile = new File(displayPicPath);

        MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
        mtftp.addMimeTypes("image png jpg jpeg");

        String mimeType = mtftp.getContentType(displayPicFile).split("/")[0];
        boolean isPicture = mimeType.equals("image");

        return isPicture && displayPicFile.exists();
    }

    @Override
    public String toString() {
        return this.newDisplayPicPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPic // instanceof handles nulls
                && this.newDisplayPicPath.equals(((DisplayPic) other).newDisplayPicPath)); // state check
    }

    @Override
    public int hashCode() {
        return newDisplayPicPath.hashCode();
    }
}
```
###### \java\seedu\address\storage\ImageFileStorage.java
``` java
/**
 */
public class ImageFileStorage {

    private String dirPath;

    public ImageFileStorage(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getImageFilePath(String imageName) {
        return dirPath + imageName + ".png";
    }

    /**
     * Copy the image {@code currentImagePath} to the designated ImageFileStorage
     * folder with image name {@code imageName}
     */
    public void copyImage(String currentImagePath, String imageName) throws IOException {
        File currentImage = new File(currentImagePath);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(currentImage));
        createImageDir();
        String newImagePath = getImageFilePath(imageName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newImagePath));

        int data;

        while ((data = bis.read()) != -1) {
            bos.write(data);
        }

        bis.close();
        bos.close();
    }

    /**
     * Create the image file storage directory if it does not exists.
     */
    public void createImageDir() throws IOException {
        requireNonNull(dirPath);
        FileUtil.createDirs(new File(dirPath));
    }
}

```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Copy the chosen image from filechooser into the hard disk.
     *  Creates the directory if it is missing
     * Raises {@link DataSavingExceptionEvent} if there was an error during copying.
     */
    void handleNewImageEvent(NewImageEvent nie);
}

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    public void copyImageFile(String currentImageFile, String imageName) throws IOException {
        imageFileStorage.copyImage(currentImageFile, imageName);
    }

    public String getImageFilePath(String imageName) {
        return imageFileStorage.getImageFilePath(imageName);
    }

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleNewImageEvent(NewImageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Copying display picture to the designated folder"));
        event.setImagePath(getImageFilePath(event.imageName));
        try {
            copyImageFile(event.currentImagePath, event.imageName);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private String getDisplayPicPath() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter ("PICTURE files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return DEFAULT_DP;
        }
    }

    @Subscribe
    private void handleFileChooserEvent(FileChooserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Select your image from the file chooser"));
        String currentImgPath = getDisplayPicPath();
        event.setImgPath(currentImgPath);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        person.displayPicProperty().addListener((observable, oldValue, newValue) -> {
            initDisplayPic(person);
        });
        person.instagramNameProperty().addListener((observable, oldValue, newValue) -> {
            initInstaIcon(person);
        });
        person.twitterNameProperty().addListener((observable, oldValue, newValue) -> {
            initTwitterIcon(person);
        });
    }

    /**
     * Initialise the image in PersonCard display
     * @param person
     */
    private void initDisplayPic(ReadOnlyPerson person) {
        Image displayPicture;
        if (person.getDisplayPic().toString().equals(DEFAULT_DP)) {
            displayPicture = AppUtil.getImage(DEFAULT_DP);
        } else {
            File personImg = new File(person.getDisplayPic().toString());
            String imgUrl = personImg.toURI().toString();
            displayPicture = new Image(imgUrl);
        }
        displayPic.setFill(new ImagePattern(displayPicture));
    }

    /**
     * Initialise the twitter icon is the person has a twitter account
     */
    private void initTwitterIcon(ReadOnlyPerson person) {
        String twitterName = person.getTwitterName().value;
        if (!twitterName.isEmpty()) {
            Image twitterIconPic = AppUtil.getImage(TWITTER_ICON);
            twitterIcon.setFill(new ImagePattern(twitterIconPic));
        }
    }

    /**
     * Initialise the instagram icon is the person has a instagram account
     */
    private void initInstaIcon(ReadOnlyPerson person) {
        String instaName = person.getInstagramName().value;
        if (!instaName.isEmpty()) {
            Image instaIconPic = AppUtil.getImage(INSTA_ICON);
            instaIcon.setFill(new ImagePattern(instaIconPic));
        }
    }

    /**
     * Create new labels and bind a colour to it
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label newTag = new Label(tag.tagName);
            newTag.setStyle("#C1D3DD");
            tags.getChildren().add(newTag);
        });
    }
```
###### \java\seedu\address\ui\PersonDescription.java
``` java
/**
 * An UI component that displays the detailed information of a selected person
 */
public class PersonDescription extends UiPart<StackPane> {

    private static final String FXML = "PersonDescription.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Circle displayPic;

    @FXML
    private Label name;

    @FXML
    private Label group;

    @FXML
    private Label mobile;

    @FXML
    private Label birthday;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label instagram;

    @FXML
    private Label twitter;


    public PersonDescription() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     *
     * @param person
     */
    private void loadPersonDescription(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        mobile.setText(person.getPhone().value);
        birthday.setText(person.getBirthday().value);
        email.setText(person.getEmail().value);
        address.setText(person.getAddress().value);
        instagram.setText(person.getInstagramName().value);
        twitter.setText(person.getTwitterName().value);
        initDisplayPic(person);
    }

    /**
     *
     * @param person
     */
    private void initDisplayPic(ReadOnlyPerson person) {
        Image displayPictureImg;
        String profilePic = person.getDisplayPic().toString();
        if (profilePic.equals(MainWindow.DEFAULT_DP)) {
            displayPictureImg = AppUtil.getImage(profilePic);
        } else {
            File imgFile = new File(person.getDisplayPic().toString());
            displayPictureImg = new Image(imgFile.toURI().toString());
        }
        displayPic.setFill(new ImagePattern(displayPictureImg));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDescription(event.getNewSelection().person);
    }


}
```
###### \resources\view\BrowserPanel.fxml
``` fxml
<StackPane prefHeight="400.0" prefWidth="300.0" style="-fx-background-radius: 5; -fx-background-insets: 0; -fx-background-color: #fff;" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser" minHeight="-Infinity" minWidth="-Infinity" prefHeight="-1.0" prefWidth="-1.0" />
   <padding>
      <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
   </padding>
</StackPane>
```
###### \resources\view\CommandBox.fxml
``` fxml
<StackPane styleClass="anchor-pane" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <JFXTextField fx:id="commandTextField" labelFloat="true" onAction="#handleCommandInputChanged" onKeyPressed="#handleKeyPress" promptText="Enter command here..." unFocusColor="BLACK">
      <padding>
         <Insets left="20.0" right="20.0" top="10.0" />
      </padding>
   </JFXTextField>
</StackPane>
```
###### \resources\view\default.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
    -fx-background-color: #F5F9FA;
}

.list-view {
     -fx-background-insets: 0;
     -fx-padding: 0;
     -fx-background-color: derive(#1d1d1d, 20%);
}

.list-cell:filled:hover{
    -fx-background-color: #00bfbf;
}

.list-cell:filled:hover .label{
    -fx-text-fill: #f7f7f7;
}

.list-cell:filled:selected{
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected .label{
    -fx-text-fill: #f7f7f7;
}

.list-cell:empty{
    -fx-background-color: #F5F9FA;
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
}
```
###### \resources\view\GroupLabel.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <Label fx:id="groupName" alignment="CENTER" minHeight="30.0" minWidth="30.0" style="-fx-alignment: center;" textAlignment="CENTER" HBox.hgrow="ALWAYS">
      <font>
         <Font name="Material Design Icons" size="13.0" />
      </font>
   </Label>
   <padding>
      <Insets left="30.0" />
   </padding>
</HBox>
```
###### \resources\view\GroupList.fxml
``` fxml
<VBox minWidth="150.0" prefHeight="445.0" prefWidth="150.0" style="-fx-background-radius: 10; -fx-background-color: white;" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Pane minHeight="30.0" minWidth="150.0" VBox.vgrow="NEVER">
         <children>
            <Label alignment="CENTER" layoutX="25.0" layoutY="-3.0" prefHeight="30.0" prefWidth="87.0" text="Groups">
               <font>
                  <Font name="Apple Chancery" size="18.0" />
               </font>
            </Label>
         </children>
      </Pane>
      <JFXListView fx:id="groupListView" style="-fx-background-radius: 10; -fx-background-color: #F5F9FA;" VBox.vgrow="ALWAYS" />
   </children>
   <padding>
      <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
   </padding>
</VBox>
```
###### \resources\view\HelpWindow.fxml
``` fxml
<StackPane fx:id="helpWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser" />
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
<VBox stylesheets="@default.css" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">

  <MenuBar fx:id="menuBar" maxHeight="25.0" prefHeight="22.0" prefWidth="1022.0" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="File">
      <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
    <Menu mnemonicParsing="false" text="Help">
      <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
    </Menu>
  </MenuBar>
  <StackPane fx:id="commandBoxPlaceholder" maxHeight="80.0" prefHeight="30.0" prefWidth="1022.0" style="-fx-background-color: #C1D3DD;" VBox.vgrow="ALWAYS">
      <padding>
         <Insets bottom="5.0" left="10.0" right="10.0" top="20.0" />
      </padding>
  </StackPane>

  <StackPane fx:id="resultDisplayPlaceholder" maxHeight="150.0" minHeight="80.0" prefHeight="80.0" style="-fx-background-color: #C1D3DD;" VBox.vgrow="ALWAYS">
      <padding>
         <Insets left="10.0" right="10.0" />
      </padding>
  </StackPane>
   <HBox prefWidth="1150.0" spacing="10.0" style="-fx-background-color: #F5F9FA;" VBox.vgrow="ALWAYS">
      <children>
         <VBox fx:id="tagList" minWidth="150.0" prefHeight="200.0" prefWidth="150.0" HBox.hgrow="NEVER">
            <children>
               <StackPane fx:id="tagListPanelPlaceholder" VBox.vgrow="ALWAYS" />
            </children>
         </VBox>
       <VBox fx:id="personList" minWidth="270.0" prefWidth="270.0" HBox.hgrow="NEVER">
         <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
       </VBox>
       <StackPane fx:id="personDescriptionPlaceHolder" minWidth="300.0" prefWidth="100.0" HBox.hgrow="NEVER">
            <HBox.margin>
               <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
            </HBox.margin>
         </StackPane>
       <StackPane fx:id="browserPlaceholder" alignment="TOP_RIGHT" minWidth="400.0" prefWidth="100.0" HBox.hgrow="ALWAYS">
            <HBox.margin>
               <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
            </HBox.margin>
         </StackPane>
      </children>
      <padding>
         <Insets left="10.0" right="10.0" />
      </padding>
   </HBox>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\PersonDescription.fxml
``` fxml
<AnchorPane prefHeight="503.0" prefWidth="269.0" style="-fx-background-color: #FDFDFD; -fx-background-radius: 5;" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ImageView fitHeight="186.0" fitWidth="297.0" pickOnBounds="true" style="-fx-opacity: 0.8;" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
         <image>
            <Image url="@../images/Avatar.jpg" />
         </image>
      </ImageView>
      <Circle fx:id="displayPic" fill="WHITE" layoutX="149.0" layoutY="186.0" radius="41.0" stroke="WHITE" strokeType="INSIDE" strokeWidth="2.0" />
      <Label fx:id="name" alignment="CENTER" layoutX="83.0" layoutY="226.0" prefHeight="25.0" prefWidth="132.0" text="Name" textAlignment="CENTER">
         <font>
            <Font name="Helvetica Bold" size="20.0" />
         </font>
      </Label>
      <Label fx:id="group" layoutX="130.0" layoutY="251.0" prefHeight="16.0" prefWidth="38.0" text="Group" textAlignment="CENTER">
         <font>
            <Font name="YuGothic Medium" size="12.0" />
         </font>
      </Label>
      <VBox alignment="CENTER_LEFT" layoutY="275.0" prefHeight="228.0" prefWidth="297.0" spacing="15.0">
         <children>
            <Label fx:id="mobile" graphicTextGap="15.0" prefHeight="25.0" prefWidth="237.0" text="Mobile" textAlignment="CENTER" VBox.vgrow="ALWAYS">
               <graphic>
                  <MaterialDesignIconView glyphName="PHONE" size="20" />
               </graphic>
            </Label>
            <Label fx:id="birthday" graphicTextGap="15.0" prefHeight="25.0" prefWidth="237.0" text="Birthday" textAlignment="CENTER">
               <graphic>
                  <MaterialDesignIconView glyphName="CAKE_VARIANT" size="20" />
               </graphic>
            </Label>
            <Label fx:id="email" graphicTextGap="15.0" prefHeight="25.0" prefWidth="237.0" text="Email" textAlignment="CENTER">
               <graphic>
                  <MaterialDesignIconView glyphName="GMAIL" size="20" />
               </graphic>
            </Label>
            <Label fx:id="address" graphicTextGap="15.0" prefHeight="20.0" prefWidth="265.0" text="Address" textAlignment="CENTER" wrapText="true">
               <graphic>
                  <MaterialDesignIconView glyphName="MAP" size="20" />
               </graphic>
            </Label>
            <Label fx:id="instagram" graphicTextGap="15.0" prefHeight="25.0" prefWidth="237.0" text="Instagram" textAlignment="CENTER">
               <graphic>
                  <MaterialDesignIconView glyphName="INSTAGRAM" size="20" />
               </graphic>
            </Label>
            <Label fx:id="twitter" graphicTextGap="15.0" prefHeight="25.0" prefWidth="237.0" text="Twitter" textAlignment="CENTER">
               <graphic>
                  <MaterialDesignIconView glyphName="TWITTER" size="20" />
               </graphic>
            </Label>
         </children>
         <padding>
            <Insets bottom="10.0" left="20.0" top="10.0" />
         </padding>
      </VBox>
   </children>
   <padding>
      <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
   </padding>
   <effect>
      <DropShadow color="#cfe4ff" />
   </effect>
</AnchorPane>
```
###### \resources\view\PersonListCard.fxml
``` fxml

<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox id="cardPane" fx:id="cardPane" prefHeight="90.0" prefWidth="230.0" style="-fx-background-color: transparent;">
         <children>
            <Pane minHeight="25.0" minWidth="70.0" prefHeight="25.0" prefWidth="30.0" HBox.hgrow="NEVER">
               <children>
                  <Circle fx:id="displayPic" fill="WHITE" layoutX="35.0" layoutY="45.0" radius="25.0" stroke="WHITE" strokeType="INSIDE" />
               </children>
            </Pane>
            <VBox alignment="CENTER_LEFT" minHeight="90.0" prefHeight="90.0" prefWidth="166.0" HBox.hgrow="ALWAYS">
               <children>
                  <HBox alignment="CENTER_LEFT" spacing="5">
                     <children>
                        <Label fx:id="id" styleClass="cell_big_label">
                           <minWidth>
                              <Region fx:constant="USE_PREF_SIZE" />
                           </minWidth>
                        </Label>
                        <Label fx:id="name" styleClass="cell_big_label" text="\$first">
                           <font>
                              <Font name="NanumGothic ExtraBold" size="15.0" />
                           </font>
                        </Label>
                     </children>
                  </HBox>
                  <FlowPane fx:id="tags" />
                  <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" textFill="#797777">
                     <font>
                        <Font name="Material Design Icons" size="13.0" />
                     </font>
                  </Label>
               </children>
               <padding>
                  <Insets left="10.0" />
               </padding>
            </VBox>
            <Pane minHeight="25.0" minWidth="70.0" prefHeight="25.0" prefWidth="30.0">
               <children>
                  <Circle fx:id="instaIcon" fill="#f5f9fa" layoutX="15.0" layoutY="45.0" radius="12.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
                  <Circle fx:id="twitterIcon" fill="#f5f9fa" layoutX="51.0" layoutY="45.0" radius="12.0" stroke="WHITE" strokeType="INSIDE" strokeWidth="0.0" />
               </children>
            </Pane>
         </children>
      </HBox>
      <Separator prefWidth="200.0">
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <padding>
            <Insets left="50.0" />
         </padding>
      </Separator>
   </children>
</VBox>
```
###### \resources\view\PersonListPanel.fxml
``` fxml
<JFXListView fx:id="personListView" depth="10" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="250.0" style="-fx-background-color: #F5F9FA;" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1" />
```
###### \resources\view\ResultDisplay.fxml
``` fxml
<StackPane fx:id="placeHolder" style="-fx-background-color: TRANSPARENT;" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
  <TextArea fx:id="resultDisplay" editable="false" opacity="0.55" style="-fx-background-color: transparent;">
      <padding>
         <Insets bottom="10.0" left="20.0" right="20.0" top="5.0" />
      </padding>
      <StackPane.margin>
         <Insets />
      </StackPane.margin>
      <font>
         <Font name="Helvetica" size="15.0" />
      </font></TextArea>
</StackPane>
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
<GridPane styleClass="grid-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <columnConstraints>
    <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="100" />
    <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="100" />
  </columnConstraints>
  <StatusBar styleClass="anchor-pane" fx:id="syncStatus" />
  <StatusBar styleClass="anchor-pane" fx:id="numberOfPersons" GridPane.columnIndex="1" nodeOrientation="RIGHT_TO_LEFT" />
  <StatusBar styleClass="anchor-pane" fx:id="saveLocationStatus" GridPane.columnIndex="2" nodeOrientation="RIGHT_TO_LEFT" />
</GridPane>
```
