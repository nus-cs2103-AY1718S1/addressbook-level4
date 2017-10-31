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
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * Created by Francis on 31/10/2017.
 */
public class ArkBot extends AbilityBot {

    private static final String BOT_TOKEN = "339790464:AAGUN2BmhnU0I2B2ULenDdIudWyv1d4OTqY";
    private static final String BOT_USERNAME = "ArkBot";

    private Logic logic;
    private Model model;
    private Optional<Message> lastKnownMessage;

    public ArkBot(Logic logic, Model model) {
        super(BOT_TOKEN, BOT_USERNAME);
        this.logic = logic;
        this.model = model;
    }

    @Override
    public int creatorId() {
        return 164502603;
    }

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
                            sender.send("Successfully added parcel.", ctx.chatId());
                        } catch (CommandException | ParseException e) {
                            sender.send(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                                    ctx.chatId());
                        }
                    });
                    // sender.send("You typed: " + combineArguments(ctx.arguments()), ctx.chatId());
                })
                .post(ctx -> sender.send("What would you like to do next?", ctx.chatId()))
                .build();
    }

    public Ability listCommand() {
        return Ability
                .builder()
                .name(ListCommand.COMMAND_WORD)
                .info("lists all parcels")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> {
                    Platform.runLater(() -> {
                        try {
                            logic.execute(ListCommand.COMMAND_WORD + " "
                                    + combineArguments(ctx.arguments()));
                            ObservableList<ReadOnlyParcel> parcels = model.getFilteredParcelList();
                            lastKnownMessage = sender.send(parseDisplayParcels(formatParcelsForBot(parcels)),
                                    ctx.chatId());
                        } catch (CommandException | ParseException e) {
                            sender.send("Sorry, I don't understand.",
                                    ctx.chatId());
                        }
                    });
                })
                .build();
    }

    public Ability deleteCommand() {
        return Ability
                .builder()
                .name(DeleteCommand.COMMAND_WORD)
                .info("deletes parcel at selected index")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action((MessageContext ctx) -> {
                    Platform.runLater(() -> {
                        try {
                            logic.execute(DeleteCommand.COMMAND_WORD + " "
                                    + combineArguments(ctx.arguments()));
                            ObservableList<ReadOnlyParcel> parcels = model.getFilteredParcelList();
                            EditMessageText editedText =
                                    new EditMessageText().setChatId(ctx.chatId())
                                                         .setMessageId(lastKnownMessage.get().getMessageId())
                                                         .setText(parseDisplayParcels(formatParcelsForBot(parcels)));

                            sender.editMessageText(editedText);
                        } catch (CommandException | ParseException | TelegramApiException e) {
                            sender.send("Sorry, I don't understand.",
                                    ctx.chatId());
                        }
                    });
                })
                .build();
    }

    private String combineArguments(String[] arguments) {
        String result = "";

        for (int i = 0; i < arguments.length; i++) {
            result += arguments[i] + " ";
        }

        return result;
    }

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
