//@@author tshradheya
package seedu.address.model.person;

/**
 * Represents a Person's frequency of visits in address book.
 * Initially the value is 0 for all
 */
public class PopularityCounter {

    private int counter;

    public PopularityCounter() {
        counter = 0;
    }

    public PopularityCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Increases popularity by 1 when searched or viewed by selecting
     */
    public void increasePopularityCounter() {
        counter++;
    }

    public void resetPopularityCounter() {
        counter = 0;
    }

    /**
     * Gets popularity counter to form the favourite list of contacts
     * @return popularity counter
     */
    public int getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PopularityCounter // instanceof handles nulls
                && this.getCounter() == ((PopularityCounter) other).getCounter()); // state check
    }
}
