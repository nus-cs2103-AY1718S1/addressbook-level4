# jo-lyn-generated
###### \resources\view\CommandBox.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.StackPane?>

<StackPane styleClass="command-pane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <TextField fx:id="commandTextField" onAction="#handleCommandInputChanged" onKeyPressed="#handleKeyPress" promptText="Enter command here...">
    <StackPane.margin>
      <Insets left="10.0" right="50.0" />
    </StackPane.margin></TextField>
  <ImageView id="keyboardImage" fx:id="keyboardIcon" fitHeight="35.0" fitWidth="35.0" pickOnBounds="true" preserveRatio="true" StackPane.alignment="CENTER_RIGHT">
    <StackPane.margin>
      <Insets left="10.0" right="20.0" />
    </StackPane.margin></ImageView>
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox styleClass="v-box" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <stylesheets>
    <URL value="@LightTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>

  <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="File">
      <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
    <Menu mnemonicParsing="false" text="Help">
      <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
    </Menu>
  </MenuBar>

  <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="10.0" left="25.0" right="25.0" top="25.0" />
    </padding>
    <VBox.margin>
      <Insets />
    </VBox.margin>
  </StackPane>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" focusTraversable="false" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList">
      <StackPane fx:id="personListPanelPlaceholder" alignment="CENTER_LEFT" minWidth="350.0" VBox.vgrow="ALWAYS">
        <padding>
          <Insets bottom="25.0" left="25.0" right="10.0" top="20.0" />
        </padding>
         </StackPane>
    </VBox>
    <VBox>
      <children>
        <StackPane fx:id="resultDisplayPlaceholder" minHeight="180.0" prefHeight="180.0">
          <padding>
            <Insets bottom="15.0" left="10.0" right="25.0" top="20.0" />
          </padding>
          <VBox.margin>
            <Insets />
          </VBox.margin>
        </StackPane>
              <StackPane fx:id="personDetailPlaceholder" minWidth="400.0" prefHeight="1200.0">
          <opaqueInsets>
            <Insets />
          </opaqueInsets>
          <padding>
            <Insets bottom="25.0" left="10.0" right="25.0" top="10.0" />
          </padding>
        </StackPane>
      </children>
    </VBox>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" minHeight="30.0" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\PersonDetailPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.shape.Circle?>

