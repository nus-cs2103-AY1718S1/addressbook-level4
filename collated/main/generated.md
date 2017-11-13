# generated
###### \resources\view\MainWindow.fxml
``` fxml
    <MenuBar fx:id="menuBar" minHeight="30.0" prefHeight="25.0" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
            <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
            <MenuItem fx:id="aboutMenuItem" mnemonicParsing="false" onAction="#handleAbout" text="About iungo" />
        </Menu>
    </MenuBar>
```
###### \resources\view\MainWindow.fxml
``` fxml
                        <StackPane fx:id="sidePersonPlaceholder">
                           <HBox.margin>
                              <Insets bottom="10.0" left="5.0" top="10.0" />
                           </HBox.margin>
                        </StackPane>
```
###### \resources\view\PersonSideCard.fxml
``` fxml

<GridPane alignment="CENTER" minWidth="280.0" xmlns="http://javafx.com/javafx/9" xmlns:fx="http://javafx.com/fxml/1">
   <columnConstraints>
      <ColumnConstraints fillWidth="false" />
   </columnConstraints>
   <rowConstraints>
      <RowConstraints fillHeight="false" />
   </rowConstraints>
   <children>
      <VBox alignment="CENTER" minWidth="200.0" prefWidth="200.0" GridPane.columnIndex="0">
         <children>
            <ImageView fx:id="avatar" fitHeight="90.0" fitWidth="90.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../images/default_avatar.png" />
               </image>
               <VBox.margin>
                  <Insets />
               </VBox.margin>
            </ImageView>
            <HBox alignment="TOP_CENTER">
               <opaqueInsets>
                  <Insets />
               </opaqueInsets>
               <VBox.margin>
                  <Insets bottom="1.0" />
               </VBox.margin>
               <children>
                  <Label fx:id="id" styleClass="cell_big_label">
                     <minWidth>
                        <Region fx:constant="USE_PREF_SIZE" />
                     </minWidth>
                  </Label>
                  <Label fx:id="name" styleClass="cell_big_label" text="\$first" textAlignment="CENTER" wrapText="true" />
               </children>
            </HBox>
            <FlowPane fx:id="tags" alignment="CENTER">
               <VBox.margin>
                  <Insets bottom="40.0" />
               </VBox.margin>
            </FlowPane>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/phone.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/address.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="address" styleClass="cell_small_label" text="\$address" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/email.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="email" styleClass="cell_small_label" text="\$email" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/homepage.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="homepage" styleClass="cell_small_label" text="\$homepage" underline="true" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/remark.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
         </children>
         <GridPane.margin>
            <Insets />
         </GridPane.margin>
         <padding>
            <Insets top="15.0" />
         </padding>
      </VBox>
   </children>
</GridPane>
```
