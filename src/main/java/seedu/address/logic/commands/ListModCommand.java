package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.model.mod.Mod;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all mods in the address book to the user.
 */
public class ListModCommand extends Command {

    public static final String COMMAND_WORD = "listmod";
    public static final String COMMAND_ALIAS = "lm";

    public static final String MESSAGE_SUCCESS = "Listed all mods";

    private ArrayList<Mod> modList = new ArrayList<>();
    private ArrayList<String> tempList = new ArrayList<>();
    private String modString = "Mod names: ";


    @Override
    public CommandResult execute() {
        extractAllTags();
        sortTags();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + modString + formatTagString());
    }

    /**
     * Formats the mod list into a string
     */
    private String formatTagString() {
        return tempList.toString().replace(",", "]").replace(" ", " [");
    }

    /**
     * Sorts the mods alphabetically
     */
    private void sortTags() {
        for (Mod t : modList) {
            tempList.add(t.modName);
        }

        Collections.sort(tempList);
    }

    /**
     * Extract all the mods from the address book and stores it in a list
     */
    private void extractAllTags() {
        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            for (Mod mod : person.getMods()) {
                if (!modList.contains(mod)) {
                    modList.add(mod);
                }
            }
        }
    }

}
