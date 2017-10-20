package seedu.address.model.module;

import java.util.Objects;

public class BookedSlot {

    public final Location location;
    public final TimeSlot timeSlot;

    public BookedSlot(Location newLocation, TimeSlot newTimeSlot) {
        this.location = newLocation;
        this.timeSlot = newTimeSlot;
    }

    public Location getLocation() {
        return location;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public boolean isSameStateAs(BookedSlot other) {
        return other == this || (other != null && other.getLocation().equals(this.location)
                && other.getTimeSlot().equals(this.timeSlot));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookedSlot // instanceof handles nulls
                && this.isSameStateAs((BookedSlot) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(location, timeSlot);
    }
}
