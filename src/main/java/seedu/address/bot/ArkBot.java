package seedu.address.bot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import seedu.address.bot.parcel.DisplayParcel;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;

public class ArkBot extends AbilityBot {

    private static final String BOT_MESSAGE_FAILURE = "Sorry, I don't understand.";
    private static final String BOT_MESSAGE_SUCCESS = "%s command has been successfully executed!";
    private static final String BOT_MESSAGE_HELP = "Welcome to ArkBot, your friendly companion to ArkBot on Desktop.\n"
                                                 + "Over here, you can interface with your Desktop application with "
                                                 + "the following functions /add, /list, /delete, /undo, /redo, "
                                                 + "/complete and /help.";
    private Logic logic;
    private Model model;
    private Optional<Message> lastKnownMessage;

    public ArkBot(Logic logic, Model model, String botToken, String botUsername) {
        super(botToken, botUsername);
        this.logic = logic;
        this.model = model;
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
                .action(ctx -> {
                    Platform.runLater(() -> {
                        try {
                            logic.execute(AddCommand.COMMAND_WORD + " "
                                    + combineArguments(ctx.arguments()));
                            sender.send(String.format(BOT_MESSAGE_SUCCESS, AddCommand.COMMAND_WORD), ctx.chatId());
                        } catch (CommandException | ParseException e) {
                            sender.send(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                                    ctx.chatId());
                        }
                    });
                })
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
                .action(ctx -> {
                    Platform.runLater(() -> {
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
                    });
                })
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
                .action(ctx -> {
                    Platform.runLater(() -> {
                        sender.send(BOT_MESSAGE_HELP, ctx.chatId());
                    });
                })
                .post(ctx -> sender.send("What would you like to do next?", ctx.chatId()))
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

}
