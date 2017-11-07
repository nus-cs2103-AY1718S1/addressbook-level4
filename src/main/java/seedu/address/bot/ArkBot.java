package seedu.address.bot;

import static org.telegram.abilitybots.api.objects.Flag.PHOTO;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import seedu.address.bot.parcel.DisplayParcel;
import seedu.address.bot.parcel.ParcelParser;
import seedu.address.bot.qrcode.QRCodeAnalyser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * Arkbot contains all of the commands available for use.
 */
public class ArkBot extends AbilityBot {

    private static final String BOT_MESSAGE_FAILURE = "Oh dear, something went wrong! Please try again!";
    private static final String BOT_MESSAGE_SUCCESS = "%s command has been successfully executed!";
    private static final String BOT_MESSAGE_HELP = "Welcome to ArkBot, your friendly companion to ArkBot on Desktop.\n"
                                                 + "Over here, you can interface with your Desktop application with "
                                                 + "the following functions /add, /list, /delete, /undo, /redo, "
                                                 + "/complete and /help.";
    private static final String BOT_SET_COMPLETED = " " + "s/Completed";
    private static final String BOT_DEMO_JOHN = "#/RR000000000SG n/John Doe p/98765432 e/johnd@example.com "
            + "a/John street, block 123, #01-01 S123121 d/01-01-2001 s/DELIVERING";
    private static final String BOT_DEMO_BETSY = "add #/RR000000000SG n/Betsy Crowe t/frozen d/02-02-2002 "
            + "e/betsycrowe@example.com a/22 Crowe road S123123 p/1234567 t/fragile";
    private static final Logger logger = LogsCenter.getLogger(ArkBot.class);
    private Logic logic;
    private Model model;
    private String botToken;
    private String botUsername;
    private Optional<Message> lastKnownMessage;
    private boolean waitingForImage;

    public ArkBot(Logic logic, Model model, String botToken, String botUsername) {
        super(botToken, botUsername);
        this.logic = logic;
        this.model = model;
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.waitingForImage = false;
        logger.info("ArkBot successfully booted up.");
    }

    @Override
    public int creatorId() {
        return 164502603;
    }

