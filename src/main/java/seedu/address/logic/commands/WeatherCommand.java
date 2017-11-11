package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowWeatherRequestEvent;

//@@author LuLechuan
/**
 * Open the Yahoo Weather Window.
 */
public class WeatherCommand extends Command {

    public static final String COMMAND_WORD = "weather";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens Yahoo Weather Window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_WEATHER_MESSAGE = "Opened Yahoo Weather Window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowWeatherRequestEvent());
        return new CommandResult(SHOWING_WEATHER_MESSAGE);
    }
}
