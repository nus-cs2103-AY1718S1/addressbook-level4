package seedu.address.commons.events.ui;

import seedu.address.commons.core.StarWars;
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the star wars window.
 */
public class StarWarsEvent extends BaseEvent {

    private StarWars starWars;

    public StarWarsEvent(StarWars starWarsStreamer) {
        starWars = starWarsStreamer;
    }

    public StarWars getStarWars() {
        return starWars;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
