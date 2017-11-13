//@@author thanhson16198
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowWeatherRequestEvent;

/**
 *
 * Show the real-time weather on the `BrowserPanel`
 */
public class WeatherCommand extends Command {
    public static final String COMMAND_WORD = "weather";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows Weather.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_WEATHER_MESSAGE = "Opened Weather on the Browser.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowWeatherRequestEvent());
        return new CommandResult(SHOWING_WEATHER_MESSAGE);
    }
}
//@@author
