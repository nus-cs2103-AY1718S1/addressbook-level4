package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.StarWars;
import seedu.address.commons.events.ui.StarWarsEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens the the star wars window and streams using telnet from towel.blinkenlights.nl.
 */
public class StarWarsCommand extends Command {
    public static final String COMMAND_WORD = "starwars";
    public static final String COMMAND_WORD_ABBREV = "sw";

    private StarWars starWars;

    public StarWarsCommand(StarWars starWarsStreamer) {
        starWars = starWarsStreamer;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new StarWarsEvent(starWars));
        return new CommandResult("Ba dum tsk");
    }
}
