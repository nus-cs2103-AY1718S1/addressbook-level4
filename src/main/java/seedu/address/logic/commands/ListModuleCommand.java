// @@author ahmadalkaff
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.model.module.Module;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all modules in the address book to the user.
 */
public class ListModuleCommand extends Command {

    public static final String COMMAND_WORD = "listmodules";
    public static final String COMMAND_ALIAS = "lm";

    public static final String MESSAGE_SUCCESS = "Listed all modules";

    private ArrayList<Module> moduleList = new ArrayList<>();
    private ArrayList<String> tempList = new ArrayList<>();
    private String moduleString = "Module names: ";


    @Override
    public CommandResult execute() {
        extractAllModules();
        sortModules();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + moduleString + formatModuleString());
    }

    /**
     * Formats the module list into a string
     */
    private String formatModuleString() {
        return tempList.toString().replace(",", "]").replace(" ", " [");
    }

    /**
     * Sorts the modules alphabetically
     */
    private void sortModules() {
        for (Module m : moduleList) {
            tempList.add(m.moduleName);
        }

        Collections.sort(tempList);
    }

    /**
     * Extract all the modules from the address book and stores it in a list
     */
    private void extractAllModules() {
        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            for (Module m : person.getModules()) {
                if (!moduleList.contains(m)) {
                    moduleList.add(m);
                }
            }
        }
    }

}
