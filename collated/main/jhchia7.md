# jhchia7
###### /java/seedu/address/logic/commands/SendCommand.java
``` java

/**
 * Sends a message to the contact through email
 */

public class SendCommand extends Command {

    public static final String COMMAND_WORD = "send";
    public static final String COMMAND_ALIAS = "snd";
    public static final String COMMAND_HELP = "send INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up third-party communication application with"
                                                            + " the information of the person identified"
                                                            + " by the index number used in the last person listing. ";


    public static final String MESSAGE_OPEN_MAIL_SUCCESS = "Opened Mail App...";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to send message to
     */
    public SendCommand(Index index) {
        this.index = index;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToCommunicate = lastShownList.get(index.getZeroBased());
        String emailOfPerson = personToCommunicate.getEmail().toString();

        //Open Windows 10 Mail app
        try {
            Process p = Runtime.getRuntime().exec("cmd /c start mailto:" + emailOfPerson);
            p.waitFor();
            System.out.println("Mail launched!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventsCenter.getInstance().post(new SendMessageEvent());
        return new CommandResult(String.format(MESSAGE_OPEN_MAIL_SUCCESS));
    }


}
```
###### /java/seedu/address/logic/parser/SendCommandParser.java
``` java

/**
 * Parses input arguments and creates a new SendCommand object
 */
public class SendCommandParser implements Parser<SendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SendCommand
     * and returns an SendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SendCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/model/person/ChannelId.java
``` java

/**
 * Represents a Person's Channel ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidChannelId(String)}
 */
public class ChannelId {

    public static final String MESSAGE_CHANNEL_ID_INVALID = "Person's Channel ID is invalid.";

    public final String value;

    /**
     * Validates given channel ID.
     *
     * @throws IllegalValueException if given channel ID string is invalid.
     */
    public ChannelId(String channelId) throws IllegalValueException {
        requireNonNull(channelId);
        if (!isValidChannelId(channelId)) {
            throw new IllegalValueException(MESSAGE_CHANNEL_ID_INVALID);
        }
        this.value = channelId;
    }

