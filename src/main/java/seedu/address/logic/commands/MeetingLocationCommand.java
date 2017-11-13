package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.logic.MrtMapLogic;
import seedu.address.ui.MrtMapUI;

//@@author Yew Onn
/**
 * Display a list of mrt stations that minimises the accumulated travelling time
 * of every specified individual
 */
public class MeetingLocationCommand extends Command {
    private final int NUM_DISPLAY_STATION = 3;
    public static final String COMMAND_WORD = "MeetingLocation";
    public static final String COMMAND_ALIAS = "ml";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": takes in a list of indices of persons who appeared in the last list and get the "
            + "most convenient station for them.\n"
            + "Parameters: Indices separated by spaces (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 5 7 8";

    public static final String MESSAGE_MEETING_LOCATION_SUCCESS = "meeting location successfully arranged!";

    private final Index[] listOfIndex;

    public MeetingLocationCommand(int[] listOfIndex) {
        this.listOfIndex = new Index[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            this.listOfIndex[i] = Index.fromOneBased(listOfIndex[i]);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < listOfIndex.length; i++) {
            if (listOfIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        ArrayList<String> mrtStations = new ArrayList<String>();
        for (int i = 0; i < listOfIndex.length; i++) {
            String mrtStation = model.getAddressBook().getPersonList().
                    get(listOfIndex[i].getZeroBased()).getMrt().value;
            mrtStations.add(mrtStation);
        }

        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> sortedStationNames = mrtMapLogic.getSortedMrtList(mrtStations);
        MrtMapUI mrtMapUI = new MrtMapUI();
        mrtMapUI.displayConvenientPoints(sortedStationNames, NUM_DISPLAY_STATION);
        String showMrtInfoToUser = getMrtInfos(sortedStationNames, NUM_DISPLAY_STATION);
        return new CommandResult(String.format(MESSAGE_MEETING_LOCATION_SUCCESS)
                + showMrtInfoToUser);

    }

    public int[] getSortedZeroBasedIndex() {
        int[] thisIndexList = new int[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            thisIndexList[i] = listOfIndex[i].getZeroBased();
        }
        Arrays.sort(thisIndexList);
        return thisIndexList;
    }

    /**
     * Returns the info of schedule to be shown to the user later.
     */
    public String getMrtInfos(ArrayList<String> stationNames, int numStationToDisplay) {
        String toShow = "\nTop Metting Location: ";
        System.out.println("toShow = "+toShow);
        for (int i = 0; i < stationNames.size() && i < numStationToDisplay; i++) {
            toShow += "\n";
            String currLine = Integer.toString(i + 1);
            currLine += ") ";
            currLine += stationNames.get(i);
            toShow += currLine;
            System.out.println("currLine = "+currLine);
        }
        return toShow;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArrangeCommand // instanceof handles nulls
                && Arrays.equals(this.getSortedZeroBasedIndex(), ((ArrangeCommand) other).getSortedZeroBasedIndex()));
        // state check
    }
}

