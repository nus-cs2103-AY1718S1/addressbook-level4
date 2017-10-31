package seedu.address.bot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PARCELS;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import seedu.address.bot.parcel.DisplayParcel;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
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
                .name("add")
                .info("adds parcel to list")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> {
                    Platform.runLater(() -> {
                        try {
                            logic.execute("add " + combineArguments(ctx.arguments()));
                            sender.send("Successfully added parcel.", ctx.chatId());
                        } catch (CommandException | ParseException e) {
                            sender.send(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                                    ctx.chatId());
                        }
                    });
//                    sender.send("You typed: " + combineArguments(ctx.arguments()), ctx.chatId());
                })
                .post(ctx -> sender.send("What would you like to do next?", ctx.chatId()))
                .build();

    }

    public Ability listCommand() {
        return Ability
                .builder()
                .name("list")
                .info("lists all parcels")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.ADMIN)
                .action(ctx -> {
                    Platform.runLater(() -> {
                        try {
                            logic.execute("list " + combineArguments(ctx.arguments()));
                            ObservableList<ReadOnlyParcel> Parcels = model.getFilteredParcelList();
                            sender.send(parseDisplayParcels(formatParcelsForBot(Parcels)), ctx.chatId());
                        } catch (CommandException | ParseException e) {
                            sender.send("Sorry, I don't understand.",
                                    ctx.chatId());
                        }
                    });
//                    sender.send("You typed: " + combineArguments(ctx.arguments()), ctx.chatId());
                })
//                .post(ctx -> sender.send("What would you like to do next?", ctx.chatId()))
                .build();

    }

    private String combineArguments(String[] arguments) {
        String result = "";

        for(int i = 0; i < arguments.length; i++) {
            result += arguments[i] + " ";
        }

        return result;
    }

    private ArrayList<DisplayParcel> formatParcelsForBot(ObservableList<ReadOnlyParcel> parcels) {
        ArrayList<DisplayParcel> toDisplay = new ArrayList<>();

        Iterator<ReadOnlyParcel> parcelIterator = parcels.iterator();

        while(parcelIterator.hasNext()) {
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
            for(int i = 0; i < displayParcels.size(); i++) {
                result += (i + 1) + ". " + displayParcels.get(i).toString() + "\n";
            }

            return result;
        }
    }

}