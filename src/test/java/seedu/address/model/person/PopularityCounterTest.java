//@@author tshradheya
package seedu.address.model.person;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

import org.junit.Test;

public class PopularityCounterTest {

    @Test
    public void popularityCounterEquals() {

        PopularityCounter popularityCounter = new PopularityCounter();
        PopularityCounter samePopularityCounter = new PopularityCounter();

        assertEquals(popularityCounter, samePopularityCounter);

        samePopularityCounter.increasePopularityCounter();

        assertNotSame(popularityCounter, samePopularityCounter);
    }
}