    /**
     * Returns true if a given string is a valid person channel ID.
     */
    public static boolean isValidChannelId (String test) {
        Channel channel = YouTubeAuthorizer.getYouTubeChannel(test, "statistics,snippet");
        boolean isChannelAvailable = (channel != null);
        return isChannelAvailable;

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChannelId // instanceof handles nulls
                && this.value.equals(((ChannelId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Channel channel;

    @FXML
    private TextFlow channelTitle;
    @FXML
    private ImageView channelThumbnail;
    @FXML
    private TextFlow channelDescription;
    @FXML
    private TextFlow subscriberCount;
    @FXML
    private TextFlow viewCount;
    @FXML
    private TextFlow videoCount;


    public BrowserPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    /**
     * Calls helper methods to get Channel title, description, subscriber count, view count
     * and create date
     * @param person target person to be selected
     * @throws IOException
     */

    private void loadPersonPage(ReadOnlyPerson person) throws IOException {

        channel = YouTubeAuthorizer.getYouTubeChannel(person.getChannelId().toString(), "statistics,snippet");

        Text title = new Text(getChannelTitle());
        title.setFont(Font.font("Calibri", 40));
        title.setFill(Color.WHITE);
        channelTitle.getChildren().clear();
        channelTitle.getChildren().add(title);

        Text description = new Text("Description:\n" + getChannelDescription());
        description.setFont(Font.font("Calibri", 20));
        description.setFill(Color.WHITE);
        channelDescription.getChildren().clear();
        channelDescription.getChildren().add(description);

        Text videoNumber = new Text("Videos: " + getVideoCount());
        videoNumber.setFont(Font.font("Calibri", 25));
        videoNumber.setFill(Color.WHITE);
        videoCount.getChildren().clear();
        videoCount.getChildren().add(videoNumber);

        Text subNumber = new Text("Subscribers: " + getSubCount());
        subNumber.setFont(Font.font("Calibri", 25));
        subNumber.setFill(Color.WHITE);
        subscriberCount.getChildren().clear();
        subscriberCount.getChildren().add(subNumber);

        Text viewNumber = new Text("Views: " + getViewCount());
        viewNumber.setFont(Font.font("Calibri", 25));
        viewNumber.setFill(Color.WHITE);
        viewCount.getChildren().clear();
        viewCount.getChildren().add(viewNumber);

        Image thumbnail = getChannelThumbnail();
        channelThumbnail.setImage(thumbnail);

    }


    /**
     * Frees resources allocated to the browser.
     */

    public void freeResources() {

        channelTitle = null;
        channelThumbnail = null;
        channelDescription = null;
        subscriberCount = null;
        viewCount = null;
        videoCount = null;

    }


    private String getChannelTitle() throws IOException {

        return channel.getSnippet().getTitle();

    }

    private Image getChannelThumbnail() {

        Image thumbnail = new Image(channel.getSnippet().getThumbnails().getHigh().getUrl());

        return thumbnail;

    }

    private String getChannelDescription() {

        return channel.getSnippet().getDescription();
    }

    private String getSubCount() {

        return formatNumber(channel.getStatistics().getSubscriberCount().longValue());
    }

    private String getViewCount() {

        return formatNumber(channel.getStatistics().getViewCount().longValue());
    }

    private String getVideoCount() {
        return formatNumber(channel.getStatistics().getVideoCount().longValue());

    }

    /**
     * Formats number with thousand, million and billion suffix
     * @param number to be formatted with suffix
     * @return
     */
    private String formatNumber(long number) {
        final long thousand = 1000L;
        final long million = 1000000L;
        final long billion = 1000000000L;

        if (number >= billion) {
            return String.format("%.1f%c", (double) number / billion, 'b');
        } else if (number >= million) {
            return String.format("%.1f%c", (double) number / million, 'm');
        } else if (number >= thousand) {
            return String.format("%.1f%c", (double) number / thousand, 'k');
        } else {
            return number + "";
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
```
###### /resources/view/BrowserPanel.fxml
``` fxml

<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <children>
    <ScrollPane style="-fx-background: #383838;" fx:id="profile">
      <content>
        <AnchorPane prefHeight="600.0" prefWidth="800.0">
          <children>
            <TextFlow fx:id="channelDescription" layoutX="24.0" layoutY="256.0" maxWidth="1.7976931348623157E308" prefHeight="190.0" prefWidth="671.0" stylesheets="@DarkTheme.css" AnchorPane.leftAnchor="50.0" AnchorPane.rightAnchor="79.0" AnchorPane.topAnchor="280.0" />
            <ImageView fx:id="channelThumbnail" fitHeight="200.0" fitWidth="200.0" layoutX="33.0" layoutY="29.0" pickOnBounds="true" preserveRatio="true" AnchorPane.leftAnchor="50.0" AnchorPane.topAnchor="50.0" />
            <TextFlow fx:id="channelTitle" layoutX="288.0" layoutY="68.0" prefHeight="35.0" prefWidth="432.0" AnchorPane.topAnchor="50.0" />
            <TextFlow fx:id="subscriberCount" layoutX="288.0" layoutY="50.0" prefHeight="35.0" prefWidth="432.0" AnchorPane.topAnchor="105.0" />
            <TextFlow fx:id="viewCount" layoutX="288.0" layoutY="159.0" prefHeight="35.0" prefWidth="432.0" AnchorPane.topAnchor="160.0" />
            <TextFlow fx:id="videoCount" layoutX="288.0" layoutY="215.0" prefHeight="35.0" prefWidth="432.0" />
          </children>
        </AnchorPane>
      </content>
    </ScrollPane>
  </children>
</StackPane>
```
