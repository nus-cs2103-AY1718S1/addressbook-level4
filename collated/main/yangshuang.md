# yangshuang
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java

/**
 * Signals that the operation will result in duplicate Event objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml

<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.control.Tab?>
<?import javafx.scene.control.TabPane?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@../../../../../../2103_project/src/main/resources/view/BrightTheme.css" />
        <URL value="@../../../../../../2103_project/src/main/resources/view/Extensions.css" />
        <URL value="@../../../../../../2103_project/src/main/resources/view/CalendarView.css" />
    </stylesheets>

<VBox xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@../../../../../../2103_project/src/main/resources/view/BrightTheme.css" />
        <URL value="@../../../../../../2103_project/src/main/resources/view/Extensions.css" />
    </stylesheets>

    <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
            <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
        </Menu>
    </MenuBar>

    <SplitPane dividerPositions="0.15, 0.85" prefHeight="50.0" prefWidth="200.0">
        <items>
            <StackPane alignment="CENTER">
                <padding>
                    <Insets bottom="20" left="20" right="10" top="10" />
                </padding>
                <AnchorPane fx:id="calendarButton" maxWidth="35" prefHeight="30">
                    <ImageView fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                        <Image url="/images/calendar.png" />
                    </ImageView>
                </AnchorPane>
            </StackPane>
            <StackPane fx:id="commandBoxPlaceholder" minHeight="60.0" styleClass="commandBox" VBox.vgrow="NEVER">
                <padding>
                    <Insets bottom="5" left="10" right="10" top="5" />
                </padding>
            </StackPane>
            <StackPane alignment="CENTER">
                <padding>
                    <Insets bottom="10" left="10" right="20" top="10" />
                </padding>
                <AnchorPane fx:id="notificationButton" maxWidth="35" prefHeight="30">
                    <ImageView fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                        <Image url="/images/notification.png" />
                    </ImageView>
                </AnchorPane>
            </StackPane>
        </items>
    </SplitPane>

```
