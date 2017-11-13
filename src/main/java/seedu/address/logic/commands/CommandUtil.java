package seedu.address.logic.commands;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;

//@@author raisa2010
/**
 * Contains common methods for commands.
 */
public class CommandUtil {

    /**
     * Filters the valid indices in a given array of indices.
     */
    public static Index[] filterValidIndices(int lastShownListSize, Index[] indices) {
        return Arrays.stream(indices)
                .filter(currentIndex -> currentIndex.getZeroBased() < lastShownListSize)
                .toArray(Index[]::new);
    }
}
