package seedu.address.model.module;

import java.util.Objects;

/**
 * Represents a Booked time slot of a venue
 */
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

    /**
     * Returns true if both booked slot have the same location and time slot(case insensitive)
     */
    public boolean isSameStateAs(BookedSlot other) {
        return this.location.value.toUpperCase().equals(other.getLocation().value.toUpperCase())
                && this.timeSlot.value.toUpperCase().equals(other.getTimeSlot().value.toUpperCase());
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

    @Override
    public String toString() {
        return location.toString() + "  " + timeSlot.toString();
    }
}