    /**
     * Replicates the effects of AddCommand on ArkBot.
     */
    public Ability addCommand() {
        return Ability
                .builder()
                .name(AddCommand.COMMAND_WORD)
                .info("adds parcel to list")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    try {
                        logic.execute(AddCommand.COMMAND_WORD + " "
                                + combineArguments(ctx.arguments()));
                        sender.send(String.format(BOT_MESSAGE_SUCCESS, AddCommand.COMMAND_WORD), ctx.chatId());
                    } catch (CommandException | ParseException e) {
                        sender.send(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                                ctx.chatId());
                    }
                }))
                .post(ctx -> sender.send("What would you like to do next?", ctx.chatId()))
                .build();
    }

    /**
     * Replicates the effects of ListCommand on ArkBot.
     */
    public Ability listCommand() {
        return Ability
                .builder()
                .name(ListCommand.COMMAND_WORD)
                .info("lists all parcels")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    try {
                        logic.execute(ListCommand.COMMAND_WORD + " "
                                + combineArguments(ctx.arguments()));
                        ObservableList<ReadOnlyParcel> parcels = model.getFilteredUndeliveredParcelList();
                        lastKnownMessage = sender.send(parseDisplayParcels(formatParcelsForBot(parcels)),
                                ctx.chatId());
                    } catch (CommandException | ParseException e) {
                        sender.send("Sorry, I don't understand.",
                                ctx.chatId());
                    }
                }))
                .build();
    }

    /**
     * Replicates the effects of DeleteCommand on ArkBot.
     */
    public Ability deleteCommand() {
        return Ability
                .builder()
                .name(DeleteCommand.COMMAND_WORD)
                .info("deletes parcel at selected index")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action((MessageContext ctx) -> Platform.runLater(() -> {
                    try {
                        logic.execute(DeleteCommand.COMMAND_WORD + " "
                                + combineArguments(ctx.arguments()));
                        ObservableList<ReadOnlyParcel> parcels = model.getFilteredUndeliveredParcelList();
                        EditMessageText editedText =
                                new EditMessageText().setChatId(ctx.chatId())
                                                     .setMessageId(lastKnownMessage.get().getMessageId())
                                                     .setText(parseDisplayParcels(formatParcelsForBot(parcels)));
                        sender.editMessageText(editedText);
                    } catch (CommandException | ParseException | TelegramApiException e) {
                        sender.send(BOT_MESSAGE_FAILURE,
                                ctx.chatId());
                    }
                }))
                .build();
    }

    /**
     * Replicates the effects of RedoCommand on ArkBot.
     */
    public Ability redoCommand() {
        return Ability
                .builder()
                .name(RedoCommand.COMMAND_WORD)
                .info("deletes parcel at selected index")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action((MessageContext ctx) -> Platform.runLater(() -> {
                    try {
                        logic.execute(RedoCommand.COMMAND_WORD);
                        sender.send(String.format(BOT_MESSAGE_SUCCESS, RedoCommand.COMMAND_WORD), ctx.chatId());
                    } catch (CommandException | ParseException e) {
                        sender.send(BOT_MESSAGE_FAILURE, ctx.chatId());
                    }
                }))
                .build();
    }

    /**
     * Replicates the effects of UndoCommand on ArkBot.
     */
    public Ability undoCommand() {
        return Ability
                .builder()
                .name(UndoCommand.COMMAND_WORD)
                .info("deletes parcel at selected index")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action((MessageContext ctx) -> Platform.runLater(() -> {
                    try {
                        logic.execute(UndoCommand.COMMAND_WORD);
                        sender.send(String.format(BOT_MESSAGE_SUCCESS, UndoCommand.COMMAND_WORD), ctx.chatId());
                    } catch (CommandException | ParseException e) {
                        sender.send(BOT_MESSAGE_FAILURE, ctx.chatId());
                    }
                }))
                .build();
    }

    /**
     * Replicates the effects of FindCommand on ArkBot.
     */
    public Ability findCommand() {
        return Ability
                .builder()
                .name(FindCommand.COMMAND_WORD)
                .info("adds parcel to list")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    try {
                        logic.execute(FindCommand.COMMAND_WORD + " "
                                + combineArguments(ctx.arguments()));
                        ObservableList<ReadOnlyParcel> parcels = model.getFilteredUndeliveredParcelList();
                        lastKnownMessage = sender.send(parseDisplayParcels(formatParcelsForBot(parcels)),
                                ctx.chatId());
                    } catch (CommandException | ParseException e) {
                        sender.send(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE),
                                ctx.chatId());
                    }
                }))
                .build();
    }

    /**
     * Command to complete tasks with QR code or number
     */
    public Ability completeCommand() {
        return Ability
                .builder()
                .name("complete")
                .info("completes a parcel in list")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    try {
                        if (combineArguments(ctx.arguments()).trim().equals("")) {
                            this.waitingForImage = true;
                            sender.send("Please upload QR code to complete delivery.\n"
                                    + "Type \"/cancel\" to stop uploading process.", ctx.chatId());
                        } else {
                            logic.execute(EditCommand.COMMAND_WORD + " "
                                    + combineArguments(ctx.arguments()) + BOT_SET_COMPLETED);
                            ObservableList<ReadOnlyParcel> parcels = model.getFilteredUndeliveredParcelList();
                            EditMessageText editedText =
                                    new EditMessageText().setChatId(ctx.chatId())
                                            .setMessageId(lastKnownMessage.get().getMessageId())
                                            .setText(parseDisplayParcels(formatParcelsForBot(parcels)));
                            sender.editMessageText(editedText);
                        }
                    } catch (CommandException | ParseException e) {
                        sender.send(BOT_MESSAGE_FAILURE, ctx.chatId());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }))
                .build();
    }

    public Ability cancelCommand() {
        return Ability
                .builder()
                .name("cancel")
                .info("cancels QR code upload")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    this.waitingForImage = false;
                    sender.send("QR Code upload successfully cancelled!", ctx.chatId());
                }))
                .build();
    }

    /**
     * Command to advise user on usage of bot.
     */
    public Ability helpCommand() {
        return Ability
                .builder()
                .name("help")
                .info("adds parcel to list")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    sender.send(BOT_MESSAGE_HELP, ctx.chatId());
                }))
                .post(ctx -> sender.send("What would you like to do next?", ctx.chatId()))
                .build();
    }

    /**
     * Demo command, adds JOHN and BETSY
     */
    public Ability demoCommand() {
        return Ability
                .builder()
                .name("demo")
                .info("adds demo parcels to list")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> Platform.runLater(() -> {
                    try {
                        logic.execute(AddCommand.COMMAND_WORD + " " + BOT_DEMO_JOHN);
                        logic.execute(AddCommand.COMMAND_WORD + " " + BOT_DEMO_BETSY);
                        sender.send(String.format(BOT_MESSAGE_SUCCESS, "Demo"), ctx.chatId());
                    } catch (CommandException | ParseException e) {
                        sender.send(BOT_MESSAGE_FAILURE, ctx.chatId());
                    }
                }))
                .build();
    }

    /**
     * Takes in the array of arguments that is parsed into ArkBot and
     * returns a formatted string.
     */
    private String combineArguments(String[] arguments) {
        String result = "";

        for (int i = 0; i < arguments.length; i++) {
            result += arguments[i] + " ";
        }

        return result;
    }

    /**
     * Filters the list of parcels to only show Name, Address and Phone number
     * attributed to each parcel.
     */
    private ArrayList<DisplayParcel> formatParcelsForBot(ObservableList<ReadOnlyParcel> parcels) {
        ArrayList<DisplayParcel> toDisplay = new ArrayList<>();

        Iterator<ReadOnlyParcel> parcelIterator = parcels.iterator();

        while (parcelIterator.hasNext()) {
            ReadOnlyParcel currParcel = parcelIterator.next();
            DisplayParcel displayed = new DisplayParcel(currParcel.getName(), currParcel.getAddress(),
                    currParcel.getPhone());
            toDisplay.add(displayed);
        }

        return toDisplay;
    }

    /**
     * Formats a list of Parcels to be displayed on ArkBot
     */
    private String parseDisplayParcels(ArrayList<DisplayParcel> displayParcels) {
        if (displayParcels.size() == 0) {
            return "No parcels to be displayed.";
        } else {
            String result = "";
            for (int i = 0; i < displayParcels.size(); i++) {
                result += (i + 1) + ". " + displayParcels.get(i).toString() + "\n";
            }

            return result;
        }
    }

    @Override
    public boolean checkGlobalFlags(Update update) {
        return true;
    }

    /**
     * This ability has an extra "flag". It needs a photo to activate.
     */
    public Ability onPhotoCommand() {
        return Ability
                .builder()
                .name(DEFAULT)
                .flag(PHOTO)
                .info("receives Photos")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action((MessageContext ctx) -> Platform.runLater(() -> {
                    Update update = ctx.update();
                    if (update.hasMessage() && update.getMessage().hasPhoto()) {
                        java.io.File picture = downloadPhotoByFilePath(getFilePath(getPhoto(update)));
                        QRCodeAnalyser qrca = new QRCodeAnalyser(picture);
                        ParcelParser pp = new ParcelParser();
                        try {
                            System.out.println(qrca.getDecodedText());
                            ReadOnlyParcel retrievedParcel = pp.parse(qrca.getDecodedText());
                            System.out.println(retrievedParcel);
                            if (retrievedParcel.equals(null)) {
                                sender.send("Sorry, I didn't seem to understand your image. Please try again.",
                                        ctx.chatId());
                            } else if (this.waitingForImage) {
                                int indexZeroBased = model.getFilteredUndeliveredParcelList().indexOf(retrievedParcel);

                                if (indexZeroBased < 0) {
                                    sender.send("The parcel cannot be found! Please try again.",
                                            ctx.chatId());
                                } else {
                                    logic.execute(EditCommand.COMMAND_WORD + " "
                                            + (indexZeroBased + 1) + BOT_SET_COMPLETED);
                                    ObservableList<ReadOnlyParcel> parcels = model.getFilteredUndeliveredParcelList();
                                    EditMessageText editedText =
                                            new EditMessageText().setChatId(ctx.chatId())
                                                    .setMessageId(lastKnownMessage.get().getMessageId())
                                                    .setText(parseDisplayParcels(formatParcelsForBot(parcels)));
                                    sender.editMessageText(editedText);
                                    this.waitingForImage = false;
                                }
                            } else {
                                sender.send("Here are the details of the parcel: \n" + retrievedParcel.toString(),
                                        ctx.chatId());
                            }
                        } catch (ParseException | CommandException e) {
                            e.printStackTrace();
                            sender.send(BOT_MESSAGE_FAILURE, ctx.chatId());
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                    }
                })).build();
    }


    /* The following three methods are from https://github.com/rubenlagus/TelegramBots/wiki/FAQ#how_to_get_picture */
    public PhotoSize getPhoto(Update update) {
        // Check that the update contains a message and the message has a photo
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // When receiving a photo, you usually get different sizes of it
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // We fetch the bigger photo
            return photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null);
        }

        // Return null if not found
        return null;
    }

    public String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.hasFilePath()) { // If the file_path is already present, we are done!
            return photo.getFilePath();
        } else { // If not, let find it
            // We create a GetFile method and set the file_id from the photo
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                // We execute the method using AbsSender::getFile method.
                File file = getFile(getFileMethod);
                // We now have the file_path
                return file.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null; // Just in case
    }

    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

}