<StackPane fx:id="personDetailPanel" minHeight="200.0" minWidth="400.0" styleClass="person-detail-pane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane alignment="CENTER">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <HBox alignment="CENTER_LEFT" spacing="20.0">
               <children>
                  <StackPane>
                     <children>
                        <Circle id="avatarBig" fx:id="avatar" fill="DODGERBLUE" radius="70.0" stroke="TRANSPARENT" strokeType="INSIDE" />
                        <Label id="initialBig" fx:id="initial" text="Label" textFill="WHITE" />
                     </children>
                  </StackPane>
                  <VBox alignment="CENTER_LEFT">
                     <children>
                        <VBox spacing="3.0">
                           <children>
                              <Label fx:id="name" alignment="TOP_LEFT" styleClass="person-big-label" text="\$first" wrapText="true" />
                              <FlowPane fx:id="tagsWithBorder" />
                           </children>
                           <padding>
                              <Insets bottom="20.0" />
                           </padding>
                        </VBox>
                        <VBox>
                           <children>
                              <Label fx:id="phone" graphicTextGap="20.0" styleClass="person-small-label" text="\$phone">
                                 <padding>
                                    <Insets bottom="4.0" right="4.0" top="4.0" />
                                 </padding>
                                 <graphic>
                                    <ImageView fx:id="iconPhone" fitWidth="17.0" pickOnBounds="true" preserveRatio="true">
                                       <image>
                                          <Image url="@../images/iconPhone.png" />
                                       </image></ImageView>
                                 </graphic>
                                 <opaqueInsets>
                                    <Insets />
                                 </opaqueInsets>
                              </Label>
                              <Label fx:id="email" graphicTextGap="20.0" styleClass="person-small-label" text="\$email">
                                 <padding>
                                    <Insets bottom="4.0" right="4.0" top="4.0" />
                                 </padding>
                                 <graphic>
                                    <ImageView fx:id="iconEmail" fitWidth="17.0" pickOnBounds="true" preserveRatio="true">
                                       <image>
                                          <Image url="@../images/iconEmail.png" />
                                       </image></ImageView>
                                 </graphic>
                              </Label>
                              <Label fx:id="address" graphicTextGap="20.0" styleClass="person-small-label" text="\$address">
                                 <padding>
                                    <Insets bottom="4.0" right="4.0" top="4.0" />
                                 </padding>
                                 <graphic>
                                    <ImageView fx:id="iconAddress" fitWidth="17.0" pickOnBounds="true" preserveRatio="true">
                                       <image>
                                          <Image url="@../images/iconAddress.png" />
                                       </image></ImageView>
                                 </graphic>
                              </Label>
                           </children>
                           <padding>
                              <Insets bottom="20.0" />
                           </padding>
                        </VBox>
                        <Label fx:id="remark" text="\$remark" wrapText="true" />
                     </children>
                     <padding>
                        <Insets left="30.0" />
                     </padding>
                  </VBox>
               </children>
               <opaqueInsets>
                  <Insets />
               </opaqueInsets>
               <padding>
                  <Insets bottom="20.0" left="40.0" right="20.0" top="20.0" />
               </padding>
            </HBox>
         </children>
      </GridPane>
   </children>
   <padding>
      <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
   </padding>
</StackPane>
```
###### \resources\view\PersonListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.shape.Circle?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="33.0" left="30.0" right="30.0" top="33.0" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
            <StackPane>
               <children>
                  <Circle fx:id="avatar" fill="#4faaff" radius="25.0" stroke="TRANSPARENT" strokeType="INSIDE" />
                  <Label id="initialSmall" fx:id="initial" alignment="CENTER" contentDisplay="CENTER" text="\$initial" textAlignment="CENTER" textFill="WHITE" textOverrun="CLIP" />
               </children>
            </StackPane>
            <VBox alignment="CENTER_LEFT">
               <children>
                  <HBox>
                     <children>
                    <Label fx:id="name" minWidth="0.0" styleClass="cell-name-label" text="\$first" wrapText="true" />
                     </children>
                  </HBox>
               <FlowPane fx:id="tags" />
               </children>
               <padding>
                  <Insets left="10.0" />
               </padding>
            </VBox>
      </HBox>
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
   <HBox alignment="CENTER_RIGHT">
      <children>
      <Label fx:id="id" alignment="CENTER_RIGHT" minWidth="0.0" styleClass="cell-id-label" textAlignment="RIGHT" wrapText="true" />
      </children>
      <padding>
         <Insets right="30.0" />
      </padding>
   </HBox>
</HBox>
```
###### \resources\view\PersonListPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="personListPanel" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="personListView" VBox.vgrow="ALWAYS">
      <VBox.margin>
         <Insets />
      </VBox.margin></ListView>
   <opaqueInsets>
      <Insets />
   </opaqueInsets>
   <padding>
      <Insets bottom="10.0" top="10.0" />
   </padding>
</VBox>
```
###### \resources\view\ResultDisplay.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.StackPane?>

<StackPane fx:id="placeHolder" alignment="CENTER_LEFT" styleClass="result-pane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <TextArea fx:id="resultDisplay" editable="false" maxHeight="300.0" styleClass="result-text-area" wrapText="true">
    <StackPane.margin>
      <Insets />
    </StackPane.margin>
    <padding>
      <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
    </padding></TextArea>
  <opaqueInsets>
    <Insets />
  </opaqueInsets>
   <padding>
      <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
   </padding>
</StackPane>
```
